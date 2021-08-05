//package com.wee0.box.data;
//
//import com.wee0.demo.JavaSE.commons.data.IDataRequest;
//import com.wee0.demo.JavaSE.commons.data.IDataRequestModel;
//import com.wee0.demo.JavaSE.commons.data.IDataResponse;
//import com.wee0.demo.JavaSE.commons.data.IDataStore;
//import com.wee0.demo.JavaSE.commons.utils.LogUtil;
//import org.apache.logging.log4j.Logger;
//
//import java.util.Set;
//
///**
// * @author <a href="78026399@qq.com">白华伟</a>
// * @CreateDate 2021/5/27 7:25
// * @Description 一个简单的数据存储对象实现
// * <pre>
// * 补充说明
// * </pre>
// **/
//public class SimpleDataStore implements IDataStore {
//
//    private static final Logger log = LogUtil.getLogger(SimpleDataStore.class);
//
//    @Override
//    public void init() {
//        boolean _ddl = true;
//        if (!_ddl) return;
//
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//
//    @Override
//    public IDataResponse apply(IDataRequest request) {
//        log.debug("request: {}", request);
//        Set<IDataRequestModel> _models = request.getModels();
//        for (IDataRequestModel _model : _models) {
//            log.debug("model: {}", _model);
//        }
//        IDataResponse _res = new SimpleDataResponse();
//        return _res;
//    }
//
//}
