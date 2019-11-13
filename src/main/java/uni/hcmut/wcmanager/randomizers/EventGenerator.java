package uni.hcmut.wcmanager.randomizers;

import uni.hcmut.wcmanager.constants.MatchRule;
import uni.hcmut.wcmanager.entities.*;
import uni.hcmut.wcmanager.events.*;
import uni.hcmut.wcmanager.utils.EventUtils;
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

    public void playEventForDrawableMatch(DrawableMatch match) {
        if (events == null) {
            generateEventInMainTime(match);
            return;
        }

        for (Event e : events) {
            if (e.getAt() >= MatchRule.FULL_DURATION || match.isFinished()) {
                throw new InvalidParameterException("Match has already finished");
            }

            match.handleEvent(e);
        }
    }

    public void playEventForKnockoutMatch(KnockoutMatch match) {
        TeamInMatch homeTeam = match.getHomeTeam();
        TeamInMatch awayTeam = match.getAwayTeam();

        if (events == null) {
            // Generate events from 0' to 90'
            generateEventInMainTime(match);
            if (homeTeam.getGoalFor() != awayTeam.getGoalFor()) {
                return;
            }

            // Generate events from 90' to 105'
            generateEventInExtraTime(match);
            if (homeTeam.getGoalFor() != awayTeam.getGoalFor()) {
                return;
            }

            // Generate events from 105' to 120'
            generateEventInExtraTime(match);
            if (homeTeam.getGoalFor() != awayTeam.getGoalFor()) {
                return;
            }

            return;
        }

        boolean anyEventOccursAfterMin105 = false;
        boolean anyEventOccursAfterMin90 = false;

        for (int i = 0; i < events.size(); i++) {
            Event e = events.get(i);

            // If match's already finished due to lack of players, we dont generate event anymore
            if (match.isFinished()) {
                throw new InvalidParameterException("Match has already finished");
            }

            if (e.getAt() >= MatchRule.FULL_DURATION) {
                if (EventUtils.isEventRightAfterMinute(events, i, MatchRule.FULL_DURATION)
                        && homeTeam.getGoalFor() != awayTeam.getGoalFor()) {
                    throw new InvalidParameterException("Match has already finished");
                }

                anyEventOccursAfterMin90 = true;
            }

            if (e.getAt() >= MatchRule.FULL_DURATION + MatchRule.EXTRA_HALF_DURATION) {
                // If silver-goal rule happens,
                // but there is still a event
                if (EventUtils.isEventRightAfterMinute(events, i, MatchRule.EXTRA_FULL_DURATION)
                        && homeTeam.getGoalFor() != awayTeam.getGoalFor()) {
                    throw new InvalidParameterException("Match has already finished");
                }

                anyEventOccursAfterMin105 = true;
            }

            if (e.getAt() >= MatchRule.EXTRA_FULL_DURATION) {
                throw new InvalidParameterException("Invalid time at which event occurs");
            }

            match.handleEvent(e);
        }


        // If there after 90 mins but the match is still draw
        // and there is no more event for the first extra-time half
        if (!anyEventOccursAfterMin90 && homeTeam.getGoalFor() == awayTeam.getGoalFor()) {
            throw new InvalidParameterException("Match cannot be a draw");
        }

        // If there after first extra-time half but the match is still draw
        // and there is no more event for the second extra-time half
        if (!anyEventOccursAfterMin105 && homeTeam.getGoalFor() == awayTeam.getGoalFor()) {
            throw new InvalidParameterException("Match cannot be a draw");
        }
    }

    private void generateEventInMainTime(Match match) {
        for (int offsetMin = 0; offsetMin < MatchRule.FULL_DURATION; offsetMin += 10) {
            for (int j = 0; j < MatchRule.MAX_EVENT_EVERY_10MIN; j++) {
                // If match's already finished due to lack of players, we dont generate event anymore
                if (match.isFinished()) {
                    return;
                }

                boolean eventOccurs = random.nextBoolean();
                if (eventOccurs) {
                    Event e = createRandomEvent(match, offsetMin);
                    match.handleEvent(e);
                }
            }
        }
    }

    private void generateEventInExtraTime(Match match) {
        for (int offsetMin = 0; offsetMin < MatchRule.EXTRA_HALF_DURATION; offsetMin += 5) {
            for (int j = 0; j < MatchRule.MAX_EVENT_EVERY_5MIN; j++) {
                // If match's already finished, we dont generate event anymore
                if (match.isFinished()) {
                    return;
                }

                boolean eventOccurs = random.nextBoolean();
                if (eventOccurs) {
                    Event e = createRandomEvent(match, MatchRule.FULL_DURATION + offsetMin);
                    match.handleEvent(e);
                }
            }
        }
    }

    private Event createRandomEvent(Match match, int offsetMinute) {
        int at = offsetMinute + random.nextInt(10);
        int p = random.nextInt(100);

        TeamInMatch team = random.nextBoolean() ? match.getHomeTeam() : match.getAwayTeam();
        List<PlayerInMatch> playingPlayers = team.getPlayingPlayers();
        int playingPlayerIndex = random.nextInt(playingPlayers.size());
        PlayerInMatch actor = playingPlayers.get(playingPlayerIndex);

        Event event;

        if (LangUtils.intInRange(p, 0, 40)) {
            event = new GoalEvent(match, actor, at);
        } else if (LangUtils.intInRange(p, 50, 70)) {
            event = new YellowCardEvent(match, actor, at);
        } else if (LangUtils.intInRange(p, 70, 80)) {
            event = new RedCardEvent(match, actor, at);
        } else if (LangUtils.intInRange(p, 80, 90)) {
            event = new SubstitutionEvent(match, actor, at);
        } else if (LangUtils.intInRange(p, 40, 50)) {
            event = new InjuryEvent(match, actor, at);
        } else {
            event = new OwnGoalEvent(match, actor, at);
        }

        return event;
    }
}
