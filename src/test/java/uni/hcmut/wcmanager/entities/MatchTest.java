package uni.hcmut.wcmanager.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uni.hcmut.wcmanager.constants.GameRule;
import uni.hcmut.wcmanager.enums.RoundName;
import uni.hcmut.wcmanager.entities.Player;
import uni.hcmut.wcmanager.entities.Team;
import uni.hcmut.wcmanager.entities.Match;
import uni.hcmut.wcmanager.entities.TeamInMatch;
import uni.hcmut.wcmanager.enums.MatchType;

import java.util.ArrayList;
import java.util.List;

public class MatchTest {
    Team TeamHome;
    Team TeamAway;
    Match match;
    List<Player> playerHome = new ArrayList<Player>();
    List<Player> playerAway = new ArrayList<Player>();

    @Before
    public void init(){
        TeamHome = new Team();
        TeamAway = new Team();
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
        match = new KnockoutMatch(TeamHome, TeamAway);
    }

    @Test
    public void shouldReturnTrueNameOfHomeTeam1() {
        TeamHome.setName("Viet Nam");
        String actual = match.getHomeTeam().getTeam().getName();
        Assert.assertEquals("Viet Nam", actual);
    }

    @Test
    public void shouldReturnTrueNameOfHomeTeam2() {
        TeamHome.setName("Viet Nam");
        String actual = match.getHomeTeam().getTeam().getName();
        Assert.assertNotEquals("Thai Lan", actual);
    }

    @Test
    public void shouldReturnTrueNameOfAwayTeam1() {
        TeamAway.setName("Thai Lan");
        String actual = match.getAwayTeam().getTeam().getName();
        Assert.assertEquals("Thai Lan", actual);
    }

    @Test
    public void shouldReturnTrueNameOfAwayTeam2() {
        TeamAway.setName("Thai Lan");
        String actual = match.getAwayTeam().getTeam().getName();
        Assert.assertNotEquals("Viet Nam", actual);
    }

    @Test
    public void shouldReturnTrueNameOfOpponentTeam1() {
        TeamHome.setName("Viet Nam");
        TeamAway.setName("Thai Lan");
        String actual = match.getOpponentTeam(match.getHomeTeam()).getTeam().getName();
        Assert.assertEquals("Thai Lan", actual);
    }

    @Test
    public void shouldReturnTrueNameOfOpponentTeam2() {
        TeamHome.setName("Viet Nam");
        TeamAway.setName("Thai Lan");
        String actual = match.getOpponentTeam(match.getHomeTeam()).getTeam().getName();
        Assert.assertNotEquals("Viet Nam", actual);
    }

    @Test
    public void shouldSetTrueRound() {
        RoundName round = RoundName.QUARTER_FINAL_ROUND;
        match.setRoundName(round);
        RoundName actual = match.roundName;
        Assert.assertEquals(3, actual.getId());
    }

    @Test
    public void shouldReturnTrueRoundName() {
        RoundName round = RoundName.QUARTER_FINAL_ROUND;
        match.setRoundName(round);
        RoundName actual = match.getRoundName();
        Assert.assertEquals(round.getId(), actual.getId());
    }

    @Test
    public void shouldCheckTeamCompetesInMatch1() {
        //Case: True
        TeamHome.setName("Viet Nam");
        Boolean actual = match.checkTeamCompetesInMatch(TeamHome);
        Assert.assertEquals(true, actual);
    }

    @Test
    public void shouldCheckTeamCompetesInMatch2() {
        //Case: False
        Team anyTeam = new Team();
        Boolean actual = match.checkTeamCompetesInMatch(anyTeam);
        Assert.assertEquals(false, actual);
    }

    @Test
    public void shouldEndDueToLackOfPlayers1() {
        //Case: Check return true winner vs loser
        TeamAway.setName("Thai Lan");
        TeamHome.setName("Viet Nam");
        TeamInMatch loser = new TeamInMatch(TeamAway);
        match.endDueToLackOfPlayers(loser);
        match.setWinner();
        String actual = match.getWinner().getTeam().getName() + "-" + match.getOpponentTeam(match.getWinner()).getTeam().getName();
        Assert.assertEquals("Viet Nam-Thai Lan", actual);
    }

    @Test
    public void shouldEndDueToLackOfPlayers2() {
        //Case: Check return true goals of winner vs loser
        TeamAway.setName("Thai Lan");
        TeamHome.setName("Viet Nam");
        TeamInMatch loser = new TeamInMatch(TeamAway);
        loser.setGoalFor(5);
        match.endDueToLackOfPlayers(loser);
        match.setWinner();
        String actual = match.getWinner().getGoalFor() + "-" + match.getOpponentTeam(match.getWinner()).getGoalFor();
        Assert.assertEquals("3-0", actual);
    }
}
