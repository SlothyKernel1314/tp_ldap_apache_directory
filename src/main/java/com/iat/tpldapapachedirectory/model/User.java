package com.iat.tpldapapachedirectory.model;


import org.springframework.stereotype.Component;

@Component
public class User {

    private String name; // sn
    private String category; // radiusTunnelPrivateGroup

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
