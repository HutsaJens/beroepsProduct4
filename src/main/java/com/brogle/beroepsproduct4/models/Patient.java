package com.brogle.beroepsproduct4.models;

public class Patient extends Persoon {
    
    private String gewicht, lengte;
    private Double tijd;
    
    public Patient (String naam, String bsn, String gewicht, String lengte, Double tijd) {
        this.naam = naam;
        this.bsn = bsn;
        this.gewicht = gewicht;
        this.lengte = lengte;
        this.tijd = tijd;
    }
    
    @Override
    public String toString() {
        return naam + " | " + bsn;
    }

    public String getGewicht() {
        return gewicht;
    }

    public void setGewicht(String gewicht) {
        this.gewicht = gewicht;
    }

    public String getLengte() {
        return lengte;
    }

    public void setLengte(String lengte) {
        this.lengte = lengte;
    }

    public Double getTijd() {
        return tijd;
    }

    public void setTijd(Double tijd) {
        this.tijd = tijd;
    }
}
