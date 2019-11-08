package uni.hcmut.wcmanager.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import uni.hcmut.wcmanager.entities.DbMatch;
import uni.hcmut.wcmanager.entities.Match;

public class DbUtils {
    public static void persistMatch(Match match) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        DbMatch dbMatch = DbMatch.fromMatch(match);
        session.persist(dbMatch);
    }
}
