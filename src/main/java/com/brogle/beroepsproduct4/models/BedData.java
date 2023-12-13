package com.brogle.beroepsproduct4.models;

import java.util.Objects;

public class BedData {

    final private boolean tolong;
    final private String turns, lastturn;

    public BedData(boolean tolong, String turns, String lastturn) {
        this.tolong = tolong;
        this.turns = turns;
        this.lastturn = lastturn;
    }
    
    @Override 
    public String toString() {
        return tolong + " - " + turns + " - " + lastturn;
    }

    public boolean isTolong() {
        return tolong;
    }

    public String getTurns() {
        return turns;
    }

    public String getLastturn() {
        return lastturn;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BedData other = (BedData) obj;
        return tolong == other.tolong &&
                Objects.equals(turns, other.turns) &&
                Objects.equals(lastturn, other.lastturn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tolong, turns, lastturn);
    }
}
