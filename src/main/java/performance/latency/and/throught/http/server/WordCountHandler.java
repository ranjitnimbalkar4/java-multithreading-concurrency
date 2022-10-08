package performance.latency.and.throught.http.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class WordCountHandler implements HttpHandler {

    private final String book;

    public WordCountHandler(String bookAsString) {
        this.book = bookAsString;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String queryParameter = httpExchange.getRequestURI().getQuery();

        String[] keyValue = queryParameter.split("=");
        String action = keyValue[0];
        String searchWord = keyValue[1];

        if(!action.equals("word")){
            httpExchange.sendResponseHeaders(400, 0);
            return;
        }
        System.out.println("Searching : "+searchWord);
        long count = countWord(searchWord);

        byte[] response = Long.toString(count).getBytes(StandardCharsets.UTF_8);

        httpExchange.sendResponseHeaders(200, response.length);
        OutputStream responseBody = httpExchange.getResponseBody();
        responseBody.write(response);
        responseBody.close();
    }

    private long countWord(String searchWord) {
            long count = 0;
            int index = 0;

            while(index >= 0){
                index = book.indexOf(searchWord, index);
                if(index >= 0){
                    count++;
                    index++;
                }
            }
            return  count;
    }
}
