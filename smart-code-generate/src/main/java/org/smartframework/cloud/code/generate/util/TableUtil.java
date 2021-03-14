package org.smartframework.cloud.code.generate.util;

import lombok.experimental.UtilityClass;

/**
 * table工具类
 *
 * @author liyulin
 * @date 2019-07-15
 */
@UtilityClass
public class TableUtil {

    /**
     * 表前缀
     */
    private static final String[] TABLE_PREFIXS = {"t_"};
    /**
     * 分表后缀
     */
    private static final String[] TABLE_SHARDING_SUFFIXS = {"_0", "_00"};
    /**
     * 字段前缀
     */
    private static final String[] COLUMN_PREFIXS = {"f_", "t_"};
    /**
     * 表、字段名称分隔符
     */
    private static final char SEPARATOR = '_';

    /**
     * 获取逻辑表名
     *
     * @param tableName
     * @return
     */
    public static String getTableName(String tableName) {
        for (String suffix : TABLE_SHARDING_SUFFIXS) {
            if (tableName.endsWith(suffix)) {
                tableName = tableName.substring(0, tableName.length() - suffix.length());
            }
        }
        return tableName;
    }

    /**
     * 根据表名获取Entity类名
     *
     * @param tableName 表名
     * @return
     */
    public static String getEntityClassName(String tableName) {
        return getJavaName(tableName, TABLE_PREFIXS, TABLE_SHARDING_SUFFIXS, true);
    }

    /**
     * 根据表字段名获取对应java对象属性名
     *
     * @param column 表字段名
     * @return
     */
    public static String getAttibuteName(String column) {
        return getJavaName(column, COLUMN_PREFIXS, null, false);
    }

    /**
     * 下划线转驼峰
     *
     * @param name                 数据库表名
     * @param prefixs              前缀
     * @param tableShardingSuffixs 分表后缀
     * @param isTable
     * @return
     */
    private static String getJavaName(String name, String[] prefixs, String[] tableShardingSuffixs, boolean isTable) {
        for (String prefix : prefixs) {
            if (name.startsWith(prefix)) {
                name = name.substring(prefix.length());
                if (isTable) {
                    // 首字母变大小
                    String tableNameStart = String.valueOf(name.charAt(0)).toUpperCase();
                    name = (name.length() == 1) ? tableNameStart : (tableNameStart + name.substring(1, name.length()));
                }
            }
        }

        if (isTable && tableShardingSuffixs != null && tableShardingSuffixs.length > 0) {
            for (String suffix : tableShardingSuffixs) {
                if (name.endsWith(suffix)) {
                    name = name.substring(0, name.length() - suffix.length());
                }
            }
        }

        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == SEPARATOR) {
                String tableNameStart = name.substring(0, i);
                char tableNameMiddle = name.charAt(i + 1);
                String tableNameEnd = name.substring(i + 2, name.length());
                name = tableNameStart + String.valueOf(tableNameMiddle).toUpperCase() + tableNameEnd;
            }
        }
        return name;
    }

}