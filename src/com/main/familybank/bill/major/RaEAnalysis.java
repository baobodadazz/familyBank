package src.com.main.familybank.bill.major;

import src.com.main.familybank.sqlConnecter.Connector;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RaEAnalysis {
    private static VBox RaEVBox;
    private static HBox title;
    private static Text titleText;

    public static VBox getRaEAnalysisVBox() {

        RaEVBox = new VBox();
        title = new HBox();
        titleText = new Text("收支分析");

        BorderStroke borderStroke = new BorderStroke(Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, null, BorderWidths.DEFAULT, new Insets(0));
        RaEVBox.setBorder(new Border(borderStroke));
        RaEVBox.setPrefSize(540, 266);
        RaEVBox.setAlignment(Pos.TOP_CENTER);

        title.setPrefHeight(30);
        title.setPadding(new Insets(0, 0, 0, 10));
        title.setAlignment(Pos.CENTER_LEFT);
        colorNotFocus();
        title.getChildren().addAll(titleText);

        //收入和支出的数值
        double inMoney = Connector.getPayByDay(todayState.today(), 1, new String[1]);
        double outMoney = Connector.getPayByDay(todayState.today(), 0, new String[1]);

        VBox mainV = new VBox(30);
        mainV.setPadding(new Insets(30));
        mainV.setAlignment(Pos.TOP_CENTER);

        HBox inH = getCellH("收入", inMoney, Color.RED, false);
        HBox outH = getCellH("支出", outMoney, Color.GREEN, false);
        HBox todayH = getCellH("今日", inMoney - outMoney, Color.BLACK, true);

        mainV.getChildren().addAll(inH, outH, todayH);

        RaEVBox.getChildren().addAll(title, mainV);

        //Action

        RaEVBox.setOnMouseEntered(event -> colorFocus());
        RaEVBox.setOnMouseExited(event -> colorNotFocus());

        return RaEVBox;
    }

    private static HBox getCellH(String text, double amount, Color color, boolean isPic) {
        HBox value = new HBox();
        value.setAlignment(Pos.CENTER);
        value.setPrefHeight(40);
        amount = (double)(Math.round(amount * 100)) / 100;

        //标志
        HBox pic = new HBox();
        pic.setPrefSize(40, 40);
        pic.setAlignment(Pos.CENTER);
        if (isPic) {
            ImageView img;
            if (amount > 0) {
                img = new ImageView(new Image("file:FamilyBank/img/littlePic/up.png"));
            } else if (amount < 0) {
                img = new ImageView(new Image("file:FamilyBank/img/littlePic/down.png"));
            } else {
                img = new ImageView(new Image("file:FamilyBank/img/littlePic/n.png"));
            }

            img.setFitHeight(20);
            img.setFitWidth(20);
            pic.getChildren().add(img);
        }

        //文字
        HBox textH = new HBox();
        textH.setAlignment(Pos.CENTER_LEFT);
        textH.setPrefSize(230,40);
        Text text1 = new Text(text);
        text1.setFont(new Font(25));
        textH.getChildren().add(text1);

        //数值
        HBox mountH = new HBox();
        mountH.setAlignment(Pos.CENTER_LEFT);
        mountH.setPrefSize(80,40);
        Text amount1 = new Text(String.valueOf(amount));
        amount1.setFont(new Font(25));
        amount1.setFill(color);
        mountH.getChildren().add(amount1);

        //元
        Text dt = new Text("元");
        dt.setFont(new Font(25));

        value.getChildren().addAll(pic, textH, mountH, dt);

        return value;
    }

    public static void colorFocus() {
        title.setBackground(new Background(new BackgroundFill(Color.rgb(249, 114, 154), null, null)));
        RaEVBox.setBackground(new Background(new BackgroundFill(Color.rgb(255, 247, 250), null, null)));
        titleText.setFill(Color.WHITE);
    }

    public static void colorNotFocus() {
        title.setBackground(new Background(new BackgroundFill(Color.rgb(234, 234, 234), null, null)));
        RaEVBox.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), null, null)));
        titleText.setFill(Color.BLACK);
    }

    public static void beFocus() {
        colorFocus();
        ConsumptionAnalysis.colorNotFocus();
        budget.colorNotFocus();
        todayState.colorNotFocus();
    }
}
