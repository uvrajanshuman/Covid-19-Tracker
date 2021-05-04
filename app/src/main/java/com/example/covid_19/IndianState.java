package com.example.covid_19;

public class IndianState {
    private String name,confirmed,active,recovered,death,deltaConfirmed,deltaDeath,deltaActive,deltaRecovered;

    public IndianState(String name, String confirmed, String active, String recovered,
                       String death, String deltaConfirmed, String deltaDeath,
                       String deltaActive, String deltaRecovered) {
        this.name = name;
        this.confirmed = confirmed;
        this.active = active;
        this.recovered = recovered;
        this.death = death;
        this.deltaConfirmed = deltaConfirmed;
        this.deltaDeath = deltaDeath;
        this.deltaActive = deltaActive;
        this.deltaRecovered = deltaRecovered;
    }

    public String getDeltaConfirmed() {
        return deltaConfirmed;
    }

    public String getDeltaDeath() {
        return deltaDeath;
    }

    public String getDeltaActive() {
        return deltaActive;
    }

    public String getDeltaRecovered() {
        return deltaRecovered;
    }

    public String getName() {
        return name;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public String getActive() {
        return active;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getDeath() {
        return death;
    }
}
