package uni.hcmut.wcmanager.entities;

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

    public void prepareSqlEntity() {
        this.homeId = this.homeTeam.getTeam().getId();
        this.awayId = this.awayTeam.getTeam().getId();
        // TODO: Retrieve match result
    }

    private transient TeamInMatch homeTeam;
    private transient TeamInMatch awayTeam;
    private transient Event[] events;

    public void start() {
        // TODO: Initialization events
    }

    public void finish() {
        // TODO: Determine the winner base on match's events
    }

    public TeamInMatch getWinner() {
        return null;
    }

    public void handleEvent(Event e) {

    }

}
