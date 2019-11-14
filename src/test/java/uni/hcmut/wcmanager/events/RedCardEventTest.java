package uni.hcmut.wcmanager.events;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uni.hcmut.wcmanager.entities.*;
import uni.hcmut.wcmanager.events.CardEvent;
import uni.hcmut.wcmanager.events.RedCardEvent;
import uni.hcmut.wcmanager.constants.GameRule;
import uni.hcmut.wcmanager.enums.GroupName;
import uni.hcmut.wcmanager.enums.RoundName;
import uni.hcmut.wcmanager.entities.Player;
import uni.hcmut.wcmanager.entities.PlayerInMatch;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class RedCardEventTest {
    Team TeamHome = new Team();
    Team TeamAway = new Team();
    Player player = new Player();
    List<Player> playerHome = new ArrayList<Player>();
    List<Player> playerAway = new ArrayList<Player>();
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
        }

        TeamHome.setName("Thai Lan");
        TeamAway.setName("Viet Nam");

        TeamHome.setPlayers(playerHome);
        TeamAway.setPlayers(playerAway);
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
        RedCardEvent event = new RedCardEvent(matchKnockout, actor, 82);
        event.handle();
    }

    @Test(expected = InvalidParameterException.class)
    public void shouldInvalidParameterException2(){
        TeamInMatch team = new TeamInMatch(TeamHome);
        new PlayerInMatch(player, team);
        PlayerInMatch actor;
        actor = team.getPlayingPlayers().get(1);
        RedCardEvent event = new RedCardEvent(matchKnockout, actor, 62);
        event.handle();
        event = new RedCardEvent(matchKnockout, actor, 83);
        event.handle();
    }

    @Test
    public void shouldIncreaseRedCardOfTeam() {
        TeamInMatch team = new TeamInMatch(TeamHome);
        new PlayerInMatch(player, team);
        PlayerInMatch actor;
        actor = team.getPlayingPlayers().get(1);
        RedCardEvent event = new RedCardEvent(matchKnockout, actor, 82);
        event.handle();
        int countRedCard = team.getRedCardCount();
        Assert.assertEquals(1, countRedCard);
    }

    @Test
    public void shouldReturnTruePlayerReceiveRedCard() {
        TeamInMatch team = new TeamInMatch(TeamHome);
        new PlayerInMatch(player, team);
        PlayerInMatch actor;
        actor = team.getPlayingPlayers().get(1);
        RedCardEvent event = new RedCardEvent(matchKnockout, actor, 82);
        event.handle();
        PlayerInMatch actual = team.getSentOffPlayers().get(0);
        Assert.assertEquals(actor, actual);
    }

    @Test(expected = InvalidParameterException.class)
    public void shouldReturnInvalidParameterException() {
        //Case: Time > time of event
        TeamInMatch team = new TeamInMatch(TeamHome);
        new PlayerInMatch(player, team);
        PlayerInMatch actor;
        actor = team.getPlayingPlayers().get(1);
        RedCardEvent event = new RedCardEvent(matchKnockout, actor, 200);
        event.handle();
    }

    @Test
    public void shouldReturnTruePlayerReceiveRedCard1() {
        //Case: player in substitute list received red card
        TeamInMatch team = new TeamInMatch(TeamHome);
        new PlayerInMatch(player, team);
        PlayerInMatch actor;
        actor = team.getSubstitutePlayers().get(0);
        RedCardEvent event = new RedCardEvent(matchKnockout, actor, 82);
        event.handle();
        PlayerInMatch actual = team.getSentOffPlayers().get(0);
        Assert.assertEquals(actor, actual);
    }

    @Test
    public void shouldReturnTrueQuantityOfPlayingPlayer() {
        int initSize = matchKnockout.getHomeTeam().getStartingPlayers().size();
        for (int i = 0; i < 5; i++) {
            PlayerInMatch actor;
            actor = matchKnockout.getHomeTeam().getPlayingPlayers().get(0);
            RedCardEvent event = new RedCardEvent(matchKnockout, actor, 60 + i*3);
            event.handle();
        }
        int actual = matchKnockout.getHomeTeam().getPlayingPlayers().size();
        Assert.assertEquals(initSize - 5, actual);
    }

    @Test
    public void shouldReturnTrueWinner() {
        TeamInMatch expected = matchKnockout.getOpponentTeam(matchKnockout.getHomeTeam());
        for (int i = 0; i < 5; i++) {
            PlayerInMatch actor;
            actor = matchKnockout.getHomeTeam().getPlayingPlayers().get(0);
            RedCardEvent event = new RedCardEvent(matchKnockout, actor, 60 + i*3);
            event.handle();
        }
        matchKnockout.setWinner();
        TeamInMatch actual = matchKnockout.getWinner();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldReturnTrueQuantityOfPlayingPlayer2() {
        for (int i = 0; i < 12; i++) {
            PlayerInMatch actor;
            actor = matchKnockout.getHomeTeam().getPlayingPlayers().get(0);
            RedCardEvent event = new RedCardEvent(matchKnockout, actor, 60 + i*3);
            event.handle();
        }
    }
}
