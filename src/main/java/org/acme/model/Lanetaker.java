package org.acme.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Lanetaker {

    @JsonProperty("fnr")
    String fnr;
    @JsonProperty("navn")
    String navn;

    public Lanetaker(){
        //empty
    }

    public Lanetaker(String fnr, String navn) {
        this.fnr = fnr;
        this.navn = navn;
    }

    public String getFnr() {
        return fnr;
    }

    public void setFnr(String fnr) {
        this.fnr = fnr;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }
}
