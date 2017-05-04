/**
 * Created by wills on 2017-05-02.
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;


public class FXTest extends Application {

    @Override public void start(Stage stage) {
        stage.setTitle("Scatter Chart Sample");
        final NumberAxis xAxis = new NumberAxis(-3750, 3750, 156.25);
        final NumberAxis yAxis = new NumberAxis(-3750, 3750, 156.25);
        final ScatterChart<Number,Number> sc =
                new ScatterChart<Number,Number>(xAxis,yAxis);

        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        series1.setName("Candidate Locations");
        series2.setName("Selected Locations");
        for(BaseStation b : ScenarioManager.candidates) {
            if(!ScenarioManager.solutionSet.contains(b)) {
                series1.getData().add(new XYChart.Data(b.getX(), b.getY()));
            }
            else{
                series2.getData().add(new XYChart.Data(b.getX(), b.getY()));
            }
        }

        XYChart.Series series3 = new XYChart.Series();

        series3.setName("Macro BS");
        for(BaseStation b : ScenarioManager.macroBaseStations) {
            series3.getData().add(new XYChart.Data(b.getX(), b.getY()));
        }

        XYChart.Series series4 = new XYChart.Series();

        series4.setName("Users");
        for(User u : ScenarioManager.users) {
            series4.getData().add(new XYChart.Data(u.getX(), u.getY()));
        }





        sc.setPrefSize(1000, 800);
        sc.getData().addAll(series1, series2, series3, series4);
        Scene scene  = new Scene(new Group());
        final VBox vbox = new VBox();
        final HBox hbox = new HBox();


        vbox.getChildren().addAll(sc, hbox);
        hbox.setPadding(new Insets(10, 10, 10, 50));

        ((Group)scene.getRoot()).getChildren().add(vbox);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) throws FileNotFoundException{
        ScenarioManager.init("scenario.txt");
        launch(args);
    }


}