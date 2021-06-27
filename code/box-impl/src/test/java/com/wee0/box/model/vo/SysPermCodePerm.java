package com.wee0.box.model.vo;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/23 7:22
 * @Description 权限编码权限关系
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SysPermCodePerm implements java.io.Serializable {
    /**
     * 权限字Id
     */
    private Long permCodeId;
    /**
     * 权限id
     */
    private Long permId;

    public SysPermCodePerm() {
    }

    /**
     * 权限字Id
     *
     * @return
     */
    public Long getPermCodeId() {
        return permCodeId;
    }

    /**
     * 权限字Id
     *
     * @param permCodeId
     */
    public void setPermCodeId(Long permCodeId) {
        this.permCodeId = permCodeId;
    }

    /**
     * 权限id
     *
     * @return
     */
    public Long getPermId() {
        return permId;
    }

    /**
     * 权限id
     *
     * @param permId
     */
    public void setPermId(Long permId) {
        this.permId = permId;
    }
}
