import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Main {

    private static int port = 8000;

    public static void main(String[] args) throws Exception {
        initializeCache();
        startWebServer();
    }

    // TODO: Review this and implement a nonblocking server
    //       http://www.baeldung.com/jetty-embedded
    private static void startWebServer() throws Exception {
        Server server = new Server(port);
        ServletContextHandler handler = new ServletContextHandler(server, "/v1");
        handler.addServlet(CodeServlet.class, "/code");
        server.start();
    }

    private static void initializeCache() {
        CacheSingleton cache = CacheSingleton.getInstance();
        cache.initialize();
    }
}
