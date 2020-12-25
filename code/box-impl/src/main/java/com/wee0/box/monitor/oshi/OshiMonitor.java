/*
 * Copyright (c) 2019-present, wee0.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wee0.box.monitor.oshi;

import com.wee0.box.monitor.IMonitor;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import java.io.ObjectStreamException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/6/3 9:02
 * @Description 基于oshi组件实现的运行环境监控对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public class OshiMonitor implements IMonitor {

    // 操作系统信息管理对象
    private final OperatingSystem os;
    // 硬件信息管理对象
    private final HardwareAbstractionLayer hardware;


    public Map<String, Object> overview() {
        Map<String, Object> _info = new LinkedHashMap<>(128, 1.0F);
        _info.put("os.processId", os.getProcessId());
        _info.put("os.manufacturer", os.getManufacturer());
        _info.put("os.family", os.getFamily());
        _info.put("os.versionInfo", os.getVersionInfo());
        _info.put("os.bit", os.getBitness());
        _info.put("os.bootTime", Instant.ofEpochSecond(os.getSystemBootTime()).atZone(ZoneId.systemDefault()));
        _info.put("os.upTime", os.getSystemUptime());
//        _info.put("os.maxFileDescriptors", os.getFileSystem().getMaxFileDescriptors());
//        _info.put("os.openFileDescriptors", os.getFileSystem().getOpenFileDescriptors());
//        _info.put("os.TCPv4Stats", os.getInternetProtocolStats().getTCPv4Stats());
//        _info.put("os.TCPv6Stats", os.getInternetProtocolStats().getTCPv6Stats());
//        _info.put("os.sessions", os.getSessions());
//        _info.put("os.processCount", os.getProcessCount());
//        _info.put("os.threadCount", os.getThreadCount());
        _info.put("hw.computer", hardware.getComputerSystem());
        _info.put("hw.firmware", hardware.getComputerSystem().getFirmware());
        _info.put("hw.processor", hardware.getProcessor());
        _info.put("hw.memory", hardware.getMemory());
        _info.put("hw.diskStores", hardware.getDiskStores());
        _info.put("hw.graphicsCards", hardware.getGraphicsCards());

//        _info.put("hw.sensors", hardware.getSensors());
        return _info;
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private OshiMonitor() {
        if (null != OshiMonitorHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
        SystemInfo _info = new SystemInfo();
        this.os = _info.getOperatingSystem();
        this.hardware = _info.getHardware();
    }

    // 当前对象唯一实例持有者。
    private static final class OshiMonitorHolder {
        private static final OshiMonitor _INSTANCE = new OshiMonitor();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return OshiMonitorHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static OshiMonitor me() {
        return OshiMonitorHolder._INSTANCE;
    }
}
