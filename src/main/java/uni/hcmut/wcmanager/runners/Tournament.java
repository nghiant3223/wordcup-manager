package uni.hcmut.wcmanager.runners;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import uni.hcmut.wcmanager.entities.*;
import uni.hcmut.wcmanager.randomizers.GroupStageDraw;
import uni.hcmut.wcmanager.utils.HibernateUtils;

import java.util.List;
import java.util.Map;

public class Tournament {
    private List<Team> teams;

    private Team champion;
    private Team runnerUp;
    private Team[] thirdPlaces;

    public Tournament() {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        String hql = "FROM Team";
        Query query = session.createQuery(hql);

        teams = (List<Team>) query.list();
    }

    public void start() {
        Round currentRound;

        List<Group> groups = GroupStageDraw.draw(teams);
        currentRound = new GroupStage(groups);
        currentRound.run();
        Map<Integer, Team[]> groupStageResult = currentRound.getResult();
    }

    public void finish() {
        // TODO: Persist match, team performance in group, player into database
    }

    private Player getTopScorer() {
        return null;
    }
}
