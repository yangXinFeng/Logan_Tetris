package ui;

import java.awt.*;

/**
 * 签名层
 */
public class LayerAbout extends Layer {
    public LayerAbout(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void paint(Graphics g) {
        this.createWindow(g);
        this.drawImageAtCenter(Img.SIGN, g);
    }
}
