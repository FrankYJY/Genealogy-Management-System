package scene;

import dataLayer.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author 姚君彦
 * 2020/6/17,22:30
 * 奇怪的程序增加了
 */
public class EditUserController implements Initializable {


    public TextArea UserListShower;
    public Button freshButton;
    public Button addButton;
    public Button deleteButton;
    public Button editButton;
    public TextField addUserName;
    public TextField addUserPassword;
    public TextField deleteUserName;
    public TextField editedUserName;
    public TextField editUserNameTo;
    public TextField editUserPasswordTo;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void freshList(ActionEvent actionEvent) {
        StringBuilder tempString = new StringBuilder();
        tempString.append("用户列表：\n");
        for (int i = 0; i < Common.userList.size(); i++) {
            tempString.append(Common.userList.get(i).getAccount()+"\n");
        }
        UserListShower.setText(tempString.toString());
    }

    public void add(ActionEvent actionEvent) {
        //不允许重复用户名
        if (User.ifNameExist(addUserName.getText())){
            Common.sendAlert(false,"用户名已存在,请重新输入");
            return;
        }
        //密码必须六位及以上
        if(addUserPassword.getText().length()<6){
            Common.sendAlert(false,"密码未达到六位及以上，请重新输入");
            return;
        }
        Common.userList.add(new User(addUserName.getText(),addUserPassword.getText()));
        Common.sendAlert(true,"成功建立普通账户");
    }

    public void delete(ActionEvent actionEvent) {
        if (deleteUserName.getText().equals(Common.currentUser.getAccount())){
            Common.sendAlert(false,"不允许删除正在操作的账号");
        }else if (User.ifNameExist(deleteUserName.getText())){
            for (int i = 0; i < Common.userList.size(); i++) {
                if (Common.userList.get(i).getAccount().equals(deleteUserName.getText())){
                    Common.userList.remove(i);
                    break;
                }
            }
            Common.sendAlert(true,"成功删除");
        }else {
            Common.sendAlert(true,"用户本不存在");
        }
    }

    public void edit(ActionEvent actionEvent) {
        if (!User.ifNameExist(editedUserName.getText())){
            Common.sendAlert(true,"要修改的用户本不存在");
            return;
        }
        if (User.ifNameExist(editUserNameTo.getText())){
            Common.sendAlert(false,"用户名已存在,请重新输入");
            return;
        }
        if(editUserPasswordTo.getText().length()<6){
            Common.sendAlert(false,"密码未达到六位及以上，请重新输入");
            return;
        }
        int i;//获得要修改的位置
        for (i = 0; i < Common.userList.size(); i++) {
            if (Common.userList.get(i).getAccount().equals(editedUserName.getText())){
                break;
            }
        }
        User temp = Common.userList.get(i);
        temp.setAccount(editUserNameTo.getText());
        temp.setPassword(editUserPasswordTo.getText());
        Common.sendAlert(true,"修改成功");
    }
}
