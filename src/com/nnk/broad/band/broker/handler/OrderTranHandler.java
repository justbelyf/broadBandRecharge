package com.nnk.broad.band.broker.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.nnk.broad.band.broker.common.DateUtil;
import com.nnk.broad.band.broker.common.GlobalConstants;
import com.nnk.broad.band.broker.config.ApplicationConfig;
import com.nnk.broad.band.broker.core.InterfacesCache;
import com.nnk.broad.band.broker.core.SupportDict;
import com.nnk.broad.band.broker.entity.ChannelInfo;
import com.nnk.broad.band.broker.entity.AgentInfo;
import com.nnk.broad.band.broker.entity.InterfaceInfoVo;
import com.nnk.broad.band.broker.entity.InterfaceOrder;
import com.nnk.broad.band.broker.entity.InterfaceSupportVo;
import com.nnk.broad.band.broker.service.BroadBandService;
import com.nnk.broad.band.broker.service.impl.BroadBandServiceImpl;
import com.nnk.broad.band.broker.vo.ProtocolInfo;
import com.nnk.msgsrv.client.core.MsgSrvManager;
import com.nnk.msgsrv.client.core.MsgSrvService;
import com.nnk.msgsrv.client.core.Session;

public class OrderTranHandler {

	private static final Logger LOGGER = Logger.getLogger(OrderTranHandler.class);

//	private static final BroadBandService service = new BroadBandServiceImpl();
	
	private ProtocolInfo protocolInfo;
	
//	private ChannelInfo channelInfo; 

	public void orderTran(Session session) {
		String fromAppName=null;
		String[] protocol = session.getContent().split(" +");
		protocolInfo = new ProtocolInfo(protocol);
		BroadBandService broadBandService = new BroadBandServiceImpl(protocolInfo);
		if(fromAppName.equals("callback")){
			broadBandService.rechargeChannelAgain();
		}else if(fromAppName.equals("accept")){
			broadBandService.rechargeChannelAgain();
			//
		}
		
		
		
		
		
		Map<String, List<AgentInfo>> rechargeChannel = InterfacesCache.rechargeChannel;
		Map<String, ChannelInfo> channelInfos = InterfacesCache.channelInfos;

		if (GlobalConstants.TRADING_SUCCESS.equals(protocolInfo.getOrderStatus())) {
			boolean isAllSend = true;
//			if(rechargeChannel.containsKey(compositionChannelKey(orderInfo))){
//				List<InterfaceInfo> interfaceInfos = rechargeChannel.get(compositionChannelKey(orderInfo));
//				if(null ==interfaceInfos || interfaceInfos.isEmpty()){
//					//撮合失败
//					return;
//				}
//				if(interfaceInfos.size()==1){
//					InterfaceInfo interfaceInfo = interfaceInfos.get(0);
//				}else{
//					for (InterfaceInfo interfaceInfo : interfaceInfos) {
//						if(orderInfo.getSystemOrderNum().equals(interfaceInfo.getLastProcessSystemOrderNum())){
//							continue;
//						}
//						if(interfaceInfo.getCount()>=interfaceInfo.getWeight()){
//							continue;
//						}
//						isAllSend = false;
//					}
//					
//				}
			if(channelInfos.containsKey(compositionChannelKey(protocolInfo))){
				ChannelInfo channelInfo = channelInfos.get(compositionChannelKey(protocolInfo));
				if(null ==channelInfo ){
					//不支持此业务，戳和失败
					matchFail();
				}else{
					List<InterfaceOrder> failedAgents = broadBandService.findFailedAgent(protocolInfo.getSystemOrderNum());
					if(!(null ==failedAgents || failedAgents.isEmpty())){
						for (InterfaceOrder interfaceOrder : failedAgents) {
							if(channelInfo.getInterfaceInfo().getAgentId().equals(interfaceOrder.getMerid())){
								
							}
						}
					}else{
						if(!channelInfo.isUniqueChannel()){
							if(channelInfo.isAchieveMaxWeight()){
							
							}
							
						}
						
					}
					
					if(channelInfo.isUniqueChannel()){
						
					}else{
						
					}
				}
				
			}else{
//				List<InterfaceInfo> interfaceInfos = broadBandService.getValidAgents(orderInfo);
//				rechargeChannel.put(compositionChannelKey(orderInfo), interfaceInfos);
//				if(null==interfaceInfos || interfaceInfos.isEmpty()){
//					channelInfos.put(compositionChannelKey(orderInfo), null);
//					//撮合失败
//					//update table partner_order
//					//do callback
//					return;
//				}
//				ChannelInfo channelInfo = new ChannelInfo();
//				if(interfaceInfos.size()==1){
//					channelInfo.setUniqueChannel(true);
//				}else{
//					channelInfo.setUniqueChannel(false);
//				}
//				channelInfo.setChannelsSize(interfaceInfos.size());
//				InterfaceInfo interfaceInfo = interfaceInfos.get(0);
//				if(!channelInfo.isUniqueChannel()){
//					if(interfaceInfo.getWeight()==1){
//						channelInfo.setCurrentChannelIndex(1);
//						channelInfo.setExecuteCount(0);
//						channelInfo.setInterfaceInfo(interfaceInfos.get(1));
//					}else{
//						channelInfo.setCurrentChannelIndex(0);
//						channelInfo.setExecuteCount(1);
//						channelInfo.setInterfaceInfo(interfaceInfo);
//						channelInfo.setLastSystemOrderNum(orderInfo.getSystemOrderNum());
//					}
//					
//				}
//			
//				channelInfos.put(compositionChannelKey(orderInfo), channelInfo);
				
				List<AgentInfo> interfaceInfos = InterfacesCache.rechargeChannel.get(compositionChannelKey(protocolInfo));
				if(null==interfaceInfos || interfaceInfos.isEmpty()){
					matchFail();
				}else{
					initChannelInfo();
					MsgSrvService msgSrvService = MsgSrvManager.getService("BroadBandBroker");
					msgSrvService.send("protocol");
					//发送
				}
			}
			broadBandService.brokerSuccess(protocolInfo.getSystemOrderNum(), protocolInfo.getSystemOrderNum(), protocolInfo.getAgentId(), protocolInfo.getRechargeAccount(), protocolInfo.getRechargeAmount());
		}	
	}		
	
	
	public String compositionChannelKey(ProtocolInfo orderInfo){
		String channelKey = orderInfo.getOperator()+ orderInfo.getBroadBandType()+orderInfo.getProvince()+orderInfo.getCity();
		return channelKey;
	}
	
