package scene;

/**
 * @author 姚君彦
 * 2020/6/4,16:40
 * 奇怪的程序增加了
 */
import dataDealer.IODealer;
import dataLayer.Common;
import dataLayer.Tree;
import dataLayer.User;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

//    private TableView table= new TableView;
//    private final ObservableList<record> dataList  = FXCollections.observableArrayList();
    @Override
    public void start(Stage primaryStage) throws Exception{
        Common.currentStage = primaryStage;
        Common.currentRoot = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));//加载fxml场景
//        Common.currentRoot = FXMLLoader.load(getClass().getResource("JiapuScene.fxml"));//加载fxml场景
        primaryStage.setTitle("家谱 by:H17000623姚君彦");
        primaryStage.setScene(new Scene(Common.currentRoot));
        primaryStage.show();

        //当关闭程序时需要保存已经修改的用户list和家谱树，下面一般情况被跳过，在家谱主界面关闭时保存用户和树
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("监听到主窗口关闭");
//                IODealer.writeObjectToFile(Common.userList,Common.userInfoPath);
            }
        });

    }

    static ArrayList<User> loadUserInfo() {
        return (ArrayList<User>)IODealer.readObjectFromFile(Common.userInfoPath);
    }
    static Tree loadFamilyTree(){
        return (Tree) IODealer.readObjectFromFile(Common.treePath);
    }

    //检查用户名与密码是否对应正确



    public static void main(String[] args) {
//        System.out.println(ifInfoOK("admin","123456"));
        launch(args);
    }

    //开启第二个界面
    public static void startJiapuScene(){
        Stage secondStage = new Stage();
        try {
            Common.currentRoot = FXMLLoader.load(Main.class.getResource("JiapuScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        secondStage.setTitle("家谱 by:姚君彦");
        secondStage.setScene(new Scene(Common.currentRoot));
        secondStage.show();

        //再次保存
        secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                //正常关闭才能保存家谱改动
                System.out.println("监听到窗口关闭");
                IODealer.writeObjectToFile(Common.userList,Common.userInfoPath);
                IODealer.writeObjectToFile(Common.currentTree,Common.treePath);
            }
        });
    }

    //开启第三个界面
    public static void startEditScene() {
        Stage thirdStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(Main.class.getResource("EditScene.fxml"));
//            System.out.println("###############################");
        } catch (IOException e) {
            e.printStackTrace();
        }
        thirdStage.setTitle("家谱修改");
        thirdStage.setScene(new Scene(root));
        thirdStage.show();
    }

    //开启第四个界面
    public static void startEditUserScene() {
        Stage fourthStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(Main.class.getResource("EditUserScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        fourthStage.setTitle("用户修改");
        fourthStage.setScene(new Scene(root));
        fourthStage.show();
        fourthStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                //关闭时保存用户改动
                System.out.println("监听到窗口关闭");
                IODealer.writeObjectToFile(Common.userList,Common.userInfoPath);
            }
        });
    }
}
