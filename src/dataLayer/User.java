package dataLayer;

import java.io.Serializable;

/**
 * @author 姚君彦
 * 2020/6/17,11:43
 * 奇怪的程序增加了
 */

public class User implements Serializable {
    public static final long serialVersionUID = 42L;//需要手动设定，否则重新编译会自动生成不同序列号
    private String account;//账户名字
    private String password;//密码
    private Power power;//权限,枚举型
    public User(String account, String password, boolean editable) {
        this.account = account;
        this.password = password;
        if (editable)
            this.power = Power.admin;
        else
            this.power = Power.common;
    }

    public User(String account, String password) {
        this.account = account;
        this.password = password;
        this.power = Power.common;
    }

    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }


    //覆盖equals方法,为了实现账户和密码相同的User是同一个对象,如果不覆盖,比较的时候会按照哈希值
    //未使用
    @Override
    public boolean equals(Object obj) {

        if (obj instanceof User) {
            if (this.getAccount().equals(((User) obj).getAccount())
                    && this.getPassword().equals(((User) obj).getPassword())) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public static boolean ifInfoOK(String userName, String userPassword){
        User temp;
        for (int i = 0; i < Common.userList.size(); i++) {
            temp = Common.userList.get(i);
            if (temp.getAccount().equals(userName)
                    && temp.getPassword().equals(userPassword) ) {
                Common.currentUser = temp;//存储当前账户，可以查权限
                return true;
            }
        }
        return false;
    }

    //检测是否此用户名已存在，存在返回true
    public static boolean ifNameExist(String userName){
        User temp;
        for (int i = 0; i < Common.userList.size(); i++) {
            temp = Common.userList.get(i);
            if (temp.getAccount().equals(userName) ) {
                return true;
            }
        }
        return false;
    }


}


