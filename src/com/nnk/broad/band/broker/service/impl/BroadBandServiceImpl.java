package com.nnk.broad.band.broker.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nnk.broad.band.broker.config.ApplicationConfig;
import com.nnk.broad.band.broker.core.InterfacesCache;
import com.nnk.broad.band.broker.dao.InterfaceInfoDao;
import com.nnk.broad.band.broker.dao.InterfaceOrderDao;
import com.nnk.broad.band.broker.dao.PartnerOrderDao;
import com.nnk.broad.band.broker.dao.impl.InterfaceInfoDaoImpl;
import com.nnk.broad.band.broker.dao.impl.InterfaceOrderDaoImpl;
import com.nnk.broad.band.broker.dao.impl.PartnerOrderDaoImpl;
import com.nnk.broad.band.broker.entity.AgentInfo;
import com.nnk.broad.band.broker.entity.ChannelInfo;
import com.nnk.broad.band.broker.entity.InterfaceOrder;
import com.nnk.broad.band.broker.service.BroadBandService;
import com.nnk.broad.band.broker.vo.ProtocolInfo;
import com.nnk.msgsrv.client.core.MsgSrvManager;
import com.nnk.msgsrv.client.core.MsgSrvService;

public class BroadBandServiceImpl implements BroadBandService {
	private Logger logger = Logger.getLogger(BroadBandServiceImpl.class);
	
//	private InterfaceInfoDao interfaceInfoDao = new InterfaceInfoDaoImpl();

//	private InterfaceSupportDao interfaceSupporDao = new InterfaceSupportDaoImpl();

	private InterfaceOrderDao interfaceOrderDao = new InterfaceOrderDaoImpl();

	private PartnerOrderDao partnerOrderDao = new PartnerOrderDaoImpl();
	
	private ProtocolInfo protocolInfo;
	
	private Map<String, ChannelInfo> channels = InterfacesCache.channels;
	private ChannelInfo channelInfo;
	
	public BroadBandServiceImpl(ProtocolInfo protocolInfo) {
		this.protocolInfo = protocolInfo;
	}
	public void rechargeChannel(){
		if(channels.containsKey(compositionChannelKey(protocolInfo))){
			channelInfo = channels.get(compositionChannelKey(protocolInfo));
			if(channelInfo.isAvailable()){
				if(channelInfo.isUsed()){
					if(channelInfo.isUniqueChannel()){
						tempMethod();
					}else{
						 if(channelInfo.isOverWeight()){
							 nextAgent();
							 tempMethod();
						 }else{
							 tempMethod();
						 }
						 
					}
				}else{
					channelInfo.setUsed(true);
					tempMethod();
				}
				
				
			}else{
				partnerOrderDao.update(protocolInfo.getSystemOrderNum());
				sendFailMsg();
			}
		}else{
			initChannel();
			rechargeChannel();
//			channelInfo = channels.get(compositionChannelKey(protocolInfo));
//			if(channelInfo.isAvailable()){
//				AgentInfo agentInfo = channelInfo.getCurrentAgent();
//				interfaceOrderDao.addInterfaceOrder(protocolInfo,agentInfo.getAgentId());
//				sendSuccessMsg();
//			}else{
//				partnerOrderDao.update(protocolInfo.getSystemOrderNum());
//				sendFailMsg();
//			}
		}
	}
	
