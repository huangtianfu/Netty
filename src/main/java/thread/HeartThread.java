package thread;

import org.apache.log4j.Logger;

public class HeartThread extends Thread {
    private static Logger logger = Logger.getLogger(HeartThread.class);
    private static HeartThread instance = null;

    public static HeartThread getInstance() {
        if (instance == null) {
            instance = new HeartThread();
        }

        return instance;
    }

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
