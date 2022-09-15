package webserver;

import controller.FrontController;
import http.HttpRequest;
import http.HttpResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;
    private static final FrontController frontController = new FrontController();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpRequest.of(in);
            HttpResponse response = HttpResponse.of(out);

            frontController.process(request, response);
            response.write();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
