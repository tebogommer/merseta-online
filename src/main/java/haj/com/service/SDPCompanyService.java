package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.SDPCompanyDAO;
import haj.com.entity.Company;
import haj.com.entity.SDPCompany;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.RelationshipToCompanyService;
import haj.com.service.lookup.ScopeOfResponsibilityService;

public class SDPCompanyService extends AbstractService {
	
	/** The dao. */
	private SDPCompanyDAO dao = new SDPCompanyDAO();
	
	/* Service levels */
	private TrainingProviderApplicationService trainingProviderApplicationService = null;
	private CompanyService companyService = null;
	private TrainingSiteService trainingSiteService = null;
	
	/**
	 * Get all SDPCompany
 	 * @author TechFinium 
 	 * @see   SDPCompany
 	 * @return a list of {@link haj.com.entity.SDPCompany}
	 * @throws Exception the exception
 	 */
	public List<SDPCompany> allSDPCompany() throws Exception {
	  	return dao.allSDPCompany();
	}


	/**
	 * Create or update SDPCompany.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SDPCompany
	 */
	public void create(SDPCompany entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SDPCompany.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SDPCompany
	 */
	public void update(SDPCompany entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SDPCompany.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SDPCompany
	 */
	public void delete(SDPCompany entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SDPCompany}
	 * @throws Exception the exception
	 * @see    SDPCompany
	 */
	public SDPCompany findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SDPCompany by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SDPCompany}
	 * @throws Exception the exception
	 * @see    SDPCompany
	 */
	public List<SDPCompany> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SDPCompany
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SDPCompany}
	 * @throws Exception the exception
	 */
	public List<SDPCompany> allSDPCompany(int first, int pageSize) throws Exception {
		return dao.allSDPCompany(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SDPCompany for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SDPCompany
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SDPCompany.class);
	}
	
