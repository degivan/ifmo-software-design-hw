package ru.degtiarenko.hw.mvc.model;

import java.util.List;

public class BusinessList {
    private String name;

    private List<Business> businesses;

    public BusinessList() { }

    public BusinessList(List<Business> businesses, String name) {
        this.businesses = businesses;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Business> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<Business> businesses) {
        this.businesses = businesses;
    }

    public void addBusiness(Business business) {
        businesses.add(business);
    }
}
