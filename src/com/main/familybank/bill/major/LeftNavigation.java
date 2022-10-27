package src.com.main.familybank.bill.major;

import src.com.main.familybank.Datas.Datas;
import src.com.main.familybank.bill.bills.Bill;
import src.com.main.familybank.sqlConnecter.Connector;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Calendar;


public class LeftNavigation {
    public static VBox getVBox() {
        VBox left = new VBox(10);
        left.setAlignment(Pos.TOP_CENTER);
        left.setPrefWidth(110);
        left.setPadding(new Insets(10));
        left.setBackground(new Background(new BackgroundFill(Color.rgb(252, 240, 245), null, null)));
        BorderStroke borderStroke = new BorderStroke(null, Color.BLACK, null, null, null, BorderStrokeStyle.SOLID, null, null, null, BorderWidths.DEFAULT, new Insets(0));
        left.setBorder(new Border(borderStroke));

        //各个导航点图标和行为的插入
        //1.收入账单
        ImageView addBill = new ImageView(new Image("file:FamilyBank\\img\\navigat\\iconadd.png"));
        addBill.setFitWidth(90);
        addBill.setFitHeight(90);
        addBill.setOnMouseClicked(event -> addBill());

        //2.支出账单
        ImageView subBill = new ImageView(new Image("file:FamilyBank\\img\\navigat\\iconsub.png"));
        subBill.setFitWidth(90);
        subBill.setFitHeight(90);
        subBill.setOnMouseClicked(event -> subBill());

        //3.账目列表
        ImageView showList = new ImageView(new Image("file:FamilyBank\\img\\navigat\\iconlist.png"));
        showList.setFitWidth(90);
        showList.setFitHeight(90);
        showList.setOnMouseClicked(event -> showList());

        left.getChildren().addAll(addBill, subBill, showList);


        return left;
    }

