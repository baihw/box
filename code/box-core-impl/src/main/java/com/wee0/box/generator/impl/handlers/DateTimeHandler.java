package com.wee0.box.generator.impl.handlers;

import com.wee0.box.generator.IGenerateHandler;
import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.util.shortcut.DateUtils;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/7/10 7:02
 * @Description 时间日期生成
 * <pre>
 * 补充说明
 * </pre>
 **/
public class DateTimeHandler implements IGenerateHandler {
    @Override
    public String getName() {
        return "DateTime";
    }

    @Override
    public Object generate(Object... args) {
        Object _result = DateUtils.getCurrentDateTime();
        if (null != args && 0 != args.length) {
            String _arg1 = String.valueOf(args[0]);
            switch (_arg1) {
                case "now":
                    break;
                default:
                    CheckUtils.checkArgument(true, "%s unknown parameter '%s'", getName(), _arg1);
                    break;
            }
        }
        return _result;
    }

}
