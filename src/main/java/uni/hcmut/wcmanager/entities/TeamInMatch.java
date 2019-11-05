package uni.hcmut.wcmanager.entities;

import java.util.Set;

public class TeamInMatch {
    private Team team;

    private Set<PlayerInMatch> startingPlayers;
    private Set<PlayerInMatch> substitutePlayers;
    private Set<PlayerInMatch> sentOffPlayers;

    private int remainingSubstitution;
    private int goalFor;
    private int goalAgainst;

    public TeamInMatch(Team team) {
        this.team = team;

        this.goalFor = 0;
        this.goalAgainst = 0;
        this.remainingSubstitution = 3;

        // TODO: Init startingPlayers, substitutePlayers, sentOffPlayers
    }

    public Team getTeam() {
        return this.team;
    }

    public void substitute(PlayerInMatch out, PlayerInMatch in) {
        startingPlayers.remove(out);
        substitutePlayers.add(in);
    }

    public boolean isAbleToSubstitute() {
        return this.remainingSubstitution > 0;
    }

    public void score(Player player) {
        this.goalFor = this.goalFor + 1;
    }

    public void concede(Player player) {
        this.goalAgainst = this.goalAgainst + 1;
    }
}
