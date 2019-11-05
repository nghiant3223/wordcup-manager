package uni.hcmut.wcmanager.entities;

public class PlayerInMatch {
    private Player player;
    private TeamInMatch teamInMatch;

    private int redCardCount;
    private int yellowCardCount;
    private int goalCount;

    public PlayerInMatch(Player player, TeamInMatch teamInMatch) {
        this.player = player;
        this.teamInMatch = teamInMatch;

        this.redCardCount = 0;
        this.yellowCardCount = 0;
        this.goalCount = 0;
    }

    public int getRedCardCount() {
        return redCardCount;
    }

    public void incrementRedCount() {
        this.redCardCount = this.redCardCount + 1;
    }

    public int getYellowCardCount() {
        return yellowCardCount;
    }

    public void incrementYellowCard() {
        this.yellowCardCount = this.yellowCardCount + 1;
    }

    public int getGoalCount() {
        return goalCount;
    }

    public void incrementGoalCount() {
        this.goalCount = this.goalCount + 1;
        this.player.setGoalCount(this.player.getGoalCount() + 1);
    }

    public TeamInMatch getTeamInMatch() {
        return teamInMatch;
    }
}
