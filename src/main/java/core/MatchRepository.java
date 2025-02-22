package core;

import core.model.MatchInfo;
import core.model.MatchResult;

import java.util.Collection;

public interface MatchRepository {
    MatchInfo crateNewMatch(String homeTeamName, String awayTeamName);

    Collection<MatchInfo> findAll();

    void removeMatch(String matchId);

    void updateResult(String id, MatchResult newResult);
}
