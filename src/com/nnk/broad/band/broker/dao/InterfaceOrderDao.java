package com.nnk.broad.band.broker.dao;

import java.util.List;

import com.nnk.broad.band.broker.entity.InterfaceOrder;
import com.nnk.broad.band.broker.vo.ProtocolInfo;

public interface InterfaceOrderDao {

	List<InterfaceOrder> getFailAgents(String systemOrderNum);
	
	boolean addInterfaceOrder(ProtocolInfo protocolInfo,String agentId);
}
