package uni.hcmut.wcmanager;

import org.hibernate.SessionFactory;
import uni.hcmut.wcmanager.utils.HibernateUtils;

public class App {
    public static void main(String[] args) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        System.out.println("Hello World!");
    }
}
