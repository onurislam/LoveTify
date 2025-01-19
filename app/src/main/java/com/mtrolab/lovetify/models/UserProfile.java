package com.mtrolab.lovetify.models;

public class UserProfile {
    private String name;
    private int age;
    private String info;
    private String imageUrl;

    public UserProfile(String name, int age, String info, String imageUrl) {
        this.name = name;
        this.age = age;
        this.info = info;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
} 