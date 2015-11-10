package com.fruitpay.base.controller;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.CheckoutPostBean;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.ShipmentDay;
import com.fruitpay.base.service.CheckoutService;
import com.fruitpay.comm.model.ReturnData;

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
@RequestMapping("checkoutCtrl")
public class checkoutController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject
	private CheckoutService checkoutService;
	
	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public @ResponseBody ReturnData checkout(
			@RequestBody CheckoutPostBean checkoutPostBean,
			HttpServletRequest request, HttpServletResponse response){
		Customer customer = checkoutPostBean.getCustomer();
		CustomerOrder customerOrder = checkoutPostBean.getCustomerOrder();
		logger.debug(customer);
		logger.debug(customerOrder);
		
		PrintWriter out = null;
		
		if(customer == null || customerOrder == null)
			return ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage();
		
		customerOrder = checkoutService.checkoutOrder(customer, customerOrder);
		
		/*
		 * 產生訂單的範例程式碼。
		 */
		List<String> enErrors = new ArrayList<String>();
		try {
			out = response.getWriter(); 
			AllInOne oPayment = new AllInOne();
			/* 服務參數 */
			oPayment.ServiceMethod = HttpMethod.HttpPOST;
			oPayment.ServiceURL = "https://payment.allpay.com.tw/Cashier/QueryTradeInfo";
			oPayment.HashKey = "<<AllPay提供給您的Hash Key>>";
			oPayment.HashIV = "<<AllPay提供給您的Hash IV>>";
			oPayment.MerchantID = "1074763";
			/* 基本參數 */
			oPayment.Send.ReturnURL = "<<您要收到付款完成通知的伺服器端網址>>";
			oPayment.Send.ClientBackURL = "<<您要歐付寶返回按鈕導向的瀏覽器端網址>>";
			oPayment.Send.OrderResultURL = "<<您要收到付款完成通知的瀏覽器端網址>>";
			oPayment.Send.MerchantTradeNo = "1";
			oPayment.Send.MerchantTradeDate = new Date();// "<<您此筆訂單的交易時間>>"
			oPayment.Send.TotalAmount = new Decimal("499");
			oPayment.Send.TradeDesc = "<<您該筆訂單的描述>>";
			oPayment.Send.ChoosePayment = PaymentMethod.Alipay;
			oPayment.Send.Remark = "<<您要填寫的其他備註>>";
			oPayment.Send.ChooseSubPayment = PaymentMethodItem.None;
			oPayment.Send.NeedExtraPaidInfo = ExtraPaymentInfo.No;
			oPayment.Send.DeviceSource = DeviceType.PC;
			// 加入選購商品資料。
			Item a1 = new Item();
			a1.Name = "果物箱";
			a1.Price = new Decimal("499");
			a1.Currency = "<<幣別>>";
			a1.Quantity = 1;// <<數量>>
			a1.URL = "<<產品說明位址>>";
			oPayment.Send.Items.add(a1);
			/* Credit 定期定額延伸參數 */
			oPayment.SendExtend.PeriodAmount = new Decimal("499");
			oPayment.SendExtend.PeriodType = PeriodType.Month;
			oPayment.SendExtend.Frequency = 7;// "<<執行頻率>>";
			oPayment.SendExtend.ExecTimes = 999;// "<<執行次數>>";
			oPayment.SendExtend.PeriodReturnURL = "<<定期定額執行結果的回應位址>>";
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
		
		return null;
	}

}
