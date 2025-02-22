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
            matches.add(MatchInfo.create(homeTeamName, awayTeamName));
        } catch (Exception e) {
            System.out.println("Error during creating match : " + e.getMessage());
        }

    }

    @Override
    public Collection<MatchInfo> findAll() {
        return matches;
    }
}
