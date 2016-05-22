package com.fruitpay.util;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.mock.web.MockHttpSession;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import com.fruitpay.base.comm.CommConst.VALID_FLAG;
import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.model.Constant;
import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.Coupon;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderPlatform;
import com.fruitpay.base.model.OrderPreference;
import com.fruitpay.base.model.OrderProgram;
import com.fruitpay.base.model.PaymentMode;
import com.fruitpay.base.model.PostalCode;
import com.fruitpay.base.model.Product;
import com.fruitpay.base.model.Role;
import com.fruitpay.base.model.ShipmentChange;
import com.fruitpay.base.model.ShipmentDay;
import com.fruitpay.base.model.ShipmentPeriod;
import com.fruitpay.base.model.UserRole;
import com.fruitpay.base.service.LoginService;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.auth.Base64;
import com.fruitpay.comm.auth.LoginConst;
import com.fruitpay.comm.utils.AssertUtils;
import com.fruitpay.comm.utils.Md5Util;

@Component
public class DataUtil {
	
	@Inject
	StaticDataService staticDataService;
	@Inject
	LoginService loginService;
	
	public AuthenticationInfo getAuthInfo(MockMvc mockMvc) throws UnsupportedEncodingException, Exception{
		loginService.signup(this.getSignupCustomer());
		String uId = this.getUniqueUid(this.getSignupCustomer());
		MockHttpSession session = new MockHttpSession();
		
		HttpSession returnSession = mockMvc.perform(post("/loginCtrl/login")
				.session(session)
				.header(LoginConst.LOGIN_UID, uId)
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytesByGson(this.getSignupCustomer())))
	   		.andExpect(status().isOk())
	   		.andReturn()
	   		.getRequest()
	   		.getSession();
		
		String authentication = (String)returnSession.getAttribute(LoginConst.LOGIN_AUTHORIZATION);
		
