package core;

import core.model.MatchInfo;

import java.util.List;

public interface SortingResultStrategy {
    List<MatchInfo> sortMatches(List<MatchInfo> matchInfo);
}
