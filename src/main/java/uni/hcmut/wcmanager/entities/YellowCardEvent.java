package uni.hcmut.wcmanager.entities;

import java.util.Date;

public class YellowCardEvent extends Event {
    public YellowCardEvent(Match match, PlayerInMatch actor, Date at) {
        super(match, actor, at);
    }

    @Override
    public void handle() {

    }
}
