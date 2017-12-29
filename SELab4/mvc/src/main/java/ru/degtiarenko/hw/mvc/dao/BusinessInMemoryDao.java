package ru.degtiarenko.hw.mvc.dao;

import ru.degtiarenko.hw.mvc.model.Business;
import ru.degtiarenko.hw.mvc.model.BusinessList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BusinessInMemoryDao implements BusinessDao {
    private Map<String, BusinessList> businessLists = new HashMap<>();

    @Override
    public void addBusiness(String name, Business business) {
        businessLists.computeIfAbsent(name, n -> new BusinessList(new ArrayList<>(), n))
                .addBusiness(business);
    }

    @Override
    public List<BusinessList> getLists() {
        return new ArrayList<>(businessLists.values());
    }

    @Override
    public void addList(BusinessList businessList) {
        businessLists.put(businessList.getName(), businessList);
    }

    @Override
    public void dropList(String name) {
        businessLists.remove(name);
    }

    @Override
    public void finishBusiness(String name, String description) {
        BusinessList businessList = businessLists.remove(name);
        if (businessList != null) {
            List<Business> businesses = businessList.getBusinesses()
                    .stream()
                    .filter(b -> !b.getDescription().equals(description))
                    .collect(Collectors.toList());
            businessLists.put(name, new BusinessList(businesses, name));
        }
    }
}
