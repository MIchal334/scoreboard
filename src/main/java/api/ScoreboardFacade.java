package api;

import core.ScoreboardService;
import core.model.MatchInfo;
import core.model.MatchResult;

import java.util.List;

public class ScoreboardFacade {
    private final ScoreboardService scoreboardService;

    public ScoreboardFacade(ScoreboardService scoreboardService) {
        this.scoreboardService = scoreboardService;
    }

    public String startNewMatch(String homeTeamName, String awayTeamName) {
        return scoreboardService.startNewMatch(homeTeamName, awayTeamName);
    }

    public String updateResult(int matchId, MatchResult newResult) {
        return scoreboardService.updateResultMatch(matchId, newResult);
    }

    public String finishMatch(int matchId) {
        return scoreboardService.finishMatch(matchId);
    }

    public List<MatchInfo> getOnGoingMatches() {
        return scoreboardService.getAllCurrentMatches();
    }
}
