import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import server.Server;

public class Bootstrap {
    public static void main(String[] args) throws Exception {
        PropertyConfigurator.configure(Bootstrap.class.getResourceAsStream("/log4j.properties"));
        Logger logger = Logger.getLogger(Bootstrap.class);
        logger.info("Server Start!");

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/applicationContext.xml");
        Server server = applicationContext.getBean(Server.class);
        server.test();
        server.start();
    }
}
