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
import uni.hcmut.wcmanager.events.*;
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

    @Test
    public void test_event_with_only_red_card(){

        TeamInMatch homeTeam = matchDraw.getHomeTeam();
        TeamInMatch awayTeam = matchDraw.getAwayTeam();


        int size = homeTeam.getPlayingPlayers().size();
        int numRedCard = size - 6;
        List<Event> events = new ArrayList<>();
        Event goal = new GoalEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 20);
        events.add(goal);

        for(int i = 0; i < numRedCard; i++){
            Event redCard = new RedCardEvent(matchDraw, homeTeam.getPlayingPlayers().get(i), i + 30);
            events.add(redCard);
        }

        EventGenerator generator = new EventGenerator(events);
        matchDraw.start(generator);
        Assert.assertEquals(awayTeam, matchDraw.getWinner());

    }

    @Test
    public void test_event_with_only_yellow_card(){

        TeamInMatch homeTeam = matchDraw.getHomeTeam();
        TeamInMatch awayTeam = matchDraw.getAwayTeam();


        int size = homeTeam.getPlayingPlayers().size();
        int numYellowCard = size - 6;
        List<Event> events = new ArrayList<>();
        Event goal = new GoalEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 20);
        events.add(goal);

        for(int i = 0; i < numYellowCard; i++){
            Event yellowCard = new YellowCardEvent(matchDraw, homeTeam.getPlayingPlayers().get(i), i + 30);
            events.add(yellowCard);
        }
        for(int i = 0; i < numYellowCard; i++){
            Event yellowCard2 = new YellowCardEvent(matchDraw, homeTeam.getPlayingPlayers().get(i), i + 40);
            events.add(yellowCard2);
        }

        EventGenerator generator = new EventGenerator(events);
        matchDraw.start(generator);
        Assert.assertEquals(awayTeam, matchDraw.getWinner());

    }

    @Test
    public void test_event_with_red_card_and_yellow_card(){

        TeamInMatch homeTeam = matchDraw.getHomeTeam();
        TeamInMatch awayTeam = matchDraw.getAwayTeam();


        int size = homeTeam.getPlayingPlayers().size();
        int numRedCard = size - 7;

        List<Event> events = new ArrayList<>();
        Event goal = new GoalEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 20);
        Event yellowCard = new YellowCardEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 21);
        Event yellowCard2 = new YellowCardEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 22);

        events.add(goal);
        events.add(yellowCard);
        events.add(yellowCard2);

        for(int i = 0; i < numRedCard; i++){
            Event redCard = new RedCardEvent(matchDraw, homeTeam.getPlayingPlayers().get(i+1), i + 30);
            events.add(redCard);
        }


        EventGenerator generator = new EventGenerator(events);
        matchDraw.start(generator);
        Assert.assertEquals(awayTeam, matchDraw.getWinner());

    }

    @Test
    public void test_event_with_only_injury(){

        TeamInMatch homeTeam = matchDraw.getHomeTeam();
        TeamInMatch awayTeam = matchDraw.getAwayTeam();


        int size = homeTeam.getPlayingPlayers().size();
        System.out.println(size);
        int numInjuryEvent = size - 3;
        List<Event> events = new ArrayList<>();
        Event goal = new GoalEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 20);

        events.add(goal);

        for(int i = 0; i < numInjuryEvent; i++){
            Event injury = new InjuryEvent(matchDraw, homeTeam.getPlayingPlayers().get(i), i + 30);
            events.add(injury);
        }

        EventGenerator generator = new EventGenerator(events);
        matchDraw.start(generator);
        Assert.assertEquals(awayTeam, matchDraw.getWinner());

    }

    @Test
    public void test_event_with_substitution_injury(){

        TeamInMatch homeTeam = matchDraw.getHomeTeam();
        TeamInMatch awayTeam = matchDraw.getAwayTeam();


        int size = homeTeam.getPlayingPlayers().size();
        int numInjuryEvent = size - 6;
        List<Event> events = new ArrayList<>();
        Event goal = new GoalEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 20);
        Event subStitution = new SubstitutionEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 21);
        Event subStitution1 = new SubstitutionEvent(matchDraw, homeTeam.getPlayingPlayers().get(1), 22);
        Event subStitution2 = new SubstitutionEvent(matchDraw, homeTeam.getPlayingPlayers().get(2), 23);


        events.add(goal);
        events.add(subStitution);
        events.add(subStitution1);
        events.add(subStitution2);
        for(int i = 0; i < numInjuryEvent; i++){
            Event injury = new InjuryEvent(matchDraw, homeTeam.getPlayingPlayers().get(i+3), i + 30);
            events.add(injury);
        }

        EventGenerator generator = new EventGenerator(events);
        matchDraw.start(generator);
        Assert.assertEquals(awayTeam, matchDraw.getWinner());

    }

    @Test
    public void test_event_with_redcard_and_injury_and_substitution(){

        TeamInMatch homeTeam = matchDraw.getHomeTeam();
        TeamInMatch awayTeam = matchDraw.getAwayTeam();


        int size = homeTeam.getPlayingPlayers().size();
        System.out.println(size);
        int numInjuryEvent = size - 7;
        List<Event> events = new ArrayList<>();
        Event goal = new GoalEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 20);
        Event subStitution = new SubstitutionEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 21);
        Event subStitution1 = new SubstitutionEvent(matchDraw, homeTeam.getPlayingPlayers().get(1), 22);
        Event subStitution2 = new SubstitutionEvent(matchDraw, homeTeam.getPlayingPlayers().get(2), 23);
        Event redCard = new RedCardEvent(matchDraw, homeTeam.getPlayingPlayers().get(3), 24);

        events.add(goal);
        events.add(subStitution);
        events.add(subStitution1);
        events.add(subStitution2);
        events.add(redCard);

        for(int i = 0; i < numInjuryEvent; i++){
            Event injury = new InjuryEvent(matchDraw, homeTeam.getPlayingPlayers().get(i+4), i + 30);
            events.add(injury);
        }

        EventGenerator generator = new EventGenerator(events);
        matchDraw.start(generator);
        Assert.assertEquals(awayTeam, matchDraw.getWinner());

    }

    @Test(expected = InvalidParameterException.class)
    public void test_event_with_redcard_and_injury_and_substitution_Wrong(){

        TeamInMatch homeTeam = matchDraw.getHomeTeam();
        TeamInMatch awayTeam = matchDraw.getAwayTeam();


        int size = homeTeam.getPlayingPlayers().size();
        int numInjuryEvent = size - 7;
        List<Event> events = new ArrayList<>();
        Event goal = new GoalEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 20);
        Event subStitution = new SubstitutionEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 21);
        Event subStitution1 = new SubstitutionEvent(matchDraw, homeTeam.getPlayingPlayers().get(1), 22);
        Event subStitution2 = new SubstitutionEvent(matchDraw, homeTeam.getPlayingPlayers().get(2), 23);
        Event redCard = new RedCardEvent(matchDraw, homeTeam.getPlayingPlayers().get(3), 24);

        events.add(goal);
        events.add(subStitution);
        events.add(subStitution1);
        events.add(subStitution2);
        events.add(redCard);

        for(int i = 0; i < numInjuryEvent + 1; i++){
            Event injury = new InjuryEvent(matchDraw, homeTeam.getPlayingPlayers().get(i+4), i + 30);
            events.add(injury);
        }

        EventGenerator generator = new EventGenerator(events);
        matchDraw.start(generator);

    }

    @Test
    public void test_event_with_injury_and_substitution_And_enough_people(){

        TeamInMatch homeTeam = matchDraw.getHomeTeam();
        TeamInMatch awayTeam = matchDraw.getAwayTeam();


        int size = homeTeam.getPlayingPlayers().size();
        int numInjuryEvent = size - 7;
        List<Event> events = new ArrayList<>();
        Event goal = new GoalEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 20);
        Event subStitution = new SubstitutionEvent(matchDraw, homeTeam.getPlayingPlayers().get(0), 21);
        Event subStitution1 = new SubstitutionEvent(matchDraw, homeTeam.getPlayingPlayers().get(1), 22);

        events.add(goal);
        events.add(subStitution);
        events.add(subStitution1);

        for(int i = 0; i < numInjuryEvent ; i++){
            Event injury = new InjuryEvent(matchDraw, homeTeam.getPlayingPlayers().get(i+3), i + 30);
            events.add(injury);
        }

        EventGenerator generator = new EventGenerator(events);
        matchDraw.start(generator);
        Assert.assertEquals(homeTeam, matchDraw.getWinner());
    }

    @After
    public void finish(){
        session.getTransaction().rollback();
    }
}
