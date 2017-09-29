package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.LoginServlet;

public class Main {

    public static void main(String[] args) throws Exception {

        /*Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(LoginServlet.class, "/");

        try {
            cfg.setDirectoryForTemplateLoading(new File("C:\\Users\\Admin\\Desktop\\WebServer\\src\\main\\java\\main"));
            Template helloTemplate = cfg.getTemplate("hello.html");
            StringWriter writer = new StringWriter();
            Map<String, Object> helloMap = new HashMap();
            helloMap.put("name", "Yeeeahhhh!");

            helloTemplate.process(helloMap, writer);

            System.out.println(writer);

        } catch (TemplateException e) {
            e.printStackTrace();
        }*/

        LoginServlet loginServlet = new LoginServlet();

        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(context);
        context.addServlet(new ServletHolder(loginServlet), "/");

        server.start();
        server.join();
    }
}
