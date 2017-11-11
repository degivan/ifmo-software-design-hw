package ru.akirakozov.sd.refactoring.servlet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ValueRetriever {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public List<String> apply(ResultSet rs) throws SQLException {
        List<String> result = new ArrayList<>();
        result.add("<html><body>");
        result.add(name);
        result.addAll(getData(rs));
        result.add("</body></html>");
        return result;
    }


    protected abstract List<String> getData(ResultSet rs) throws SQLException;
}
