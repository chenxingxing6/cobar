package com.alibaba.cobar.jdbc;

import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.NonRegisteringDriver;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * Cobar驱动
 *
 * 在使用集群时提供负载均衡的功能，其他情况和MySQLDriver一样。
 *  Class.forName("com.alibaba.cobar.jdbc.Driver");
 *  String url = "jdbc:cobar://host:port/dbname?user=xxx&password=xxx";
 */
public class Driver extends NonRegisteringDriver implements java.sql.Driver {
    public static final String VERSION = "1.0.0";
    static {
        try {
            java.sql.DriverManager.registerDriver(new Driver());
        } catch (SQLException E) {
            throw new RuntimeException("Can't register driver!");
        }
    }

    public Driver() throws SQLException {
        // Required for Class.forName().newInstance()
    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        return super.connect(UrlProvider.getUrl(url, info), info);
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return super.acceptsURL(UrlProvider.getMySQLUrl(url));
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return super.getPropertyInfo(UrlProvider.getMySQLUrl(url), info);
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("Not supported yet.");
    }

}
