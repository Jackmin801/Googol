package com.jacksteam.googol.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListElement{
    private String title;
    private String link;
    private String excerpt;

    public ListElement(
        @JsonProperty("title") String title,
        @JsonProperty("link") String link,
        @JsonProperty("excerpt") String excerpt
        ){
            this.title = title;
            this.link = link;
            this.excerpt = excerpt;
    }

    // Getters
    public String getTitle() {
        return title;
    }
    public String getLink() {
        return link;
    }
    public String getExcerpt() {
        return excerpt;
    }

    // Setters
    public void setLink(String link) {
        this.link = link;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

}