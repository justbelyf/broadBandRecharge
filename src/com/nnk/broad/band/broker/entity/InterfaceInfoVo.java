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
	private String merid;// �����̱��
	@Column
	private int level;// ����Խ��Խ���ȣ���ͬ���������ʹ��weight�������������ĸ�����
	@Column
	private int weight;// ����һ�µ�����£�Ȩ�ؽ��ж������ķ���
	@Column
	private String name;// �ӿ�����
	@Column
	private String appname;// �ӿ�appname������
	@Column
	private int state;// ���ã�0�������ã�1������
	@Column
	private Date insertTime;// ����ʱ��
	@Column
	private Date lastTime;// ������ʱ��

	private int counter = 0;// ����������

	private boolean isSend = false;

	private List<InterfaceSupportVo> supports = new ArrayList<InterfaceSupportVo>();// �ӿ�֧�ֵĳ�ֵ��ʽ

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
