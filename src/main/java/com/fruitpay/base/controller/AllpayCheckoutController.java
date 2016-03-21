package com.fruitpay.base.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.model.AllpayOrder;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.service.CheckoutService;
import com.fruitpay.base.service.CustomerOrderService;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.utils.AssertUtils;
import com.fruitpay.comm.utils.ConfigMap;
import com.fruitpay.comm.utils.HttpUtil;

import AllPay.Payment.Integration.AllInOne;
import AllPay.Payment.Integration.Decimal;
import AllPay.Payment.Integration.DeviceType;
import AllPay.Payment.Integration.ExtraPaymentInfo;
import AllPay.Payment.Integration.HttpMethod;
import AllPay.Payment.Integration.Item;
import AllPay.Payment.Integration.PaymentMethod;
import AllPay.Payment.Integration.PaymentMethodItem;
import AllPay.Payment.Integration.PeriodType;
import javassist.NotFoundException;

@Controller
@RequestMapping("allpayCtrl")
public class AllpayCheckoutController {
	
	
	
	private final Logger logger = Logger.getLogger(this.getClass());
	private String ORDER_RESULT_URL = null;
	private String SHOW_ORDER_SUCCESS_POST_URL = null;
	private String PERIOD_RETURN_URL = null;
	private String SHOW_ORDER_URL = null;
	private String SHOW_ORDER_SUCCESS_URL = null;
	
	private final String TEST_SERVICE_URL = "http://payment-stage.allpay.com.tw/Cashier/AioCheckOut";
	private final String TEST_HASH_KEY = "5294y06JbISpM5x9";
	private final String TEST_HASH_IV = "v77hoKGq4kWxNNIS";
	private final String TEST_MERCHANT_ID = "2000132";
	
	private final String SERVICE_URL = "https://payment.allpay.com.tw/Cashier/AioCheckOut";
	private final String HASH_KEY = "gXLhKG6NYxJOosdd";
	private final String HASH_IV = "z2G1om86YlJ35noj";
	private final String MERCHANT_ID = "1074763";
	
	private final Integer MAX_EXCUTE_TIME = 999;
	
	@Inject
	ConfigMap configMap;
	@Inject
	private HttpUtil httpUtil;
	@Inject
	private CheckoutService checkoutService;
	@Inject
	private StaticDataService staticDataService;
	@Inject
	CustomerOrderService customerOrderService;
	
	@PostConstruct
	public void init() throws Exception{
		ORDER_RESULT_URL = httpUtil.getDomainURL() + "allpayCtrl/callback";
		SHOW_ORDER_SUCCESS_POST_URL = httpUtil.getDomainURL() + "allpayCtrl/orderSuccess";
		PERIOD_RETURN_URL = httpUtil.getDomainURL() + "allpayCtrl/schduleCallback";
		SHOW_ORDER_URL = httpUtil.getDomainURL() + "app/user/orders";
		SHOW_ORDER_SUCCESS_URL = httpUtil.getDomainURL() + "app/thanks";
	}
	
	
	@RequestMapping(value = "/orderSuccess/{id}", method = RequestMethod.POST)
	public void checkoutCreditCardSuccess( 
			@PathVariable String id, HttpServletRequest request, HttpServletResponse response){

		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.setHeader("Location", SHOW_ORDER_SUCCESS_URL + "?id=" + id);
	}
	
