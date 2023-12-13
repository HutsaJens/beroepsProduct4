package systemTest;

import com.brogle.beroepsproduct4.models.Bed;
import com.brogle.beroepsproduct4.models.Patient;
import com.brogle.beroepsproduct4.models.Werknemer;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.sql.Date;

public class TestModels {
    
    @Test
    public void testPatient() {
        // Set up test data
        String patientName = "John Doe";
        String patientBsn = "123456789";
        String patientWeight = "70 kg";
        String patientHeight = "180 cm";
        Double currentTime = (double) System.currentTimeMillis();

        // Create an instance of the Patient class
        Patient patient = new Patient(patientName, patientBsn, patientWeight, patientHeight, currentTime);

        // Validate the properties of the Patient instance
        assertEquals(patientName, patient.getNaam());
        assertEquals(patientBsn, patient.getBsn());
        assertEquals(patientWeight, patient.getGewicht());
        assertEquals(patientHeight, patient.getLengte());
        assertEquals(currentTime, patient.getTijd());
    }

    @Test
    public void testBed() {
        // Set up test data or environment
        Date currentTime = new Date(System.currentTimeMillis());
        String bedID = "A5";
        String numDraaien = "3";

        // Create an instance of the Bed class with the patient
        Bed bed = new Bed(null, bedID, numDraaien, currentTime, false);

        // Validate the system's response or behavior
        assertEquals(bedID, bed.getBedID());
        assertEquals(numDraaien, bed.getNumDraaien());
        assertEquals(currentTime, bed.getTijd());
    }
    
    @Test
    public void testWerknemer() {
        // Set up test data
        String werknemerName = "Jane Smith";
        String werknemerBSN = "123456789";
        String beddenRange = "A1-A10";
        
        // Create an instance of the Werknemer class
        Werknemer werknemer = new Werknemer(werknemerName, werknemerBSN, beddenRange);
        
        // Verify the properties of the Werknemer object
        assertEquals(werknemerName, werknemer.getNaam());
        assertEquals(werknemerBSN, werknemer.getBsn());
        assertEquals(beddenRange, werknemer.getBedden_range());
    }
    
}
