package com.nnk.broad.band.broker.dao;

import java.util.List;

import com.nnk.broad.band.broker.entity.AgentInfo;
import com.nnk.broad.band.broker.vo.ProtocolInfo;

public interface InterfaceInfoDao {

//	List<InterfaceInfoVo> list(String sql);
	
	List<AgentInfo> getValidAgents(ProtocolInfo protocolInfo);
}
