package uni.hcmut.wcmanager.events;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uni.hcmut.wcmanager.entities.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class RedCardEventTest {
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

    @Test(expected = InvalidParameterException.class)
    public void checkHandleException() {
        CardEvent cardEvent = new RedCardEvent(this.match, this.playerInMatch, 15);
        cardEvent.handle();
        cardEvent.handle();
    }

    @Test
    public void checkHandleForIncreaseRedCardCountForPlayer() {
        CardEvent cardEvent = new RedCardEvent(this.match, this.playerInMatch, 15);
        cardEvent.handle();
        Assert.assertTrue(playerInMatch.getRedCardCount() == 1);
    }

    @Test
    public void checkHandleForIncreaseRedCardCountForTeamInMatch() {
        CardEvent cardEvent = new RedCardEvent(this.match, this.playerInMatch, 15);
        cardEvent.handle();
        Assert.assertTrue(teamInMatch.getRedCardCount() == 1);
    }

    @Test
    public void checkHandleForSentOffPlayer() {
        CardEvent cardEvent = new RedCardEvent(this.match, this.playerInMatch, 15);
        cardEvent.handle();
        Assert.assertTrue(teamInMatch.getSentOffPlayers().contains(playerInMatch));
    }

    @Test
    public void checkAfterRedCard() {
        CardEvent cardEvent = new RedCardEvent(this.match, this.playerInMatch, 15);
        cardEvent.handle();
        if (playerInMatch.getTeamInMatch().getPlayingPlayers().size() < 7) {
            Assert.assertTrue(match.getWinner() == match.getOpponentTeam(teamInMatch));
        } else {
            Assert.assertTrue(true);
        }
    }
}
