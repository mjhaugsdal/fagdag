package org.acme.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.BigDecimal;
import java.util.List;

public class Soknad {


    @JsonProperty("lanetakere")
    List<Lanetaker> lanetakere;
    @JsonProperty("lanebelop")
    private BigDecimal lanebelop;
    @JsonProperty("behov")
    private String behov;
    @JsonProperty("lopetid")
    private int lopetid; //antall måneder, most likely
    @JsonProperty("avdragsfriPeriode")
    private int avdragsfriPeriode;
    @JsonProperty("type")
    private String type;


    public Soknad() {
        //empty
    }

    public Soknad(List<Lanetaker> lanetakere, BigDecimal lanebelop, String behov, int lopetid, int avdragsfriPeriode, String type) {
        this.lanetakere = lanetakere;
        this.lanebelop = lanebelop;
        this.behov = behov;
        this.lopetid = lopetid;
        this.avdragsfriPeriode = avdragsfriPeriode;
        this.type = type;
    }

    public List<Lanetaker> getLanetakere() {
        return lanetakere;
    }

    public void setLanetakere(List<Lanetaker> lanetakere) {
        this.lanetakere = lanetakere;
    }

    public BigDecimal getLanebelop() {
        return lanebelop;
    }

    public void setLanebelop(BigDecimal lanebelop) {
        this.lanebelop = lanebelop;
    }

    public String getBehov() {
        return behov;
    }

    public void setBehov(String behov) {
        this.behov = behov;
    }

    public int getLopetid() {
        return lopetid;
    }

    public void setLopetid(int lopetid) {
        this.lopetid = lopetid;
    }

    public int getAvdragsfriPeriode() {
        return avdragsfriPeriode;
    }

    public void setAvdragsfriPeriode(int avdragsfriPeriode) {
        this.avdragsfriPeriode = avdragsfriPeriode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(this);
    }
}
