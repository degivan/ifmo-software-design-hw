package ru.itmo.degtiarenko.hw.search.engines;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;

public class WebResponseTest {
    @Test
    public void testFromGoogle() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("mgmt_google.html").getFile());
        Document document = Jsoup.parse(file, "UTF-8");
        WebResponse response = WebResponse.fromGoogle(document);
        assertTrue(response.getResults().size() == 5);
    }

    @Test
    public void testFromDDG() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("mgmt_ddg.html").getFile());
        Document document = Jsoup.parse(file, "UTF-8");
        WebResponse response = WebResponse.fromDDG(document);
        assertTrue(response.getResults().size() == 5);
    }
}
