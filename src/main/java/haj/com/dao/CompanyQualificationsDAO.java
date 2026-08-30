package haj.com.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Company;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyQualificationsDAO.
 */
public class CompanyQualificationsDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CompanyQualifications.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CompanyQualifications}
	 * @throws Exception global exception
	 * @see    CompanyQualifications
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyQualifications> allCompanyQualifications() throws Exception {
		return (List<CompanyQualifications>)super.getList("select o from CompanyQualifications o");
	}

	/**
	 * Get all CompanyQualifications between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.CompanyQualifications}
	 * @throws Exception global exception
	 * @see    CompanyQualifications
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyQualifications> allCompanyQualifications(Long from, int noRows) throws Exception {
	 	String hql = "select o from CompanyQualifications o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<CompanyQualifications>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.CompanyQualifications}
	 * @throws Exception global exception
	 * @see    CompanyQualifications
	 */
	public CompanyQualifications findByKey(Long id) throws Exception {
	 	String hql = "select o from CompanyQualifications o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (CompanyQualifications)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CompanyQualifications by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.CompanyQualifications}
	 * @throws Exception global exception
	 * @see    CompanyQualifications
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyQualifications> findByName(String description) throws Exception {
	 	String hql = "select o from CompanyQualifications o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<CompanyQualifications>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyQualifications> findByTargetClassAndTargetKey(String targetClass,Long targetKey) throws Exception {
	 	String hql = "select o from CompanyQualifications o left join fetch o.qualification where o.targetClass = :targetClass and o. targetKey = :targetKey" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetKey",targetKey);
	    parameters.put("targetClass",targetClass);
		return (List<CompanyQualifications>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyQualifications> findByTargetClassAndTargetKeyAndAccept(String targetClass,Long targetKey,Boolean accept) throws Exception {
	 	String hql = "select o from CompanyQualifications o left join fetch o.qualification where o.targetClass = :targetClass and o.targetKey = :targetKey and o.accept =:accept" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetKey",targetKey);
	    parameters.put("targetClass",targetClass);
	    parameters.put("accept",accept);
		return (List<CompanyQualifications>)super.getList(hql, parameters);
	}
	
	/**
	 * Find CompanyQualifications by company.
	 *
	 * @author TechFinium
	 * @param company the company
	 * @return a list of {@link haj.com.entity.CompanyQualifications}
	 * @throws Exception global exception
	 * @see    CompanyQualifications
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyQualifications> findByCompany(Company company) throws Exception {
	 	String hql = "select o from CompanyQualifications o left join fetch o.qualification where o.company.id = :companyId";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", company.getId());
		return (List<CompanyQualifications>)super.getList(hql, parameters);
	}
	
	/**
	 * Find CompanyQualifications by company.
	 *
	 * @author TechFinium
	 * @param company the company
	 * @param qualification the qualification
	 * @return a list of {@link haj.com.entity.CompanyQualifications}
	 * @throws Exception global exception
	 * @see    CompanyQualifications
	 */
	public CompanyQualifications findByCompany(Company company, Qualification qualification) throws Exception {
	 	String hql = "select o from CompanyQualifications o left join fetch o.qualification where o.company.id = :companyId and o.qualification.id =:qualificationId";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", company.getId());
	    parameters.put("qualificationId", qualification.getId());
		return (CompanyQualifications)super.getUniqueResult(hql, parameters);
	}
	
	public CompanyQualifications findByCompany(Company company, Qualification qualification, Boolean accept) throws Exception {
	 	String hql = "select o from CompanyQualifications o left join fetch o.qualification where o.company.id = :companyId and o.qualification.id =:qualificationId and o.accept =:accept";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", company.getId());
	    parameters.put("qualificationId", qualification.getId());
	    parameters.put("accept", accept);
		return (CompanyQualifications)super.getUniqueResult(hql, parameters);
	}
	
	public Integer countfindByCompany(Company company, Qualification qualification, Boolean accept) throws Exception {
	 	String hql = "select count(o) from CompanyQualifications o where o.company.id = :companyId and o.qualification.id =:qualificationId and o.accept =:accept";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", company.getId());
	    parameters.put("qualificationId", qualification.getId());
	    parameters.put("accept", accept);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countfindByTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication, Qualification qualification, Boolean accept) throws Exception {
	 	String hql = "select count(o) from CompanyQualifications o where o.targetKey = :targetKey and o.targetClass = :targetClass and o.qualification.id =:qualificationId and o.accept =:accept";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetKey", trainingProviderApplication.getId());
	    parameters.put("targetClass", trainingProviderApplication.getClass().getName());
	    parameters.put("qualificationId", qualification.getId());
	    parameters.put("accept", accept);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}	
	
	@SuppressWarnings("unchecked")	
	public List<Qualification> findQualificationAutocomplete(Company company, Boolean accept) throws Exception {
		String hql = "select o.qualification from CompanyQualifications o where o.company.id = :companyId and o.accept =:accept";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", company.getId());
	    parameters.put("accept", accept);
		return (List<Qualification>) super.getList(hql, parameters);
	}
	
	public CompanyQualifications findByQualification(Qualification qualification, Boolean accept) throws Exception {
	 	String hql = "select o from CompanyQualifications o left join fetch o.qualification where o.qualification.id =:qualificationId and o.accept =:accept";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("qualificationId", qualification.getId());
	    parameters.put("accept", accept);
		return (CompanyQualifications)super.getUniqueResult(hql, parameters);
	}

	public Integer countfindByQualification(Qualification qualification, Boolean accept) {
		String hql = "select count(o) from CompanyQualifications o where o.qualification.id =:qualificationId and o.accept =:accept";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("qualificationId", qualification.getId());
	    parameters.put("accept", accept);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> findCompaniesAssignedToQualificationAndAccept(String desc, Qualification qualification, Boolean accept) throws Exception {
	 	String hql = "select distinct(c) from CompanyQualifications o left join Company c on c.id = o.company.id where "
	 			+ "(o.company.companyName like :companyName OR o.company.tradingName like  :companyName OR o.company.levyNumber like  :companyName) and o.company.nonSetaCompany = false "
	 			+ "and o.qualification.id = :qualificationId and o.accept = :accept ";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyName", "%" + desc.trim() + "%");
	    parameters.put("qualificationId", qualification.getId());
	    parameters.put("accept", accept);
	    return (List<Company>)super.getList(hql, parameters, 10);
	}
	
	public Integer countCompaniesAssignedToQualificationAndAccept(Qualification qualification, Boolean accept) throws Exception {
		String hql = "select count(o.company.id) from CompanyQualifications o where o.qualification.id = :qualificationId and o.accept =:accept";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("qualificationId", qualification.getId());
	    parameters.put("accept", accept);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	/**
	 * Locates distinct companies by:
	 * Company Name
	 * Qualification ID
	 * Accepted
	 * Company located in training provider application
	 * TP application Approved
	 * @param desc
	 * @param qualification
	 * @param accept
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Company> findCompaniesAssignedToQualificationAndTpApplicationIsApproved(String desc, Qualification qualification, Boolean accept) throws Exception {
	 	String hql = "select distinct(c) from CompanyQualifications o left join Company c on c.id = o.company.id where "
	 			+ " (o.company.companyName like :companyName OR o.company.tradingName like  :companyName OR o.company.levyNumber like  :companyName) and o.company.nonSetaCompany = false "
	 			+ " and o.qualification.id = :qualificationId and o.accept = :accept "
	 			+ " and o.company.id in (select x.company.id from TrainingProviderApplication x where x.approvalStatus = :approvedStatus )";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyName", "%" + desc.trim() + "%");
	    parameters.put("qualificationId", qualification.getId());
	    parameters.put("accept", accept);
	    parameters.put("approvedStatus", ApprovalEnum.Approved);
	    return (List<Company>)super.getList(hql, parameters, 10);
	}
	
	public Integer countCompaniesAssignedToQualificationAndTpApplicationIsApproved(Qualification qualification, Boolean accept) throws Exception {
		String hql = "select count(o.company.id) from CompanyQualifications o where "
				+ " o.qualification.id = :qualificationId and o.accept = :accept "
				+ " and o.company.id in ( select x.company.id from TrainingProviderApplication x where x.approvalStatus = :approvedStatus )";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("qualificationId", qualification.getId());
	    parameters.put("accept", accept);
	    parameters.put("approvedStatus", ApprovalEnum.Approved);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}	
	
	/**
	 * Locates ditinct companies by:
	 * Company Name
	 * Qualification ID
	 * Accepted
	 * Company located in training provider application
	 * TP application Approved
	 * TP Application has not expired
	 * @param desc
	 * @param qualification
	 * @param accept
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Company> findCompaniesAssignedToQualificationAndTpApplicationIsApprovedAndBeforeExpiryDate(String desc, Qualification qualification, Boolean accept, Date date) throws Exception {
	 	String hql = "select distinct(c) from CompanyQualifications o left join Company c on c.id = o.company.id where "
	 			+ " (o.company.companyName like :companyName OR o.company.tradingName like  :companyName OR o.company.levyNumber like  :companyName) and o.company.nonSetaCompany = false "
	 			+ " and o.qualification.id = :qualificationId and o.accept = :accept "
	 			+ " and o.company.id in (select x.company.id from TrainingProviderApplication x where x.approvalStatus = :approvedStatus and DATE(expiriyDate) > DATE(:date) )";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyName", "%" + desc.trim() + "%");
	    parameters.put("qualificationId", qualification.getId());
	    parameters.put("accept", accept);
	    parameters.put("approvedStatus", ApprovalEnum.Approved);
	    parameters.put("date", date);
	    return (List<Company>)super.getList(hql, parameters, 10);
	}
	
	public Integer countCompaniesAssignedToQualificationAndTpApplicationIsApprovedAndBeforeExpiryDate(Qualification qualification, Boolean accept, Date date) throws Exception {
		String hql = "select count(o.company.id) from CompanyQualifications o where o.qualification.id = :qualificationId and o.accept = :accept "
				+ " and o.company.id in (select x.company.id from TrainingProviderApplication x where x.approvalStatus = :approvedStatus and DATE(expiriyDate) > DATE(:date) )";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("qualificationId", qualification.getId());
	    parameters.put("accept", accept);
	    parameters.put("approvedStatus", ApprovalEnum.Approved);
	    parameters.put("date", date);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> findCompaniesByAcceptQualificationApprovedApplicationBeforeExpiryAndTypeOfApplication(String desc, Qualification qualification, Boolean accept, Date date, AccreditationApplicationTypeEnum accreditationApplicationTypeEnum) throws Exception {
	 	String hql = "select distinct(c) from CompanyQualifications o left join Company c on c.id = o.company.id "
	 			+ " where (o.company.companyName like :companyName OR o.company.tradingName like  :companyName OR o.company.levyNumber like  :companyName) and o.company.nonSetaCompany = false "
	 			+ " and o.qualification.id = :qualificationId and o.accept = :accept "
	 			+ " and o.targetClass = :tpApplicationClass "
	 			+ " and o.company.id in ( "
	 			+ " select x.company.id from TrainingProviderApplication x "
	 			+ " where x.approvalStatus = :approvedStatus and DATE(x.expiriyDate) > DATE(:date) "
	 			+ " and x.id = o.targetKey and x.accreditationApplicationTypeEnum = :applicationType "
	 			+ " ) ";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyName", "%" + desc.trim() + "%");
	    parameters.put("qualificationId", qualification.getId());
	    parameters.put("accept", accept);
	    parameters.put("approvedStatus", ApprovalEnum.Approved);
	    parameters.put("date", date);
	    parameters.put("tpApplicationClass", TrainingProviderApplication.class.getName());
	    parameters.put("applicationType", accreditationApplicationTypeEnum); 
	    return (List<Company>)super.getList(hql, parameters, 10);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> findTrainingProviderApllicationsByAcceptQualificationApprovedApplicationBeforeExpiryAndTypeOfApplication(String desc, Qualification qualification, Boolean accept, Date date, AccreditationApplicationTypeEnum accreditationApplicationTypeEnum) throws Exception {
	 	String hql = "select distinct(o) from TrainingProviderApplication o " + 
	 			" left join Company c on c.id = o.company.id " + 
	 			" where (o.company.companyName like :companyName OR o.company.tradingName like  :companyName OR o.company.levyNumber like  :companyName) " + 
	 			" and o.company.nonSetaCompany = false " + 
	 			" and o.approvalStatus = :approvedStatus " + 
	 			" and DATE(o.expiriyDate) > DATE(:date) " + 
	 			" and o.accreditationApplicationTypeEnum = :applicationType" + 
	 			" and o.id in " + 
	 			" ( select distinct(x.targetKey) from CompanyQualifications x " + 
	 			" where x.targetClass = :tpApplicationClass " + 
	 			" and x.qualification.id = :qualificationId " + 
	 			" and x.accept = :accept )";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyName", "%" + desc.trim() + "%");
	    parameters.put("qualificationId", qualification.getId());
	    parameters.put("accept", accept);
	    parameters.put("approvedStatus", ApprovalEnum.Approved);
	    parameters.put("date", date);
	    parameters.put("tpApplicationClass", TrainingProviderApplication.class.getName());
	    parameters.put("applicationType", accreditationApplicationTypeEnum); 
	    return (List<TrainingProviderApplication>)super.getList(hql, parameters, 10);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> findTrainingProviderApllicationsByCompanyIdAcceptQualificationApprovedApplicationBeforeExpiryAndTypeOfApplication(Long companyId, Qualification qualification, Boolean accept, Date date, AccreditationApplicationTypeEnum accreditationApplicationTypeEnum) throws Exception {
	 	String hql = "select distinct(o) from TrainingProviderApplication o where " + 
	 			" o.company.id = :companyId " + 
	 			" and o.approvalStatus = :approvedStatus " + 
	 			" and DATE(o.expiriyDate) > DATE(:date) " + 
	 			" and o.accreditationApplicationTypeEnum = :applicationType" + 
	 			" and o.id in " + 
	 			" ( select distinct(x.targetKey) from CompanyQualifications x " + 
	 			" where x.targetClass = :tpApplicationClass " + 
	 			" and x.qualification.id = :qualificationId " + 
	 			" and x.accept = :accept )";
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("companyId", companyId);
	    parameters.put("qualificationId", qualification.getId());
	    parameters.put("accept", accept);
	    parameters.put("approvedStatus", ApprovalEnum.Approved);
	    parameters.put("date", date);
	    parameters.put("tpApplicationClass", TrainingProviderApplication.class.getName());
	    parameters.put("applicationType", accreditationApplicationTypeEnum); 
	    return (List<TrainingProviderApplication>)super.getList(hql, parameters, 10);
	}
	
	public Integer countCompaniesByAcceptQualificationApprovedApplicationBeforeExpiryAndTypeOfApplication(Qualification qualification, Boolean accept, Date date, AccreditationApplicationTypeEnum accreditationApplicationTypeEnum) throws Exception {
		String hql = "select count(o.company.id) from CompanyQualifications o "
				+ " where o.qualification.id = :qualificationId and o.accept = :accept "
				+ " and o.targetClass = :tpApplicationClass "
				+ " and o.company.id in ( "
				+ "	select x.company.id from TrainingProviderApplication x "
				+ "	where x.approvalStatus = :approvedStatus and DATE(expiriyDate) > DATE(:date) "
				+ " and x.id = o.targetKey and x.accreditationApplicationTypeEnum = :applicationType "
				+ "	) ";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("qualificationId", qualification.getId());
	    parameters.put("accept", accept);
	    parameters.put("approvedStatus", ApprovalEnum.Approved);
	    parameters.put("date", date);
	    parameters.put("tpApplicationClass", TrainingProviderApplication.class.getName());
	    parameters.put("applicationType", accreditationApplicationTypeEnum); 
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countQualificationAccptedByTargetClassAndKey(String targetClass, Long targetKey, Qualification qualification, Boolean accept) throws Exception {
	 	String hql = "select count(o) from CompanyQualifications o where o.qualification.id =:qualificationId and o.accept = :accept and o.targetClass = :targetClass  and o.targetKey = :targetKey";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("qualificationId", qualification.getId());
	    parameters.put("accept", accept);
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countByApplicationAndQualification(String targetClass, Long targetKey, Long qualificationId, Long companyId) throws Exception {
	 	String hql = "select count(o) from CompanyQualifications o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.qualification.id = :qualificationId and o.company.id = :companyId";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
	    parameters.put("qualificationId", qualificationId);
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
}