package infra;

import core.MatchRepository;
import core.model.MatchInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MatchRepositoryInMemory implements MatchRepository {
    private final List<MatchInfo> matches = new ArrayList<>();


    @Override
    public void crateNewMatch(String homeTeamName, String awayTeamName) {
        try {
            if (checkIsTeamNameAlreadyExist(homeTeamName) || checkIsTeamNameAlreadyExist(awayTeamName)) {
                throw new IllegalArgumentException("Team name already exist.");
            }
            MatchInfo match = MatchInfo.create(homeTeamName, awayTeamName);
            matches.add(match);
        } catch (IllegalArgumentException e) {
            System.out.println("Error during creating match : " + e.getMessage());
        }

    }

    @Override
    public Collection<MatchInfo> findAll() {
        return matches;
    }


    private boolean checkIsTeamNameAlreadyExist(String teamName) {
        return matches.stream()
                .anyMatch(info ->
                        info.homeTeamName().equals(teamName) || info.awayTeamName().equals(teamName)
                );
    }
}
