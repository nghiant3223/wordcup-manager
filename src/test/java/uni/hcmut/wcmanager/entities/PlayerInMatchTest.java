package uni.hcmut.wcmanager.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uni.hcmut.wcmanager.constants.GameRule;
import uni.hcmut.wcmanager.constants.MatchRule;

import java.util.ArrayList;

public class PlayerInMatchTest {
    private PlayerInMatch playerInMatch;
    private Player player;

    @Before
    public void init() {

        Team team = new Team();
        ArrayList<Player> players = new ArrayList<Player>(15);
        for (int i = 0; i < 15; i++) {
            Player player = new Player();
            player.setFullname("Nguyễn Văn " + i);
            players.add(player);
        }
        team.setPlayers(players);
        TeamInMatch teamInMatch = new TeamInMatch(team);
        this.player = players.get(0);
        this.playerInMatch = new PlayerInMatch(this.player, teamInMatch);
    }

    @Test
    public void checkGetRedCardCount() {
        Assert.assertTrue(this.playerInMatch.getRedCardCount() == 0);
    }

    @Test
    public void checkIncrementRedCardCount() {
        this.playerInMatch.incrementRedCardCount();
        Assert.assertTrue(this.playerInMatch.getRedCardCount() == 1);
    }

    @Test
    public void checkGetYellowCardCount() {
        Assert.assertTrue(this.playerInMatch.getYellowCardCount() == 0);
    }

    @Test
    public void checkIncrementYellowCard() {
        this.playerInMatch.incrementYellowCard();
        Assert.assertTrue(this.playerInMatch.getYellowCardCount() == 1);
    }

    @Test
    public void checkGetGoalCount() {
        Assert.assertTrue(this.playerInMatch.getGoalCount() == 0);
    }

    @Test
    public void checkIncrementGoalCountForPlayerInMatch() {
        this.playerInMatch.incrementGoalCount();
        Assert.assertTrue(this.playerInMatch.getGoalCount() == 1);
    }

    @Test
    public void checkIncrementGoalCountForPlayer() {
        this.playerInMatch.incrementGoalCount();
        Assert.assertTrue(this.player.getGoalCount() == 1);
    }

    @Test
    public void checkGetTeamInMatch() {
        TeamInMatch teamInMatch = this.playerInMatch.getTeamInMatch();
        Assert.assertTrue(teamInMatch.getTeam().getPlayers().get(0).getFullname().equals("Nguyễn Văn 0"));
    }
}
