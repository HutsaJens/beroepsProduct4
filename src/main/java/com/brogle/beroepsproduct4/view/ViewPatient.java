/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.brogle.beroepsproduct4.view;

import com.brogle.beroepsproduct4.database.DatabaseConnector;
import com.brogle.beroepsproduct4.models.Patient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class ViewPatient extends GridPane {

    private static final String CSS_BORDER_STYLE = "-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 8;";
    private static final String CSS_PADDING = "-fx-padding: 10;";

    private Label lblPatientNaamOne, lblBsnOne, lblGewichtOne, lblLengteOne, lblTijdOne;
    private TextField tfldPatientNaamOne, tfldBsnOne, tfldGewichtOne, tfldLengteOne;
    private ComboBox cmbTijdOne;

    private Label lblBsnTwo, lblGewichtTwo, lblLengteTwo, lblTijdTwo;
    private TextField tfldGewichtTwo, tfldLengteTwo;
    private ComboBox cmbBsnTwo, cmbTijdTwo;

    private TableView<Patient> tableView;

    private Label lblPatientDelete;
    private ComboBox cmbDelete;

    private Button btnOpslaan, btnWijzigen, btnDelete;

    private GridPane dataGridOne, dataGridTwo, dataGridRead, dataGridDelete;

    public ViewPatient() {
        initializeUI();
        configureLayout();
    }

    private void initializeUI() {
        dataGridOne = createGridPane();
        dataGridTwo = createGridPane();
        dataGridRead = createGridPane();
        dataGridDelete = createGridPane();

        lblPatientNaamOne = new Label("Naam: ");
        lblBsnOne = new Label("Bsn: ");
        lblGewichtOne = new Label("Gewicht: ");
        lblLengteOne = new Label("Lengte: ");
        lblTijdOne = new Label("Draaitijd: ");

        lblBsnTwo = new Label("Patient: ");
        lblGewichtTwo = new Label("Gewicht: ");
        lblLengteTwo = new Label("Lengte: ");
        lblTijdTwo = new Label("Draaitijd: ");

        tfldPatientNaamOne = new TextField();
        tfldBsnOne = new TextField();
        tfldGewichtOne = new TextField();
        tfldLengteOne = new TextField();

        tfldGewichtTwo = new TextField();
        tfldLengteTwo = new TextField();

        cmbTijdOne = new ComboBox<Double>();
        cmbTijdOne.setItems(getTimeIntervals());

        cmbBsnTwo = new ComboBox<Patient>();
        cmbBsnTwo.setItems(getAllPatienten());

        cmbTijdTwo = new ComboBox<Double>();
        cmbTijdTwo.setItems(getTimeIntervals());

        lblPatientDelete = new Label("Patiënt");

        cmbDelete = new ComboBox<Patient>();
        cmbDelete.setItems(getAllPatienten());

        btnOpslaan = new Button("Opslaan");
        btnOpslaan.setOnAction(e -> handleOpslaanButtonClicked());

        btnWijzigen = new Button("Bijwerken");
        btnWijzigen.setOnAction(e -> handleWijzigenButtonClicked());

        btnDelete = new Button("Verwijderen");
        btnDelete.setOnAction(e -> handleDeleteButtonClicked());

        tableView = new TableView<>();

    }

    private void configureLayout() {
        dataGridOne.add(lblPatientNaamOne, 0, 0);
        dataGridOne.add(lblBsnOne, 0, 1);
        dataGridOne.add(lblGewichtOne, 0, 2);
        dataGridOne.add(lblLengteOne, 0, 3);
        dataGridOne.add(lblTijdOne, 0, 4);

        dataGridOne.add(tfldPatientNaamOne, 1, 0);
        dataGridOne.add(tfldBsnOne, 1, 1);
        dataGridOne.add(tfldGewichtOne, 1, 2);
        dataGridOne.add(tfldLengteOne, 1, 3);
        dataGridOne.add(cmbTijdOne, 1, 4);
        dataGridOne.add(btnOpslaan, 1, 5);

        dataGridTwo.add(lblBsnTwo, 0, 0);
        dataGridTwo.add(lblGewichtTwo, 0, 1);
        dataGridTwo.add(lblLengteTwo, 0, 2);
        dataGridTwo.add(lblTijdTwo, 0, 3);

        dataGridTwo.add(cmbBsnTwo, 1, 0);
        dataGridTwo.add(tfldGewichtTwo, 1, 1);
        dataGridTwo.add(tfldLengteTwo, 1, 2);
        dataGridTwo.add(cmbTijdTwo, 1, 3);
        dataGridTwo.add(btnWijzigen, 1, 4);

        showTableView();

        dataGridRead.add(tableView, 0, 0);

        dataGridDelete.add(lblPatientDelete, 0, 0);
        dataGridDelete.add(cmbDelete, 0, 1);
        dataGridDelete.add(btnDelete, 1, 1);

        dataGridOne.setStyle(CSS_BORDER_STYLE + CSS_PADDING);
        dataGridTwo.setStyle(CSS_BORDER_STYLE + CSS_PADDING);
        dataGridRead.setStyle(CSS_BORDER_STYLE + CSS_PADDING);
        dataGridDelete.setStyle(CSS_BORDER_STYLE + CSS_PADDING);

        this.setHgap(5);
        this.setVgap(5);
        
        this.add(dataGridOne, 0, 0);
        this.add(dataGridTwo, 1, 0);
        this.add(dataGridRead, 0, 1);
        this.add(dataGridDelete, 1, 1);
    }

    private GridPane createGridPane() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10));
        return grid;
    }

    private void handleOpslaanButtonClicked() {
        String naam = tfldPatientNaamOne.getText();
        String bsn = tfldBsnOne.getText();
        String gewicht = tfldGewichtOne.getText();
        String lengte = tfldLengteOne.getText();
        Double tijd = (Double) cmbTijdOne.getValue();

        if (!naam.isEmpty() && !bsn.isEmpty() && !gewicht.isEmpty() && !lengte.isEmpty() && tijd != null) {
            Patient patient = new Patient(naam, bsn, gewicht, lengte, tijd);

            try ( DatabaseConnector connector = new DatabaseConnector()) {
                Connection connection = connector.getConnection();

                // Prepare the SQL statement with parameter placeholders
                String sql = "INSERT INTO PatientGegevens (Patient, Bsn, Gewicht, Lengte, Tijd) VALUES (?, ?, ?, ?, ?)";

                // Create a PreparedStatement with the SQL statement
                PreparedStatement statement = connection.prepareStatement(sql);

                // Set the values of the parameters
                statement.setString(1, patient.getNaam());
                statement.setString(2, patient.getBsn());
                statement.setString(3, patient.getGewicht());
                statement.setString(4, patient.getLengte());
                statement.setDouble(5, patient.getTijd());

                // Execute the statement
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Patient data inserted into the database: " + patient);

                    // Clear the text fields and combo box selection
                    tfldPatientNaamOne.clear();
                    tfldBsnOne.clear();
                    tfldGewichtOne.clear();
                    tfldLengteOne.clear();
                    cmbTijdOne.getSelectionModel().clearSelection();

                    updateTableView();

                } else {
                    System.err.println("Error inserting patient data into the database: No rows affected.");
                }

            } catch (SQLException e) {
                System.err.println("Error inserting patient data into the database: " + e.getMessage());
            }
        }
    }

    private void handleWijzigenButtonClicked() {
        String bsn = ((Patient) cmbBsnTwo.getValue()).getBsn();
        String gewicht = tfldGewichtTwo.getText();
        String lengte = tfldLengteTwo.getText();
        Double tijd = (Double) cmbTijdTwo.getValue();

        try ( DatabaseConnector connector = new DatabaseConnector()) {
            Connection connection = connector.getConnection();

            // Create the base SQL UPDATE statement
            StringBuilder sqlBuilder = new StringBuilder("UPDATE PatientGegevens SET");

            // Prepare the dynamic parts of the SQL statement
            List<String> setClauses = new ArrayList<>();
            List<Object> parameters = new ArrayList<>();

            // Check and add the gewicht parameter if not null
            if (gewicht != null && !gewicht.isEmpty()) {
                setClauses.add("Gewicht = ?");
                parameters.add(gewicht);
            }

            // Check and add the lengte parameter if not null
            if (lengte != null && !lengte.isEmpty()) {
                setClauses.add("Lengte = ?");
                parameters.add(lengte);
            }

            // Check and add the tijd parameter if not null
            if (tijd != null) {
                setClauses.add("Tijd = ?");
                parameters.add(tijd);
            }

            // Append the SET clauses to the SQL statement
            sqlBuilder.append(" ").append(String.join(", ", setClauses));

            // Append the WHERE clause
            sqlBuilder.append(" WHERE Bsn = ?");

            // Create a PreparedStatement
            PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString());

            // Set the parameter values
            int parameterIndex = 1;
            for (Object parameter : parameters) {
                statement.setObject(parameterIndex++, parameter);
            }
            statement.setString(parameterIndex, bsn);

            // Execute the UPDATE statement
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Record updated successfully.");
                // Handle any additional logic after the update if needed
            } else {
                System.out.println("No record found to update.");
                // Handle the case where no record was found for the given BSN
            }

        } catch (SQLException e) {
            System.err.println("Error updating record in the database: " + e.getMessage());
        }

        updateTableView();

        tfldGewichtTwo.clear();
        tfldLengteTwo.clear();
        cmbTijdTwo.getSelectionModel().clearSelection();
    }

    private void handleDeleteButtonClicked() {
        // Get the BSN value from the selected Patient
        String bsn = ((Patient) cmbDelete.getValue()).getBsn();

        try ( DatabaseConnector connector = new DatabaseConnector()) {
            Connection connection = connector.getConnection();

            // Prepare the SQL statement
            String sql = "DELETE FROM PatientGegevens WHERE Bsn = ?";

            // Create a PreparedStatement
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, bsn);

            // Execute the DELETE statement
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Records deleted successfully.");
                updateTableView();

                // Handle any additional logic after deletion if needed
            } else {
                System.out.println("No records found to delete.");
                // Handle the case where no records were found for the given BSN
            }

        } catch (SQLException e) {
            System.err.println("Error deleting records from the database: " + e.getMessage());
        }
    }

    private void showTableView() {
        TableColumn<Patient, String> naamColumn = new TableColumn<>("Naam");
        TableColumn<Patient, String> bsnColumn = new TableColumn<>("BSN");
        TableColumn<Patient, String> gewichtColumn = new TableColumn<>("Gewicht");
        TableColumn<Patient, String> lengteColumn = new TableColumn<>("Lengte");
        TableColumn<Patient, Double> tijdColumn = new TableColumn<>("Draaitijd");

        // Map the columns to the corresponding property in the Patient class
        naamColumn.setCellValueFactory(new PropertyValueFactory<>("naam"));
        bsnColumn.setCellValueFactory(new PropertyValueFactory<>("bsn"));
        gewichtColumn.setCellValueFactory(new PropertyValueFactory<>("gewicht"));
        lengteColumn.setCellValueFactory(new PropertyValueFactory<>("lengte"));
        tijdColumn.setCellValueFactory(new PropertyValueFactory<>("tijd"));

        // Add the columns to the TableView
        tableView.getColumns().addAll(naamColumn, bsnColumn, gewichtColumn, lengteColumn, tijdColumn);

        tableView.setItems(getAllPatienten());

    }

    private ObservableList<Double> getTimeIntervals() {
        ObservableList<Double> intervals = FXCollections.observableArrayList();
        double value = 0.25;
        while (value <= 6.0) {
            intervals.add(value);
            value += 0.25;
        }
        return intervals;
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

    private void updateTableView() {
        tableView.getItems().clear();
        tableView.setItems(getAllPatienten());
    }

}
