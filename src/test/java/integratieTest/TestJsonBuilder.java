//package integratieTest;
//
//import com.brogle.beroepsproduct4.API.DataHelper;
//import com.brogle.beroepsproduct4.API.JSONBuilder;
//import com.brogle.beroepsproduct4.models.BedData;
//import static org.junit.Assert.assertEquals;
//import org.junit.Test;
//
//public class TestJsonBuilder {
//    
//    @Test
//    public void testReturnJsonData() {
//        // Create an instance of your class
//        DataHelper dh = new DataHelper();
//        JSONBuilder jb = new JSONBuilder();
//
//        // Call the method under test
//        String jsonData = dh.getJSONData();
//        BedData resultBed = jb.returnJsonData(jsonData);
//        
//        // Expected output
//        BedData expectedBed = new BedData(false, "0", "0");
//
//        // Verify the result (modify this according to your expected output)
//        assertEquals(expectedBed, resultBed);
//    }
//}
