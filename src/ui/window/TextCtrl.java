package ui.window;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 文本框配置
 */
public class TextCtrl extends JTextField {
    private int keyCode;
    private final String methodName;

    public TextCtrl(int x, int y, int w, int h, String methodName) {

        //设置文本框位置
        this.setBounds(x, y, w, h);

        //初始化方法名
        this.methodName = methodName;
        //增加事件监听
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }
            @Override
            public void keyPressed(KeyEvent keyEvent) {
            }
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                setKeyCode(keyEvent.getKeyCode());
            }
        });
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
        this.setText(KeyEvent.getKeyText(this.keyCode));
    }

    public String getMethodName() {
        return methodName;
    }
}
