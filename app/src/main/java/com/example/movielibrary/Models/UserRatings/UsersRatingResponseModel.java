package com.example.movielibrary.Models.UserRatings;

import java.util.List;

public class UsersRatingResponseModel {
    private String imDbId;
    private String title;
    private String fullTitle;
    private String type;
    private String year;
    private String totalRating;
    private String totalRatingVotes;
    private String errorMessage;
    private List<AllRatingsItem> ratings;

    private DemographicRatings demographicAll;
    private DemographicRatings demographicMales;
    private DemographicRatings demographicFemales;

    private Ratings top1000Voters;
    private Ratings usUsers;
    private Ratings nonUSUsers;

    public String getImDbId() {
        return imDbId;
    }

    public void setImDbId(String imDbId) {
        this.imDbId = imDbId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(String totalRating) {
        this.totalRating = totalRating;
    }

    public String getTotalRatingVotes() {
        return totalRatingVotes;
    }

    public void setTotalRatingVotes(String totalRatingVotes) {
        this.totalRatingVotes = totalRatingVotes;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<AllRatingsItem> getRatings() {
        return ratings;
    }

    public void setRatings(List<AllRatingsItem> ratings) {
        this.ratings = ratings;
    }

    public DemographicRatings getDemographicAll() {
        if(demographicAll == null)
            return new DemographicRatings();

        return demographicAll;
    }

    public void setDemographicAll(DemographicRatings demographicAll) {
        this.demographicAll = demographicAll;
    }

    public DemographicRatings getDemographicMales() {
        if(demographicMales == null)
            return new DemographicRatings();

        return demographicMales;
    }

    public void setDemographicMales(DemographicRatings demographicMales) {
        this.demographicMales = demographicMales;
    }

    public DemographicRatings getDemographicFemales() {
        if(demographicFemales == null)
            return new DemographicRatings();

        return demographicFemales;
    }

    public void setDemographicFemales(DemographicRatings demographicFemales) {
        this.demographicFemales = demographicFemales;
    }

    public Ratings getTop1000Voters() {
        if(top1000Voters == null)
            return new Ratings();

        return top1000Voters;
    }

    public void setTop1000Voters(Ratings top1000Voters) {
        this.top1000Voters = top1000Voters;
    }

    public Ratings getUsUsers() {
        if(usUsers == null)
            return new Ratings();

        return usUsers;
    }

    public void setUsUsers(Ratings usUsers) {
        this.usUsers = usUsers;
    }

    public Ratings getNonUSUsers() {
        if(nonUSUsers == null)
            return new Ratings();

        return nonUSUsers;
    }

    public void setNonUSUsers(Ratings nonUSUsers) {
        this.nonUSUsers = nonUSUsers;
    }
}
