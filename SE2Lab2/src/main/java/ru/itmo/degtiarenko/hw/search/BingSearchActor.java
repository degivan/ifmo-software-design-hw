package ru.itmo.degtiarenko.hw.search;

import java.net.*;
import java.util.*;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;

/*
 * Gson: https://github.com/google/gson
 * Maven info:
 *     groupId: com.google.code.gson
 *     artifactId: gson
 *     version: 2.8.1
 *
 * Once you have compiled or downloaded gson-2.8.1.jar, assuming you have placed it in the
 * same folder as this file (BingWebSearch.java), you can compile and run this program at
 * the command line as follows.
 *
 * javac BingWebSearch.java -classpath .;gson-2.8.1.jar -encoding UTF-8
 * java -cp .;gson-2.8.1.jar BingWebSearch
 */

import akka.actor.AbstractActor;
import akka.actor.ActorRef;

public class BingSearchActor extends AbstractActor {
    private static final String API_KEY = "78e2dae32dac449e952d11a3eba04f0c";
    private static final String HOST = "https://api.cognitive.microsoft.com";
    private static final String PATH = "/bing/v7.0/search";

    public Receive createReceive() {
        return receiveBuilder().match(WebRequest.class, this::processWebRequest)
                .matchAny(this::unhandled).build();
    }

    private void processWebRequest(WebRequest request) throws Exception {
        System.out.println("processWebRequest");
        ActorRef master = getSender();

        SearchResults searchResults = searchWeb(request.getRequest());

        master.tell(WebResponse.from(searchResults), getSelf());
        context().stop(self());
    }

    public static SearchResults searchWeb(String searchQuery) throws Exception {
        // construct URL of search request (endpoint + query string)
        URL url = new URL(HOST + PATH + "?q=" + URLEncoder.encode(searchQuery, "UTF-8"));
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestProperty("Ocp-Apim-Subscription-Key", API_KEY);

        // receive JSON body
        InputStream stream = connection.getInputStream();
        String response = new Scanner(stream).useDelimiter("\\A").next();

        // construct result object for return
        SearchResults results = new SearchResults(new HashMap<>(), response);

        // extract Bing-related HTTP headers
        Map<String, List<String>> headers = connection.getHeaderFields();
        for (String header : headers.keySet()) {
            if (header == null) continue;      // may have null key
            if (header.startsWith("BingAPIs-") || header.startsWith("X-MSEdge-")) {
                results.relevantHeaders.put(header, headers.get(header).get(0));
            }
        }

        stream.close();
        System.out.println("searchWebOver");
        return results;
    }

    public static class SearchResults {
        HashMap<String, String> relevantHeaders;
        String jsonResponse;

        SearchResults(HashMap<String, String> headers, String json) {
            relevantHeaders = headers;
            jsonResponse = json;
        }
    }
}
