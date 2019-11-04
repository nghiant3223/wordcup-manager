package uni.hcmut.wcmanager.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "fullname")
    private int fullname;

    @Column(name = "goal_count")
    private int goalCount;

    @Column(name = "red_card_count")
    private int redCardCount;

    @Column(name = "yel_card_count")
    private int yellowCardCount;

    public int getGoalCount() {
        return goalCount;
    }

    public void setGoalCount(int goalCount) {
        this.goalCount = goalCount;
    }

    public int getRedCardCount() {
        return redCardCount;
    }

    public void setRedCardCount(int redCardCount) {
        this.redCardCount = redCardCount;
    }

    public int getYellowCardCount() {
        return yellowCardCount;
    }

    public void setYellowCardCount(int yellowCardCount) {
        this.yellowCardCount = yellowCardCount;
    }
}
