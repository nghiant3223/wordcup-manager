package uni.hcmut.wcmanager.entities;

public class GoalEvent extends Event {
    public GoalEvent(Match match, PlayerInMatch actor, int at) {
        super(match, actor, at);
    }

    @Override
    public void handle() {
        actor.getTeamInMatch().score();
        actor.incrementGoalCount();

        TeamInMatch actorOpponentTeam = match.getOpponentTeam(actor);
        actorOpponentTeam.concede();
    }
}
