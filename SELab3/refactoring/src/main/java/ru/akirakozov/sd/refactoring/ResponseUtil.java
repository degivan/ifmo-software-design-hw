package ru.akirakozov.sd.refactoring;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public final class ResponseUtil {
    private ResponseUtil() {

    }

    public static void fillResponse(HttpServletResponse response, List<String> valuesRetriever) throws IOException {
        for(String value: valuesRetriever) {
            response.getWriter().println(value);
        }
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
