package com.scoreboard.api;

import com.scoreboard.core.MatchRepository;
import com.scoreboard.core.ScoreboardService;
import com.scoreboard.core.SortingResultStrategy;
import com.scoreboard.infra.HighestScoreSortStrategy;
import com.scoreboard.infra.MatchRepositoryInMemory;

public class ScoreboardFactory {
    public static ScoreboardFacade create() {
        MatchRepository matchRepository = new MatchRepositoryInMemory();
        SortingResultStrategy sortingResultStrategy = new HighestScoreSortStrategy();
        ScoreboardService scoreboardService = new ScoreboardService(matchRepository, sortingResultStrategy);
        return new ScoreboardFacade(scoreboardService);
    }
}
