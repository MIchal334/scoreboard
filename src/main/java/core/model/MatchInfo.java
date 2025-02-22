package core.model;

import java.util.UUID;

public record MatchInfo(String id, String homeTeamName, String awayTeamName, MatchResult matchResult) {

    public static MatchInfo create(String homeTeamName, String awayTeamName) {
        return new MatchInfo(UUID.randomUUID().toString(), homeTeamName, awayTeamName, new MatchResult(0, 0));
    }
}
