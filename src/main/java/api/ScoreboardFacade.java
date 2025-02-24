package api;

import core.ScoreboardService;

public class ScoreboardFacade {
    private final ScoreboardService scoreboardService;

    public ScoreboardFacade(ScoreboardService scoreboardService) {
        this.scoreboardService = scoreboardService;
    }
}
