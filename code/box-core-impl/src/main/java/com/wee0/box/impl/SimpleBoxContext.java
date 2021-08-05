package com.wee0.box.impl;

import com.wee0.box.BoxConfig;
import com.wee0.box.IBoxConfig;
import com.wee0.box.IBoxContext;
import com.wee0.box.beans.IDestroyable;
import com.wee0.box.code.BizCodeManager;
import com.wee0.box.code.IBizCodeManager;
import com.wee0.box.code.impl.BizCodeDef;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.plugin.PluginManager;
import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.util.shortcut.StringUtils;

import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/6/29 10:39
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleBoxContext implements IBoxContext, IDestroyable {

//    static {
//        // 当需要手动加载配置数据时，配置此属性阻止框架自动加载。
//        System.setProperty(IBoxConfig.KEY_MANUAL_LOAD_CONFIG_DATA, "true");
//    }

    // 日志对象
    private static ILogger log;

    private Map<String, Object> _idBeans = new ConcurrentHashMap<>(32);
    private Map<Class, Object> _clsBeans = new ConcurrentHashMap<>(32);

    @Override
    public boolean hasBean(String id) {
        return this._idBeans.containsKey(id);
    }

    @Override
    public <T> T getBean(Class<T> type) {
        return (T) this._clsBeans.get(type);
    }

    @Override
    public Object getBean(String id) {
        return this._idBeans.get(id);
    }

    @Override
    public <T> T getBean(String id, Class<T> type) {
//        throw new UnsupportedOperationException("This method is not yet implemented.");
        Object _bean = this.getBean(id);
        if (null == _bean)
            return this.getBean(type);
        if (type.isAssignableFrom(_bean.getClass()))
            return (T) _bean;
        throw new IllegalStateException(id + " bean is not " + type);
    }

    @Override
    public void addBean(String id, Object bean) {
        CheckUtils.checkNotNull(bean, "bean cannot be null!");
        if (this._idBeans.containsKey(id))
            throw new IllegalStateException("already bean id: ".concat(id));
        this._idBeans.put(id, bean);
        log.debug("add bean by id: {}", id);
    }

    @Override
    public void init() {
//        // 在框架配置数据加载之前手动注入前置配置数据。
//        Map<String, String> _initData = new HashMap<>(16);
//        // 加载框架配置数据
//        BoxConfig.impl().loadData(_initData);
        IBoxConfig _config = BoxConfig.impl();

        log = LoggerFactory.getLogger(SimpleBoxContext.class);

        log.info("user.timezone:{}.", System.getProperty("user.timezone"));
        log.info("externalDir:{}.", BoxConfig.impl().getExternalDir());
        log.info("resourceDir:{}.", BoxConfig.impl().getResourceDir());
//        log.info("user.dir:{}.", System.getProperty("user.dir"));
//        log.info("/resource:{}.", BoxConfig.class.getResource("/"));
//        log.info(".dir:{}.", new File(".").getAbsoluteFile().getParent());
        log.trace("before init...");

        // TODO: 扫描注册Bean。

        if (StringUtils.parseBoolean(_config.get(BoxConfigKeys.bizCodeEnable), false)) {
            // 初始化业务编码管理器
            IBizCodeManager _bizCodeManager = BizCodeManager.impl();
            log.debug("_bizCodeManager: {}", _bizCodeManager);
            _bizCodeManager.addBizCodeEnum(BizCodeDef.class);
            if (_bizCodeManager instanceof IDestroyable) {
                ((IDestroyable) _bizCodeManager).init();
            }
            log.info("enabled bizCode module.");
        }

        if (StringUtils.parseBoolean(_config.get(BoxConfigKeys.pluginEnable), false)) {
            // 启动插件管理器
            PluginManager.impl().start();
            log.info("enabled plugin module.");
        }

        log.trace("after init...");
    }

    @Override
    public void destroy() {
//        // 停止插件管理器
//        PluginManager.impl().stop();
        // 清理资源
        Collection _allImpl = BoxConfig.impl().getAllInterfaceImpl();
        for (Object _impl : _allImpl) {
            if (_impl instanceof IDestroyable && !(_impl instanceof SimpleBoxContext)) {
                ((IDestroyable) _impl).destroy();
            }
        }
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleBoxContext() {
        if (null != SimpleBoxContextHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleBoxContextHolder {
        private static final SimpleBoxContext _INSTANCE = new SimpleBoxContext();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleBoxContextHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleBoxContext me() {
        return SimpleBoxContextHolder._INSTANCE;
    }

}
