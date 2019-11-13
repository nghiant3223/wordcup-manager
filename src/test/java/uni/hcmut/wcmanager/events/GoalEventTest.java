package uni.hcmut.wcmanager.events;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uni.hcmut.wcmanager.entities.*;

import java.util.ArrayList;

public class GoalEventTest {

    private GoalEvent goalEvent;

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

        Match match = new KnockoutMatch(teamHome, teamAway);

        TeamInMatch teamInMatch = new TeamInMatch(teamHome);
        PlayerInMatch playerInMatch = new PlayerInMatch(players1.get(0), teamInMatch);

        this.goalEvent = new GoalEvent(match, playerInMatch, 15);
    }
}
