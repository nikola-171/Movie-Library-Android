package com.example.movielibrary.Models.ReviewsModel;

public class ReviewItemModel {

    private String username;
    private String userUrl;
    private String reviewLink;
    private String date;
    private String rate;
    private String helpful;
    private String title;
    private String content;
    private Boolean warningSpoilers;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getReviewLink() {
        return reviewLink;
    }

    public void setReviewLink(String reviewLink) {
        this.reviewLink = reviewLink;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getHelpful() {
        return helpful;
    }

    public void setHelpful(String helpful) {
        this.helpful = helpful;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getWarningSpoilers() {
        return warningSpoilers;
    }

    public void setWarningSpoilers(Boolean warningSpoilers) {
        this.warningSpoilers = warningSpoilers;
    }
}
