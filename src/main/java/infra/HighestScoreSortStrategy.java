package infra;

import core.SortingResultStrategy;
import core.model.MatchInfo;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HighestScoreSortStrategy implements SortingResultStrategy {
    private static final Comparator<MatchInfo> MATCH_COMPARATOR = Comparator.comparingInt(match ->
            match.matchResult().homeTeamScore() + match.matchResult().awayTeamScore()
    );


    @Override
    public List<MatchInfo> sortMatches(List<MatchInfo> matchInfo) {
        return matchInfo.stream().sorted(MATCH_COMPARATOR).collect(Collectors.toList()).reversed();
    }
}
