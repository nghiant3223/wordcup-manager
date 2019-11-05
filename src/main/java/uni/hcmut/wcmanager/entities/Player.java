package uni.hcmut.wcmanager.entities;

import javax.persistence.*;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "goal_count")
    private int goalCount;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getGoalCount() {
        return goalCount;
    }

    public void setGoalCount(int goalCount) {
        this.goalCount = goalCount;
    }
}
