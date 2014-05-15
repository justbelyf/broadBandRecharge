package com.nnk.broad.band.broker.dao;

import java.util.List;

import org.junit.Test;

import com.nnk.broad.band.broker.dao.impl.InterfaceOrderDaoImpl;
import com.nnk.broad.band.broker.dao.impl.PartnerOrderDaoImpl;
import com.nnk.broad.band.broker.entity.InterfaceOrder;
import com.nnk.msgsrv.client.core.MsgSrvManager;

public class InterfaceOrderDaoTest {

	InterfaceOrderDao interfaceOrderDao = new InterfaceOrderDaoImpl();
	
	@Test
	public void testList() throws Exception {
		MsgSrvManager.start("config/msgsrv.xml");
		boolean started = MsgSrvManager.waitingStarted();
		if (!started) {
			throw new RuntimeException("启动消息服务失败");
		}
		
		String sql = "SELECT id,buyOrder,sendOrder,merid,oemOrder,account,chgAmount,reqTime,accState,accTime,hdlState,hdlTime,failedCause FROM interface_order";
		new PartnerOrderDaoImpl().update(sql);
//		List<InterfaceOrderVo> vos = interfaceOrderDao.list(sql);
//		for (InterfaceOrderVo iov : vos) {
//			System.out.println(iov);
//		}
	}
	
}
