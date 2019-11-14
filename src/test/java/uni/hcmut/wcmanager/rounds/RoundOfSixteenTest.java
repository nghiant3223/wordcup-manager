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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoundOfSixteenTest {
    Team TeamHome = new Team();
    Team TeamHome2 = new Team();
    Team TeamAway = new Team();
    Team TeamAway2 = new Team();

    List<Player> playerHome = new ArrayList<Player>();
    List<Player> playerHome2 = new ArrayList<Player>();
    List<Player> playerAway = new ArrayList<Player>();
    List<Player> playerAway2 = new ArrayList<Player>();

    DrawableMatch matchDraw;
    DrawableMatch matchDraw2;
    KnockoutMatch matchKnockout;
    KnockoutMatch matchKnockout2;
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

            Player player4 = new Player();
            String fullNameAway4 = "TeamAway4" + Integer.toString(index);
            player4.setFullname(fullNameAway4);
            player4.setGoalCount(index%3);
            playerHome2.add(player4);
        }

        TeamHome.setPlayers(playerHome);
        TeamAway.setPlayers(playerAway);
        TeamAway2.setPlayers(playerAway2);
        TeamHome2.setPlayers(playerHome2);

        group.addTeam(TeamAway2);
        group.addTeam(TeamHome);
        group.addTeam(TeamAway);
        group.addTeam(TeamHome2);

        matchKnockout = new KnockoutMatch(TeamHome, TeamAway);
        matchKnockout2 = new KnockoutMatch(TeamHome2, TeamAway2);
    }

    @Test
    public void test_getResult(){

        TeamInMatch homeTeam = matchKnockout.getHomeTeam();
        TeamInMatch aWayTeam = matchKnockout.getAwayTeam();
        TeamInMatch homeTeam2 = matchKnockout2.getHomeTeam();
        TeamInMatch aWayTeam2 = matchKnockout2.getAwayTeam();

        Event homeGoal = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 10);
        Event homeGoal2 = new GoalEvent(matchKnockout, homeTeam.getPlayingPlayers().get(0), 20);

        Event aWayTeamGoal = new GoalEvent(matchKnockout, aWayTeam.getPlayingPlayers().get(0), 30);
        Event aWayTeamGoal2 = new GoalEvent(matchKnockout, aWayTeam.getPlayingPlayers().get(0), 40);

        Event home2Goal = new GoalEvent(matchKnockout2, homeTeam2.getPlayingPlayers().get(0), 50);

        List<Event> events = new ArrayList<>();
        events.add(homeGoal);
        events.add(homeGoal2);
        events.add(aWayTeamGoal);
        events.add(aWayTeamGoal2);

        List<Event> events2 = new ArrayList<>();
        events2.add(home2Goal);

        EventGenerator generator = new EventGenerator(events);
        boolean[][] shootout = {{true, false}, {true, false}, {true, true}, {true, true}};
        PenaltyShootoutGenerator penalty = new PenaltyShootoutGenerator(shootout);
        matchKnockout.start(generator, penalty);

        EventGenerator generator2 = new EventGenerator(events2);
        PenaltyShootoutGenerator penalty2 = null;
        matchKnockout2.start(generator2, penalty2);

        List<Match> matchList = new ArrayList<>();
        matchList.add(matchKnockout);
        matchList.add(matchKnockout2);

        RoundOfSixteen roundOfSixteen = new RoundOfSixteen(matchList);
        Map<Integer, Team[]> result = roundOfSixteen.getResult();
        Team[] roundOfSixteen1 =  result.get(0);
        Team[] roundOfSixteen2 =  result.get(1);
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(TeamHome, roundOfSixteen1[0]);
        Assert.assertEquals(TeamHome2, roundOfSixteen2[0]);
    }

    @Test
    public void test_getResult_with_match_null(){

        List<Match> roundOfSixteenMatch = new ArrayList<>();
        RoundOfSixteen roundOfSixteenResult = new RoundOfSixteen(roundOfSixteenMatch);
        Map<Integer, Team[]> result =  roundOfSixteenResult.getResult();
        Assert.assertEquals(0, result.size());
    }



    @Test
    public void test_getName(){
        List<Match> roundOfSixteenMatch = new ArrayList<>();
        RoundOfSixteen roundOfSixteenResult = new RoundOfSixteen(roundOfSixteenMatch);
        Assert.assertEquals(RoundName.ROUND_OF_SIXTEEN, roundOfSixteenResult.getName());
    }

    @After
    public void finish(){
        session.getTransaction().rollback();
    }
}
