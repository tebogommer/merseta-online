package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;

import haj.com.bean.AssessorModeratorBean;
import haj.com.bean.LearnersMentorBean;
import haj.com.bean.LegacyDataReportBean;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.enums.AssessorModeratorTypeEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class AssessorModeratorApplicationDAO extends AbstractDAO  {
	
	String joins=" left join fetch o.jobTitle jt "
				+ "left join fetch o.ofoCodes ofo "
				+ "left join fetch o.user u "
				+ "left join fetch u.residentialAddress "
				+ "left join fetch u.postalAddress "
				+ "left join fetch u.disabled "
				+ "left join fetch u.gender "
				+ "left join fetch u.equity "
				+ "left join fetch u.disabilityStatus "
				+ "left join fetch u.nationality ";
	String usersjoin = "left join fetch o.ofoCodes ofo " 
			+ "left join fetch o.user u ";
	
	// AssessorModeratorTypeEnum assessorModeratorType
	
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all AssessorModeratorApplication
 	 * @author TechFinium 
 	 * @see    AssessorModeratorApplication
 	 * @return a list of {@link haj.com.entity.AssessorModeratorApplication}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> allAssessorModeratorApplication() throws Exception {
		return (List<AssessorModeratorApplication>)super.getList("select o from AssessorModeratorApplication o");
	}

	/**
	 * Get all AssessorModeratorApplication between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    AssessorModeratorApplication
 	 * @return a list of {@link haj.com.entity.AssessorModeratorApplication}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> allAssessorModeratorApplication(Long from, int noRows) throws Exception {
	 	String hql = "select o from AssessorModeratorApplication o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<AssessorModeratorApplication>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    AssessorModeratorApplication
 	 * @return a {@link haj.com.entity.AssessorModeratorApplication}
 	 * @throws Exception global exception
 	 */
	public AssessorModeratorApplication findByKey(Long id) throws Exception {
	 	String hql = "select o from AssessorModeratorApplication o"+joins+"where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (AssessorModeratorApplication)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find object by primary user ID and accreditation number
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    AssessorModeratorApplication
 	 * @return a {@link haj.com.entity.AssessorModeratorApplication}
 	 * @throws Exception global exception
 	 */
	public AssessorModeratorApplication findByAccreditation(String accreditation,Long userID) throws Exception {
	 	String hql = "select o from AssessorModeratorApplication o "+joins+" where o.certificateNumber = :accreditation and o.user.id = :userID " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("accreditation", accreditation);
	    parameters.put("userID", userID);
		return (AssessorModeratorApplication)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find AssessorModeratorApplication by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    AssessorModeratorApplication
  	 * @return a list of {@link haj.com.entity.AssessorModeratorApplication}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> findByName(String description) throws Exception {
	 	String hql = "select o from AssessorModeratorApplication o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<AssessorModeratorApplication>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> findByCerticateNumber(String description) throws Exception {
	 	String hql = "select o from AssessorModeratorApplication o where o.certificateNumber like  :description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<AssessorModeratorApplication>)super.getList(hql, parameters);
	}
	
	/**
	 * Find AssessorModeratorApplication by status
 	 * @author TechFinium 
 	 * @param ApprovalEnum the status 
 	 * @see    AssessorModeratorApplication
  	 * @return a list of {@link haj.com.entity.AssessorModeratorApplication}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> findByStatus(ApprovalEnum status) throws Exception {
	 	String hql = "select o from AssessorModeratorApplication o "+joins+" where o.status =:status order by o.createDate " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("status", status);
		return (List<AssessorModeratorApplication>)super.getList(hql, parameters);
	}
	

	public List<AssessorModeratorApplication> findByUserAndAppType(Long userId,AssessorModeratorAppTypeEnum applicationType)throws Exception {	
		//String hql = "select o from AssessorModeratorApplication o where o.user.id =:userId and  o.applicationType =:applicationType and o.status <>:status " ;
		String hql = "select o from AssessorModeratorApplication o where o.user.id =:userId and  o.applicationType =:applicationType and o.finalRejected <>:finalRejected " ;
		    Map<String, Object> parameters = new Hashtable<String, Object>();
		    parameters.put("userId", userId);
		    parameters.put("applicationType", applicationType);
		    parameters.put("finalRejected",true);
		    //parameters.put("status", ApprovalEnum.Rejected);
			return (List<AssessorModeratorApplication>)super.getList(hql, parameters);
		
	}
	
	public List<AssessorModeratorApplication> findByUser(Long userId)throws Exception {	
		
		String hql = "select o from AssessorModeratorApplication o where o.user.id =:userId " ;
		    Map<String, Object> parameters = new Hashtable<String, Object>();
		    parameters.put("userId", userId);
			return (List<AssessorModeratorApplication>)super.getList(hql, parameters);
		
	}
	
	public List<AssessorModeratorApplication> findByUser(Long userId,Boolean finalRejected)throws Exception {	
		
		String hql = "select o from AssessorModeratorApplication o where o.user.id =:userId and o.finalRejected =:finalRejected " ;
		    Map<String, Object> parameters = new Hashtable<String, Object>();
		    parameters.put("userId", userId);
		    parameters.put("finalRejected", finalRejected);
			return (List<AssessorModeratorApplication>)super.getList(hql, parameters);
		
	}
	
	/**
	 * Find Active AssessorModeratorApplication for user
 	 * @author TechFinium 
 	 * @param Users the Users 
 	 * @see    AssessorModeratorApplication
  	 * @return a list of {@link haj.com.entity.AssessorModeratorApplication}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> findByUserForValidation(Users user) throws Exception {
		ApprovalEnum status=ApprovalEnum.Rejected;
	 	String hql = "select o from AssessorModeratorApplication o where o.user.id = :userID and o.status <> :status order by o.createDate " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userID", user.getId());
	    parameters.put("status", status);
		return (List<AssessorModeratorApplication>)super.getList(hql, parameters);
	}
	
	/**
	 * Find Last Approved AssessorModeratorApplication
 	 * @author TechFinium 
 	 * @param ApprovalEnum the status 
 	 * @see    AssessorModeratorApplication
  	 * @return AssessorModeratorApplication
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public AssessorModeratorApplication findLastApproved() throws Exception {
		ApprovalEnum status=ApprovalEnum.Approved;
	 	String hql = "select o from AssessorModeratorApplication o "+joins+" where o.status =:status order by o.createDate desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("status", status);
		return (AssessorModeratorApplication) super.getList(hql, parameters);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> findListLastApproved() throws Exception {
		ApprovalEnum status=ApprovalEnum.Approved;
	 	String hql = "select o from AssessorModeratorApplication o "+joins+" where o.status =:status order by o.createDate desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("status", status);
		return (List<AssessorModeratorApplication>) super.getList(hql, parameters);
	}
	
	/**
	 * Find Approved AssessorModeratorApplication for user
 	 * @author TechFinium 
 	 * @param Users the Users 
 	 * @see    AssessorModeratorApplication
  	 * @return a list of {@link haj.com.entity.AssessorModeratorApplication}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> findByApprovedUserApplications(Users user) throws Exception {
		ApprovalEnum status=ApprovalEnum.Approved;
	 	String hql = "select o from AssessorModeratorApplication o where (o.user.id = :userID and o.status = :status) or (o.user.id = :userID and o.certificateNumber is not null) order by o.createDate " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userID", user.getId());
	    parameters.put("status", status);
		return (List<AssessorModeratorApplication>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> findApplicationByUserAndType(Users user, AssessorModeratorTypeEnum assessorModeratorType) throws Exception {
	 	String hql = "select o from AssessorModeratorApplication o where (o.user.id = :userID and o.status = :status and o.assessorModeratorType = :assessorModeratorType) or (o.user.id = :userID and o.certificateNumber is not null and o.assessorModeratorType = :assessorModeratorType) order by o.createDate " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userID", user.getId());
	    parameters.put("status", ApprovalEnum.Approved);
	    parameters.put("assessorModeratorType", assessorModeratorType);
		return (List<AssessorModeratorApplication>)super.getList(hql, parameters);
	}
	
	/**
	 * Find Approved AssessorModeratorApplication for user
 	 * @author TechFinium 
 	 * @param Users the Users 
 	 * @see    AssessorModeratorApplication
  	 * @return a list of {@link haj.com.entity.AssessorModeratorApplication}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> findByApprovedUserByType(Users user,AssessorModeratorAppTypeEnum applicationType) throws Exception {
		ApprovalEnum status=ApprovalEnum.Approved;
	 	String hql = "select o from AssessorModeratorApplication o where o.user.id = :userID and o.status = :status and applicationType = :applicationType order by o.createDate " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userID", user.getId());
	    parameters.put("status", status);
	    parameters.put("applicationType", applicationType);
		return (List<AssessorModeratorApplication>)super.getList(hql, parameters);
	}
	
	
	
	public List<?> sortAndFilterWhereAMInfo(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String hql) {
		if (filters != null) {
			// boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (!hql.contains(entry.getKey())) {
					if (entry.getKey().equals("firstName") || entry.getKey().equals("lastName") || entry.getKey().equals("rsaIDNumber") || entry.getKey().equals("passportNumber")) {
						hql += " and o.user." + entry.getKey() + " like " + " :" + hvar;
					}
					else if (entry.getKey().equals("description"))
					{
						hql += " and o.interventionType." + entry.getKey() + " like " + " :" + hvar;
					}
					else {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}
					
				}
			}
			filters = parms;
		}

		if (sortField != null) {
			switch (sortOrder) {
				case ASCENDING:
					if (sortField.equals("firstName") || sortField.equals("lastName") ||sortField.equals("rsaIDNumber")  ||sortField.equals("passportNumber")) {
						hql += " order by o.user." + sortField + " asc ";
					}
					else if (sortField.equals("description"))
					{
						hql += " order by o.interventionType." + sortField + " asc ";
					}
					else{
						hql += " order by o." + sortField + " asc ";
					}
					
					break;
				case DESCENDING:
					if (sortField.equals("firstName") || sortField.equals("lastName") ||sortField.equals("rsaIDNumber")  ||sortField.equals("passportNumber")) {
						hql += " order by o.user." + sortField + " desc ";
					}
					else if (sortField.equals("description"))
					{
						hql += " order by o.interventionType." + sortField + " desc ";
					}
					else{
						hql += " order by o." + sortField + " desc ";
					}
					
					break;
				default:
					break;
			}
		}
		//System.out.println(hql);
		return getList(hql, filters, startingAt, pageSize);
	}
	
	/**
	 * Count.
	 * @param filters
	 *            the filters
	 *
	 * @return the int
	 */
	public int countWhereAMInfo(Map<String, Object> filters, String hql) {
		if (filters != null) {
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (!hql.contains(entry.getKey())) {
					 if (entry.equals("firstName") || entry.equals("lastName") ||entry.equals("rsaIDNumber") ||entry.equals("passportNumber")){
						hql += " and o.user." + entry.getKey() + " like " + " :" + hvar;
					}
				    else if (entry.equals("description"))
					{
				    	hql += " and o.interventionType." + entry.getKey() + " like " + " :" + hvar;
					}
					else{
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}
					
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	public int countWhereUserAppInfo(Map<String, Object> filters, String hql) {
		/*
		 * if (filters != null) { Map<String, Object> parms = new HashMap<String,
		 * Object>(); String hvar = null; for (Entry<String, Object> entry :
		 * filters.entrySet()) { hvar = entry.getKey(); if (hvar.contains(".")) { hvar =
		 * hvar.replaceAll("\\.", ""); parms.put(hvar, entry.getValue()); } else {
		 * parms.put(entry.getKey(), entry.getValue()); } if
		 * (!entry.getKey().equalsIgnoreCase("userId") && !hql.contains(entry.getKey()))
		 * { hql += " and o." + entry.getKey() + " like " + " :" + hvar; } } filters =
		 * parms; }
		 */
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	public int countAllApprovedApplication(AssessorModeratorAppTypeEnum applicationType) throws Exception {
		Map<String, Object> filters=new HashMap<>();
		AssessorModeratorAppTypeEnum reReg=AssessorModeratorAppTypeEnum.AssessorReRegistration;
		AssessorModeratorAppTypeEnum extOfScope=AssessorModeratorAppTypeEnum.AssessorExtensionOfScope;
		if(applicationType==AssessorModeratorAppTypeEnum.NewModeratorRegistration){
			 reReg=AssessorModeratorAppTypeEnum.ModeratorReRegistration;
			 extOfScope=AssessorModeratorAppTypeEnum.ModeratorExtensionOfScope;
		}
		String hql="select count(o) from AssessorModeratorApplication o where o.certificateNumber <> null and (o.applicationType =:applicationType or o.applicationType =:reReg or o.applicationType =:extOfScope)";
		filters.put("applicationType", applicationType);
		filters.put("reReg", reReg);
		filters.put("extOfScope", extOfScope);
		return countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> findAllApprovedAssessorModeratorApplicationsByTypeListStatus(List<AssessorModeratorAppTypeEnum> applicationTypeList) throws Exception {
	 	String hql = "select o from AssessorModeratorApplication o where o.finalApproved = :booleanValue and o.status = :approvedStatus " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("approvedStatus", ApprovalEnum.Approved);
	    parameters.put("booleanValue", true);
	    if (!applicationTypeList.isEmpty()) {
			int counter = 1;
			hql += " and (  ";
			for (AssessorModeratorAppTypeEnum assessorModeratorAppTypeEnum : applicationTypeList) {
				if (counter == applicationTypeList.size()) {
					hql += " o.applicationType = :applicationType"+counter;
				} else {
					hql += " o.applicationType = :applicationType"+counter+" or ";
				}
				parameters.put("applicationType" + counter, assessorModeratorAppTypeEnum);
				counter++;
			}
			hql += ") ";
		}
		return (List<AssessorModeratorApplication>)super.getList(hql, parameters);
	}

	public AssessorModeratorApplication findByCerticateNumberOrIdNumber(String accreditation, Company trainingProvider, CompanyLearners companyLearners, Qualification qualification) {
		String hql = "select o from AssessorModeratorApplication o "+usersjoin+" where o.certificateNumber = :accreditation " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("accreditation", accreditation);
		return (AssessorModeratorApplication)super.getUniqueResult(hql, parameters);
	}
	
	public AssessorModeratorApplication findByUserForAssessorModeratorType(Long userId, AssessorModeratorTypeEnum assessorModeratorType) {
		String hql = "select o from AssessorModeratorApplication o "+usersjoin+" where o.user.id = :userId and o.assessorModeratorType = :assessorModeratorType " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("assessorModeratorType", assessorModeratorType);
	    parameters.put("userId", userId);
		return (AssessorModeratorApplication)super.getUniqueResult(hql, parameters);
	}
	
	public AssessorModeratorApplication findByUserForAssessorModeratorTypeAndApplicationType(Long userId, AssessorModeratorTypeEnum assessorModeratorType, List<AssessorModeratorAppTypeEnum> applicationTypeList) throws Exception {
		String hql = "select o from AssessorModeratorApplication o "+usersjoin+" where o.user.id = :userId and o.assessorModeratorType = :assessorModeratorType " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("assessorModeratorType", assessorModeratorType);
	    parameters.put("userId", userId);
	    if (!applicationTypeList.isEmpty()) {
			int counter = 1;
			hql += " and (  ";
			for (AssessorModeratorAppTypeEnum assessorModeratorAppTypeEnum : applicationTypeList) {
				hql += " o.applicationType = :applicationType"+counter;
				if (counter != applicationTypeList.size()) {
					hql += " or ";
				}
				parameters.put("applicationType" + counter, assessorModeratorAppTypeEnum);
				counter++;
			}
			hql += ") ";
		}
		return (AssessorModeratorApplication)super.getUniqueResult(hql, parameters);
	}
	
	public AssessorModeratorApplication findByCerticateNumberAssessorModeratorTypeAndApplicationType(String certificateNumber, AssessorModeratorTypeEnum assessorModeratorType, List<AssessorModeratorAppTypeEnum> applicationTypeList) throws Exception {
		String hql = "select o from AssessorModeratorApplication o where o.certificateNumber = :certificateNumber and o.assessorModeratorType = :assessorModeratorType " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("assessorModeratorType", assessorModeratorType);
	    parameters.put("certificateNumber", certificateNumber);
	    if (!applicationTypeList.isEmpty()) {
			int counter = 1;
			hql += " and (  ";
			for (AssessorModeratorAppTypeEnum assessorModeratorAppTypeEnum : applicationTypeList) {
				hql += " o.applicationType = :applicationType"+counter;
				if (counter != applicationTypeList.size()) {
					hql += " or ";
				}
				parameters.put("applicationType" + counter, assessorModeratorAppTypeEnum);
				counter++;
			}
			hql += ") ";
		}
		return (AssessorModeratorApplication)super.getUniqueResult(hql, parameters);
	}
	
	/*
	 * @SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> sortAndFilterWhereAMInfoApprovedAndByApplicationType(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, List<AssessorModeratorAppTypeEnum> applicationTypeList) throws Exception {
		String hql = "select o from AssessorModeratorApplication o where o.finalApproved = :booleanValue ";
		filters.put("booleanValue", true);
		if (!applicationTypeList.isEmpty()) {
			int counter = 1;
			hql += " and (  ";
			for (AssessorModeratorAppTypeEnum assessorModeratorAppTypeEnum : applicationTypeList) {
				if (counter == applicationTypeList.size()) {
					hql += " o.applicationType = :applicationType"+counter;
				} else {
					hql += " o.applicationType = :applicationType"+counter+" or ";
				}
				filters.put("applicationType" + counter, assessorModeratorAppTypeEnum);
				counter++;
			}
			hql += ") ";
		}
		return resolveUserDocs((List<AssessorModeratorApplication>) dao.sortAndFilterWhereAMInfo(first, pageSize, sortField, sortOrder, filters, hql));
	}
	 */
	
	public int countByCertificateNumber(String certificateNumber) throws Exception {
		String hql = "select count(o) from AssessorModeratorApplication o  where UPPER(o.certificateNumber) = :certificateNumber  " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("certificateNumber", certificateNumber.trim().toUpperCase());
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countByCertificateNumberExcludingId(String certificateNumber, Long id) throws Exception {
		String hql = "select count(o) from AssessorModeratorApplication o where UPPER(o.certificateNumber) = :certificateNumber and o.id <> :id " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("certificateNumber", certificateNumber.trim().toUpperCase());
	    parameters.put("id", id);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public AssessorModeratorApplication findByUserForAssessorModeratorTypeAndNotEqualStatus(Long userId, AssessorModeratorTypeEnum assessorModeratorType, ApprovalEnum approvalEnum, AssessorModeratorAppTypeEnum applicationType) throws Exception{
		String hql = "select o from AssessorModeratorApplication o "+usersjoin+" where o.user.id = :userId and o.assessorModeratorType = :assessorModeratorType and o.status <> :approvalEnum and o.applicationType = :applicationType" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("assessorModeratorType", assessorModeratorType);
	    parameters.put("userId", userId);
	    parameters.put("approvalEnum", approvalEnum);
	    parameters.put("applicationType", applicationType);
		return (AssessorModeratorApplication)super.getUniqueResult(hql, parameters);
	}

	public List<AssessorModeratorBean> populateReport() throws Exception {
		String sql = "select " + 
				" CAST(ama.start_date as CHAR(10)) as 'startDateString', " + 
				" CAST(ama.end_date as CHAR(10)) as 'endDateString', " + 
				" ama.certificate_number as 'registrationNumber', " + 
				" rcma.decision_number as 'decisionNumber', " + 
				" CAST(rcm.from_date_time as CHAR(10)) as 'reviewDateString', " + 
				
				" CASE " + 
				"	when ama.application_type = 0 then 'Assessor Registration' " + 
				"	when ama.application_type = 1 then 'Moderator Registration' " + 
				"	when ama.application_type = 2 then 'Assessor and Moderator Registration' " + 
				"	when ama.application_type = 3 then 'Assessor Extension of Scope' " + 
				"	when ama.application_type = 4 then 'Assessor Re-Registration' " + 
				"	when ama.application_type = 5 then 'Moderator Extension of Scope' " + 
				"	when ama.application_type = 6 then 'Moderator Re-Registration' " + 
				" end as 'type', " + 
				
				" CASE " + 
				"	when ama.type_assessor_moderator = 0 then 'merSETA' " + 
				"	when ama.type_assessor_moderator = 1 then 'TTC Assessor / Moderator' " + 
				" end as 'assModType', " + 
				
				" CASE " + 
				"	when ama.status = 0 then 'Approved' " + 
				"	when ama.status = 1 then 'Rejected' " + 
				"	when ama.status = 2 then 'Pending Manager Approval' " + 
				"	when ama.status = 3 then 'Pending Approval' " + 
				"	when ama.status = 4 then 'Pending Sign Off' " + 
				"	when ama.status = 5 then 'Completed' " + 
				"	when ama.status = 6 then 'Pending accept code of conduct' " + 
				"	when ama.status = 7 then 'Awaiting DHET'   " + 
				"	when ama.status = 8 then 'Pending Final Approval' " + 
				"	when ama.status = 9 then 'Withdrawn'   " + 
				"	when ama.status = 10 then 'N/A' " + 
				"	when ama.status = 11 then 'Recommended' " + 
				"	when ama.status = 12 then 'Appealed' " + 
				"	when ama.status = 13 then 'Pending Committee Approval' " + 
				"	when ama.status = 14 then 'Approved By ETQA Review Committee' " + 
				"	when ama.status = 15 then 'Rejected By ETQA Review Commitee' " + 
				"	when ama.status = 16 then 'Requested Higher Allocation' " + 
				"	when ama.status = 17 then 'Accepted MOA' " + 
				"	when ama.status = 18 then 'Requested Change' " + 
				"	when ama.status = 19 then 'Rejected By MANCO Review' " + 
				"	when ama.status = 20 then 'Rejected By Learner Review Committee' " + 
				"	when ama.status = 21 then 'Qualification Obtained' " + 
				"	when ama.status = 22 then 'Deactivated' " + 
				"	when ama.status = 23 then 'Project Terminated Manager Approval' " + 
				"	when ama.status = 24 then 'Suspend Project' " + 
				"	when ama.status = 25 then 'Project Terminated' " + 
				"	when ama.status = 26 then 'Pending Review Approval' " + 
				"	when ama.status = 27 then 'Uphold' " + 
				"	when ama.status = 28 then 'Pending Resubmission'   " + 
				"	when ama.status = 29 then 'Awaiting NAMB' " + 
				"	when ama.status = 30 then 'Pending Withdrawal' " + 
				"	when ama.status = 31 then 'Pending Inverstigation' " + 
				"	when ama.status = 32 then 'Pending Change Approval' " + 
				"	when ama.status = 33 then 'Not Competent' " + 
				" end as 'status', " + 
				
				" u.first_name as 'firstName', "  + 
				" u.middle_name as 'middleName', "  + 
				" u.last_name as 'lastName', "  +  
				" u.rsa_id_number as 'rsaIDNumber', " + 
				" u.passport_number as 'passportNumber' " + 
				" from " + 
				" assessor_moderator_application ama " +
				" left join users u on u.id = ama.users_id " +
				" left join review_committee_meeting rcm on rcm.id = ama.review_committee_meeting_id " +
				" left join Review_committee_meeting_agenda rcma on rcma.id = ama.review_committee_meeting_agenda_id "; 
		return (List<AssessorModeratorBean>) super.nativeSelectSqlList(sql, AssessorModeratorBean.class);
	}

	public List<AssessorModeratorBean> populateReport(AssessorModeratorTypeEnum assessorModeratorTypeEnum) throws Exception {
		String sql = "select " + 
				" CAST(ama.start_date as CHAR(20)) as 'startDateString', " + 
				" CAST(ama.end_date as CHAR(20)) as 'endDateString', " + 
				" ama.certificate_number as 'registrationNumber', " + 
				" rcma.decision_number as 'decisionNumber', " + 
				" CAST(rcm.from_date_time as CHAR(20)) as 'reviewDateString', " + 
				
				" CASE " + 
				"	when ama.application_type = 0 then 'Assessor Registration' " + 
				"	when ama.application_type = 1 then 'Moderator Registration' " + 
				"	when ama.application_type = 2 then 'Assessor and Moderator Registration' " + 
				"	when ama.application_type = 3 then 'Assessor Extension of Scope' " + 
				"	when ama.application_type = 4 then 'Assessor Re-Registration' " + 
				"	when ama.application_type = 5 then 'Moderator Extension of Scope' " + 
				"	when ama.application_type = 6 then 'Moderator Re-Registration' " + 
				" end as 'type', " + 
				
				" CASE " + 
				"	when ama.type_assessor_moderator = 0 then 'merSETA' " + 
				"	when ama.type_assessor_moderator = 1 then 'TTC Assessor / Moderator' " + 
				" end as 'assModType', " + 
				
				" CASE " + 
				"	when ama.status = 0 then 'Approved' " + 
				"	when ama.status = 1 then 'Rejected' " + 
				"	when ama.status = 2 then 'Pending Manager Approval' " + 
				"	when ama.status = 3 then 'Pending Approval' " + 
				"	when ama.status = 4 then 'Pending Sign Off' " + 
				"	when ama.status = 5 then 'Completed' " + 
				"	when ama.status = 6 then 'Pending accept code of conduct' " + 
				"	when ama.status = 7 then 'Awaiting DHET'   " + 
				"	when ama.status = 8 then 'Pending Final Approval' " + 
				"	when ama.status = 9 then 'Withdrawn'   " + 
				"	when ama.status = 10 then 'N/A' " + 
				"	when ama.status = 11 then 'Recommended' " + 
				"	when ama.status = 12 then 'Appealed' " + 
				"	when ama.status = 13 then 'Pending Committee Approval' " + 
				"	when ama.status = 14 then 'Approved By ETQA Review Committee' " + 
				"	when ama.status = 15 then 'Rejected By ETQA Review Commitee' " + 
				"	when ama.status = 16 then 'Requested Higher Allocation' " + 
				"	when ama.status = 17 then 'Accepted MOA' " + 
				"	when ama.status = 18 then 'Requested Change' " + 
				"	when ama.status = 19 then 'Rejected By MANCO Review' " + 
				"	when ama.status = 20 then 'Rejected By Learner Review Committee' " + 
				"	when ama.status = 21 then 'Qualification Obtained' " + 
				"	when ama.status = 22 then 'Deactivated' " + 
				"	when ama.status = 23 then 'Project Terminated Manager Approval' " + 
				"	when ama.status = 24 then 'Suspend Project' " + 
				"	when ama.status = 25 then 'Project Terminated' " + 
				"	when ama.status = 26 then 'Pending Review Approval' " + 
				"	when ama.status = 27 then 'Uphold' " + 
				"	when ama.status = 28 then 'Pending Resubmission'   " + 
				"	when ama.status = 29 then 'Awaiting NAMB' " + 
				"	when ama.status = 30 then 'Pending Withdrawal' " + 
				"	when ama.status = 31 then 'Pending Inverstigation' " + 
				"	when ama.status = 32 then 'Pending Change Approval' " + 
				"	when ama.status = 33 then 'Not Competent' " + 
				" end as 'status', " + 
				
				" u.first_name as 'firstName', "  + 
				" u.middle_name as 'middleName', "  + 
				" u.last_name as 'lastName', "  +  
				" u.rsa_id_number as 'rsaIDNumber', " + 
				" u.passport_number as 'passportNumber' " + 
				" from " + 
				" assessor_moderator_application ama " +
				" left join users u on u.id = ama.users_id " +
				" left join review_committee_meeting rcm on rcm.id = ama.review_committee_meeting_id " +
				" left join Review_committee_meeting_agenda rcma on rcma.id = ama.review_committee_meeting_agenda_id " +
				" where ama.type_assessor_moderator = :assessorModeratorTypeEnum"; 
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("assessorModeratorTypeEnum", assessorModeratorTypeEnum.ordinal());
		return (List<AssessorModeratorBean>)super.nativeSelectSqlList(sql, AssessorModeratorBean.class, parameters);
	}
}

