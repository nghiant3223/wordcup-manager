package uni.hcmut.wcmanager.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uni.hcmut.wcmanager.constants.GameRule;
import uni.hcmut.wcmanager.enums.GroupName;
import uni.hcmut.wcmanager.events.Event;
import uni.hcmut.wcmanager.events.RedCardEvent;
import uni.hcmut.wcmanager.events.YellowCardEvent;
import uni.hcmut.wcmanager.randomizers.EventGenerator;
import uni.hcmut.wcmanager.randomizers.PenaltyShootoutGenerator;

import java.util.ArrayList;
import java.util.List;

public class TeamPerformanceTest {

    Group group = new Group(GroupName.A);
    Team TeamHome = new Team();
    Team TeamAway = new Team();
    List<Player> playerHome = new ArrayList<Player>();
    List<Player> playerAway = new ArrayList<Player>();
    Match match;
    @Before
    public void init() {
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
        group.addTeam(TeamHome);
        group.addTeam(TeamAway);

        match = new DrawableMatch(TeamHome, TeamAway);
    }

    @Test
    public void Test_getTeam(){
        TeamPerformance teamPerformances = new TeamPerformance(TeamHome, group);
        Assert.assertEquals(TeamHome, teamPerformances.getTeam());
    }

    @Test
    public void Test_getWrongTeam(){
        TeamPerformance teamPerformances = new TeamPerformance(TeamHome, group);
        Assert.assertNotEquals(TeamAway, teamPerformances.getTeam());
    }

    @Test
    public void Test_Update_TeamHome(){
        TeamPerformance teamPerformances = new TeamPerformance(TeamHome, group);
        TeamInMatch team1 = new TeamInMatch(TeamHome);
        PlayerInMatch playerInMatch = new PlayerInMatch(playerHome.get(0), team1);
        Event RedCard = new YellowCardEvent(match, playerInMatch, 10);
        List<Event> events = new ArrayList<>();
        events.add(RedCard);
        EventGenerator generator = new EventGenerator(events);
        PenaltyShootoutGenerator shootoutGenerator = new PenaltyShootoutGenerator();
        match.start(generator, shootoutGenerator);
        match.handleEvent(RedCard);
        teamPerformances.update(match);
        Assert.assertEquals(1, teamPerformances.getYellowCard());
    }


}
