package com.nnk.broad.band.broker.service;

import java.util.List;

import org.junit.Test;

import com.nnk.broad.band.broker.entity.InterfaceInfoVo;
import com.nnk.broad.band.broker.entity.InterfaceSupportVo;
import com.nnk.broad.band.broker.service.impl.BroadBandServiceImpl;
import com.nnk.msgsrv.client.core.MsgSrvManager;

public class InterfaceServiceTest {

	public BroadBandService service = new BroadBandServiceImpl();
	
	@Test
	public void loadTest() throws Exception{
		MsgSrvManager.start("config/msgsrv.xml");
		boolean started = MsgSrvManager.waitingStarted();
		if (!started) {
			throw new RuntimeException("启动消息服务失败");
		}
		List<InterfaceInfoVo> vos = service.load();
		for (InterfaceInfoVo interfaceInfoVo : vos) {
			System.out.println(interfaceInfoVo);
			List<InterfaceSupportVo> supports = interfaceInfoVo.getSupports();
			for (InterfaceSupportVo isv : supports) {
				System.out.println(isv);
			}
			System.out.println("------------------------------------------");
		}
	}
}
