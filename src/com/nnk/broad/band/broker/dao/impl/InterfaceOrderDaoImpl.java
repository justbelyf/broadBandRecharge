package com.nnk.broad.band.broker.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nnk.broad.band.broker.common.DBServerUtil;
import com.nnk.broad.band.broker.common.GlobalConstants;
import com.nnk.broad.band.broker.dao.InterfaceOrderDao;
import com.nnk.broad.band.broker.entity.InterfaceOrder;
import com.nnk.broad.band.broker.vo.ProtocolInfo;
import com.nnk.dbsrv.client.heleper.DBSrvTemplate;

public class InterfaceOrderDaoImpl implements InterfaceOrderDao {

	private static final Logger LOGGER = Logger.getLogger(InterfaceOrderDaoImpl.class);

	@Override
	public List<InterfaceOrder> getFailAgents(String systemOrderNum) {
		String sql = "SELECT merid FROM interface_order WHERE buyOrder='" + systemOrderNum + "' AND hdlState='" + GlobalConstants.TRADING_FAILED + "';";
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("SQL:" + sql);
		}
		List<InterfaceOrder> interfaceOrders = new ArrayList<InterfaceOrder>();
		try {
			Connection connection = DBServerUtil.getConnection();
			DBSrvTemplate template = new DBSrvTemplate(connection);
			interfaceOrders = template.list(sql, InterfaceOrder.class);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return interfaceOrders;
	}

	@Override
	public boolean addInterfaceOrder(ProtocolInfo protocolInfo , String agentId) {
		String sql = "INSERT INTO interface_order (buyOrder,sendOrder,merid,account,chgAmount,reqTime) VALUES('%s','%s','%s','%s',%s,'%s');";
		sql = String.format(sql, protocolInfo.getSystemOrderNum(), protocolInfo.getSystemOrderNum(), agentId, protocolInfo.getRechargeAccount(), protocolInfo.getRechargeAmount());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("SQL:" + sql);
		}
		boolean result = false;
		Connection connection = DBServerUtil.getConnection();
		try {
			Statement statement = connection.createStatement();
			result = statement.execute(sql);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			result = false;
		}
		return result;
	}

//	public List<AgentInfo> getValidAgents(String sql) {
//		if (LOGGER.isDebugEnabled()) {
//			LOGGER.debug("SQL:" + sql);
//		}
//		List<AgentInfo> interfaceOrders = new ArrayList<AgentInfo>();
//		try {
//			Connection connection = DBServerUtil.getConnection();
//			DBSrvTemplate template = new DBSrvTemplate(connection);
//			interfaceOrders = template.list(sql, AgentInfo.class);
//		} catch (SQLException e) {
//			LOGGER.error(e.getMessage(), e);
//		}
//		return interfaceOrders;
//	}
}
