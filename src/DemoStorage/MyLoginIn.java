package DemoStorage;
import static java.lang.System.err;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import dataLayer.User;
import javafx.application.Application;

public class MyLoginIn {
    static ArrayList<User> list = new ArrayList<User>();  //存放用户数据的列表

    public static void main(String[] args) {
        LoadUser(); //从文件中载入用户数据
        Application.launch(QQLogin.class, args); //启动javafx Application程序
    }

    public static void LoadUser() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("data/user.txt")));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\s+");
                list.add(new User(data[0], data[1],false));
            }
            reader.close();
        } catch (IOException e) {
            err.println("用户文件数据 user.txt找不到");
            e.printStackTrace();
        }
    }

    public static boolean findUser(String account, String pwd) {
        return list.contains(new User(account, pwd,false));
    }

    public static User findAccount(String account) {
        for (User u : list)
            if (u.getAccount().equalsIgnoreCase(account))
                return u;
        return null;
    }

    public static User newUser() {
        User user = list.get(list.size() - 1);
        return new User(Integer.parseInt(user.getAccount()) + 1 + "", "",false);
    }

    public static void addNewUser(User user) {
        list.add(user);
        LoadUser();
    }
}
