package uni.hcmut.wcmanager.entities;

import java.util.Date;

public class RedCardEvent extends Event {
    public RedCardEvent(Match match, PlayerInMatch actor, Date at) {
        super(match, actor, at);
    }

    @Override
    public void handle() {

    }
}
