package scene;

import dataLayer.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author 姚君彦
 * 2020/6/17,22:30
 * 奇怪的程序增加了
 */
public class EditController implements Initializable {

    public Button editButton;
    public TextField name;
    public ChoiceBox gender;
    public ChoiceBox isAlive;
    public TextField birthYear;
    public TextField birthMonth;
    public TextField birthDay;
    public TextField deathYear;
    public TextField deathMonth;
    public TextField deathDay;
    public ChoiceBox marriage;
    public TextField address;
    public TextField extra;

    public TextField addParent;
    public TextField removeName;
    public Button addButton;
    public Button removeButton;
    public TextField movedName;
    public TextField moveUnderParent;
    public Button moveButton;
    public TextField nameToSearch;
    public Button searchButton;
    public TextField generation;
    public TextField father;
    public TextField mother;
    public TextField descendents;

    private Member currentMember;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gender.getItems().addAll(GenderType.values());//使用enum类型加载到choice box
        marriage.getItems().addAll(MarriageState.values());//使用enum类型加载到choice box
        isAlive.getItems().addAll(LiveState.values());//使用enum类型加载到choice box
    }

    //choice box的default为null
    //textfield的default为""

    public void addInTree(ActionEvent actionEvent) {
        String addparentString = addParent.getText();
        String nameString = name.getText();
        //至少要有两个名字的信息以及性别（关系到分配父母）才能赋值，名字不可重复
        /*记录一下可重复的思路：
        * 增加：没有问题
        * 查找：在tree下维护一个静态列表，记录每个名字的个数，每次加入删除时需要修改，同时被删除的子孙都需要修改个数，
        *       像维护每一代个数（已实现）一样做成方法每次刷新
        * 删除：已实现的方法可以删除所有重复的
        * 查找与删除的定位：将在editcontroller中调用的方法定向为记录的界面右侧的member实例，每次给用户看一个，确定后对当前member删除
        *       然后查找时做成查找与查找下一个，
        *       如果有同名可以查找下一个实例，根据list中的个数，通过在每个member类中新添加一个flag记录有没有被查过来实现，
        *       每次结束需要将flag归位，可以再次遍历来全部重置或者再开一个member类型数组记录已经查过的实例
        * 查找与删除的另一个思路：在基础的树外部再建立二维都可变数组，每个名字为第一参数，内数组存同名个体们，然后都通过这个二维数组定位到记录
        * 修改：通过查找下一个定位到个体后没有问题
        * 移动：同上，定位两个个体让用户确定
        * */
        if (!addparentString.equals("") && !nameString.equals("") && !gender.getValue().equals("")&& Common.currentTree.find(nameString)==null){
            if (Common.currentTree.insert(addParent.getText(),
                    new Member(
                        name.getText(), (GenderType) gender.getValue(),(LiveState) isAlive.getValue(),
                            transInt(birthYear.getText()),transInt(birthMonth.getText()),transInt(birthDay.getText()),
                            transInt(deathYear.getText()),transInt(deathMonth.getText()),transInt(deathDay.getText()),
                            (MarriageState) marriage.getValue(),address.getText(),extra.getText()
                    )
            ))
                Common.sendAlert(true,"添加成功");
            else
                Common.sendAlert(false,"添加失败，未找到指定的父母");
        }else{
            Common.sendAlert(false,"输入错误，至少要有两个名字的信息以及性别（可未知）才能赋值，而且名字不可重复");
        }
//        System.out.println( (GenderType) gender.getValue());
    }

    public void removeFromTree(ActionEvent actionEvent) {//remove会查找所有，不需要单独处理空名字
//        if(Common.currentTree.remove(removeName.getText())){
        Common.currentTree.remove(removeName.getText());
            Common.sendAlert(true,"移除成功");
//        }else{
//            Common.sendAlert(false,"移除失败");
//        }
    }

    public void moveInsideTree(ActionEvent actionEvent) {//同上
        if(Common.currentTree.move2des(movedName.getText(),moveUnderParent.getText())){
            Common.sendAlert(true,"移动成功");
        }else{
            Common.sendAlert(false,"移动失败");
        }
    }

    //此部分可以去掉重复计算部分加速，本程序还有很多地方可以合并计算
    public void editPerson(ActionEvent actionEvent) {
        boolean flag = false;
        if (Common.currentTree.find(name.getText())==null ||Common.currentMember.getName().equals(name.getText()))//要加入的名字不存在或是未修改
            if (gender.getValue()!=null)
                flag = true;
        if(flag) {
            Common.currentMember.setName(name.getText());
            Common.currentMember.setGender((GenderType) gender.getValue());
            if (isAlive.getValue()!=null)Common.currentMember.setIsAlive((LiveState) isAlive.getValue());
            if (transInt(birthYear.getText()) != 0 &&
                    transInt(birthMonth.getText()) != 0 &&
                    transInt(birthDay.getText()) != 0)
                Common.currentMember.setBirthday(transInt(birthYear.getText()),
                        transInt(birthMonth.getText()), transInt(birthDay.getText()));
            if (transInt(deathYear.getText()) != 0 &&
                    transInt(deathMonth.getText()) != 0 &&
                    transInt(deathDay.getText()) != 0)
                Common.currentMember.setDeathday(transInt(deathYear.getText()),
                        transInt(deathMonth.getText()), transInt(deathDay.getText()));
            if (marriage.getValue() != null) Common.currentMember.setIfMarried((MarriageState) marriage.getValue());
            if (address.getText() != "") Common.currentMember.setAddress(address.getText());
            if (extra.getText() != "") Common.currentMember.setExtraMessage(extra.getText());
            Common.sendAlert(true,"修改成功。");
        }else{
            Common.sendAlert(false,"至少输入姓名和性别，且姓名不能与已存在的重复，不修改名字除外。");
        }
    }

    private int transInt(String getText){
        try{
            return Integer.valueOf(getText);
        }catch (Exception e){
            return 0;
        }
    }


    public void search(ActionEvent actionEvent) {
        if (!nameToSearch.getText().equals("")) {
            Common.currentMember = Common.currentTree.find(nameToSearch.getText());
            freshView();
        }
    }

    private void freshView() {
        name.setText(Common.currentMember.getName());
        gender.setValue(Common.currentMember.getGender());
        isAlive.setValue(Common.currentMember.getIsAlive());
        birthYear.setText(Common.currentMember.getBirthday()[0]+"");
        birthMonth.setText(Common.currentMember.getBirthday()[1]+"");
        birthDay.setText(Common.currentMember.getBirthday()[2]+"");
        deathYear.setText(Common.currentMember.getDeathday()[0]+"");
        deathMonth.setText(Common.currentMember.getDeathday()[1]+"");
        deathDay.setText(Common.currentMember.getDeathday()[2]+"");
        marriage.setValue(Common.currentMember.getIfMarried());
        address.setText(Common.currentMember.getAddress());
        generation.setText(Common.currentMember.getGeneration()+"");
        if (Common.currentMember.getFather() != null)
            father.setText(Common.currentMember.getFather().getName());
        if (Common.currentMember.getMother() != null)
            mother.setText(Common.currentMember.getMother().getName());
        descendents.setText(Common.currentMember.getDescendentsString());
        extra.setText(Common.currentMember.getExtraMessage());
        addParent.setText("");
        removeName.setText("");
        movedName.setText("");
        moveUnderParent.setText("");
    }
}
