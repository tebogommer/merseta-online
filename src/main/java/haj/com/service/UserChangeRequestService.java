package haj.com.service;

import java.util.Hashtable;
import java.util.List;

import haj.com.dao.UserChangeRequestDAO;
import haj.com.entity.Address;
import haj.com.entity.AddressChange;
import haj.com.entity.UserChangeRequest;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class UserChangeRequestService extends AbstractService {
	/** The dao. */
	private UserChangeRequestDAO dao = new UserChangeRequestDAO();

	/**
	 * Get all UserChangeRequest
 	 * @author TechFinium 
 	 * @see   UserChangeRequest
 	 * @return a list of {@link haj.com.entity.UserChangeRequest}
	 * @throws Exception the exception
 	 */
	public List<UserChangeRequest> allUserChangeRequest() throws Exception {
	  	return dao.allUserChangeRequest();
	}


	/**
	 * Create or update UserChangeRequest.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     UserChangeRequest
	 */
	public void create(UserChangeRequest entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  UserChangeRequest.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UserChangeRequest
	 */
	public void update(UserChangeRequest entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  UserChangeRequest.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UserChangeRequest
	 */
	public void delete(UserChangeRequest entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UserChangeRequest}
	 * @throws Exception the exception
	 * @see    UserChangeRequest
	 */
	public UserChangeRequest findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find UserChangeRequest by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.UserChangeRequest}
	 * @throws Exception the exception
	 * @see    UserChangeRequest
	 */
	public List<UserChangeRequest> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	
	public List<UserChangeRequest> findByTargetKeyAndTargetClass(String targetClass,Long targetKey) throws Exception {
	 	return dao.findByTargetKeyAndTargetClass(targetClass, targetKey);
	}
	
	
	public UserChangeRequest findLatestByTargetKeyAndTargetClass(String targetClass,Long targetKey) throws Exception {
	 	return dao.findLatestByTargetKeyAndTargetClass(targetClass, targetKey);
	}
	
	
	/**
	 * Lazy load UserChangeRequest
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.UserChangeRequest}
	 * @throws Exception the exception
	 */
	public List<UserChangeRequest> allUserChangeRequest(int first, int pageSize) throws Exception {
		return dao.allUserChangeRequest(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of UserChangeRequest for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the UserChangeRequest
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(UserChangeRequest.class);
	}
	
    /**
     * Lazy load UserChangeRequest with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 UserChangeRequest class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.UserChangeRequest} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<UserChangeRequest> allUserChangeRequest(Class<UserChangeRequest> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<UserChangeRequest>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of UserChangeRequest for lazy load with filters
     * @author TechFinium 
     * @param entity UserChangeRequest class
     * @param filters the filters
     * @return Number of rows in the UserChangeRequest entity
     * @throws Exception the exception     
     */	
	public int count(Class<UserChangeRequest> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public List<UserChangeRequest> findByUserAndStatus(Long id,ApprovalEnum approvalStatus) throws Exception {
		return dao.findByUserAndStatus(id,approvalStatus);
	}
	
	public void copyUserChangeRequestToUser(UserChangeRequest from, Users to) {
		to.setFirstName(from.getFirstName());
		
		if(from.getMiddleName() != null) {
			to.setMiddleName(from.getMiddleName());
		}
		
		to.setLastName(from.getLastName());
		to.setGender(from.getGender());
		to.setEquity(from.getEquity());
		
		if(from.getTelNumber() != null) {
			to.setTelNumber(from.getTelNumber());
		}
		
		to.setCellNumber(from.getCellNumber());
		to.setEmail(from.getEmail());
		
		if(from.getRsaIDNumber() != null) {
			to.setRsaIDNumber(from.getRsaIDNumber());
		}
		
		if(from.getPassportNumber() != null) {
			to.setPassportNumber(from.getPassportNumber());
		}
		
		to.setNationality(from.getNationality());
		
		to.setUrbanRuralEnum(to.getUrbanRuralEnum());
	}
	
	public void copyUserToUserChangeRequest(Users from, UserChangeRequest to) {
		to.setFirstName(from.getFirstName());
		
		if(from.getMiddleName() != null) {
			to.setMiddleName(from.getMiddleName());
		}
		
		to.setLastName(from.getLastName());
		to.setGender(from.getGender());
		to.setEquity(from.getEquity());
		
		if(from.getTelNumber() != null) {
			to.setTelNumber(from.getTelNumber());
		}
		
		to.setCellNumber(from.getCellNumber());
		to.setEmail(from.getEmail());
		
		if(from.getRsaIDNumber() != null) {
			to.setRsaIDNumber(from.getRsaIDNumber());
		}
		
		if(from.getPassportNumber() != null) {
			to.setPassportNumber(from.getPassportNumber());
		}
		
		to.setNationality(from.getNationality());
		
		to.setUrbanRuralEnum(to.getUrbanRuralEnum());
	}
}
