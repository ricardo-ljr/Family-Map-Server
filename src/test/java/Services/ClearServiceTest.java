package Services;

import Result.SuccessMessageResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {

    @Test
    void clearResult() {
        ClearService clearService = new ClearService();
        SuccessMessageResult myResult = clearService.clearResult();
        SuccessMessageResult expected = new SuccessMessageResult("Cleared database successfully.");
        assertEquals(myResult.getMessage(), expected.getMessage());
    }

    @Test
    void clearResult2() {
        ClearService clearService = new ClearService();
        SuccessMessageResult myResult = clearService.clearResult();
        assertNotNull(myResult);
    }
}