package control;

import config.DataInterfaceConfig;
import config.GameConfig;
import dao.Data;
import dto.GameDto;
import dto.Player;
import service.GameService;
import service.GameTetris;
import ui.window.FrameGame;
import ui.window.FrameSavePoint;
import ui.window.PanelGame;
import ui.window.FrameConfig;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 接受玩家键盘事件
 */
public class GameControl {
    //数据访问接口A
    private Data dataA;
    //数据访问接口B
    private Data dataB;
    //游戏逻辑层
    private GameService gameService;
    //游戏界面层
    private PanelGame panelGame;
    //游戏控制设置窗口
    private ui.window.FrameConfig frameConfig;
    //保存分数窗口
    private FrameSavePoint frameSavePoint;
    //行为控制
    private Map<Integer, Method> actionList;
    //游戏线程
    private Thread gameThread = null;
    //游戏数据源
    private GameDto dto = null;

    public GameControl() {
        //创建游戏数据源头
        this.dto = new GameDto();
        //创建游戏逻辑块（连接数据源）
        this.gameService = new GameTetris(dto);
        //获得数据接口A类对象
        this.dataA = createDataObject(GameConfig.getDataConfig().getDataA());
        //设置数据库记录到游戏
        this.dto.setDbRecode(dataA.loadData());
        //从数据接口B获得本地磁盘记录
        this.dataB = createDataObject(GameConfig.getDataConfig().getDataB());
        //设置本地磁盘记录到游戏
        this.dto.setDiskRecode(dataB.loadData());
        //创建游戏面板
        this.panelGame = new PanelGame(this, dto);
        //读取用户控制设置
        this.setControlConfig();
        //初始化用户配置窗口
        this.frameConfig = new FrameConfig(this);
        //初始化保存分数窗口
        this.frameSavePoint = new FrameSavePoint(this);
        //初始化游戏主窗口，安装游戏面板
        new FrameGame(this.panelGame);
    }

    /**
     * 读取用户控制设置
     */
    private void setControlConfig() {
        //创建键盘码与方法名的映射数组
        this.actionList = new HashMap<Integer, Method>();
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("data/control.dat"));
            HashMap<Integer, String> cfgSet = (HashMap<Integer, String>) ois.readObject();
            Set<Map.Entry<Integer, String>> entrySet = cfgSet.entrySet();
            for (Map.Entry<Integer, String> e : entrySet) {
                actionList.put(e.getKey(), this.gameService.getClass().getMethod(e.getValue()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //
    }

    /**
     * 创建数据对象
     *
     * @param cfg:数据接口配置对象
     * @return 数据对象
     */
    private Data createDataObject(DataInterfaceConfig cfg) {
        try {
            //获得类对象
            Class<?> cls = Class.forName(cfg.getClassName());
            Constructor<?> ctr = cls.getConstructor(HashMap.class);
            return (Data) ctr.newInstance(cfg.getParam());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据玩家控制来决定行为
     *
     * @param keyCode:键盘码
     */
    public void actionByKeyCode(int keyCode) {
        try {
            if (this.actionList.containsKey(keyCode)) {
                this.actionList.get(keyCode).invoke(this.gameService);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.panelGame.repaint();
    }

    /**
     * 显示玩家控制窗口
     */
    public void showUserConfig() {
        this.frameConfig.setVisible(true);
    }

    /**
     * 子窗口关闭事件
     */
    public void setOver() {
        this.panelGame.repaint();
        this.setControlConfig();
    }

    /**
     * 开始按钮事件
     */
    public void start() {
        //按钮设置为不可点击
        this.panelGame.buttonSwitch(false);
        //关闭窗口
        this.frameConfig.setVisible(false);
        this.frameSavePoint.setVisible(false);
        //游戏数据初始化
        this.gameService.startGame();
        //刷新画面
        panelGame.repaint();
        //创建线程对象
        this.gameThread = new MainThread();
        //启动线程
        this.gameThread.start();
        //刷新画面
        this.panelGame.repaint();
    }

    /**
     * 失败后的处理
     */
    public void afterLose() {
        if (!this.dto.isCheat()) {//若使用作弊键，则不能保存记录
            //显示保存得分窗口
            this.frameSavePoint.showWindow(this.dto.getNowPoint());
        }
        //使按钮可以点击
        this.panelGame.buttonSwitch(true);
    }

    //刷新画面
    public void repaint() {
        this.panelGame.repaint();
    }

    /**
     * 保存分数
     *
     * @param name:玩家名字
     */
    public void savePoint(String name) {
        Player pla = new Player(name, this.dto.getNowPoint());
        //保存记录到数据库
        this.dataA.saveData(pla);
        //保存记录到本次磁盘
        this.dataB.saveData(pla);
        //设置数据库记录到游戏
        this.dto.setDbRecode(dataA.loadData());
        //设置磁盘记录到游戏
        this.dto.setDiskRecode(dataB.loadData());
        //刷新画面
        this.panelGame.repaint();

    }

    /**
     * 游戏主线程
     */
    private class MainThread extends Thread {
        public void run() {
            //刷新画面
            panelGame.repaint();
            //主循环
            while (dto.isStart()) {
                try {
                    //线程睡眠
                    Thread.sleep(dto.getSleepTime());
                    //如果暂停，则不执行主行为
                    if (dto.isPause()) {
                        continue;
                    }
                    //游戏主行为
                    gameService.mainAction();
                    //刷新画面
                    panelGame.repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            afterLose();
        }
    }
}
