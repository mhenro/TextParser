package com.mycompany.app.model.settings;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mhenr on 07.02.2018.
 */
@XmlRootElement(name = "settings")
public class Settings implements Serializable {
    private Page page;
    private List<Column> columns;
    private String dataPath;
    private String destPath;

    @XmlElement(name = "page")
    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @XmlElementWrapper
    @XmlElement(name = "column")
    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    @XmlTransient
    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    @XmlTransient
    public String getDestPath() {
        return destPath;
    }

    public void setDestPath(String destPath) {
        this.destPath = destPath;
    }

    @XmlTransient
    public int getTotalColumnsWidth() {
        final int columnWidth = columns.stream()
                .map(column -> column.getWidth())
                .collect(Collectors.summingInt(n -> n));

        return columnWidth + getTotalColumnsOffset();
    }

    @XmlTransient
    private int getTotalColumnsOffset() {
        return columns.size() * 3;  //3 is DATA_DELEMITER + SPACE + SPACE
    }
}
