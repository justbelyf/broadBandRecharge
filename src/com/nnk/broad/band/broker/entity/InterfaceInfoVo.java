package com.nnk.broad.band.broker.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nnk.dbsrv.client.heleper.Column;
import com.nnk.dbsrv.client.heleper.Table;

@Table(name = "interface_info")
public class InterfaceInfoVo {

	@Column
	private int id;
	@Column
	private String merid;// 代理商编号
	@Column
	private int level;// 级别越高越优先，相同级别情况下使用weight来决定具体走哪个代理
	@Column
	private int weight;// 级别一致的情况下，权重进行订单量的分配
	@Column
	private String name;// 接口名称
	@Column
	private String appname;// 接口appname的名称
	@Column
	private int state;// 启用，0：不启用，1：启用
	@Column
	private Date insertTime;// 插入时间
	@Column
	private Date lastTime;// 最后更新时间

	private int counter = 0;// 订单发送量

	private boolean isSend = false;

	private List<InterfaceSupportVo> supports = new ArrayList<InterfaceSupportVo>();// 接口支持的充值方式

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMerid() {
		return merid;
	}

	public void setMerid(String merid) {
		this.merid = merid;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public List<InterfaceSupportVo> getSupports() {
		return supports;
	}

	public void setSupports(List<InterfaceSupportVo> supports) {
		this.supports = supports;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InterfaceInfoVo [id=");
		builder.append(id);
		builder.append(", merid=");
		builder.append(merid);
		builder.append(", level=");
		builder.append(level);
		builder.append(", weight=");
		builder.append(weight);
		builder.append(", name=");
		builder.append(name);
		builder.append(", appname=");
		builder.append(appname);
		builder.append(", state=");
		builder.append(state);
		builder.append(", insertTime=");
		builder.append(insertTime);
		builder.append(", lastTime=");
		builder.append(lastTime);
		builder.append(", supports=");
		builder.append(supports);
		builder.append("]");
		return builder.toString();
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public boolean isSend() {
		return isSend;
	}

	public void setSend(boolean isSend) {
		this.isSend = isSend;
	}

}
