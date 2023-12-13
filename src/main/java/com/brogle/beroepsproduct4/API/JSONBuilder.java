package com.brogle.beroepsproduct4.API;

import com.brogle.beroepsproduct4.models.BedData;
import com.google.gson.Gson;
import java.sql.Date;

/**
 *
 * @author Jens
 */
public class JSONBuilder {
    
    private BedData bed;
    
    public BedData returnJsonData(String jsonString) {
        
        parseJson(jsonString);
               
        return bed;
    }
    
    public void parseJson(String jsonString) { 
        Gson gson = new Gson();
        bed = gson.fromJson(jsonString, BedData.class);        
    }
    

    //String data is de URL
    public void printJsonFields(String jsonString) {
            
        parseJson(jsonString);

        // Access the parsed data
        System.out.println("tolong: " + bed.isTolong());
        System.out.println("turns: " + bed.getTurns());
        System.out.println("lastturn: " + new Date(Long.parseLong(bed.getLastturn())*1000));

    }

}
