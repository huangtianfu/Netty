package thread;

import org.apache.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class HeartThread extends Thread {
    private static Logger logger = Logger.getLogger(HeartThread.class);

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(60000);
                System.out.println("HeartThread");
            } catch (Exception exception) {
                logger.error("HeartThread", exception);
            }
        }
    }
}