	@RequestMapping(value = "/callback", method = RequestMethod.POST)
	public void allpayCallback(HttpServletRequest request, HttpServletResponse response)
		throws Exception{
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=utf-8");

		List<String> enErrors = new ArrayList<String>();
		String szMerchantTradeNo = "";
		String szPaymentDate = "";
		String szRtnCode = "";
		String szRtnMsg = "";
		try {
			out = response.getWriter();
			AllInOne oPayment = new AllInOne();
			oPayment.HashKey = "true".equals(configMap.get(ConfigMap.Key.DEBUG_MODE)) ? TEST_HASH_KEY : HASH_KEY;
			oPayment.HashIV = "true".equals(configMap.get(ConfigMap.Key.DEBUG_MODE)) ? TEST_HASH_IV : HASH_IV;

			Hashtable<String, String> htFeedback = new Hashtable<String, String>();
			enErrors.addAll(oPayment.CheckOutFeedback(htFeedback, request));

			Set<String> key = htFeedback.keySet();
			String name[] = key.toArray(new String[key.size()]);
			/* 支付後的回傳的基本參數 */
			String szMerchantID = "";
			String szPaymentType = "";
			String szPaymentTypeChargeFee = "";
			String szSimulatePaid = "";
			String szTradeAmt = "";
			String szTradeDate = "";
			String szTradeNo = ""; /* 使用定期定額交易時，回傳的額外參數 */
			String szPeriodType = "";
			String szFrequency = "";
			String szExecTimes = "";
			String szAmount = "";
			String szGwsr = "";
			String szProcessDate = "";
			String szAuthCode = "";
			String szFirstAuthAmount = "";
			String szTotalSuccessTimes = ""; // 取得資料
			for (int i = 0; i < name.length; i++) { /* 支付後的回傳的基本參數 */
				if (name[i].equals("MerchantID"))
					szMerchantID = htFeedback.get(name[i]);
				else if (name[i].equals("MerchantTradeNo"))
					szMerchantTradeNo = htFeedback.get(name[i]);
				else if (name[i].equals("PaymentDate"))
					szPaymentDate = htFeedback.get(name[i]);
				else if (name[i].equals("PaymentType"))
					szPaymentType = htFeedback.get(name[i]);
				else if (name[i].equals("PaymentTypeChargeFee"))
					szPaymentTypeChargeFee = htFeedback.get(name[i]);
				else if (name[i].equals("RtnCode"))
					szRtnCode = htFeedback.get(name[i]);
				else if (name[i].equals("RtnMsg"))
					szRtnMsg = htFeedback.get(name[i]);
				else if (name[i].equals("SimulatePaid"))
					szSimulatePaid = htFeedback.get(name[i]);
				else if (name[i].equals("TradeAmt"))
					szTradeAmt = htFeedback.get(name[i]);
				else if (name[i].equals("TradeDate"))
					szTradeDate = htFeedback.get(name[i]);
				else if (name[i].equals("TradeNo"))
					szTradeNo = htFeedback.get(name[i]);
				else if (name[i].equals("PeriodType"))
					szPeriodType = htFeedback.get(name[i]);
				else if (name[i].equals("Frequency"))
					szFrequency = htFeedback.get(name[i]);
				else if (name[i].equals("ExecTimes"))
					szExecTimes = htFeedback.get(name[i]);
				else if (name[i].equals("Amount"))
					szAmount = htFeedback.get(name[i]);
				else if (name[i].equals("Gwsr"))
					szGwsr = htFeedback.get(name[i]);
				else if (name[i].equals("ProcessDate"))
					szProcessDate = htFeedback.get(name[i]);
				else if (name[i].equals("AuthCode"))
					szAuthCode = htFeedback.get(name[i]);
				else if (name[i].equals("FirstAuthAmount"))
					szFirstAuthAmount = htFeedback.get(name[i]);
				else if (name[i].equals("TotalSuccessTimes"))
					szTotalSuccessTimes = htFeedback.get(name[i]);
			}
			System.out.println("MerchantID = " + szMerchantID);
			System.out.println("MerchantTradeNo = " + szMerchantTradeNo);
			System.out.println("PaymentDate = " + szPaymentDate);
			System.out.println("PaymentType = " + szPaymentType);
			System.out.println("PaymentTypeChargeFee = " + szPaymentTypeChargeFee);
			System.out.println("RtnCode = " + szRtnCode);
			System.out.println("RtnMsg = " + szRtnMsg);
			System.out.println("SimulatePaid = " + szSimulatePaid);
			System.out.println("TradeAmt = " + szTradeAmt);
			System.out.println("TradeDate = " + szTradeDate);
			System.out
					.println("TradeNo = " + szTradeNo); /* 使用定期定額交易時，回傳的額外參數 */
			System.out.println("PeriodType = " + szPeriodType);
			System.out.println("Frequency = " + szFrequency);
			System.out.println("ExecTimes = " + szExecTimes);
			System.out.println("Amount = " + szAmount);
			System.out.println("Gwsr = " + szGwsr);
			System.out.println("ProcessDate = " + szProcessDate);
			System.out.println("AuthCode = " + szAuthCode);
			System.out.println("FirstAuthAmount = " + szFirstAuthAmount);
			System.out.println("TotalSuccessTimes = " + szTotalSuccessTimes);
			out.println("");
		} catch (Exception e) {
			enErrors.add(e.getMessage());
		} finally { 
			
			AllpayOrder allpayOrder = new AllpayOrder();
			allpayOrder.setRtnCode(szRtnCode);
			allpayOrder.setRtnMessage(szRtnMsg);
			allpayOrder.setPaymentDate(szPaymentDate);
			// 回覆成功訊息。
			if (enErrors.size() == 0 && "1".equals(allpayOrder.getRtnCode())) {
				checkoutService.updateOrderStatus(Integer.valueOf(szMerchantTradeNo), OrderStatus.CreditPaySuccessful, allpayOrder);
				out.println("1|OK"); 
			// 回覆錯誤訊息。
			} else {
				checkoutService.updateOrderStatus(Integer.valueOf(szMerchantTradeNo), OrderStatus.CreditPayFailed, allpayOrder);
				String outMessage = "";
				if(enErrors.isEmpty()){
					outMessage = allpayOrder.getRtnMessage();
				}else{
					outMessage = enErrors.toString();
				}					
				out.println("0|" + outMessage);
			}

		}
		
	}
	
