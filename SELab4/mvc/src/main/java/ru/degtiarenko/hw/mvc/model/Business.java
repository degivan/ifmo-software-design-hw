package ru.degtiarenko.hw.mvc.model;

public class Business {
    private String listName;
    private String description;

    public Business() {}

    public Business(String listName, String description) {
        this.description = description;
        this.listName = listName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
