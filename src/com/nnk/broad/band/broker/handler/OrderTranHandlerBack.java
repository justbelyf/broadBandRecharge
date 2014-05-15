package com.nnk.broad.band.broker.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.nnk.broad.band.broker.common.DateUtil;
import com.nnk.broad.band.broker.common.GlobalConstants;
import com.nnk.broad.band.broker.config.ApplicationConfig;
import com.nnk.broad.band.broker.core.InterfacesCache;
import com.nnk.broad.band.broker.core.SupportDict;
import com.nnk.broad.band.broker.entity.InterfaceInfoVo;
import com.nnk.broad.band.broker.entity.InterfaceSupportVo;
import com.nnk.broad.band.broker.service.BroadBandService;
import com.nnk.broad.band.broker.service.impl.BroadBandServiceImpl;
import com.nnk.broad.band.broker.vo.ProtocolInfo;
import com.nnk.msgsrv.client.core.MsgSrvManager;
import com.nnk.msgsrv.client.core.MsgSrvService;
import com.nnk.msgsrv.client.core.Session;

public class OrderTranHandlerBack implements  SupportDict {

	private static final Logger LOGGER = Logger.getLogger(OrderTranHandlerBack.class);

	private static final BroadBandService service = new BroadBandServiceImpl();

	public void orderTran(Session session) {

		String[] protocol = session.getContent().split(" +");
		ProtocolInfo orderInfo = new ProtocolInfo(protocol);
//		StringBuilder orderInfo = new StringBuilder();
//
//		orderInfo.append("[OrderTran]").append("[session:" + contents[SESSION_ID] + "]").append("[�����̱��:" + contents[MER_ID] + "]")
//				.append("[�����̶�����:" + contents[OEM_ORDER] + "]").append("[007��������:" + contents[_007KA_ORDER] + "]").append("[��Ӫ��:" + contents[CARRIER] + "]")
//				.append("[�������:" + contents[TYPE] + "]").append("[ʡ�ݴ���:" + contents[PROVINCE] + "]").append("[���д���:" + contents[CITY] + "]")
//				.append("[��ֵ�˺�:" + contents[CHARGE_NO] + "]").append("[��ֵ���:" + contents[VALUE] + "]").append("[����ʱ��:" + contents[ACC_TIME] + "]")
//				.append("[�ص�URL:" + contents[URL] + "]").append("[������Ϣ:" + contents[ATTACH] + "]").append("[״̬:" + contents[STATE] + "]");
//
//		LOGGER.info(orderInfo.toString());

		if (GlobalConstants.TRADING_SUCCESS.equals(orderInfo.getOrderStatus())) {
			// �õ��˶����Ѿ�ʧ�ܵĴ���
			Set<String> failedAgents = service.findFailedAgent(orderInfo.getSystemOrderNum());

			List<InterfaceInfoVo> interfaces = new ArrayList<InterfaceInfoVo>(InterfacesCache.INTERFACES);

			Iterator<InterfaceInfoVo> iterator = interfaces.iterator();
			while (iterator.hasNext()) {
				InterfaceInfoVo iiv = iterator.next();
				// �ж϶����Ƿ��Ѿ���ĳЩ�����̴���ʧ��
				if (failedAgents.contains(iiv.getMerid())) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("����[" + contents[_007KA_ORDER] + "�ڴ���[" + iiv.getMerid() + "]��ʧ�ܣ����Դ˴�����д��");
					}
					iterator.remove();
					continue;

				}
				// �ж��Ƿ�֧�ֵĿ����Ӫ�̣����ͣ�ʡ�ݣ����г�ֵ
				if (!isSupportRechargeAgents(iiv, contents[CARRIER], contents[TYPE], contents[PROVINCE], contents[CITY])) {
					iterator.remove();
					continue;
				}
			}