		return new AuthenticationInfo(session, uId, authentication);
	}
	
	public String getUniqueUid(Customer customer){
		
		String key = AssertUtils.hasValue(customer.getFbId()) ? 
				customer.getFbId() : customer.getEmail() + ':' + customer.getPassword();
		return Md5Util.getMd5(key + ':' + new Date().getTime());
	}
	
	public UserRole getUserRole(Customer customer, Role role){
		
		UserRole userRole = new UserRole();	
		userRole.setCustomer(customer);
		userRole.setRole(role);
		
		return userRole;	
	}
	
	public Role getRole(){
		Constant roleType = staticDataService.getConstant(12);
		
		Role role = new Role();
		role.setRoleType(roleType.getConstOptions().get(0));
		return role;	
	}
	
	public ShipmentChange getShipmentChangeWithPulse(){
		Constant couponTypes = staticDataService.getConstant(11);
		return getShipmentChange(couponTypes.getConstOptions().get(0));
	}
	
	public ShipmentChange getShipmentChangeWithCancel(){
		Constant couponTypes = staticDataService.getConstant(11);
		return getShipmentChange(couponTypes.getConstOptions().get(1));
	}
	
	public ShipmentChange getShipmentChange(ConstantOption ShipmentChangeType){
		ShipmentChange shipmentChange = new ShipmentChange();
		shipmentChange.setShipmentChangeType(ShipmentChangeType);
		return shipmentChange;
	}
	
	public List<Coupon> getCouponList(){
		List<Coupon> coupons = new ArrayList<Coupon>();
		coupons.add(getByPercentageCoupon(10));
		return coupons;
	}
	
	
	public Coupon getByPercentageCoupon(int discount){
		Constant couponTypes = staticDataService.getConstant(8);
		Constant yesOrNo = staticDataService.getConstant(7);
		
		Coupon coupon = new Coupon();
		coupon.setCouponName("test");
		coupon.setCouponDesc("測試資料");
		coupon.setCouponType(couponTypes.getConstOptions().get(0));
		coupon.setExpiryDay(new Date());
		coupon.setValue(discount);
		coupon.setUsageIndividually(yesOrNo.getConstOptions().get(0));
		
		return coupon;
	}
	
	public Coupon getByAmountCoupon(int amount){
		Constant couponTypes = staticDataService.getConstant(8);
		Constant yesOrNo = staticDataService.getConstant(7);
		
		Coupon coupon = new Coupon();
		coupon.setCouponName("test");
		coupon.setCouponDesc("測試資料");
		coupon.setCouponType(couponTypes.getConstOptions().get(3));
		coupon.setExpiryDay(new Date());
		coupon.setValue(amount);
		coupon.setUsageIndividually(yesOrNo.getConstOptions().get(0));
		
		return coupon;
	}
	
	public Customer getSignupCustomer(){
		Customer customer = new Customer();
		customer.setEmail("u9734017@gmail.com");
		customer.setPassword("123456");
		customer.setFirstName("瑋志");
		customer.setLastName("徐");
		return customer;
	}
	
	public Customer getCheckoutCustomer(){
		PostalCode postalCode = staticDataService.getPostalCode(100);

		Customer customer = new Customer();
		customer.setEmail("u9734017@gmail.com");
		customer.setPassword("123456");
		customer.setFirstName("瑋志");
		customer.setLastName("徐");
		customer.setGender("M");
		customer.setPostalCode(postalCode);
		customer.setAddress("同安村西畔巷66弄40號");
		customer.setCellphone("0933370691");
		customer.setHousePhone("048238111");
		
		return customer;
	}
	
	public Customer getBackgroundCustomer(){
		PostalCode postalCode = staticDataService.getPostalCode(100);

		Customer customer = new Customer();
		customer.setEmail("u9734017@gmail.com");
		customer.setPassword("123456");
		customer.setFirstName("瑋志");
		customer.setLastName("徐");
		customer.setGender("M");
		customer.setPostalCode(postalCode);
		customer.setAddress("同安村西畔巷66弄40號");
		customer.setCellphone("0933370691");
		customer.setHousePhone("048238111");
		customer.setPassword("123456");
		
		return customer;
	}
	
	public CustomerOrder getCustomerOrder(){
		return getCustomerOrder(7);
	}
	
	public CustomerOrder getCustomerOrder(int shipmentDuration){
		
		PostalCode postalCode = staticDataService.getPostalCode(100);
		OrderPlatform orderPlatform = staticDataService.getOrderPlatform(1);
		OrderProgram orderProgram = staticDataService.getOrderProgram(1);
		PaymentMode paymentMode = staticDataService.getPaymentMode(1);
		ShipmentPeriod shipmentPeriod;
		if(shipmentDuration == 7){
			shipmentPeriod = staticDataService.getShipmentPeriod(1);
		}else{
			shipmentPeriod = staticDataService.getShipmentPeriod(2);
		}
			
		ShipmentDay shipmentDay = staticDataService.getShipmentDay(2);
		List<Product> products = staticDataService.getAllProducts();
		
		Constant receiveWays =  staticDataService.getConstant(1);
		ConstantOption receiveWay = receiveWays.getConstOptions().get(0);
		
		Constant shipmentTimes =  staticDataService.getConstant(2);
		ConstantOption shipmentTime = shipmentTimes.getConstOptions().get(0);
		
		Constant comingFroms =  staticDataService.getConstant(3);
		ConstantOption comingFrom = comingFroms.getConstOptions().get(0);
		
		Constant receiptWays =  staticDataService.getConstant(4);
		ConstantOption receiptWay = receiptWays.getConstOptions().get(0);
		
		Constant deliveryDays =  staticDataService.getConstant(6);
		ConstantOption deliveryDay = deliveryDays.getConstOptions().get(0);
		
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setOrderPlatform(orderPlatform);
		customerOrder.setOrderProgram(orderProgram);
		customerOrder.setReceiverLastName("徐");
		customerOrder.setReceiverFirstName("瑋志");
		customerOrder.setReceiverGender("M");
		customerOrder.setReceiverCellphone("0933370691");
		customerOrder.setReceiverHousePhone("048238111");
		customerOrder.setPostalCode(postalCode);
		customerOrder.setReceiverAddress("同安村西畔巷66弄40號");
		customerOrder.setReceiptTitle("抬頭");
		customerOrder.setReceiptVatNumber("123456789");
		customerOrder.setPaymentMode(paymentMode);
		customerOrder.setShipmentPeriod(shipmentPeriod);
		customerOrder.setShipmentDay(shipmentDay);
		customerOrder.setReceiveWay(receiveWay);
		customerOrder.setShipmentTime(shipmentTime);
		customerOrder.setComingFrom(comingFrom);
		customerOrder.setReceiptWay(receiptWay);
		customerOrder.setDeliveryDay(deliveryDay);
		customerOrder.setAllowForeignFruits("Y");
		customerOrder.setProgramNum(1);
		customerOrder.setOrderDate(Calendar.getInstance().getTime());
		customerOrder.setOrderStatus(staticDataService.getOrderStatus(OrderStatus.AlreadyCheckout.getStatus()));
		customerOrder.setRemark("test");
		
		int shippingCost = paymentMode.getPaymentExtraPrice();
		int totalPrice = orderProgram.getPrice() * customerOrder.getProgramNum() + shippingCost;	
		customerOrder.setShippingCost(shippingCost);
		customerOrder.setTotalPrice(totalPrice);
		
		List<OrderPreference> orderPreferences = new ArrayList<>();
		for (Iterator<Product> iterator = products.iterator(); iterator.hasNext();) {
			Product product = iterator.next();
			OrderPreference orderPreference = new OrderPreference();
			orderPreference.setProduct(product);
			orderPreference.setLikeDegree(Byte.parseByte("5"));
			orderPreference.setCustomerOrder(customerOrder);
			orderPreferences.add(orderPreference);
		}

		customerOrder.setOrderPreferences(orderPreferences);
		
		return customerOrder;
	}
	
	public Constant getConstant() {
		Constant constant = new Constant();
		constant.setConstName("TestContant");
		return constant;
	}
	
	public ConstantOption getConstantOption(Constant constant) {
		ConstantOption constantOption = new ConstantOption();
		constantOption.setConstant(constant);
		constantOption.setOrderNo(0);
		constantOption.setValidFlag(VALID_FLAG.VALID.value());
		constantOption.setOptionName("testOptionName");
		return constantOption;
	}

}
