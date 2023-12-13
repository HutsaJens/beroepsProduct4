/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.brogle.beroepsproduct4.view;

import com.brogle.beroepsproduct4.database.DatabaseConnector;
import com.brogle.beroepsproduct4.models.Patient;
import com.brogle.beroepsproduct4.models.Werknemer;
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

public class ViewWerknemer extends GridPane {

    private static final String CSS_BORDER_STYLE = "-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 8;";
    private static final String CSS_PADDING = "-fx-padding: 10;";

    private Label lblWerknemerInsert, lblBsnInsert, lblRangeInsert;
    private TextField tfldWerknemerInsert, tfldBsnInsert;
    private ComboBox cmbRangeInsert;

    private Label lblWerknemerUpdate, lblBsnUpdate, lblRangeUpdate;
    private TextField tfldWerknemerUpdate;
    private ComboBox cmbBsnUpdate, cmbRangeUpdate;

    private TableView<Werknemer> tableView;

    private Label lblPatientDelete;
    private ComboBox cmbDelete;

    private Button btnOpslaan, btnWijzigen, btnDelete;

    private GridPane dataGridOne, dataGridTwo, dataGridRead, dataGridDelete;

    public ViewWerknemer() {
        initializeUI();
        configureLayout();
    }

    private void initializeUI() {
        dataGridOne = createGridPane();
        dataGridTwo = createGridPane();
        dataGridRead = createGridPane();
        dataGridDelete = createGridPane();

        lblWerknemerInsert = new Label("Naam: ");
        lblBsnInsert = new Label("Bsn: ");
        lblRangeInsert = new Label("Range: ");

        lblWerknemerUpdate = new Label("Werknemer: ");
        lblBsnUpdate = new Label("Naam: ");
        lblRangeUpdate = new Label("Range: ");

        tfldWerknemerInsert = new TextField();
        tfldBsnInsert = new TextField();

        tfldWerknemerUpdate = new TextField();

        cmbRangeInsert = new ComboBox<String>();
        cmbRangeInsert.setItems(getBeddenData());

        cmbBsnUpdate = new ComboBox<Werknemer>();
        cmbBsnUpdate.setItems(getAllWerknemers());

        cmbRangeUpdate = new ComboBox<String>();
        cmbRangeUpdate.setItems(getBeddenData());

        lblPatientDelete = new Label("Patiënt");

        cmbDelete = new ComboBox<Patient>();
        cmbDelete.setItems(getAllWerknemers());

        btnOpslaan = new Button("Opslaan");
        btnOpslaan.setOnAction(e -> handleOpslaanButtonClicked());

        btnWijzigen = new Button("Bijwerken");
        btnWijzigen.setOnAction(e -> handleWijzigenButtonClicked());

        btnDelete = new Button("Verwijderen");
        btnDelete.setOnAction(e -> handleDeleteButtonClicked());

        tableView = new TableView<>();

    }

