package service;

import dao.base.GenericDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BaseService {
    private static ApplicationContext mAppContext = null;

    public BaseService() {
        if (null == mAppContext) {
            mAppContext = new ClassPathXmlApplicationContext("/spring/mysql.xml");
        }
    }

    protected GenericDao getDaoBean(Class clazz) {
        return (GenericDao) mAppContext.getBean(clazz.getSimpleName(), clazz);
    }
}
