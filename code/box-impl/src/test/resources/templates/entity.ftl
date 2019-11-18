package ${entityPackage};

import com.wee0.box.sql.annotation.BoxColumn;
import com.wee0.box.sql.annotation.BoxTable;

import ${entityBase!'com.wee0.box.sql.entity.BaseEntity'};

/**
 * @author ${author!}
 * @CreateDate ${createDate!}
 * @Description ${tableComment}
 * <pre>
 * 补充说明
 * </pre>
 **/
@BoxTable(name = "${tableName}")
public class ${entityName} extends BaseEntity{
<#list columns as column>

    /**
    * ${column.comment}
    */
    @BoxColumn(name = "${column.name}")
    private ${column.typeJava} ${column.nameJavaField};
    public ${column.typeJava} get${column.nameJava}() {
    	return this.${column.nameJavaField};
    }
    public void set${column.nameJava}(${column.typeJava} ${column.nameJavaField}) {
    	this.${column.nameJavaField} = ${column.nameJavaField};
    }
</#list>

}
