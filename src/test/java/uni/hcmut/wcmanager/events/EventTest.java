package uni.hcmut.wcmanager.events;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uni.hcmut.wcmanager.entities.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class EventTest {
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

        this.playerInMatch = new PlayerInMatch(players1.get(0), teamInMatch);
    }

    @Test
    public void checkGetAt() {
        GoalEvent goalEvent = new GoalEvent(this.match, this.playerInMatch, 15);
        Assert.assertTrue(goalEvent.getAt() == 15);
    }

    @Test
    public void checkGetActor() {
        GoalEvent goalEvent = new GoalEvent(this.match, this.playerInMatch, 15);
        Assert.assertTrue(goalEvent.getActor().getTeamInMatch().getTeam().getName().equals("Đội Việt Nam"));
        Assert.assertTrue(goalEvent.getActor().getTeamInMatch().getTeam().getPlayers().get(0).getFullname().equals("Nguyễn Văn 0"));
    }

    @Test
    public void checkGetMatch() {
        GoalEvent goalEvent = new GoalEvent(this.match, this.playerInMatch, 15);
        Assert.assertTrue(goalEvent.getMatch().getHomeTeam().getTeam().getName().equals("Đội Việt Nam"));
        Assert.assertTrue(goalEvent.getMatch().getAwayTeam().getTeam().getName().equals("Đội Thái Lan"));
    }

    @Test(expected = InvalidParameterException.class)
    public void checkExceptionTime() {
        GoalEvent goalEvent = new GoalEvent(this.match, this.playerInMatch, 140);
    }
}
