package uni.hcmut.wcmanager.events;

import uni.hcmut.wcmanager.constants.MatchRule;
import uni.hcmut.wcmanager.entities.Match;
import uni.hcmut.wcmanager.entities.PlayerInMatch;
import uni.hcmut.wcmanager.entities.TeamInMatch;

public abstract class CardEvent extends Event {
    public CardEvent(Match match, PlayerInMatch actor, int at) {
        super(match, actor, at);
    }

    public void afterRedCardEvent() {
        TeamInMatch playersTeam = actor.getTeamInMatch();
        int actorTeammateOnFieldCount = playersTeam.getPlayingPlayers().size();

        if (actorTeammateOnFieldCount < MatchRule.MIN_PLAYING_PLAYER_COUNT) {
            match.endDueToLackOfPlayers(playersTeam);
        }
    }
}
