package dataLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author 姚君彦
 * 2020/6/14,0:44
 * 奇怪的程序增加了
 */
public class Tree implements Serializable {
    public static final long serialVersionUID = 42L;//需要手动设定，否则重新编译会自动生成不同序列号
    private Member root;//存根
    private LinkedList<Member> sortedlist;//记录按生日排序的列表
    private ArrayList<Integer> generationCount;//记录每一代有几个人
    //    private static specialLinkedList list = new specialLinkedList();
    static Member tempmem = null;

    //Construct the empty tree
    public Tree(Member ancient) {
        root = ancient;
        generationCount = new ArrayList<Integer>();
        resort();
    }

    //origin
    public void insert(Member x) {
        insert(root, x);
    }

    public boolean insert(String parentName, Member mem2add) {
        Member a = find(parentName);
        boolean temp = insert(a,mem2add);
        resort();
        return temp;
    }

    //以下是增删查改功能
    private boolean insert(Member parent, Member mem2add) {
        if (find(parent.getName())!=null) {
            parent.addinDesendents(mem2add);
            switch (parent.getGender()) {
                case male:
                    mem2add.setFather(parent);
                    break;
                case female:
                    mem2add.setMother(parent);
                    break;
                case unknown:
            }
            mem2add.setGenerationUnder(parent);
            return true;
        }else{
            return false;
        }
    }

    public void remove(String movename){
        remove(root,movename);
        resort();
    }

    private void remove(Member t, String name){
        //清空父母里面自己的记录
        if(name.equals(t.getName()) ){
            if (t.getFather()!= null){
                t.getFather().getDescendents().remove(t);
            }
            if (t.getMother()!= null){
                t.getMother().getDescendents().remove(t);
            }
        }
        //递归寻找
        ArrayList<Member> temp = t.getDescendents();
        for (int i = 0; i < temp.size(); i++) {
            remove(temp.get(i),name);
        }
    }

//    private void removefromdeslist(ArrayList des,int isDesendentNo){
//        for (int i = isDesendentNo; i < des.size(); i++) {
//            des.remove()
//            des.set(i,des.get(i+1));
//        }
//    }

    //树形查找
    //从根开始找所有
    public Member find(String name){
        tempmem = null;
        return find(root,name);
    }

    //从特定开始找子树,如果直接调用这个双参方法前需要先清空tempmem，设为null
    private Member find(Member t, String name){
        boolean have=name.equals(t.getName());
        if(have ){
            tempmem = t;
            return tempmem;
        }
        ArrayList<Member> temp = t.getDescendents();
        for (int i = 0; i < temp.size(); i++) {
            find(temp.get(i),name);
        }
        return tempmem;//if return root, not founded
    }

    //理论上是可以重复名字的，
    // 但是会对删除部分以及移动部分造成很大的复杂性，需要每次查找父节点和子节点的时候遍历给用户确认后删除，过于繁琐
    //所以在视图部分禁止了重复名字
    //如下，这里仅实现了重复名字的查找并且没有实际使用（插入时重复是不会带来问题的）

    //链表查找
    public LinkedList<Member> findAll(String name){
        LinkedList<Member> sameNameList = new LinkedList<Member>();
        for (int i = 0; i < sortedlist.size(); i++) {
            if (sortedlist.get(i).getName().equals(name)){
                sameNameList.add(sortedlist.get(i));
            }
        }
        return sameNameList;
    }

    public boolean isDirectRelated(String a,String b){
        Member memberA = find(a);
        Member memberB = find(b);
        tempmem =null;
        Member temp1 = find(memberA,b);
        tempmem = null;
        Member temp2 = find(memberB,a);
//        if (temp1!=null)
//        System.out.println("!!!1"+memberA.getName()+temp1.getName());
//        if (temp2!=null)
//        System.out.println("!!!2"+memberB.getName()+temp2.getName());
        if (temp1==null&&temp2 == null) {
                return false;
        }else{
        return true;
        }
    }

    //排序并刷新链表，这里每次改动树都需要resort，也可以不进行
    public void resort(){
        this.sortedlist = new LinkedList<Member>();
        resort(root);
    }

//    private void resort(Member t){
//        resort();
//
//    }

    private static boolean sortflag = false;//标记是否已经被插入过
    //正常sort,因为要不断增加所以用链表
    private void resort(Member t){
        //已经有多个的情况,比较前后相邻两个
        for (int i = 0; i < sortedlist.size()-1; i++) {
            if (t.compareTo(sortedlist.get(i))>=0 && t.compareTo(sortedlist.get(i+1))<0){
                sortedlist.add(i+1,t);
                sortflag =true;
                break;
            }
        }
        //已经有一个或多个的情况,比较第一个
        if (sortedlist.size()>0){
            if (t.compareTo(sortedlist.get(0))<0){
                sortedlist.add(0,t);
                sortflag=true;
            }
        }
        //如果不满足前两个情况，在末尾加入
        if (!sortflag){
            sortedlist.add(sortedlist.size(),t);
        }
        sortflag = false;
        //递归排序所有
        ArrayList<Member> temp = t.getDescendents();
        for (int i = 0; i < temp.size(); i++) {
            resort(temp.get(i));
        }
    }

//    static class specialLinkedList {
//        Member member;
//        specialLinkedList next;
//    }

//    private void specialresort(){
//
//    }



    //通过链表先进行检查是否存在，更快
    public boolean isInList(String t){
        for (int i = 0; i < sortedlist.size()-1; i++) {
            if (t.equals(sortedlist.get(i).getName())){
                return true;
            }
        }
        return false;
    }

    public LinkedList<Member> getSortedlist() {
        return sortedlist;
    }




    //查找一个名字,连他之下所有孩子都转移到目标父母之下
    public boolean move2des(String moved,String targetParent){
        Member movedMem = find(moved);
        Member targetParentMem = find(targetParent);
        if (movedMem==null||targetParentMem==null){
           return false;//操作失败返回false
        }else {
            this.remove(moved);
            targetParentMem.addinDesendents(movedMem);
            switch (targetParentMem.getGender()) {
                case male:
                    movedMem.setFather(targetParentMem);
                    break;
                case female:
                    movedMem.setMother(targetParentMem);
                    break;
                case unknown:
            }
            resort();
        return true;//如果操作成功返回true
        }
    }

    public Member getRoot() {
        return root;
    }

    //到用时再进行统计
    public ArrayList<Integer> generationStatistic(){
        generationCount = new ArrayList<Integer>();
        generationStatistic(root);
        System.out.println("generation statistic over");
        return generationCount;
    }

    //统计每一代有几个人，用于画树
    private void generationStatistic(Member member){
        int tempGen = member.getGeneration();
        if (tempGen<generationCount.size()){
            generationCount.set(tempGen, generationCount.get(tempGen)+1);
        }else{
            generationCount.add(1);
        }
        ArrayList<Member> tempDes = member.getDescendents();
        for (int i = 0; i<tempDes.size(); i++) {
            generationStatistic(tempDes.get(i));
        }
    }
}
