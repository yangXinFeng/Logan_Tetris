package config;

import org.dom4j.Element;

import java.io.Serializable;

public class DataConfig implements Serializable {

    private final int maxRow;
    private final DataInterfaceConfig dataA;
    private final DataInterfaceConfig dataB;

    public DataConfig(Element data) {
        //初始化最大行数
        this.maxRow = Integer.parseInt(data.attributeValue("maxRow"));
        //初始化dataA
        this.dataA = new DataInterfaceConfig(data.element("dataA"));
        //初始化dataB
        this.dataB = new DataInterfaceConfig(data.element("dataB"));
    }

    public DataInterfaceConfig getDataA() {
        return dataA;
    }

    public DataInterfaceConfig getDataB() {
        return dataB;
    }

    public int getMaxRow() {
        return maxRow;
    }
}
