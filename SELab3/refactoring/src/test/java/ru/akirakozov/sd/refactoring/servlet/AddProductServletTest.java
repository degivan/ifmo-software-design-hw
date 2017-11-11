package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.DBConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AddProductServletTest {
    private AddProductServlet servlet;
    private DBConnection dbConnection;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Before
    public void init() {
        dbConnection = mock(DBConnection.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        servlet = new AddProductServlet(dbConnection);
    }

    @Test
    public void testGetProducts() throws SQLException, IOException {
        PrintWriter printWriter = mock(PrintWriter.class);
        when(request.getParameter("name")).thenReturn("test");
        when(request.getParameter("price")).thenReturn("300");
        when(dbConnection.executeQuery("INSERT INTO PRODUCT (NAME, PRICE) VALUES ('test',300)")).thenReturn(null);

        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        verify(response, times(1)).getWriter();
        verify(printWriter, times(1)).println(eq("OK"));
    }
}
