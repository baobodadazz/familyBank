package src.com.main.familybank.bill.major;

import src.com.main.familybank.Datas.Datas;
import src.com.main.familybank.sqlConnecter.Connector;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class budget {
    private static VBox budgetVBox;
    private static HBox title;
    private static Text titleText;

    public static VBox getBudgetVBox() {
        Font defaultFont = new Font(30);

        budgetVBox = new VBox();
        title = new HBox();
        titleText = new Text("预算简报");

        BorderStroke borderStroke = new BorderStroke(Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, null, BorderWidths.DEFAULT, new Insets(0));
        budgetVBox.setBorder(new Border(borderStroke));
        budgetVBox.setPrefSize(540, 266);
        budgetVBox.setAlignment(Pos.TOP_CENTER);

        title.setPrefHeight(30);
        title.setPadding(new Insets(0, 0, 0, 10));
        title.setAlignment(Pos.CENTER_LEFT);
        colorNotFocus();
        title.getChildren().addAll(titleText);
        VBox mainV = new VBox(20);
        mainV.setPrefHeight(236);
        mainV.setAlignment(Pos.CENTER);

        //今日预算
        HBox budgetH = new HBox(20);
        budgetH.setPrefHeight(50);
        budgetH.setAlignment(Pos.CENTER);
        Text budgetText = new Text("今日预算：");
        HBox budgetHInside = new HBox(20);
        budgetHInside.setAlignment(Pos.CENTER);
        budgetHInside.setPrefWidth(350);
        budgetText.setFont(defaultFont);
        TextField budgetField = new TextField();
        budgetField.setPrefSize(250, 40);
        budgetField.setText(String.valueOf(Datas.account.getBudget()));
        Button ok_btn = new Button("确定");
        ok_btn.setPrefSize(80,40);
        budgetHInside.getChildren().addAll(budgetField, ok_btn);

        budgetH.getChildren().addAll(budgetText, budgetHInside);
        //今日支出
        HBox payH = new HBox(20);
        payH.setPrefHeight(50);
        payH.setAlignment(Pos.CENTER);
        Text payText = new Text("今日支出：");
        HBox payHInside = new HBox(20);
        payHInside.setAlignment(Pos.CENTER_LEFT);
        payHInside.setPrefWidth(350);
        payText.setFont(defaultFont);
        double payMoney = Connector.getPayByDay(todayState.today(), 0, new String[1]);
        Text payOutText = new Text("￥ " + payMoney + " 元");
        payOutText.setFont(defaultFont);
        payHInside.getChildren().addAll(payOutText);

        payH.getChildren().addAll(payText, payHInside);
        //超出或冗余  !!超出使用红色字体，冗余使用绿色字体
        HBox outOfH = new HBox();
        outOfH.setPrefHeight(50);
        outOfH.setAlignment(Pos.CENTER);
        Text showText = new Text();
        showText.setFont(defaultFont);

        final double[] earn = {Datas.account.getBudget() - payMoney};

        if(earn[0] >= 0){
            showText.setFill(Color.GREEN);
            showText.setText("预算还剩余：￥ " + (double)(Math.round(Math.abs(earn[0]) * 100)) / 100 + " 元");
        }else{
            showText.setFill(Color.RED);
            showText.setText("预算已超出：￥ " +  (double)(Math.round(Math.abs(earn[0]) * 100)) / 100 + " 元");
        }

        outOfH.getChildren().addAll(showText);

        mainV.getChildren().addAll(budgetH, payH, outOfH);

        budgetVBox.getChildren().addAll(title, mainV);
        //Action

        budgetVBox.setOnMouseEntered(event -> colorFocus());
        budgetVBox.setOnMouseExited(event -> colorNotFocus());

        ok_btn.setOnAction(event -> {
            Datas.account.setBudget(Double.parseDouble(budgetField.getText()));

            earn[0] =  ((double)(Math.round((Datas.account.getBudget() - payMoney) * 100)) / 100);

            if(earn[0] >= 0){
                showText.setFill(Color.GREEN);
                showText.setText("预算还剩余：￥ " + (double)(Math.round(Math.abs(earn[0]) * 100)) / 100 + " 元");
            }else{
                showText.setFill(Color.RED);
                showText.setText("预算已超出：￥ " +  (double)(Math.round(Math.abs(earn[0]) * 100)) / 100 + " 元");
            }
        });

        return budgetVBox;
    }

    public static void colorFocus() {
        title.setBackground(new Background(new BackgroundFill(Color.rgb(249, 114, 154), null, null)));
        budgetVBox.setBackground(new Background(new BackgroundFill(Color.rgb(255, 247, 250), null, null)));
        titleText.setFill(Color.WHITE);
    }

    public static void colorNotFocus() {
        budgetVBox.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), null, null)));
        title.setBackground(new Background(new BackgroundFill(Color.rgb(234, 234, 234), null, null)));
        titleText.setFill(Color.BLACK);
    }

    public static void beFocus() {
        colorFocus();
        ConsumptionAnalysis.colorNotFocus();
        RaEAnalysis.colorNotFocus();
        todayState.colorNotFocus();
    }

}
