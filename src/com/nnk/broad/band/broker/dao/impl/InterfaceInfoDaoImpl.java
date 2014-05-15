package com.nnk.broad.band.broker.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nnk.broad.band.broker.common.DBServerUtil;
import com.nnk.broad.band.broker.dao.InterfaceInfoDao;
import com.nnk.broad.band.broker.entity.AgentInfo;
import com.nnk.broad.band.broker.vo.ProtocolInfo;
import com.nnk.dbsrv.client.heleper.DBSrvTemplate;

public class InterfaceInfoDaoImpl implements InterfaceInfoDao {

	private   Logger logger = Logger.getLogger(InterfaceInfoDaoImpl.class);

@Override
	public List<AgentInfo> getValidAgents(ProtocolInfo protocolInfo) {
	
	StringBuffer sql = new StringBuffer("SELECT id,agentId,level,weight,appname,operators,type,province,city,comment,status FROM interface_info WHERE status=1 and (province=");
	sql.append("'");
	sql.append(protocolInfo.getProvince());
	sql.append("' or province='*')");
	sql.append(" and (city='");
	sql.append(protocolInfo.getCity());
	sql.append("' or city='*')");
	sql.append(" and (type='");
	sql.append(protocolInfo.getBroadBandType());
	sql.append("' or type='*')");
	sql.append(" and (operators='");
	sql.append(protocolInfo.getOperator());
	sql.append("' or operators='*')");
	sql.append("and weight >=1 ORDER BY LEVEL DESC ,WEIGHT DESC , createTime DESC;");
	
	logger.info("getValidAgents SQL:" + sql);
	
	List<AgentInfo> interfaceOrders = new ArrayList<AgentInfo>();
	try {
		Connection connection = DBServerUtil.getConnection();
		DBSrvTemplate template = new DBSrvTemplate(connection);
		interfaceOrders = template.list(sql.toString(), AgentInfo.class);
	} catch (SQLException e) {
		logger.error(e.getMessage(), e);
	}
	return interfaceOrders;
}
	
	
	
//	@Override
//	public List<InterfaceInfoVo> list(String sql) {
//		if (logger.isDebugEnabled()) {
//			logger.debug("SQL:" + sql);
//		}
//		List<InterfaceInfoVo> vos = new ArrayList<InterfaceInfoVo>();
//		try {
//			Connection connection = DBServerUtil.getConnection();
//			DBSrvTemplate template = new DBSrvTemplate(connection);
//			vos = template.list(sql, InterfaceInfoVo.class);
//		} catch (SQLException e) {
//			logger.error(e.getMessage(),e);
//		}
//		return vos;
//	}

}
