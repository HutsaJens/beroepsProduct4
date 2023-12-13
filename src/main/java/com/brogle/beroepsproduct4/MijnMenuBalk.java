package com.brogle.beroepsproduct4;

import com.brogle.beroepsproduct4.view.ViewBedData;
import com.brogle.beroepsproduct4.view.ViewPatient;
import com.brogle.beroepsproduct4.view.ViewSingleBedData;
import com.brogle.beroepsproduct4.view.ViewWerknemer;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class MijnMenuBalk extends MenuBar {

    private final Menu mGegevens, mData;
//    private final MenuItem miLogin, miNewAccount, miGegevens, miUitloggen;
    private final MenuItem miPatient, miWerknemer;
    private final MenuItem miBedData, miAPI;

    public MijnMenuBalk(GridPane root) {

        root.getChildren().clear();

        //Menubalk voor aanvullen van de identificaties is de database
        mGegevens = new Menu("Gegevens");

        miPatient = new MenuItem("Patiënt");
        miWerknemer = new MenuItem("Werknemer");

        mGegevens.getItems().addAll(miPatient, miWerknemer);

        miPatient.setOnAction(event -> {
            root.getChildren().clear();
            root.getChildren().add(new ViewPatient());
        });

        miWerknemer.setOnAction(event -> {
            root.getChildren().clear();
            root.getChildren().add(new ViewWerknemer());
        });

        //Menubalk voor Aanmelden
        mData = new Menu("Data");
        miBedData = new MenuItem("Bed Data");
        miAPI = new MenuItem("API");

        mData.getItems().addAll(miBedData, miAPI);

        miBedData.setOnAction(event -> {
            root.getChildren().clear();
            root.getChildren().add(new ViewBedData());
        });
        miAPI.setOnAction(event -> {
            root.getChildren().clear();
            root.getChildren().add(new ViewSingleBedData());
        });

        this.getMenus().addAll(mGegevens, mData);
    }
}
