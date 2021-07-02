package com.areservices.model;

public class ServiceMenModel {
    private String name;
    private String address;
    private String workingStatus;

    public ServiceMenModel(String name, String address, String stream,String workingStatus) {
        this.name = name;
        this.address = address;
        this.stream = stream;
        this.workingStatus = workingStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkingStatus() {
        return workingStatus;
    }

    public void setWorkingStatus(String workingStatus) {
        this.workingStatus = workingStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    private String stream;
}
