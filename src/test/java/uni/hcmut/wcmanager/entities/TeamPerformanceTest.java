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
import uni.hcmut.wcmanager.events.RedCardEvent;
import uni.hcmut.wcmanager.events.YellowCardEvent;
import uni.hcmut.wcmanager.randomizers.EventGenerator;
import uni.hcmut.wcmanager.randomizers.PenaltyShootoutGenerator;
import uni.hcmut.wcmanager.utils.HibernateUtils;

import java.util.ArrayList;
import java.util.List;

public class TeamPerformanceTest {

    Group group = new Group(GroupName.A);
    Team TeamHome = new Team();
    Team TeamAway = new Team();
    List<Player> playerHome = new ArrayList<Player>();
    List<Player> playerAway = new ArrayList<Player>();
    DrawableMatch match; // cho nay` tao kieu DrawableMatch de tét, k can` them method kia vo Match dau
    Session session;
    SessionFactory factory;

    @Before
    public void init() {
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

        match = new DrawableMatch(TeamHome, TeamAway);
        match.setRoundName(RoundName.GROUP_STAGE);
    }

    @Test
    public void Test_getTeam(){
        TeamPerformance teamPerformances = new TeamPerformance(TeamHome, group);
        Assert.assertEquals(TeamHome, teamPerformances.getTeam());
    }

    @Test
    public void Test_getWrongTeam(){
        TeamPerformance teamPerformances = new TeamPerformance(TeamHome, group);
        Assert.assertNotEquals(TeamAway, teamPerformances.getTeam());
    }

    @Test
    public void Test_Update_Yellowcard_TeamHome(){
        TeamPerformance teamPerformances = new TeamPerformance(TeamHome, group);
        TeamInMatch homeTeam = match.getHomeTeam();
        Event YellowCard = new YellowCardEvent(match, homeTeam.getPlayingPlayers().get(0), 10);
        List<Event> events = new ArrayList<>();
        events.add(YellowCard);
        EventGenerator generator = new EventGenerator(events);
        match.start(generator);
        teamPerformances.update(match);
        Assert.assertEquals(1, teamPerformances.getYellowCard());
    }

    @Test
    public void Test_Update_Redcard_TeamHome(){
        TeamPerformance teamPerformances = new TeamPerformance(TeamHome, group);
        TeamInMatch homeTeam = match.getHomeTeam();
        Event RedCard = new RedCardEvent(match, homeTeam.getPlayingPlayers().get(0), 10);
        List<Event> events = new ArrayList<>();
        events.add(RedCard);
        EventGenerator generator = new EventGenerator(events);
        match.start(generator);
        teamPerformances.update(match);
        Assert.assertEquals(2, teamPerformances.getYellowCard());
    }

    @Test
    public void Test_Update_MultiRedcard_TeamHome(){
        TeamPerformance teamPerformances = new TeamPerformance(TeamHome, group);
        TeamInMatch homeTeam = match.getHomeTeam();
        Event RedCard = new RedCardEvent(match, homeTeam.getPlayingPlayers().get(0), 10);
        Event RedCard2 = new YellowCardEvent(match, homeTeam.getPlayingPlayers().get(0), 10);
        List<Event> events = new ArrayList<>();
        events.add(RedCard);
        events.add(RedCard2);
        EventGenerator generator = new EventGenerator(events);
        match.start(generator);
        teamPerformances.update(match);
        Assert.assertEquals(3, teamPerformances.getYellowCard());
    }

    @Test
    public void Test_Update_GoalFor_TeamHome(){
        TeamPerformance teamPerformances = new TeamPerformance(TeamHome, group);
        TeamInMatch homeTeam = match.getHomeTeam();
        Event Goal = new GoalEvent(match, homeTeam.getPlayingPlayers().get(0), 10);
        List<Event> events = new ArrayList<>();
        events.add(Goal);
        EventGenerator generator = new EventGenerator(events);
        match.start(generator);
        teamPerformances.update(match);
        Assert.assertEquals(1, teamPerformances.getGoalFor());
    }
    @Test
    public void Test_Update_GoalAgains_TeamHome(){
        TeamPerformance teamPerformances = new TeamPerformance(TeamHome, group);
        TeamInMatch homeTeam = match.getHomeTeam();
        Event Goal = new GoalEvent(match, homeTeam.getPlayingPlayers().get(0), 10);
        List<Event> events = new ArrayList<>();
        events.add(Goal);
        EventGenerator generator = new EventGenerator(events);
        match.start(generator);
        teamPerformances.update(match);
        Assert.assertEquals(1, teamPerformances.getGoalFor());
    }

    @After
    public void finish(){
        session.getTransaction().rollback();
    }

}
