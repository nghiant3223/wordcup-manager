package uni.hcmut.wcmanager.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TeamInMatchTest {
    private TeamInMatch teamInMatch;
    private Team team;

    @Before
    public void init() {

        this.team = new Team();
        ArrayList<Player> players = new ArrayList<Player>(22);
        for (int i = 0; i < 15; i++) {
            Player player = new Player();
            player.setFullname("Nguyễn Văn " + i);
            players.add(player);
        }
        this.team.setPlayers(players);
        this.team.setName("Đội A");
        this.teamInMatch = new TeamInMatch(team);
    }

    @Test
    public void checkGetTeam() {
        Team team = teamInMatch.getTeam();
        Assert.assertTrue(team.getName().equals("Đội A"));
    }

    @Test
    public void checkIsAbleToSubstitute() {
        Assert.assertTrue(teamInMatch.isAbleToSubstitute() == true);
    }

    @Test
    public void checkScore() {
        Assert.assertTrue(teamInMatch.getGoalFor() == 0);
        teamInMatch.score();
        Assert.assertTrue(teamInMatch.getGoalFor() == 1);
    }

    @Test
    public void checkConcede() {
        Assert.assertTrue(teamInMatch.getGoalAgainst() == 0);
        teamInMatch.concede();
        Assert.assertTrue(teamInMatch.getGoalAgainst() == 1);
    }

    @Test
    public void checkSetGoalFor() {
        Assert.assertTrue(teamInMatch.getGoalFor() == 0);
        teamInMatch.setGoalFor(3);
        Assert.assertTrue(teamInMatch.getGoalFor() == 3);
    }

    @Test
    public void checkSetGoalAgainst() {
        Assert.assertTrue(teamInMatch.getGoalAgainst() == 0);
        teamInMatch.setGoalAgainst(3);
        Assert.assertTrue(teamInMatch.getGoalAgainst() == 3);
    }

    @Test
    public void checkGetPlayingPlayersForValidNumber() {
        ArrayList<PlayerInMatch> playingPlayers = new ArrayList<PlayerInMatch>(teamInMatch.getPlayingPlayers());
        int size = playingPlayers.size();
        Assert.assertTrue(size >= 7 && size <= 11);
    }

    @Test
    public void checkGetPlayingPlayersForUniquePlayer() {
        ArrayList<PlayerInMatch> playingPlayers = new ArrayList<PlayerInMatch>(teamInMatch.getPlayingPlayers());
        int size = playingPlayers.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                Assert.assertTrue(!playingPlayers.get(i).equals(playingPlayers.get(j)));
            }
        }
    }

    @Test
    public void checkGetStartingPlayersForValidNumber() {
        ArrayList<PlayerInMatch> startingPlayers = new ArrayList<PlayerInMatch>(teamInMatch.getStartingPlayers());
        int size = startingPlayers.size();
        Assert.assertTrue(size >= 7 && size <= 11);
    }

    @Test
    public void checkGetStartingPlayersForUniquePlayer() {
        ArrayList<PlayerInMatch> startingPlayers = new ArrayList<PlayerInMatch>(teamInMatch.getStartingPlayers());
        int size = startingPlayers.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                Assert.assertTrue(!startingPlayers.get(i).equals(startingPlayers.get(j)));
            }
        }
    }

    @Test
    public void checkGetBenchPlayersForValidNumber() {
        ArrayList<PlayerInMatch> benchPlayers = new ArrayList<PlayerInMatch>(teamInMatch.getBenchPlayers());
        int size = benchPlayers.size();
        Assert.assertTrue(size <= 5);
    }

    @Test
    public void checkGetBenchPlayersForUniquePlayer() {
        ArrayList<PlayerInMatch> benchPlayers = new ArrayList<PlayerInMatch>(teamInMatch.getBenchPlayers());
        int size = benchPlayers.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                Assert.assertTrue(!benchPlayers.get(i).equals(benchPlayers.get(j)));
            }
        }
    }

    @Test
    public void checkGetSubstitutePlayersForValidNumber() {
        ArrayList<PlayerInMatch> substitutePlayers = new ArrayList<PlayerInMatch>(teamInMatch.getSubstitutePlayers());
        int size = substitutePlayers.size();
        Assert.assertTrue(size <= 5);
    }

    @Test
    public void checkGetSubstitutePlayersForUniquePlayer() {
        ArrayList<PlayerInMatch> substitutePlayers = new ArrayList<PlayerInMatch>(teamInMatch.getSubstitutePlayers());
        int size = substitutePlayers.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                Assert.assertTrue(!substitutePlayers.get(i).equals(substitutePlayers.get(j)));
            }
        }
    }

    @Test
    public void checkGetSentOffPlayersForValidNumber() {
        ArrayList<PlayerInMatch> sentOffPlayers = new ArrayList<PlayerInMatch>(teamInMatch.getSentOffPlayers());
        int size = sentOffPlayers.size();
        Assert.assertTrue(size <= 4);
    }

    @Test
    public void checkGetSentOffPlayersForUniquePlayer() {
        ArrayList<PlayerInMatch> sentOffPlayers = new ArrayList<PlayerInMatch>(teamInMatch.getSentOffPlayers());
        int size = sentOffPlayers.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                Assert.assertTrue(!sentOffPlayers.get(i).equals(sentOffPlayers.get(j)));
            }
        }
    }

    @Test
    public void checkSubstitutePlayerForRemovePlayingPlayer() {
        ArrayList<PlayerInMatch> playingPlayers = new ArrayList<PlayerInMatch>(teamInMatch.getPlayingPlayers());
        ArrayList<PlayerInMatch> benchPlayers = new ArrayList<PlayerInMatch>(teamInMatch.getBenchPlayers());

        PlayerInMatch out = playingPlayers.get(0);
        PlayerInMatch in = benchPlayers.get(0);

        teamInMatch.substitutePlayer(out, in);

        Assert.assertTrue(teamInMatch.getPlayingPlayers().get(0) != out);
    }

    @Test
    public void checkSubstitutePlayerForAddSentOffPlayer() {
        ArrayList<PlayerInMatch> playingPlayers = new ArrayList<PlayerInMatch>(teamInMatch.getPlayingPlayers());
        ArrayList<PlayerInMatch> benchPlayers = new ArrayList<PlayerInMatch>(teamInMatch.getBenchPlayers());

        PlayerInMatch out = playingPlayers.get(0);
        PlayerInMatch in = benchPlayers.get(0);

        teamInMatch.substitutePlayer(out, in);

        Assert.assertTrue(teamInMatch.getSentOffPlayers().get(teamInMatch.getSentOffPlayers().size() - 1) == out);
    }

    @Test
    public void checkSubstitutePlayerForAddPlayingPlayer() {
        ArrayList<PlayerInMatch> playingPlayers = new ArrayList<PlayerInMatch>(teamInMatch.getPlayingPlayers());
        ArrayList<PlayerInMatch> benchPlayers = new ArrayList<PlayerInMatch>(teamInMatch.getBenchPlayers());

        PlayerInMatch out = playingPlayers.get(0);
        PlayerInMatch in = benchPlayers.get(0);

        teamInMatch.substitutePlayer(out, in);

        Assert.assertTrue(teamInMatch.getPlayingPlayers().get(teamInMatch.getPlayingPlayers().size() -1) == in);
    }

    @Test
    public void checkSubstitutePlayerRemoveBenchPlayer() {
        ArrayList<PlayerInMatch> playingPlayers = new ArrayList<PlayerInMatch>(teamInMatch.getPlayingPlayers());
        ArrayList<PlayerInMatch> benchPlayers = new ArrayList<PlayerInMatch>(teamInMatch.getBenchPlayers());

        PlayerInMatch out = playingPlayers.get(0);
        PlayerInMatch in = benchPlayers.get(0);

        teamInMatch.substitutePlayer(out, in);
        if(teamInMatch.getBenchPlayers().size() > 0) {
            Assert.assertTrue(teamInMatch.getBenchPlayers().get(0) != in);
        }
    }

    @Test
    public void checkSubstitutePlayerForRemainSubstitution() {
        ArrayList<PlayerInMatch> playingPlayers = new ArrayList<PlayerInMatch>(teamInMatch.getPlayingPlayers());
        ArrayList<PlayerInMatch> benchPlayers = new ArrayList<PlayerInMatch>(teamInMatch.getBenchPlayers());

        PlayerInMatch out = playingPlayers.get(0);
        PlayerInMatch in = benchPlayers.get(0);

        teamInMatch.substitutePlayer(out, in);

        Assert.assertEquals(teamInMatch.isAbleToSubstitute(),teamInMatch.getBenchPlayers().size() > 0);
    }

    @Test
    public void checkSendPlayerOffForRemovePlayingPlayer() {
        ArrayList<PlayerInMatch> playingPlayers = new ArrayList<PlayerInMatch>(teamInMatch.getPlayingPlayers());

        PlayerInMatch out = playingPlayers.get(0);

        teamInMatch.sendPlayerOff(out);

        Assert.assertTrue(teamInMatch.getPlayingPlayers().get(0) != out);
    }

    @Test
    public void checkSendPlayerOffForSentOffPlayer() {
        ArrayList<PlayerInMatch> playingPlayers = new ArrayList<PlayerInMatch>(teamInMatch.getPlayingPlayers());

        PlayerInMatch out = playingPlayers.get(0);

        teamInMatch.sendPlayerOff(out);

        Assert.assertTrue(teamInMatch.getSentOffPlayers().get(teamInMatch.getSentOffPlayers().size() - 1) == out);
    }

    @Test
    public void checkGetGoalFor() {
        Assert.assertTrue(teamInMatch.getGoalFor() == 0);
    }

    @Test
    public void checkGetYellowCardCount() {
        Assert.assertTrue(teamInMatch.getYellowCardCount() == 0);
    }

    @Test
    public void checkIncrementYellowCardCount() {
        teamInMatch.incrementYellowCardCount();
        Assert.assertTrue(teamInMatch.getYellowCardCount() == 1);
    }

    @Test
    public void checkGetRedCardCount() {
        Assert.assertTrue(teamInMatch.getRedCardCount() == 0);
    }

    @Test
    public void checkIncrementRedCardCount() {
        teamInMatch.incrementRedCardCount();
        teamInMatch.incrementRedCardCount();
        Assert.assertTrue(teamInMatch.getRedCardCount() == 2);
    }

    @Test
    public void checkGetPenaltyShootScore() {
        Assert.assertTrue(teamInMatch.getPenaltyShootScore() == 0);
    }

    @Test
    public void checkIncrementPenaltyShootScore() {
        teamInMatch.incrementPenaltyShootScore();
        teamInMatch.incrementPenaltyShootScore();
        Assert.assertTrue(teamInMatch.getPenaltyShootScore() == 2);
    }

    @Test
    public void checkInitPenaltyShootoutHistory() {
        teamInMatch.initPenaltyShootoutHistory();
        Assert.assertTrue( teamInMatch.getPenaltyShootoutHistory() != null);
    }

    @Test
    public void checkAddPenaltyShootoutHistory() {
        teamInMatch.initPenaltyShootoutHistory();
        teamInMatch.addPenaltyShootoutHistory(true);
        Assert.assertTrue( teamInMatch.getPenaltyShootoutHistory().get(0) == true);
    }
}