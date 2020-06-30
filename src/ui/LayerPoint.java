package ui;

import config.GameConfig;

import java.awt.*;

public class LayerPoint extends Layer {
    //分数最大位数
    private static final int POINT_BIT = 5;
    //每升一级的消行数
    private static final int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();
    //消行y坐标
    private final int rmLineY;
    //分数y坐标
    private final int pointY;
    //共同x坐标
    private final int comX;
    //经验值槽Y坐标
    private final int expY;

    public LayerPoint(int x, int y, int w, int h) {
        super(x, y, w, h);
        //初始化共同显示的X坐标
        this.comX = this.w - IMG_NUMBER_W * POINT_BIT - PADDING;
        //初始化分数显示的Y坐标
        this.pointY = PADDING;
        //初始化消行显示的Y坐标
        this.rmLineY = this.pointY + Img.POINT.getHeight(null) + PADDING;
        //初始化经验值的Y坐标
        this.expY = this.rmLineY + Img.RMLINE.getHeight(null) + PADDING;
    }

    /**
     * 绘画
     * @param g:画笔
     */
    public void paint(Graphics g) {
        this.createWindow(g);
        //绘制窗口标题(分数)
        g.drawImage(Img.POINT, this.x + PADDING, this.y + pointY, null);
        this.drawNumberLeftPad(comX, 0, this.dto.getNowPoint(), 5, g);
        //绘制窗口标题(消行)
        g.drawImage(Img.RMLINE, this.x + PADDING, this.y + rmLineY, null);
        this.drawNumberLeftPad(comX, rmLineY - PADDING, this.dto.getNowRemoveLine(), 5, g);
        //绘制值槽（经验值）
        int rmLine = this.dto.getNowRemoveLine();
        drawRect(expY, "下一级", null, (double) (rmLine % LEVEL_UP) / (double) LEVEL_UP, g);
    }

}
