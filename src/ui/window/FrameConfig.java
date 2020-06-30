package ui.window;

import control.GameControl;
import util.FrameUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 窗口配置
 */
public class FrameConfig extends JFrame {
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JButton btnUser = new JButton("应用");
    private final static Image IMG_PSP = new ImageIcon("data/psp.jpg").getImage();
    private TextCtrl[] keyText = new TextCtrl[8];
    private JLabel errorMsg = new JLabel();

    private GameControl gameControl;
    private final static String[] METHOD_NAMES = {
            "keyUp", "keyLeft", "keyDown", "keyRight",
            "keyFunUp", "keyFunLeft", "keyFunRight", "keyFunDown"
    };
    private final static String PATH = "data/control.dat";


    public FrameConfig(GameControl gameControl) {
        //获得游戏控制器对象
        this.gameControl = gameControl;
        //设置布局管理器为边界布局
        this.setLayout(new BorderLayout());
        this.setTitle("设置");
        //初始化按键输入框
        this.initKeyText();
        //添加主面板
        this.add(this.createMainPanel(), BorderLayout.CENTER);
        //添加按钮面板
        this.add(this.createButtonPanel(), BorderLayout.SOUTH);
        //设置窗口大小,且不能改变大小
        this.setSize(645, 315);
        this.setResizable(false);
        //居中
        FrameUtil.setFrameCenter(this);
    }

    /**
     * 初始化按键输入框
     */
    private void initKeyText() {
        keyText[0] = new TextCtrl(63, 87, 40, 20, METHOD_NAMES[0]);//上
        keyText[1] = new TextCtrl(28, 110, 40, 20, METHOD_NAMES[1]);//左
        keyText[2] = new TextCtrl(63, 135, 40, 20, METHOD_NAMES[2]);//下
        keyText[3] = new TextCtrl(168, 10, 40, 20, METHOD_NAMES[3]);//右
        keyText[4] = new TextCtrl(539, 87, 40, 20, METHOD_NAMES[4]);//三角
        keyText[5] = new TextCtrl(442, 8, 40, 20, METHOD_NAMES[5]);//方块
        keyText[6] = new TextCtrl(580, 110, 40, 20, METHOD_NAMES[6]);//圆圈
        keyText[7] = new TextCtrl(542, 135, 40, 20, METHOD_NAMES[7]);//大叉

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH));
            HashMap<Integer, String> cfgSet = (HashMap<Integer, String>) ois.readObject();
            ois.close();
            Set<Map.Entry<Integer, String>> entrySet = cfgSet.entrySet();
            for (Map.Entry<Integer, String> e : entrySet) {
                for (TextCtrl tc : keyText) {
                    if (tc.getMethodName().equals(e.getValue())) {
                        tc.setKeyCode(e.getKey());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建按钮面板
     *
     * @return JPanel
     */
    private JPanel createButtonPanel() {
        //创建按钮面板，流式布局
        JPanel jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        //给确定按钮添加事件监听
        this.btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (writeConfig()) {
                    setVisible(false);
                    gameControl.setOver();
                }
            }
        });
        this.errorMsg.setForeground(Color.RED);
        jp.add(this.errorMsg);
        jp.add(this.btnOk);
        //给取消按钮添加事件监听
        this.btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
                gameControl.setOver();
            }
        });
        jp.add(this.btnCancel);
        //给应用按钮添加事件监听
        this.btnUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                writeConfig();
                gameControl.repaint();
            }
        });
        jp.add(this.btnUser);
        return jp;
    }

    /**
     * 创建主面板
     *
     * @return JTabbedPane
     */
    private JTabbedPane createMainPanel() {
        JTabbedPane jtp = new JTabbedPane();
        jtp.addTab("控制设置", this.createControlPanel());
        return jtp;
    }

    /**
     * 玩家控制设置面板
     *
     * @return JPanel
     */
    private JPanel createControlPanel() {
        JPanel jp = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(IMG_PSP, 0, 0, null);
            }
        };
        //设置布局管理器
        jp.setLayout(null);
        for (int i = 0; i < keyText.length; i++) {
            jp.add(keyText[i]);
        }
        return jp;
    }


    /**
     * 写入游戏配置
     */
    private boolean writeConfig() {
        HashMap<Integer, String> keySet = new HashMap<Integer, String>();
        for (int i = 0; i < this.keyText.length; i++) {
            int keyCode = this.keyText[i].getKeyCode();
            if (keyCode == 0) {
                this.errorMsg.setText("错误按键");
                return false;
            }
            keySet.put(keyCode, this.keyText[i].getMethodName());
        }
        if (keySet.size() != 8) {
            this.errorMsg.setText("重复按键");
            return false;
        }
        try {
            //写入控制配置
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH));
            oos.writeObject(keySet);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
            this.errorMsg.setText(e.getMessage());
            return false;
        }
        this.errorMsg.setText(null);
        return true;
    }
}
