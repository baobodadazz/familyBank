package src.com.main.familybank.account.login;

import src.com.main.familybank.bill.major.majorInterface;
import src.com.main.familybank.sqlConnecter.Connector;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class Login {
    public static void todo() {
        Stage loginStage = new Stage();

        //根容器
        VBox root = new VBox();//根VBox
        root.setPrefSize(536,411);

        //标题行
        HBox head = new HBox();
        head.setPadding(new Insets(30));
        head.setPrefHeight(50);
        head.setAlignment(Pos.CENTER);
        Text title = new Text("家庭记账系统");
        title.setFont(new Font(36));
        title.setTextAlignment(TextAlignment.LEFT);
        head.getChildren().addAll(title);

        //间隔1，头像层
        HBox gap1 = new HBox();
        gap1.setPadding(new Insets(0,0,10,0));
        gap1.setPrefHeight(56);
        gap1.setAlignment(Pos.CENTER);
        Image headPort = new Image("file:FamilyBank\\img\\userImg.png");
        ImageView hPView = new ImageView();
        hPView.setImage(headPort);
        hPView.setPreserveRatio(true);
        hPView.setFitHeight(56);
        hPView.setFitWidth(56);
        gap1.getChildren().add(hPView);

        //账号行
        HBox userFi = new HBox();
        userFi.setAlignment(Pos.CENTER);
        userFi.setPrefHeight(40);
        Text userText = new Text("账号:  ");
        userText.setFont(new Font(25));
        TextField userFiled = new TextField();
        userFiled.setPrefHeight(40);
        userFiled.setPrefWidth(306);
        userFi.getChildren().addAll(userText, userFiled);

        //密码行
        HBox passFi = new HBox();
        passFi.setPadding(new Insets(8));
        passFi.setAlignment(Pos.CENTER);
        passFi.setPrefHeight(60);
        Text passText = new Text("密码:  ");
        passText.setFont(new Font(25));
        PasswordField passFiled = new PasswordField();
        passFiled.setPrefHeight(40);
        passFiled.setPrefWidth(306);
        passFi.getChildren().addAll(passText, passFiled);

        //间隔2
        HBox gap2 = new HBox();
        gap2.setPrefHeight(45);
        gap2.setAlignment(Pos.CENTER);

        //登陆按钮行
        HBox logInHPane = new HBox();
        logInHPane.setAlignment(Pos.CENTER);
        Button loginBtn = new Button("登陆");
        loginBtn.setPrefSize(360,45);
        logInHPane.getChildren().addAll(loginBtn);

        HBox regH = new HBox(10);
        regH.setAlignment(Pos.CENTER);
        regH.setPadding(new Insets(4,0,0,0));
        Text regText = new Text("没有账号？");
        Text regText2 = new Text("注册");
        regText2.setUnderline(true);
        regText2.setFill(Color.BLUE);
        regH.getChildren().addAll(regText, regText2);

//        loginBtn.setBackground(new Background(new BackgroundFill(Color.rgb(249, 114, 154),null, null)));

        Text failLog = new Text("账号或密码错误");
        failLog.setFill(Color.RED);
        final boolean[] flag = {true};
        loginBtn.setOnAction(event -> {

            System.out.println("[INFO]TRY TO LOGIN WITH userName = \"" + userFiled.getText() + "\",password = \"" + passFiled.getText() + "\"");

            if(Connector.login(userFiled.getText(),passFiled.getText())){
                System.out.println("[INFO]:LOGIN IN SUCCESS");
                loginStage.close();
                System.out.println("[INFO]:LOGIN UI HAS BEEN CLOSED");
                majorInterface.todo();
                System.out.println("[INFO]:NOW IN MAJOR INTERFACE");
            }else{
                if (flag[0]) {
                    gap2.getChildren().add(failLog);
                    flag[0] = false;
                }
                System.out.println("[INFO]:LOGIN IN FAILED");
            }
        });

        regText2.setOnMouseClicked(event -> Register.todo());

        //显示窗口
        root.getChildren().addAll(head, gap1, userFi, passFi, gap2, logInHPane, regH);
        loginStage.getIcons().add(new Image("file:FamilyBank\\img\\icon\\1.ico"));
        loginStage.setScene(new Scene(root, 536, 411));
        loginStage.setTitle("家庭账户管理平台-登陆");
        loginStage.show();
    }
}
