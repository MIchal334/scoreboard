import infra.MatchRepositoryInMemory;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchRepositoryInMemoryTest {
    @Test
    public void testCreateNewMatchHappyPath() {
        //GIVEN
        String homeTeamName = "homeTeamName ";
        String awayTeamName = "awayTeamName";
        var matchRepo = new MatchRepositoryInMemory();

        // WHEN
        var startAmount = matchRepo.findAll().size();
        matchRepo.crateNewMatch(homeTeamName, awayTeamName);
        //THEN
        assertEquals(startAmount + 1, matchRepo.findAll().size());
    }
}
