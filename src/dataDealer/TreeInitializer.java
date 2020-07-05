package dataDealer;

import dataLayer.GenderType;
import dataLayer.Member;
import dataLayer.Tree;

/**
 * @author 姚君彦
 * 2020/6/17,14:40
 * 奇怪的程序增加了
 */
public class TreeInitializer {
    public static void main(String[] args) {
        String treeInfoPath = "src/data/tree.data";
        //每棵树先拥有一个此节点，祖宗在其上进行修改信息，其他人按照insert加入
        Tree d = new Tree(new Member("可修改祖宗",0, GenderType.unknown));
        IODealer.writeObjectToFile(d,treeInfoPath);
    }
}
