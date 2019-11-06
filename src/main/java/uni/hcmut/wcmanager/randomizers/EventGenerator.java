package uni.hcmut.wcmanager.randomizers;

import uni.hcmut.wcmanager.constants.MatchConstants;
import uni.hcmut.wcmanager.entities.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EventGenerator {
    private List<Event> events;
    private Random random = new Random();

    public EventGenerator(List<Event> events) {
        this.events = events;
    }

    public EventGenerator() {
        this.events = new ArrayList<>();
    }

    public void startMatch(Match match) {
        if (events.size() > 0) {
            for (Event e : events) {
                if (match.isFinished()) {
                    throw new InvalidParameterException("Cannot play event after match finishes!");
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
        for (int i = 0; i < MatchConstants.DRAWABLE_MATCH_DURATION; i += 10) {
            for (int j = 0; j < MatchConstants.MAX_EVENT_EVERY_10MIN; j++) {
                boolean eventOccurs = random.nextBoolean();
                if (eventOccurs) {
                    Event event;
                    int at = i + random.nextInt(10);
                    int type = random.nextInt(4);
                    TeamInMatch team = random.nextBoolean() ? match.getHomeTeam() : match.getAwayTeam();
                    List<PlayerInMatch> playingPlayers = team.getStartingPlayers();
                    PlayerInMatch actor = playingPlayers.get(random.nextInt(playingPlayers.size()));

                    switch (type) {
                        case 0:
                            event = new GoalEvent(match, actor, at);
                            break;
                        case 1:
//                            event = new RedCardEvent(match, actor, at);
//                            break;
                        case 2:
//                            event = new YellowCardEvent(match, actor, at);
//                            break;
                        default:
                            event = new InjuryEvent(match, actor, at);
                    }

                    match.handleEvent(event);

                    if (match.isFinished()) {
                        return;
                    }
                }
            }
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
