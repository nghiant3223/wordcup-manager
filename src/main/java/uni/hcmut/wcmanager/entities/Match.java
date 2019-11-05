package uni.hcmut.wcmanager.entities;

import uni.hcmut.wcmanager.enums.MatchType;
import uni.hcmut.wcmanager.enums.RoundName;
import uni.hcmut.wcmanager.randomizers.EventGenerator;
import uni.hcmut.wcmanager.utils.MatchUtils;

import javax.persistence.*;

@Entity
@Table(name = "matches")
public class Match {
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

    private transient RoundName roundName;
    private transient MatchType matchType;
    private transient TeamInMatch homeTeam;
    private transient TeamInMatch awayTeam;
    private transient boolean isFinished;

    public Match(Team home, Team away, RoundName roundName) {
        this.isFinished = false;

        this.roundName = roundName;
        this.matchType = MatchUtils.getType(roundName);

        homeTeam = new TeamInMatch(home);
        awayTeam = new TeamInMatch(away);
    }

    public void start(EventGenerator eventGenerator) {
        eventGenerator.startMatch(this);
        finish();
    }

    private void finish() {
        System.out.println(String.format("%s %d - %d %s",
                homeTeam.getTeam().getName(), homeTeam.getGoalFor(),
                awayTeam.getGoalFor(), awayTeam.getTeam().getName()));
    }

    public TeamInMatch getWinner() {
        return null;
    }


    public void handleEvent(Event e) {
        e.handle();
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public TeamInMatch getHomeTeam() {
        return homeTeam;
    }

    public TeamInMatch getAwayTeam() {
        return awayTeam;
    }

    public TeamInMatch getOpponentTeam(PlayerInMatch player) {
        if (player.getTeamInMatch() == homeTeam) {
            return awayTeam;
        }

        return homeTeam;
    }
}
