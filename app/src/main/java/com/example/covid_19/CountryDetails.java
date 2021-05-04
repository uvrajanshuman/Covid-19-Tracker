package com.example.covid_19;

public class CountryDetails {
    private long updated;
    private String name,flagUrl,cases,deaths,recovered,active;

    public long getUpdated() {
        return updated;
    }

    public String getName() {
        return name;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public String getCases() {
        return cases;
    }

    public String getDeaths() {
        return deaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getActive() {
        return active;
    }

    public CountryDetails(long updated, String name, String flagUrl, String cases, String deaths, String recovered, String active) {
        this.updated = updated;
        this.name = name;
        this.flagUrl = flagUrl;
        this.cases = cases;
        this.deaths = deaths;
        this.recovered = recovered;
        this.active = active;
    }
    /*
    "cases":49970,
      "todayCases":0,
      "deaths":2017,
      "todayDeaths":0,
      "recovered":38648,
      "todayRecovered":0,
      "active":9305,
      "critical":93,
      "casesPerOneMillion":1271,
      "deathsPerOneMillion":51,
      "tests":174711,
      "testsPerOneMillion":4443,
      "population":39326408,
      "continent":"Asia",
      "oneCasePerPeople":787,
      "oneDeathPerPeople":19497,
      "oneTestPerPeople":225,
      "activePerOneMillion":236.61,
      "recoveredPerOneMillion":982.75,
      "criticalPerOneMillion":2.36
     */

}
