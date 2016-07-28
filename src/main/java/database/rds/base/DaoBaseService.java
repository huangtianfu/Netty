package database.rds.base;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DaoBaseService {
    private static ApplicationContext mAppContext = null;

    public DaoBaseService() {
        if (null == mAppContext) {
            mAppContext = new ClassPathXmlApplicationContext("/spring/mysql.xml");
        }
    }

    protected GenericDao getDaoBean(Class clazz) {
        return (GenericDao) mAppContext.getBean(clazz.getSimpleName(), clazz);
    }
}
