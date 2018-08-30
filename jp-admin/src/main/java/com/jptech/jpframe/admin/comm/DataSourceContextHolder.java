package com.jptech.jpframe.admin.comm;

public class DataSourceContextHolder {

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static String getDataSourceId() {
        return CONTEXT.get();
    }

    public static void set(String dataSourceId) {
        CONTEXT.set(dataSourceId);
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
