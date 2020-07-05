package dataLayer;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * @author 姚君彦
 * 2020/6/17,22:25
 * 奇怪的程序增加了
 */
public class Common {
    public static ArrayList<User> userList;//读取，存储位置无需改变
    public static String userInfoPath = "src/data/user.data";//用户数据的存储地点
    public static String treePath = "src/data/tree.data";//树的存储地点
    public static Stage currentStage;//用于关闭窗口
    public static Parent currentRoot;//记录当前,用于寻址缓存,下同
    public static Tree currentTree;
    public static Member currentMember;
    public static User currentUser;


    //公共的发送警告以及成功弹窗方法
    public static void sendAlert(boolean OK, String info){
        Alert alert;
        if(OK) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("成功提示");
        }
        else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误提示");
        }
        alert.setHeaderText(null);
        alert.setContentText(info);
        alert.showAndWait();
    }
}
