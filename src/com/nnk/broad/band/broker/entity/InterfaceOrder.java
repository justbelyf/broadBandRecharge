package com.nnk.broad.band.broker.entity;

import java.util.Date;

import com.nnk.dbsrv.client.heleper.Column;
import com.nnk.dbsrv.client.heleper.Table;

@Table(name = "interface_order")
public class InterfaceOrder {

	@Column
	private int id;
	@Column
	private String buyOrder;
	@Column
	private String sendOrder;
	@Column
	private String merid;
	@Column
	private String oemOrder;
	@Column
	private double account;
	@Column
	private double chgAmount;
	@Column
	private Date reqTime;
	@Column
	private String accState;
	@Column
	private Date accTime;
	@Column
	private String hdlState;
	@Column
	private Date hdlTime;
	@Column
	private String failedCause;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBuyOrder() {
		return buyOrder;
	}

	public void setBuyOrder(String buyOrder) {
		this.buyOrder = buyOrder;
	}

	public String getSendOrder() {
		return sendOrder;
	}

	public void setSendOrder(String sendOrder) {
		this.sendOrder = sendOrder;
	}

	public String getMerid() {
		return merid;
	}

	public void setMerid(String merid) {
		this.merid = merid;
	}

	public String getOemOrder() {
		return oemOrder;
	}

	public void setOemOrder(String oemOrder) {
		this.oemOrder = oemOrder;
	}

	public double getAccount() {
		return account;
	}

	public void setAccount(double account) {
		this.account = account;
	}

	public double getChgAmount() {
		return chgAmount;
	}

	public void setChgAmount(double chgAmount) {
		this.chgAmount = chgAmount;
	}

	public Date getReqTime() {
		return reqTime;
	}

	public void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
	}

	public String getAccState() {
		return accState;
	}

	public void setAccState(String accState) {
		this.accState = accState;
	}

	public Date getAccTime() {
		return accTime;
	}

	public void setAccTime(Date accTime) {
		this.accTime = accTime;
	}

	public String getHdlState() {
		return hdlState;
	}

	public void setHdlState(String hdlState) {
		this.hdlState = hdlState;
	}

	public Date getHdlTime() {
		return hdlTime;
	}

	public void setHdlTime(Date hdlTime) {
		this.hdlTime = hdlTime;
	}

	public String getFailedCause() {
		return failedCause;
	}

	public void setFailedCause(String failedCause) {
		this.failedCause = failedCause;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InterfaceOrderVo [id=");
		builder.append(id);
		builder.append(", buyOrder=");
		builder.append(buyOrder);
		builder.append(", sendOrder=");
		builder.append(sendOrder);
		builder.append(", merid=");
		builder.append(merid);
		builder.append(", oemOrder=");
		builder.append(oemOrder);
		builder.append(", account=");
		builder.append(account);
		builder.append(", chgAmount=");
		builder.append(chgAmount);
		builder.append(", reqTime=");
		builder.append(reqTime);
		builder.append(", accState=");
		builder.append(accState);
		builder.append(", accTime=");
		builder.append(accTime);
		builder.append(", hdlState=");
		builder.append(hdlState);
		builder.append(", hdlTime=");
		builder.append(hdlTime);
		builder.append(", failedCause=");
		builder.append(failedCause);
		builder.append("]");
		return builder.toString();
	}

}
