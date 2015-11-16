package com.fruitpay.base.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.service.CheckoutService;

import AllPay.Payment.Integration.AllInOne;
import AllPay.Payment.Integration.Decimal;
import AllPay.Payment.Integration.DeviceType;
import AllPay.Payment.Integration.ExtraPaymentInfo;
import AllPay.Payment.Integration.HttpMethod;
import AllPay.Payment.Integration.Item;
import AllPay.Payment.Integration.PaymentMethod;
import AllPay.Payment.Integration.PaymentMethodItem;
import AllPay.Payment.Integration.PeriodType;

@Controller
@RequestMapping("allpayCtrl")
public class AllpayCheckoutController {
	
	private boolean DEBUG_MODE = true;
	private final Logger logger = Logger.getLogger(this.getClass());
	private final String ORDER_RESULT_URL = "/allpayCtrl/callback";
	private final String PERIOD_RETURN_URL = "/allpayCtrl/schduleCallback";
	private final String SHOW_ORDER_URL = "#/index/user/orders";
	
	private final String TEST_SERVICE_URL = "http://payment-stage.allpay.com.tw/Cashier/AioCheckOut";
	private final String TEST_HASH_KEY = "5294y06JbISpM5x9";
	private final String TEST_HASH_IV = "v77hoKGq4kWxNNIS";
	private final String TEST_MERCHANT_ID = "2000132";
	
	private final String SERVICE_URL = "http://payment.allpay.com.tw/Cashier/AioCheckOut";
	private final String HASH_KEY = "gXLhKG6NYxJOosdd";
	private final String HASH_IV = "z2G1om86YlJ35noj";
	private final String MERCHANT_ID = "1074763";
	
	private final Integer MAX_EXCUTE_TIME = 999;
	
	
	
	@Inject
	private CheckoutService checkoutService;
	
	@RequestMapping(value = "/callbackTest", method = RequestMethod.POST)
	public void callbackTest( 
			HttpServletRequest request, HttpServletResponse response){
		Integer orderId = 11;
		checkoutService.updateOrderStatus(orderId, OrderStatus.CreditPaySuccessful);
		
	}
	
	@RequestMapping(value = "/callback", method = RequestMethod.POST)
	public void allpayCallback( 
			HttpServletRequest request, HttpServletResponse response){
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);

		List<String> enErrors = new ArrayList<String>();
		try {
			out = response.getWriter();
			AllInOne oPayment = new AllInOne();
			oPayment.HashKey = DEBUG_MODE ? TEST_HASH_KEY : HASH_KEY;
			oPayment.HashIV = DEBUG_MODE ? TEST_HASH_IV : HASH_IV;

			Hashtable<String, String> htFeedback = new Hashtable<String, String>();
			enErrors.addAll(oPayment.CheckOutFeedback(htFeedback, request));

			Set<String> key = htFeedback.keySet();
			String name[] = key.toArray(new String[key.size()]);
			/* 支付後的回傳的基本參數 */
			String szMerchantID = "";
			String szMerchantTradeNo = "";
			String szPaymentDate = "";
			String szPaymentType = "";
			String szPaymentTypeChargeFee = "";
			String szRtnCode = "";
			String szRtnMsg = "";
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
		} finally { // 回覆成功訊息。
			if (enErrors.size() == 0) {
				response.setHeader("Location", getDomainURL(request) + SHOW_ORDER_URL);
				out.println("1|OK"); // 回覆錯誤訊息。
			} else {
				response.setHeader("Location", getDomainURL(request));
				out.println("0|" + enErrors);
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
			HttpServletRequest request, HttpServletResponse response){
		
		String index = getDomainURL(request);
		
		if(orderId == null){
			logger.error("OrderId not found");
			return;
		}
		logger.debug(orderId);
		
		CustomerOrder customerOrder = checkoutService.getCustomerOrder(orderId);
		
		if(customerOrder == null){
			logger.error("Cannot find customerOrder by OrderId " + orderId);
			return;
		}
			
		
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
			oPayment.ServiceURL = DEBUG_MODE ? TEST_SERVICE_URL : SERVICE_URL;
			oPayment.HashKey = DEBUG_MODE ? TEST_HASH_KEY : HASH_KEY;
			oPayment.HashIV = DEBUG_MODE ? TEST_HASH_IV : HASH_IV;
			oPayment.MerchantID = DEBUG_MODE ? TEST_MERCHANT_ID : MERCHANT_ID;
			/* 基本參數 */
			oPayment.Send.ReturnURL = index;
			oPayment.Send.ClientBackURL = index + ORDER_RESULT_URL;
			oPayment.Send.OrderResultURL = index + ORDER_RESULT_URL;
			oPayment.Send.MerchantTradeNo = String.valueOf((int)(Math.random() * 1000000));
			oPayment.Send.MerchantTradeDate = new Date();// "<<您此筆訂單的交易時間>>"
			oPayment.Send.TotalAmount = new Decimal(String.valueOf(customerOrder.getOrderProgram().getPrice()));
			oPayment.Send.TradeDesc = "no";
			oPayment.Send.ChoosePayment = PaymentMethod.Credit;
			oPayment.Send.Remark = "";
			oPayment.Send.ChooseSubPayment = PaymentMethodItem.None;
			oPayment.Send.NeedExtraPaidInfo = ExtraPaymentInfo.No;
			oPayment.Send.DeviceSource = DeviceType.PC;
			// 加入選購商品資料。
			Item a1 = new Item();
			a1.Name = customerOrder.getOrderProgram().getProgramName();
			a1.Price = new Decimal(String.valueOf(customerOrder.getOrderProgram().getPrice()));
			a1.Currency = "NTD";
			a1.Quantity = 1;// <<數量>>
			a1.URL = "";
			oPayment.Send.Items.add(a1);
			/* Credit 定期定額延伸參數 */
			oPayment.SendExtend.PeriodAmount = new Decimal(String.valueOf(customerOrder.getOrderProgram().getPrice()));
			oPayment.SendExtend.PeriodType = PeriodType.Day;
			oPayment.SendExtend.Frequency = customerOrder.getOrderProgram().getShipmentPeriod().getDuration();// "<<執行頻率>>";
			oPayment.SendExtend.ExecTimes = MAX_EXCUTE_TIME;// "<<執行次數>>";
			oPayment.SendExtend.PeriodReturnURL = index + PERIOD_RETURN_URL;
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
	
	private String getDomainURL(HttpServletRequest request){
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String contextPath = request.getContextPath();  // includes leading forward slash
		String resultPath = scheme + "://" + serverName + ":" + serverPort + contextPath;
		return resultPath;
	}
}
