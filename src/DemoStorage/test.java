package DemoStorage;

import dataLayer.GenderType;
import dataLayer.Member;
import dataLayer.Tree;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author 姚君彦
 * 2020/6/14,18:12
 * 奇怪的程序增加了
 */
enum gaaaa{
    wowo,nini
}
public class test {
    public static void main(String[] args) {
//        int[] t1 = new int[3];
//        System.out.println(t1[0]+" "+t1[1]+" "+t1[2]);
//        ttt a = new ttt(1999,1,2);
//        ttt b = new ttt(1999,1,1);
//        System.out.println(a.compareTo(b));
        gaaaa c = gaaaa.wowo;
        gaaaa e = null;
        System.out.println(null+"2323232323");
        System.out.println(c);

        ArrayList<Integer> ab = new ArrayList<Integer>();
        ab.add(4);
        ab.add(5);
        System.out.println("Array size is"+ab.size()+"   "+ab.get(1));
//test fileIO
//        String path = "demo1.data";
//        ArrayList<Member> d = new ArrayList<Member>();
//        for (int i = 0; i < 100; i++) {
//            Member e = new Member();
//            d.add(e);
//        }
//        IODealer.writeObjectToFile(b,path);
//
//        ArrayList<Member> n = (ArrayList<Member>)IODealer.readObjectFromFile(path);
//        System.out.println(n.get(4).toString());

        Tree tree = new Tree(new Member("A60",60, GenderType.male));
        tree.insert("A60", new Member("A61",61, GenderType.male));
        tree.insert("A61", new Member("A78",78, GenderType.male));
        tree.insert("A61", new Member("A79",79, GenderType.male));
        tree.insert("A78", new Member("A99",99, GenderType.male));
        tree.insert("A78", new Member("A99r",99, GenderType.male));
        tree.insert("A61", new Member("A79",79, GenderType.male));
        tree.insert("A60", new Member("A67",67, GenderType.male));
        tree.insert("A67", new Member("A88",88, GenderType.male));

        System.out.println(tree.find("A78").equals(tree.find("A78")));

        System.out.println(tree.find("A67").getBirthdayString()+"wwwwwwwwwwwwwwww");
        tree.remove("A79");
        tree.remove( "A79");
        System.out.println(tree.find("A99r")+"啊啊啊啊啊啊啊");
        tree.resort();
        for (int k = 0; k < tree.getSortedlist().size(); k++) {
            System.out.println(tree.getSortedlist().get(k));
        }




        LinkedList www = new LinkedList();
        www.add(0,"a");
        www.add(1,"b");
        www.add(2,"c");
        System.out.println(www.size());
        System.out.println(www.get(2));



        int[] w = new  int[3];

    }




}
class ttt implements Comparable{
    private int[] birthday = new int[3];//year/month/day
    public int compareTo(Object o) {
        ttt B = (ttt) o;
        if (this.birthday[0]>B.birthday[0])
            return 1;
        else if (this.birthday[0]==B.birthday[0])
            if (this.birthday[1]>B.birthday[1])
                return 1;
            else if (this.birthday[1]==B.birthday[1])
                if (this.birthday[2] >B.birthday[2])
                    return 1;
                else if (this.birthday[2] ==B.birthday[2])
                    return 0;
        return -1;
    }
    public ttt(int year, int month, int day) {
        this.birthday[0] = year;
        this.birthday[1] = month;
        this.birthday[2] = day;
    }
}