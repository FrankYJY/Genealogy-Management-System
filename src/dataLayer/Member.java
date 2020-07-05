package dataLayer;

import java.io.*;
import java.util.ArrayList;

/**
 * @author 姚君彦
 * 2020/6/13,15:58
 * 奇怪的程序增加了
 */




//member就是node
public class Member implements Serializable, Comparable {
    public static final long serialVersionUID = 42L;//序列化与反序列化的序列数,
    // 需要手动设定，否则重新编译会自动生成不同序列号
    // 个人信息:
    private String name = "";
    private int[] birthday = new int[3];//year/month/day
    private int[] deathday = new int[3];//year/month/day
    private GenderType gender = GenderType.unknown;//自定义的枚举类型,下同:female male unknown
    private MarriageState ifMarried = MarriageState.unknown;//unmarried married unknown
    private String address = "";//地址
    private LiveState isAlive = LiveState.unknown;//died alive unknown
    private String extraMessage = "";//额外信息

    //交叉信息，作为树中节点需要记录的信息
    private int generation;//记录本例是第几世代
    private Member father = null;
    private Member mother = null;
    private ArrayList<Member> descendents = new ArrayList<Member>();//记录直系下一代



    public Member(String name, int year, GenderType a){
        setName(name);
        setBirthday(year,0,0);

        gender = a;
//        System.out.println(gender+"哇哇哇哇哇哇哇");
    }

//    Member(
//            String name,
//            livestate isAlive,
//            int birthyear,int birthmonth, int birthday,
//            int deathyear,int deathmonth, int deathday,//未死亡填写000
//            gendertype gender,
//            marriagestate ifMarried,
//            String address,
//            String extraMessage
//    ){
//        setName(name);
//        setIsAlive(isAlive);
//        setBirthday(birthyear,birthmonth,birthday);
//        setDeathday(deathyear,deathmonth,deathday);
//        setGender(gender);
//        setIfMarried(ifMarried);
//        setAddress(address);
//        setExtraMessage(extraMessage);
//    }
    public Member(
            String name,
            GenderType gender,
            LiveState isAlive,
            int birthyear,int birthmonth, int birthday,
            int deathyear,int deathmonth, int deathday,//未死亡填写000
            MarriageState ifMarried,
            String address,
            String extraMessage
    ){
        setName(name);
        setIsAlive(isAlive);
        setBirthday(birthyear,birthmonth,birthday);
        setDeathday(deathyear,deathmonth,deathday);
        setGender(gender);
        setIfMarried(ifMarried);
        setAddress(address);
        setExtraMessage(extraMessage);
    }

    public String toString(){
        System.out.println(name);
//        System.out.println(getBirthdayString());
//        System.out.println(getDeathdayString());
//        System.out.println(getGender());
//        System.out.println(getIfMarried());
//        System.out.println(getAddress());
//        System.out.println(getIsAlive());
//        System.out.println(getExtraMessage());
//        System.out.println(getGeneration());
        return name;
    }



    public void deleteDescendent(Member mem2add) {
        descendents.add(mem2add);
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getBirthday() {
        return birthday;
    }

    public String getBirthdayString() {
        return birthday[0]+"/"+birthday[1]+"/"+birthday[2];
    }

    public void setBirthday(int year, int month, int day) {
        this.birthday[0] = year;
        this.birthday[1] = month;
        this.birthday[2] = day;
    }

    public int[] getDeathday() {
        return deathday;
    }

    public String getDeathdayString() {
        return deathday[0]+"/"+deathday[1]+"/"+deathday[2];
    }

    public void setDeathday(int year, int month, int day) {
        this.deathday[0] = year;
        this.deathday[1] = month;
        this.deathday[2] = day;
    }

    public GenderType getGender() {
        return gender;
    }
    public String getGenderString() {
        return gender.toString();
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public MarriageState getIfMarried() {
        return ifMarried;
    }
    public String getIfMarriedString() {
        return ifMarried.toString();
    }

    public void setIfMarried(MarriageState ifMarried) {
        this.ifMarried = ifMarried;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LiveState getIsAlive() {
        return isAlive;
    }
    public String getIsAliveString() {
        return isAlive.toString();
    }

    public void setIsAlive(LiveState isAlive) {
        this.isAlive = isAlive;
    }

    public String getExtraMessage() {
        return extraMessage;
    }

    public void setExtraMessage(String extraMessage) {
        this.extraMessage = extraMessage;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGenerationUnder(Member parent) {
        this.generation = parent.getGeneration()+1;
    }

    public Member getFather() {
        return father;
    }

    public void setFather(Member father) {
        this.father = father;
    }

    public Member getMother() {
        return mother;
    }

    public void setMother(Member mother) {
        this.mother = mother;
    }

    public ArrayList<Member> getDescendents() {
        return descendents;
    }
    public String getDescendentsString() {
        String temp = "";
        for (int i = 0; i < descendents.size(); i++) {
            temp = temp + descendents.get(i).name + " ";
        }
        return temp;
    }

    public void setDescendents(ArrayList<Member> descendents) {
        this.descendents = descendents;
    }

    //插入时亲兄弟姐妹已经考虑排序
    public void addinDesendents(Member newMem){
        boolean flag = false;//代表是否遍历到插入位
        for (int i = 0; i < descendents.size()-1; i++) {
            flag = newMem.compareTo(descendents.get(i))>=0 & newMem.compareTo(descendents.get(i+1))<0;
            if (flag){
                descendents.add(i+1,newMem);
                break;
            }
        }
        if(!flag)
            descendents.add(newMem);
        freshGeneration(newMem,this);
    }

    //当加入子代或者加入子代树时需要依靠父母的代数刷新下面所有代数
    private void freshGeneration(Member newMem,Member parent) {
        newMem.setGenerationUnder(parent);
        for (int i = 0; i < newMem.getDescendents().size(); i++) {
            freshGeneration(newMem.getDescendents().get(i),newMem);
        }
    }

    @Override
    //compare birthday
    public int compareTo(Object o) {
        Member B = (Member)o;
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
    //
}
