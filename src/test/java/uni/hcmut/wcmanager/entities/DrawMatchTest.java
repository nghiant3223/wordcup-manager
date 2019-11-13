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
import uni.hcmut.wcmanager.events.RedCardEvent;
import uni.hcmut.wcmanager.randomizers.EventGenerator;
import uni.hcmut.wcmanager.randomizers.PenaltyShootoutGenerator;
import uni.hcmut.wcmanager.utils.HibernateUtils;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class DrawMatchTest {
    Team TeamHome = new Team();
    Team TeamAway = new Team();
    List<Player> playerHome = new ArrayList<Player>();
    List<Player> playerAway = new ArrayList<Player>();
    DbMatch matchDB = new DbMatch();
    DrawableMatch matchDraw;
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

        TeamHome.setName("Thai Lan");
        TeamAway.setName("Viet Nam");

        TeamHome.setPlayers(playerHome);
        TeamAway.setPlayers(playerAway);
        group.addTeam(TeamHome);
        group.addTeam(TeamAway);

        matchDraw = new DrawableMatch(TeamHome, TeamAway);
        matchDraw.setRoundName(RoundName.GROUP_STAGE);

    }



    @Test
    public void test_winner_before_90(){

        TeamInMatch homeTeam = matchDraw.getHomeTeam();

        Event goal = new GoalEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 89);

        List<Event> events = new ArrayList<>();
        events.add(goal);

        EventGenerator generator = new EventGenerator(events);
        matchDraw.start(generator);

        Assert.assertEquals(homeTeam, matchDraw.getWinner());
    }

    @Test
    public void test_draw_before_90(){

        TeamInMatch homeTeam = matchDraw.getHomeTeam();
        TeamInMatch awayTeam = matchDraw.getAwayTeam();

        Event goal = new GoalEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 80);
        Event awaygoal = new GoalEvent(matchDraw, awayTeam.getPlayingPlayers().get(0), 89);

        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(awaygoal);

        EventGenerator generator = new EventGenerator(events);
        matchDraw.start(generator);

        Assert.assertNull(matchDraw.getWinner());
    }

    @Test(expected = InvalidParameterException.class)
    public void test_event_with_after_89(){

        TeamInMatch homeTeam = matchDraw.getHomeTeam();
        TeamInMatch awayTeam = matchDraw.getAwayTeam();

        Event goal = new GoalEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 80);
        Event awaygoal = new GoalEvent(matchDraw, awayTeam.getPlayingPlayers().get(0), 90);

        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(awaygoal);

        EventGenerator generator = new EventGenerator(events);
        matchDraw.start(generator);
    }

    @Test(expected = InvalidParameterException.class)
    public void test_event_with_red_card(){

        TeamInMatch homeTeam = matchDraw.getHomeTeam();
        TeamInMatch awayTeam = matchDraw.getAwayTeam();

        Event goal = new GoalEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 80);
        Event redCard = new RedCardEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 30);
        Event redCard1 = new RedCardEvent(matchDraw, homeTeam.getPlayingPlayers().get(1), 30);
        Event redCard2 = new RedCardEvent(matchDraw, homeTeam.getPlayingPlayers().get(2), 30);
        Event redCard3 = new RedCardEvent(matchDraw, homeTeam.getPlayingPlayers().get(4), 30);

        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(redCard);
        events.add(redCard1);
        events.add(redCard2)

        EventGenerator generator = new EventGenerator(events);
        matchDraw.start(generator);
    }

    @After
    public void finish(){
        session.getTransaction().rollback();
    }
}
