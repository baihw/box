package com.wee0.box.model.vo;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/23 7:25
 * @Description 行政区划
 * <pre>
 * 补充说明
 * </pre>
 **/
public class AreaCode implements java.io.Serializable {
    /**
     * 行政区划主键Id
     */
    private Long areaId;
    /**
     * 行政区划名称
     */
    private String areaName;
    /**
     * 行政区划级别 (1: 省级别 2: 市级别 3: 区级别)
     */
    private Integer areaLevel;
    /**
     * 父级行政区划Id
     */
    private Long parentId;

    public AreaCode() {
    }

    /**
     * 行政区划主键Id
     *
     * @return
     */
    public Long getAreaId() {
        return areaId;
    }

    /**
     * 行政区划主键Id
     *
     * @param areaId
     */
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    /**
     * 行政区划名称
     *
     * @return
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * 行政区划名称
     *
     * @param areaName
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * 行政区划级别 (1: 省级别 2: 市级别 3: 区级别)
     *
     * @return
     */
    public Integer getAreaLevel() {
        return areaLevel;
    }

    /**
     * 行政区划级别 (1: 省级别 2: 市级别 3: 区级别)
     *
     * @param areaLevel
     */
    public void setAreaLevel(Integer areaLevel) {
        this.areaLevel = areaLevel;
    }

    /**
     * 父级行政区划Id
     *
     * @return
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 父级行政区划Id
     *
     * @param parentId
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
