package uni.hcmut.wcmanager.entities;

import uni.hcmut.wcmanager.constants.GameRule;
import uni.hcmut.wcmanager.randomizers.CoachSimulator;

import java.util.ArrayList;
import java.util.List;

public class TeamInMatch {
    private Team team;

    private List<PlayerInMatch> startingPlayers;
    private List<PlayerInMatch> substitutePlayers;
    private List<PlayerInMatch> playingPlayers;
    private List<PlayerInMatch> benchPlayers;
    private List<PlayerInMatch> sentOffPlayers;

    private List<Boolean> penaltyShootoutHistory;

    private int penaltyShootScore;
    private int remainingSubstitution;
    private int goalFor;
    private int goalAgainst;
    private int yellowCardCount;
    private int redCardCount;

    public TeamInMatch(Team team) {
        this.team = team;

        this.goalFor = 0;
        this.goalAgainst = 0;
        this.redCardCount = 0;
        this.yellowCardCount = 0;
        this.penaltyShootScore = 0;
        this.remainingSubstitution = GameRule.AVAILABLE_SUBSTITUTION_COUNT;

        this.sentOffPlayers = new ArrayList<>();
        this.startingPlayers = new ArrayList<>();
        this.substitutePlayers = new ArrayList<>();
        this.playingPlayers = new ArrayList<>();
        this.benchPlayers = new ArrayList<>();

        List<ArrayList<Player>> lineup = CoachSimulator.presentMatchLineup(team);

        for (Player p : lineup.get(0)) {
            PlayerInMatch pim = new PlayerInMatch(p, this);
            this.startingPlayers.add(pim);
            this.playingPlayers.add(pim);
        }

        for (Player p : lineup.get(1)) {
            PlayerInMatch pim = new PlayerInMatch(p, this);
            this.substitutePlayers.add(pim);
            this.benchPlayers.add(pim);
        }
    }

    public Team getTeam() {
        return team;
    }

    public void substitutePlayer(PlayerInMatch out, PlayerInMatch in) {
        // Remove `out` player from list of playing players
        playingPlayers.remove(out);

        // Once substituted, player cannot return to play, so he is sent off the field.
        sentOffPlayers.add(out);

        // Add `in` player to list of playing players
        playingPlayers.add(in);
        // Remove `in` player from list of players sitting on bench
        benchPlayers.remove(in);

        // Reduce #substitution available
        remainingSubstitution -= 1;
    }

    public boolean isAbleToSubstitute() {
        return remainingSubstitution > 0 && benchPlayers.size() > 0;
    }

    public void score() {
        goalFor = goalFor + 1;
    }

    public void concede() {
        goalAgainst = goalAgainst + 1;
    }

    public void setGoalFor(int goalFor) {
        this.goalFor = goalFor;
    }

    public void setGoalAgainst(int goalAgainst) {
        this.goalAgainst = goalAgainst;
    }

    public List<PlayerInMatch> getPlayingPlayers() {
        return playingPlayers;
    }

    public List<PlayerInMatch> getStartingPlayers() {
        return startingPlayers;
    }

    public List<PlayerInMatch> getBenchPlayers() {
        return benchPlayers;
    }

    public List<PlayerInMatch> getSubstitutePlayers() {
        return substitutePlayers;
    }

    public List<PlayerInMatch> getSentOffPlayers() {
        return sentOffPlayers;
    }

    public void sendPlayerOff(PlayerInMatch player) {
        playingPlayers.remove(player);
        sentOffPlayers.add(player);
    }

    public int getGoalFor() {
        return goalFor;
    }

    public int getGoalAgainst() {
        return goalAgainst;
    }

    public int getYellowCardCount() {
        return yellowCardCount;
    }

    public void incrementYellowCardCount() {
        this.yellowCardCount += 1;
    }

    public int getRedCardCount() {
        return redCardCount;
    }

    public void incrementRedCardCount() {
        this.redCardCount += 1;
    }

    public int getPenaltyShootScore() {
        return penaltyShootScore;
    }

    public void incrementPenaltyShootScore() {
        penaltyShootScore += 1;
    }

    public List<Boolean> getPenaltyShootoutHistory() {
        return penaltyShootoutHistory;
    }

    public void initPenaltyShootoutHistory() {
        penaltyShootoutHistory = new ArrayList<>();
    }

    public void addPenaltyShootoutHistory(boolean in) {
        penaltyShootoutHistory.add(in);
    }
}
