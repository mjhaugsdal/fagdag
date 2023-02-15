package org.acme;

import ch.qos.logback.classic.Logger;
import io.swagger.v3.oas.annotations.Operation;
import org.acme.model.Lanetaker;
import org.acme.model.Soknad;
import org.acme.model.SoknadSvar;
import org.acme.validate.PersonNummerValidator;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.ff4j.FF4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Path("/soknad")
public class SoknadService {


    @Autowired
    FF4j ff4j;

    private static final Logger logger
            = (Logger) LoggerFactory.getLogger(SoknadService.class);

    /***
     * trådsikker map for å holde søknader.
     */
    private static final ConcurrentMap<String, Soknad> soknadMap = new ConcurrentHashMap<>();

    /***
     * atomiclong som counter.
     *
     * */
    private static final AtomicLong idCounter = new AtomicLong();

    public SoknadService(FF4j ff4j) {
        this.ff4j = ff4j;
    }

    /***
     * inkrementerer og henter et id-nummer.
     * @return søknadsnummer.
     */
    public static String createId() {
        return String.valueOf(idCounter.getAndIncrement());
    }

    /***
     *
     * @param soknad søknad
     * @return søknadsvar med ID og status hvis alt gikk OK.
     */
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Søknadslevering")
    public SoknadSvar postSoknad(final Soknad soknad) {

        for (Lanetaker lanetaker : soknad.getLanetakere()) {
            if (!PersonNummerValidator.isValidNIN(lanetaker.getFnr())) {
                return new SoknadSvar("", "Ikke gyldig personnnummer på lånetaker");
            }
        }

        var id = createId();
        soknadMap.putIfAbsent(id, soknad);

        try {
            logger.info(soknad.toString());
        } catch (Exception ex) {
            logger.warn("Caught exception while trying to print object: " + ex.getMessage());
        }
        return new SoknadSvar(id, "Mottatt");
    }

    /***
     * endepunkt som tar imot et søknadsnummer
     * og svarer med status, «Mottatt» eller «Ukjent».
     * @param id søknadsnummeret
     * @return bekreftelse på om søknad er mottatt eller ukjent
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Hent søknad")
    public Response getSoknad(
            @QueryParam("id") final String id
    ) {

        if(ff4j.getFeature("getSoknad").isEnable()) {
            if (!id.isEmpty()) {
                if (!soknadMap.containsKey(id)) {
                    logger.info("Application with given id {} not stored", id);
                    logMetaInfo(JAXRSUtils.getCurrentMessage());
                    return Response.ok(new SoknadSvar(id, "Ukjent")).build();
                } else {
                    return Response.ok(new SoknadSvar(id, "Mottatt")).build();
                }
            } else {
                logger.info("Id not given");
                logMetaInfo(JAXRSUtils.getCurrentMessage());
                return Response.ok(new SoknadSvar(id, "Ukjent")).build();
            }
        }
        else {
            return Response.status(501).build();
        }
    }

    private void logMetaInfo(Message currentMessage) {
        HttpServletRequest request = (HttpServletRequest) currentMessage.get(AbstractHTTPDestination.HTTP_REQUEST);
        logger.debug(request.getRemoteAddr());
        logger.debug(request.getRequestURI() + request.getServletPath());
        logger.debug(request.getMethod());
    }
}
