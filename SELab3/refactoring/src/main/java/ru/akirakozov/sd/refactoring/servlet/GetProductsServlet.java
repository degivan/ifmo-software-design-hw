package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractServlet {

    protected String getSql(HttpServletRequest request) {
        return "SELECT * FROM PRODUCT";
    }

    protected List<String> getValues(ResultSet rs, HttpServletRequest request) throws SQLException {
        List<String> values = new ArrayList<>();
        values.add("<html><body>");
        while (rs.next()) {
            String  name = rs.getString("name");
            int price  = rs.getInt("price");
            values.add(name + "\t" + price + "</br>");
        }
        values.add("</body></html>");
        return values;
    }
}
