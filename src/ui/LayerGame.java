package ui;

import config.GameConfig;
import entity.GameAct;

import java.awt.*;

/**
 * 游戏层
 */
public class LayerGame extends Layer {

    //左位移偏移量
    private static final int SIZE_ROL = GameConfig.getFrameConfig().getSizeRol();
    //左右边距
    private static final int LEFT_SIDE = 0;
    private static final int RIGHT_SIDE = GameConfig.getSystemConfig().getMaxX();
    //失败图片方块的idx
    private static final int LOSE_IDX = GameConfig.getFrameConfig().getLoseIdx();

    public LayerGame(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    /**
     * 绘制
     * @param g：画笔
     */
    public void paint(Graphics g) {
        this.createWindow(g);
        //获得方块数组集合
        GameAct act = this.dto.getGameAct();
        if (act != null) {
            Point[] points = act.getActPoints();
            //绘制阴影
            this.drawShadow(points, g);
            //绘制活动方块
            this.drawMainAct(points, g);
        }
        //绘制游戏地图
        this.drawMap(g);
        //暂停
        if (this.dto.isPause()) {
            this.drawImageAtCenter(Img.PAUSE, g);
        }
    }

    /**
     * 绘制活动方块
     *
     * @param g：画笔
     */
    private void drawMainAct(Point[] points, Graphics g) {
        //获得方块类型编号(0~6)
        int typeCode = this.dto.getGameAct().getTypeCode();
        //绘制方块
        for (int i = 0; i < points.length; i++) {
            this.drawActByPoint(points[i].x, points[i].y, typeCode + 1, g);
        }
    }

    /**
     * 绘制地图
     *
     * @param g：画笔
     */
    private void drawMap(Graphics g) {
        boolean[][] map = this.dto.getGameMap();
        //计算当前堆积颜色
        int lv = this.dto.getNowlevel();
        int imgIdx = lv == 0 ? 0 : (lv - 1) % 7 + 1;
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                if (map[x][y]) {
                    this.drawActByPoint(x, y, imgIdx, g);
                }
            }
        }
    }


    /**
     * 绘制阴影
     *
     * @param points：点集合
     */
    private void drawShadow(Point[] points, Graphics g) {
        //阴影关闭
        if (!this.dto.isShowShadow()) {
            return;
        }
        int lefX = RIGHT_SIDE;
        int rightX = LEFT_SIDE;
        //若当前的x位置小于最右端，则取当前x;若当前的x位置大于最左端，则取当前x;
        for (Point p : points) {
            lefX = p.x < lefX ? p.x : lefX;
            rightX = p.x > rightX ? p.x : rightX;
        }
        g.drawImage(Img.SHADOW,
                this.x + BORDER + (lefX << SIZE_ROL),
                this.y + BORDER,
                (rightX - lefX + 1) << SIZE_ROL,
                this.h - (BORDER << 1), null
        );
    }

    /**
     * 绘制正方形块
     *
     * @param x：当前x
     * @param y：当前y
     * @param imgIdx：方块图片位置
     * @param g：画笔
     */
    private void drawActByPoint(int x, int y, int imgIdx, Graphics g) {
        imgIdx = this.dto.isStart() ? imgIdx : LOSE_IDX;
        g.drawImage(Img.ACT,
                this.x + (x << SIZE_ROL) + BORDER,     //this.x + x * ACT_SIZE + 7
                this.y + (y << SIZE_ROL) + BORDER,     //this.y + y * ACT_SIZE + 7
                this.x + (x + 1 << SIZE_ROL) + BORDER, //this.x + x * ACT_SIZE + ACT_SIZE + 7
                this.y + (y + 1 << SIZE_ROL) + BORDER, //this.y + y * ACT_SIZE + ACT_SIZE + 7
                imgIdx << SIZE_ROL, 0, (imgIdx + 1) << SIZE_ROL, 1 << SIZE_ROL, null);
    }
}
