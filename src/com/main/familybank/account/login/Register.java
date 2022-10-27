package src.com.main.familybank.account.login;

import src.com.main.familybank.sqlConnecter.Connector;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Register {

    public static void todo(){

        Stage s = new Stage();

        //底层容器
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);

        //用户名
        HBox userNameH = new HBox();
        userNameH.setAlignment(Pos.CENTER);
        userNameH.setPrefHeight(40);
        Text userNameText = new Text("用户名：   ");
        userNameText.setFont(new Font(25));
        TextField userNameField = new TextField();
        userNameField.setPrefHeight(40);
        userNameH.getChildren().addAll(userNameText, userNameField);

        //重名提醒
        HBox reNameH = new HBox();
        reNameH.setAlignment(Pos.TOP_CENTER);
        reNameH.setPrefHeight(20);
        Text reNameText = new Text("");
        reNameText.setFill(Color.RED);
        reNameH.getChildren().add(reNameText);

        //密码
        HBox passwordH = new HBox();
        passwordH.setAlignment(Pos.CENTER);
        passwordH.setPrefHeight(40);
        Text passwordText = new Text("密码：     ");
        passwordText.setFont(new Font(25));
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(40);
        passwordH.getChildren().addAll(passwordText, passwordField);

        //间隔
        HBox gap1 = new HBox();
        gap1.setPrefHeight(20);

        //真实姓名
        HBox realNameH = new HBox();
        realNameH.setAlignment(Pos.CENTER);
        realNameH.setPrefHeight(40);
        Text realNameText = new Text("真实姓名： ");
        realNameText.setFont(new Font(25));
        TextField realNameField = new TextField();
        realNameField.setPrefHeight(40);
        realNameH.getChildren().addAll(realNameText, realNameField);

        //间隔
        HBox gap2 = new HBox();
        gap2.setPrefHeight(20);

        //按钮层
        HBox btnH = new HBox(40);
        btnH.setPrefHeight(40);
        btnH.setAlignment(Pos.CENTER);
        btnH.setPadding(new Insets(20,0,0,0));
        Button btnReg = new Button("注册");
        btnReg.setPrefSize(140, 40);
        Button btnBack = new Button("返回");
        btnBack.setPrefSize(140,40);
        btnH.getChildren().addAll(btnReg, btnBack);

        //Action
        btnReg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean flag = Connector.register(userNameField.getText(), passwordField.getText(), realNameField.getText());

                if(flag){
                    s.close();
                    Stage s2;
                    BorderPane bd = new BorderPane();
                    bd.setCenter(new Text("注册成功"));
                    s.setScene(new Scene(bd, 270, 140));
                    s.setTitle("信息");
                    s.show();

                } else {
                    reNameText.setText("用户名已存在");
                }
            }
        });

        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                s.close();
            }
        });

        //
        root.getChildren().addAll(userNameH, reNameH, passwordH, gap1, realNameH, gap2, btnH);
        s.setScene(new Scene(root, 400, 300));
        s.setTitle("家庭账户管理平台-注册");
        s.show();
    }
}
