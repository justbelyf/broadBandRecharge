package com.nnk.broad.band.broker.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.NONE)
public class CallbackConfig {

	@XmlAttribute
	private String appsrv = "";
	@XmlAttribute
	private String appname = "";
	@XmlAttribute
	private String command = "";

	public String getAppsrv() {
		return appsrv;
	}

	public void setAppsrv(String appsrv) {
		this.appsrv = appsrv;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

}
