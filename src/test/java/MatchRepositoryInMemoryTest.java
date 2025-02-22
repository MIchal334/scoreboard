import core.model.MatchInfo;
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


}



