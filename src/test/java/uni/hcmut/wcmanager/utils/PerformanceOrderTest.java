package uni.hcmut.wcmanager.utils;

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
import uni.hcmut.wcmanager.events.YellowCardEvent;
import uni.hcmut.wcmanager.randomizers.EventGenerator;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class PerformanceOrderTest {

    Team TeamHome = new Team();
    Team TeamAway = new Team();
    Team TeamAway2 = new Team();

    List<Player> playerHome = new ArrayList<Player>();
    List<Player> playerAway = new ArrayList<Player>();
    List<Player> playerAway2 = new ArrayList<Player>();

    DbMatch matchDB = new DbMatch();
    DrawableMatch matchDraw;
    DrawableMatch matchDraw2;
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

            Player player3 = new Player();
            String fullNameAway2 = "TeamAway2" + Integer.toString(index);
            player3.setFullname(fullNameAway2);
            player3.setGoalCount(index%3);
            playerAway2.add(player3);
        }

        TeamHome.setPlayers(playerHome);
        TeamAway.setPlayers(playerAway);
        TeamAway2.setPlayers(playerAway2);

        group.addTeam(TeamHome);
        group.addTeam(TeamAway);
        group.addTeam(TeamAway2);

        matchKnockout = new KnockoutMatch(TeamHome, TeamAway);
        matchDraw = new DrawableMatch(TeamHome, TeamAway);
        matchDraw2 = new DrawableMatch(TeamHome, TeamAway2);
        matchDraw.setRoundName(RoundName.GROUP_STAGE);
        matchDraw2.setRoundName(RoundName.GROUP_STAGE);
    }

    @Test
    public void test_scoreDiff(){
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

        PerformanceOrder performanceOrder = new PerformanceOrder();
        int result = performanceOrder.compare(teamPerformancesAway, teamPerformancesHome);
        Assert.assertEquals(3, result);
    }

    @Test
    public void test_goalDiff(){
        TeamPerformance teamPerformancesHome = new TeamPerformance(TeamHome, group);
        TeamPerformance teamPerformancesAway = new TeamPerformance(TeamAway, group);
        TeamPerformance teamPerformancesAway2 = new TeamPerformance(TeamAway2, group);

        TeamInMatch homeTeam = matchDraw.getHomeTeam();
        TeamInMatch aWayTeam = matchDraw.getAwayTeam();
        TeamInMatch homeTeam2 = matchDraw2.getHomeTeam();
        TeamInMatch aWayTeam2 = matchDraw2.getAwayTeam();

        Event homeGoal = new GoalEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 10);
        Event homeGoal2 = new GoalEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 20);

        Event aWayTeamGoal = new GoalEvent(matchDraw, aWayTeam.getPlayingPlayers().get(0), 30);
        Event aWayTeamGoal2 = new GoalEvent(matchDraw, aWayTeam.getPlayingPlayers().get(0), 40);

        Event home2Goal = new GoalEvent(matchDraw2, homeTeam2.getPlayingPlayers().get(0), 50);
        Event aWayTeamGoal2Goal2 = new GoalEvent(matchDraw2, aWayTeam2.getPlayingPlayers().get(0), 60);

        List<Event> events = new ArrayList<>();
        events.add(homeGoal);
        events.add(homeGoal2);
        events.add(aWayTeamGoal);
        events.add(aWayTeamGoal2);

        List<Event> events2 = new ArrayList<>();
        events2.add(home2Goal);
        events2.add(aWayTeamGoal2Goal2);

        EventGenerator generator = new EventGenerator(events);
        matchDraw.start(generator);

        EventGenerator generator2 = new EventGenerator(events2);
        matchDraw2.start(generator2);

        teamPerformancesHome.update(matchDraw);
        teamPerformancesAway.update(matchDraw);

        teamPerformancesHome.update(matchDraw2);
        teamPerformancesAway2.update(matchDraw2);

        PerformanceOrder performanceOrder = new PerformanceOrder();
        int result = performanceOrder.compare(teamPerformancesAway, teamPerformancesHome);
        Assert.assertEquals(1, result);
    }

    @Test
    public void test_cardDiff(){
        TeamPerformance teamPerformancesHome = new TeamPerformance(TeamHome, group);
        TeamPerformance teamPerformancesAway = new TeamPerformance(TeamAway, group);
        TeamPerformance teamPerformancesAway2 = new TeamPerformance(TeamAway2, group);

        TeamInMatch homeTeam = matchDraw.getHomeTeam();
        TeamInMatch aWayTeam = matchDraw.getAwayTeam();
        TeamInMatch homeTeam2 = matchDraw2.getHomeTeam();
        TeamInMatch aWayTeam2 = matchDraw2.getAwayTeam();

        Event homeGoal = new GoalEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 10);
        Event yellowCard = new YellowCardEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 20);
        Event aWayTeamGoal = new GoalEvent(matchDraw, aWayTeam.getPlayingPlayers().get(0), 30);

        Event home2Goal = new GoalEvent(matchDraw2, homeTeam2.getPlayingPlayers().get(0), 50);
        Event aWayTeamGoal2Goal2 = new GoalEvent(matchDraw2, aWayTeam2.getPlayingPlayers().get(0), 60);

        List<Event> events = new ArrayList<>();
        events.add(homeGoal);
        events.add(yellowCard);
        events.add(aWayTeamGoal);

        List<Event> events2 = new ArrayList<>();
        events2.add(home2Goal);
        events2.add(aWayTeamGoal2Goal2);

        EventGenerator generator = new EventGenerator(events);
        matchDraw.start(generator);

        EventGenerator generator2 = new EventGenerator(events2);
        matchDraw2.start(generator2);

        teamPerformancesHome.update(matchDraw);
        teamPerformancesAway.update(matchDraw);

        teamPerformancesHome.update(matchDraw2);
        teamPerformancesAway2.update(matchDraw2);

        PerformanceOrder performanceOrder = new PerformanceOrder();
        int result = performanceOrder.compare(teamPerformancesAway, teamPerformancesHome);
        Assert.assertEquals(1, result);
    }

    @Test
    public void test_not_twoTeamInVersusMatch(){
        TeamPerformance teamPerformancesHome = new TeamPerformance(TeamHome, group);
        TeamPerformance teamPerformancesAway = new TeamPerformance(TeamAway, group);
        TeamPerformance teamPerformancesAway2 = new TeamPerformance(TeamAway2, group);

        TeamInMatch homeTeam = matchDraw.getHomeTeam();
        TeamInMatch aWayTeam = matchDraw.getAwayTeam();
        TeamInMatch homeTeam2 = matchDraw2.getHomeTeam();
        TeamInMatch aWayTeam2 = matchDraw2.getAwayTeam();

        Event homeGoal = new GoalEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 10);
        Event yellowCard = new YellowCardEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 20);
        Event aWayTeamGoal = new GoalEvent(matchDraw, aWayTeam.getPlayingPlayers().get(0), 30);

        Event home2Goal = new GoalEvent(matchDraw2, homeTeam2.getPlayingPlayers().get(0), 50);
        Event aWayTeamGoal2Goal2 = new GoalEvent(matchDraw2, aWayTeam2.getPlayingPlayers().get(0), 60);

        List<Event> events = new ArrayList<>();
        events.add(homeGoal);
        events.add(yellowCard);
        events.add(aWayTeamGoal);

        List<Event> events2 = new ArrayList<>();
        events2.add(home2Goal);
        events2.add(aWayTeamGoal2Goal2);

        EventGenerator generator = new EventGenerator(events);
        matchDraw.start(generator);

        EventGenerator generator2 = new EventGenerator(events2);
        matchDraw2.start(generator2);

        teamPerformancesHome.update(matchDraw);
        teamPerformancesAway.update(matchDraw);

        teamPerformancesHome.update(matchDraw2);
        teamPerformancesAway2.update(matchDraw2);

        PerformanceOrder performanceOrder = new PerformanceOrder();
        int result = performanceOrder.compare(teamPerformancesAway, teamPerformancesAway2);
        Assert.assertEquals(0, result);
    }

    @Test
    public void test_twoTeamInVersusMatch(){
        TeamPerformance teamPerformancesHome = new TeamPerformance(TeamHome, group);
        TeamPerformance teamPerformancesAway = new TeamPerformance(TeamAway, group);
        TeamPerformance teamPerformancesAway2 = new TeamPerformance(TeamAway2, group);

        TeamInMatch homeTeam = matchDraw.getHomeTeam();
        TeamInMatch aWayTeam = matchDraw.getAwayTeam();
        TeamInMatch homeTeam2 = matchDraw2.getHomeTeam();
        TeamInMatch aWayTeam2 = matchDraw2.getAwayTeam();

        Event aWayTeamGoal = new GoalEvent(matchDraw, aWayTeam.getPlayingPlayers().get(0), 30);

        Event home2Goal = new GoalEvent(matchDraw2, homeTeam2.getPlayingPlayers().get(0), 50);

        List<Event> events = new ArrayList<>();
        events.add(aWayTeamGoal);

        List<Event> events2 = new ArrayList<>();
        events2.add(home2Goal);

        EventGenerator generator = new EventGenerator(events);
        matchDraw.start(generator);

        EventGenerator generator2 = new EventGenerator(events2);
        matchDraw2.start(generator2);

        teamPerformancesHome.update(matchDraw);
        teamPerformancesAway.update(matchDraw);

        teamPerformancesHome.update(matchDraw2);
        teamPerformancesAway2.update(matchDraw2);

        PerformanceOrder performanceOrder = new PerformanceOrder();
        int result = performanceOrder.compare(teamPerformancesAway, teamPerformancesHome);
        Assert.assertEquals(-1, result);
    }

    @Test
    public void test_random_team(){
        TeamPerformance teamPerformancesHome = new TeamPerformance(TeamHome, group);
        TeamPerformance teamPerformancesAway = new TeamPerformance(TeamAway, group);
        TeamPerformance teamPerformancesAway2 = new TeamPerformance(TeamAway2, group);

        TeamInMatch homeTeam = matchDraw.getHomeTeam();
        TeamInMatch aWayTeam = matchDraw.getAwayTeam();
        TeamInMatch homeTeam2 = matchDraw2.getHomeTeam();
        TeamInMatch aWayTeam2 = matchDraw2.getAwayTeam();

        Event homeGoal = new GoalEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 10);
        Event aWayTeamGoal = new GoalEvent(matchDraw, aWayTeam.getPlayingPlayers().get(0), 30);

        Event home2Goal = new GoalEvent(matchDraw2, homeTeam2.getPlayingPlayers().get(0), 50);
        Event aWayTeamGoal2Goal2 = new GoalEvent(matchDraw2, aWayTeam2.getPlayingPlayers().get(0), 60);

        List<Event> events = new ArrayList<>();
        events.add(homeGoal);
        events.add(aWayTeamGoal);

        List<Event> events2 = new ArrayList<>();
        events2.add(home2Goal);
        events2.add(aWayTeamGoal2Goal2);

        EventGenerator generator = new EventGenerator(events);
        matchDraw.start(generator);

        EventGenerator generator2 = new EventGenerator(events2);
        matchDraw2.start(generator2);

        teamPerformancesHome.update(matchDraw);
        teamPerformancesAway.update(matchDraw);

        teamPerformancesHome.update(matchDraw2);
        teamPerformancesAway2.update(matchDraw2);

        PerformanceOrder performanceOrder = new PerformanceOrder();
        int result = performanceOrder.compare(teamPerformancesAway, teamPerformancesHome);
        Assert.assertEquals(1, abs(result));
    }

    @After
    public void finish(){
        session.getTransaction().rollback();
    }
}
