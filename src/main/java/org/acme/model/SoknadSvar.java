package org.acme.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SoknadSvar {

    @JsonProperty("id")
    String id;
    @JsonProperty("status")
    String status;

    public SoknadSvar() {
        //empty
    }

    public SoknadSvar(String id, String status) {
        this.id = id;
        this.status = status;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
