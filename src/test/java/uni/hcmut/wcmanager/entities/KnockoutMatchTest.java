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

import java.lang.reflect.AnnotatedWildcardType;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class KnockoutMatchTest {
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

        TeamHome.setName("Thai Lan");
        TeamAway.setName("Viet Nam");

        TeamHome.setPlayers(playerHome);
        TeamAway.setPlayers(playerAway);
        group.addTeam(TeamHome);
        group.addTeam(TeamAway);

        matchKnockout = new KnockoutMatch(TeamHome, TeamAway);
        matchDraw = new DrawableMatch(TeamHome, TeamAway);
        matchDraw.setRoundName(RoundName.GROUP_STAGE);

    }

    @Test(expected = InvalidParameterException.class)
    public void test_Penalty_With_Event_Null(){
        TeamPerformance teamPerformancesHome = new TeamPerformance(TeamHome, group);
        TeamPerformance teamPerformancesAway = new TeamPerformance(TeamAway, group);

        EventGenerator generator = null;

        boolean[][] shootout = {{true, true}, {true, true}, {true, true}, {true, true}, {true, true}, {true, false}};
        PenaltyShootoutGenerator penalty = new PenaltyShootoutGenerator(shootout);
        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);
    }

    @Test(expected = InvalidParameterException.class)
    public void test_Penalty_event_draw_90_minute(){
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
        PenaltyShootoutGenerator penalty = null;

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);
    }

    @Test
    public void test_winner_before_90(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 89);

        List<Event> events = new ArrayList<>();
        events.add(goal);

        EventGenerator generator = new EventGenerator(events);
        PenaltyShootoutGenerator penalty = null;

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);

        Assert.assertEquals(homeTeam, matchKnockout.getWinner());
    }

    @Test
    public void test_winner_90_minute(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 88);
        Event awayGoal = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 89);
        Event awayGoal1 = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 90);

        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(awayGoal);
        events.add(awayGoal1);

        EventGenerator generator = new EventGenerator(events);
        PenaltyShootoutGenerator penalty = null;

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);

        Assert.assertEquals(awayTeam, matchKnockout.getWinner());
    }

    @Test
    public void test_winner_gte_90_minute(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 88);
        Event awayGoal = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 89);
        Event awayGoal1 = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 91);
        Event awayGoal2 = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 92);

        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(awayGoal);
        events.add(awayGoal1);
        events.add(awayGoal2);

        EventGenerator generator = new EventGenerator(events);
        PenaltyShootoutGenerator penalty = null;

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);

        Assert.assertEquals(awayTeam, matchKnockout.getWinner());
    }

    @Test
    public void test_winner_after_90_minute(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event awayGoal1 = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 91);
        Event awayGoal2 = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 92);
        Event awayGoal3 = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 93);

        List<Event> events = new ArrayList<>();
        events.add(awayGoal1);
        events.add(awayGoal2);
        events.add(awayGoal3);

        EventGenerator generator = new EventGenerator(events);
        PenaltyShootoutGenerator penalty = null;

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);

        Assert.assertEquals(awayTeam, matchKnockout.getWinner());
    }

    @Test
    public void test_winner_90_105_minutes(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 88);
        Event awayGoal = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 89);
        Event awayGoal1 = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 91);
        Event goal2 = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 92);
        Event goal3 = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 105);

        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(awayGoal);
        events.add(awayGoal1);
        events.add(goal2);
        events.add(goal3);

        EventGenerator generator = new EventGenerator(events);
        PenaltyShootoutGenerator penalty = null;

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);

        Assert.assertEquals(homeTeam, matchKnockout.getWinner());
    }

    @Test
    public void test_winner_before_105_minute(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 88);
        Event awayGoal = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 89);
        Event awayGoal1 = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 104);
        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(awayGoal);
        events.add(awayGoal1);

        EventGenerator generator = new EventGenerator(events);
        PenaltyShootoutGenerator penalty = null;

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);

        Assert.assertEquals(awayTeam, matchKnockout.getWinner());
    }

    @Test
    public void test_winner_105_minute(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 88);
        Event awayGoal = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 89);
        Event awayGoal1 = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 105);
        Event awayGoal2 = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 106);

        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(awayGoal);
        events.add(awayGoal1);
        events.add(awayGoal2);

        EventGenerator generator = new EventGenerator(events);
        PenaltyShootoutGenerator penalty = null;

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);

    }

    @Test(expected = InvalidParameterException.class)
    public void test_draw_in_first_extra_time(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event awayGoal1 = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 89);
        Event awayGoal2 = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 88);

        List<Event> events = new ArrayList<>();;
        events.add(awayGoal1);
        events.add(awayGoal2);

        EventGenerator generator = new EventGenerator(events);
        PenaltyShootoutGenerator penalty = null;

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);
    }

    @Test
    public void test_winner_in_extra_time(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event homeGoal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 92);
        Event awayGoal2 = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 93);
        Event awayGoal3 = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 105);

        List<Event> events = new ArrayList<>();;
        events.add(homeGoal);
        events.add(awayGoal2);
        events.add(awayGoal3);

        EventGenerator generator = new EventGenerator(events);
        PenaltyShootoutGenerator penalty = null;

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);

        Assert.assertEquals(awayTeam, matchKnockout.getWinner());
    }

    @Test
    public void test_winner_with_ownGoal(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event homeGoal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 92);
        Event awayGoal2 = new OwnGoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 93);

        List<Event> events = new ArrayList<>();;
        events.add(homeGoal);
        events.add(awayGoal2);

        EventGenerator generator = new EventGenerator(events);
        PenaltyShootoutGenerator penalty = null;

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);

        Assert.assertEquals(homeTeam, matchKnockout.getWinner());
    }

    @Test
    public void test_winner_with_ownGoal_in_90_minutes(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event awayGoal = new OwnGoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 10);

        List<Event> events = new ArrayList<>();;
        events.add(awayGoal);

        EventGenerator generator = new EventGenerator(events);
        PenaltyShootoutGenerator penalty = null;

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);

        Assert.assertEquals(homeTeam, matchKnockout.getWinner());
    }

    @Test(expected = InvalidParameterException.class)
    public void test_silver_goal(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 88);
        Event awayGoal = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 89);
        Event awayGoal1 = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 91);
        Event awayGoal2 = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 105);

        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(awayGoal);
        events.add(awayGoal1);
        events.add(awayGoal2);

        EventGenerator generator = new EventGenerator(events);
        PenaltyShootoutGenerator penalty = null;

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);

    }

    @Test(expected = InvalidParameterException.class)
    public void test_draw_no_penalty(){
        List<Event> events = new ArrayList<>();

        EventGenerator generator = new EventGenerator(events);
        PenaltyShootoutGenerator penalty = null;

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);
    }

    @Test
    public void test_Penalty_5(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 10);
        Event awayGoal = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 20);

        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(awayGoal);

        EventGenerator generator = new EventGenerator(events);

        boolean[][] shootout = {{true, true}, {true, true}, {true, true}, {true, true}, {true, false}};
        PenaltyShootoutGenerator penalty = new PenaltyShootoutGenerator(shootout);

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);

        Assert.assertEquals(homeTeam, matchKnockout.getWinner());
    }

    @Test
    public void test_Penalty_3_0(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 10);
        Event awayGoal = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 20);

        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(awayGoal);

        EventGenerator generator = new EventGenerator(events);

        boolean[][] shootout = {{true, false}, {true, false}, {true, false}};
        PenaltyShootoutGenerator penalty = new PenaltyShootoutGenerator(shootout);

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);

        Assert.assertEquals(homeTeam, matchKnockout.getWinner());

    }

    @Test(expected = InvalidParameterException.class)
    public void test_Penalty_Wrong_3_0(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 10);
        Event awayGoal = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 20);

        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(awayGoal);

        EventGenerator generator = new EventGenerator(events);

        boolean[][] shootout = {{true, false}, {true, false}, {true, false}, {false, true}};
        PenaltyShootoutGenerator penalty = new PenaltyShootoutGenerator(shootout);

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);
    }

    @Test(expected = InvalidParameterException.class)
    public void test_Penalty_Wrong_3_1(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 10);
        Event awayGoal = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 20);

        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(awayGoal);

        EventGenerator generator = new EventGenerator(events);

        boolean[][] shootout = {{true, false}, {true, false}, {true, true}};
        PenaltyShootoutGenerator penalty = new PenaltyShootoutGenerator(shootout);

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);
    }

    @Test
    public void test_Penalty_4_1(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 10);
        Event awayGoal = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 20);

        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(awayGoal);

        EventGenerator generator = new EventGenerator(events);

        boolean[][] shootout = {{true, true}, {true, false}, {true, false}, {true, false}};
        PenaltyShootoutGenerator penalty = new PenaltyShootoutGenerator(shootout);

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);
        Assert.assertEquals(homeTeam, matchKnockout.getWinner());
    }

    @Test
    public void test_Penalty_4_2(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 10);
        Event awayGoal = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 20);

        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(awayGoal);

        EventGenerator generator = new EventGenerator(events);

        boolean[][] shootout = {{true, false}, {true, false}, {true, true}, {true, true}};
        PenaltyShootoutGenerator penalty = new PenaltyShootoutGenerator(shootout);

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);
        Assert.assertEquals(homeTeam, matchKnockout.getWinner());
    }

    @Test(expected = InvalidParameterException.class)
    public void test_Penalty_4_2_wrong(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 10);
        Event awayGoal = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 20);

        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(awayGoal);

        EventGenerator generator = new EventGenerator(events);

        boolean[][] shootout = {{true, false}, {true, false}, {true, true}, {true, true}, {false, true}};
        PenaltyShootoutGenerator penalty = new PenaltyShootoutGenerator(shootout);

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);
    }

    @Test(expected = InvalidParameterException.class)
    public void test_Penalty_2_0(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 10);
        Event awayGoal = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 20);

        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(awayGoal);

        EventGenerator generator = new EventGenerator(events);

        boolean[][] shootout = {{true, false}, {true, false}};
        PenaltyShootoutGenerator penalty = new PenaltyShootoutGenerator(shootout);

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);
    }

    @Test(expected = InvalidParameterException.class)
    public void test_Penalty_1_1(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 10);
        Event awayGoal = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 20);

        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(awayGoal);

        EventGenerator generator = new EventGenerator(events);

        boolean[][] shootout = {{true, false}};
        PenaltyShootoutGenerator penalty = new PenaltyShootoutGenerator(shootout);

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);
    }

    @Test(expected = InvalidParameterException.class)
    public void test_Penalty_lack(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 10);
        Event awayGoal = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 20);

        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(awayGoal);

        EventGenerator generator = new EventGenerator(events);

        boolean[][] shootout = {{true, true}, {true, true}, {true, true}, {true, true}};
        PenaltyShootoutGenerator penalty = new PenaltyShootoutGenerator(shootout);

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);
    }

    @Test(expected = InvalidParameterException.class)
    public void test_Penalty_gte_5_wrong(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 10);
        Event awayGoal = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 20);

        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(awayGoal);

        EventGenerator generator = new EventGenerator(events);

        boolean[][] shootout = {{true, true}, {true, true}, {true, true}, {true, true}, {true, false}, {false, true}};
        PenaltyShootoutGenerator penalty = new PenaltyShootoutGenerator(shootout);

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);
    }

    @Test
    public void test_Penalty_gte_5(){

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

        Assert.assertEquals(homeTeam, matchKnockout.getWinner());
    }

    @Test(expected = InvalidParameterException.class)
    public void test_Penalty_gte_5_wrong_redundant(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 10);
        Event awayGoal = new GoalEvent(matchKnockout, awayTeam.getPlayingPlayers().get(0), 20);

        List<Event> events = new ArrayList<>();
        events.add(goal);
        events.add(awayGoal);

        EventGenerator generator = new EventGenerator(events);

        boolean[][] shootout = {{true, true}, {true, true}, {true, true}, {true, true}, {true, true}, {true, false}, {false, true}};
        PenaltyShootoutGenerator penalty = new PenaltyShootoutGenerator(shootout);

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);
    }

    @Test(expected = InvalidParameterException.class)
    public void test_Penalty_but_finish_match(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 10);

        List<Event> events = new ArrayList<>();
        events.add(goal);

        EventGenerator generator = new EventGenerator(events);

        boolean[][] shootout = {{true, true}, {true, true}, {true, true}, {true, true}, {true, true}, {true, false}, {false, true}};
        PenaltyShootoutGenerator penalty = new PenaltyShootoutGenerator(shootout);

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);
    }

    @Test(expected = InvalidParameterException.class)
    public void test_Penalty_but_finish_match_in_first_extra_time(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 92);

        List<Event> events = new ArrayList<>();
        events.add(goal);

        EventGenerator generator = new EventGenerator(events);

        boolean[][] shootout = {{true, true}, {true, true}, {true, true}, {true, true}, {true, true}, {true, false}, {false, true}};
        PenaltyShootoutGenerator penalty = new PenaltyShootoutGenerator(shootout);

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);
    }

    @Test(expected = InvalidParameterException.class)
    public void test_Penalty_but_finish_match_in_2nd_extra_time(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 106);

        List<Event> events = new ArrayList<>();
        events.add(goal);

        EventGenerator generator = new EventGenerator(events);

        boolean[][] shootout = {{true, true}, {true, true}, {true, true}, {true, true}, {true, true}, {true, false}, {false, true}};
        PenaltyShootoutGenerator penalty = new PenaltyShootoutGenerator(shootout);

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);
    }

    @Test(expected = InvalidParameterException.class)
    public void test_Penalty_but_shootout_empty(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch awayTeam = matchKnockout.getAwayTeam();

        Event goal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 106);

        List<Event> events = new ArrayList<>();
        events.add(goal);

        EventGenerator generator = new EventGenerator(events);

        boolean[][] shootout = {};
        PenaltyShootoutGenerator penalty = new PenaltyShootoutGenerator(shootout);

        matchKnockout.setRoundName(RoundName.ROUND_OF_SIXTEEN);
        matchKnockout.start(generator, penalty);
    }


    @After
    public void finish(){
        session.getTransaction().rollback();
    }
}
