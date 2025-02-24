package com.scoreboard.core.model;


public record MatchInfo(
        int id,
        String homeTeamName,
        String awayTeamName,
        MatchResult matchResult
) {
    private static int currentId;

    public MatchInfo {
        if (homeTeamName == null || homeTeamName.trim().isEmpty() ||
                awayTeamName == null || awayTeamName.trim().isEmpty() ||
                homeTeamName.trim().equalsIgnoreCase(awayTeamName.trim())) {
            throw new IllegalArgumentException("Team names not correct");
        }
    }

    public static MatchInfo create(String homeTeamName, String awayTeamName) {
        try {
            currentId++;
            return new MatchInfo(
                    currentId,
                    homeTeamName,
                    awayTeamName,
                    new MatchResult(0, 0)
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Creating match is impossible" + e.getMessage()
            );
        }
    }

    public MatchInfo withMatchResult(MatchResult newMatchResult) {
        if (newMatchResult.awayTeamScore() < 0 || newMatchResult.homeTeamScore() < 0) {
            throw new IllegalArgumentException("Result should be above 0");
        }
        return new MatchInfo(id, homeTeamName, awayTeamName, newMatchResult);
    }
}