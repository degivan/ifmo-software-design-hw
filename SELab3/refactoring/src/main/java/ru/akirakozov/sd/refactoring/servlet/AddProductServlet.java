package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author akirakozov
 */
public class AddProductServlet extends AbstractServlet {

    protected List<String> getValues(ResultSet rs, HttpServletRequest request) {
        List<String> values = new ArrayList<>();
        values.add("OK");
        return values;
    }

    protected String getSql(HttpServletRequest request) {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        return "INSERT INTO PRODUCT (NAME, PRICE) VALUES ('" + name + "'," + price + ")";
    }
}
