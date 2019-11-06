package uni.hcmut.wcmanager.entities;

public class YellowCardEvent extends Event {
    public YellowCardEvent(Match match, PlayerInMatch actor, int at) {
        super(match, actor, at);
    }

    @Override
    public void handle() {
        actor.incrementYellowCard();
        // TODO: Check if this actor receive 2 yellow card
        actor.getTeamInMatch().incrementYellowCardCount();
    }
}