    private static void subBill() {
        Stage subStage = new Stage();

        VBox rootV = new VBox(20);
        rootV.setAlignment(Pos.CENTER);
        rootV.setPadding(new Insets(20));

        Font defaultFont = new Font(30);

        //信息层
        //1.数值
        HBox valueH = new HBox();
        valueH.setAlignment(Pos.CENTER_LEFT);
        valueH.setPrefHeight(40);
        Text valueText = new Text("金额：");
        valueText.setFont(defaultFont);
        TextField valueField = new TextField();
        valueField.setPrefHeight(40);
        Pane gap = new Pane();
        gap.setPrefWidth(60);
        Text categoryText = new Text("  类型:  ");
        categoryText.setFont(defaultFont);
        ChoiceBox<String> categoryChoice = new ChoiceBox<>();
        categoryChoice.setPrefSize(140, 40);
        categoryChoice.getItems().addAll("餐饮", "休闲玩乐", "购物", "穿搭美容", "水果零食", "交通", "生活日用", "人情社交", "宠物", "养娃", "运动", "生活服务", "买菜", "住房", "爱车", "学习", "网络虚拟", "烟酒", "医疗保健", "金融保险", "家具家电", "酒店旅行", "转账", "公益", "数码", "通讯", "游戏", "办公", "礼金", "书籍", "彩票", "其他");
        final String[] categorySelect = {"餐饮"};
        categoryChoice.getSelectionModel().selectFirst();
        valueH.getChildren().addAll(valueText, valueField, gap, categoryText, categoryChoice);

        //2.时间
        HBox timeH = new HBox(10);
        timeH.setAlignment(Pos.CENTER_LEFT);
        timeH.setPrefHeight(40);//100 50....
        Text timeText = new Text("时间：");
        timeText.setFont(defaultFont);
        TextField yearField = getSizeableTextField(100, 40);
        Text time_year_Text = new Text("年");
        time_year_Text.setFont(defaultFont);
        TextField monthField = getSizeableTextField(50, 40);
        Text time_month_Text = new Text("月");
        time_month_Text.setFont(defaultFont);
        TextField dayField = getSizeableTextField(50, 40);
        Text time_day_Text = new Text("日");
        time_day_Text.setFont(defaultFont);
        TextField hField = getSizeableTextField(50, 40);
        Text time_h_Text = new Text("时");
        time_h_Text.setFont(defaultFont);
        TextField mField = getSizeableTextField(50, 40);
        Text time_m_Text = new Text("分");
        time_m_Text.setFont(defaultFont);
        TextField sField = getSizeableTextField(50, 40);
        Text time_s_Text = new Text("秒");
        time_s_Text.setFont(defaultFont);
        Button getNow = new Button("当前");
        getNow.setPrefSize(80, 40);
        timeH.getChildren().addAll(timeText, yearField, time_year_Text, monthField, time_month_Text, dayField, time_day_Text, hField, time_h_Text, mField, time_m_Text, sField, time_s_Text, getNow);

        //3.详情
        VBox detailsRootV = new VBox(10);
        detailsRootV.setPadding(new Insets(10));
        detailsRootV.setAlignment(Pos.CENTER_LEFT);
        detailsRootV.setPrefHeight(240);
        BorderStroke borderStroke = new BorderStroke(Color.BLACK, null, Color.BLACK, null, BorderStrokeStyle.SOLID, null, BorderStrokeStyle.SOLID, null, null, BorderWidths.DEFAULT, new Insets(0));
        detailsRootV.setBorder(new Border(borderStroke));
        Text detailsText = new Text("详情：");
        TextArea detailArea = new TextArea();
        detailsRootV.getChildren().addAll(detailsText, detailArea);

        //按钮层
        HBox btnH = new HBox(160);
        btnH.setAlignment(Pos.CENTER);
        btnH.setPrefHeight(40);
        Button yes = new Button("确定");
        Button back = new Button("返回");
        yes.setPrefSize(160, 40);
        back.setPrefSize(160, 40);
        btnH.getChildren().addAll(yes, back);

        //Action
        //choiceBox

        categoryChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            categorySelect[0] = newValue;
            System.out.println("[INFO][SUB]:INCOME TYPE CHANGED:" + oldValue + " --> " + newValue);
        });

        yes.setOnAction(event -> {
            String longTime = yearField.getText() + monthField.getText() + dayField.getText() + hField.getText() + mField.getText() + sField.getText();
            Bill b = new Bill(Datas.account.getUserId(), Long.parseLong(longTime), Double.parseDouble("-" + valueField.getText()), categorySelect[0], detailArea.getText());
            System.out.print("[INFO][MAJOR]:GET BILL:");
            System.out.println(b);
            Connector.addBills(b);
            majorInterface.todo();
            subStage.close();

        });

        back.setOnAction(event -> {
            majorInterface.todo();
            subStage.close();
        });

        getNow.setOnAction(event -> {
            //获取当前时间
            Calendar cal = Calendar.getInstance();
            int y = cal.get(Calendar.YEAR);
            int m = cal.get(Calendar.MONTH);
            int d = cal.get(Calendar.DATE);
            int h = cal.get(Calendar.HOUR_OF_DAY);
            int mi = cal.get(Calendar.MINUTE);
            int s = cal.get(Calendar.SECOND);

            yearField.setText("" + y);

            if (m + 1 < 10) {
                monthField.setText("0" + (m + 1));
            } else {
                monthField.setText("" + m);
            }

            if (d < 10) {
                dayField.setText("0" + d);
            } else {
                dayField.setText("" + d);
            }

            if (h < 10) {
                hField.setText("0" + h);
            } else {
                hField.setText("" + h);
            }

            if (mi < 10) {
                mField.setText("0" + mi);
            } else {
                mField.setText("" + mi);
            }

            if (s < 10) {
                sField.setText("0" + s);
            } else {
                sField.setText("" + s);
            }
        });

        rootV.getChildren().addAll(valueH, timeH, detailsRootV, btnH);

        subStage.setScene(new Scene(rootV, 890 - 12, 480 - 12));
        subStage.setResizable(false);
        subStage.setTitle("支出记录");
        subStage.show();

        subStage.setOnCloseRequest(event -> {
            majorInterface.todo();
            subStage.close();
        });

        majorInterface.close();
    }

    private static void showList() {
        // 列表宽800
        Stage primaryStage = new Stage();
        HBox rootH = new HBox(5);
        Font defaultFont = new Font(35);

        //窗口内绘制start
        //左侧筛选栏start
        VBox leftV = new VBox(20);
        leftV.setPadding(new Insets(20));
        leftV.setPrefWidth(350);
        BorderStroke borderStroke = new BorderStroke(null, Color.BLACK, null, null, null, BorderStrokeStyle.SOLID, null, null, null, BorderWidths.DEFAULT, new Insets(0));
        leftV.setBorder(new Border(borderStroke));
        //时间筛选器start
        Text timeText = new Text("时间");
        HBox fromH = new HBox(10);
        fromH.setPrefHeight(40);
        fromH.setAlignment(Pos.CENTER);
        Text f = new Text("从:");
        f.setFont(defaultFont);
        TextField fromField = new TextField();
        fromField.setPrefSize(255, 40);
        fromH.getChildren().addAll(f, fromField);
        //----------------------------------------------------
        HBox toH = new HBox(10);
        toH.setPrefHeight(40);
        toH.setAlignment(Pos.CENTER);
        Text t = new Text("到:");
        t.setFont(defaultFont);
        TextField toField = new TextField();
        toField.setPrefSize(255, 40);
        toH.getChildren().addAll(t, toField);
        //时间筛选器end
        //预设选择器start
        HBox yesterdayTodayH = new HBox(20);
        yesterdayTodayH.setPrefHeight(40);
        yesterdayTodayH.setAlignment(Pos.CENTER);
        Button yesterdayBtn = new Button("昨天");
        yesterdayBtn.setPrefSize(145, 40);
        Button todayBtn = new Button("今天");
        todayBtn.setPrefSize(145, 40);
        Button timeClearBtn = new Button("清空");
        timeClearBtn.setPrefSize(310, 40);
        yesterdayTodayH.getChildren().addAll(yesterdayBtn, todayBtn);
        //预设选择器end
        Separator separator1 = new Separator();
        //专项筛选start
        Text cateText = new Text("类别");
        HBox cateH = new HBox(10);
        cateH.setAlignment(Pos.CENTER);
        cateH.setPrefHeight(40);
        ChoiceBox<String> stateChoice = new ChoiceBox<String>();
        stateChoice.getItems().addAll("全选", "支出", "收入");
        stateChoice.setPrefSize(80, 40);
        stateChoice.getSelectionModel().selectFirst();
        final String[] stateChoice_now = {"全选"};
        ChoiceBox<String> cateChoice = new ChoiceBox<String>();
        cateChoice.setPrefSize(210, 40);
        final String[] cateChoice_now = {"全选"};
        cateH.getChildren().addAll(stateChoice, cateChoice);
        //专项筛选end
        Separator separator2 = new Separator();
        //精确筛选start
        Text accurateText = new Text("精确");
        TextField accurateField = new TextField();
        accurateField.setPrefSize(310, 40);
        //精确筛选end
        //确定和返回start
        Button ok_btn = new Button("查询");
        ok_btn.setPrefSize(310, 40);
        Button cancel_btn = new Button("取消");
        cancel_btn.setPrefSize(310, 40);
        //确定和返回end
        Button all_btn = new Button("显示全部");
        all_btn.setPrefSize(310, 40);
        //专项筛选end
        leftV.getChildren().addAll(timeText, fromH, toH, yesterdayTodayH, timeClearBtn, separator1, cateText, cateH, separator2, accurateText, accurateField, ok_btn, cancel_btn, all_btn);
        //左侧筛选栏end

        //右侧start
        VBox rightV = new VBox();
        rightV.setPadding(new Insets(20));

        //table
        ObservableList<Bill> list = FXCollections.observableArrayList();
        TableView<Bill> table = new TableView<Bill>(list);

        if(Datas.bill != null){
            for (int i = 0; i < Datas.bill.length; i++) {
                list.add(Datas.bill[i]);
            }
        }



        table.setPrefSize(900, 692);
        TableColumn<Bill, Number> time = new TableColumn<Bill, Number>("账单时间");
        time.setMinWidth(200);
        time.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Bill, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Bill, Number> param) {
                return param.getValue().getTimeProperty();
            }
        });

        TableColumn<Bill, Number> money = new TableColumn<Bill, Number>("账单金额");
        money.setMinWidth(100);
        money.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Bill, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Bill, Number> param) {
                return param.getValue().getMoneyProperty();
            }
        });

        TableColumn<Bill, String> category = new TableColumn<Bill, String>("账单类型");
        category.setMinWidth(100);
        category.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Bill, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Bill, String> param) {
                return param.getValue().getCategoryProperty();
            }
        });

        TableColumn<Bill, String> detail = new TableColumn<Bill, String>("细节描述");
        detail.setMinWidth(500);
        detail.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Bill, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Bill, String> param) {
                return param.getValue().getDetailsProperty();
            }
        });

        //给table添加列
        table.getColumns().addAll(time, money, category, detail);
        //table end
        rightV.getChildren().addAll(table);
        //右侧end
        //窗口内绘制end

        rootH.getChildren().addAll(leftV, rightV);
        primaryStage.setTitle("历史账单");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(rootH, 1280, 720));
        primaryStage.show();

        //Listener
        stateChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            stateChoice_now[0] = newValue;
            System.out.println("[INFO][LIST]STATE SELECT: " + oldValue + " --> " + newValue);
            cateChoice.getItems().clear();
            if (newValue.equals("支出")) {
                cateChoice.getItems().addAll("全选", "餐饮", "休闲玩乐", "购物", "穿搭美容", "水果零食", "交通", "生活日用", "人情社交", "宠物", "养娃", "运动", "生活服务", "买菜", "住房", "爱车", "学习", "网络虚拟", "烟酒", "医疗保健", "金融保险", "家具家电", "酒店旅行", "转账", "公益", "数码", "通讯", "游戏", "办公", "礼金", "书籍", "彩票", "其他");
                cateChoice.getSelectionModel().selectFirst();
                cateChoice_now[0] = "全选";
            } else if (newValue.equals("收入")) {
                cateChoice.getItems().addAll("全选", "工资", "兼职", "投资理财", "人情社交", "奖金补贴", "报销", "生意", "卖二手", "生活费", "中奖", "转账", "保险理赔", "其他", "店铺销售", "礼金");
                cateChoice.getSelectionModel().selectFirst();
                cateChoice_now[0] = "全选";
            } else {
                cateChoice_now[0] = "全选";
            }
        });

        cateChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("[INFO][LIST]STATE SELECT: " + oldValue + " --> " + newValue);
            cateChoice_now[0] = newValue;
        });

        //Action
        timeClearBtn.setOnAction(event -> {
            fromField.clear();
            toField.clear();
        });

        yesterdayBtn.setOnAction(event -> {
            Calendar cal = Calendar.getInstance();
            int y = cal.get(Calendar.YEAR);
            int m = cal.get(Calendar.MONTH);
            int d = cal.get(Calendar.DATE);
            m += 1;

            if (d == 1) {
                if (m == 1) {
                    m = 12;
                    y--;
                } else {
                    m--;
                    if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
                        d = 31;
                    } else if (m == 2) {
                        d = 28;
                    } else {
                        d = 30;
                    }
                }
            } else {
                d--;
            }

            String year, month, day;

            year = "" + y;

            if (m + 1 < 10) {
                month = "0" + m;
            } else {
                month = "" + m;
            }

            if (d < 10) {
                day = "0" + d;
            } else {
                day = "" + d;
            }
            fromField.setText(year + month + day + "000000");
            toField.setText(year + month + day + "240000");
        });

        todayBtn.setOnAction(event -> {
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
            fromField.setText(year + month + day + "000000");
            toField.setText(year + month + day + "240000");
        });

        ok_btn.setOnAction(event -> {
            if(Datas.bill == null){
                return;
            }
            list.clear();
            if (fromField.getText().equals("") & toField.getText().equals("")) {
                //未限定时间
                if (stateChoice_now[0].equals("全选")) {
                    //未限定状态（无法限制类别）
                    if (accurateField.getText().equals("")) {
                        //未精确
                        for (int i = 0; i < Datas.bill.length; i++) {
                            list.add(Datas.bill[i]);
                        }

                    } else {
                        //精确
                        for (int i = 0; i < Datas.bill.length; i++) {
                            if (accurateField.getText().equals(Datas.bill[i].getDetails())) {
                                list.add(Datas.bill[i]);
                            }
                        }
                    }
                } else {
                    //限定了状态
                    if (cateChoice_now[0].equals("全选")) {
                        if (accurateField.getText().equals("")) {
                            if (stateChoice_now[0].equals("支出")){
                                for (int i = 0; i < Datas.bill.length; i++) {
                                    if (Datas.bill[i].getMoney() < 0) {
                                        list.add(Datas.bill[i]);
                                    }
                                }
                            }else{
                                for (int i = 0; i < Datas.bill.length; i++) {
                                    if (Datas.bill[i].getMoney() > 0) {
                                        list.add(Datas.bill[i]);
                                    }
                                }
                            }
                        } else {
                            if (stateChoice_now[0].equals("支出")){
                                for (int i = 0; i < Datas.bill.length; i++) {
                                    if (Datas.bill[i].getMoney() < 0 && accurateField.getText().equals(Datas.bill[i].getDetails())) {
                                        list.add(Datas.bill[i]);
                                    }
                                }
                            }else{
                                for (int i = 0; i < Datas.bill.length; i++) {
                                    if (Datas.bill[i].getMoney() > 0 && accurateField.getText().equals(Datas.bill[i].getDetails())) {
                                        list.add(Datas.bill[i]);
                                    }
                                }
                            }
                        }
                    } else {
                        //限定了种类
                        if (accurateField.getText().equals("")) {
                            if (stateChoice_now[0].equals("支出")){
                                for (int i = 0; i < Datas.bill.length; i++) {
                                    if (Datas.bill[i].getMoney() < 0 && cateChoice_now[0].equals(Datas.bill[i].getCategory())) {
                                        list.add(Datas.bill[i]);
                                    }
                                }
                            }else{
                                for (int i = 0; i < Datas.bill.length; i++) {
                                    if (Datas.bill[i].getMoney() > 0 && cateChoice_now[0].equals(Datas.bill[i].getCategory())) {
                                        list.add(Datas.bill[i]);
                                    }
                                }
                            }
                        } else {
                            if (stateChoice_now[0].equals("支出")){
                                for (int i = 0; i < Datas.bill.length; i++) {
                                    if (Datas.bill[i].getMoney() < 0 && accurateField.getText().equals(Datas.bill[i].getDetails()) && cateChoice_now[0].equals(Datas.bill[i].getCategory())) {
                                        list.add(Datas.bill[i]);
                                    }
                                }
                            }else{
                                for (int i = 0; i < Datas.bill.length; i++) {
                                    if (Datas.bill[i].getMoney() > 0 && accurateField.getText().equals(Datas.bill[i].getDetails()) && cateChoice_now[0].equals(Datas.bill[i].getCategory())) {
                                        list.add(Datas.bill[i]);
                                    }
                                }
                            }
                        }
                    }

                }
            } else {
                if (stateChoice_now[0].equals("全选")) {
                    //未限定状态（无法限制类别）
                    if (accurateField.getText().equals("")) {
                        //未精确
                        for (int i = 0; i < Datas.bill.length; i++) {
                            if (Datas.bill[i].getTime() >= Long.parseLong(fromField.getText()) && Datas.bill[i].getTime() < Long.parseLong(toField.getText())) {
                                list.add(Datas.bill[i]);
                            }
                        }

                    } else {
                        //精确
                        for (int i = 0; i < Datas.bill.length; i++) {
                            if (accurateField.getText().equals(Datas.bill[i].getDetails()) && Datas.bill[i].getTime() >= Long.parseLong(fromField.getText()) && Datas.bill[i].getTime() < Long.parseLong(toField.getText())) {
                                list.add(Datas.bill[i]);
                            }
                        }
                    }
                } else {
                    //限定了状态
                    if (cateChoice_now[0].equals("全选")) {
                        if (accurateField.getText().equals("")) {
                            if (stateChoice_now[0].equals("支出")){
                                for (int i = 0; i < Datas.bill.length; i++) {
                                    if (Datas.bill[i].getMoney() < 0 && Datas.bill[i].getTime() >= Long.parseLong(fromField.getText()) && Datas.bill[i].getTime() < Long.parseLong(toField.getText())) {
                                        list.add(Datas.bill[i]);
                                    }
                                }
                            }else{
                                for (int i = 0; i < Datas.bill.length; i++) {
                                    if (Datas.bill[i].getMoney() > 0 && Datas.bill[i].getTime() >= Long.parseLong(fromField.getText()) && Datas.bill[i].getTime() < Long.parseLong(toField.getText())) {
                                        list.add(Datas.bill[i]);
                                    }
                                }
                            }
                        } else {
                            if (stateChoice_now[0].equals("支出")){
                                for (int i = 0; i < Datas.bill.length; i++) {
                                    if (Datas.bill[i].getTime() >= Long.parseLong(fromField.getText()) && Datas.bill[i].getTime() < Long.parseLong(toField.getText()) && Datas.bill[i].getMoney() < 0 && accurateField.getText().equals(Datas.bill[i].getDetails())) {
                                        list.add(Datas.bill[i]);
                                    }
                                }
                            }else{
                                for (int i = 0; i < Datas.bill.length; i++) {
                                    if (Datas.bill[i].getTime() >= Long.parseLong(fromField.getText()) && Datas.bill[i].getTime() < Long.parseLong(toField.getText()) && Datas.bill[i].getMoney() > 0 && accurateField.getText().equals(Datas.bill[i].getDetails())) {
                                        list.add(Datas.bill[i]);
                                    }
                                }
                            }
                        }
                    } else {
                        //限定了种类
                        if (accurateField.getText().equals("")) {
                            if (stateChoice_now[0].equals("支出")){
                                for (int i = 0; i < Datas.bill.length; i++) {
                                    if (Datas.bill[i].getTime() >= Long.parseLong(fromField.getText()) && Datas.bill[i].getTime() < Long.parseLong(toField.getText()) && Datas.bill[i].getMoney() < 0 && cateChoice_now[0].equals(Datas.bill[i].getCategory())) {
                                        list.add(Datas.bill[i]);
                                    }
                                }
                            }else{
                                for (int i = 0; i < Datas.bill.length; i++) {
                                    if (Datas.bill[i].getTime() >= Long.parseLong(fromField.getText()) && Datas.bill[i].getTime() < Long.parseLong(toField.getText()) && Datas.bill[i].getMoney() > 0 && cateChoice_now[0].equals(Datas.bill[i].getCategory())) {
                                        list.add(Datas.bill[i]);
                                    }
                                }
                            }
                        } else {
                            if (stateChoice_now[0].equals("支出")){
                                for (int i = 0; i < Datas.bill.length; i++) {
                                    if (Datas.bill[i].getTime() >= Long.parseLong(fromField.getText()) && Datas.bill[i].getTime() < Long.parseLong(toField.getText()) && Datas.bill[i].getMoney() < 0 && accurateField.getText().equals(Datas.bill[i].getDetails()) && cateChoice_now[0].equals(Datas.bill[i].getCategory())) {
                                        list.add(Datas.bill[i]);
                                    }
                                }
                            }else{
                                for (int i = 0; i < Datas.bill.length; i++) {
                                    if (Datas.bill[i].getTime() >= Long.parseLong(fromField.getText()) && Datas.bill[i].getTime() < Long.parseLong(toField.getText()) && Datas.bill[i].getMoney() > 0 && accurateField.getText().equals(Datas.bill[i].getDetails()) && cateChoice_now[0].equals(Datas.bill[i].getCategory())) {
                                        list.add(Datas.bill[i]);
                                    }
                                }
                            }
                        }
                    }

                }
            }

            table.refresh();
        });

        all_btn.setOnAction(event -> {
            list.clear();
            for (int i = 0; i < Datas.bill.length; i++) {
                list.add(Datas.bill[i]);
            }
            table.refresh();
        });

        cancel_btn.setOnAction(event -> {
            System.out.println("[INFO][LIST]:LIST CLOSED");
            primaryStage.close();
        });
    }

    private static void addBill() {
        Stage addStage = new Stage();

        VBox rootV = new VBox(20);
        rootV.setAlignment(Pos.CENTER);
        rootV.setPadding(new Insets(20));

        Font defaultFont = new Font(30);

        //信息层
        //1.数值
        HBox valueH = new HBox();
        valueH.setAlignment(Pos.CENTER_LEFT);
        valueH.setPrefHeight(40);
        Text valueText = new Text("金额：");
        valueText.setFont(defaultFont);
        TextField valueField = new TextField();
        valueField.setPrefHeight(40);
        Pane gap = new Pane();
        gap.setPrefWidth(60);
        Text categoryText = new Text("  类型:  ");
        categoryText.setFont(defaultFont);
        ChoiceBox<String> categoryChoice = new ChoiceBox<>();
        categoryChoice.setPrefSize(140, 40);
        categoryChoice.getItems().addAll("工资", "兼职", "投资理财", "人情社交", "奖金补贴", "报销", "生意", "卖二手", "生活费", "中奖", "转账", "保险理赔", "其他", "店铺销售", "礼金");
        final String[] categorySelect = {"工资"};
        categoryChoice.getSelectionModel().selectFirst();
        valueH.getChildren().addAll(valueText, valueField, gap, categoryText, categoryChoice);

        //2.时间
        HBox timeH = new HBox(10);
        timeH.setAlignment(Pos.CENTER_LEFT);
        timeH.setPrefHeight(40);//100 50....
        Text timeText = new Text("时间：");
        timeText.setFont(defaultFont);
        TextField yearField = getSizeableTextField(100, 40);
        Text time_year_Text = new Text("年");
        time_year_Text.setFont(defaultFont);
        TextField monthField = getSizeableTextField(50, 40);
        Text time_month_Text = new Text("月");
        time_month_Text.setFont(defaultFont);
        TextField dayField = getSizeableTextField(50, 40);
        Text time_day_Text = new Text("日");
        time_day_Text.setFont(defaultFont);
        TextField hField = getSizeableTextField(50, 40);
        Text time_h_Text = new Text("时");
        time_h_Text.setFont(defaultFont);
        TextField mField = getSizeableTextField(50, 40);
        Text time_m_Text = new Text("分");
        time_m_Text.setFont(defaultFont);
        TextField sField = getSizeableTextField(50, 40);
        Text time_s_Text = new Text("秒");
        time_s_Text.setFont(defaultFont);
        Button getNow = new Button("当前");
        getNow.setPrefSize(80, 40);
        timeH.getChildren().addAll(timeText, yearField, time_year_Text, monthField, time_month_Text, dayField, time_day_Text, hField, time_h_Text, mField, time_m_Text, sField, time_s_Text, getNow);

        //3.详情
        VBox detailsRootV = new VBox(10);
        detailsRootV.setPadding(new Insets(10));
        detailsRootV.setAlignment(Pos.CENTER_LEFT);
        detailsRootV.setPrefHeight(240);
        BorderStroke borderStroke = new BorderStroke(Color.BLACK, null, Color.BLACK, null, BorderStrokeStyle.SOLID, null, BorderStrokeStyle.SOLID, null, null, BorderWidths.DEFAULT, new Insets(0));
        detailsRootV.setBorder(new Border(borderStroke));
        Text detailsText = new Text("详情：");
        TextArea detailArea = new TextArea();
        detailsRootV.getChildren().addAll(detailsText, detailArea);

        //按钮层
        HBox btnH = new HBox(160);
        btnH.setAlignment(Pos.CENTER);
        btnH.setPrefHeight(40);
        Button yes = new Button("确定");
        Button back = new Button("返回");
        yes.setPrefSize(160, 40);
        back.setPrefSize(160, 40);
        btnH.getChildren().addAll(yes, back);

        //Action
        //choiceBox

        categoryChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                categorySelect[0] = newValue;
                System.out.println("[INFO][ADD]:INCOME TYPE CHANGED:" + oldValue + " --> " + newValue);
            }
        });

        yes.setOnAction(event -> {
            String longTime = yearField.getText() + monthField.getText() + dayField.getText() + hField.getText() + mField.getText() + sField.getText();
            Bill b = new Bill(Datas.account.getUserId(), Long.parseLong(longTime), Double.parseDouble(valueField.getText()), categorySelect[0], detailArea.getText());
            System.out.print("[INFO][MAJOR]:GET BILL:");
            System.out.println(b);
            Connector.addBills(b);
            majorInterface.todo();
            addStage.close();
        });

        back.setOnAction(event -> {
            majorInterface.todo();
            addStage.close();
        });

        getNow.setOnAction(event -> {
            //获取当前时间
            Calendar cal = Calendar.getInstance();
            int y = cal.get(Calendar.YEAR);
            int m = cal.get(Calendar.MONTH);
            int d = cal.get(Calendar.DATE);
            int h = cal.get(Calendar.HOUR_OF_DAY);
            int mi = cal.get(Calendar.MINUTE);
            int s = cal.get(Calendar.SECOND);

            yearField.setText("" + y);

            if (m + 1 < 10) {
                monthField.setText("0" + (m + 1));
            } else {
                monthField.setText("" + m);
            }

            if (d < 10) {
                dayField.setText("0" + d);
            } else {
                dayField.setText("" + d);
            }

            if (h < 10) {
                hField.setText("0" + h);
            } else {
                hField.setText("" + h);
            }

            if (mi < 10) {
                mField.setText("0" + mi);
            } else {
                mField.setText("" + mi);
            }

            if (s < 10) {
                sField.setText("0" + s);
            } else {
                sField.setText("" + s);
            }
        });

        rootV.getChildren().addAll(valueH, timeH, detailsRootV, btnH);

        addStage.setScene(new Scene(rootV, 890 - 12, 480 - 12));
        addStage.setResizable(false);
        addStage.setTitle("收入记录");
        addStage.show();

        addStage.setOnCloseRequest(event -> {
            majorInterface.todo();
            addStage.close();
        });


        majorInterface.close();
    }

    private static TextField getSizeableTextField(double width, double height) {
        TextField t = new TextField();
        t.setPrefSize(width, height);

        return t;
    }
}
