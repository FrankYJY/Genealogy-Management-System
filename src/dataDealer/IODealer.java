package dataDealer;

import java.io.*;

/**
 * @author 姚君彦
 * 2020/6/14,0:34
 * 奇怪的程序增加了
 */
public class IODealer {

    public static void writeObjectToFile(Object obj,String path) {
        File file = new File(path);//输出文件位置
        try {
            FileOutputStream out = new FileOutputStream(file);//创建文件实例
            ObjectOutputStream objOut = new ObjectOutputStream(out);//实例化对象输出流
            objOut.writeObject(obj);//把内容写入输出流
            objOut.flush();//强制输出缓冲
            objOut.close();//关闭,节省资源,下同
            out.close();
            System.out.println("write object success!");
        } catch (
                IOException e) {
            System.out.println("write object failed");
            e.printStackTrace();
        }
    }

    public static Object readObjectFromFile(String path)
    {
        Object temp = null;
        File file =new File(path);
        try {
            FileInputStream in = new FileInputStream(file);//文件输入流
            ObjectInputStream objIn=new ObjectInputStream(in);//实例化输入流
            temp=objIn.readObject();//读取
            objIn.close();
            System.out.println("read object success!");
        } catch (IOException e) {
            System.out.println("read object failed");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
