package infra;

import core.MatchRepository;
import core.model.MatchInfo;
import core.model.MatchResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class MatchRepositoryInMemory implements MatchRepository {
    private final List<MatchInfo> matches = new ArrayList<>();


    @Override
    public MatchInfo crateNewMatch(String homeTeamName, String awayTeamName) {
        try {
            if (checkIsTeamNameAlreadyExist(homeTeamName) || checkIsTeamNameAlreadyExist(awayTeamName)) {
                throw new IllegalArgumentException("Team name already exist.");
            }
            MatchInfo match = MatchInfo.create(homeTeamName, awayTeamName);
            matches.add(match);
            return match;
        } catch (IllegalArgumentException e) {
            System.out.println("Error during creating match : " + e.getMessage());
            return null;
        }

    }

    @Override
    public Collection<MatchInfo> findAll() {
        return matches;
    }

    @Override
    public void removeMatch(String matchId) {
        try {
            MatchInfo info = findMatchById(matchId);
            matches.remove(info);
        } catch (IllegalArgumentException e) {
            System.out.println("Error during removing match : " + e.getMessage());
        }
    }

    @Override
    public void updateResult(String id, MatchResult newResult) {
        try {
            MatchInfo info = findMatchById(id);
            MatchInfo newInfo = info.withMatchResult(newResult);
            removeMatch(id);
            matches.add(newInfo);
        }catch (IllegalArgumentException e) {
            System.out.println("Error during updating match : " + e.getMessage());
        }

    }


    private MatchInfo findMatchById(String matchId) {
        return matches.stream().filter(match -> match.id().equals(matchId))
                .findFirst().
                orElseThrow(() -> new IllegalArgumentException("Match with id " + matchId + "  not found"));
    }

    private boolean checkIsTeamNameAlreadyExist(String teamName) {
        return matches.stream()
                .anyMatch(info ->
                        info.homeTeamName().equals(teamName) || info.awayTeamName().equals(teamName)
                );
    }
}
