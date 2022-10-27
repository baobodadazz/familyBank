package src.com.main.familybank.bill.major;

import src.com.main.familybank.sqlConnecter.Connector;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Calendar;

public class todayState {
    private static VBox todayStateVBox;
    private static HBox title;
    private static Text titleText;

    public static VBox getTodayStateVBox() {
        todayStateVBox = new VBox();
        title = new HBox();
        titleText = new Text("今日情况");

        BorderStroke borderStroke = new BorderStroke(Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, null, BorderWidths.DEFAULT, new Insets(0));
        todayStateVBox.setBorder(new Border(borderStroke));
        todayStateVBox.setPrefSize(540, 266);
        todayStateVBox.setAlignment(Pos.TOP_CENTER);
        //@TODO 今日状况
        title.setPrefHeight(30);
        title.setPadding(new Insets(0,0,0,10));
        title.setAlignment(Pos.CENTER_LEFT);
        colorNotFocus();
        title.getChildren().addAll(titleText);

        VBox mainV = new VBox();
        mainV.setAlignment(Pos.CENTER);

        Font big = new Font(48);
        //支
        HBox payOutH = new HBox(40);
        payOutH.setPrefHeight(77);
        payOutH.setAlignment(Pos.CENTER);
        Text outText = new Text("支");
        outText.setFont(big);
        outText.setFill(Color.GREEN);
        VBox detailV = new VBox(10);
        detailV.setPrefWidth(360);
        detailV.setAlignment(Pos.CENTER_LEFT);
        String[] str1 = new String[1];
        double outMoney = Connector.getPayByDay(today(), 0, str1);
        Text OutNum = new Text("￥ " + outMoney);
        OutNum.setFill(Color.GREEN);
        Text Out = new Text(str1[0]);
        Out.setFill(Color.GRAY);
        detailV.getChildren().addAll(OutNum, Out);

        payOutH.getChildren().addAll(outText, detailV);
        Separator separatorOut = new Separator();
        //收
        HBox payInH = new HBox(40);
        payInH.setPrefHeight(77);
        payInH.setAlignment(Pos.CENTER);
        Text InText = new Text("收");
        InText.setFont(big);
        InText.setFill(Color.RED);
        VBox detailV2 = new VBox(10);
        detailV2.setPrefWidth(360);
        detailV2.setAlignment(Pos.CENTER_LEFT);
        String[] str2 = new String[1];
        double inMoney = Connector.getPayByDay(today(), 1, str2);
        Text INNum = new Text("￥ " + inMoney);
        INNum.setFill(Color.RED);
        Text IN = new Text(str2[0]);
        IN.setFill(Color.GRAY);
        detailV2.getChildren().addAll(INNum, IN);

        payInH.getChildren().addAll(InText, detailV2);
        Separator separatorIn = new Separator();

        //余
        HBox earn = new HBox(40);
        earn.setPrefHeight(77);
        earn.setAlignment(Pos.CENTER);

        Text EarnText = new Text("余");
        EarnText.setFont(big);
        EarnText.setFill(Color.GRAY);
        VBox detailV3 = new VBox(10);
        detailV3.setPrefWidth(360);
        detailV3.setAlignment(Pos.CENTER_LEFT);

        Text EarnNum = new Text("￥ " + ((double) Math.round(inMoney - outMoney) * 100) / 100);
        EarnNum.setFill(Color.GRAY);
        EarnNum.setUnderline(true);
        detailV3.getChildren().addAll(EarnNum);

        earn.getChildren().addAll(EarnText, detailV3);

        mainV.getChildren().addAll(payOutH, separatorOut, payInH, separatorIn, earn);

        todayStateVBox.getChildren().addAll(title, mainV);

        //Action

        todayStateVBox.setOnMouseEntered(event -> colorFocus());
        todayStateVBox.setOnMouseExited(event -> colorNotFocus());

        return todayStateVBox;
    }

    public static void colorFocus(){
        title.setBackground(new Background(new BackgroundFill(Color.rgb(249, 114, 154),null,null)));
        todayStateVBox.setBackground(new Background(new BackgroundFill(Color.rgb(255, 247, 250), null, null)));
        titleText.setFill(Color.WHITE);
    }

    public static void colorNotFocus(){
        title.setBackground(new Background(new BackgroundFill(Color.rgb(234, 234, 234), null, null)));
        todayStateVBox.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), null, null)));
        titleText.setFill(Color.BLACK);
    }

    public static void beFocus(){
        colorFocus();
        ConsumptionAnalysis.colorNotFocus();
        RaEAnalysis.colorNotFocus();
        budget.colorNotFocus();
    }

    public static long today(){
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DATE);

        String year, month, day;

        year = "" + y;

        if (m + 1 < 10) {
            month = "0" + (m + 1);
        } else {
            month = "" + m;
        }

        if (d < 10) {
            day = "0" + d;
        } else {
            day = "" + d;
        }

        return Long.parseLong(year + month + day);
    }
}
