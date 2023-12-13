package com.brogle.beroepsproduct4.view;

import com.brogle.beroepsproduct4.API.DataHelper;
import com.brogle.beroepsproduct4.API.JSONBuilder;
import com.brogle.beroepsproduct4.database.DatabaseConnector;
import com.brogle.beroepsproduct4.models.Bed;
import com.brogle.beroepsproduct4.models.BedData;
import com.brogle.beroepsproduct4.models.Patient;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Jens
 */
public class ViewBedData extends GridPane {

    private static final String CSS_BORDER_STYLE = "-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 8;";
    private static final String CSS_PADDING = "-fx-padding: 10;";

    private GridPane gridInsert, gridUpdate, gridRead, gridDelete;

    // Insert
    private Label lblBedInsert, lblBsnInsert, lblChooseBedInsert;
    private TextField tfldBedInsert;
    private ComboBox cmbBsnInsert, cmbChooseBedInsert;
    private Button btnInsert;

    // Update
    private Label lblBedUpdate, lblBsnUpdate, lblChooseBedUpdate;
    private ComboBox cmbBedNumUpdate, cmbBsnUpdate, cmbBedUpdate;
    private Button btnUpdate;

    // Read
    private TableView tableView;

    // Delete
    private Label lblBedDelete;
    private ComboBox cmbBedDelete;
    private Button btnDelete;

    public ViewBedData() {
        initializeUI();
        configureLayout();
    }

    private void initializeUI() {
        gridInsert = createGridPane();
        gridUpdate = createGridPane();
        gridRead = createGridPane();
        gridDelete = createGridPane();

        // Insert
        lblBedInsert = new Label("Bed ID: ");
        lblBsnInsert = new Label("Bsn: ");
        lblChooseBedInsert = new Label("Besturing: ");

        tfldBedInsert = new TextField();

        cmbChooseBedInsert = new ComboBox<BedData>();
        cmbChooseBedInsert.setItems(getAllBesturing());

        cmbBsnInsert = new ComboBox<Patient>();
        cmbBsnInsert.setItems(getAllPatienten());

        btnInsert = new Button("Opslaan");
        btnInsert.setOnAction(e -> handleOpslaanButtonClicked());

        // Update
        lblBedUpdate = new Label("Bed ID: ");
        lblBsnUpdate = new Label("Bsn: ");
        lblChooseBedUpdate = new Label("Besturing: ");

        cmbBedNumUpdate = new ComboBox<Bed>();
        cmbBedNumUpdate.setItems(getAllBedden());

        cmbBsnUpdate = new ComboBox<Patient>();
        cmbBsnUpdate.setItems(getAllPatienten());

        cmbBedUpdate = new ComboBox<BedData>();
        cmbBedUpdate.setItems(getAllBesturing());

        btnUpdate = new Button("Wijzigen");
//        btnUpdate.setOnAction(e -> handleWijzigenButtonClicked());

        // Read
        tableView = new TableView<>();

        // Delete
        lblBedDelete = new Label("Bed ID: ");

        cmbBedDelete = new ComboBox<Bed>();
        cmbBedDelete.setItems(getAllBedden());

        btnDelete = new Button("Verwijderen");
//        btnDelete.setOnAction(e -> handleDeleteButtonClicked());

    }

    private void configureLayout() {

        // Insert
        gridInsert.add(lblBedInsert, 0, 0);
        gridInsert.add(lblBsnInsert, 0, 1);
        gridInsert.add(lblChooseBedInsert, 0, 2);

        gridInsert.add(tfldBedInsert, 1, 0);
        gridInsert.add(cmbBsnInsert, 1, 1);
        gridInsert.add(cmbChooseBedInsert, 1, 2);

        gridInsert.add(btnInsert, 1, 3);

        // Update
        gridUpdate.add(lblBedUpdate, 0, 0);
        gridUpdate.add(lblBsnUpdate, 0, 1);
        gridUpdate.add(lblChooseBedUpdate, 0, 2);

        gridUpdate.add(cmbBedNumUpdate, 1, 0);
        gridUpdate.add(cmbBsnUpdate, 1, 1);
        gridUpdate.add(cmbBedUpdate, 1, 2);

        gridUpdate.add(btnUpdate, 1, 3);

        showTableView();

        gridRead.add(tableView, 0, 0);

        gridDelete.add(lblBedDelete, 0, 0);
        gridDelete.add(cmbBedDelete, 1, 0);
        gridDelete.add(btnDelete, 1, 1);

        gridInsert.setStyle(CSS_BORDER_STYLE + CSS_PADDING);
        gridUpdate.setStyle(CSS_BORDER_STYLE + CSS_PADDING);
        gridRead.setStyle(CSS_BORDER_STYLE + CSS_PADDING);
        gridDelete.setStyle(CSS_BORDER_STYLE + CSS_PADDING);

        this.setHgap(5);
        this.setVgap(5);

        this.add(gridInsert, 0, 0);
        this.add(gridUpdate, 1, 0);
        this.add(gridRead, 0, 1);
        this.add(gridDelete, 1, 1);
    }

