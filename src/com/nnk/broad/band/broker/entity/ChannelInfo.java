package com.nnk.broad.band.broker.entity;

import java.util.List;

import com.nnk.broad.band.broker.dao.InterfaceInfoDao;
import com.nnk.broad.band.broker.dao.impl.InterfaceInfoDaoImpl;
import com.nnk.broad.band.broker.vo.ProtocolInfo;

public class ChannelInfo {
	private List<AgentInfo> agentInfos;
//	private InterfaceInfo interfaceInfo;
//	private int currentChannelIndex;
//	private int channelsSize;
	private int executeCount;
//	private String lastSystemOrderNum;
	private boolean isUniqueChannel;
	
	private boolean isAvailable;
	
	private boolean isUsed;
	
	public AgentInfo getCurrentAgent(){
		return agentInfos.get(0);
	}
	
	public boolean isOverWeight(){
		AgentInfo agentInfo = getCurrentAgent();
		return agentInfo.getCount() >= agentInfo.getWeight();
	}
	
	
	public ChannelInfo(List<AgentInfo> interfaceInfos) {
		this.agentInfos = interfaceInfos;
	}

	public List<AgentInfo> getAgentInfos() {
		return agentInfos;
	}

	public void setAgentInfos(List<AgentInfo> agentInfos) {
		this.agentInfos = agentInfos;
	}

	public int getExecuteCount() {
		return executeCount;
	}
	public void setExecuteCount(int executeCount) {
		this.executeCount = executeCount;
	}
	public boolean isUniqueChannel() {
		return isUniqueChannel;
	}
	public void setUniqueChannel(boolean isUniqueChannel) {
		this.isUniqueChannel = isUniqueChannel;
	}
	public boolean isAvailable() {
		return isAvailable;
	}
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
	
//	public boolean isAchieveMaxWeight(){
//		return executeCount>=interfaceInfo.getWeight();
//	}
	
//	public void nexChannel(ChannelInfo channelInfo ,String key){
//		int nextChannelIndex=channelInfo.getCurrentChannelIndex()+1;
//		if(nextChannelIndex>= channelsSize){
//			nextChannelIndex=0;
//		}
//		channelInfo.setInterfaceInfo(InterfacesCache.rechargeChannel.get(key).get(nextChannelIndex));
//		channelInfo.setCurrentChannelIndex(nextChannelIndex);
//		channelInfo.setExecuteCount(channelInfo.getInterfaceInfo().getCount());
//	}
	
	
	
}
