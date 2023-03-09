package com.example.movielibrary.Models.SearchModels.TopLists;

public class BoxOfficeModel {
    String id;
    String rank;
    String title;
    String image;
    String weekend;
    String gross;
    String weeks;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWeekend() {
        return weekend;
    }

    public void setWeekend(String weekend) {
        this.weekend = weekend;
    }

    public String getGross() {
        return gross;
    }

    public void setGross(String gross) {
        this.gross = gross;
    }

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    public BoxOfficeModel(String id, String rank, String title, String image, String weekend, String gross, String weeks) {
        this.id = id;
        this.rank = rank;
        this.title = title;
        this.image = image;
        this.weekend = weekend;
        this.gross = gross;
        this.weeks = weeks;
    }
}
