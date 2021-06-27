package com.wee0.box.model.vo;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/23 7:20
 * @Description 权限白名单
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SysPermWhitelist implements java.io.Serializable {
    /**
     * 权限资源的url
     */
    private String permUrl;
    /**
     * 权限资源所属模块名字(通常是Controller的名字)
     */
    private String moduleName;
    /**
     * 权限的名称
     */
    private String permName;

    public SysPermWhitelist() {
    }

    /**
     * 权限资源的url
     *
     * @return
     */
    public String getPermUrl() {
        return permUrl;
    }

    /**
     * 权限资源的url
     *
     * @param permUrl
     */
    public void setPermUrl(String permUrl) {
        this.permUrl = permUrl;
    }

    /**
     * 权限资源所属模块名字(通常是Controller的名字)
     *
     * @return
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * 权限资源所属模块名字(通常是Controller的名字)
     *
     * @param moduleName
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * 权限的名称
     *
     * @return
     */
    public String getPermName() {
        return permName;
    }

    /**
     * 权限的名称
     *
     * @param permName
     */
    public void setPermName(String permName) {
        this.permName = permName;
    }
}
