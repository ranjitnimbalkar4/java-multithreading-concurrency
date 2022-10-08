package performance.latency.and.throught.http.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HttpServerStart {
    private static final String BOOK_PATH = "/war_and_peace.txt";
    private static final int NUMBER_OF_WORKERS = 10;

    public static void main(String[] args) throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(HttpServerStart.class.getResource(BOOK_PATH)).toURI();
        String bookAsString = new String(Files.readAllBytes(Paths.get(uri)));
        startServer(bookAsString);
    }

    private static void startServer(String bookAsString) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/search", new WordCountHandler(bookAsString));

        Executor executor = Executors.newFixedThreadPool(NUMBER_OF_WORKERS);
        server.setExecutor(executor);

        server.start();
        System.out.println("Server Started at Port : 8080");
    }
}
