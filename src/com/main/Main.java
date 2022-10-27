package com.main;

import src.com.main.familybank.account.login.Login;
import src.com.main.familybank.sqlConnecter.Connector;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        Text welcome = new Text("欢迎使用");
        welcome.setFont(new Font(40));
        root.setCenter(welcome);
        Text vision = new Text("Vision 1.0.1 release");
        HBox visionH = new HBox();
        visionH.setAlignment(Pos.CENTER);
        visionH.getChildren().add(vision);
        root.setBottom(visionH);

        System.out.println("[INFO]:LOADING");

        primaryStage.setAlwaysOnTop(true);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.setTitle("欢迎");
        primaryStage.setScene(new Scene(root, 350, 240));
        primaryStage.show();

        //单击界面运行程序
        root.setOnMouseClicked(event -> {
            System.out.println("[INFO]:LOADING UI CLOSED");

            boolean flag = true;

            int i = Connector.init();
            if (i != 0) {
                flag = false;
                Stage s = new Stage();
                s.setTitle("错误");
                VBox v0 = new VBox();
                v0.setAlignment(Pos.CENTER);
                HBox btn_h = new HBox();
                btn_h.setAlignment(Pos.CENTER);
                Text infomation = null;
                switch (i) {
                    case -1:
                        infomation = new Text("包加载失败");
                    case -2:
                        infomation = new Text("数据库服务器链接失败");
                }

                Button btn1 = new Button("退出");
                btn1.setOnAction(event1 -> {
                    s.close();
                });

                btn_h.getChildren().addAll(btn1);

                v0.getChildren().addAll(infomation, btn_h);
                primaryStage.close();
                s.setAlwaysOnTop(true);
                s.initStyle(StageStyle.UNDECORATED);
                s.setResizable(false);
                s.setTitle("Hello World");
                s.setScene(new Scene(v0, 350, 240));
                s.show();

            }


            String s = "";
            s.matches("1");

            if (flag) {
                primaryStage.close();
                Login.todo();
                System.out.println("[INFO]:LOGIN UI HAS BE OPENED");
            }
        });

    }

    public static void main(String[] args) {

        launch(args);
//        Connector.init();

    }
}
