package unitTest;

import com.brogle.beroepsproduct4.API.DataHelper;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestDataHelper {

    @Test
    public void testGetJSONData() {
        // Create an instance of your class
        DataHelper dh = new DataHelper();

        // Call the method under test
        String result = dh.getJSONData();
        
        // Expected output
        String expected = "{\"tolong\":false,\"turns\":0,\"lastturn\":0}";

        // Verify the result (modify this according to your expected output)
        assertEquals(expected, result);
    }
}




