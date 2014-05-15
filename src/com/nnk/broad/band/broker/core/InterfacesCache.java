package com.nnk.broad.band.broker.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.nnk.broad.band.broker.entity.ChannelInfo;
import com.nnk.broad.band.broker.entity.AgentInfo;
import com.nnk.broad.band.broker.entity.InterfaceInfoVo;

public class InterfacesCache {

	//�ӿڵĵȼ���Ȩ�أ��Լ�������Ϣ
	public static final Queue<InterfaceInfoVo> INTERFACES =  new ConcurrentLinkedQueue<InterfaceInfoVo>();
	public static final List<String> list = new ArrayList<String>();
	
	public static Map<String, List<AgentInfo>> rechargeChannel = new HashMap<String, List<AgentInfo>>(); 
	public static Map<String, ChannelInfo> channels = new HashMap<String, ChannelInfo>(); 
	public static Map<String, ChannelInfo> channelInfos = new HashMap<String, ChannelInfo>(); 

}
