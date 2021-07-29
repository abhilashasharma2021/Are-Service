package com.areservices.model;

public class TopRatedServiceModel {
    private String service_Name;

    public TopRatedServiceModel(String service_Name, int image) {
        this.service_Name = service_Name;
        this.image = image;
    }

    public String getService_Name() {
        return service_Name;
    }

    public void setService_Name(String service_Name) {
        this.service_Name = service_Name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    private int image;


}
