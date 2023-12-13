package com.brogle.beroepsproduct4.view;

import com.brogle.beroepsproduct4.API.DataHelper;
import com.brogle.beroepsproduct4.API.JSONBuilder;
import com.brogle.beroepsproduct4.models.BedData;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 *
 * @author Jens
 */
public class ViewSingleBedData extends GridPane {

    private static final String CSS_BORDER_STYLE = "-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 8;";
    private static final String CSS_PADDING = "-fx-padding: 10;";

    private final GridPane grid;

    private TableView<BedData> tableview;

    public ViewSingleBedData() {

        grid = createGridPane();

        grid.setStyle(CSS_BORDER_STYLE + CSS_PADDING);

        tableview = new TableView<>();

        showTableView();

        // Create a Timeline to refresh the TableView every 2 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> refreshTableView(tableview)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        grid.add(tableview, 0, 0);

        this.setHgap(5);
        this.setVgap(5);

        this.add(grid, 0, 0);
    }

    private GridPane createGridPane() {
        GridPane tempGrid = new GridPane();
        tempGrid.setHgap(5);
        tempGrid.setVgap(5);
        return tempGrid;
    }

    private void showTableView() {
        TableColumn<BedData, Boolean> toLongColumn = new TableColumn<>("Te Lang");
        TableColumn<BedData, String> turnsColumn = new TableColumn<>("Draaien");
        TableColumn<BedData, String> lastTurnColumn = new TableColumn<>("Laatste Draai");

        // Map the columns to the corresponding property in the BedData class
        turnsColumn.setCellValueFactory(new PropertyValueFactory<>("turns"));
        lastTurnColumn.setCellValueFactory(new PropertyValueFactory<>("lastturn"));
        toLongColumn.setCellValueFactory(new PropertyValueFactory<>("toLong"));

        // Use a Callback for the toLong column to create a BooleanProperty
        toLongColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isTolong()));
        toLongColumn.setCellFactory(CheckBoxTableCell.forTableColumn(toLongColumn));

        // Set the cell factory for the lastTurnColumn to format the date
        lastTurnColumn.setCellFactory(column -> new TableCell<>() {
            private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd:MM:yyyy");

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    long unixTimestamp = Long.parseLong(item);
                    String formattedDate = dateFormat.format(new Date(unixTimestamp * 1000));
                    setText(formattedDate);
                } else {
                    setText(null);
                }
            }
        });

        // Add the columns to the TableView
        tableview.getColumns().addAll(toLongColumn, turnsColumn, lastTurnColumn);

        tableview.setItems(getAllBedData());
    }

    private ObservableList<BedData> getAllBedData() {
        ObservableList<BedData> bed = FXCollections.observableArrayList();

        String json = new DataHelper().getJSONData();
        BedData b = new JSONBuilder().returnJsonData(json);

        bed.add(b);

        bed.add(new BedData(true, "6", "1686162965"));

        return bed;
    }

    private void refreshTableView(TableView<BedData> tableView) {
        // Refresh the data in the table view
        tableView.setItems(getAllBedData());
    }

}
