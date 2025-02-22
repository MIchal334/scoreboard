import core.model.MatchInfo;
import core.model.MatchResult;
import infra.MatchRepositoryInMemory;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchRepositoryInMemoryTest {
    @Test
    public void testCreateNewMatchHappyPath() {
        //GIVEN
        String homeTeamName = "homeTeamName";
        String awayTeamName = "awayTeamName";
        var matchRepo = new MatchRepositoryInMemory();

        // WHEN
        var startAmount = matchRepo.findAll().size();
        matchRepo.crateNewMatch(homeTeamName, awayTeamName);
        //THEN
        assertEquals(startAmount + 1, matchRepo.findAll().size());
    }

    @Test
    public void testCreateNewMatchWithSameTeamName() {
        //GIVEN
        String homeTeamName = "homeTeamName";
        String awayTeamName = "homeTeamName";
        var matchRepo = new MatchRepositoryInMemory();

        // WHEN
        var startAmount = matchRepo.findAll().size();
        matchRepo.crateNewMatch(homeTeamName, awayTeamName);
        //THEN
        assertEquals(startAmount, matchRepo.findAll().size());
    }


    @Test
    public void testCreateNewMatchWithNullTeamName() {
        //GIVEN
        String homeTeamName = null;
        String awayTeamName = null;
        var matchRepo = new MatchRepositoryInMemory();

        // WHEN
        var startAmount = matchRepo.findAll().size();
        matchRepo.crateNewMatch(homeTeamName, awayTeamName);
        //THEN
        assertEquals(startAmount, matchRepo.findAll().size());
    }


    @Test
    public void testCreateNewMatchWhenTeamNameExists() {
        //GIVEN
        String homeTeamName = "homeTeamName";
        String awayTeamName = "awayTeamName";
        var matchRepo = new MatchRepositoryInMemory();

        // WHEN
        var startAmount = matchRepo.findAll().size();
        matchRepo.crateNewMatch(homeTeamName, awayTeamName);
        matchRepo.crateNewMatch(homeTeamName, awayTeamName);
        //THEN
        assertEquals(startAmount + 1, matchRepo.findAll().size());
    }

    @Test
    public void testRemoveMatchHappyPath() {
        //GIVEN
        String homeTeamName = "homeTeamName";
        String awayTeamName = "awayTeamName";
        var matchRepo = new MatchRepositoryInMemory();
        var matchInfo = matchRepo.crateNewMatch(homeTeamName, awayTeamName);

        // WHEN
        var startAmount = matchRepo.findAll().size();
        matchRepo.removeMatch(matchInfo.id());
        //THEN
        assertEquals(startAmount - 1, matchRepo.findAll().size());
    }

    @Test
    public void testRemoveMatchWithNotExistingMatchId() {
        //GIVEN
        String homeTeamName = "homeTeamName";
        String awayTeamName = "awayTeamName";
        var matchRepo = new MatchRepositoryInMemory();
        var matchInfo = matchRepo.crateNewMatch(homeTeamName, awayTeamName);

        // WHEN
        var startAmount = matchRepo.findAll().size();
        matchRepo.removeMatch("notExistingMatchId");
        //THEN
        assertEquals(startAmount, matchRepo.findAll().size());
    }


    @Test
    public void testUpdateMatchResultHappyPath() {
        //GIVEN
        String homeTeamName = "homeTeamName";
        String awayTeamName = "awayTeamName";
        int homeScore = 1;
        int awayScore = 1;
        var matchRepo = new MatchRepositoryInMemory();
        var matchInfo = matchRepo.crateNewMatch(homeTeamName, awayTeamName);
        var newResult = new MatchResult(homeScore, awayScore);

        // WHEN
        matchRepo.updateResult(matchInfo.id(), newResult);
        var currentInfo = matchRepo.findAll().stream().filter(match -> match.id().equals(matchInfo.id())).findFirst().get();

        //THEN
        assertEquals(currentInfo.matchResult().awayTeamScore(), awayScore);
        assertEquals(currentInfo.matchResult().homeTeamScore(), homeScore);
    }


}



