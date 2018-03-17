package ru.itmo.degtiarenko.hw.search.logic;

public class StartRequest {
    private final String request;

    public StartRequest(String searchRequest) {
        this.request = searchRequest;
    }

    public String getRequest() {
        return request;
    }
}
