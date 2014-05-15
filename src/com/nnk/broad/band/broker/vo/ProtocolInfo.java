package com.nnk.broad.band.broker.vo;

import com.nnk.broad.band.broker.common.OrderInfoIndexConstants;

public class ProtocolInfo {

//	private String	appname	;//	订单处理程序appname
//	private String	command	;//	OrderTran 订单处理
	private String	sessionId;//	由接口生成会话ID，用以区分每次回话唯一性。
	private String	agentId	;//	代理商编号
	private String	agentOrderNum;//	代理商订单号
	private String	systemOrderNum;//	007卡订单号
	private String	operator	;//	运营商
	private String	broadBandType	;//	宽带类型
	private String	province;//	省份代码
	private String	city	;//	城市代码
	private String	rechargeAccount;//	充值账号
	private String	rechargeAmount	;//	充值金额
	private String	processTime	;//	订单受理时间
	private String	callbackUrl		   ;// 订单回调URL
	private String	tradeInfo	;//	交易信息
	private String	orderStatus	;//	状态 0000，初始状态 1000，成功受理，2000受理失败，3000不确定状态
	
	public ProtocolInfo(String[] protocol) {
		setSessionId(protocol[OrderInfoIndexConstants.SESSION_ID]);
		setAgentId(protocol[OrderInfoIndexConstants.AGENT_ID]);
		setAgentOrderNum(protocol[OrderInfoIndexConstants.AGENT_ORDER_NUM]);
		setSystemOrderNum(protocol[OrderInfoIndexConstants.SYSTEM_ORDER_NUM]);
		setOperator(protocol[OrderInfoIndexConstants.OPERATOR]);
		setBroadBandType(protocol[OrderInfoIndexConstants.BROAD_BAND_TYPE]);
		setProvince(protocol[OrderInfoIndexConstants.PROVINCE]);
		setCity(protocol[OrderInfoIndexConstants.CITY]);
		setRechargeAccount(protocol[OrderInfoIndexConstants.RECHARGE_ACCOUNT]);
		setRechargeAmount(protocol[OrderInfoIndexConstants.RECHARGE_AMOUNT]);
		setProcessTime(protocol[OrderInfoIndexConstants.PROCESS_TIME]);
		setCallbackUrl(protocol[OrderInfoIndexConstants.CALLBACK_URL]);
		setTradeInfo(protocol[OrderInfoIndexConstants.TRADE_INFO]);
		setOrderStatus(protocol[OrderInfoIndexConstants.ORDER_STATUS]);
	}
	
//	public String getAppname() {
//		return appname;
//	}
//	public void setAppname(String appname) {
//		this.appname = appname;
//	}
//	public String getCommand() {
//		return command;
//	}
//	public void setCommand(String command) {
//		this.command = command;
//	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getAgentOrderNum() {
		return agentOrderNum;
	}
	public void setAgentOrderNum(String agentOrderNum) {
		this.agentOrderNum = agentOrderNum;
	}
	public String getSystemOrderNum() {
		return systemOrderNum;
	}
	public void setSystemOrderNum(String systemOrderNum) {
		this.systemOrderNum = systemOrderNum;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getBroadBandType() {
		return broadBandType;
	}
	public void setBroadBandType(String broadBandType) {
		this.broadBandType = broadBandType;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRechargeAccount() {
		return rechargeAccount;
	}
	public void setRechargeAccount(String rechargeAccount) {
		this.rechargeAccount = rechargeAccount;
	}
	public String getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(String rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	public String getProcessTime() {
		return processTime;
	}
	public void setProcessTime(String processTime) {
		this.processTime = processTime;
	}
	
	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getTradeInfo() {
		return tradeInfo;
	}
	public void setTradeInfo(String tradeInfo) {
		this.tradeInfo = tradeInfo;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

}
