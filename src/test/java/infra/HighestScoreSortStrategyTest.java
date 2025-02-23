package infra;

import core.model.MatchInfo;
import core.model.MatchResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HighestScoreSortStrategyTest {
    @Test
    public void testSortHighestScoreStrategy() {
        //GIVEN
        var matchHighestResult = createMatch("H", "aa", "bb", new MatchResult(5, 4));
        var matchMiddleResult = createMatch("M", "cc", "dd", new MatchResult(7, 2));
        var matchLowScore = createMatch("L", "ee", "ff", new MatchResult(1, 0));
        List<MatchInfo> matchList = List.of(matchMiddleResult, matchHighestResult, matchLowScore);
        var sortStrategy = new HighestScoreSortStrategy();

        // WHEN
        var result = sortStrategy.sortMatches(matchList);

        //THEN
        assertEquals(matchHighestResult, result.get(0));
        assertEquals(matchMiddleResult, result.get(1));
        assertEquals(matchLowScore, result.get(2));

    }

    private MatchInfo createMatch(String id, String homeTeamName, String awayTeamName, MatchResult matchResult) {
        return new MatchInfo(id, homeTeamName, awayTeamName, matchResult);
    }

}
