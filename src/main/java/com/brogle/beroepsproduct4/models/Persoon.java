package com.brogle.beroepsproduct4.models;

/**
 *
 * @author Jens
 */
public abstract class Persoon {
    
    protected String naam, bsn;

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBsn() {
        return bsn;
    }

    public void setBsn(String bsn) {
        this.bsn = bsn;
    }
}
