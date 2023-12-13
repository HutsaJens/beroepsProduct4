package com.brogle.beroepsproduct4.models;

/**
 *
 * @author Jens
 */
public class Werknemer extends Persoon {

    private String bedden_range;

    public Werknemer(String naam, String bsn, String bedden_range) {
        this.naam = naam;
        this.bsn = bsn;
        this.bedden_range = bedden_range;
    }

    @Override
    public String toString() {
        return naam + " | " + bsn;
    }

    public String getBedden_range() {
        return bedden_range;
    }

    public void setBedden_range(String bedden_range) {
        this.bedden_range = bedden_range;
    }
}
