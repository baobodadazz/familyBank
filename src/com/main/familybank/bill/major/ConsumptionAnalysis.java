package src.com.main.familybank.bill.major;

import src.com.main.familybank.sqlConnecter.Connector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Vector;

public class ConsumptionAnalysis {
    private static VBox ConsumptionVBox;
    private static HBox title;
    private static Text titleText;

    public static VBox getConsumptionAnalysisVBox() {

        ConsumptionVBox = new VBox();
        title = new HBox();
        titleText = new Text("消费分析");

        BorderStroke borderStroke = new BorderStroke(Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, null, BorderWidths.DEFAULT, new Insets(0));
        ConsumptionVBox.setBorder(new Border(borderStroke));
        ConsumptionVBox.setPrefSize(540, 266);
        ConsumptionVBox.setAlignment(Pos.TOP_CENTER);

        title.setPrefHeight(30);
        title.setPadding(new Insets(0, 0, 0, 10));
        title.setAlignment(Pos.CENTER_LEFT);
        colorNotFocus();
        title.getChildren().addAll(titleText);

        HBox mainH = new HBox();
        mainH.setPrefHeight(236);

        Vector<String> s = new Vector<String>();
        Vector<Double> d = new Vector<Double>();
        double total = Connector.getPayByDay(todayState.today(), 0, new String[1], s, d);

        //图表绘制
        ObservableList<PieChart.Data> dataList = FXCollections.observableArrayList();
        if (s.size() == 0) {
            dataList.add(new PieChart.Data("无数据", 1));
        } else {
            for (int i = 0; i < s.size(); i++) {
                double temp = ((double) Math.round(d.elementAt(i) * 100)) / 100;
                dataList.add(new PieChart.Data(s.elementAt(i), temp));
            }
        }

        PieChart pieChart = new PieChart(dataList);
        pieChart.setLabelsVisible(false);
        pieChart.setLegendSide(Side.RIGHT);

        //弹出提示信息
        pieChart.getData().forEach(data -> {
            Node node = data.getNode();
            double temp;

            if (total == 0) {
                temp = 100;
            } else {
                temp = ((double) Math.round((data.getPieValue() / total) * 10000)) / 100;
            }

            Tooltip tip = new Tooltip(data.getName() + " - " + data.getPieValue() + " (" + temp +"%)");
            tip.setFont(new Font(30));
            Tooltip.install(node, tip);
        });

        mainH.getChildren().add(pieChart);

        ConsumptionVBox.getChildren().addAll(title, mainH);

        //Action

        ConsumptionVBox.setOnMouseEntered(event -> colorFocus());
        ConsumptionVBox.setOnMouseExited(event -> colorNotFocus());

        return ConsumptionVBox;
    }

    public static void colorFocus() {
        title.setBackground(new Background(new BackgroundFill(Color.rgb(249, 114, 154), null, null)));
        ConsumptionVBox.setBackground(new Background(new BackgroundFill(Color.rgb(255, 247, 250), null, null)));
        titleText.setFill(Color.WHITE);
    }

    public static void colorNotFocus() {
        ConsumptionVBox.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), null, null)));
        title.setBackground(new Background(new BackgroundFill(Color.rgb(234, 234, 234), null, null)));
        titleText.setFill(Color.BLACK);
    }

    public static void beFocus() {
        colorFocus();
        budget.colorNotFocus();
        RaEAnalysis.colorNotFocus();
        todayState.colorNotFocus();
    }
}