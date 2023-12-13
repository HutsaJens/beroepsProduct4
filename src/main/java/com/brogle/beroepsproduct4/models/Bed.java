package com.brogle.beroepsproduct4.models;

import java.sql.Date;

/**
 *
 * @author Jens
 */
public class Bed {
    
    private Patient patient;
    private String bedID, numDraaien;
    private Date tijd;
    private Boolean toLong;

    public Bed(Patient patient, String bedID, String numDraaien, Date tijd, Boolean toLong) {
        this.patient = patient;
        this.bedID = bedID;
        this.numDraaien = numDraaien;
        this.tijd = tijd;
        this.toLong = toLong;
    }
    
    @Override
    public String toString() {
        return "Bed: " + bedID + " | BSN: " + patient.getBsn();
    }

    public Boolean getToLong() {
        return toLong;
    }

    public void setToLong(Boolean toLong) {
        this.toLong = toLong;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getBedID() {
        return bedID;
    }

    public void setBedID(String bedID) {
        this.bedID = bedID;
    }

    public String getNumDraaien() {
        return numDraaien;
    }

    public void setNumDraaien(String numDraaien) {
        this.numDraaien = numDraaien;
    }

    public Date getTijd() {
        return tijd;
    }

    public void setTijd(Date tijd) {
        this.tijd = tijd;
    }
}
