package com.example.movielibrary.Models.SearchModels;

public class TvSeriesInfo {
    private String yearEnd;
    private String creators;
    private String[] seasons;
    private Creator[] creatorList;

    public String getYearEnd() {
        return yearEnd;
    }

    public void setYearEnd(String yearEnd) {
        this.yearEnd = yearEnd;
    }

    public String getCreators() {
        return creators;
    }

    public void setCreators(String creators) {
        this.creators = creators;
    }

    public String[] getSeasons() {
        return seasons;
    }

    public void setSeasons(String[] seasons) {
        this.seasons = seasons;
    }

    public Creator[] getCreatorList() {
        return creatorList;
    }

    public void setCreatorList(Creator[] creatorList) {
        this.creatorList = creatorList;
    }
}
