package com.wee0.box.data.empire;

import com.wee0.box.BoxConfig;
import com.wee0.box.BoxContext;
import com.wee0.box.IBoxConfig;
import com.wee0.box.IBoxContext;
import com.wee0.box.beans.IDestroyable;
import com.wee0.box.impl.BoxConfigKeys;

import java.util.HashMap;
import java.util.Map;

public class BoxHelper {

//    static {
//        // 当需要手动加载配置数据时，配置此属性阻止框架自动加载。
//        System.setProperty(IBoxConfig.KEY_MANUAL_LOAD_CONFIG_DATA, "true");
//    }

    static void initBoxCustom() {
        // 当需要手动加载配置数据时，配置此属性阻止框架自动加载。
        System.setProperty(IBoxConfig.KEY_MANUAL_LOAD_CONFIG_DATA, "true");

        // 在框架配置数据加载之前手动注入前置配置数据。
        Map<String, String> _initData = new HashMap<>(16);
        _initData.put(BoxConfigKeys.pluginEnable, "False");
        // 加载框架配置数据
        BoxConfig.impl().loadData(_initData);

        IBoxContext _ctx = BoxContext.impl();
        if (_ctx instanceof IDestroyable)
            ((IDestroyable) _ctx).init();
    }

    static void initBoxSimple() {
        IBoxContext _ctx = BoxContext.impl();
        if (_ctx instanceof IDestroyable)
            ((IDestroyable) _ctx).init();
    }
}
