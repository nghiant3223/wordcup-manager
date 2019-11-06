package uni.hcmut.wcmanager.entities;

import uni.hcmut.wcmanager.constants.GameRule;

public abstract class CardEvent extends Event {
    public CardEvent(Match match, PlayerInMatch actor, int at) {
        super(match, actor, at);
    }

    public void afterRedCardEvent() {
        TeamInMatch playersTeam = actor.getTeamInMatch();
        int actorTeammateOnFieldCount = playersTeam.getPlayingPlayers().size();

        if (actorTeammateOnFieldCount < GameRule.MIN_PLAYING_PLAYER_COUNT) {
            match.endDueToLackOfPlayers(playersTeam);
        }
    }
}
