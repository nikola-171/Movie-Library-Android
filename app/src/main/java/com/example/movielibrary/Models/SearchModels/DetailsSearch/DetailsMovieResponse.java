package com.example.movielibrary.Models.SearchModels.DetailsSearch;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.movielibrary.Models.SearchModels.SimilarMovieModel;
import com.example.movielibrary.Models.SearchModels.TvSeriesInfo;

import java.io.Serializable;
import java.util.List;

public class DetailsMovieResponse implements Serializable {

    String id = "";
    String title = "";
    String originalTitle = "";
    String fullTitle = "";
    String type = "";
    String year = "";
    String image = "";
    String releaseDate = "";
    String runtimeMins = "";
    String runtimeStr = "";
    String plot = "";
    String plotLocal = "";
    String plotLocalIsRtl = "";
    String awards = "";
    List<Actor> actorList = null;
    String imDbRating = "";
    String imDbRatingVotes = "";
    String errorMessage = "";
    String genres = "";
    String companies = "";
    String languages = "";
    String contentRating = "";
    String metacriticRating = "";
    BoxOffice boxOffice;
    TvSeriesInfo tvSeriesInfo;

    public TvSeriesInfo getTvSeriesInfo() {
        return tvSeriesInfo;
    }

    public void setTvSeriesInfo(TvSeriesInfo tvSeriesInfo) {
        this.tvSeriesInfo = tvSeriesInfo;
    }

    public BoxOffice getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(BoxOffice boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getMetacriticRating() {
        return metacriticRating;
    }

    public void setMetacriticRating(String metacriticRating) {
        this.metacriticRating = metacriticRating;
    }

    public String getContentRating() {
        return contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    String keywords = "";

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getCompanies() {
        return companies;
    }

    public void setCompanies(String companies) {
        this.companies = companies;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getImDbRating() {
        return imDbRating;
    }

    public void setImDbRating(String imDbRating) {
        this.imDbRating = imDbRating;
    }

    public String getImDbRatingVotes() {
        return imDbRatingVotes;
    }

    public void setImDbRatingVotes(String imDbRatingVotes) {
        this.imDbRatingVotes = imDbRatingVotes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRuntimeMins() {
        return runtimeMins;
    }

    public void setRuntimeMins(String runtimeMins) {
        this.runtimeMins = runtimeMins;
    }

    public String getRuntimeStr() {
        return runtimeStr;
    }

    public void setRuntimeStr(String runtimeStr) {
        this.runtimeStr = runtimeStr;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPlotLocal() {
        return plotLocal;
    }

    public void setPlotLocal(String plotLocal) {
        this.plotLocal = plotLocal;
    }

    public String getPlotLocalIsRtl() {
        return plotLocalIsRtl;
    }

    public void setPlotLocalIsRtl(String plotLocalIsRtl) {
        this.plotLocalIsRtl = plotLocalIsRtl;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }

    public List<SimilarMovieModel> similars;

    public List<SimilarMovieModel> getSimilars() {
        return similars;
    }

    public void setSimilars(List<SimilarMovieModel> similars) {
        this.similars = similars;
    }
}

