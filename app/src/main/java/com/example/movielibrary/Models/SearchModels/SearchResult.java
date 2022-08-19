package com.example.movielibrary.Models.SearchModels;

import java.util.List;

public class SearchResult {

    String searchType;
    String expression;
    List<MovieSearchResult> results;
    String errorMessage;

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public List<MovieSearchResult> getResults() {
        return results;
    }

    public void setResults(List<MovieSearchResult> results) {
        this.results = results;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
