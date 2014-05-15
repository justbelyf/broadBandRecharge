package com.nnk.broad.band.broker.config;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.nnk.broad.band.broker.common.XmlUtil;

@XmlRootElement(name = "application")
@XmlAccessorType(XmlAccessType.NONE)
public class ApplicationConfig {

	@XmlElement
	private CallbackConfig callback = new CallbackConfig();

	private static ApplicationConfig instance;

	public static synchronized ApplicationConfig getInstance() {
		if (instance == null) {
			try {
				instance = XmlUtil.loadXmlFromFile(ApplicationConfig.class, "config/BroadBandApp.xml");
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			} catch (JAXBException e) {
				throw new RuntimeException(e);
			}
		}
		return instance;
	}

	public CallbackConfig getCallback() {
		return callback;
	}

	@Override
	public String toString() {
		try {
			return XmlUtil.toXml(this);
		} catch (JAXBException e) {
		} catch (IOException e) {
		}
		return "";
	}
}
