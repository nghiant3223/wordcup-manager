package uni.hcmut.wcmanager.runners;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import uni.hcmut.wcmanager.constants.TemplateString;
import uni.hcmut.wcmanager.entities.*;
import uni.hcmut.wcmanager.enums.GroupName;
import uni.hcmut.wcmanager.randomizers.GroupStageDraw;
import uni.hcmut.wcmanager.rounds.*;
import uni.hcmut.wcmanager.utils.HibernateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Tournament {
    private List<Team> teams;
    IRound currentRound;

    private Team champion;
    private Team runnerUp;
    private Team[] thirdPlaces;
    private List<Player> topScorers;

    public Tournament() {
        thirdPlaces = new Team[2];

        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        String hql = "FROM Team";
        Query query = session.createQuery(hql);
        teams = (List<Team>) query.list();
    }

    public void start() {
        List<Group> groups = GroupStageDraw.draw(teams);

        System.out.println("\n>>>>>>>>>> GROUP STAGE STARTED <<<<<<<<<<\n");
        runGroupStage(groups);
        Map<Integer, Team[]> groupStageResult = currentRound.getResult();
        System.out.println("\n>>>>>>>>>> GROUP STAGE ENDED <<<<<<<<<<\n");

        System.out.println("\n>>>>>>>>>> ROUND OF 16 STARTED <<<<<<<<<<\n");
        runRoundOfSixteen(groupStageResult);
        Map<Integer, Team[]> roundOf16Result = currentRound.getResult();
        System.out.println("\n>>>>>>>>>> ROUND OF 16 ENDED <<<<<<<<<<\n");

        System.out.println("\n>>>>>>>>>> QUARTERFINAL STARTED <<<<<<<<<<\n");
        runQuarterFinalRound(roundOf16Result);
        Map<Integer, Team[]> quarterFinalResult = currentRound.getResult();
        System.out.println("\n>>>>>>>>>> QUARTERFINAL ENDED <<<<<<<<<<\n");

        System.out.println("\n>>>>>>>>>> SEMIFINAL STARTED <<<<<<<<<<\n");
        runSemiFinalRound(quarterFinalResult);
        Map<Integer, Team[]> semiFinalResult = currentRound.getResult();
        System.out.println("\n>>>>>>>>>> SEMIFINAL ENDED <<<<<<<<<<\n");

        System.out.println("\n>>>>>>>>>> FINAL STARTED <<<<<<<<<<\n");
        runFinal(semiFinalResult);
        Map<Integer, Team[]> finalResult = currentRound.getResult();
        System.out.println("\n>>>>>>>>>> FINAL ENDED <<<<<<<<<<<\n");

        champion = finalResult.get(0)[0];

        Team winnerOfS1 = semiFinalResult.get(0)[0];
        Team winnerOfS2 = semiFinalResult.get(1)[0];
        runnerUp = winnerOfS1 != champion ? winnerOfS1 : winnerOfS2;

        Team winnerOfQ1 = quarterFinalResult.get(0)[0];
        Team winnerOfQ2 = quarterFinalResult.get(1)[0];
        Team winnerOfQ3 = quarterFinalResult.get(2)[0];
        Team winnerOfQ4 = quarterFinalResult.get(3)[0];

        Team loserOfS1 = winnerOfQ1 != winnerOfS1 ? winnerOfQ1 : winnerOfQ2;
        Team loserOfS2 = winnerOfQ3 != winnerOfS2 ? winnerOfQ3 : winnerOfQ4;
        thirdPlaces[0] = loserOfS1;
        thirdPlaces[1] = loserOfS2;

        topScorers = getTopScorers();
    }

    private void runGroupStage(List<Group> groups) {
        currentRound = new GroupStage(groups);
        currentRound.run();
    }

    private void runRoundOfSixteen(Map<Integer, Team[]> groupStageResult) {
        Team firstOfA = groupStageResult.get(GroupName.A.getId())[0];
        Team secondOfA = groupStageResult.get(GroupName.A.getId())[1];

        Team firstOfB = groupStageResult.get(GroupName.B.getId())[0];
        Team secondOfB = groupStageResult.get(GroupName.B.getId())[1];

        Team firstOfC = groupStageResult.get(GroupName.C.getId())[0];
        Team secondOfC = groupStageResult.get(GroupName.C.getId())[1];

        Team firstOfD = groupStageResult.get(GroupName.D.getId())[0];
        Team secondOfD = groupStageResult.get(GroupName.D.getId())[1];

        Team firstOfE = groupStageResult.get(GroupName.E.getId())[0];
        Team secondOfE = groupStageResult.get(GroupName.E.getId())[1];

        Team firstOfF = groupStageResult.get(GroupName.F.getId())[0];
        Team secondOfF = groupStageResult.get(GroupName.F.getId())[1];

        Team firstOfH = groupStageResult.get(GroupName.H.getId())[0];
        Team secondOfH = groupStageResult.get(GroupName.H.getId())[1];

        Team firstOfG = groupStageResult.get(GroupName.G.getId())[0];
        Team secondOfG = groupStageResult.get(GroupName.G.getId())[1];

        Match sq1 = new KnockoutMatch(firstOfA, secondOfB);
        Match sq2 = new KnockoutMatch(firstOfB, secondOfA);
        Match sq3 = new KnockoutMatch(firstOfC, secondOfD);
        Match sq4 = new KnockoutMatch(firstOfD, secondOfC);
        Match sq5 = new KnockoutMatch(firstOfE, secondOfF);
        Match sq6 = new KnockoutMatch(firstOfF, secondOfE);
        Match sq7 = new KnockoutMatch(firstOfG, secondOfH);
        Match sq8 = new KnockoutMatch(firstOfH, secondOfG);

        List<Match> matches = new ArrayList<>();
        matches.add(sq1);
        matches.add(sq2);
        matches.add(sq3);
        matches.add(sq4);
        matches.add(sq5);
        matches.add(sq6);
        matches.add(sq7);
        matches.add(sq8);

        currentRound = new RoundOfSixteen(matches);
        currentRound.run();
    }

    private void runQuarterFinalRound(Map<Integer, Team[]> roundOf16Result) {
        Team sq1Winner = roundOf16Result.get(0)[0];
        Team sq2Winner = roundOf16Result.get(1)[0];
        Team sq3Winner = roundOf16Result.get(2)[0];
        Team sq4Winner = roundOf16Result.get(3)[0];
        Team sq5Winner = roundOf16Result.get(4)[0];
        Team sq6Winner = roundOf16Result.get(5)[0];
        Team sq7Winner = roundOf16Result.get(6)[0];
        Team sq8Winner = roundOf16Result.get(7)[0];

        Match q1 = new KnockoutMatch(sq1Winner, sq2Winner);
        Match q2 = new KnockoutMatch(sq3Winner, sq4Winner);
        Match q3 = new KnockoutMatch(sq5Winner, sq6Winner);
        Match q4 = new KnockoutMatch(sq7Winner, sq8Winner);

        List<Match> matches = new ArrayList<>();
        matches.add(q1);
        matches.add(q2);
        matches.add(q3);
        matches.add(q4);

        currentRound = new QuarterFinalRound(matches);
        currentRound.run();
    }

    private void runSemiFinalRound(Map<Integer, Team[]> quarterFinalResult) {
        Team q1Winner = quarterFinalResult.get(0)[0];
        Team q2Winner = quarterFinalResult.get(1)[0];
        Team q3Winner = quarterFinalResult.get(2)[0];
        Team q4Winner = quarterFinalResult.get(3)[0];

        Match s1 = new KnockoutMatch(q1Winner, q2Winner);
        Match s2 = new KnockoutMatch(q3Winner, q4Winner);

        List<Match> matches = new ArrayList<>();
        matches.add(s1);
        matches.add(s2);

        currentRound = new SemiFinalRound(matches);
        currentRound.run();
    }

    private void runFinal(Map<Integer, Team[]> quarterFinalResult) {
        Team s1Winner = quarterFinalResult.get(0)[0];
        Team s2Winner = quarterFinalResult.get(1)[0];

        Match f = new KnockoutMatch(s1Winner, s2Winner);

        currentRound = new Final(f);
        currentRound.run();
    }

    public void finish() {
        System.out.println(String.format(TemplateString.WINNER_RESULT_TEMPLATE,
                "CHAMPION", champion.getName()));
        System.out.println(String.format(TemplateString.WINNER_RESULT_TEMPLATE,
                "RUNNER-UP", runnerUp.getName()));

        List<String> thirdPlaceNames = new ArrayList<>();
        thirdPlaceNames.add(thirdPlaces[0].getName());
        thirdPlaceNames.add(thirdPlaces[1].getName());

        System.out.println(String.format(TemplateString.WINNER_RESULT_TEMPLATE,
                "THIRD-PLACES", String.join(", ", thirdPlaceNames)));

        List<String> topScorerNames = new ArrayList<>();
        for (Player scorer : topScorers) {
            topScorerNames.add(scorer.getFullname());
        }

        System.out.println(String.format(TemplateString.WINNER_RESULT_TEMPLATE,
                "TOP-SCORERS", String.join(", ", topScorerNames)));
        System.out.println("\nTournament has ended! Thanks for watching ❤\n");
    }

    private List<Player> getTopScorers() {
        List<Player> topScorers = new ArrayList<>();

        for (Team t : teams) {
            for (Player p : t.getPlayers()) {
                if (topScorers.size() == 0) {
                    topScorers.add(p);
                    continue;
                }

                Player currentTopScorer = topScorers.get(0);

                if (p.getGoalCount() > currentTopScorer.getGoalCount()) {
                    topScorers = new ArrayList<>();
                    topScorers.add(p);
                } else if (p.getGoalCount() == currentTopScorer.getGoalCount()) {
                    topScorers.add(p);
                }
            }
        }

        return topScorers;
    }
}
