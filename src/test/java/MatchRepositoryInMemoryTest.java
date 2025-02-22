import core.model.MatchResult;
import infra.MatchRepositoryInMemory;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(awayScore, currentInfo.matchResult().awayTeamScore());
        assertEquals(homeScore, currentInfo.matchResult().homeTeamScore());
    }


    @Test
    public void testUpdateWithNotExistingMatchId() {
        //GIVEN
        int homeScore = 1;
        int awayScore = 1;
        var matchRepo = new MatchRepositoryInMemory();
        var newResult = new MatchResult(homeScore, awayScore);

        // WHEN
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        matchRepo.updateResult("id", newResult);

        //THEN
        assertTrue(outContent.toString().toLowerCase().contains("not found"));
    }


    @Test
    public void testUpdateMatchResultResultBelowZero() {
        //GIVEN
        String homeTeamName = "homeTeamName";
        String awayTeamName = "awayTeamName";
        int homeScoreFirst = 0;
        int awayScoreFirst = 2;
        int homeScoreSecond = -1;
        int awayScoreSecond = 2;
        var matchRepo = new MatchRepositoryInMemory();
        var matchInfo = matchRepo.crateNewMatch(homeTeamName, awayTeamName);
        var firstResult = new MatchResult(homeScoreFirst, awayScoreFirst);
        var secondResult = new MatchResult(homeScoreSecond, awayScoreSecond);
        // WHEN
        matchRepo.updateResult(matchInfo.id(), firstResult);
        matchRepo.updateResult(matchInfo.id(), secondResult);
        var currentInfo = matchRepo.findAll().stream().filter(match -> match.id().equals(matchInfo.id())).findFirst().get();

        //THEN
        assertEquals(awayScoreFirst, currentInfo.matchResult().awayTeamScore());
        assertEquals(homeScoreFirst, currentInfo.matchResult().homeTeamScore());
    }

    @Test
    public void testFindAll() {
        //GIVEN
        String homeTeamName1 = "homeTeamName1";
        String awayTeamName1 = "awayTeamName1";
        String homeTeamName2 = "homeTeamName2";
        String awayTeamName2 = "awayTeamName2";
        var matchRepo = new MatchRepositoryInMemory();
        matchRepo.crateNewMatch(homeTeamName1, awayTeamName1);
        matchRepo.crateNewMatch(homeTeamName2, awayTeamName2);

        // WHEN
        var allMatches = matchRepo.findAll();

        // THEN
        assertEquals(2, allMatches.size());
    }
}



