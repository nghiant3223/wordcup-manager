package uni.hcmut.wcmanager.entities;

import java.util.Date;

public class InjuryEvent extends Event {
    public InjuryEvent(Match match, PlayerInMatch actor, Date at) {
        super(match, actor, at);
    }

    @Override
    public void handle() {

    }
}
