package config;

import org.dom4j.Element;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemConfig implements Serializable {
    private final int minX;
    private final int minY;
    private final int maxX;
    private final int maxY;
    private final int levelUp;
    private final List<Point[]> typeConfig;
    private final List<Boolean> typeRound;
    private final Map<Integer, Integer> plusPoint;


    public SystemConfig(Element system) {
        this.minX = Integer.parseInt(system.attributeValue("minX"));
        this.minY = Integer.parseInt(system.attributeValue("minY"));
        this.maxX = Integer.parseInt(system.attributeValue("maxX"));
        this.maxY = Integer.parseInt(system.attributeValue("maxY"));
        this.levelUp = Integer.parseInt(system.attributeValue("levelUp"));
        List<Element> rects = system.elements("rect");
        typeConfig = new ArrayList<Point[]>(rects.size());
        typeRound = new ArrayList<Boolean>(rects.size());
        for (Element rect : rects) {
            //是否旋转
            this.typeRound.add(Boolean.parseBoolean(rect.attributeValue("round")));
            //获得坐标对象
            List<Element> pointConfig = rect.elements("Point");
            //创建Point对象数组
            Point[] points = new Point[pointConfig.size()];
            //初始化Point对象数组
            for (int i = 0; i < pointConfig.size(); i++) {
                int x = Integer.parseInt(pointConfig.get(i).attributeValue("x"));
                int y = Integer.parseInt(pointConfig.get(i).attributeValue("y"));
                points[i] = new Point(x, y);
            }
            //把Point对象数组放到typeConfig中
            typeConfig.add(points);
        }
        //获得消行加分配置
        this.plusPoint = new HashMap<Integer, Integer>();
        List<Element> plusPointCfg = system.elements("plusPoint");
        for (Element cfg : plusPointCfg) {
            int rm = Integer.parseInt(cfg.attributeValue("rm"));
            int point = Integer.parseInt(cfg.attributeValue("point"));
            this.plusPoint.put(rm, point);
        }
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public List<Point[]> getTypeConfig() {
        return typeConfig;
    }

    public int getLevelUp() {
        return levelUp;
    }

    public List<Boolean> getTypeRound() {
        return typeRound;
    }

    public Map<Integer, Integer> getPlusPoint() {
        return plusPoint;
    }
}
