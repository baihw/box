package com.wee0.box.data;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/6/12 7:02
 * @Description 数据模型字段
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IDataField {

    /**
     * 字段类型
     */
    enum Type {

        /**
         * 布尔值(Bool)
         */
        Bool,

        /**
         * 字符串
         * (String)varchar
         */
        Str,

        /**
         * 固定长度的字符串
         * (Character)char
         */
        Char,

        /**
         * 整数，支持的长度为1，2，4，8。
         * <p>
         * (Byte)tinyint存储大小为 1 字节。 从 0 到 255 的整型数据。
         * (Short)smallint存储大小为 2 个字节。 从 -2^15 (-32,768) 到 2^15 – 1 (32,767) 的整型数据。
         * (Integer)int存储大小为 4 个字节。从 -2^31 (-2,147,483,648) 到 2^31 – 1 (2,147,483,647) 的整型数据（所有数字）。int 的 SQL-92 同义字为 integer。
         * (Long)bigint存储大小为 8 个字节。 从 -2^63 (-9223372036854775808) 到 2^63-1 (9223372036854775807) 的整型数据（所有数字）。bigint已经有长度了，在mysql建表中的length，只是用于显示的位数。
         */
        Int,

        /**
         * 浮点数(BigDecimal)
         */
        Decimal,

        /**
         * 日期时间(Date)，默认格式为：yyyy-mm-dd hh:mm:ss[.fffffffff]
         */
        Date,

    }

    /**
     * @return 字段名称
     */
    String getName();

    /**
     * @return 字段类型
     */
    Type getType();

    /**
     * @return 字段长度
     */
    double getLength();

    /**
     * @return 是否必须有值
     */
    boolean isRequired();

//    /**
//     * @return 字段默认值
//     */
//    Object getDefValue();

    /**
     * @return 字段值生成规则
     */
    String getGenerateRule();

    /**
     * @return 字段注释
     */
    String getComment();

    /**
     * @return 字段数据校验规则名称集合
     */
    String[] getDataRules();

}