    private void configureLayout() {
        dataGridOne.add(lblWerknemerInsert, 0, 0);
        dataGridOne.add(lblBsnInsert, 0, 1);
        dataGridOne.add(lblRangeInsert, 0, 2);

        dataGridOne.add(tfldWerknemerInsert, 1, 0);
        dataGridOne.add(tfldBsnInsert, 1, 1);
        dataGridOne.add(cmbRangeInsert, 1, 2);
        dataGridOne.add(btnOpslaan, 1, 3);

        dataGridTwo.add(lblWerknemerUpdate, 0, 0);
        dataGridTwo.add(lblBsnUpdate, 0, 1);
        dataGridTwo.add(lblRangeUpdate, 0, 2);

        dataGridTwo.add(cmbBsnUpdate, 1, 0);
        dataGridTwo.add(tfldWerknemerUpdate, 1, 1);
        dataGridTwo.add(cmbRangeUpdate, 1, 2);
        dataGridTwo.add(btnWijzigen, 1, 3);

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
        String naam = tfldWerknemerInsert.getText();
        String bsn = tfldBsnInsert.getText();
        String range = (String) cmbRangeInsert.getValue();

        if (!naam.isEmpty() && !bsn.isEmpty() && range != null) {
            Werknemer werknemer = new Werknemer(naam, bsn, range);

            try ( DatabaseConnector connector = new DatabaseConnector()) {
                Connection connection = connector.getConnection();

                // Prepare the SQL statement with parameter placeholders
                String sql = "INSERT INTO werknemers (Werknemer, Bsn, BeddenRange) VALUES (?, ?, ?)";

                // Create a PreparedStatement with the SQL statement
                PreparedStatement statement = connection.prepareStatement(sql);

                // Set the values of the parameters
                statement.setString(1, werknemer.getNaam());
                statement.setString(2, werknemer.getBsn());
                statement.setString(3, werknemer.getBedden_range());

                // Execute the statement
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Patient data inserted into the database: " + werknemer);

                    // Clear the text fields and combo box selection
                    tfldWerknemerInsert.clear();
                    tfldBsnInsert.clear();
                    cmbRangeInsert.getSelectionModel().clearSelection();

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
        String bsn = ((Werknemer) cmbBsnUpdate.getValue()).getBsn();
        String naam = tfldWerknemerUpdate.getText();
        String range = (String) cmbRangeUpdate.getValue();

        try ( DatabaseConnector connector = new DatabaseConnector()) {
            Connection connection = connector.getConnection();

            // Create the base SQL UPDATE statement
            StringBuilder sqlBuilder = new StringBuilder("UPDATE WerknemerGegevens SET");

            // Prepare the dynamic parts of the SQL statement
            List<String> setClauses = new ArrayList<>();
            List<Object> parameters = new ArrayList<>();

            // Check and add the naam parameter if not null
            if (naam != null && !naam.isEmpty()) {
                setClauses.add("Werknemer = ?");
                parameters.add(naam);
            }

            // Check and add the range parameter if not null
            if (range != null && !range.isEmpty()) {
                setClauses.add("BeddenRange = ?");
                parameters.add(range);
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

        tfldWerknemerUpdate.clear();
        cmbBsnUpdate.getSelectionModel().clearSelection();
        cmbRangeUpdate.getSelectionModel().clearSelection();
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
        TableColumn<Werknemer, String> naamColumn = new TableColumn<>("Naam");
        TableColumn<Werknemer, String> bsnColumn = new TableColumn<>("BSN");
        TableColumn<Werknemer, String> rangeColumn = new TableColumn<>("Range");

        // Map the columns to the corresponding property in the Patient class
        naamColumn.setCellValueFactory(new PropertyValueFactory<>("naam"));
        bsnColumn.setCellValueFactory(new PropertyValueFactory<>("bsn"));
        rangeColumn.setCellValueFactory(new PropertyValueFactory<>("bedden_range"));

        // Add the columns to the TableView
        tableView.getColumns().addAll(naamColumn, bsnColumn, rangeColumn);

        tableView.setItems(getAllWerknemers());

    }

    private ObservableList<String> getBeddenData() {
        ObservableList<String> beddenData = FXCollections.observableArrayList();

        try ( DatabaseConnector connector = new DatabaseConnector()) {
            Connection connection = connector.getConnection();

            // Prepare the SQL statement
            String sql = "SELECT BeddenRange FROM Bedden ORDER BY CAST(SUBSTRING_INDEX(BeddenRange, '-', 1) AS UNSIGNED)";

            // Create a Statement
            Statement statement = connection.createStatement();

            // Execute the query
            ResultSet resultSet = statement.executeQuery(sql);

            // Iterate over the result set and add BeddenRange to the list
            while (resultSet.next()) {
                String beddenRange = resultSet.getString("BeddenRange");
                beddenData.add(beddenRange);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving bedden data from the database: " + e.getMessage());
        }

        return beddenData;
    }

    private ObservableList<Werknemer> getAllWerknemers() {
        ObservableList<Werknemer> werknemers = FXCollections.observableArrayList();

        try ( DatabaseConnector connector = new DatabaseConnector()) {
            Connection connection = connector.getConnection();

            // Prepare the SQL statement
            String sql = "SELECT * FROM werknemergegevens";

            // Create a Statement
            Statement statement = connection.createStatement();

            // Execute the query
            ResultSet resultSet = statement.executeQuery(sql);

            // Iterate over the result set and create Patient objects
            while (resultSet.next()) {
                String naam = resultSet.getString("Werknemer");
                String bsn = resultSet.getString("Bsn");
                String range = resultSet.getString("BeddenRange");

                // Create a Patient object with the retrieved data
                Werknemer w = new Werknemer(naam, bsn, range);

                // Add the patient to the list
                werknemers.add(w);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving patient data from the database: " + e.getMessage());
        }

        return werknemers;
    }

    private void updateTableView() {
        tableView.getItems().clear();
        tableView.setItems(getAllWerknemers());
    }

}
