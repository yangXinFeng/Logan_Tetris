package ui;

import java.awt.*;

/**
 * 下一个层
 */
public class LayerNext extends Layer {

    public LayerNext(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void paint(Graphics g) {
        this.createWindow(g);
        //如果是开始状态才绘制下一个方块
        if (this.dto.isStart()) {
            drawImageAtCenter(Img.NEXT_ACT[this.dto.getNext()], g);
        }
    }


}
