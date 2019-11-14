package uni.hcmut.wcmanager.events;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uni.hcmut.wcmanager.entities.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class SubstituteEventTest {
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
    public void checkHandleCheckException() {
        PlayerInMatch benchPlayer = this.teamInMatch.getBenchPlayers().get(0);
        SubstitutionEvent substituteEvent = new SubstitutionEvent(this.match, benchPlayer, 15);
        substituteEvent.handle();
    }

    @Test
    public void checkHandleForAbleSubstitute() {
        PlayerInMatch playingPlayer = this.teamInMatch.getPlayingPlayers().get(0);
        SubstitutionEvent substituteEvent = new SubstitutionEvent(this.match, playingPlayer, 15);
        substituteEvent.handle();

        Assert.assertTrue(playingPlayer.getTeamInMatch().getSentOffPlayers().contains(playingPlayer));
    }
}
