package core;

import core.model.MatchInfo;
import core.model.MatchResult;

import java.io.IOException;
import java.util.Collection;

public interface MatchRepository {
    MatchInfo crateNewMatch(String homeTeamName, String awayTeamName) throws IllegalArgumentException;

    Collection<MatchInfo> findAll();

    void removeMatch(String matchId) throws IllegalArgumentException;

    void updateResult(String id, MatchResult newResult) throws IllegalArgumentException;
}
