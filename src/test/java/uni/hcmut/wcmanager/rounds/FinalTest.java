package uni.hcmut.wcmanager.rounds;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uni.hcmut.wcmanager.constants.GameRule;
import uni.hcmut.wcmanager.entities.*;
import uni.hcmut.wcmanager.enums.GroupName;
import uni.hcmut.wcmanager.enums.RoundName;
import uni.hcmut.wcmanager.events.Event;
import uni.hcmut.wcmanager.events.GoalEvent;
import uni.hcmut.wcmanager.randomizers.EventGenerator;
import uni.hcmut.wcmanager.randomizers.PenaltyShootoutGenerator;
import uni.hcmut.wcmanager.utils.HibernateUtils;
import uni.hcmut.wcmanager.utils.RoundUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FinalTest {
    Team TeamHome = new Team();
    Team TeamAway = new Team();

    List<Player> playerHome = new ArrayList<Player>();
    List<Player> playerAway = new ArrayList<Player>();

    KnockoutMatch finalMatch;
    Group group = new Group(GroupName.A);

    Session session;
    SessionFactory factory;

    @Before
    public void init(){
        factory = HibernateUtils.getSessionFactory();
        session = factory.getCurrentSession();
        session.getTransaction().begin();

        for(int index = 0; index < GameRule.TEAM_PLAYER_COUNT; index ++){
            Player player = new Player();
            String fullName = "TeamHome" + Integer.toString(index);
            player.setFullname(fullName);
            player.setGoalCount(index%2);
            playerHome.add(player);

            Player player2 = new Player();
            String fullNameAway = "TeamAway" + Integer.toString(index);
            player2.setFullname(fullNameAway);
            player2.setGoalCount(index%3);
            playerAway.add(player2);

        }

        TeamHome.setPlayers(playerHome);
        TeamAway.setPlayers(playerAway);

        group.addTeam(TeamHome);
        group.addTeam(TeamAway);

        finalMatch = new KnockoutMatch(TeamHome, TeamAway);
    }

    @Test
    public void test_getResult(){

        TeamInMatch homeTeam = finalMatch.getHomeTeam();
        TeamInMatch aWayTeam = finalMatch.getAwayTeam();

        Event homeGoal = new GoalEvent(finalMatch, homeTeam.getPlayingPlayers().get(0), 10);
        Event homeGoal2 = new GoalEvent(finalMatch, homeTeam.getPlayingPlayers().get(0), 20);

        Event aWayTeamGoal = new GoalEvent(finalMatch, aWayTeam.getPlayingPlayers().get(0), 30);
        Event aWayTeamGoal2 = new GoalEvent(finalMatch, aWayTeam.getPlayingPlayers().get(0), 40);


        List<Event> events = new ArrayList<>();
        events.add(homeGoal);
        events.add(homeGoal2);
        events.add(aWayTeamGoal);
        events.add(aWayTeamGoal2);


        EventGenerator generator = new EventGenerator(events);
        boolean[][] shootout = {{true, false}, {true, false}, {true, true}, {true, true}};
        PenaltyShootoutGenerator penalty = new PenaltyShootoutGenerator(shootout);
        finalMatch.start(generator, penalty);

        Final matchFinal = new Final(finalMatch);
        Map<Integer, Team[]> result =  matchFinal.getResult();
        Team[] champion =  result.get(0);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(TeamHome, champion[0]);
    }

    @Test(expected = Exception.class)
    public void test_getResult_with_match_null(){

        Match finalEmpty = null;
        Final matchFinal = new Final(finalEmpty);

        Map<Integer, Team[]> result =  matchFinal.getResult();
        Team[] champion =  result.get(0);
    }

    @Test(expected = Exception.class)
    public void test_getResult_with_match_empty(){

        Final matchFinal = new Final(finalMatch);

        Map<Integer, Team[]> result =  matchFinal.getResult();
        Team[] champion =  result.get(0);
    }

    @Test
    public void test_getName(){
        Final matchFinal = new Final(finalMatch);
        Assert.assertEquals(RoundName.FINAL, matchFinal.getName());
    }

    @After
    public void finish(){
        session.getTransaction().rollback();
    }
}
