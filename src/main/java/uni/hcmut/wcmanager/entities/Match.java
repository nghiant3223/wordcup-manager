package uni.hcmut.wcmanager.entities;

import uni.hcmut.wcmanager.enums.MatchType;
import uni.hcmut.wcmanager.enums.RoundName;
import uni.hcmut.wcmanager.events.Event;

import javax.persistence.*;

@Entity
@Table(name = "matches")
public abstract class Match {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "home_id")
    private int homeId;

    @Column(name = "away_id")
    private int awayId;

    @Column(name = "home_result")
    private int homeResult;

    @Column(name = "away_result")
    private int awayResult;

    @Column(name = "away_penalty")
    private int awayPenalty;

    @Column(name = "home_penalty")
    private int homePenalty;

    @Column(name = "winner_id")
    private int winnerId;

    @Column(name = "round_id")
    private int roundId;

    public Match() {

    }

    public void prepareSqlEntity() {
        this.homeId = this.homeTeam.getTeam().getId();
        this.awayId = this.awayTeam.getTeam().getId();
        this.roundId = this.roundName.getId();
        // TODO: Prepare record to be saved on database
    }

    protected transient RoundName roundName;
    protected transient MatchType matchType;
    protected transient TeamInMatch homeTeam;
    protected transient TeamInMatch awayTeam;
    protected transient boolean isFinished;
    protected transient TeamInMatch winner;
    protected transient int[] penaltyResult;

    public Match(Team home, Team away, RoundName roundName) {
        this.isFinished = false;

        this.roundName = roundName;
        homeTeam = new TeamInMatch(home);
        awayTeam = new TeamInMatch(away);
    }

    public abstract void start();

    protected abstract void finish();

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
}
