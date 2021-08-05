package com.wee0.box.data;

import java.util.Set;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/27 7:25
 * @Description 数据请求对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IDataRequest {

    /**
     * 增加一个请求的模型信息
     *
     * @param model 模型信息
     * @return 链式调用对象
     */
    IDataRequest addModel(IDataRequestModel model);

    /**
     * 获取请求的模型信息集合
     *
     * @return 请求的模型信息集合
     */
    Set<IDataRequestModel> getModels();
}
