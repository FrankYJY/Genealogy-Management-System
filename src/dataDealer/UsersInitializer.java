package dataDealer;

import dataDealer.IODealer;
import dataLayer.User;

import java.util.ArrayList;

/**
 * @author 姚君彦
 * 2020/6/17,14:40
 * 奇怪的程序增加了
 */
public class UsersInitializer {
    public static void main(String[] args) {
            String userInfoPath = "src/data/user.data";
            ArrayList<User> d = new ArrayList<User>();
            d.add(new User("admin","123456", true));
            IODealer.writeObjectToFile(d,userInfoPath);
    }
}
