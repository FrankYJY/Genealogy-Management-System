package scene;

import dataLayer.Common;
import dataLayer.LiveState;
import dataLayer.Member;
import dataLayer.Power;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 * @author 姚君彦
 * 2020/6/17,16:36
 * 奇怪的程序增加了
 */
public class JiapuController implements Initializable {

    public Button editButton;
    public Button searchButton;
    public TextField nameToSearch;
    public TextField name;
    public TextField gender;
    public TextField isAlive;
    public TextField birthday;
    public TextField deathday;
    public TextField marriage;
    public TextField address;
    public TextField generation;
    public TextField father;
    public TextField mother;
    public TextField descendents;
    public TextField extra;
    public TextArea chronicleList;
    public Button checkButton1;
    public TextField checkRelative2;
    public TextField checkRelative1;
    public TextField checkResult;
    public Canvas canvas;
    public Button editUserButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //加载树
        Common.currentTree = Main.loadFamilyTree();
//        if (Common.currentTree!=null)
//            System.out.println("good!");

    }

    private void setChronicleList() {
        Common.currentTree.resort();
        LinkedList<Member> temp = Common.currentTree.getSortedlist();
        StringBuilder tempString = new StringBuilder();
        tempString.append("按生日排序：（从早生到晚生）\n");
        Calendar now = Calendar.getInstance();//可以对每个时间域单独修改
        int month = now.get(Calendar.MONTH)+1;//calendar 获得的月份是从0开始的
        int date = now.get(Calendar.DATE);
//        System.out.println("今天是"+month+" / "+date);
        Member tempMem;
        for (int i = 0; i < temp.size(); i++) {
            tempMem = temp.get(i);
            if (tempMem.getIsAlive()== LiveState.alive && tempMem.getBirthday()[1]==month && tempMem.getBirthday()[2] == date)
                tempString.append(tempMem.getBirthday()[0]+"  "+tempMem.getName()+" 今天是ta的生日！\n");
            else
                tempString.append(tempMem.getBirthday()[0]+"  "+tempMem.getName()+"\n");
        }
//        System.out.println(tempString.toString());
//        for (int k = 0; k < Common.currentTree.getSortedlist().size(); k++) {
//            System.out.println(Common.currentTree.getSortedlist().get(k));
//        }
        chronicleList.setText(tempString.toString());
    }

    public void search(ActionEvent actionEvent) {
//        if (Common.currentTree.isInList(nameToSearch.getText())){
//        System.out.println("空值测试："+nameToSearch.getText());
        if (nameToSearch.getText().length()<1){
            setChronicleList();
            TreeDrawing.draw(canvas,Common.currentTree);
        }else {
            Common.currentMember = Common.currentTree.find(nameToSearch.getText());
//        }else{
            if (Common.currentMember == null) {
                //带有刷新功能，按日期列以及画树
                setChronicleList();
                TreeDrawing.draw(canvas,Common.currentTree);
                Common.sendAlert(false, "名字不存在");
            }
//        }
            else {
                name.setText(Common.currentMember.getName());
                gender.setText(Common.currentMember.getGenderString());
                isAlive.setText(Common.currentMember.getIsAliveString());
                birthday.setText(Common.currentMember.getBirthdayString());
                deathday.setText(Common.currentMember.getDeathdayString());
                marriage.setText(Common.currentMember.getIfMarriedString());
                address.setText(Common.currentMember.getAddress());
                generation.setText(Common.currentMember.getGeneration() + "");
                if (Common.currentMember.getFather() != null)
                    father.setText(Common.currentMember.getFather().getName());
                if (Common.currentMember.getMother() != null)
                    mother.setText(Common.currentMember.getMother().getName());
                descendents.setText(Common.currentMember.getDescendentsString());
                extra.setText(Common.currentMember.getExtraMessage());
            }
        }

    }

    public void toEditScene(ActionEvent actionEvent) {
        //只有管理者可以进入到修改界面
        if (Common.currentUser.getPower()== Power.admin)
            Main.startEditScene();
        else
            Common.sendAlert(false,"你没有权限修改");
    }


    public void check(ActionEvent actionEvent) {
        if (checkRelative1.getText()!=""&&checkRelative2.getText()!=""){
            if (Common.currentTree.isDirectRelated(checkRelative1.getText(),checkRelative2.getText())){
                checkResult.setText("直系");
            }else{
                checkResult.setText("非直系");
            }
        }
    }


    public void toEditUserScene(ActionEvent actionEvent) {
        if (Common.currentUser.getPower()== Power.admin)
            Main.startEditUserScene();
        else
            Common.sendAlert(false,"你没有权限修改");
    }
}
