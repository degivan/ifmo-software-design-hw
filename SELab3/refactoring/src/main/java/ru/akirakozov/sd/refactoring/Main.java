package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

/**
 * @author akirakozov
 */
public class Main {
    public static void main(String[] args) throws Exception {
        String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID SERIAL PRIMARY KEY NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)";
        new DBConnection().executeQuery(sql);

        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(new DBConnection())), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(new DBConnection())),"/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(new DBConnection())),"/query");

        server.start();
        server.join();
    }
}
