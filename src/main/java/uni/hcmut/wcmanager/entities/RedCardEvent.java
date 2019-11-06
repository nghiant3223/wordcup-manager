package uni.hcmut.wcmanager.entities;

public class RedCardEvent extends CardEvent {
    public RedCardEvent(Match match, PlayerInMatch actor, int at) {
        super(match, actor, at);
    }

    @Override
    public void handle() {
        actor.incrementRedCardCount();

        TeamInMatch playersTeam = actor.getTeamInMatch();
        playersTeam.incrementRedCardCount();
        playersTeam.sendPlayerOff(actor);

        afterRedCardEvent();
    }

}
