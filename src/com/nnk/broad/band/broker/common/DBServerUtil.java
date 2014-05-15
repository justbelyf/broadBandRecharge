package com.nnk.broad.band.broker.common;

import java.sql.Connection;

import com.nnk.dbsrv.client.core.sql.DBManage;

public class DBServerUtil {

	private static Connection connection = null;

	public static synchronized final Connection getConnection() {
		if (connection == null) {
			DBManage dbManage = new DBManage();
			connection = dbManage.getConnection("BroadBandBrokerDB");
		}
		return connection;
	}
}
