package com.spring.sql;

import com.spring.ioc.helper.ConfigHelper;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MyDatabaseUtils {
    private static DataSource dataSource;
    private static Connection conn;
    /**
     * 数据类型与 ResultSet 方法名映射
     */
    static Map<Class, String> typeMethodMappings = new HashMap<>();
    static {
        try {
            Class.forName(ConfigHelper.getJdbcDriver()); //执行驱动

            conn = DriverManager.getConnection(ConfigHelper.getJdbcUrl(), ConfigHelper.getJdbcUsername(), ConfigHelper.getJdbcPassword()); //获取连接
        } catch (Exception e) {
            e.printStackTrace();
        }
        typeMethodMappings.put(Long.class, "getLong");
        typeMethodMappings.put(String.class, "getString");
        typeMethodMappings.put(Date.class, "getDate");
    }



    /**
     * 获取数据库连接
     */
    public static Connection getConnection() {
        if(conn == null) {
            try {
                Class.forName(ConfigHelper.getJdbcDriver()); //执行驱动

                conn = DriverManager.getConnection(ConfigHelper.getJdbcUrl(), ConfigHelper.getJdbcUsername(), ConfigHelper.getJdbcPassword()); //获取连接
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    /***
     * 新增  修改 删除 的方法
     * @param sql
     * @param param
     */
    public static void updateSql(String sql, LinkedHashMap<String,Object> param){
        conn = MyDatabaseUtils.getConnection();
        PreparedStatement pstmt = null;	//使用预编译语句
        try {
            pstmt = conn.prepareStatement(sql);
            if( param != null && param.size() > 0) {
                int s = 1;
                for (String fieldName : param.keySet()) {
                    Object val =  param.get(fieldName);
                    if(val.getClass().getName().equals("java.util.Date") ) {
                        if(val != null) {
                            pstmt.setObject(s++,val);
                        }

                    }else if(val.getClass().getName().equals("java.lang.String") ) {
                        pstmt.setString(s++,(String)val);
                    }else  {
                        pstmt.setObject(s++, val);
                    }
                }
            }
            pstmt.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(conn != null) {
                    conn.close();
                    conn = null;
                }
                if(pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public static <T>  T  queryOne(Class<T> entityClass, String sql, LinkedHashMap<String,Object> param) {
        conn = MyDatabaseUtils.getConnection();
        T t=null;
        PreparedStatement pstmt = null;	//使用预编译语句
        ResultSet rs = null;	//获取的结果集
        try {
            t=  entityClass.newInstance();
            pstmt = conn.prepareStatement(sql);
            if( param != null && param.size() > 0) {
                int s = 1;
                for (String fieldName : param.keySet()) {
                    Object val =  param.get(fieldName);
                    if(val.getClass().getName().equals("java.util.Date") ) {
                        if(val != null) {
                            pstmt.setObject(s++,val);
                        }

                    }else if(val.getClass().getName().equals("java.lang.String") ) {
                        pstmt.setString(s++,(String)val);
                    }else  {
                        pstmt.setObject(s++, val);
                    }
                }
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ResultSetMetaData metaData = rs.getMetaData();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    System.out.println("列名称：" + metaData.getColumnLabel(i) + ", 类型：" + metaData.getColumnClassName(i));

                }
                BeanInfo userBeanInfo = Introspector.getBeanInfo(entityClass, Object.class);
                for (PropertyDescriptor propertyDescriptor : userBeanInfo.getPropertyDescriptors()) {
                    String column = propertyDescriptor.getName();//列名称
                    Class columnType = propertyDescriptor.getPropertyType();//列类型
                    System.out.println("columnType="+columnType);
                    String methodName =typeMethodMappings.get(columnType);
                    Method resultSetMethod  ;
                    if(methodName.equals("getInteger")) {
                        resultSetMethod = ResultSet.class.getMethod(methodName, Integer.class);
                    }else {
                        resultSetMethod = ResultSet.class.getMethod(methodName, String.class);
                    }
                    // 通过放射调用 getXXX(String) 方法
                    Object resultValue = resultSetMethod.invoke(rs, column);
                    Method setterMethodFromUser = propertyDescriptor.getWriteMethod();
                    // 以 id 为例，  user.setId(resultSet.getLong("id"));
                    setterMethodFromUser.invoke(t, resultValue);

                }

            }
            System.out.println(t);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(conn != null) {
                    conn.close();
                    conn = null;
                }
                if(pstmt != null) {
                    pstmt.close();
                }
                if(rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return  t;
    }

}
