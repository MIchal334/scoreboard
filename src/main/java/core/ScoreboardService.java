package core;


public class ScoreboardService {
    private final MatchRepository matchRepository;
    private final SortingResultStrategy sortingResultStrategy;

    public ScoreboardService(MatchRepository matchRepository, SortingResultStrategy sortingResultStrategy) {
        this.matchRepository = matchRepository;
        this.sortingResultStrategy = sortingResultStrategy;
    }


}
