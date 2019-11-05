package uni.hcmut.wcmanager.entities;

import javax.persistence.*;

@Entity
@Table(name = "group_performances")
public class TeamPerformanceInGroup {
    @Id
    @Column(name = "team_id")
    private int teamId;

    @Column(name = "group_id")
    private int groupId;

    @Column(name = "match_played")
    private int matchPlayed;

    @Column(name = "goal_for")
    private int goalFor;

    @Column(name = "goal_against")
    private int goalAgainst;

    @Column(name = "win_count")
    private int winCount;

    @Column(name = "draw_count")
    private int drawCount;

    @Column(name = "lost_count")
    private int lostCount;

    private int goalDiff;

    private int score;

    private transient Team team;

    public void update(Match match) {
        // TODO: Update team's performance
    }
}
