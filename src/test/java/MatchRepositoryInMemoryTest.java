import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchRepositoryInMemoryTest {
    @Test
    public void testCreateNewMatchHappyPath() {
        //GIVEN
        var matchRepo = new MatchRepositoryInMemory();

        // WHEN
        var startAmount = matchRepository.findAll().size();
        matchRepo.crateNewMatch();
        //THEN
        assertEquals(startAmount + 1, matchRepository.findAll().size());
    }
}
