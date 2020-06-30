package ui;

import config.GameConfig;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片库
 */
public class Img {
    private Img() {
    }

    //图片路径
    public static final String GRAPHICS_PATH = "Graphics/";

    private static final String DEFAULT_PATH = "default/";

    public static void setSkin(String path) {
        String skinPath = GRAPHICS_PATH + path;

        //个人签名图片
        SIGN = new ImageIcon(skinPath + "string/sign.png").getImage();
        //窗口图片
        WINDOW = new ImageIcon(skinPath + "window/Window.png").getImage();
        //数字图片(w:34,h:58 )
        NUMBER = new ImageIcon(skinPath + "string/num.png").getImage();
        //矩形槽图片
        RECT = new ImageIcon(skinPath + "window/rect.png").getImage();
        //数据库图片
        DB = new ImageIcon(skinPath + "string/db.png").getImage();
        //本地记录图片
        DISK = new ImageIcon(skinPath + "string/disk.png").getImage();
        //方块图片
        ACT = new ImageIcon(skinPath + "game/rect.png").getImage();
        //阴影图片
        SHADOW = new ImageIcon(skinPath + "game/shadow.png").getImage();
        //等级图片
        LEVEL = new ImageIcon(skinPath + "string/level.png").getImage();
        //标题图片（分数）
        POINT = new ImageIcon(skinPath + "string/point.png").getImage();
        //标题图片(消行)
        RMLINE = new ImageIcon(skinPath + "string/rmline.png").getImage();
        //开始按钮
        BTN_START = new ImageIcon(skinPath + "string/start.png");
        //设置按钮
        BTN_CONFIG = new ImageIcon(skinPath + "string/config.png");
        //暂停图片
        PAUSE = new ImageIcon(skinPath + "string/pause.png").getImage();

        //下一个方块图片
        NEXT_ACT = new Image[GameConfig.getSystemConfig().getTypeConfig().size()];
        for (int i = 0; i < NEXT_ACT.length; i++) {
            NEXT_ACT[i] = new ImageIcon(skinPath + "game/" + i + ".png").getImage();
        }
        //背景图片数组
        File dir = new File(skinPath + "background");
        File[] files = dir.listFiles();
        BG_LIST = new ArrayList<Image>();
        for (File file : files) {
            if (!file.isDirectory()) {
                BG_LIST.add(new ImageIcon(file.getPath()).getImage());
            }
        }
    }

    //个人签名图片
    public static Image SIGN = null;
    //窗口图片
    public static Image WINDOW = null;
    //数字图片(w:34,h:58 )
    public static Image NUMBER = null;
    //矩形槽图片
    public static Image RECT = null;
    //数据库图片
    public static Image DB = null;
    //本地记录图片
    public static Image DISK = null;
    //方块图片
    public static Image ACT = null;
    //阴影图片
    public static Image SHADOW = null;
    //等级图片
    public static Image LEVEL = null;
    //标题图片（分数）
    public static Image POINT = null;
    //标题图片(消行)
    public static Image RMLINE = null;
    //开始按钮
    public static ImageIcon BTN_START = null;
    //设置按钮
    public static ImageIcon BTN_CONFIG = null;
    //暂停图片
    public static Image PAUSE = null;

    //下一个背景图片数组
    public static List<Image> BG_LIST = null;
    //下一个方块数组
    public static Image[] NEXT_ACT = null;

    //初始化图片
    static {
        setSkin(DEFAULT_PATH);
    }
}
