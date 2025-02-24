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

        // UPDATE RESULT
        MatchResult newResult = new MatchResult(scoreHome, scoreAway);
        String updatedInfo = scoreboardFacade.updateResult(newResult);
        assertEquals(scoreHome, matchRepository.findAll().get(0).matchResult().homeTeamScore());
        assertEquals(scoreAway, matchRepository.findAll().get(0).matchResult().awayTeamScore());
        assertTrue(updatedInfo.contains("result updated"));


        // FINISH MATCH
        String id = matchRepository.findAll().get(0).id();
        String finishInfo = scoreboardFacade.finishMatch(id);
        assertEquals(0, matchRepository.findAll().size());
        assertTrue(finishInfo.contains("finished"));

    }


}