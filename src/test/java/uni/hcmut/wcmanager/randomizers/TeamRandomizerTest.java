package uni.hcmut.wcmanager.randomizers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uni.hcmut.wcmanager.constants.MatchConstants;
import uni.hcmut.wcmanager.entities.Player;
import uni.hcmut.wcmanager.entities.Team;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TeamRandomizerTest {
    private Team team;

    @Before
    public void init() {
        team = new Team();
        team.setPlayers(new ArrayList<Player>());

        for (int i = 0; i < 23; i++) {
            Player p = new Player();
            p.setFullname(Integer.toString(i));
            team.getPlayers().add(p);
        }
    }

    @Test
    public void shouldTeamHas11StartingAnd5Substitute() {
        List<ArrayList<Player>> lineup = TeamRandomizer.createLineup(team);
        ArrayList<Player> starting = lineup.get(0);
        ArrayList<Player> substitute = lineup.get(1);

        Assert.assertEquals(starting.size(), MatchConstants.STARTING_PLAYER_COUNT);
        Assert.assertEquals(substitute.size(), MatchConstants.SUBSTITUTE_PLAYER_COUNT);
    }

    @Test
    public void shouldTeamLineupHasUniquePlayer() {
        List<ArrayList<Player>> lineup = TeamRandomizer.createLineup(team);
        ArrayList<Player> starting = lineup.get(0);
        ArrayList<Player> substitute = lineup.get(1);
        Set<Player> players = new HashSet<>();

        for (Player p : starting) {
            if (players.contains(p)) {
                Assert.fail("Non unique player");
            }

            players.add(p);
        }

        for (Player p : substitute) {
            if (players.contains(p)) {
                Assert.fail("Non unique player");
            }

            players.add(p);
        }
    }
}
