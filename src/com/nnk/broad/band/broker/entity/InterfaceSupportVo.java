package com.nnk.broad.band.broker.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.nnk.dbsrv.client.heleper.Column;
import com.nnk.dbsrv.client.heleper.Table;

@Table(name = "interface_support")
public class InterfaceSupportVo {

	@Column
	private int id;
	@Column
	private int iid;// '上游接口信息表中的ID字段',
	@Column
	private String operators;// '*' '接口支持/不支持 运营商 “*”通配所有，多值采用","分隔开',
	private Set<String> operatorsSet = new HashSet<String>();
	@Column
	private String type;// '*' comment '接口支持/不支持 宽带类型 “*” 通配所有，多值采用","分隔开',
	private Set<String> typeSet = new HashSet<String>();
	@Column
	private String province;// '*' comment '接口支持/不支持 省份 “*” 通配所有，多值采用","分隔开',
	private Set<String> provinceSet = new HashSet<String>();
	@Column
	private String city;// '接口支持/不支持 城市 “*” 通配所有，多值采用","分隔开',
	private Set<String> citySet = new HashSet<String>();
	@Column
	private String oper;// '限制条件建立人',
	@Column
	private String note;// 备注
	@Column
	private Date operTime;// '限制建立时间',
	@Column
	private int state;// '本条限制是否有效 0 无效，1 支持，2 不支持',

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIid() {
		return iid;
	}

	public void setIid(int iid) {
		this.iid = iid;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Set<String> getOperatorsSet() {
		return operatorsSet;
	}

	public void setOperatorsSet(Set<String> operatorsSet) {
		this.operatorsSet = operatorsSet;
	}

	public Set<String> getTypeSet() {
		return typeSet;
	}

	public void setTypeSet(Set<String> typeSet) {
		this.typeSet = typeSet;
	}

	public Set<String> getProvinceSet() {
		return provinceSet;
	}

	public void setProvinceSet(Set<String> provinceSet) {
		this.provinceSet = provinceSet;
	}

	public Set<String> getCitySet() {
		return citySet;
	}

	public void setCitySet(Set<String> citySet) {
		this.citySet = citySet;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InterfaceSupportVo [id=");
		builder.append(id);
		builder.append(", iid=");
		builder.append(iid);
		builder.append(", operators=");
		builder.append(operators);
		builder.append(", operatorsSet=");
		builder.append(operatorsSet);
		builder.append(", type=");
		builder.append(type);
		builder.append(", typeSet=");
		builder.append(typeSet);
		builder.append(", province=");
		builder.append(province);
		builder.append(", provinceSet=");
		builder.append(provinceSet);
		builder.append(", city=");
		builder.append(city);
		builder.append(", citySet=");
		builder.append(citySet);
		builder.append(", oper=");
		builder.append(oper);
		builder.append(", note=");
		builder.append(note);
		builder.append(", operTime=");
		builder.append(operTime);
		builder.append(", state=");
		builder.append(state);
		builder.append("]");
		return builder.toString();
	}
}
