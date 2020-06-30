package ui;

import config.GameConfig;
import dto.Player;

import java.awt.*;
import java.util.List;

/**
 * 数据值槽层
 */
public abstract class LayerData extends Layer {
    //最大数据行数
    private static final int MAX_ROW = GameConfig.getDataConfig().getMaxRow();
    //起始Y坐标
    private static int START_Y = 0;
    //间距
    private static int SPA = 0;
    //值槽外径
    private static final int RectH = IMG_RECT_H + 4;

    protected LayerData(int x, int y, int w, int h) {
        super(x, y, w, h);
        SPA = (this.h - RectH * 5 - (PADDING << 1) - Img.DB.getHeight(null)) / MAX_ROW;
        START_Y = PADDING + Img.DB.getHeight(null) + SPA;
    }

    /**
     * 绘制所有值槽
     *
     * @param imgTitle:图片
     * @param players：玩家
     * @param g：画笔
     */
    public void showData(Image imgTitle, List<Player> players, Graphics g) {
        //绘制标题
        g.drawImage(imgTitle, this.x + PADDING, this.y + PADDING, null);
        //获得现在分数
        int nowPoint = this.dto.getNowPoint();
        //循环记录
        for (int i = 0; i < MAX_ROW; i++) {
            //获得一条玩家记录
            Player pla = players.get(i);
            //获得该得分
            int recodePoint = pla.getPoint();
            //计算现在与记录分数比值
            double percent = (double) nowPoint / recodePoint;
            //若破纪录则设为100%
            percent = percent > 1 ? 1.0 : percent;
            //绘制单条记录
            String strPoint = recodePoint == 0 ? null : Integer.toString(recodePoint);
            this.drawRect(START_Y + i * (RectH + SPA),
                    pla.getName(), strPoint,
                    percent, g);
        }
    }

    @Override
    abstract public void paint(Graphics g);
}