			// ��ϳɹ�
			if (!interfaces.isEmpty()) {

				// ����ӿڵ��������ȡȡ���ȼ����Ľӿڽ��д��
				interfaces = determineLevel(interfaces);
				// ���ڶ�����������ͬ�Ĵ�����Ȩ����ѯ���ͽ��д��
				InterfaceInfoVo _interface = findValidInterface(interfaces);
				_interface.setSend(true);
				_interface.setCounter(_interface.getCounter() + 1);
				service.brokerSuccess(contents[_007KA_ORDER], contents[_007KA_ORDER], _interface.getMerid(), contents[CHARGE_NO], contents[VALUE]);
				MsgSrvService service = MsgSrvManager.getService("BroadBandBroker");

				StringBuilder message = new StringBuilder();
				message.append(_interface.getAppname()).append(" ").append("OrderHandler").append(" ").append(contents[SESSION_ID]);
				message.append(" ").append(_interface.getMerid()).append(" ").append("NA").append(" ").append(contents[_007KA_ORDER]);
				message.append(" ").append(contents[CARRIER]).append(" ").append(contents[TYPE]).append(" ").append(contents[PROVINCE]);
				message.append(" ").append(contents[CITY]).append(" ").append(contents[CHARGE_NO]).append(" ").append(contents[VALUE]);
				message.append(" ").append(contents[ACC_TIME]).append(" ").append(contents[URL]).append(" ").append(contents[ATTACH]);
				message.append(" ").append(contents[STATE]).append(" ").append(DateUtil.format(new Date())).append(" ").append("NA");
				message.append(" ").append("NA");

				service.send(message.toString());
			} else {
				// ���������ʧ��
				service.brokerFailure(contents[_007KA_ORDER]);
				// ���͸�callback����֪����ʧ��
				String appsrv = ApplicationConfig.getInstance().getCallback().getAppsrv();
				String appname = ApplicationConfig.getInstance().getCallback().getAppname();
				String command = ApplicationConfig.getInstance().getCallback().getCommand();
				MsgSrvService callbackMsgSrv = MsgSrvManager.getService(appsrv);
				if (callbackMsgSrv == null) {
					LOGGER.warn("[config/msgsrv.xml]�в����� name=" + appsrv + "����Ϣ��������");
				} else {
					StringBuilder message = new StringBuilder();
					message.append(appname).append(" ").append(command).append(" ").append(contents[SESSION_ID]);
					message.append(" ").append("NA").append(" ").append("NA").append(" ").append(contents[_007KA_ORDER]);
					message.append(" ").append(contents[CARRIER]).append(" ").append(contents[TYPE]).append(" ").append(contents[PROVINCE]);
					message.append(" ").append(contents[CITY]).append(" ").append(contents[CHARGE_NO]).append(" ").append(contents[VALUE]);
//					message.append(" ").append(contents[ACC_TIME]).append(" ").append(contents[URL]).append(" ").append(contents[ATTACH]);
//					message.append(" ").append(FAILURE).append(" ").append(DateUtil.format(new Date())).append(" ").append(DateUtil.format(new Date()));
					message.append(" ").append("���ʧ��");

					callbackMsgSrv.send(message.toString());
				}
			}
		} else {
			LOGGER.info("�������״̬��[" + contents[STATE] + "]�Ķ���[" + contents[_007KA_ORDER] + "]");
		}

	}

	private InterfaceInfoVo findValidInterface(List<InterfaceInfoVo> interfaces) {
		//LYF�����ֻ��һ����߼������Ѿ����͹������������Ļ��������⡣
		if (interfaces.size() == 1) {
			return interfaces.get(0);
		}

		InterfaceInfoVo _interface = null;

		// �����ȫ�����Ѿ����͹�������
		if (isAllSend(interfaces)) {
			// �ж��Ƿ񵽴���������
			if (isAllMaxCounter(interfaces)) {
				//LYF : �����������ֵ����1ƥ��1��2��3��������2�ʶ����󣬻�����3Ϊ���ͣ��������˳�ֵ����2��ƥ�������1��2��4����ʹ��4ͨ������Ϊ1,2ͨ���Ѿ�ʹ��
				//�����ʱ���ٷ��ͳ�ֵ����2����ֻ��1���ͣ���2,4����Ϊδ���ͣ�������ֵ����1����ͬ2ͨ����3ͨ��������Զû�취��ֵ��
				resetInterfaces(interfaces);
				_interface = interfaces.get(0);
			} else {
				for (InterfaceInfoVo vo : interfaces) {
					if (vo.getCounter() <= vo.getWeight()) {
						_interface = vo;
						break;
					}
				}
			}
		} else {
			for (InterfaceInfoVo vo : interfaces) {
				if (!vo.isSend()) {
					_interface = vo;
					break;
				}
			}
		}
		return _interface;
	}

	private boolean isAllMaxCounter(List<InterfaceInfoVo> interfaces) {
		boolean result = true;

		for (InterfaceInfoVo vo : interfaces) {
			if (vo.getCounter() <= vo.getWeight()) {
				result = false;
				break;
			}
		}

		return result;
	}

	private void resetInterfaces(List<InterfaceInfoVo> interfaces) {
		for (InterfaceInfoVo vo : interfaces) {
			vo.setCounter(0);
			vo.setSend(false);
		}
	}

	private boolean isAllSend(List<InterfaceInfoVo> interfaces) {
		boolean result = true;

		for (InterfaceInfoVo vo : interfaces) {
			if (!vo.isSend()) {
				result = false;
			}
		}

		return result;
	}

	// ��ȡ������ߵĴ���
	//LYF :ֻ���ü�����ߵĴ������������ߵĴ����Ѿ����͹������ü���͵�����Կ�  OK
	private List<InterfaceInfoVo> determineLevel(List<InterfaceInfoVo> interfaces) {

		Collections.sort(interfaces, new Comparator<InterfaceInfoVo>() {
			@Override
			public int compare(InterfaceInfoVo o1, InterfaceInfoVo o2) {
				return o2.getLevel() - o1.getLevel();
			}
		});

		List<InterfaceInfoVo> result = new ArrayList<InterfaceInfoVo>();

		InterfaceInfoVo iiv = interfaces.get(0);

		result.add(iiv);

		for (int i = 1; i < interfaces.size(); i++) {
			InterfaceInfoVo vo = interfaces.get(i);
			if (vo.getLevel() >= iiv.getLevel()) {
				result.add(vo);
			} else {
				break;
			}
		}

		return result;
	}

	private boolean isSupportRechargeAgents(InterfaceInfoVo iiv, String carrier, String type, String province, String city) {
		boolean result = true;
		// �жϴ����Ƿ�֧�ִ˳�ֵ��ʽ
		List<InterfaceSupportVo> supports = iiv.getSupports();
		if (!supports.isEmpty()) {
			for (InterfaceSupportVo isv : supports) {
				// �����֧��״̬
				if (SUPPORT.equals(isv.getState())) {
					// �ж��Ƿ�����Ӫ������
					if (!isv.getOperatorsSet().isEmpty() && !isv.getOperatorsSet().contains(carrier)) {
						result = false;
						break;
					}
					// �ж��Ƿ���ڿ����������
					if (!isv.getTypeSet().isEmpty() && !isv.getTypeSet().contains(type)) {
						result = false;
						break;
					}
					// �ж��Ƿ����ʡ������
					if (!isv.getProvinceSet().isEmpty() && !isv.getProvinceSet().contains(province)) {
						result = false;
						break;
					}
					// �ж��Ƿ���ڳ�������
					if (!isv.getCitySet().isEmpty() && !isv.getCitySet().contains(city)) {
						result = false;
						break;
					}

				} else if (NONSUPPORT.equals(isv.getState())) {
					// ������г�ֵ����֧��
					if (isv.getOperatorsSet().isEmpty() && isv.getTypeSet().isEmpty() && isv.getProvinceSet().isEmpty() && isv.getCitySet().isEmpty()) {
						result = false;
						break;
					}
					if (!isv.getOperatorsSet().isEmpty() && isv.getOperatorsSet().contains(carrier)) {
						result = false;
						break;
					}
					// �ж��Ƿ���ڿ����������
					if (!isv.getTypeSet().isEmpty() && isv.getTypeSet().contains(type)) {
						result = false;
						break;
					}
					// �ж��Ƿ����ʡ������
					if (!isv.getProvinceSet().isEmpty() && isv.getProvinceSet().contains(province)) {
						result = false;
						break;
					}
					// �ж��Ƿ���ڳ�������
					if (!isv.getCitySet().isEmpty() && isv.getCitySet().contains(city)) {
						result = false;
						break;
					}
				}
			}
		}
		return result;
	}
}
