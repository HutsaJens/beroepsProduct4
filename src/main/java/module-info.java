module com.brogle.beroepsproduct4 {
    requires javafx.controls;
    requires com.google.gson;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;
    
    exports com.brogle.beroepsproduct4;
    
    opens com.brogle.beroepsproduct4.models to com.google.gson, javafx.base;
    
}
