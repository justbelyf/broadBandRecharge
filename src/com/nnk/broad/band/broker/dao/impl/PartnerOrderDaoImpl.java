package com.nnk.broad.band.broker.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.nnk.broad.band.broker.common.DBServerUtil;
import com.nnk.broad.band.broker.common.GlobalConstants;
import com.nnk.broad.band.broker.dao.PartnerOrderDao;

public class PartnerOrderDaoImpl implements PartnerOrderDao {

	private  Logger logger = Logger.getLogger(PartnerOrderDaoImpl.class);

	@Override
	public int update(String systemOrderNum) {
		String sql = "UPDATE partner_order SET brokerState = '" + GlobalConstants.TRADING_FAILED + "',lastBrokerTime=NOW() WHERE buyOrder = '" + systemOrderNum + "';";
		logger.info(sql);
		int rowCount=0;
		try {
			Connection connection = DBServerUtil.getConnection();
			Statement statement = connection.createStatement();
			rowCount = statement.executeUpdate(sql);
			return rowCount;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return rowCount;
	}
	
	
public static void main(String[] args) {
	new PartnerOrderDaoImpl().update("");
}
}
