package dao;

import dto.Player;

import java.io.*;
import java.util.HashMap;
import java.util.List;

/**
 * 本地磁盘操作
 */
public class DataDisk implements Data {
    private final String filePath;

    public DataDisk(HashMap<String, String> param) {
        this.filePath = param.get("path");
    }

    @Override
    public List<Player> loadData() {
        ObjectInputStream ois = null;
        List<Player> players = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(filePath));
            players = (List<Player>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return players;
    }

    @Override
    public void saveData(Player pla) {
        //先取出数据
        List<Player> players = this.loadData();
        //TODO 判断记录是否超过5条,若超过则删除分数低者
        //追加数据
        players.add(pla);
        //重新写入
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(filePath));
            oos.writeObject(players);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
