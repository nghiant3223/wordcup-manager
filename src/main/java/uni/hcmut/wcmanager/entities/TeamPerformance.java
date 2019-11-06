package uni.hcmut.wcmanager.entities;

import javax.persistence.*;

@Entity
@Table(name = "group_performances")
public class TeamPerformance {
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

    private int yellowCard;

    private transient Team team;

    private transient Group group;

    public TeamPerformance(Team team, Group group) {
        this.team = team;
        this.group = group;
    }

    public TeamPerformance() {

    }

    public void update(Match match) {
        TeamInMatch thisTeam;

        if (team == match.getAwayTeam().getTeam()) {
            thisTeam = match.getAwayTeam();
        } else {
            thisTeam = match.getHomeTeam();
        }


        goalFor += thisTeam.getGoalFor();
        goalAgainst += thisTeam.getGoalAgainst();
        yellowCard += thisTeam.getRedCardCount() * 2 + thisTeam.getYellowCardCount();
        goalDiff = goalFor - goalAgainst;
        matchPlayed += 1;

        if (thisTeam.getGoalFor() > thisTeam.getGoalAgainst()) {
            score += 3;
            winCount += 1;
        } else if (thisTeam.getGoalFor() == thisTeam.getGoalAgainst()) {
            score += 1;
            drawCount += 1;
        } else {
            lostCount += 1;
        }
    }

    public Team getTeam() {
        return team;
    }

    public int getMatchPlayed() {
        return matchPlayed;
    }

    public int getGoalFor() {
        return goalFor;
    }

    public int getGoalAgainst() {
        return goalAgainst;
    }

    public int getWinCount() {
        return winCount;
    }

    public int getDrawCount() {
        return drawCount;
    }

    public int getLostCount() {
        return lostCount;
    }

    public int getGoalDiff() {
        return goalDiff;
    }

    public int getScore() {
        return score;
    }

    public int getYellowCard() {
        return yellowCard;
    }

    public Group getGroup() {
        return group;
    }
}
