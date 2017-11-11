package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.DBConnection;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractServlet {
    private Map<String, String> queries = new HashMap<>();
    private Map<String, ValueRetriever> retrievers = new HashMap<>();

    {
        queries.put("max", "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
        queries.put("min", "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
        queries.put("sum", "SELECT SUM(price) FROM PRODUCT");
        queries.put("count", "SELECT COUNT(*) FROM PRODUCT");
        ValueRetriever maxRetriever = new NamePriceRetriever();
        ValueRetriever minRetriever = new NamePriceRetriever();
        ValueRetriever sumRetriever = new SingleValueRetriever();
        ValueRetriever countRetriever = new SingleValueRetriever();
        maxRetriever.setName("<h1>Product with max price: </h1>");
        minRetriever.setName("<h1>Product with min price: </h1>");
        sumRetriever.setName("Summary price: ");
        countRetriever.setName("Number of products: ");
        retrievers.put("max", maxRetriever);
        retrievers.put("min", minRetriever);
        retrievers.put("sum", sumRetriever);
        retrievers.put("count", countRetriever);
    }

    public QueryServlet(DBConnection dbConnection) {
        super(dbConnection);
    }


    @Override
    protected List<String> getValues(ResultSet rs, HttpServletRequest request) throws SQLException {
        String command = request.getParameter("command");
        ValueRetriever retriever = retrievers.get(command);
        if (retriever == null) {
            return Collections.singletonList("Unknown command: " + command);
        } else {
            return retriever.apply(rs);
        }
    }

    @Override
    protected String getSql(HttpServletRequest request) {
        return queries.get(request.getParameter("command"));
    }

    private static class NamePriceRetriever extends ValueRetriever {
        @Override
        protected List<String> getData(ResultSet rs) throws SQLException {
            List<String> result = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                result.add(name + "\t" + price + "</br>");
            }
            return result;
        }
    }

    private class SingleValueRetriever extends ValueRetriever {
        @Override
        protected List<String> getData(ResultSet rs) throws SQLException {
            if (rs.next()) {
                return Collections.singletonList(Integer.toString(rs.getInt(1)));
            }
            return Collections.EMPTY_LIST;
        }
    }
}
