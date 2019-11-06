package uni.hcmut.wcmanager.randomizers;

import uni.hcmut.wcmanager.constants.MatchRule;
import uni.hcmut.wcmanager.entities.*;
import uni.hcmut.wcmanager.utils.LangUtils;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Random;

public class EventGenerator {
    private List<Event> events;
    private Random random = new Random();

    public EventGenerator(List<Event> events) {
        this.events = events;
    }

    public EventGenerator() {
    }

    public void startGeneratingMatchEvents(Match match) {
        // If events is passed to constructor, just play them.
        // WARNING: JUST FOR TESTING
        if (events != null) {
            for (Event e : events) {
                // Throw exception if redundant event detected
                if (match.isFinished()) {
                    throw new InvalidParameterException("Match has already finished");
                }

                match.handleEvent(e);
            }

            return;
        }

        switch (match.getMatchType()) {
            case KNOCKOUT:
                generateEventForKnockoutMatch(match);
                break;

            case DRAWABLE:
                generateEventForDrawableMatch(match);
                break;
        }
    }

    private void generateEventForDrawableMatch(Match match) {
        for (int i = 0; i < MatchRule.DRAWABLE_MATCH_DURATION; i += 10) {
            for (int j = 0; j < MatchRule.MAX_EVENT_EVERY_10MIN; j++) {
                boolean eventOccurs = random.nextBoolean();
                if (eventOccurs) {
                    int at = i + random.nextInt(10);
                    int p = random.nextInt(100);

                    TeamInMatch team = random.nextBoolean() ? match.getHomeTeam() : match.getAwayTeam();
                    List<PlayerInMatch> playingPlayers = team.getPlayingPlayers();
                    int playingPlayerIndex = random.nextInt(playingPlayers.size());
                    PlayerInMatch actor = playingPlayers.get(playingPlayerIndex);

                    Event event;

                    if (LangUtils.intInRange(p, 0, 50)) {
                        event = new GoalEvent(match, actor, at);
                    } else if (LangUtils.intInRange(p, 50, 70)) {
                        event = new YellowCardEvent(match, actor, at);
                    } else if (LangUtils.intInRange(p, 70, 80)) {
                        event = new RedCardEvent(match, actor, at);
                    } else if (LangUtils.intInRange(p, 80, 90)) {
                        event = new SubstitutionEvent(match, actor, at);
                    } else {
                        event = new InjuryEvent(match, actor, at);
                    }

                    match.handleEvent(event);

                    // If match finishes due to some special condition,
                    // no more events is generated
                    if (match.isFinished()) {
                        return;
                    }
                }
            }
        }

        // If 90 minutes elapse, the match finishes
        if (!match.isFinished()) {
            match.setFinished(true);
        }
    }

    private void generateEventForKnockoutMatch(Match match) {
        for (int i = 0; i < 10; i++) {
            Event e = new GoalEvent(match, null, 5);
            match.handleEvent(e);

            if (match.isFinished()) {
                return;
            }
        }
    }
}
