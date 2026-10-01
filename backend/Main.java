package backend;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Main.java
 * ---------
 * This is the entry point (starting point) of the Java backend.
 * It starts a small HTTP server (built into Java, no extra framework needed)
 * and listens for requests coming from the frontend (index.html/script.js).
 *
 * Marathi: Hi file backend server suru karte. Frontend kadun aalele request
 * (POST /generate-design) hya server madhe yetat, ani mag DesignAgent hya
 * request var process karto.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        // Needed so the placeholder-image drawing (java.awt/Graphics2D) works
        // even on machines with no display/monitor attached (e.g. servers).
        System.setProperty("java.awt.headless", "true");

       int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        // int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Route 1: Handles the main "generate design" request from frontend
        server.createContext("/generate-design", new DesignHandler());

        // Route 2: Serves the generated images so the browser can show them
        server.createContext("/generated-images", new StaticImageHandler());

        server.setExecutor(null); // default executor is fine for a student project
        server.start();

        System.out.println("=================================================");
        System.out.println(" AI Design Agent Backend Server Started");
        System.out.println(" URL              : http://localhost:" + port);
        System.out.println(" API Endpoint     : POST http://localhost:" + port + "/generate-design");
        System.out.println(" Images served at : http://localhost:" + port + "/generated-images/<filename>");
        System.out.println("=================================================");
    }

    /**
     * Handles POST /generate-design
     * Reads the JSON body sent by the frontend, passes it to DesignAgent,
     * and sends back the AI-generated result (prompt + image path).
     */
    static class DesignHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Allow the frontend (running from a different origin/port) to call this API
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                sendResponse(exchange, 405, "{\"error\":\"Only POST method is allowed\"}");
                return;
            }

            try {
                // Step 1: Read the raw JSON text sent by the browser
                InputStream is = exchange.getRequestBody();
                String requestBody = new Scanner(is, StandardCharsets.UTF_8)
                        .useDelimiter("\\A").next();

                System.out.println("\n[Received Request] " + requestBody);

                // Step 2: Hand it over to the AI Design Agent (the "brain")
                DesignAgent agent = new DesignAgent();
                String resultJson = agent.processRequest(requestBody);

                // Step 3: Send the result (image path + prompt used) back to frontend
                sendResponse(exchange, 200, resultJson);

            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500,
                        "{\"status\":\"error\",\"error\":\"" + JsonUtil.escape(e.getMessage()) + "\"}");
            }
        }
    }

    /**
     * Serves image files saved inside the "generated-images" folder,
     * so the frontend <img> tag can display them.
     */
    static class StaticImageHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            String path = exchange.getRequestURI().getPath(); // e.g. /generated-images/design_123.png
            File file = new File("." + path);

            if (!file.exists() || file.isDirectory()) {
                sendResponse(exchange, 404, "{\"error\":\"Image not found\"}");
                return;
            }

            exchange.getResponseHeaders().add("Content-Type", "image/png");
            exchange.sendResponseHeaders(200, file.length());
            try (OutputStream os = exchange.getResponseBody();
                 FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[4096];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, count);
                }
            }
        }
    }

    static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
