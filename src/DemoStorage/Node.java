package DemoStorage;

/**
 * @author 姚君彦
 * 2020/6/18,18:25
 * 奇怪的程序增加了
 */
/**
 * 2010-11-8
 * John
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 树的结构
 * @author John
 *
 */
public class Node {
    private String name;    //该结点名字
    private int layer = 0;  //该结点层级

    private List<Node> childs = null; //保存该结点的孩子

    public Node(String name){
        this.name = name;
    }

    /**
     * 增加一个孩子
     * @param n 要作为孩子增加的结点
     */
    public void add(Node n){
        if(childs == null)
            childs = new ArrayList<Node>();
        n.setLayer(layer+1);
        setChildLayout(n);
        childs.add(n);
    }

    /**
     * 递归设置孩子的层级
     * @param n
     */
    private void setChildLayout(Node n){
        if(n.hasChild()){
            List<Node> c = n.getChilds();
            for(Node node : c){
                node.setLayer(node.getLayer()+1);
                setChildLayout(node);
            }
        }
    }

    /**
     * 获取结点名
     * @return 结点名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置结点名
     * @param name 结点名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取该结点的层级
     * @return 该结点的层级
     */
    public int getLayer() {
        return layer;
    }

    /**
     * 设置该结点的层级
     * @param layer 该结点的层级
     */
    public void setLayer(int layer) {
        this.layer = layer;
    }

    /**
     * 获取该结点的孩子
     * @return 所有孩子结点
     */
    public List<Node> getChilds() {
        return childs;
    }

    /**
     * 检查是否存在孩子
     * @return 是则返回true，否则返回false
     */
    public boolean hasChild(){
        return childs == null ? false : true;
    }

    /**
     * 递归打印所有的结点（包括子结点）
     * @param n 要打印的根结点
     */
    public void printAllNode(Node n){
        System.out.println(n);
        if(n.hasChild()){
            List<Node> c = n.getChilds();
            for(Node node : c){
                printAllNode(node);
            }
        }
    }

    public String getAllNodeName(Node n){
        String s = n.toString()+"/n";
        if(n.hasChild()){
            List<Node> c = n.getChilds();
            for(Node node : c){
                s+=getAllNodeName(node)+"/n";
            }
        }
        return s;
    }

    public String toString(){
        return layer+"层/t: "+name;
    }
}