	public void getNext(ChannelInfo channelInfo){
		for (int i = channelInfo.getCurrentChannelIndex(); i < channelInfo.getChannelsSize(); i++) {
			channelInfo.nexChannel(channelInfo, compositionChannelKey(protocolInfo));
			if(channelInfo.isAchieveMaxWeight()){
				return;
			}
		}
	}
	
	public void initChannel(){
		BroadBandService broadBandService = new BroadBandServiceImpl();
		List<AgentInfo> interfaceInfos = broadBandService.getValidAgents(protocolInfo);
		InterfacesCache.rechargeChannel.put(compositionChannelKey(protocolInfo), interfaceInfos);
	}
	
	public void initChannelInfo(){
		List<AgentInfo> interfaceInfos = InterfacesCache.rechargeChannel.get(compositionChannelKey(protocolInfo));
		if(null ==interfaceInfos || interfaceInfos.isEmpty()){
			InterfacesCache.channelInfos.put(compositionChannelKey(protocolInfo), null);
		}
		ChannelInfo channelInfo = new ChannelInfo();
		channelInfo.setUniqueChannel(interfaceInfos.size()==1);
		channelInfo.setChannelsSize(interfaceInfos.size());
		AgentInfo interfaceInfo = interfaceInfos.get(0);
		channelInfo.setCurrentChannelIndex(0);
		channelInfo.setExecuteCount(1);
		channelInfo.setInterfaceInfo(interfaceInfo);
		InterfacesCache.channelInfos.put(compositionChannelKey(protocolInfo), channelInfo);
	}
	
	public void matchFail(){
		// 如果程序撮合失败
		BroadBandService broadBandService = new BroadBandServiceImpl();
		broadBandService.brokerFailure(protocolInfo.getSystemOrderNum());
		// 发送给callback，告知处理失败
		String appsrv = ApplicationConfig.getInstance().getCallback().getAppsrv();
		String appname = ApplicationConfig.getInstance().getCallback().getAppname();
		String command = ApplicationConfig.getInstance().getCallback().getCommand();
		MsgSrvService callbackMsgSrv = MsgSrvManager.getService(appsrv);
		if (callbackMsgSrv == null) {
			LOGGER.warn("[config/msgsrv.xml]中不存在 name=" + appsrv + "的消息服务连接");
		} else {
			StringBuilder message = new StringBuilder();
			message.append(appname).append(" ").append(command).append(" ").append(protocolInfo.getSessionId());
			message.append(" ").append("撮合失败");
			callbackMsgSrv.send(message.toString());
		}
	}
	
