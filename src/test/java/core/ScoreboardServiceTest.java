package core;


import core.model.MatchInfo;
import core.model.MatchResult;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ScoreboardServiceTest {

    @Test
    public void testGetAllCurrentMatchesHappyPath() {
        //GIVEN
        var matchRepository = Mockito.mock(MatchRepository.class);
        var sortingResultStrategy = Mockito.mock(SortingResultStrategy.class);
        List<MatchInfo> matchList = crateMatchesList();
        when(matchRepository.findAll()).thenReturn(matchList);
        when(sortingResultStrategy.sortMatches(matchList)).thenReturn(matchList);

        var service = new ScoreboardService(matchRepository, sortingResultStrategy);

        //WHEN
        List<MatchInfo> result = service.getAllCurrentMatches();

        //THEN
        assertEquals(matchList, result);
    }

    @Test
    public void testStartNewMatchHappyPath() {
        //GIVEN
        var matchRepository = Mockito.mock(MatchRepository.class);
        var sortingResultStrategy = Mockito.mock(SortingResultStrategy.class);
        var matchFake = createMatch("H", "aa", "bb", new MatchResult(5, 4));
        when(matchRepository.crateNewMatch(anyString(),anyString())).thenReturn(matchFake);
        var service = new ScoreboardService(matchRepository, sortingResultStrategy);

        //WHEN
        String result = service.startNewMatch();

        //THEN
        assertTrue(result.contains("created"));
    }

    private List<MatchInfo> crateMatchesList() {
        var matchHighestResult = createMatch("H", "aa", "bb", new MatchResult(5, 4));
        var matchMiddleResult = createMatch("M", "cc", "dd", new MatchResult(7, 2));
        var matchLowScore = createMatch("L", "ee", "ff", new MatchResult(1, 0));
        return List.of(matchHighestResult, matchMiddleResult, matchLowScore);
    }


    private MatchInfo createMatch(String id, String homeTeamName, String awayTeamName, MatchResult matchResult) {
        return new MatchInfo(id, homeTeamName, awayTeamName, matchResult);
    }

}