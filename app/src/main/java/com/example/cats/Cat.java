package com.example.cats;

import com.google.gson.annotations.SerializedName;

public class Cat {

    private String name;
    private String url;
    private String description;
    private String origin;
    @SerializedName("life_span")
    private String lifeSpan;
    @SerializedName("dog_friendly")
    private String dogFriendly;
    private String temperament;
    @SerializedName("wikipedia_url")
    private String wikipediaUrl;
    private Image image;

    public Cat(String name, String url, String description, String origin, String lifeSpan, String dogFriendly, String temperament, String wikipediaUrl) {
        this.name = name;
        this.url = url;
        this.description = description;
        this.origin = origin;
        this.lifeSpan = lifeSpan;
        this.dogFriendly = dogFriendly;
        this.temperament = temperament;
        this.wikipediaUrl = wikipediaUrl;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(String lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public String getDogFriendly() {
        return dogFriendly;
    }

    public void setDogFriendly(String dogFriendly) {
        this.dogFriendly = dogFriendly;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public String getWikipediaUrl() {
        return wikipediaUrl;
    }

    public void setWikipediaUrl(String wikipediaUrl) {
        this.wikipediaUrl = wikipediaUrl;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
