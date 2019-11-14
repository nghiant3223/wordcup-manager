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
import uni.hcmut.wcmanager.events.YellowCardEvent;
import uni.hcmut.wcmanager.randomizers.EventGenerator;
import uni.hcmut.wcmanager.randomizers.PenaltyShootoutGenerator;
import uni.hcmut.wcmanager.utils.HibernateUtils;
import uni.hcmut.wcmanager.utils.PerformanceOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupStageTest {
    Team TeamGroupA1 = new Team();
    Team TeamGroupA2 = new Team();
    Team TeamGroupA3= new Team();
    Team TeamGroupA4 = new Team();

    Team TeamGroupB1 = new Team();
    Team TeamGroupB2 = new Team();
    Team TeamGroupB3 = new Team();
    Team TeamGroupB4 = new Team();


    List<Player> playerTeamGroupA1 = new ArrayList<Player>();
    List<Player> playerTeamGroupA2 = new ArrayList<Player>();
    List<Player> playerTeamGroupA3 = new ArrayList<Player>();
    List<Player> playerTeamGroupA4 = new ArrayList<Player>();

    List<Player> playerTeamGroupB1 = new ArrayList<Player>();
    List<Player> playerTeamGroupB2 = new ArrayList<Player>();
    List<Player> playerTeamGroupB3 = new ArrayList<>();
    List<Player> playerTeamGroupB4 = new ArrayList<Player>();

    DrawableMatch matchDrawA1;
    DrawableMatch matchDrawA2;
    DrawableMatch matchDrawA3;
    DrawableMatch matchDrawA4;
    DrawableMatch matchDrawA5;
    DrawableMatch matchDrawA6;

    DrawableMatch matchDrawB1;
    DrawableMatch matchDrawB2;
    DrawableMatch matchDrawB3;
    DrawableMatch matchDrawB4;
    DrawableMatch matchDrawB5;
    DrawableMatch matchDrawB6;

    Group groupA = new Group(GroupName.A);
    Group groupB = new Group(GroupName.B);

    Session session;
    SessionFactory factory;

    @Before
    public void init(){
        factory = HibernateUtils.getSessionFactory();
        session = factory.getCurrentSession();
        session.getTransaction().begin();

        for(int index = 0; index < GameRule.TEAM_PLAYER_COUNT; index ++){
            Player player = new Player();
            String fullName = "A" + Integer.toString(index);
            player.setFullname(fullName);
            player.setGoalCount(index%2);
            playerTeamGroupA1.add(player);

            Player player2 = new Player();
            String fullNameAway = "B" + Integer.toString(index);
            player2.setFullname(fullNameAway);
            player2.setGoalCount(index%3);
            playerTeamGroupA2.add(player2);

            Player player3 = new Player();
            String fullNameAway2 = "C" + Integer.toString(index);
            player3.setFullname(fullNameAway2);
            player3.setGoalCount(index%3);
            playerTeamGroupA3.add(player3);

            Player player4 = new Player();
            String fullNameAway4 = "D" + Integer.toString(index);
            player4.setFullname(fullNameAway4);
            player4.setGoalCount(index%3);
            playerTeamGroupB1.add(player4);

            Player player5 = new Player();
            String fullNameAway5 = "D" + Integer.toString(index);
            player5.setFullname(fullNameAway4);
            player5.setGoalCount(index%3);
            playerTeamGroupB2.add(player4);

            Player player6 = new Player();
            String fullNameAway6 = "E" + Integer.toString(index);
            player6.setFullname(fullNameAway4);
            player6.setGoalCount(index%3);
            playerTeamGroupB3.add(player4);

            Player player7 = new Player();
            String fullNameAway7 = "F" + Integer.toString(index);
            player7.setFullname(fullNameAway7);
            player7.setGoalCount(index%3);
            playerTeamGroupB4.add(player7);

            Player player8 = new Player();
            String fullNameAway8 = "F" + Integer.toString(index);
            player8.setFullname(fullNameAway8);
            player8.setGoalCount(index%3);
            playerTeamGroupA4.add(player8);

        }

        TeamGroupA1.setPlayers(playerTeamGroupA1);
        TeamGroupA2.setPlayers(playerTeamGroupA2);
        TeamGroupA3.setPlayers(playerTeamGroupA3);
        TeamGroupA4.setPlayers(playerTeamGroupA4);

        TeamGroupB1.setPlayers(playerTeamGroupB1);
        TeamGroupB2.setPlayers(playerTeamGroupB2);
        TeamGroupB3.setPlayers(playerTeamGroupB3);
        TeamGroupB4.setPlayers(playerTeamGroupB4);

        groupA.addTeam(TeamGroupA1);
        groupA.addTeam(TeamGroupA2);
        groupA.addTeam(TeamGroupA3);
        groupA.addTeam(TeamGroupA4);

        groupB.addTeam(TeamGroupB1);
        groupB.addTeam(TeamGroupB2);
        groupB.addTeam(TeamGroupB3);
        groupB.addTeam(TeamGroupB4);

        matchDrawA1 = new DrawableMatch(TeamGroupA1, TeamGroupA2);
        matchDrawA2 = new DrawableMatch(TeamGroupA2, TeamGroupA3);
        matchDrawA3 = new DrawableMatch(TeamGroupA1, TeamGroupA3);
        matchDrawA4 = new DrawableMatch(TeamGroupA1, TeamGroupA4);
        matchDrawA5 = new DrawableMatch(TeamGroupA2, TeamGroupA4);
        matchDrawA6 = new DrawableMatch(TeamGroupA3, TeamGroupA4);

        matchDrawB1 = new DrawableMatch(TeamGroupB1, TeamGroupB2);
        matchDrawB2 = new DrawableMatch(TeamGroupB2, TeamGroupB3);
        matchDrawB3 = new DrawableMatch(TeamGroupB1, TeamGroupB3);
        matchDrawB4 = new DrawableMatch(TeamGroupB1, TeamGroupB4);
        matchDrawB5 = new DrawableMatch(TeamGroupB2, TeamGroupB4);
        matchDrawB6 = new DrawableMatch(TeamGroupB3, TeamGroupB4);
    }

    @Test
    public void test_getResult(){

        List<Group> groupList = new ArrayList<>();
        groupList.add(groupA);
        groupList.add(groupB);

        GroupStage groupStage = new GroupStage(groupList);
        groupStage.run();
        Map<Integer, Team[]> result = groupStage.getResult();
        Team[] groupStageA =  result.get(0);
        TeamPerformance firstGroupA = groupA.getTeamPerformances().get(0);
        TeamPerformance secondGroupA = groupA.getTeamPerformances().get(1);

        Team[] groupStageB =  result.get(0);
        TeamPerformance firstGroupB = groupB.getTeamPerformances().get(0);
        TeamPerformance secondGroupB = groupB.getTeamPerformances().get(1);

        PerformanceOrder performanceOrder = new PerformanceOrder();
        int actualA = performanceOrder.compare(secondGroupA, firstGroupA);
        PerformanceOrder performanceOrderB = new PerformanceOrder();
        int actualB = performanceOrder.compare(secondGroupB, firstGroupB);
        Assert.assertTrue(actualA > 0);
        Assert.assertTrue(actualB > 0);
    }


    @Test
    public void test_getName(){

        List<Group> groupList = new ArrayList<>();
        groupList.add(groupA);
        groupList.add(groupB);

        GroupStage groupStage = new GroupStage(groupList);
        groupStage.run();
        Assert.assertEquals(RoundName.GROUP_STAGE, groupStage.getName());
    }

    @After
    public void finish(){
        session.getTransaction().rollback();
    }
}
