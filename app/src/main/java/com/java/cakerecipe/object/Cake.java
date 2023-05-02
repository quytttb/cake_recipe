package com.java.cakerecipe.object;

import java.io.Serializable;

public class Cake implements Serializable {
    private String name;
    private String description;
    private byte[] image;

    public Cake() {
    }

    public Cake(byte[] image, String name, String description) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}