package com.nnk.broad.band.broker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nnk.broad.band.broker.config.ApplicationConfig;
import com.nnk.broad.band.broker.core.InterfacesCache;
import com.nnk.broad.band.broker.service.BroadBandService;
import com.nnk.broad.band.broker.service.impl.BroadBandServiceImpl;
import com.nnk.msgsrv.client.core.MsgSrvManager;

public class Main {

	private static final Logger LOGGER = Logger.getLogger(Main.class);

	public static void main(String[] args) {
//		try {
//			MsgSrvManager.start("config/msgsrv.xml");
//			boolean started = MsgSrvManager.waitingStarted();
//			if (!started) {
//				throw new RuntimeException("启动消息服务失败");
//			}
//			LOGGER.info("[启动消息服务][成功]");
//
//			BroadBandService service = new BroadBandServiceImpl();
//			service.load();
//			LOGGER.info("[加载撮合规则][成功]");
//
//			ApplicationConfig.getInstance();
//			LOGGER.info("[加载app信息][成功]");
//		} catch (Exception e) {
//			LOGGER.error("[启动订单撮合程序][失败]，程序自动退出！", e);
//			System.exit(-1);
//		}
		List<String> list = InterfacesCache.list;
		list.add("aaa");
		System.out.println(InterfacesCache.list.get(0));
		System.out.println("a b c d ".trim());
		Map<String, String> map = new HashMap<String, String>();
		map.put("aa", null);
		System.out.println(map.containsKey("aa"));
		System.out.println(map.get("aa")==null);
	}

}
