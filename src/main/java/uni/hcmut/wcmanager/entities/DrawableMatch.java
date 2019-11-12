package uni.hcmut.wcmanager.entities;

import uni.hcmut.wcmanager.constants.TemplateString;
import uni.hcmut.wcmanager.enums.RoundName;
import uni.hcmut.wcmanager.randomizers.EventGenerator;

import java.security.InvalidParameterException;

public class DrawableMatch extends Match {
    public DrawableMatch(Team home, Team away) {
        super(home, away);
    }

    public void start(EventGenerator generator) {
        if (generator == null) {
            throw new InvalidParameterException("eventGenerator mustn't be null");
        }

        generator.playEventForDrawableMatch(this);

        setFinished();
        setWinner();
        finish();
    }

    public void start() {
        EventGenerator generator = new EventGenerator();
        generator.playEventForDrawableMatch(this);

        setFinished();
        setWinner();
        finish();
    }

    protected void finish() {
        super.finish();

        System.out.println(String.format(TemplateString.MATCH_RESULT_TEMPLATE,
                homeTeam.getTeam().getName(), homeTeam.getGoalFor(),
                awayTeam.getGoalFor(), awayTeam.getTeam().getName()));
        System.out.println(TemplateString.MATCH_SEPARATOR);
    }
}
