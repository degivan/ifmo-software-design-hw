package ru.akirakozov.sd.refactoring;

import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class ResponseUtilTest {
    @Test
    public void testStatus() throws IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);

        ResponseUtil.fillResponse(response, new ArrayList<>());

        verify(response, times(0)).getWriter();
        verify(response, times(1)).setContentType("text/html");
        verify(response, times(1)).setStatus(HttpServletResponse.SC_OK);
    }
}
