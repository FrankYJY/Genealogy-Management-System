package scene;


import dataLayer.LiveState;
import dataLayer.Member;
import dataLayer.Tree;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author 姚君彦
 * 2020/6/18,21:09
 * 奇怪的程序增加了
 */
public class TreeDrawing {
    private static ArrayList<Integer> generationCount;
    private static double canvasWidth;//画布宽度
    private static double canvasHeight;//画布长度
    private static int gridWidth = 40;//每个结点的宽度
    private static int gridHeight = 20;//每个结点的高度
    private static int yGap = 50;//每2个结点的垂直距离
    private static int[] xGap;//每2个结点的水平距离
    private static int[] drawCount;//记录每层已经画了几个
    private static int startY = 10;//边框Y，默认距离顶部10像素
    private static int startX = 10;//边框X，默认水平居中对齐
    private static Calendar now;
    private static int month;
    private static int date;

    public static void draw(Canvas canvas,Tree tree){
        now = Calendar.getInstance();//可以对每个时间域单独修改
        month = now.get(Calendar.MONTH)+1;//calendar 获得的月份是从0开始的
        date = now.get(Calendar.DATE);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());//清空画布
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        generationCount = tree.generationStatistic();//用于分配空间
        yGap = ((int)canvasHeight-2*startY)/(generationCount.size()+1)-gridHeight;//垂直距离固定
        xGap = new int[generationCount.size()];
        //转换成每行的距离
        for (int i = 0; i < xGap.length; i++) {
            xGap[i] = (((int)canvasWidth-2*startX))/(generationCount.get(i))-gridWidth;
        }
        drawCount = new int[generationCount.size()];
        draw(gc,tree.getRoot());
    }

    private static void draw(GraphicsContext gc, Member member){
//        gc.fillText(member.getName(),);
        //generation好像是从0开始的
        int lay = member.getGeneration();
        int y = startY+lay*(yGap +gridHeight);//框位置
        int StringY = y + gridHeight/2+5;//文字位置

        //符合要求的变成蓝框
        if (member.getIsAlive()== LiveState.alive && member.getBirthday()[1]==month && member.getBirthday()[2] == date){
            gc.setStroke(Color.BLUE);
        }else{
            gc.setStroke(Color.BLACK);
        }

        int x = startX + drawCount[lay]*(xGap[lay]+gridWidth);
        gc.strokeRect(x,y,gridWidth,gridHeight);
        gc.fillText(member.getName(),x+5,StringY);
        drawCount[lay]=drawCount[lay]+1;

        gc.setStroke(Color.BLACK);//画线的部分还是回归黑色
        for (int i = 0; i < member.getDescendents().size(); i++) {
            //计算下一层的中上点，有重复计算，可以优化
            gc.strokeLine(x+gridWidth/2,y+gridHeight,startX + drawCount[lay+1]*(xGap[lay+1]+gridWidth)+gridWidth/2,y+gridHeight+yGap);
            draw(gc,member.getDescendents().get(i));
        }

    }








    public static void drawExamples(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //shape examples
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(40, 10, 10, 40);
        gc.fillOval(10, 60, 30, 30);
        gc.strokeOval(60, 60, 30, 30);
        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
        gc.fillPolygon(new double[]{10, 40, 10, 40},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolygon(new double[]{60, 90, 60, 90},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolyline(new double[]{110, 140, 110, 140},
                new double[]{210, 210, 240, 240}, 4);
    }


}
