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
//					//���ʧ��
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
					//��֧�ִ�ҵ�񣬴���ʧ��
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
//					//���ʧ��
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
					//����
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
		// ���������ʧ��
		BroadBandService broadBandService = new BroadBandServiceImpl();
		broadBandService.brokerFailure(protocolInfo.getSystemOrderNum());
		// ���͸�callback����֪����ʧ��
		String appsrv = ApplicationConfig.getInstance().getCallback().getAppsrv();
		String appname = ApplicationConfig.getInstance().getCallback().getAppname();
		String command = ApplicationConfig.getInstance().getCallback().getCommand();
		MsgSrvService callbackMsgSrv = MsgSrvManager.getService(appsrv);
		if (callbackMsgSrv == null) {
			LOGGER.warn("[config/msgsrv.xml]�в����� name=" + appsrv + "����Ϣ��������");
		} else {
			StringBuilder message = new StringBuilder();
			message.append(appname).append(" ").append(command).append(" ").append(protocolInfo.getSessionId());
			message.append(" ").append("���ʧ��");
			callbackMsgSrv.send(message.toString());
		}
	}
	
	/**
	 * 		
	
			
			// �õ��˶����Ѿ�ʧ�ܵĴ���
			Set<String> failedAgents = broadBandService.findFailedAgent(orderInfo.getSystemOrderNum());

			List<InterfaceInfoVo> interfaces = new ArrayList<InterfaceInfoVo>(InterfacesCache.INTERFACES);

			Iterator<InterfaceInfoVo> iterator = interfaces.iterator();
			while (iterator.hasNext()) {
				InterfaceInfoVo iiv = iterator.next();
				// �ж϶����Ƿ��Ѿ���ĳЩ�����̴���ʧ��
				if (failedAgents.contains(iiv.getMerid())) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("����[" + contents[_007KA_ORDER] + "�ڴ���[" + iiv.getMerid() + "]��ʧ�ܣ����Դ˴�����д��");
					}
					iterator.remove();
					continue;

				}
				// �ж��Ƿ�֧�ֵĿ����Ӫ�̣����ͣ�ʡ�ݣ����г�ֵ
				if (!isSupportRechargeAgents(iiv, contents[CARRIER], contents[TYPE], contents[PROVINCE], contents[CITY])) {
					iterator.remove();
					continue;
				}
			}

			// ��ϳɹ�
			if (!interfaces.isEmpty()) {

				// ����ӿڵ��������ȡȡ���ȼ����Ľӿڽ��д��
				interfaces = determineLevel(interfaces);
				// ���ڶ�����������ͬ�Ĵ�����Ȩ����ѯ���ͽ��д��
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
				// ���������ʧ��
				service.brokerFailure(contents[_007KA_ORDER]);
				// ���͸�callback����֪����ʧ��
				String appsrv = ApplicationConfig.getInstance().getCallback().getAppsrv();
				String appname = ApplicationConfig.getInstance().getCallback().getAppname();
				String command = ApplicationConfig.getInstance().getCallback().getCommand();
				MsgSrvService callbackMsgSrv = MsgSrvManager.getService(appsrv);
				if (callbackMsgSrv == null) {
					LOGGER.warn("[config/msgsrv.xml]�в����� name=" + appsrv + "����Ϣ��������");
				} else {
					StringBuilder message = new StringBuilder();
					message.append(appname).append(" ").append(command).append(" ").append(contents[SESSION_ID]);
					message.append(" ").append("NA").append(" ").append("NA").append(" ").append(contents[_007KA_ORDER]);
					message.append(" ").append(contents[CARRIER]).append(" ").append(contents[TYPE]).append(" ").append(contents[PROVINCE]);
					message.append(" ").append(contents[CITY]).append(" ").append(contents[CHARGE_NO]).append(" ").append(contents[VALUE]);
//					message.append(" ").append(contents[ACC_TIME]).append(" ").append(contents[URL]).append(" ").append(contents[ATTACH]);
//					message.append(" ").append(FAILURE).append(" ").append(DateUtil.format(new Date())).append(" ").append(DateUtil.format(new Date()));
					message.append(" ").append("���ʧ��");

					callbackMsgSrv.send(message.toString());
				}
			}
		} else {
			LOGGER.info("�������״̬��[" + contents[STATE] + "]�Ķ���[" + contents[_007KA_ORDER] + "]");
		}

	}
	
 
	private InterfaceInfoVo findValidInterface(List<InterfaceInfoVo> interfaces) {
		//LYF�����ֻ��һ����߼������Ѿ����͹������������Ļ��������⡣
		if (interfaces.size() == 1) {
			return interfaces.get(0);
		}

		InterfaceInfoVo _interface = null;

		// �����ȫ�����Ѿ����͹�������
		if (isAllSend(interfaces)) {
			// �ж��Ƿ񵽴���������
			if (isAllMaxCounter(interfaces)) {
				//LYF : �����������ֵ����1ƥ��1��2��3��������2�ʶ����󣬻�����3Ϊ���ͣ��������˳�ֵ����2��ƥ�������1��2��4����ʹ��4ͨ������Ϊ1,2ͨ���Ѿ�ʹ��
				//�����ʱ���ٷ��ͳ�ֵ����2����ֻ��1���ͣ���2,4����Ϊδ���ͣ�������ֵ����1����ͬ2ͨ����3ͨ��������Զû�취��ֵ��
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

	// ��ȡ������ߵĴ���
	//LYF :ֻ���ü�����ߵĴ������������ߵĴ����Ѿ����͹������ü���͵�����Կ�  OK
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
		// �жϴ����Ƿ�֧�ִ˳�ֵ��ʽ
		List<InterfaceSupportVo> supports = iiv.getSupports();
		if (!supports.isEmpty()) {
			for (InterfaceSupportVo isv : supports) {
				// �����֧��״̬
				if (SUPPORT.equals(isv.getState())) {
					// �ж��Ƿ�����Ӫ������
					if (!isv.getOperatorsSet().isEmpty() && !isv.getOperatorsSet().contains(carrier)) {
						result = false;
						break;
					}
					// �ж��Ƿ���ڿ����������
					if (!isv.getTypeSet().isEmpty() && !isv.getTypeSet().contains(type)) {
						result = false;
						break;
					}
					// �ж��Ƿ����ʡ������
					if (!isv.getProvinceSet().isEmpty() && !isv.getProvinceSet().contains(province)) {
						result = false;
						break;
					}
					// �ж��Ƿ���ڳ�������
					if (!isv.getCitySet().isEmpty() && !isv.getCitySet().contains(city)) {
						result = false;
						break;
					}

				} else if (NONSUPPORT.equals(isv.getState())) {
					// ������г�ֵ����֧��
					if (isv.getOperatorsSet().isEmpty() && isv.getTypeSet().isEmpty() && isv.getProvinceSet().isEmpty() && isv.getCitySet().isEmpty()) {
						result = false;
						break;
					}
					if (!isv.getOperatorsSet().isEmpty() && isv.getOperatorsSet().contains(carrier)) {
						result = false;
						break;
					}
					// �ж��Ƿ���ڿ����������
					if (!isv.getTypeSet().isEmpty() && isv.getTypeSet().contains(type)) {
						result = false;
						break;
					}
					// �ж��Ƿ����ʡ������
					if (!isv.getProvinceSet().isEmpty() && isv.getProvinceSet().contains(province)) {
						result = false;
						break;
					}
					// �ж��Ƿ���ڳ�������
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