	/**
	 * 		
	
			
			// 得到此订单已经失败的代理
			Set<String> failedAgents = broadBandService.findFailedAgent(orderInfo.getSystemOrderNum());

			List<InterfaceInfoVo> interfaces = new ArrayList<InterfaceInfoVo>(InterfacesCache.INTERFACES);

			Iterator<InterfaceInfoVo> iterator = interfaces.iterator();
			while (iterator.hasNext()) {
				InterfaceInfoVo iiv = iterator.next();
				// 判断订单是否已经在某些代理商处理失败
				if (failedAgents.contains(iiv.getMerid())) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("订单[" + contents[_007KA_ORDER] + "在代理[" + iiv.getMerid() + "]已失败，不对此代理进行撮合");
					}
					iterator.remove();
					continue;

				}
				// 判断是否支持的宽带运营商，类型，省份，城市充值
				if (!isSupportRechargeAgents(iiv, contents[CARRIER], contents[TYPE], contents[PROVINCE], contents[CITY])) {
					iterator.remove();
					continue;
				}
			}

			// 撮合成功
			if (!interfaces.isEmpty()) {

				// 多个接口的情况，采取取优先级最大的接口进行撮合
				interfaces = determineLevel(interfaces);
				// 存在多个多个级别相同的代理，则按权重轮询发送进行撮合
				InterfaceInfoVo _interface = findValidInterface(interfaces);
				_interface.setSend(true);
				_interface.setCounter(_interface.getCounter() + 1);
				service.brokerSuccess(contents[_007KA_ORDER], contents[_007KA_ORDER], _interface.getMerid(), contents[CHARGE_NO], contents[VALUE]);
				MsgSrvService service = MsgSrvManager.getService("BroadBandBroker");

				StringBuilder message = new StringBuilder();
				message.append(_interface.getAppname()).append(" ").append("OrderHandler").append(" ").append(contents[SESSION_ID]);
				message.append(" ").append(_interface.getMerid()).append(" ").append("NA").append(" ").append(contents[_007KA_ORDER]);
				message.append(" ").append(contents[CARRIER]).append(" ").append(contents[TYPE]).append(" ").append(contents[PROVINCE]);
				message.append(" ").append(contents[CITY]).append(" ").append(contents[CHARGE_NO]).append(" ").append(contents[VALUE]);
				message.append(" ").append(contents[ACC_TIME]).append(" ").append(contents[URL]).append(" ").append(contents[ATTACH]);
				message.append(" ").append(contents[STATE]).append(" ").append(DateUtil.format(new Date())).append(" ").append("NA");
				message.append(" ").append("NA");

				service.send(message.toString());
			} else {
				// 如果程序撮合失败
				service.brokerFailure(contents[_007KA_ORDER]);
				// 发送给callback，告知处理失败
				String appsrv = ApplicationConfig.getInstance().getCallback().getAppsrv();
				String appname = ApplicationConfig.getInstance().getCallback().getAppname();
				String command = ApplicationConfig.getInstance().getCallback().getCommand();
				MsgSrvService callbackMsgSrv = MsgSrvManager.getService(appsrv);
				if (callbackMsgSrv == null) {
					LOGGER.warn("[config/msgsrv.xml]中不存在 name=" + appsrv + "的消息服务连接");
				} else {
					StringBuilder message = new StringBuilder();
					message.append(appname).append(" ").append(command).append(" ").append(contents[SESSION_ID]);
					message.append(" ").append("NA").append(" ").append("NA").append(" ").append(contents[_007KA_ORDER]);
					message.append(" ").append(contents[CARRIER]).append(" ").append(contents[TYPE]).append(" ").append(contents[PROVINCE]);
					message.append(" ").append(contents[CITY]).append(" ").append(contents[CHARGE_NO]).append(" ").append(contents[VALUE]);
//					message.append(" ").append(contents[ACC_TIME]).append(" ").append(contents[URL]).append(" ").append(contents[ATTACH]);
//					message.append(" ").append(FAILURE).append(" ").append(DateUtil.format(new Date())).append(" ").append(DateUtil.format(new Date()));
					message.append(" ").append("撮合失败");

					callbackMsgSrv.send(message.toString());
				}
			}
		} else {
			LOGGER.info("不处理此状态码[" + contents[STATE] + "]的订单[" + contents[_007KA_ORDER] + "]");
		}

	}
	
 
	private InterfaceInfoVo findValidInterface(List<InterfaceInfoVo> interfaces) {
		//LYF：如果只有一个最高级别且已经发送过，还继续发的话会有问题。
		if (interfaces.size() == 1) {
			return interfaces.get(0);
		}

		InterfaceInfoVo _interface = null;

		// 如果是全部都已经发送过订单了
		if (isAllSend(interfaces)) {
			// 判断是否到达最大计数器
			if (isAllMaxCounter(interfaces)) {
				//LYF : 隐患，如果充值类型1匹配1、2、3代理，发了2笔订单后，还是下3为发送，这是来了充值类型2，匹配代理商1、2、4这是使用4通道，因为1,2通道已经使用
				//如果此时在再发送充值类型2，则将只用1发送，而2,4设置为未发送，再来充值类型1，则同2通道，3通道可能永远没办法充值。
				resetInterfaces(interfaces);
				_interface = interfaces.get(0);
			} else {
				for (InterfaceInfoVo vo : interfaces) {
					if (vo.getCounter() <= vo.getWeight()) {
						_interface = vo;
						break;
					}
				}
			}
		} else {
			for (InterfaceInfoVo vo : interfaces) {
				if (!vo.isSend()) {
					_interface = vo;
					break;
				}
			}
		}
		return _interface;
	}

	private boolean isAllMaxCounter(List<InterfaceInfoVo> interfaces) {
		boolean result = true;

		for (InterfaceInfoVo vo : interfaces) {
			if (vo.getCounter() <= vo.getWeight()) {
				result = false;
				break;
			}
		}

		return result;
	}

	private void resetInterfaces(List<InterfaceInfoVo> interfaces) {
		for (InterfaceInfoVo vo : interfaces) {
			vo.setCounter(0);
			vo.setSend(false);
		}
	}

	private boolean isAllSend(List<InterfaceInfoVo> interfaces) {
		boolean result = true;

		for (InterfaceInfoVo vo : interfaces) {
			if (!vo.isSend()) {
				result = false;
			}
		}

		return result;
	}

	// 获取级别最高的代理
	//LYF :只区用级别最高的代理，如果级别最高的代理已经发送过，你让级别低的情何以堪  OK
	private List<InterfaceInfoVo> determineLevel(List<InterfaceInfoVo> interfaces) {

		Collections.sort(interfaces, new Comparator<InterfaceInfoVo>() {
			@Override
			public int compare(InterfaceInfoVo o1, InterfaceInfoVo o2) {
				return o2.getLevel() - o1.getLevel();
			}
		});

		List<InterfaceInfoVo> result = new ArrayList<InterfaceInfoVo>();

		InterfaceInfoVo iiv = interfaces.get(0);

		result.add(iiv);

		for (int i = 1; i < interfaces.size(); i++) {
			InterfaceInfoVo vo = interfaces.get(i);
			if (vo.getLevel() >= iiv.getLevel()) {
				result.add(vo);
			} else {
				break;
			}
		}

		return result;
	}

	private boolean isSupportRechargeAgents(InterfaceInfoVo iiv, String carrier, String type, String province, String city) {
		boolean result = true;
		// 判断代理是否支持此充值方式
		List<InterfaceSupportVo> supports = iiv.getSupports();
		if (!supports.isEmpty()) {
			for (InterfaceSupportVo isv : supports) {
				// 如果是支持状态
				if (SUPPORT.equals(isv.getState())) {
					// 判断是否有运营商限制
					if (!isv.getOperatorsSet().isEmpty() && !isv.getOperatorsSet().contains(carrier)) {
						result = false;
						break;
					}
					// 判断是否存在宽带类型限制
					if (!isv.getTypeSet().isEmpty() && !isv.getTypeSet().contains(type)) {
						result = false;
						break;
					}
					// 判断是否存在省份限制
					if (!isv.getProvinceSet().isEmpty() && !isv.getProvinceSet().contains(province)) {
						result = false;
						break;
					}
					// 判断是否存在城市限制
					if (!isv.getCitySet().isEmpty() && !isv.getCitySet().contains(city)) {
						result = false;
						break;
					}

				} else if (NONSUPPORT.equals(isv.getState())) {
					// 如果所有充值都不支持
					if (isv.getOperatorsSet().isEmpty() && isv.getTypeSet().isEmpty() && isv.getProvinceSet().isEmpty() && isv.getCitySet().isEmpty()) {
						result = false;
						break;
					}
					if (!isv.getOperatorsSet().isEmpty() && isv.getOperatorsSet().contains(carrier)) {
						result = false;
						break;
					}
					// 判断是否存在宽带类型限制
					if (!isv.getTypeSet().isEmpty() && isv.getTypeSet().contains(type)) {
						result = false;
						break;
					}
					// 判断是否存在省份限制
					if (!isv.getProvinceSet().isEmpty() && isv.getProvinceSet().contains(province)) {
						result = false;
						break;
					}
					// 判断是否存在城市限制
					if (!isv.getCitySet().isEmpty() && isv.getCitySet().contains(city)) {
						result = false;
						break;
					}
				}
			}
		}
		return result;
	}
	*/
}
