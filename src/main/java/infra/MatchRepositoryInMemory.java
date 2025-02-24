package infra;

import core.MatchRepository;
import core.model.MatchInfo;
import core.model.MatchResult;

import java.util.ArrayList;
import java.util.List;


public class MatchRepositoryInMemory implements MatchRepository {
    private final List<MatchInfo> matches = new ArrayList<>();


    @Override
    public MatchInfo crateNewMatch(String homeTeamName, String awayTeamName) throws IllegalArgumentException {
        if (checkIsTeamNameAlreadyExist(homeTeamName) || checkIsTeamNameAlreadyExist(awayTeamName)) {
            throw new IllegalArgumentException("Team name already exist.");
        }
        MatchInfo match = MatchInfo.create(homeTeamName, awayTeamName);
        matches.add(match);
        return match;

    }

    @Override
    public List<MatchInfo> findAll() {
        return matches;
    }

    @Override
    public void removeMatch(int matchId) throws IllegalArgumentException {
        MatchInfo info = findMatchById(matchId);
        matches.remove(info);
    }

    @Override
    public void updateResult(int id, MatchResult newResult) throws IllegalArgumentException {
        MatchInfo info = findMatchById(id);
        MatchInfo newInfo = info.withMatchResult(newResult);
        removeMatch(id);
        matches.add(newInfo);
    }


    private MatchInfo findMatchById(int matchId) {
        return matches.stream().filter(match -> match.id() == matchId)
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
