package uni.hcmut.wcmanager.entities;

public class RedCardEvent extends Event {
    public RedCardEvent(Match match, PlayerInMatch actor, int at) {
        super(match, actor, at);
    }

    @Override
    public void handle() {

    }
}
