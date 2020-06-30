package ui.window;

import config.FrameConfig;
import config.GameConfig;

import javax.swing.*;

/**
 * 游戏窗口配置
 */
public class FrameGame extends JFrame {
    public FrameGame(PanelGame panelGame) {
        //获得游戏配置
        FrameConfig fCfg=GameConfig.getFrameConfig();
        //设置标题
        this.setTitle(fCfg.getTitle());
        //设置默认关闭属性
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置大小
        this.setSize(fCfg.getWidth(), fCfg.getHeight());
        //不允许用户改变窗口大小
        this.setResizable(false);
        //设置默认Panel
        this.setContentPane(panelGame);
        //默认该窗口为显示
        this.setVisible(true);
    }
}
