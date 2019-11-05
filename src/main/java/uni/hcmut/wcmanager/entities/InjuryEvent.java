package uni.hcmut.wcmanager.entities;

public class InjuryEvent extends Event {
    public InjuryEvent(Match match, PlayerInMatch actor, int at) {
        super(match, actor, at);
    }

    @Override
    public void handle() {

    }
}
