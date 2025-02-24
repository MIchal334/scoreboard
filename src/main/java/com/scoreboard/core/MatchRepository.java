package com.scoreboard.core;

import com.scoreboard.core.model.MatchInfo;
import com.scoreboard.core.model.MatchResult;

import java.util.List;

public interface MatchRepository {
    MatchInfo crateNewMatch(String homeTeamName, String awayTeamName) throws IllegalArgumentException;

    List<MatchInfo> findAll();

    void removeMatch(int matchId) throws IllegalArgumentException;

    void updateResult(int id, MatchResult newResult) throws IllegalArgumentException;
}
