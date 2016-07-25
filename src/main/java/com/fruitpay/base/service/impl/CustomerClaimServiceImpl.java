package com.fruitpay.base.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.comm.CommConst.VALID_FLAG;
import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.dao.CustomerClaimDAO;
import com.fruitpay.base.dao.CustomerClaimStatusDAO;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerClaim;
import com.fruitpay.base.model.CustomerClaimCondition;
import com.fruitpay.base.model.CustomerClaimStatus;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.ShipmentRecordDetail;
import com.fruitpay.base.service.CustomerClaimService;

@Service
public class CustomerClaimServiceImpl implements CustomerClaimService {

	@Inject
	private CustomerClaimDAO customerClaimDAO;
	@Inject
	private CustomerClaimStatusDAO customerClaimStatusDAO;
	
	@Override
	public Page<CustomerClaim> findAllByCondition(CustomerClaimCondition orderCondition, int page , int size) {
		List<Integer> customerClaimIds = customerClaimDAO.findByCondition(
				orderCondition.getName(), 
				orderCondition.getOrderId(), 
				orderCondition.getStartDate(), 
				orderCondition.getEndDate(), 
				orderCondition.getValidFlag(), 
				orderCondition.getReceiverCellphone(), 
				orderCondition.getEmail());
		
		Page<CustomerClaim> customerClaims = customerClaimDAO.findByClaimIdIn(customerClaimIds, new PageRequest(page, size, new Sort(Sort.Direction.DESC, "updateDate")));
		return customerClaims;
	}
	
	@Override
	@Transactional
	public CustomerClaim add(CustomerClaim customerClaim) {
		customerClaim.setValidFlag(VALID_FLAG.VALID.value());
		customerClaim = customerClaimDAO.save(customerClaim);
		return customerClaim;
	}
	
	@Override
	@Transactional
	public CustomerClaim update(CustomerClaim customerClaim) {
		CustomerClaim origin = customerClaimDAO.findOne(customerClaim.getClaimId());
		if(origin == null)
			throw new HttpServiceException(ReturnMessageEnum.Order.OrderNotFound.getReturnMessage());
		
		if(customerClaim.getCustomerClaimStatuses() == null) {
			customerClaim.setCustomerClaimStatuses(origin.getCustomerClaimStatuses());
		}
		BeanUtils.copyProperties(customerClaim, origin);
		origin = customerClaimDAO.save(origin);
		return origin;
	}

	@Override
	@Transactional
	public CustomerClaim invalidate(CustomerClaim customerClaim) {
		CustomerClaim origin = customerClaimDAO.findOne(customerClaim.getClaimId());
		origin.setValidFlag(VALID_FLAG.INVALID.value());
		return origin;
	}

	@Override
	public List<CustomerClaim> findByCustomerId(int customerId) {
		Customer customer = new Customer();
		customer.setCustomerId(customerId);
		List<CustomerClaim> customerClaims = customerClaimDAO.findByCustomer(customer);
		return customerClaims;
	}
	
	@Override
	public List<CustomerClaim> findByCustomerIdIncludingStatuses(int customerId) {
		Customer customer = new Customer();
		customer.setCustomerId(customerId);
		List<CustomerClaim> customerClaims = customerClaimDAO.findByCustomer(customer);
		customerClaims = this.setCustomerClaimStatuses(customerClaims);
		return customerClaims;
	}

	@Override
	public List<CustomerClaim> findByOrderId(int orderId) {
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setOrderId(orderId);
		List<CustomerClaim> customerClaims = customerClaimDAO.findByCustomerOrder(customerOrder);
		return customerClaims;
	}

	@Override
	public List<CustomerClaim> findByShipmentRecordDetailId(int shipmentRecordDetailId) {
		ShipmentRecordDetail shipmentRecordDetail = new ShipmentRecordDetail();
		shipmentRecordDetail.setShipmentRecordDetailId(shipmentRecordDetailId);
		List<CustomerClaim> customerClaims = customerClaimDAO.findByShipmentRecordDetail(shipmentRecordDetail);
		return customerClaims;
	}
	
	private List<CustomerClaim> setCustomerClaimStatuses(List<CustomerClaim> customerClaims) {
		List<CustomerClaimStatus> customerClaimStatuses = customerClaimStatusDAO.findByCustomerClaimIn(customerClaims);
		customerClaims = customerClaims.stream().map(customerClaim -> {
			List<CustomerClaimStatus> thisCustomerClaimStatuses = customerClaimStatuses.stream()
					.filter(customerClaimStatus -> {
						return customerClaimStatus.getCustomerClaim().getClaimId().equals(customerClaim.getClaimId());
					}).collect(Collectors.toList());
			
			customerClaim.setCustomerClaimStatuses(thisCustomerClaimStatuses);
			return customerClaim;
		}).collect(Collectors.toList());
		
		return customerClaims;
	}

}
