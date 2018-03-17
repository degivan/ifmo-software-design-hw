package ru.itmo.degtiarenko.hw.search.engines;

import java.net.*;
import java.util.*;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;

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

        master.tell(WebResponse.fromBing(searchResults), getSelf());
        context().stop(self());
    }

    private static SearchResults searchWeb(String searchQuery) throws Exception {
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
