package api;

import core.MatchRepository;
import core.ScoreboardService;
import core.SortingResultStrategy;
import core.model.MatchInfo;
import core.model.MatchResult;
import infra.HighestScoreSortStrategy;
import infra.MatchRepositoryInMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreboardFacadeIntegrationTest {
    private ScoreboardFacade scoreboardFacade;
    private MatchRepository matchRepository;

    @BeforeEach
    void setUp() {
        matchRepository = new MatchRepositoryInMemory();
        SortingResultStrategy sortingResultStrategy = new HighestScoreSortStrategy();
        ScoreboardService scoreboardService = new ScoreboardService(matchRepository, sortingResultStrategy);
        scoreboardFacade = new ScoreboardFacade(scoreboardService);
    }


    @Test
    void testMatchLifeCycle() {
        String homeTeamName = "homeTeamName";
        String awayTeamName = "awayTeamName";
        int scoreHome = 1;
        int scoreAway = 2;

        // CRATE NEW MATCH
        String crateInfo = scoreboardFacade.startNewMatch(homeTeamName, awayTeamName);
        assertEquals(1, matchRepository.findAll().size());
        assertTrue(crateInfo.contains("created"));
        int id = matchRepository.findAll().get(0).id();

        // UPDATE RESULT
        MatchResult newResult = new MatchResult(scoreHome, scoreAway);
        String updatedInfo = scoreboardFacade.updateResult(id, newResult);
        assertEquals(scoreHome, matchRepository.findAll().get(0).matchResult().homeTeamScore());
        assertEquals(scoreAway, matchRepository.findAll().get(0).matchResult().awayTeamScore());
        assertTrue(updatedInfo.contains("result updated"));


        // FINISH MATCH
        String finishInfo = scoreboardFacade.finishMatch(id);
        assertEquals(0, matchRepository.findAll().size());
        assertTrue(finishInfo.contains("finished"));

    }

    @Test
    void testShowSortedMatches() {
        List<MatchInfo> sortedList = crateMatchInRepo();

        List<MatchInfo> result = scoreboardFacade.getOnGoingMatches();

        assertEquals(sortedList.size(), result.size());
        assertEquals(sortedList, result);
    }


    private List<MatchInfo> crateMatchInRepo() {
        List<MatchInfo> matchInfoListCorrectResult = new ArrayList<>();

        MatchInfo matchWithResult5 = crateMatchInfo("Mexico", "Canada", new MatchResult(0, 5));
        MatchInfo matchWithResult12Older = crateMatchInfo("Spain", "Brazil", new MatchResult(10, 2));
        MatchInfo matchWithResult4Older = crateMatchInfo("Germany", "France", new MatchResult(2, 2));
        MatchInfo matchWithResult12 = crateMatchInfo("Uruguay", "Italy", new MatchResult(2, 2));
        MatchInfo matchWithResult4 = crateMatchInfo("Argentina", "Australia", new MatchResult(3, 1));


        matchInfoListCorrectResult.add(matchWithResult12);
        matchInfoListCorrectResult.add(matchWithResult12Older);
        matchInfoListCorrectResult.add(matchWithResult5);
        matchInfoListCorrectResult.add(matchWithResult4);
        matchInfoListCorrectResult.add(matchWithResult4Older);


        return matchInfoListCorrectResult;
    }

    private MatchInfo crateMatchInfo(String homeTeamName, String awayTeamName, MatchResult currentMatchResult) {
        MatchInfo match = matchRepository.crateNewMatch(homeTeamName, awayTeamName);
        matchRepository.updateResult(match.id(), currentMatchResult);
        return matchRepository.findAll().stream().filter(m -> m.id() == match.id()).findFirst().get();
    }

}