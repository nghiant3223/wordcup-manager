package uni.hcmut.wcmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import uni.hcmut.wcmanager.runners.Tournament;
import uni.hcmut.wcmanager.utils.HibernateUtils;

public class App {
    public static void main(String[] args) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            session.getTransaction().begin();

            Tournament tournament = new Tournament();
            tournament.start();
            tournament.finish();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
}
