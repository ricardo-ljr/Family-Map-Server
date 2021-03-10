package Services;

import Result.MessageResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {

    @Test
    void clearResult() {
        ClearService clearService = new ClearService();
        MessageResult myResult = clearService.clearResult();
        MessageResult expected = new MessageResult("Cleared database successfully.");
        assertEquals(myResult.getMessage(), expected.getMessage());
    }

    @Test
    void clearResult2() {
        ClearService clearService = new ClearService();
        MessageResult myResult = clearService.clearResult();
        assertNotNull(myResult);
    }
}