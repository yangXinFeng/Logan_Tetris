package config;

import org.dom4j.Element;

import java.io.Serializable;

public class ButtonConfig implements Serializable {
    private final int buttonW;
    private final int buttonH;
    private final int startX;
    private final int startY;
    private final int userConfigX;
    private final int userConfigY;

    public ButtonConfig(Element button) {
        //初始化按钮宽度
        this.buttonW = Integer.parseInt(button.attributeValue("w"));
        //初始化按钮高度
        this.buttonH = Integer.parseInt(button.attributeValue("h"));
        //初始化开始按钮X值
        this.startX = Integer.parseInt(button.element("start").attributeValue("x"));
        //初始化开始按钮Y值
        this.startY = Integer.parseInt(button.element("start").attributeValue("y"));
        //初始化设置按钮X值
        this.userConfigX = Integer.parseInt(button.element("userConfig").attributeValue("x"));
        //初始化设置按钮Y值
        this.userConfigY = Integer.parseInt(button.element("userConfig").attributeValue("y"));
    }

    public int getButtonW() {
        return buttonW;
    }

    public int getButtonH() {
        return buttonH;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getUserConfigX() {
        return userConfigX;
    }

    public int getUserCOnfigY() {
        return userConfigY;
    }
}
