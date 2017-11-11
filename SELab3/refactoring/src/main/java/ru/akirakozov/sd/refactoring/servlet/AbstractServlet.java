package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.util.DBUtil;
import ru.akirakozov.sd.refactoring.util.ResponseUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String sql = getSql(request);
            if (sql != null) {
                ResultSet rs = DBUtil.executeQuery(sql);
                List<String> values = getValues(rs, request);
                ResponseUtil.fillResponse(response, values);
                if (rs != null) {
                    rs.getStatement().close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract List<String> getValues(ResultSet rs, HttpServletRequest request) throws SQLException;

    protected abstract String getSql(HttpServletRequest request);
}
