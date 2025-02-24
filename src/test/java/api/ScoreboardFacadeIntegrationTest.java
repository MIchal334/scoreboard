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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreboardFacadeIntegrationTest {
    private ScoreboardFacade scoreboardFacade;
    private MatchRepository matchRepository;
    private static int currentId;

    @BeforeEach
    void setUp() {
        currentId = 0;
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


    }

//    private List<MatchInfo> getListToSortAndResult() {
//        List<MatchInfo> matchInfoListToSort = new ArrayList<>();
//        List<MatchInfo> matchInfoListCorrectResult = new ArrayList<>();
//
//        MatchInfo matchWithResult5 = crateMatchInfo("Mexico", "Canada", new MatchResult(0, 5));
//        MatchInfo matchWithResult12Older = crateMatchInfo("Spain", "Brazil", new MatchResult(10, 2));
//        MatchInfo matchWithResult4 = crateMatchInfo("Germany", "France", new MatchResult(2, 2));
//        MatchInfo matchWithResult12 = crateMatchInfo("Uruguay", "Italy", new MatchResult(2, 2));
//
//
//    }

    private MatchInfo crateMatchInfo(String homeTeamName, String awayTeamName, MatchResult currentMatchResult) {
        currentId++;
        return new MatchInfo(currentId, homeTeamName, awayTeamName, currentMatchResult);
    }

}