    /**
     * Lazy load SDPCompany with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SDPCompany class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SDPCompany} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SDPCompany> allSDPCompany(Class<SDPCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<SDPCompany>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SDPCompany> allSdpLinkedToCompany(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long companyId) throws Exception {
		String hql = "select o from SDPCompany o where o.company.id = :companyId";
		filters.put("companyId", companyId);
		return (List<SDPCompany>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllSdpLinkedToCompany(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SDPCompany o where o.company.id = :companyId";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<SDPCompany> allSdpLinkedToHoldingCompany(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long companyId) throws Exception {
		String hql = "select o from SDPCompany o where o.company.id = :companyId and o.trainingSite is null ";
		filters.put("companyId", companyId);
		return (List<SDPCompany>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllSdpLinkedToHoldingCompany(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SDPCompany o where o.company.id = :companyId and o.trainingSite is null ";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<SDPCompany> allSdpLinkedToTrainingSite(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long trainingSiteId) throws Exception {
		String hql = "select o from SDPCompany o where o.trainingSite.id = :trainingSiteId ";
		filters.put("trainingSiteId", trainingSiteId);
		return (List<SDPCompany>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllSdpLinkedToTrainingSite(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SDPCompany o where o.trainingSite.id = :trainingSiteId ";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<SDPCompany> allSdpLinkedToHoldingCompanySdpView(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long companyId) throws Exception {
		String hql = "select o from SDPCompany o where o.company.id = :companyId and o.trainingSite is null ";
		filters.put("companyId", companyId);
		return resolveSdpInfoSdpView((List<SDPCompany>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllSdpLinkedToHoldingCompanySdpView(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SDPCompany o where o.company.id = :companyId and o.trainingSite is null ";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<SDPCompany> allSdpLinkedToTrainingSiteSdpView(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long trainingSiteId) throws Exception {
		String hql = "select o from SDPCompany o where o.trainingSite.id = :trainingSiteId ";
		filters.put("trainingSiteId", trainingSiteId);
		return resolveSdpInfoSdpView((List<SDPCompany>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllSdpLinkedToTrainingSiteSdpView(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SDPCompany o where o.trainingSite.id = :trainingSiteId ";
		return dao.countWhere(filters, hql);
	}
	
	public List<SDPCompany> resolveSdpInfoSdpView(List<SDPCompany> entityList) throws Exception {
		for (SDPCompany entity : entityList) {
			resolveSdpInfoSdpView(entity);
		}
		return entityList;
	}
	
	public SDPCompany resolveSdpInfoSdpView(SDPCompany entity) throws Exception {
		if (entity != null && entity.getId() != null) {
			if (entity.getSdpType() != null && entity.getSdpType().getId() != null && !entity.getSdpType().getId().equals(HAJConstants.PRIMARY_SDP_ID) && entity.getApprovalStatus() == ApprovalEnum.Approved) {
				entity.setCanAction(true);
			}else {
				entity.setCanAction(false);
			}
		}
		return entity;
	} 
	
	public List<SDPCompany> resolveSdpInfoRelationshipScope(List<SDPCompany> entityList) throws Exception {
		for (SDPCompany entity : entityList) {
			resolveSdpInfoRelationshipScope(entity);
		}
		return entityList;
	}
	
	public SDPCompany resolveSdpInfoRelationshipScope(SDPCompany entity) throws Exception {
		if (entity != null && entity.getId() != null) {
			if (entity.getRelationshipToCompany() != null && entity.getRelationshipToCompany().getId() != null) {
				entity.setRelationshipToCompany(RelationshipToCompanyService.instance().findByKey(entity.getRelationshipToCompany().getId()));
			}
			if (entity.getScopeOfResponsibility() != null && entity.getScopeOfResponsibility().getId() != null) {
				entity.setScopeOfResponsibility(ScopeOfResponsibilityService.instance().findByKey(entity.getScopeOfResponsibility().getId()));
			}
		}
		return entity;
	}
	
    /**
     * Get count of SDPCompany for lazy load with filters
     * @author TechFinium 
     * @param entity SDPCompany class
     * @param filters the filters
     * @return Number of rows in the SDPCompany entity
     * @throws Exception the exception     
     */	
	public int count(Class<SDPCompany> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public Integer countBySdpIdAndCompanyId(Long sdpId, Long companyId) throws Exception {
		return dao.countBySdpIdAndCompanyId(sdpId, companyId);
	}
	
	public List<SDPCompany> findBySdpIdAndCompanyId(Long sdpId, Long companyId) throws Exception {
		return dao.findBySdpIdAndCompanyId(sdpId, companyId);
	}
	
	public SDPCompany findBySdpIdAndCompanyIdReturnOneResult(Long sdpId, Long companyId) throws Exception {
		List<SDPCompany> list = findBySdpIdAndCompanyId(sdpId, companyId);
		if (list.isEmpty()) {
			return null;
		}else {
			return list.get(0);
		}
	}
	
	public List<SDPCompany> findAllSdpByCompanyId(Long companyId) throws Exception {
		return dao.findAllSdpByCompanyId(companyId);
	}
	
	public List<Users> findAllSdpByCompanyIdReturnUsers(Long companyId) throws Exception {
		return dao.findAllSdpByCompanyIdReturnUsers(companyId);
	}
	
	public List<SDPCompany> findBySdpIdCompanyIdAndTrainingSiteId(Long sdpId, Long companyId, Long trainingSiteId) throws Exception {
		return dao.findBySdpIdCompanyIdAndTrainingSiteId(sdpId, companyId, trainingSiteId);
	}
	
	public SDPCompany findBySdpIdCompanyIdAndTrainingSiteIdReturnOneResult(Long sdpId, Long companyId, Long trainingSiteId) throws Exception {
		List<SDPCompany> list = findBySdpIdCompanyIdAndTrainingSiteId(sdpId, companyId, trainingSiteId);
		if (list.isEmpty()) {
			return null;
		}else {
			return list.get(0);
		}
	}
	
	public List<SDPCompany> findAllSdpByCompanyIdAndTrainingSiteId(Long companyId, Long trainingSiteId) throws Exception {
		return dao.findAllSdpByCompanyIdAndTrainingSiteId(companyId, trainingSiteId);
	}
	
	public List<Users> findAllSdpByCompanyIdAndTrainingSiteIdReturnUsers(Long companyId, Long trainingSiteId) throws Exception {
		return dao.findAllSdpByCompanyIdAndTrainingSiteIdReturnUsers(companyId, trainingSiteId);
	}
	
	/* Used to Identify SDP users for Tasks by training provider application  */
	public List<Users> locateSdpUsersByProviderApplication(TrainingProviderApplication trainingProviderApplication, Company company) throws Exception{
		List<Users> sdpUsers = new ArrayList<>();
		if (trainingProviderApplication != null && trainingProviderApplication.getId() != null) {
			if (trainingProviderApplicationService == null) {
				trainingProviderApplicationService = new TrainingProviderApplicationService();
			}
			TrainingProviderApplication tpa =  trainingProviderApplicationService.findByKey(trainingProviderApplication.getId());
			boolean useHostingCompanySdp = true;
			if (tpa.getTrainingSite() != null && tpa.getTrainingSite().getId() != null) {
				List<Users> sdpTsUsers = findAllSdpByCompanyIdAndTrainingSiteIdReturnUsers(tpa.getCompany().getId(), tpa.getTrainingSite().getId());
				if (!sdpTsUsers.isEmpty()) {
					useHostingCompanySdp = false;
					sdpUsers.addAll(sdpTsUsers);
				}
				sdpTsUsers = null;
			}
			if (useHostingCompanySdp) {
				sdpUsers.addAll(findAllSdpByCompanyIdReturnUsers(tpa.getCompany().getId()));
			}
		} else if (company != null && company.getId() != null) {
			// fail safe to revert to old way of locating SDPs
			
		}
		return sdpUsers;
	}

	public Integer countSdpsByHoldingCompany(Long companyId) throws Exception {
		return dao.countSdpsByHoldingCompany(companyId);
	}
	
	public Integer countSdpTypeByHoldingCompany(Long companyId, Long sdpTypeId) throws Exception {
		return dao.countSdpTypeByHoldingCompany(companyId, sdpTypeId);
	}
	
	public SDPCompany findBySdpTypeByHoldingCompany(Long companyId, Long sdpTypeId) throws Exception {
		return dao.findBySdpTypeByHoldingCompany(companyId, sdpTypeId);
	}
	
	public Integer countSdpTypeByHoldingCompanyAndApprovalStatus(Long companyId, Long sdpTypeId, ApprovalEnum approvalEnum) throws Exception {
		return dao.countSdpTypeByHoldingCompanyAndApprovalStatus(companyId, sdpTypeId, approvalEnum);
	}
	
	public Integer countSdpsByHoldingCompanyApprovalStatusAndNotSdpType(Long companyId, Long sdpTypeId, ApprovalEnum approvalEnum) throws Exception {
		return dao.countSdpsByHoldingCompanyApprovalStatusAndNotSdpType(companyId, sdpTypeId, approvalEnum);
	}
	
	public Integer countSdpsByTrainingSiteId(Long trainingSiteId) throws Exception {
		return dao.countSdpsByTrainingSiteId(trainingSiteId);
	}
	
	public Integer countSdpTypeByTrainingSiteId(Long trainingSiteId, Long sdpTypeId) throws Exception {
		return dao.countSdpTypeByTrainingSiteId(trainingSiteId, sdpTypeId);
	}
	
	public SDPCompany findBySdpTypeByTrainingSiteId(Long trainingSiteId, Long sdpTypeId) throws Exception {
		return dao.findBySdpTypeByTrainingSiteId(trainingSiteId, sdpTypeId);
	}
	
	public Integer countSdpTypeByTrainingSiteIdAndApproval(Long trainingSiteId, Long sdpTypeId, ApprovalEnum approvalEnum) throws Exception {
		return dao.countSdpTypeByTrainingSiteIdAndApproval(trainingSiteId, sdpTypeId, approvalEnum);
	}
	
	public Integer countSdpsByTrainingSiteIdApprovalAndNotSdpType(Long trainingSiteId, Long sdpTypeId, ApprovalEnum approvalEnum) throws Exception {
		return dao.countSdpsByTrainingSiteIdApprovalAndNotSdpType(trainingSiteId, sdpTypeId, approvalEnum);
	}
	
	public Integer countUserAssignedByHoldingCompany(Long companyId, Long userId) throws Exception {
		return dao.countUserAssignedByHoldingCompany(companyId, userId);
	}
	
	public Integer countUserAssignedByTrainingSiteId(Long trainingSiteId, Long userId) throws Exception {
		return dao.countUserAssignedByTrainingSiteId(trainingSiteId, userId);
	}
	
	public Integer countByUserIdAndApprovalStatus(ApprovalEnum approvalEnum, Long userId) throws Exception {
		return dao.countByUserIdAndApprovalStatus(approvalEnum, userId);
	}
	
	public Integer countByUserId(Long userId) throws Exception {
		return dao.countByUserId(userId);
	}
	
	public Integer countBySdpIdCompanyIdAndNotId(Long sdpId, Long companyId, Long id) throws Exception {
		return dao.countBySdpIdCompanyIdAndNotId(sdpId, companyId, id);
	}
	
	public Integer countBySdpIdSdpTypeIdCompanyIdAndNotId(Long sdpId, Long companyId, Long sdpTypeId, Long id) throws Exception {
		return dao.countBySdpIdSdpTypeIdCompanyIdAndNotId(sdpId, companyId, sdpTypeId, id);
	}
	
	public Integer validiationNumberOfContactsAssignedByProviderApplication(TrainingProviderApplication tpa) throws Exception{
		int count = 0;
		if (tpa.getTrainingSite() != null && tpa.getTrainingSite().getId() != null) {
			int amount = countSdpsByTrainingSiteIdApprovalAndNotSdpType(tpa.getTrainingSite().getId(), HAJConstants.PRIMARY_SDP_ID, ApprovalEnum.Approved);
			if (amount > 0) {
				count = amount;
			}
		} else {
			int amount = countSdpsByHoldingCompanyApprovalStatusAndNotSdpType(tpa.getCompany().getId(), HAJConstants.PRIMARY_SDP_ID, ApprovalEnum.Approved);
			if (amount > 0) {
				count = amount;
			}
		}
		
		return count;
	}
	
}