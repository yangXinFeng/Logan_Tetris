package util;

import javax.swing.*;
import java.awt.*;

public class FrameUtil {
    /**
     * 居中
     *
     * @param jf:JFrame
     */
    public static void setFrameCenter(JFrame jf) {
        //居中
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screen = toolkit.getScreenSize();
        int x = screen.width - jf.getWidth() >> 1;
        int y = (screen.height - jf.getHeight() >> 1) - 32;
        jf.setLocation(x, y);
    }
}
