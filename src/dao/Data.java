package dao;

import dto.Player;

import java.util.List;

/**
 * 数据层接口
 */
public interface Data {
    //读取数据
    public List<Player> loadData();
    //存储数据
    public void saveData(Player players);
}
