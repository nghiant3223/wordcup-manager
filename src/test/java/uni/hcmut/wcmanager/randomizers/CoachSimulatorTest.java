package uni.hcmut.wcmanager.randomizers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uni.hcmut.wcmanager.constants.GameRule;
import uni.hcmut.wcmanager.constants.MatchRule;
import uni.hcmut.wcmanager.entities.Team;
import uni.hcmut.wcmanager.entities.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CoachSimulatorTest {
    private Team team;

    @Before
    public void init() {
        team = new Team();
        team.setPlayers(new ArrayList<>());

        for (int i = 0; i < GameRule.TEAM_PLAYER_COUNT; i++) {
            Player p = new Player();
            p.setFullname(Integer.toString(i));
            team.getPlayers().add(p);
        }
    }

    @Test
    public void shouldTeamHas11StartingAnd5Substitute() {
        List<ArrayList<Player>> lineup = CoachSimulator.presentMatchLineup(team);
        ArrayList<Player> starting = lineup.get(0);
        ArrayList<Player> substitute = lineup.get(1);

        Assert.assertTrue(starting.size() <= MatchRule.MAX_STARTING_PLAYER_COUNT);
        Assert.assertTrue(starting.size() >= MatchRule.MIN_STARTING_PLAYER_COUNT);
        Assert.assertTrue(substitute.size() <= MatchRule.MAX_SUBSTITUTE_PLAYER_COUNT);
    }

    @Test
    public void shouldTeamLineupHasUniquePlayer() {
        List<ArrayList<Player>> lineup = CoachSimulator.presentMatchLineup(team);
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
