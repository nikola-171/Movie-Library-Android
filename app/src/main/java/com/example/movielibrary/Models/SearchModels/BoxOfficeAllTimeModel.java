package com.example.movielibrary.Models.SearchModels;

public class BoxOfficeAllTimeModel {

    String id;
    String rank;
    String title;
    String worldwideLifetimeGross;
    String domesticLifetimeGross;
    String domestic;
    String foreignLifetimeGross;
    String foreign;
    String year;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWorldwideLifetimeGross() {
        return worldwideLifetimeGross;
    }

    public void setWorldwideLifetimeGross(String worldwideLifetimeGross) {
        this.worldwideLifetimeGross = worldwideLifetimeGross;
    }

    public String getDomesticLifetimeGross() {
        return domesticLifetimeGross;
    }

    public void setDomesticLifetimeGross(String domesticLifetimeGross) {
        this.domesticLifetimeGross = domesticLifetimeGross;
    }

    public String getDomestic() {
        return domestic;
    }

    public void setDomestic(String domestic) {
        this.domestic = domestic;
    }

    public String getForeignLifetimeGross() {
        return foreignLifetimeGross;
    }

    public void setForeignLifetimeGross(String foreignLifetimeGross) {
        this.foreignLifetimeGross = foreignLifetimeGross;
    }

    public String getForeign() {
        return foreign;
    }

    public void setForeign(String foreign) {
        this.foreign = foreign;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public BoxOfficeAllTimeModel(String id, String rank, String title, String worldwideLifetimeGross, String domesticLifetimeGross, String domestic, String foreignLifetimeGross, String foreign, String year) {
        this.id = id;
        this.rank = rank;
        this.title = title;
        this.worldwideLifetimeGross = worldwideLifetimeGross;
        this.domesticLifetimeGross = domesticLifetimeGross;
        this.domestic = domestic;
        this.foreignLifetimeGross = foreignLifetimeGross;
        this.foreign = foreign;
        this.year = year;
    }
}
