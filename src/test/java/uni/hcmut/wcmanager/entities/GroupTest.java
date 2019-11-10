package uni.hcmut.wcmanager.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uni.hcmut.wcmanager.enums.GroupName;

import java.util.ArrayList;
import java.util.List;

public class GroupTest {
    Group group = new Group(GroupName.A);
    Team team1 = new Team();
    Team team2 = new Team();
    Team team3 = new Team();
    List<Team> listTeam = new ArrayList<>();
    List<TeamPerformance> teamPerformances = new ArrayList<>();

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
    }

    @Test
    public void Test_getName(){
        GroupName name = group.getName();
        Assert.assertEquals(GroupName.A, name);
    }

    @Test
    public void Test_getWrongName(){
        group = new Group(GroupName.A);
        GroupName name = group.getName();
        Assert.assertNotEquals(GroupName.B, name);
    }

    @Test
    public void Test_getTeam(){
        group.addTeam(team1);
        group.addTeam(team2);

        List<Team> listTeamActual= group.getTeams();
        Assert.assertEquals(listTeam, listTeamActual);
    }

    @Test
    public void Test_getWrongTeam(){
        Group group1 = new Group(GroupName.B);
        group1.addTeam(team1);
        group1.addTeam(team2);
        group1.addTeam(team3);

        List<Team> listTeamActual= group1.getTeams();
        Assert.assertNotEquals(listTeam, listTeamActual);
    }

    @Test
    public void Test_getNullTeamByPlace(){
       Assert.assertNull(group.getTeamByPlace(7));
    }

    @Test
    public void Test_getTeamByPlace(){
        group.addTeam(team1);
        group.addTeam(team2);
        Assert.assertEquals(team1, group.getTeamByPlace(0));
        Assert.assertEquals(team2, group.getTeamByPlace(1));
    }

}
