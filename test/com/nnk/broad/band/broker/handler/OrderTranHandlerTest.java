package com.nnk.broad.band.broker.handler;

import java.util.Date;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.nnk.broad.band.broker.common.DateUtil;
import com.nnk.msgsrv.client.core.MsgSrvManager;
import com.nnk.msgsrv.client.core.MsgSrvService;

public class OrderTranHandlerTest {

	MsgSrvService service = null;

	@Before
	public void testBefore() throws Exception {
		MsgSrvManager.start("config/test.xml");
		boolean started = MsgSrvManager.waitingStarted();
		if (!started) {
			throw new RuntimeException("启动消息服务失败");
		}

		service = MsgSrvManager.getService("test");
	}

	@Test
	public void testOrderTran() throws Exception {
		Assert.assertNotNull(service);
		String appname = "BroadBandBroker";
		String command = "OrderTran";
		String sessionId = UUID.randomUUID().toString();
		String MerID = "9999999999";
		String OEMOrder = "OEM" + DateUtil.format(new Date(), "yyyyMMddHHmmssSSSSSS");
		String _007kaOrder = "20" + System.currentTimeMillis();
		String Carrier = "1000";
		String Type = "0001";
		String Province = "440000";
		String City = "0755";
		String ChargeNo = "testNo888888";
		String Value = "50000";
		String AccTime = DateUtil.format(new Date(), "yyyyMMddHHmmss");
		String Url = "www.baidu.com";
		String Attach = "充值";
		String State = "1000";

		StringBuilder message = new StringBuilder();
		message.append(appname).append(" ").append(command).append(" ").append(sessionId).append(" ").append(MerID).append(" ").append(OEMOrder).append(" ")
				.append(_007kaOrder).append(" ").append(Carrier).append(" ").append(Type).append(" ").append(Province).append(" ").append(City).append(" ")
				.append(ChargeNo).append(" ").append(Value).append(" ").append(AccTime).append(" ").append(Url).append(" ").append(Attach).append(" ")
				.append(State).append(" ");

		service.send(message.toString());

	}
}
