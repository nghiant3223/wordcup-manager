package uni.hcmut.wcmanager.randomizers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uni.hcmut.wcmanager.entities.Group;
import uni.hcmut.wcmanager.entities.Team;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupStageDrawTest {
    private List<Team> teams;

    @Before
    public void initTeams() {
        teams = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            Team t = new Team();
            t.setName(Integer.toString(i));
            teams.add(t);
        }
    }

    @Test
    public void shouldReturn32Draws() {
        List<Integer> draws = GroupStageDraw.getDraws();
        Assert.assertEquals(draws.size(), 32);
    }

    @Test
    public void shouldGroupHas4Teams() {
        List<Group> groups = GroupStageDraw.draw(teams);
        for (Group g : groups) {
            Assert.assertEquals(g.getTeams().size(), 4);
        }
    }

    @Test(expected = InvalidParameterException.class)
    public void shouldThrowInvalidTeamsParameterException() {
        List<Team> teams = new ArrayList<>();
        GroupStageDraw.draw(teams);
    }

    @Test
    public void shouldGroupHas4UniqueTeams() {
        List<Group> groups = GroupStageDraw.draw(teams);

        for (Group g : groups) {
            Set<Team> teamNames = new HashSet<>();
            for (Team t : g.getTeams()) {

                if (teamNames.contains(t)) {
                    Assert.fail("None unique team");
                }

                teamNames.add(t);
            }
        }
    }
}
