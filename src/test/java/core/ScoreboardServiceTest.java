package core;


import core.model.MatchInfo;
import core.model.MatchResult;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
    public void testStartNewMatchHappyPathShouldCreateNewMatchAndShowInfo() {
        //GIVEN
        var matchRepository = Mockito.mock(MatchRepository.class);
        var sortingResultStrategy = Mockito.mock(SortingResultStrategy.class);
        var matchFake = createMatch(1, "aa", "bb", new MatchResult(5, 4));
        when(matchRepository.crateNewMatch(anyString(), anyString())).thenReturn(matchFake);
        var service = new ScoreboardService(matchRepository, sortingResultStrategy);

        //WHEN
        String result = service.startNewMatch("h", "b");

        //THEN
        assertTrue(result.contains("created"));
    }

    @Test
    public void testStartNewMatchShouldNotCreateNewMatchAndShowInfo() {
        //GIVEN
        var matchRepository = Mockito.mock(MatchRepository.class);
        var sortingResultStrategy = Mockito.mock(SortingResultStrategy.class);
        when(matchRepository.crateNewMatch(anyString(), anyString())).thenThrow(new IllegalArgumentException());
        var service = new ScoreboardService(matchRepository, sortingResultStrategy);

        //WHEN
        String result = service.startNewMatch("h", "b");

        //THEN
        assertTrue(result.contains("Failed to start"));

    }

    @Test
    public void testFinishMatchHappyPathShouldFinishMatchAndShowInfo() {
        //GIVEN
        var matchRepository = Mockito.mock(MatchRepository.class);
        var sortingResultStrategy = Mockito.mock(SortingResultStrategy.class);
        var fakeID = 1221;
        doNothing().when(matchRepository).removeMatch(anyInt());
        var service = new ScoreboardService(matchRepository, sortingResultStrategy);


        //WHEN
        String result = service.finishMatch(fakeID);

        //THEN
        assertTrue(result.contains("finished"));
    }

    @Test
    public void testFinishMatchShouldNotFinishMatchAndShowInfo() {
        //GIVEN
        var matchRepository = Mockito.mock(MatchRepository.class);
        var sortingResultStrategy = Mockito.mock(SortingResultStrategy.class);
        var fakeID = 2132;
        doThrow(new IllegalArgumentException()).when(matchRepository).removeMatch(anyInt());
        var service = new ScoreboardService(matchRepository, sortingResultStrategy);


        //WHEN
        String result = service.finishMatch(fakeID);

        //THEN
        assertTrue(result.contains("Failed to finish"));
    }


    @Test
    public void testUpdateResultHappyPathShouldUpdateAndShowInfo() {
        //GIVEN
        var matchRepository = Mockito.mock(MatchRepository.class);
        var sortingResultStrategy = Mockito.mock(SortingResultStrategy.class);
        var fakeID = 123;
        var fakeResult = new MatchResult(5, 4);
        doNothing().when(matchRepository).updateResult(anyInt(), any());
        var service = new ScoreboardService(matchRepository, sortingResultStrategy);

        //WHEN
        String result = service.updateResultMatch(fakeID, fakeResult);

        //THEN
        assertTrue(result.contains("result updated"));
    }


    @Test
    public void testUpdateResultShouldNotUpdateAndShowInfo() {
        //GIVEN
        var matchRepository = Mockito.mock(MatchRepository.class);
        var sortingResultStrategy = Mockito.mock(SortingResultStrategy.class);
        var fakeID = 123;
        var fakeResult = new MatchResult(5, 4);
        doThrow(new IllegalArgumentException()).when(matchRepository).updateResult(anyInt(), any());
        var service = new ScoreboardService(matchRepository, sortingResultStrategy);

        //WHEN
        String result = service.updateResultMatch(fakeID, fakeResult);

        //THEN
        assertTrue(result.contains("Failed to update"));
    }

    private List<MatchInfo> crateMatchesList() {
        var matchHighestResult = createMatch(1, "aa", "bb", new MatchResult(5, 4));
        var matchMiddleResult = createMatch(2, "cc", "dd", new MatchResult(7, 2));
        var matchLowScore = createMatch(3, "ee", "ff", new MatchResult(1, 0));
        return List.of(matchHighestResult, matchMiddleResult, matchLowScore);
    }


    private MatchInfo createMatch(int id, String homeTeamName, String awayTeamName, MatchResult matchResult) {
        return new MatchInfo(id, homeTeamName, awayTeamName, matchResult);
    }

}