package uni.hcmut.wcmanager.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uni.hcmut.wcmanager.enums.GroupName;

import java.util.ArrayList;
import java.util.List;

public class TeamPerformanceTest {

    Group group = new Group(GroupName.A);
    Team team1 = new Team();
    Team team2 = new Team();
    Team team3 = new Team();
    List<Team> listTeam = new ArrayList<>();


    @Before
    public void init() {
        Player player1 = new Player();
        Player player2 = new Player();

        player1.setFullname("Nguyen Trong Nghia");
        player1.setGoalCount(3);

        player2.setFullname("Tran Dang Khoi");
        player2.setGoalCount(4);

        List<Player> playerList1 = new ArrayList<Player>();
        playerList1.add(player1);

        List<Player> playerList2 = new ArrayList<Player>();
        playerList2.add(player2);

        team1.setPlayers(playerList1);
        team2.setPlayers(playerList2);
        team3.setPlayers(playerList2);

        listTeam.add(team1);
        listTeam.add(team2);

        group.addTeam(team1);
        group.addTeam(team2);

    }

    @Test
    public void Test_getTeam(){
        TeamPerformance teamPerformances = new TeamPerformance(team1, group);
        Assert.assertEquals(team1, teamPerformances.getTeam());
    }

    @Test
    public void Test_getWrongTeam(){
        TeamPerformance teamPerformances = new TeamPerformance(team1, group);
        Assert.assertNotEquals(team2, teamPerformances.getTeam());
    }


}
