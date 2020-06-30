package ui.window;

import control.GameControl;
import util.FrameUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 保存分数
 */
public class FrameSavePoint extends JFrame {
    private JLabel lbPoint = null;
    private JTextField txName = null;
    private JButton btnOk = null;
    private JLabel errorMsg = null;
    private GameControl gameControl = null;

    public FrameSavePoint(GameControl gameControl) {
        this.gameControl = gameControl;
        this.setTitle("保存记录");
        this.setSize(256, 128);
        FrameUtil.setFrameCenter(this);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.createCom();
        this.createAction();
    }

    /**
     * 显示窗口
     */
    public void showWindow(int point) {
        this.lbPoint.setText("您的得分：" + point);
        this.setVisible(true);
    }

    /**
     * 创建事件监听
     */
    private void createAction() {
        this.btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = txName.getText();
                if (name.length() > 16 || name == null || "".equals(name)) {
                    errorMsg.setText("请输入16位以下的名字");
                } else {
                    setVisible(false);
                    gameControl.savePoint(name);
                }
            }
        });
    }

    /**
     * 初始化控件
     */
    private void createCom() {
        //创建北部面板
        JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //创建分数文字
        this.lbPoint = new JLabel("");
        //添加分数文字到北部面板
        north.add(lbPoint);
        //创建错误信息控件
        this.errorMsg = new JLabel();
        this.errorMsg.setForeground(Color.RED);
        //添加错误消息到北部面板
        north.add(this.errorMsg);
        //北部面板添加到主面板
        this.add(north, BorderLayout.NORTH);
        //创建中部面板
        JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //创建文本框
        this.txName = new JTextField(10);
        //设置文字
        center.add(new JLabel("请输入您的名字"));
        //文本框添加到中部面板
        center.add(this.txName);
        //中部面板添加到文本框
        this.add(center, BorderLayout.CENTER);
        //创建确定按钮
        this.btnOk = new JButton("确定");
        //创建南部面板
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //按钮添加到南部面板
        south.add(btnOk);
        //南部面板添加到主面板
        this.add(south, BorderLayout.SOUTH);
    }
}
