package com.scoreboard.core;

import com.scoreboard.core.model.MatchInfo;

import java.util.List;

public interface SortingResultStrategy {
    List<MatchInfo> sortMatches(List<MatchInfo> matchInfo);
}
