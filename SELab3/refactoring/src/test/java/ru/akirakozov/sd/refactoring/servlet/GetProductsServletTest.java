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

import static org.mockito.Mockito.*;

public class GetProductsServletTest {
    private GetProductsServlet servlet;
    private DBConnection dbConnection;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Before
    public void init() {
        dbConnection = mock(DBConnection.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        servlet = new GetProductsServlet(dbConnection);
    }
    @Test
    public void testGetProducts() throws SQLException, IOException {
        ResultSet rs = mock(ResultSet.class);
        PrintWriter printWriter = mock(PrintWriter.class);
        Statement statement = mock(Statement.class);

        when(dbConnection.executeQuery("SELECT * FROM PRODUCT")).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getString("name")).thenReturn("iphone");
        when(rs.getInt("price")).thenReturn(300);
        when(rs.getStatement()).thenReturn(statement);

        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        verify(response, times(3)).getWriter();
        verify(printWriter, times(3)).println(anyString());
    }
}