	public void rechargeChannelAgain(){
		
		if(channels.containsKey(compositionChannelKey(protocolInfo))){
			channelInfo = channels.get(compositionChannelKey(protocolInfo));
			if(channelInfo.isAvailable()){
				if(channelInfo.isUsed()){
					List<InterfaceOrder> failInterfaceOrders = interfaceOrderDao.getFailAgents(protocolInfo.getSystemOrderNum());
					boolean hasFailInterfaceOrder = !(null == failInterfaceOrders || failInterfaceOrders.isEmpty());
					if(hasFailInterfaceOrder){
						if(failInterfaceOrders.size() == channelInfo.getAgentInfos().size()){
							partnerOrderDao.update(protocolInfo.getSystemOrderNum());
							sendFailMsg();
						}else{
//							List<AgentInfo> cloneAgentInfos = new ArrayList<AgentInfo>(channelInfo.getAgentInfos()); 
//							for (InterfaceOrder interfaceOrder : failInterfaceOrders) {
//								for (AgentInfo agentInfo: cloneAgentInfos){
//									if(agentInfo.getAgentId().equals(interfaceOrder.getMerid())){
//										cloneAgentInfos.remove(agentInfo);
//										break;
//									}
//								}
//							}
							
//							for (int i = 0; i < vailableAgentInfos.size(); i++) {
//								AgentInfo agentInfo = vailableAgentInfos.get(0);
//								if(agentInfo.getCount()>=agentInfo.getWeight()){
//									vailableAgentInfos.remove(agentInfo);
//									vailableAgentInfos.add(agentInfo);
//								}else{
//									break;
//								}
//							}
							
							List<AgentInfo> vailableAgentInfos = removeFailAgents(failInterfaceOrders);
							AgentInfo agentInfo = getAgent(vailableAgentInfos);
							agentInfo.setCount(agentInfo.getCount()+1);
							interfaceOrderDao.addInterfaceOrder(protocolInfo,agentInfo.getAgentId());
							sendSuccessMsg();
						}
							
					}else{
						 if(channelInfo.isOverWeight()){
							 nextAgent();
							 tempMethod();
						 }else{
							 tempMethod();
						 }
					}
//					if(channelInfo.isUniqueChannel()){
//						tempMethod();
//					}else{
//						 if(channelInfo.isOverWeight()){
//							 nextAgent();
//							 tempMethod();
//						 }else{
//							 tempMethod();
//						 }
//						 
//					}
				}
				else{
					channelInfo.setUsed(true);
					tempMethod();
				}
				
				
			}else{
				partnerOrderDao.update(protocolInfo.getSystemOrderNum());
				sendFailMsg();
			}
		}else{
			initChannel();
			rechargeChannel();
		}
	
	}
	
	public void tempMethod(){
		AgentInfo agentInfo = channelInfo.getCurrentAgent();
		channelInfo.getCurrentAgent().setCount(channelInfo.getCurrentAgent().getCount()+1);
		interfaceOrderDao.addInterfaceOrder(protocolInfo,agentInfo.getAgentId());
		sendSuccessMsg();
	}
	public String compositionChannelKey(ProtocolInfo protocolInfo){
		String channelKey = protocolInfo.getOperator()+ protocolInfo.getBroadBandType()+protocolInfo.getProvince()+protocolInfo.getCity();
		return channelKey;
	}
	
	public void initChannel(){
		InterfaceInfoDao interfaceInfoDao = new InterfaceInfoDaoImpl();
		List<AgentInfo> interfaceInfos = interfaceInfoDao.getValidAgents(protocolInfo);
		ChannelInfo channelInfo = new ChannelInfo(interfaceInfos);
		if(null != interfaceInfos && !interfaceInfos.isEmpty()){
			channelInfo.setAvailable(true);
			if(interfaceInfos.size()==1){
				channelInfo.setUniqueChannel(true);
			}else{
				channelInfo.setUniqueChannel(false);
			}
		}else{
			channelInfo.setAvailable(false);
		}
		channelInfo.setUsed(false);
		channels.put(compositionChannelKey(protocolInfo), channelInfo);
	}

	public AgentInfo nextAgent(){
		 reorder();
		 return channelInfo.getCurrentAgent();
	}
	
	public void reorder(){
		List<AgentInfo> agentInfos = channelInfo.getAgentInfos();
		boolean isAllAgentOverWeight=true;
		for (int i = 0; i < agentInfos.size(); i++) {
			AgentInfo interfaceInfo = agentInfos.get(0);
			agentInfos.remove(interfaceInfo);
			agentInfos.add(interfaceInfo);
			if(!channelInfo.isOverWeight()){
				isAllAgentOverWeight=false;
				break;
			}
		}
		if(isAllAgentOverWeight){
			initChannel();
		}
	}
	
	public List<AgentInfo> removeFailAgents(List<InterfaceOrder> failInterfaceOrders){
		List<AgentInfo> cloneAgentInfos = new ArrayList<AgentInfo>(channelInfo.getAgentInfos()); 
		for (InterfaceOrder interfaceOrder : failInterfaceOrders) {
			for (AgentInfo agentInfo: cloneAgentInfos){
				if(agentInfo.getAgentId().equals(interfaceOrder.getMerid())){
					cloneAgentInfos.remove(agentInfo);
					break;
				}
			}
		}
		return cloneAgentInfos;
	}
	
