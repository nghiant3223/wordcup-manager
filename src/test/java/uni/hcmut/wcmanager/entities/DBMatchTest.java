package uni.hcmut.wcmanager.entities;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uni.hcmut.wcmanager.constants.GameRule;
import uni.hcmut.wcmanager.enums.GroupName;
import uni.hcmut.wcmanager.enums.RoundName;
import uni.hcmut.wcmanager.events.Event;
import uni.hcmut.wcmanager.events.GoalEvent;
import uni.hcmut.wcmanager.events.OwnGoalEvent;
import uni.hcmut.wcmanager.randomizers.EventGenerator;
import uni.hcmut.wcmanager.randomizers.PenaltyShootoutGenerator;
import uni.hcmut.wcmanager.utils.HibernateUtils;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class DBMatchTest {
    Team TeamHome = new Team();
    Team TeamAway = new Team();
    List<Player> playerHome = new ArrayList<Player>();
    List<Player> playerAway = new ArrayList<Player>();
    DbMatch matchDB = new DbMatch();
    DrawableMatch matchDraw;
    KnockoutMatch matchKnockout;
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

        matchKnockout = new KnockoutMatch(TeamHome, TeamAway);
        matchDraw = new DrawableMatch(TeamHome, TeamAway);
        matchDraw.setRoundName(RoundName.GROUP_STAGE);

    }

    @Test
    public void test_getHomeTeam(){
        matchDB.setHomeTeam(TeamHome);
        Team actual = matchDB.getHomeTeam();
        Assert.assertEquals(TeamHome, actual);
    }

    @Test
    public void test_Wrong_getHomeTeam(){
        matchDB.setHomeTeam(TeamHome);
        Team actual = matchDB.getHomeTeam();
        Assert.assertNotEquals(TeamAway, actual);
    }

    @Test
    public void test_getHomeAway(){
        matchDB.setAwayTeam(TeamAway);
        Team actual = matchDB.getAwayTeam();
        Assert.assertEquals(TeamAway, actual);
    }

    @Test
    public void test_Wrong_getHomeAway(){
        matchDB.setAwayTeam(TeamAway);
        Team actual = matchDB.getAwayTeam();
        Assert.assertNotEquals(TeamHome, actual);
    }

    @Test
    public void test_getWinnerTeam(){
        matchDB.setWinnerTeam(TeamAway);
        Team actual = matchDB.getWinnerTeam();
        Assert.assertEquals(TeamAway, actual);
    }

    @Test
    public void test_Wrong_getWinnerTeam(){
        matchDB.setWinnerTeam(TeamAway);
        Team actual = matchDB.getWinnerTeam();
        Assert.assertNotEquals(TeamHome, actual);
    }

    @Test
    public void test_getRoundID(){
        matchDB.setRoundId(1);
        int actual = matchDB.getRoundId();
        Assert.assertEquals(1, actual);
    }

    @Test
    public void test_Wrong_getRoundID(){
        matchDB.setRoundId(1);
        int actual = matchDB.getRoundId();
        Assert.assertNotEquals(2, actual);
    }

    @Test
    public void test_getHomeResult(){
        matchDB.setHomeResult(1);
        int actual = matchDB.getHomeResult();
        Assert.assertEquals(1, actual);
    }

    @Test
    public void test_getAwayResult(){
        matchDB.setAwayResult(1);
        int actual = matchDB.getAwayResult();
        Assert.assertEquals(1, actual);
    }

    @Test
    public void test_getAwayPenalty(){
        matchDB.setAwayPenalty(3);
        int actual = matchDB.getAwayPenalty();
        Assert.assertEquals(3, actual);
    }

    @Test
    public void test_getHomePenalty(){
        matchDB.setHomePenalty(3);
        int actual = matchDB.getHomePenalty();
        Assert.assertEquals(3, actual);
    }

    @Test
    public void test_HomeResult_DBMatch(){
        TeamPerformance teamPerformancesHome = new TeamPerformance(TeamHome, group);
        TeamPerformance teamPerformancesAway = new TeamPerformance(TeamAway, group);

        TeamInMatch homeTeam = matchDraw.getHomeTeam();
        TeamInMatch aWayTeam = matchDraw.getAwayTeam();

        Event goal = new GoalEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 10);
        List<Event> events = new ArrayList<>();
        events.add(goal);

        EventGenerator generator = new EventGenerator(events);
        matchDraw.start(generator);

        teamPerformancesHome.update(matchDraw);
        teamPerformancesAway.update(matchDraw);

        matchDB = DbMatch.fromMatch(matchDraw);
        int result = matchDB.getHomeResult();
        Assert.assertEquals(1,result);
    }

    @Test
    public void test_AwayResult_DBMatch(){
        TeamPerformance teamPerformancesHome = new TeamPerformance(TeamHome, group);
        TeamPerformance teamPerformancesAway = new TeamPerformance(TeamAway, group);

        TeamInMatch homeTeam = matchDraw.getHomeTeam();
        TeamInMatch awayTeam = matchDraw.getAwayTeam();

        Event goal = new GoalEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 10);
        Event awayGoal = new GoalEvent(matchDraw, awayTeam.getPlayingPlayers().get(0), 20);
        Event ownAwayGoal = new OwnGoalEvent(matchDraw, awayTeam.getPlayingPlayers().get(0), 20);

        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(awayGoal);
        events.add(ownAwayGoal);

        EventGenerator generator = new EventGenerator(events);
        matchDraw.start(generator);

        teamPerformancesHome.update(matchDraw);
        teamPerformancesAway.update(matchDraw);

        matchDB = DbMatch.fromMatch(matchDraw);
        int result = matchDB.getAwayResult();
        Assert.assertEquals(1,result);

    }

    @Test(expected = InvalidParameterException.class)
    public void test_Penalty_DBMatch_With_Event_Null(){
        TeamPerformance teamPerformancesHome = new TeamPerformance(TeamHome, group);
        TeamPerformance teamPerformancesAway = new TeamPerformance(TeamAway, group);

        EventGenerator generator = null;

        boolean[][] shootout = {{true, false, true, true, true},{true, true, true, true, true}};
        PenaltyShootoutGenerator penalty = new PenaltyShootoutGenerator(shootout);
        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);

        teamPerformancesHome.update(matchKnockout);
        teamPerformancesAway.update(matchKnockout);

        matchDB = DbMatch.fromMatch(matchKnockout);
        int result = matchDB.getHomePenalty();
    }

    @Test
    public void test_Penalty_DBMatch(){
        TeamPerformance teamPerformancesHome = new TeamPerformance(TeamHome, group);
        TeamPerformance teamPerformancesAway = new TeamPerformance(TeamAway, group);

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 10);
        Event awayGoal = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 20);

        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(awayGoal);

        EventGenerator generator = new EventGenerator(events);

        boolean[][] shootout = {{true, true}, {true, true}, {true, true}, {true, true}, {true, true}, {true, false}};
        PenaltyShootoutGenerator penalty = new PenaltyShootoutGenerator(shootout);

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);

        teamPerformancesHome.update(matchKnockout);
        teamPerformancesAway.update(matchKnockout);

        matchDB = DbMatch.fromMatch(matchKnockout);
        int result = matchDB.getHomePenalty();
        Assert.assertEquals(4, result);
    }

    @After
    public void finish(){
        session.getTransaction().rollback();
    }
}
