package api;

import core.MatchRepository;
import core.ScoreboardService;
import core.SortingResultStrategy;
import infra.HighestScoreSortStrategy;
import infra.MatchRepositoryInMemory;

public class ScoreboardFactory {
    public static ScoreboardFacade create() {
        MatchRepository matchRepository = new MatchRepositoryInMemory();
        SortingResultStrategy sortingResultStrategy = new HighestScoreSortStrategy();
        ScoreboardService scoreboardService = new ScoreboardService(matchRepository, sortingResultStrategy);
        return new ScoreboardFacade(scoreboardService);
    }
}
