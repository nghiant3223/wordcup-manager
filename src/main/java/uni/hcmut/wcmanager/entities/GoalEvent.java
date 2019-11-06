package uni.hcmut.wcmanager.entities;

public class GoalEvent extends Event {
    public GoalEvent(Match match, PlayerInMatch actor, int at) {
        super(match, actor, at);
    }

    @Override
    public void handle() {
        actor.incrementGoalCount();

        TeamInMatch playersTeam = actor.getTeamInMatch();
        playersTeam.score();

        TeamInMatch playersOpponentTeam = match.getOpponentTeam(playersTeam);
        playersOpponentTeam.concede();
    }
}
