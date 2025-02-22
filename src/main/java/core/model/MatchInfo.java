package core.model;


import java.util.UUID;

public record MatchInfo(
        String id,
        String homeTeamName,
        String awayTeamName,
        MatchResult matchResult
) {

    public MatchInfo {
        if (homeTeamName == null || homeTeamName.trim().isEmpty() ||
                awayTeamName == null || awayTeamName.trim().isEmpty() ||
                homeTeamName.trim().equalsIgnoreCase(awayTeamName.trim())) {
            throw new IllegalArgumentException("Team names not correct");
        }
    }

    public static MatchInfo create(String homeTeamName, String awayTeamName) {
        try {
            return new MatchInfo(
                    UUID.randomUUID().toString(),
                    homeTeamName,
                    awayTeamName,
                    new MatchResult(0, 0)
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Creating match is impossible" + e.getMessage(),
                    e
            );
        }
    }
}