	public AgentInfo getAgent(List<AgentInfo> vailableAgentInfos){
		for (int i = 0; i < vailableAgentInfos.size(); i++) {
			AgentInfo agentInfo = vailableAgentInfos.get(0);
			if(agentInfo.getCount()>=agentInfo.getWeight()){
				vailableAgentInfos.remove(agentInfo);
				vailableAgentInfos.add(agentInfo);
			}else{
				break;
			}
		}
		return vailableAgentInfos.get(0);
	}
	
	public void sendFailMsg(){
		String appsrv = ApplicationConfig.getInstance().getCallback().getAppsrv();
		String appname = ApplicationConfig.getInstance().getCallback().getAppname();
		String command = ApplicationConfig.getInstance().getCallback().getCommand();
		MsgSrvService callbackMsgSrv = MsgSrvManager.getService(appsrv);
		if (callbackMsgSrv == null) {
			logger.warn("[config/msgsrv.xml]中不存在 name=" + appsrv + "的消息服务连接");
		} else {
			StringBuilder message = new StringBuilder();
			message.append(appname).append(" ").append(command).append(" ").append(protocolInfo.getSessionId());
			message.append(" ").append("撮合失败");
			callbackMsgSrv.send(message.toString());
		}
	}
		
	public void sendSuccessMsg(){
			String appsrv = ApplicationConfig.getInstance().getCallback().getAppsrv();
			String appname = ApplicationConfig.getInstance().getCallback().getAppname();
			String command = ApplicationConfig.getInstance().getCallback().getCommand();
			MsgSrvService service = MsgSrvManager.getService("BroadBandBroker");
			if (service == null) {
				logger.warn("[config/msgsrv.xml]中不存在 name=" + appsrv + "的消息服务连接");
			} else {
				StringBuilder message = new StringBuilder();
				message.append(appname).append(" ").append(command).append(" ").append(protocolInfo.getSessionId());
				message.append(" ").append("撮合失败");
				service.send(message.toString());
			}
	}
	
	
	
	
	
	
	
	
	
	
	
	
//	@Override
//	public List<InterfaceInfoVo> load() {
//		List<InterfaceInfoVo> interfaces = new ArrayList<InterfaceInfoVo>();
//
//		String queryInterfaceInfoSql = "SELECT id,merid,level,weight,name,appname,state,insertTime,lastTime FROM interface_info WHERE state = 1;";
//		interfaces = interfaceInfoDao.list(queryInterfaceInfoSql);
//		for (InterfaceInfoVo iiv : interfaces) {
//			String querySupporSql = "SELECT id,iid,operators,type,province,city,oper,note,operTime,state FROM interface_support WHERE iid = " + iiv.getId()
//					+ " AND state <> 0;";
//			List<InterfaceSupportVo> suppors = interfaceSupporDao.list(querySupporSql);
//
//			// 对支持信息进行排序，时间越靠近当前的时间的，排在前面
//			Collections.sort(suppors, new Comparator<InterfaceSupportVo>() {
//				@Override
//				public int compare(InterfaceSupportVo o1, InterfaceSupportVo o2) {
//					long result = o1.getOperTime().getTime() - o2.getOperTime().getTime();
//					if (result > 0) {
//						return -1;
//					} else if (result < 0) {
//						return 1;
//					}
//					return 0;
//				}
//			});
//
//			iiv.setSupports(suppors);
//
//			for (InterfaceSupportVo isv : suppors) {
//				// 将运营商的信息添加到集合中
//				String operators = isv.getOperators();
//				if (!"*".equals(operators) && StringUtil.isNotEmpty(operators)) {
//					String[] operator = operators.split(",");
//					isv.getOperatorsSet().addAll(Arrays.asList(operator));
//				}
//
//				// 将运营商的信息添加到集合中
//				String type = isv.getType();
//				if (!"*".equals(type) && StringUtil.isNotEmpty(type)) {
//					String[] types = type.split(",");
//					isv.getTypeSet().addAll(Arrays.asList(types));
//				}
//
//				// 将省份的信息添加到集合中
//				String province = isv.getProvince();
//				if (!"*".equals(province) && StringUtil.isNotEmpty(province)) {
//					String[] provinces = province.split(",");
//					isv.getProvinceSet().addAll(Arrays.asList(provinces));
//				}
//
//				// 将省份的信息添加到集合中
//				String city = isv.getCity();
//				if (!"*".equals(city) && StringUtil.isNotEmpty(city)) {
//					String[] citys = city.split(",");
//					isv.getCitySet().addAll(Arrays.asList(citys));
//				}
//			}
//		}
//
//		// 对接口信息进行排序，级别越大越靠前，级别相同情况下，最后修改时间越近越靠前
//		Collections.sort(interfaces, new Comparator<InterfaceInfoVo>() {
//			@Override
//			public int compare(InterfaceInfoVo o1, InterfaceInfoVo o2) {
//				int result = o1.getLevel() - o2.getWeight();
//				if (result == 0) {
//					long time = o1.getLastTime().getTime() - o2.getLastTime().getTime();
//					if (time > 0) {
//						result = 1;
//					} else if (time < 0) {
//						result = -1;
//					}
//				}
//				return -result;
//			}
//		});
//		// 重新加载缓存信息
//		InterfacesCache.INTERFACES.clear();
//		InterfacesCache.INTERFACES.addAll(interfaces);
//		return interfaces;
//	}

//	@Override
//	public List<InterfaceOrder> findFailedAgent(String systemOrder) {
////		Set<String> faileAgents = new HashSet<String>();
//		String sql = "SELECT merid FROM interface_order WHERE buyOrder='" + systemOrder + "' AND hdlState='" + GlobalConstants.TRADING_FAILED + "';";
//		List<InterfaceOrder> InterfaceOrders = interfaceOrderDao.getFailAgents("systemOrderNum");
////		for (InterfaceOrderVo iov : vos) {
////			faileAgents.add(iov.getMerid());
////		}
//		return InterfaceOrders;
//	}

//	public void brokerFailure(String _007kaOrder) {
//		String sql = "UPDATE partner_order SET brokerState = '" + GlobalConstants.TRADING_FAILED + "',lastBrokerTime=NOW() WHERE buyOrder = '" + _007kaOrder + "';";
//		partnerOrder.update(sql);
//	}

//	@Override
//	public void brokerSuccess(String buyOrder, String sendOrder, String merid, String account, String chgAmount) {
//		String sql = "INSERT INTO(buyOrder,sendOrder,merid,account,chgAmount,reqTime) VALUES('%s','%s','%s','%s',%s,'%s');";
//		sql = String.format(sql, buyOrder, sendOrder, merid, account, chgAmount);
//		boolean result = interfaceOrderDao.insert(sql);
//		if (!result) {
//			throw new RuntimeException("插入订单[" + buyOrder + "]失败");
//		}
//	}

	
	
	
//public List<AgentInfo> getValidAgents(ProtocolInfo orderInfo) {
//	orderInfo.setProvince("pr");
//	orderInfo.setCity("city");
//	orderInfo.setBroadBandType("broadBandType");
//	orderInfo.setOperator("operator");
//	StringBuffer sql = new StringBuffer("SELECT id,agentId,LEVEL,weight,appname,operators,TYPE,province,city,COMMENT,STATUS FROM interface_info WHERE STATUS=1 and (province=");
//	sql.append("'");
//	sql.append(orderInfo.getProvince());
//	sql.append("' or province='*')");
//	sql.append(" and (city='");
//	sql.append(orderInfo.getCity());
//	sql.append("' or city='*')");
//	sql.append(" and (type='");
//	sql.append(orderInfo.getBroadBandType());
//	sql.append("' or type='*')");
//	sql.append(" and (operators='");
//	sql.append(orderInfo.getOperator());
//	sql.append("' or operators='*')");
//	sql.append("and weight >=1 ORDER BY LEVEL DESC ,WEIGHT DESC , createTime DESC;");
////	String sql ="SELECT id,agentId,LEVEL,weight,appname,operators,TYPE,province,city,COMMENT,STATUS FROM interface_info WHERE STATUS='1'";
////	SELECT id,agentId,LEVEL,weight,appname,operators,TYPE,province,city,COMMENT,STATUS FROM interface_info WHERE STATUS='1' AND (province='' OR province='*') AND (city='' OR city ='*')
////			AND (TYPE='' OR TYPE='*') AND (operators='' OR operators='*') and weight >=1 ORDER BY LEVEL AND CREATETIME DESC;;
//	System.out.println(sql);
//	
//	return interfaceOrderDao.getValidAgents(sql.toString());
//}


public static void main(String[] args) {
//	new BroadBandServiceImpl().getValidAgents(new ProtocolInfo(new String[]{"1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1"}));
}
}
