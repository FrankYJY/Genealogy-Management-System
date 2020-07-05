package DemoStorage;

/**
 * @author 姚君彦
 * 2020/6/17,11:45
 * 奇怪的程序增加了
 */

import dataLayer.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class QQLogin extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {


        primaryStage.setTitle("家谱 by:H17000623姚君彦");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));// 设置填充, Insets实例设置矩形区域的四边的一组内偏移量

        Text title = new Text("登录");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Label Account = new Label("账户:");
        TextField userAccount = new TextField();
        Label pwd = new Label("密码:");
        PasswordField userPwd = new PasswordField();
        Label no = new Label("没有账号?->");
        no.setTextFill(Color.BLUE);
        Button confirm = new Button("确定");
        Button register = new Button("注册");
        Button forget = new Button("忘记密码");

        confirm.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {

                if (MyLoginIn.findUser(userAccount.getText(), userPwd.getText())) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("成功提示");
                    alert.setHeaderText(null);
                    alert.setContentText("登录成功");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("错误提示");
                    alert.setHeaderText(null);
                    alert.setContentText("用户名或密码错误,请重新输入");
                    alert.showAndWait();
                    userAccount.clear();
                    userPwd.clear();
                }
            }

        });

        register.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(primaryStage);
                User user = MyLoginIn.newUser();
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("成功提示");
                alert.setHeaderText(null);
                alert.setContentText("您的账户为: " + user.getAccount());
                alert.showAndWait();
                dialog.setTitle("设置密码");
                PasswordField newPwd = new PasswordField();
                Label label = new Label("密码");
                Button confirm = new Button("确认");
                confirm.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        alert.setTitle("成功提示");
                        alert.setHeaderText(null);
                        alert.setContentText("注册成功,请返回登录");
                        user.setPassword(newPwd.getText());
                        MyLoginIn.addNewUser(user);
                        alert.showAndWait();
                        dialog.close();
                        userAccount.setText(user.getAccount());
                        userPwd.clear();
                    }
                });
                GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(25, 25, 25, 25));// 设置填充, Insets实例设置矩形区域的四边的一组内偏移量
                grid.add(label, 0, 1);
                grid.add(newPwd, 1, 1);
                grid.add(confirm, 1, 2);
                dialog.setScene(new Scene(grid, 300, 100));
                dialog.show();
            }
        });

        forget.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                User user = MyLoginIn.findAccount(userAccount.getText());
                if (user != null) {
                    Stage dialog = new Stage();
                    dialog.setTitle("重置密码");
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.initOwner(primaryStage);
                    PasswordField newPwd = new PasswordField();
                    Label label = new Label("新密码");
                    Button confirm = new Button("确认");
                    confirm.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            user.setPassword(newPwd.getText());
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("成功提示");
                            alert.setHeaderText(null);
                            alert.setContentText("修改密码成功,请返回登录");
                            alert.showAndWait();
                            dialog.close();
                            userPwd.clear();
                        }
                    });
                    GridPane grid = new GridPane();
                    grid.setAlignment(Pos.CENTER);
                    grid.setHgap(10);
                    grid.setVgap(10);
                    grid.setPadding(new Insets(25, 25, 25, 25));// 设置填充, Insets实例设置矩形区域的四边的一组内偏移量
                    grid.add(label, 0, 1);
                    grid.add(newPwd, 1, 1);
                    grid.add(confirm, 1, 2);
                    dialog.setScene(new Scene(grid, 300, 100));
                    dialog.show();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("错误提示");
                    alert.setHeaderText(null);
                    alert.setContentText("您输入的账户不存在,请重新输入");
                    alert.showAndWait();
                    userAccount.clear();
                    userPwd.clear();
                }
            }
        });
        grid.add(title, 0, 0, 2, 1);
        grid.add(Account, 0, 1);
        grid.add(userAccount, 1, 1);
        grid.add(pwd, 0, 2);
        grid.add(userPwd, 1, 2);

        HBox panel = new HBox(40);
        panel.setAlignment(Pos.BOTTOM_RIGHT);
        panel.getChildren().add(confirm);

        HBox panel2 = new HBox(40);
        panel2.setAlignment(Pos.BOTTOM_RIGHT);
        panel2.getChildren().add(no);
        panel2.getChildren().add(register);
        grid.add(panel, 1, 4);
        grid.add(panel2, 1, 5);

        HBox panel3 = new HBox(20);
        panel3.setAlignment(Pos.BOTTOM_RIGHT);
        panel3.getChildren().add(forget);
        grid.add(panel3, 1, 6);
        primaryStage.setScene(new Scene(grid, 350, 300));

        primaryStage.show();
    }
}
