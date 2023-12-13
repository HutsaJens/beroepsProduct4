package com.brogle.beroepsproduct4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



/**
 * JavaFX App
 */
public class App extends Application {
    
    @Override
    public void start(Stage stage) {
        
//        Properties props = new Properties();
//        try (FileInputStream in = new FileInputStream("database.properties")) {
//            props.load(in);
//        } catch (Exception ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        String url = props.getProperty("url");
//        String user = props.getProperty("user");
//        String password = props.getProperty("password");
//        
//        System.out.println();
//        
//        System.out.println(url);
//        System.out.println(user);
//        System.out.println(password);
       
        GridPane root   = new GridPane();
        MenuBar menubar = new MijnMenuBalk(root);
        VBox vbox       = new VBox(menubar, root);
        Scene scene     = new Scene(vbox, 800, 500);
        
        VBox.setVgrow(root, Priority.ALWAYS);
        
        stage.getIcons().add(new Image("file:icon.png"));
        stage.setScene(scene);
        stage.setTitle("Mdp App");
        stage.show();
        
    }

    public static void main(String[] args) {
        launch();
    }

}