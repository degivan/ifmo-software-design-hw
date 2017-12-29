package ru.degtiarenko.hw.mvc.dao;

import ru.degtiarenko.hw.mvc.model.Business;
import ru.degtiarenko.hw.mvc.model.BusinessList;

import java.util.List;

public interface BusinessDao {
    void addBusiness(String name, Business business);

    List<BusinessList> getLists();

    void addList(BusinessList businessList);

    void dropList(String name);

    void finishBusiness(String name, String description);
}
