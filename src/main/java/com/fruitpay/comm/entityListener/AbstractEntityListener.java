package com.fruitpay.comm.entityListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.auth.AuthenticationException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.model.AbstractEntity;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.FieldChangeRecord;
import com.fruitpay.base.service.CustomerService;
import com.fruitpay.base.service.FieldChangeRecordService;
import com.fruitpay.comm.auth.SysContent;
import com.fruitpay.comm.session.FPSessionUtil;
import com.fruitpay.comm.session.model.FPSessionInfo;
import com.fruitpay.comm.utils.NeedRecordHelper;
import com.fruitpay.comm.utils.SpringApplicationContext;

@Component
public class AbstractEntityListener {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@PrePersist
	protected void onCreate(AbstractEntity abstractEntity) {
		
		Customer user = getCurrentUser(abstractEntity);
		abstractEntity.setCreateUser(user);
		abstractEntity.setUpdateUser(user);
		abstractEntity.setCreateDate(new Date());
		abstractEntity.setUpdateDate(new Date());

	}
	
	//有可能遇到同一次交易裡面,就要回復狀態,因此每次插入資料到資料庫後,再做一次double check
	@PostPersist
	protected void safePreviousRecordDoubleCheck(AbstractEntity abstractEntity) throws IllegalAccessException, IllegalArgumentException, ClassNotFoundException {
		if(abstractEntity.getPreviousRecords() == null) {
			List<FieldChangeRecord> records = NeedRecordHelper.getFieldChangeRecords(abstractEntity);
			abstractEntity.setPreviousRecords(records); 
		}
		
	}
	
	@PostLoad
	protected void safePreviousRecord(AbstractEntity abstractEntity) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException{
		List<FieldChangeRecord> records = NeedRecordHelper.getFieldChangeRecords(abstractEntity);
		abstractEntity.setPreviousRecords(records); 
    }
	
	@PreUpdate
	@Transactional
	protected void onUpdate(AbstractEntity abstractEntity) {
		
		abstractEntity.setUpdateUser(getCurrentUser(abstractEntity));
		abstractEntity.setUpdateDate(new Date());
		
		try {
			List<FieldChangeRecord> fieldChangeRecords = NeedRecordHelper.getFieldChangeRecords(abstractEntity);
			
			if(fieldChangeRecords.isEmpty())
				return;
			
			List<FieldChangeRecord> previousRecords = abstractEntity.getPreviousRecords();
			
			previousRecords = filterList(fieldChangeRecords, previousRecords);
			
			if(previousRecords.isEmpty())
				return;
			
			addFieldChangeRecords(previousRecords);
			
		} catch (IllegalArgumentException | IllegalAccessException | ClassNotFoundException e) {
			logger.error(e);
		}
		
	} 
	
	private List<FieldChangeRecord> filterList(List<FieldChangeRecord> records, List<FieldChangeRecord> previousRecords){
		//若沒有舊資料, 不需要儲存
		if(previousRecords == null)
			return new ArrayList<FieldChangeRecord>();
		
		return previousRecords.stream().filter(record -> {
			//與新紀錄比較, 若與新紀錄相同則不需要新增
			for (Iterator<FieldChangeRecord> iterator = records.iterator(); iterator.hasNext();) {
				FieldChangeRecord fieldChangeRecord = iterator.next();
				logger.info(fieldChangeRecord);
				if(fieldChangeRecord.getFieldName().equals(record.getFieldName())
						&& fieldChangeRecord.getFieldValue().equals(record.getFieldValue())){
					return false;
				}
			}
			return true;
		}).collect(Collectors.toList());
	}
	
	private void addFieldChangeRecords( List<FieldChangeRecord> fieldChangeRecords){
		FieldChangeRecordService fieldChangeRecordService = SpringApplicationContext.getBean(FieldChangeRecordService.class);
		fieldChangeRecordService.add(fieldChangeRecords);
	}
	
	private Customer getCurrentUser(AbstractEntity abstractEntity){
		Customer user = null;
		CustomerService customerService = SpringApplicationContext.getBean(CustomerService.class);
		
		try{
			HttpServletRequest request = SysContent.getRequest();
			FPSessionInfo fpSessionInfo = FPSessionUtil.getFPsessionInfo(request);
			int userId = Integer.valueOf(fpSessionInfo.getUserId());
			user = customerService.findOne(userId);
		}catch(NullPointerException | AuthenticationException e){
			//可能是在註冊的時候才會遇到這個問題
			if (abstractEntity instanceof Customer) {
				user = (Customer)abstractEntity;
			}
		}
		
		return user;
	}

}
