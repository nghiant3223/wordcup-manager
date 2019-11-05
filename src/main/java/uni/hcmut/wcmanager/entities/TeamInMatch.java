package uni.hcmut.wcmanager.entities;

import uni.hcmut.wcmanager.randomizers.TeamRandomizer;

import java.util.ArrayList;
import java.util.List;

public class TeamInMatch {
    private Team team;

    private List<PlayerInMatch> startingPlayers;
    private List<PlayerInMatch> substitutePlayers;
    private List<PlayerInMatch> sentOffPlayers;

    private int remainingSubstitution;
    private int goalFor;
    private int goalAgainst;
    private int yellowCardCount;
    private int redCardCount;

    public TeamInMatch(Team team) {
        this.team = team;

        this.yellowCardCount = 0;
        this.redCardCount = 0;
        this.goalFor = 0;
        this.goalAgainst = 0;
        this.remainingSubstitution = 3;

        this.sentOffPlayers = new ArrayList<>();
        this.startingPlayers = new ArrayList<>();
        this.substitutePlayers = new ArrayList<>();

        List<ArrayList<Player>> lineup = TeamRandomizer.createLineup(team);

        for (Player p : lineup.get(0)) {
            PlayerInMatch pim = new PlayerInMatch(p, this);
            this.startingPlayers.add(pim);
        }

        for (Player p : lineup.get(1)) {
            PlayerInMatch pim = new PlayerInMatch(p, this);
            this.substitutePlayers.add(pim);
        }
    }

    public Team getTeam() {
        return team;
    }

    public void substitute(PlayerInMatch out, PlayerInMatch in) {
        startingPlayers.remove(out);
        substitutePlayers.add(in);
    }

    public boolean isAbleToSubstitute() {
        return remainingSubstitution > 0;
    }

    public void score() {
        goalFor = goalFor + 1;
    }

    public void concede() {
        goalAgainst = goalAgainst + 1;
    }

    public List<PlayerInMatch> getStartingPlayers() {
        return startingPlayers;
    }

    public List<PlayerInMatch> getSubstitutePlayers() {
        return substitutePlayers;
    }

    public List<PlayerInMatch> getSentOffPlayers() {
        return sentOffPlayers;
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
}
