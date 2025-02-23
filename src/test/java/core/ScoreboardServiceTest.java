package core;


import core.model.MatchInfo;
import core.model.MatchResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScoreboardServiceTest {
    @Mock
    private MatchRepository matchRepository;
    @Mock
    private SortingResultStrategy sortingResultStrategy;

    @InjectMocks
    private ScoreboardService scoreboardService;

    @Test
    public void testGetAllCurrentMatchesHappyPath() {
        //GIVEN
        var matchHighestResult = createMatch("H", "aa", "bb", new MatchResult(5, 4));
        var matchMiddleResult = createMatch("M", "cc", "dd", new MatchResult(7, 2));
        var matchLowScore = createMatch("L", "ee", "ff", new MatchResult(1, 0));
        List<MatchInfo> matchList = List.of(matchHighestResult, matchMiddleResult, matchLowScore);
        when(matchRepository.findAll()).thenReturn(matchList);
        when(sortingResultStrategy.sortMatches(matchList)).thenReturn(matchList);

        //WHEN
        List<MatchInfo> result = scoreboardService.getAllCurrentMatches();

        //THEN
        assertEquals(matchList, result);
    }


    private MatchInfo createMatch(String id, String homeTeamName, String awayTeamName, MatchResult matchResult) {
        return new MatchInfo(id, homeTeamName, awayTeamName, matchResult);
    }

}