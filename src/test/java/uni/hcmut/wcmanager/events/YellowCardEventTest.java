package uni.hcmut.wcmanager.events;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uni.hcmut.wcmanager.constants.GameRule;
import uni.hcmut.wcmanager.entities.*;
import uni.hcmut.wcmanager.enums.GroupName;
import uni.hcmut.wcmanager.events.SubstitutionEvent;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class YellowCardEventTest {
    Team TeamHome = new Team();
    Team TeamAway = new Team();
    Team TeamAnother =  new Team();
    Player player = new Player();
    List<Player> playerHome = new ArrayList<Player>();
    List<Player> playerAway = new ArrayList<Player>();
    List<Player> playerAnother = new ArrayList<Player>();
    DbMatch matchDB = new DbMatch();
    DrawableMatch matchDraw;
    KnockoutMatch matchKnockout;
    Group group = new Group(GroupName.A);

    @Before
    public void init(){
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

            Player player3 = new Player();
            String fullNameAnother = "TeamAnother" + Integer.toString(index);
            player3.setFullname(fullNameAnother);
            player3.setGoalCount(index%3);
            playerAnother.add(player3);
        }

        TeamHome.setName("Thai Lan");
        TeamAway.setName("Viet Nam");

        TeamHome.setPlayers(playerHome);
        TeamAway.setPlayers(playerAway);
        TeamAnother.setPlayers(playerAnother);
        group.addTeam(TeamHome);
        group.addTeam(TeamAway);

        matchKnockout = new KnockoutMatch(TeamHome, TeamAway);
    }

    @Test(expected = InvalidParameterException.class)
    public void shouldInvalidParameterException1(){
        TeamInMatch team = new TeamInMatch(TeamHome);
        new PlayerInMatch(player, team);
        PlayerInMatch actor;
        actor = team.getPlayingPlayers().get(1);
        team.sendPlayerOff(actor);
        YellowCardEvent event = new YellowCardEvent(matchKnockout, actor, 82);
        event.handle();
    }

    @Test(expected = InvalidParameterException.class)
    public void shouldInvalidParameterException2(){
        TeamInMatch team = new TeamInMatch(TeamHome);
        new PlayerInMatch(player, team);
        PlayerInMatch actor;
        actor = team.getPlayingPlayers().get(1);
        YellowCardEvent event = new YellowCardEvent(matchKnockout, actor, 62);
        event.handle();
        event = new YellowCardEvent(matchKnockout, actor, 83);
        event.handle();
        event = new YellowCardEvent(matchKnockout, actor, 83);
        event.handle();
    }

    @Test
    public void shouldGetDoubleYellowCard() {
        PlayerInMatch actor;
        actor = matchKnockout.getHomeTeam().getPlayingPlayers().get(1);
        YellowCardEvent yellowCardEvent = new YellowCardEvent(matchKnockout, actor, 82);
        yellowCardEvent.handle();
        SubstitutionEvent substitutionEvent = new SubstitutionEvent(matchKnockout, actor, 87);
        yellowCardEvent = new YellowCardEvent(matchKnockout, actor, 89);
        yellowCardEvent.handle();
        PlayerInMatch actual = matchKnockout.getHomeTeam().getSentOffPlayers().get(0);
        Assert.assertEquals(actor, actual);
    }

    @Test(expected = InvalidParameterException.class)
    public void shouldInvalidParameterException3(){
        PlayerInMatch actor;
        actor = new PlayerInMatch(player, new TeamInMatch(TeamAnother));
        YellowCardEvent event = new YellowCardEvent(matchKnockout, actor, 62);
        event.handle();
    }

    @Test
    public void shouldIncreaseYellowCardOfTeam() {
        TeamInMatch team = new TeamInMatch(TeamHome);
        new PlayerInMatch(player, team);
        PlayerInMatch actor;
        actor = team.getPlayingPlayers().get(1);
        YellowCardEvent event = new YellowCardEvent(matchKnockout, actor, 82);
        event.handle();
        int countYellowCard = actor.getYellowCardCount();
        Assert.assertEquals(1, countYellowCard);
    }

    @Test
    public void shouldReturnTruePlayerReceiveYellowCard() {
        TeamInMatch team = new TeamInMatch(TeamHome);
        new PlayerInMatch(player, team);
        PlayerInMatch actor;
        actor = team.getPlayingPlayers().get(1);
        YellowCardEvent event = new YellowCardEvent(matchKnockout, actor, 82);
        event.handle();
        event = new YellowCardEvent(matchKnockout, actor, 82);
        event.handle();
        PlayerInMatch actual = team.getSentOffPlayers().get(0);
        Assert.assertEquals(actor, actual);
    }

    @Test
    public void shouldReturnTrueQuantityOfYellowCard() {
        int initSize = matchKnockout.getHomeTeam().getYellowCardCount();
        for (int i = 0; i < 5; i++) {
            PlayerInMatch actor;
            actor = matchKnockout.getHomeTeam().getPlayingPlayers().get(0);
            YellowCardEvent event = new YellowCardEvent(matchKnockout, actor, 60 + i*3);
            event.handle();
        }
        int actual = matchKnockout.getHomeTeam().getYellowCardCount();
        Assert.assertEquals(initSize + 5, actual);
    }
}