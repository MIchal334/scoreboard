package core;

import core.model.MatchInfo;

import java.util.Collection;

public interface MatchRepository {
    MatchInfo crateNewMatch(String homeTeamName, String awayTeamName);

    Collection<MatchInfo> findAll();
}
