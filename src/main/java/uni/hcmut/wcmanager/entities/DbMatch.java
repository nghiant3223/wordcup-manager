package uni.hcmut.wcmanager.entities;

import com.sun.istack.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "matches")
public class DbMatch {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "home_id")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_id")
    private Team awayTeam;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Team winnerTeam;

    @Column(name = "round_id")
    private int roundId;

    @Column(name = "home_result")
    private int homeResult;

    @Column(name = "away_result")
    private int awayResult;

    @Column(name = "away_penalty")
    private int awayPenalty;

    @Column(name = "home_penalty")
    private int homePenalty;

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Team getWinnerTeam() {
        return winnerTeam;
    }

    public void setWinnerTeam(Team winnerTeam) {
        this.winnerTeam = winnerTeam;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getHomeResult() {
        return homeResult;
    }

    public void setHomeResult(int homeResult) {
        this.homeResult = homeResult;
    }

    public int getAwayResult() {
        return awayResult;
    }

    public void setAwayResult(int awayResult) {
        this.awayResult = awayResult;
    }

    public int getAwayPenalty() {
        return awayPenalty;
    }

    public void setAwayPenalty(int awayPenalty) {
        this.awayPenalty = awayPenalty;
    }

    public int getHomePenalty() {
        return homePenalty;
    }

    public void setHomePenalty(int homePenalty) {
        this.homePenalty = homePenalty;
    }

    public static DbMatch fromMatch(Match match) {
        DbMatch dbMatch = new DbMatch();

        TeamInMatch homeTeam = match.getHomeTeam();
        TeamInMatch awayTeam = match.getAwayTeam();
        TeamInMatch winner = match.getWinner();

        dbMatch.setHomeTeam(homeTeam.getTeam());
        dbMatch.setHomeTeam(awayTeam.getTeam());

        dbMatch.setHomeResult(homeTeam.getGoalFor());
        dbMatch.setAwayResult(awayTeam.getGoalFor());

        if (match.getPenaltyResult() != null) {
            dbMatch.setHomePenalty(homeTeam.getPenaltyShootScore());
            dbMatch.setAwayPenalty(awayTeam.getPenaltyShootScore());
        }

        if (match.getRoundName() != null) {
            dbMatch.setRoundId(match.getRoundName().getId());
        }

        if (winner != null) {
            dbMatch.setWinnerTeam(winner.getTeam());
        }

        return dbMatch;
    }
}
