package infra;

import core.MatchRepository;
import core.model.MatchInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


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
        MatchInfo info = findMatchById(matchId).get();
        matches.remove(info);
    }


    private Optional<MatchInfo> findMatchById(String matchId) {
        return matches.stream().filter(match -> match.id().equals(matchId)).findFirst();
    }

    private boolean checkIsTeamNameAlreadyExist(String teamName) {
        return matches.stream()
                .anyMatch(info ->
                        info.homeTeamName().equals(teamName) || info.awayTeamName().equals(teamName)
                );
    }
}
