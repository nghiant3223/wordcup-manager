package uni.hcmut.wcmanager.events;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uni.hcmut.wcmanager.entities.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class OwnGoalEventTest {

    private PlayerInMatch playerInMatch;
    private TeamInMatch teamInMatch;
    private Match match;

    @Before
    public void init() {
        Team teamHome = new Team();
        ArrayList<Player> players1 = new ArrayList<Player>(22);
        for (int i = 0; i < 15; i++) {
            Player player = new Player();
            player.setFullname("Nguyễn Văn " + i);
            players1.add(player);
        }
        teamHome.setPlayers(players1);
        teamHome.setName("Đội Việt Nam");

        Team teamAway = new Team();
        ArrayList<Player> players2 = new ArrayList<Player>(22);
        for (int i = 0; i < 15; i++) {
            Player player = new Player();
            player.setFullname("Trần Văn " + i);
            players2.add(player);
        }
        teamAway.setPlayers(players2);
        teamAway.setName("Đội Thái Lan");

        this.match = new KnockoutMatch(teamHome, teamAway);

        this.teamInMatch = new TeamInMatch(teamHome);

        this.playerInMatch = this.teamInMatch.getPlayingPlayers().get(0);
    }

    @Test
    public void shouldIncrementGoalPlayer() {
        OwnGoalEvent ownGoalEvent = new OwnGoalEvent(this.match, this.playerInMatch, 15);
        ownGoalEvent.handle();
        Assert.assertTrue(this.playerInMatch.getGoalCount() == 0);
    }

    @Test
    public void shouldIncrementAgainstGoalTeam() {
        OwnGoalEvent ownGoalEvent = new OwnGoalEvent(this.match, this.playerInMatch, 15);
        ownGoalEvent.handle();
        ownGoalEvent = new OwnGoalEvent(this.match, this.playerInMatch, 30);
        ownGoalEvent.handle();
        Assert.assertTrue(this.teamInMatch.getGoalAgainst() == 2);
    }

    @Test
    public void shouldIncrementGoalForOpponentTeam() {
        OwnGoalEvent ownGoalEvent = new OwnGoalEvent(this.match, this.playerInMatch, 15);
        ownGoalEvent.handle();
        ownGoalEvent = new OwnGoalEvent(this.match, this.playerInMatch, 30);
        ownGoalEvent.handle();
        ownGoalEvent = new OwnGoalEvent(this.match, this.playerInMatch, 45);
        ownGoalEvent.handle();
        Assert.assertTrue(this.match.getOpponentTeam(this.playerInMatch.getTeamInMatch()).getGoalFor() == 3);
    }

    @Test(expected = InvalidParameterException.class)
    public void checkHandleCheckException() {
        PlayerInMatch benchPlayer = this.teamInMatch.getBenchPlayers().get(0);
        OwnGoalEvent ownGoalEvent = new OwnGoalEvent(this.match, benchPlayer, 15);
        ownGoalEvent.handle();
    }
}
