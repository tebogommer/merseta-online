package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.SDPCompany;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class SDPCompanyDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SDPCompany
 	 * @author TechFinium 
 	 * @see    SDPCompany
 	 * @return a list of {@link haj.com.entity.SDPCompany}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SDPCompany> allSDPCompany() throws Exception {
		return (List<SDPCompany>)super.getList("select o from SDPCompany o");
	}

	/**
	 * Get all SDPCompany between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    SDPCompany
 	 * @return a list of {@link haj.com.entity.SDPCompany}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SDPCompany> allSDPCompany(Long from, int noRows) throws Exception {
	 	String hql = "select o from SDPCompany o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SDPCompany>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    SDPCompany
 	 * @return a {@link haj.com.entity.SDPCompany}
 	 * @throws Exception global exception
 	 */
	public SDPCompany findByKey(Long id) throws Exception {
	 	String hql = "select o from SDPCompany o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SDPCompany)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SDPCompany by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SDPCompany
  	 * @return a list of {@link haj.com.entity.SDPCompany}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SDPCompany> findByName(String description) throws Exception {
	 	String hql = "select o from SDPCompany o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SDPCompany>)super.getList(hql, parameters);
	}
	
	public Integer countBySdpIdAndCompanyId(Long sdpId, Long companyId) throws Exception {
	 	String hql = "select count(o) from SDPCompany o where o.sdp.id = :sdpId and o.company.id = :companyId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("sdpId", sdpId);
	    parameters.put("companyId", companyId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<SDPCompany> findBySdpIdAndCompanyId(Long sdpId, Long companyId) throws Exception {
	 	String hql = "select o from SDPCompany o where o.sdp.id = :sdpId and o.company.id = :companyId and o.trainingSite.id is null" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("sdpId", sdpId);
	    parameters.put("companyId", companyId);
		return (List<SDPCompany>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SDPCompany> findAllSdpByCompanyId(Long companyId) throws Exception {
	 	String hql = "select o from SDPCompany o where o.sdp.id = :sdpId and o.company.id = :companyId and o.trainingSite.id is null" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
		return (List<SDPCompany>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findAllSdpByCompanyIdReturnUsers(Long companyId) throws Exception {
	 	String hql = "select o.sdp from SDPCompany o where o.company.id = :companyId and o.trainingSite.id is null and o.sdp is not null" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
		return (List<Users>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SDPCompany> findBySdpIdCompanyIdAndTrainingSiteId(Long sdpId, Long companyId, Long trainingSiteId) throws Exception {
	 	String hql = "select o from SDPCompany o where o.sdp.id = :sdpId and o.company.id = :companyId and o.trainingSite.id = :trainingSiteId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("sdpId", sdpId);
	    parameters.put("companyId", companyId);
	    parameters.put("trainingSiteId", trainingSiteId);
		return (List<SDPCompany>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SDPCompany> findAllSdpByCompanyIdAndTrainingSiteId(Long companyId, Long trainingSiteId) throws Exception {
	 	String hql = "select o from SDPCompany o where o.company.id = :companyId and o.trainingSite.id = :trainingSiteId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
	    parameters.put("trainingSiteId", trainingSiteId);
		return (List<SDPCompany>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findAllSdpByCompanyIdAndTrainingSiteIdReturnUsers(Long companyId, Long trainingSiteId) throws Exception {
	 	String hql = "select o.sdp from SDPCompany o where o.company.id = :companyId and o.trainingSite.id = :trainingSiteId and o.sdp is not null" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
	    parameters.put("trainingSiteId", trainingSiteId);
		return (List<Users>)super.getList(hql, parameters);
	}
	
	/*
	 *  Checks for registration
	 */
	
	// check designation used
	public Integer countSdpsByHoldingCompany(Long companyId) throws Exception {
	 	String hql = "select count(o) from SDPCompany o where o.company.id = :companyId and o.trainingSite is null  " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public SDPCompany findBySdpTypeByHoldingCompany(Long companyId, Long sdpTypeId) throws Exception {
	 	String hql = "select o from SDPCompany o where o.company.id = :companyId and o.trainingSite is null and o.sdpType.id = :sdpTypeId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
	    parameters.put("sdpTypeId", sdpTypeId);
		return (SDPCompany)super.getUniqueResult(hql, parameters);
	}
	
	public Integer countSdpTypeByHoldingCompany(Long companyId, Long sdpTypeId) throws Exception {
	 	String hql = "select count(o) from SDPCompany o where o.company.id = :companyId and o.trainingSite is null and o.sdpType.id = :sdpTypeId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
	    parameters.put("sdpTypeId", sdpTypeId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countSdpTypeByHoldingCompanyAndApprovalStatus(Long companyId, Long sdpTypeId, ApprovalEnum approvalEnum) throws Exception {
	 	String hql = "select count(o) from SDPCompany o where o.company.id = :companyId and o.trainingSite is null and o.sdpType.id = :sdpTypeId and o.approvalStatus = :approvalEnum" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
	    parameters.put("sdpTypeId", sdpTypeId);
	    parameters.put("approvalEnum", approvalEnum);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countSdpsByHoldingCompanyApprovalStatusAndNotSdpType(Long companyId, Long sdpTypeId, ApprovalEnum approvalEnum) throws Exception {
	 	String hql = "select count(o) from SDPCompany o where o.company.id = :companyId and o.trainingSite is null and o.sdpType.id <> :sdpTypeId and o.approvalStatus = :approvalEnum" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
	    parameters.put("sdpTypeId", sdpTypeId);
	    parameters.put("approvalEnum", approvalEnum);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countSdpsByTrainingSiteId(Long trainingSiteId) throws Exception {
	 	String hql = "select count(o) from SDPCompany o where o.trainingSite.id = :trainingSiteId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("trainingSiteId", trainingSiteId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countSdpTypeByTrainingSiteId(Long trainingSiteId, Long sdpTypeId) throws Exception {
	 	String hql = "select count(o) from SDPCompany o where o.trainingSite.id = :trainingSiteId and o.sdpType.id = :sdpTypeId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("trainingSiteId", trainingSiteId);
	    parameters.put("sdpTypeId", sdpTypeId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public SDPCompany findBySdpTypeByTrainingSiteId(Long trainingSiteId, Long sdpTypeId) throws Exception {
	 	String hql = "select o from SDPCompany o where o.trainingSite.id = :trainingSiteId and o.sdpType.id = :sdpTypeId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("trainingSiteId", trainingSiteId);
	    parameters.put("sdpTypeId", sdpTypeId);
		return (SDPCompany)super.getUniqueResult(hql, parameters);
	}
	
	public Integer countSdpTypeByTrainingSiteIdAndApproval(Long trainingSiteId, Long sdpTypeId, ApprovalEnum approvalEnum) throws Exception {
	 	String hql = "select count(o) from SDPCompany o where o.trainingSite.id = :trainingSiteId and o.sdpType.id = :sdpTypeId and o.approvalStatus = :approvalEnum" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("trainingSiteId", trainingSiteId);
	    parameters.put("sdpTypeId", sdpTypeId);
	    parameters.put("approvalEnum", approvalEnum);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	

	public Integer countSdpsByTrainingSiteIdApprovalAndNotSdpType(Long trainingSiteId, Long sdpTypeId, ApprovalEnum approvalEnum) throws Exception {
	 	String hql = "select count(o) from SDPCompany o where o.trainingSite.id = :trainingSiteId and o.sdpType.id <> :sdpTypeId and o.approvalStatus = :approvalEnum" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("trainingSiteId", trainingSiteId);
	    parameters.put("sdpTypeId", sdpTypeId);
	    parameters.put("approvalEnum", approvalEnum);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	
	
	// check user already assigned
	public Integer countUserAssignedByHoldingCompany(Long companyId, Long userId) throws Exception {
	 	String hql = "select count(o) from SDPCompany o where o.company.id = :companyId and o.trainingSite is null and o.sdp.id = :userId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
	    parameters.put("userId", userId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countUserAssignedByTrainingSiteId(Long trainingSiteId, Long userId) throws Exception {
	 	String hql = "select count(o) from SDPCompany o where o.trainingSite.id = :trainingSiteId and o.sdp.id = :userId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("trainingSiteId", trainingSiteId);
	    parameters.put("userId", userId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countByUserIdAndApprovalStatus(ApprovalEnum approvalEnum, Long userId) throws Exception {
	 	String hql = "select count(o) from SDPCompany o where and o.approvalStatus = :approvalEnum and o.sdp.id = :userId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("approvalEnum", approvalEnum);
	    parameters.put("userId", userId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countByUserId(Long userId) throws Exception {
	 	String hql = "select count(o) from SDPCompany o where o.sdp.id = :userId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userId", userId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countBySdpIdSdpTypeIdCompanyIdAndNotId(Long sdpId, Long companyId, Long sdpTypeId, Long id) throws Exception {
	 	String hql = "select count(o) from SDPCompany o where o.sdp.id = :sdpId and o.company.id = :companyId and o.sdpType.id = :sdpTypeId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("sdpId", sdpId);
	    parameters.put("companyId", companyId);
	    parameters.put("sdpTypeId", sdpTypeId);
	    if (id != null) {
			hql += " and o.id <> :id";
			parameters.put("id", id);
		}
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countBySdpIdCompanyIdAndNotId(Long sdpId, Long companyId, Long id) throws Exception {
	 	String hql = "select count(o) from SDPCompany o where o.sdp.id = :sdpId and o.company.id = :companyId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("sdpId", sdpId);
	    parameters.put("companyId", companyId);
	    if (id != null) {
			hql += " and o.id <> :id";
			parameters.put("id", id);
		}
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
}