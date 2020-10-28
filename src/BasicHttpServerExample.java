import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BasicHttpServerExample {
    private static String list = "";

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
        HttpContext context = server.createContext("/local");
        HttpContext context2 = server.createContext("/");
        context.setHandler(BasicHttpServerExample::handleRequest);
        context2.setHandler(BasicHttpServerExample::handleRequest2);
        server.start();
        System.out.println("\n" + "Successful!");
    }
    private static void handleRequest(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        if(exchange.getRequestMethod().equals("POST")) {
            InputStream stream = exchange.getRequestBody();
            list = new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.joining("\n"));
            System.out.println("\n" + "Received: ");
            File file = new File("todo.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            try {
                bw.write(list);
                bw.flush();
                bw.close();
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                br.close();
            } catch(IOException e){
                e.printStackTrace();
            } finally {
                bw.close();
            }
            System.out.println("List : " + list);
        }
        String response = "List : " + list;
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    private static void handleRequest2(HttpExchange exchange2) throws IOException {
        String response2 = "Hi there! Go to http://localhost:8500/local";
        exchange2.sendResponseHeaders(200, response2.getBytes().length);
        OutputStream os = exchange2.getResponseBody();
        os.write(response2.getBytes());
        os.close();
    }
}
