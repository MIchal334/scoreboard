package core;


import core.model.MatchInfo;

import java.util.List;


public class ScoreboardService {
    private final MatchRepository matchRepository;
    private final SortingResultStrategy sortingResultStrategy;

    public ScoreboardService(MatchRepository matchRepository, SortingResultStrategy sortingResultStrategy) {
        this.matchRepository = matchRepository;
        this.sortingResultStrategy = sortingResultStrategy;
    }


    public List<MatchInfo> getAllCurrentMatches() {
        List<MatchInfo> currentMatches = matchRepository.findAll();
        return sortingResultStrategy.sortMatches(currentMatches);
    }

    public String startNewMatch(String homeTeamName, String awayTeamName) {
        try {
            MatchInfo match = matchRepository.crateNewMatch(homeTeamName, awayTeamName);
            return "Match created: " + match.id();
        } catch (Exception e) {
            return "Failed to start new match: " + e.getMessage();
        }
    }

    public String finishMatch(String matchID) {
        try {
            matchRepository.removeMatch(matchID);
            return "Match finished: " + matchID;
        }catch (Exception e) {
            return "Failed to finish match: " + e.getMessage();
        }


    }
}