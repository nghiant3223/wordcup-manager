package uni.hcmut.wcmanager.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uni.hcmut.wcmanager.constants.GameRule;

import java.util.ArrayList;
import java.util.List;

public class DBMatchTest {
    Team TeamHome = new Team();
    Team TeamAway = new Team();
    List<Player> playerHome = new ArrayList<Player>();
    List<Player> playerAway = new ArrayList<Player>();
    DbMatch match = new DbMatch();

    @Before
    public void init(){
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
    }

    @Test
    public void test_getHomeTeam(){
        match.setHomeTeam(TeamHome);
        Team actual = match.getHomeTeam();
        Assert.assertEquals(TeamHome, actual);
    }

    @Test
    public void test_Wrong_getHomeTeam(){
        match.setHomeTeam(TeamHome);
        Team actual = match.getHomeTeam();
        Assert.assertNotEquals(TeamAway, actual);
    }

    @Test
    public void test_getHomeAway(){
        match.setAwayTeam(TeamAway);
        Team actual = match.getAwayTeam();
        Assert.assertEquals(TeamAway, actual);
    }

    @Test
    public void test_Wrong_getHomeAway(){
        match.setAwayTeam(TeamAway);
        Team actual = match.getAwayTeam();
        Assert.assertNotEquals(TeamHome, actual);
    }

    @Test
    public void test_getWinnerTeam(){
        match.setWinnerTeam(TeamAway);
        Team actual = match.getWinnerTeam();
        Assert.assertEquals(TeamAway, actual);
    }

    @Test
    public void test_Wrong_getWinnerTeam(){
        match.setWinnerTeam(TeamAway);
        Team actual = match.getWinnerTeam();
        Assert.assertNotEquals(TeamHome, actual);
    }

    @Test
    public void test_getRoundID(){
        match.setRoundId(1);
        int actual = match.getRoundId();
        Assert.assertEquals(1, actual);
    }

    @Test
    public void test_Wrong_getRoundID(){
        match.setRoundId(1);
        int actual = match.getRoundId();
        Assert.assertNotEquals(2, actual);
    }

    @Test
    public void test_getHomeResult(){
        match.setHomeResult(1);
        int actual = match.getHomeResult();
        Assert.assertEquals(1, actual);
    }

    @Test
    public void test_getAwayResult(){
        match.setAwayResult(1);
        int actual = match.getAwayResult();
        Assert.assertEquals(1, actual);
    }

    @Test
    public void test_getAwayPenalty(){
        match.setAwayPenalty(3);
        int actual = match.getAwayPenalty();
        Assert.assertEquals(3, actual);
    }

    @Test
    public void test_getHomePenalty(){
        match.setHomePenalty(3);
        int actual = match.getHomePenalty();
        Assert.assertEquals(3, actual);
    }
}