	@RequestMapping(value = "/schduleCallback", method = RequestMethod.POST)
	public void allpaySchduleCallback(
			HttpServletRequest request, HttpServletResponse response){
		
		logger.debug("here");
		
	}
	
	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public void allpayCheckout(
			@RequestParam("orderId") Integer orderId,
			@RequestParam("price") Decimal price,
			@RequestParam("programId") Integer programId,
			@RequestParam("duration") Integer duration,
			HttpServletRequest request, HttpServletResponse response){
		
		if(AssertUtils.anyIsEmpty(String.valueOf(orderId), String.valueOf(price), 
				String.valueOf(programId), String.valueOf(duration))){
			logger.error("OrderId not found");
			return;
		}	
		
		String programName = staticDataService.getOrderProgram(programId).getProgramName();
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=utf-8");
		 
		/*
		 * 產生訂單的範例程式碼。
		 */
		List<String> enErrors = new ArrayList<String>();
		try {
			out = response.getWriter(); 
			AllInOne oPayment = new AllInOne();
			/* 服務參數 */
			oPayment.ServiceMethod = HttpMethod.HttpPOST;
			oPayment.ServiceURL = "true".equals(configMap.get(ConfigMap.Key.DEBUG_MODE)) ? TEST_SERVICE_URL : SERVICE_URL;
			oPayment.HashKey = "true".equals(configMap.get(ConfigMap.Key.DEBUG_MODE)) ? TEST_HASH_KEY : HASH_KEY;
			oPayment.HashIV = "true".equals(configMap.get(ConfigMap.Key.DEBUG_MODE)) ? TEST_HASH_IV : HASH_IV;
			oPayment.MerchantID = "true".equals(configMap.get(ConfigMap.Key.DEBUG_MODE)) ? TEST_MERCHANT_ID : MERCHANT_ID;
			/* 基本參數 */
			oPayment.Send.ReturnURL = ORDER_RESULT_URL;
			oPayment.Send.ClientBackURL = SHOW_ORDER_SUCCESS_POST_URL + "/" + orderId;
			oPayment.Send.OrderResultURL = SHOW_ORDER_SUCCESS_POST_URL + "/" + orderId;
			oPayment.Send.MerchantTradeNo = String.valueOf((int)(orderId));
			oPayment.Send.MerchantTradeDate = new Date();// "<<您此筆訂單的交易時間>>"
			oPayment.Send.TotalAmount = price;
			oPayment.Send.TradeDesc = programName;
			oPayment.Send.ChoosePayment = PaymentMethod.Credit;
			oPayment.Send.Remark = "";
			oPayment.Send.ChooseSubPayment = PaymentMethodItem.None;
			oPayment.Send.NeedExtraPaidInfo = ExtraPaymentInfo.No;
			oPayment.Send.DeviceSource = DeviceType.PC;
			// 加入選購商品資料。
			Item a1 = new Item();
			a1.Name = programName;
			a1.Price = price;
			a1.Currency = "NTD";
			a1.Quantity = 1;// <<數量>>
			a1.URL = "";
			oPayment.Send.Items.add(a1);
			/* Credit 定期定額延伸參數 */
			oPayment.SendExtend.PeriodAmount = price;
			oPayment.SendExtend.PeriodType = PeriodType.Day;
			oPayment.SendExtend.Frequency = duration;// "<<執行頻率>>";
			oPayment.SendExtend.ExecTimes = MAX_EXCUTE_TIME;// "<<執行次數>>";
			oPayment.SendExtend.PeriodReturnURL = PERIOD_RETURN_URL;
			/* 產生訂單 */
			enErrors.addAll(oPayment.CheckOut(response.getWriter()));
			/* 產生產生訂單 Html Code 的方法 */
			StringBuilder szHtml = new StringBuilder();
			enErrors.addAll(oPayment.CheckOutString(szHtml));
		} catch (Exception e) {
			// 例外錯誤處理。
			enErrors.add(e.getMessage());
		} finally {
			// 顯示錯誤訊息。
			if (enErrors.size() > 0)
				out.print(enErrors);
		}
	}
}
