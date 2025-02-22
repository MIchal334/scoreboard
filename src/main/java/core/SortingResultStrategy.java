package core;

import core.model.MatchResult;

import java.util.List;

public interface SortingResultStrategy {
    List<MatchResult> sortMatches(List<MatchResult> matchResults);

}