    private GridPane createGridPane() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        return grid;
    }

    private void handleOpslaanButtonClicked() {
        String bedID = tfldBedInsert.getText();
        Patient patient = (Patient) cmbBsnInsert.getValue();
        BedData bedData = (BedData) cmbChooseBedInsert.getValue();

        if (!bedID.isEmpty() && patient != null && bedData != null) {

            long unixTimestamp = Long.parseLong(bedData.getLastturn());
            
            // Create a Date object from the Unix timestamp
            Date date = new Date(unixTimestamp * 1000);
            
            // Convert the Date object to a java.sql.Date object
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            Bed bed = new Bed(patient, bedID, bedData.getTurns(), sqlDate, bedData.isTolong());

            try ( DatabaseConnector connector = new DatabaseConnector()) {
                Connection connection = connector.getConnection();

                // Prepare the SQL statement with parameter placeholders
                String sql = "INSERT INTO bedinformatie (Bed, Bsn, NumDraaien, Tijd, toLong) VALUES (?, ?, ?, ?, ?)";

                // Create a PreparedStatement with the SQL statement
                PreparedStatement statement = connection.prepareStatement(sql);

                // Set the values of the parameters
                statement.setString(1, bed.getBedID());
                statement.setString(2, bed.getPatient().getBsn());
                statement.setString(3, bed.getNumDraaien());
                statement.setDate(4, bed.getTijd());
                statement.setBoolean(5, bed.getToLong());

                // Execute the statement
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Patient data inserted into the database: " + patient);

                    // Clear the text fields and combo box selection
                    tfldBedInsert.clear();
                    cmbBsnInsert.getSelectionModel().clearSelection();
                    cmbChooseBedInsert.getSelectionModel().clearSelection();

                    updateTableView();

                } else {
                    System.err.println("Error inserting patient data into the database: No rows affected.");
                }

            } catch (SQLException e) {
                System.err.println("Error inserting patient data into the database: " + e.getMessage());
            }
        }
    }

    private ObservableList<Patient> getAllPatienten() {
        ObservableList<Patient> patienten = FXCollections.observableArrayList();

        try ( DatabaseConnector connector = new DatabaseConnector()) {
            Connection connection = connector.getConnection();

            // Prepare the SQL statement
            String sql = "SELECT * FROM PatientGegevens";

            // Create a Statement
            Statement statement = connection.createStatement();

            // Execute the query
            ResultSet resultSet = statement.executeQuery(sql);

            // Iterate over the result set and create Patient objects
            while (resultSet.next()) {
                String naam = resultSet.getString("Patient");
                String bsn = resultSet.getString("Bsn");
                String gewicht = resultSet.getString("Gewicht");
                String lengte = resultSet.getString("Lengte");
                String tijd = resultSet.getString("Tijd");

                // Create a Patient object with the retrieved data
                Patient patient = new Patient(naam, bsn, gewicht, lengte, Double.valueOf(tijd));

                // Add the patient to the list
                patienten.add(patient);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving patient data from the database: " + e.getMessage());
        }

        return patienten;
    }

    private ObservableList<Bed> getAllBedden() {
        ObservableList<Bed> bedden = FXCollections.observableArrayList();

        try ( DatabaseConnector connector = new DatabaseConnector()) {
            Connection connection = connector.getConnection();

            // Prepare the SQL statement
            String sql = "SELECT * FROM BedInformatie";

            // Create a Statement
            Statement statement = connection.createStatement();

            // Execute the query
            ResultSet resultSet = statement.executeQuery(sql);

            // Iterate over the result set and create Patient objects
            while (resultSet.next()) {
                String bedID = resultSet.getString("Bed");
                String bsn = resultSet.getString("Bsn");
                String numDraaien = resultSet.getString("NumDraaien");
                Date tijd = resultSet.getDate("Tijd");
                Boolean toLong = resultSet.getBoolean("toLong");

                Patient p = getMatchingPatient(bsn);

                // Create a Bed object with the retrieved data                
                Bed bed = new Bed(p, bedID, numDraaien, tijd, toLong);

                // Add the patient to the list
                bedden.add(bed);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving Bedden data from the database: " + e.getMessage());
        }

        return bedden;
    }

    private Patient getMatchingPatient(String bsn) {
        Patient patient = null;

        try ( DatabaseConnector connector = new DatabaseConnector()) {
            Connection connection = connector.getConnection();

            // Prepare the SQL statement
            String sql = "SELECT * FROM PatientGegevens WHERE Bsn = ?";

            // Create a PreparedStatement
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, bsn);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Iterate over the result set and create Patient objects
            while (resultSet.next()) {
                String naam = resultSet.getString("Patient");
                String gewicht = resultSet.getString("Gewicht");
                String lengte = resultSet.getString("Lengte");
                String tijd = resultSet.getString("Tijd");

                // Create a Patient object with the retrieved data
                patient = new Patient(naam, bsn, gewicht, lengte, Double.valueOf(tijd));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving matching patient data from the database: " + e.getMessage());
        }

        return patient;
    }

    private ObservableList<BedData> getAllBesturing() {
        ObservableList<BedData> bed = FXCollections.observableArrayList();

        String json = new DataHelper().getJSONData();
        BedData b = new JSONBuilder().returnJsonData(json);

        bed.add(b);

        return bed;
    }

    private void showTableView() {
        TableColumn<Bed, String> bedID = new TableColumn<>("Bed");
        TableColumn<Bed, String> bsnColumn = new TableColumn<>("BSN");
        TableColumn<Bed, String> numDraaien = new TableColumn<>("Aantal draaien");
        TableColumn<Bed, Date> lastTurn = new TableColumn<>("Laatste draai");
        TableColumn<Bed, Boolean> toLong = new TableColumn<>("Te lang");

        // Map the columns to the corresponding property in the Bed class
        bedID.setCellValueFactory(new PropertyValueFactory<>("bedID"));
        bsnColumn.setCellValueFactory(new PropertyValueFactory<>("patient.bsn"));
        numDraaien.setCellValueFactory(new PropertyValueFactory<>("numDraaien"));
        lastTurn.setCellValueFactory(new PropertyValueFactory<>("tijd"));
        toLong.setCellValueFactory(new PropertyValueFactory<>("toLong"));

        // Use a Callback for the toLong column to create a BooleanProperty
        toLong.setCellValueFactory(cellData -> {
            Bed bed = cellData.getValue();
            boolean isToLong = bed.getToLong(); // Assuming there is a getter method for 'toLong' boolean property in Bed class
            return new SimpleBooleanProperty(isToLong);
        });

// Set the cell factory for the toLong column to display as checkboxes
        toLong.setCellFactory(CheckBoxTableCell.forTableColumn(toLong));

        // Use a Callback for the bsnColumn to extract the nested property value
        bsnColumn.setCellValueFactory(cellData -> {
            Bed bed = cellData.getValue();
            Patient patient = bed.getPatient();
            if (patient != null) {
                return new SimpleStringProperty(patient.getBsn());
            } else {
                return new SimpleStringProperty("");
            }
        });

        // Add the columns to the TableView
        tableView.getColumns().addAll(bedID, bsnColumn, numDraaien, lastTurn, toLong);
        tableView.setItems(getAllBedden());
    }
    
    private void updateTableView() {
        tableView.getItems().clear();
        tableView.setItems(getAllPatienten());
    }

}
