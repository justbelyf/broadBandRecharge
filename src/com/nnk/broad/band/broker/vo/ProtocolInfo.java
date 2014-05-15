package com.nnk.broad.band.broker.vo;

import com.nnk.broad.band.broker.common.OrderInfoIndexConstants;

public class ProtocolInfo {

//	private String	appname	;//	�����������appname
//	private String	command	;//	OrderTran ��������
	private String	sessionId;//	�ɽӿ����ɻỰID����������ÿ�λػ�Ψһ�ԡ�
	private String	agentId	;//	�����̱��
	private String	agentOrderNum;//	�����̶�����
	private String	systemOrderNum;//	007��������
	private String	operator	;//	��Ӫ��
	private String	broadBandType	;//	�������
	private String	province;//	ʡ�ݴ���
	private String	city	;//	���д���
	private String	rechargeAccount;//	��ֵ�˺�
	private String	rechargeAmount	;//	��ֵ���
	private String	processTime	;//	��������ʱ��
	private String	callbackUrl		   ;// �����ص�URL
	private String	tradeInfo	;//	������Ϣ
	private String	orderStatus	;//	״̬ 0000����ʼ״̬ 1000���ɹ�����2000����ʧ�ܣ�3000��ȷ��״̬
	
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
