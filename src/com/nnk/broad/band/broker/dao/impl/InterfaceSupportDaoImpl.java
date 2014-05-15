package com.nnk.broad.band.broker.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nnk.broad.band.broker.common.DBServerUtil;
import com.nnk.broad.band.broker.dao.InterfaceSupportDao;
import com.nnk.broad.band.broker.entity.InterfaceSupportVo;
import com.nnk.dbsrv.client.heleper.DBSrvTemplate;

public class InterfaceSupportDaoImpl implements InterfaceSupportDao {

	private static final Logger LOGGER = Logger.getLogger(InterfaceSupportDaoImpl.class);

	@Override
	public List<InterfaceSupportVo> list(String sql) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("SQL:" + sql);
		}
		List<InterfaceSupportVo> vos = new ArrayList<InterfaceSupportVo>();
		try {
			Connection connection = DBServerUtil.getConnection();
			DBSrvTemplate template = new DBSrvTemplate(connection);
			vos = template.list(sql, InterfaceSupportVo.class);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return vos;
	}

}
