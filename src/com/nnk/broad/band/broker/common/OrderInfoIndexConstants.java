package com.nnk.broad.band.broker.common;

public class OrderInfoIndexConstants {

//	public static final int APPNAME	=0;//	订单处理程序appname
//	public static final int COMMAND=1	;//	OrderTran 订单处理
	public static final int SESSION_ID = 0;		//	由接口生成会话ID，用以区分每次回话唯一性。
	public static final int AGENT_ID = 1;	//	代理商编号
	public static final int AGENT_ORDER_NUM = 2;		//	代理商订单号
	public static final int SYSTEM_ORDER_NUM = 3;		//	007卡订单号
	public static final int OPERATOR	= 4;//	运营商
	public static final int BROAD_BAND_TYPE = 5;		//	宽带类型
	public static final int PROVINCE = 6;		//	省份代码
	public static final int CITY	 = 7;		//城市代码
	public static final int RECHARGE_ACCOUNT = 8;	//	充值账号
	public static final int RECHARGE_AMOUNT = 9;		//	充值金额
	public static final int PROCESS_TIME = 10;		//订单受理时间
	public static final int CALLBACK_URL = 11;		// 订单回调URL
	public static final int TRADE_INFO = 12;		//	交易信息
	public static final int ORDER_STATUS = 13;	//	状态 0000，初始状态 1000，成功受理，2000受理失败，3000不确定状态
		
}
