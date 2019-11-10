package uni.hcmut.wcmanager.entities;

import uni.hcmut.wcmanager.enums.MatchType;
import uni.hcmut.wcmanager.enums.RoundName;
import uni.hcmut.wcmanager.events.Event;
import uni.hcmut.wcmanager.utils.DbUtils;

import javax.persistence.*;

public abstract class Match {
    protected RoundName roundName;
    protected MatchType matchType;
    protected TeamInMatch homeTeam;
    protected TeamInMatch awayTeam;
    protected boolean isFinished;
    protected TeamInMatch winner;
    protected int[] penaltyResult;

    public Match(Team home, Team away) {
        this.isFinished = false;

        homeTeam = new TeamInMatch(home);
        awayTeam = new TeamInMatch(away);
    }

    public abstract void start();

    protected void finish() {
        DbUtils.persistMatch(this);
    }

    public void handleEvent(Event e) {
        e.handle();
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished() {
        isFinished = true;
    }

    public TeamInMatch getHomeTeam() {
        return homeTeam;
    }

    public TeamInMatch getAwayTeam() {
        return awayTeam;
    }

    public TeamInMatch getWinner() {
        return winner;
    }

    public void setWinner() {
        if (!isFinished) {
            throw new IllegalStateException("Cannot set winner if match's not finished yet");
        }

        if (homeTeam.getGoalFor() > awayTeam.getGoalFor()) {
            winner = homeTeam;
            return;
        } else if (homeTeam.getGoalFor() < awayTeam.getGoalFor()) {
            winner = awayTeam;
            return;
        }

        winner = null;
    }

    public boolean checkTeamCompetesInMatch(Team team) {
        return team == getHomeTeam().getTeam() || team == getAwayTeam().getTeam();
    }

    public TeamInMatch getOpponentTeam(TeamInMatch team) {
        if (team == homeTeam) {
            return awayTeam;
        }

        return homeTeam;
    }

    public void endDueToLackOfPlayers(TeamInMatch loser) {
        TeamInMatch winner = getOpponentTeam(loser);

        winner.setGoalFor(3);
        winner.setGoalAgainst(0);

        loser.setGoalFor(0);
        loser.setGoalAgainst(3);

        setFinished();
    }

    public void setRoundName(RoundName roundName) {
        this.roundName = roundName;
    }

    public RoundName getRoundName() {
        return roundName;
    }

    public int[] getPenaltyResult() {
        return penaltyResult;
    }
}
