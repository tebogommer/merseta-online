package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.BankingDetails;
import haj.com.entity.Company;
import haj.com.entity.ConfigDoc;
import haj.com.entity.DgVerification;
import haj.com.entity.ExtensionRequest;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.LearnerDocRequirements;
import haj.com.entity.enums.WspDocRequirements;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigDocDAO.
 */
public class ConfigDocDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ConfigDoc.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.ConfigDoc}
	 * @throws Exception global exception
	 * @see    ConfigDoc
	 */
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> allConfigDoc() throws Exception {
		return (List<ConfigDoc>)super.getList("select o from ConfigDoc o");
	}

	/**
	 * Get all ConfigDoc between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.ConfigDoc}
	 * @throws Exception global exception
	 * @see    ConfigDoc
	 */
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> allConfigDoc(Long from, int noRows) throws Exception {
	 	String hql = "select o from ConfigDoc o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ConfigDoc>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ConfigDoc}
	 * @throws Exception global exception
	 * @see    ConfigDoc
	 */
	public ConfigDoc findByKey(Long id) throws Exception {
	 	String hql = "select o from ConfigDoc o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ConfigDoc)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ConfigDoc by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.ConfigDoc}
	 * @throws Exception global exception
	 * @see    ConfigDoc
	 */
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByName(String description) throws Exception {
	 	String hql = "select o from ConfigDoc o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ConfigDoc>)super.getList(hql, parameters);
	}
	
	/**
	 * All root config doc.
	 *
	 * @param hostingCompanyId the hosting company id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> allRootConfigDoc(Long hostingCompanyId) throws Exception {
	 	String hql = "select o from ConfigDoc o where o.hostingCompany.id = :hostingCompanyId and o.parent is null" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyId", hostingCompanyId);
		return (List<ConfigDoc>)super.getList(hql, parameters);
	}

	/**
	 * All root config doc.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> allRootConfigDoc() throws Exception {
	 	String hql = "select o from ConfigDoc o where o.parent is null" ;
		return (List<ConfigDoc>)super.getList(hql);
	}
	
	
	/**
	 * Find children.
	 *
	 * @param configDocId the config doc id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findChildren(Long configDocId) throws Exception {
	 	String hql = "select o from ConfigDoc o left join fetch o.processRoles pr left join fetch pr.roles r where o.parent.id = :configDocId" ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocId", configDocId);
		return (List<ConfigDoc>)super.getList(hql, parameters);
	}
	
	/**
	 * Find by process.
	 *
	 * @param configDocProcess the config doc process
	 * @return the config doc
	 * @throws Exception the exception
	 */
	public ConfigDoc findByProcess( ConfigDocProcessEnum configDocProcess) throws Exception {
	 	String hql = "select o from ConfigDoc o where o.configDocProcess = :configDocProcess " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
		return (ConfigDoc)super.getUniqueResult(hql, parameters);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessList(ConfigDocProcessEnum configDocProcess) throws Exception {
	 	String hql = "select o from ConfigDoc o where o.configDocProcess = :configDocProcess " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
		return (List<ConfigDoc>)super.getList(hql, parameters);
	}
	
	/**
	 * Find by process HC.
	 *
	 * @param configDocProcess the config doc process
	 * @param hostingCompanyId the hosting company id
	 * @return the config doc
	 * @throws Exception the exception
	 */
	public ConfigDoc findByProcessHC( ConfigDocProcessEnum configDocProcess, Long hostingCompanyId) throws Exception {
	 	String hql = "select o from ConfigDoc o where o.configDocProcess = :configDocProcess and o.hostingCompany.id = :hostingCompanyId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("hostingCompanyId", hostingCompanyId);
		return (ConfigDoc)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find by process.
	 *
	 * @param id the id
	 * @param configDocProcess the config doc process
	 * @return the config doc
	 * @throws Exception the exception
	 */
	public ConfigDoc findByProcess( Long id, ConfigDocProcessEnum configDocProcess) throws Exception {
	 	String hql = "select o from ConfigDoc o where o.configDocProcess = :configDocProcess and o.id <> :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
	    parameters.put("configDocProcess", configDocProcess);
		return (ConfigDoc)super.getUniqueResult(hql, parameters);
		
	}
	
	/**
	 * Find by process.
	 *
	 * @param id the id
	 * @param configDocProcess the config doc process
	 * @param hostingCompanyId the hosting company id
	 * @return the config doc
	 * @throws Exception the exception
	 */
	public ConfigDoc findByProcess( Long id, ConfigDocProcessEnum configDocProcess, Long hostingCompanyId) throws Exception {
	 	String hql = "select o from ConfigDoc o where o.configDocProcess = :configDocProcess and o.hostingCompany.id = :hostingCompanyId and o.id <> :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("hostingCompanyId", hostingCompanyId);
		return (ConfigDoc)super.getUniqueResult(hql, parameters);
	}
	
	
	/**
	 * Find by process for reg.
	 *
	 * @param configDocProcess the config doc process
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessForReg( ConfigDocProcessEnum configDocProcess) throws Exception {
	 	String hql = "select o from ConfigDoc o left join fetch o.parent p where p.configDocProcess = :configDocProcess and o.reqImmediate = true and o.parent is not null " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
		return (List<ConfigDoc>)super.getList(hql, parameters);
		
	}
	
	/**
	 * Find by process for reg by company user type enum.
	 *
	 * @param configDocProcess the config doc process
	 * @param companyUserType the company user type
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessForRegByCompanyUserTypeEnum( ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
	 	String hql = "select o from ConfigDoc o left join fetch o.parent p where p.configDocProcess = :configDocProcess and o.reqImmediate = true and o.companyOrUserDocument = :companyUserType and o.parent is not null " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("companyUserType", companyUserType);
		return (List<ConfigDoc>)super.getList(hql, parameters);
		
	}
	
	/**
	 * Find by process not for reg.
	 *
	 * @param company the company
	 * @param configDocProcess the config doc process
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessNotForReg(Company company,  ConfigDocProcessEnum configDocProcess) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.reqImmediate = false "
	 			   + "and o.parent is not null "
	 			   + "and o.id not in (select x.configDoc.id from Doc x where x.company.id = :companyID and x.configDoc.parent.configDocProcess = :configDocProcess)" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("companyID", company.getId());
		return (List<ConfigDoc>)super.getList(hql, parameters);
		
	}
	
	/**
	 * Find by process not uploaded.
	 *
	 * @param company the company
	 * @param configDocProcess the config doc process
	 * @param companyUserType the company user type
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessNotUploaded(Company company,  ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null "
	 			   + "and o.companyOrUserDocument = :type "
	 			   + "and o.id not in (select x.configDoc.id from Doc x where x.company.id = :companyID and x.configDoc.companyOrUserDocument = :type and x.configDoc.parent.configDocProcess = :configDocProcess)" ;	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("companyID", company.getId());
	    parameters.put("type", companyUserType);
		return (List<ConfigDoc>)super.getList(hql, parameters);
		
	}

	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessUploaded(Company company,  ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null "
	 			   + "and o.companyOrUserDocument = :type "
	 			   + "and o.id in (select x.configDoc.id from Doc x where x.company.id = :companyID and x.configDoc.companyOrUserDocument = :type and x.configDoc.parent.configDocProcess = :configDocProcess)" ;	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("companyID", company.getId());
	    parameters.put("type", companyUserType);
		return (List<ConfigDoc>)super.getList(hql, parameters);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessNotUploaded(ExtensionRequest extensionRequest,  ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null "
	 			   + "and o.companyOrUserDocument = :type "
	 			   + "and o.id not in (select x.configDoc.id from Doc x where x.extensionRequest.id = :extensionRequestId and x.configDoc.companyOrUserDocument = :type and x.configDoc.parent.configDocProcess = :configDocProcess)" ;	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("extensionRequestId", extensionRequest.getId());
	    parameters.put("type", companyUserType);
		return (List<ConfigDoc>)super.getList(hql, parameters);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessNotUploaded(DgVerification dgVerification,  ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null "
	 			   + "and o.companyOrUserDocument = :type "
	 			   + "and o.id not in (select x.configDoc.id from Doc x where x.dgVerification.id = :extensionRequestId and x.configDoc.companyOrUserDocument = :type and x.configDoc.parent.configDocProcess = :configDocProcess)" ;	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("extensionRequestId", dgVerification.getId());
	    parameters.put("type", companyUserType);
		return (List<ConfigDoc>)super.getList(hql, parameters);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessNotUploadedForReg(String targetClass, Long targetKey,  ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null "
	 			   + "and o.companyOrUserDocument = :type "
	 			   + "and o.reqImmediate = true "
	 			   + "and o.id not in (select x.configDoc.id from Doc x where x.targetClass = :targetClass and x.targetKey = :targetKey and x.configDoc.companyOrUserDocument = :type and x.configDoc.parent.configDocProcess = :configDocProcess)" ;	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
	    parameters.put("type", companyUserType);
		return (List<ConfigDoc>)super.getList(hql, parameters);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessNotUploadedNotForReg(String targetClass, Long targetKey,  ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null "
	 			   + "and o.companyOrUserDocument = :type "
	 			   + "and o.reqImmediate = false "
	 			   + "and o.id not in (select x.configDoc.id from Doc x where x.targetClass = :targetClass and x.targetKey = :targetKey and x.configDoc.companyOrUserDocument = :type and x.configDoc.parent.configDocProcess = :configDocProcess)" ;	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
	    parameters.put("type", companyUserType);
		return (List<ConfigDoc>)super.getList(hql, parameters);
		
	}


	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessNotUploaded(String targetClass, Long targetKey,  ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null "
	 			   + "and o.companyOrUserDocument = :type "
	 			   + "and o.useAsDownload = :useAsDownload "
	 			   + "and o.id not in (select x.configDoc.id from Doc x where x.targetClass = :targetClass and x.targetKey = :targetKey and x.configDoc.companyOrUserDocument = :type and x.configDoc.parent.configDocProcess = :configDocProcess and x.configDoc.useAsDownload = :useAsDownload)" ;	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
	    parameters.put("type", companyUserType);
	    parameters.put("useAsDownload", false);
		return (List<ConfigDoc>)super.getList(hql, parameters);
		
	}

	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessNotUploaded(String targetClass, Long targetKey,  ConfigDocProcessEnum configDocProcess) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null "
	 			   + "and o.useAsDownload = :useAsDownload "
	 			   + "and o.id not in (select x.configDoc.id from Doc x where x.targetClass = :targetClass and x.targetKey = :targetKey and x.configDoc.parent.configDocProcess = :configDocProcess and x.configDoc.useAsDownload = :useAsDownload)" ;	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
	    parameters.put("useAsDownload", false);
		return (List<ConfigDoc>)super.getList(hql, parameters);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessNotForUpload(String targetClass, Long targetKey,  ConfigDocProcessEnum configDocProcess) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null "
	 			   + "and o.reqImmediate = false "
	 			   + "and o.useAsDownload = :useAsDownload "
	 			   + "and o.id not in (select x.configDoc.id from Doc x where x.targetClass = :targetClass and x.targetKey = :targetKey and x.configDoc.parent.configDocProcess = :configDocProcess and x.configDoc.useAsDownload = :useAsDownload)" ;	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
	    parameters.put("useAsDownload", true);
		return (List<ConfigDoc>)super.getList(hql, parameters);		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessNotUploaded(BankingDetails bankingDetails,  ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null "
	 			   + "and o.companyOrUserDocument = :type "
	 			   + "and o.id not in (select x.configDoc.id from Doc x where x.bankingDetails.id = :bankingDetailsID and x.configDoc.companyOrUserDocument = :type and x.configDoc.parent.configDocProcess = :configDocProcess)" ;	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("bankingDetailsID", bankingDetails.getId());
	    parameters.put("type", companyUserType);
		return (List<ConfigDoc>)super.getList(hql, parameters);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcess(ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null "
	 			   + "and o.companyOrUserDocument = :type ";	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("type", companyUserType);
		return (List<ConfigDoc>)super.getList(hql, parameters);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessLearners(ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null "
	 			   + "and o.companyOrUserDocument = :type "
	 			   + "and o.learnerDocRequirements is null ";	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("type", companyUserType);
		return (List<ConfigDoc>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessA(ConfigDocProcessEnum configDocProcess) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null "
	 			   + "and o.processRoles is null";	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
		return (List<ConfigDoc>)super.getList(hql, parameters);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessABursary(ConfigDocProcessEnum configDocProcess) throws Exception {
		LearnerDocRequirements learnerDocRequirements = LearnerDocRequirements.Agreement;
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null "
	 			   + "and o.processRoles is null "
	 			   + "and o.learnerDocRequirements not in(:learnerDocRequirements)";	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("learnerDocRequirements", learnerDocRequirements);
		return (List<ConfigDoc>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessELearnerBursary(ConfigDocProcessEnum configDocProcess) throws Exception {
		LearnerDocRequirements bursaryDocRequirements = LearnerDocRequirements.Bursary;
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null "
	 			   + "and o.processRoles is null "
	 			   + "and (o.learnerDocRequirements in(:bursaryDocRequirements) or o.learnerDocRequirements is null) ";	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("bursaryDocRequirements", bursaryDocRequirements);
		return (List<ConfigDoc>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessADisabledBursary(ConfigDocProcessEnum configDocProcess) throws Exception {
		LearnerDocRequirements bursaryDocRequirements = LearnerDocRequirements.Agreement;
		LearnerDocRequirements disabilityDocRequirements = LearnerDocRequirements.ProofOfDisability;
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null "
	 			   + "and o.processRoles is null "
	 			   + "and (o.learnerDocRequirements not in(:bursaryDocRequirements, :disabilityDocRequirements) or o.learnerDocRequirements is null)";	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("bursaryDocRequirements", bursaryDocRequirements);
	    parameters.put("disabilityDocRequirements", disabilityDocRequirements);
		return (List<ConfigDoc>)super.getList(hql, parameters);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessABursary(ConfigDocProcessEnum configDocProcess, LearnerDocRequirements learnerDisabilityDocRequirements) throws Exception {
		LearnerDocRequirements learnerDocRequirements = LearnerDocRequirements.Agreement;
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null "
	 			   + "and o.processRoles is null "
	 			   + "and o.learnerDocRequirements not in(:learnerDocRequirements) "
	 			   + "and (o.learnerDocRequirements in(:learnerDisabilityDocRequirements) or o.learnerDocRequirements is null)";	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("learnerDocRequirements", learnerDocRequirements);
	    parameters.put("learnerDisabilityDocRequirements", learnerDisabilityDocRequirements);
		return (List<ConfigDoc>)super.getList(hql, parameters);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessAELearner(ConfigDocProcessEnum configDocProcess) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null "
	 			   + "and o.processRoles is null "
	 			   + "and o.learnerDocRequirements is null";	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
		return (List<ConfigDoc>)super.getList(hql, parameters);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessAELearnerDisabled(ConfigDocProcessEnum configDocProcess) throws Exception {
		LearnerDocRequirements learnerDocRequirements = LearnerDocRequirements.ProofOfDisability;
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null "
	 			   + "and o.processRoles is null "
	 			   + "and (o.learnerDocRequirements in(:learnerDocRequirements) or o.learnerDocRequirements is null) ";	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("learnerDocRequirements", learnerDocRequirements);
		return (List<ConfigDoc>)super.getList(hql, parameters);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessRoles(Long processRolesId) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where o.parent is not null "
	 			   + "and o.processRoles.id = :processRolesId";	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("processRolesId", processRolesId);
		return (List<ConfigDoc>)super.getList(hql, parameters);
		
	}

	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessRolesNotUploaded(String targetClass, Long targetKey,  Long processRolesId) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where o.processRoles.id = :processRolesId "
	 			   + "and o.parent is not null "
	 			   + "and o.id not in (select x.configDoc.id from Doc x where x.targetClass = :targetClass and x.targetKey = :targetKey and x.configDoc.processRoles.id = :processRolesId)" ;	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("processRolesId", processRolesId);
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
		return (List<ConfigDoc>)super.getList(hql, parameters);
		
	}

	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessRolesNotUploaded(String targetClass, Long targetKey,  ConfigDocProcessEnum processRolesId) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where o.processRoles.id = :processRolesId "
	 			   + "and o.parent is not null "
	 			   + "and o.id not in (select x.configDoc.id from Doc x where x.targetClass = :targetClass and x.targetKey = :targetKey and x.configDoc.processRoles.id = :processRolesId)" ;	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("processRolesId", processRolesId);
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
		return (List<ConfigDoc>)super.getList(hql, parameters);	
	}
	
	public ConfigDoc findByProcessByProcessRolesAndName(Long processRolesId, String name) throws Exception {
	 	String hql = " select o from ConfigDoc o" + 
	 			"	 inner join ProcessRoles p on p.id = o.procesRroles" + 
	 			"	 where o.processRoles = :processRolesId and name = :name;" ;	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("processRolesId", processRolesId);
	    parameters.put("name", name);
		return (ConfigDoc)super.getUniqueResult(hql, parameters);
		
	}
	
	
	
	
	
	/**
	 * Find by process not uploaded WSP.
	 *
	 * @param wsp the wsp
	 * @param configDocProcess the config doc process
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessNotUploadedWSP(Wsp wsp,  ConfigDocProcessEnum configDocProcess) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null "
	 			   + "and o.id not in (select x.configDoc.id from Doc x where x.wsp.id = :wspID and x.configDoc.parent.configDocProcess = :configDocProcess)" ;	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("wspID", wsp.getId());
		return (List<ConfigDoc>)super.getList(hql, parameters);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessNotUploadedNewWSP(ConfigDocProcessEnum configDocProcess) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null ";	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
		return (List<ConfigDoc>)super.getList(hql, parameters);
	}	
	
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessWSPDocRequirements(ConfigDocProcessEnum configDocProcess, List<WspDocRequirements> wspDocRequirements) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null ";	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    if (wspDocRequirements != null && wspDocRequirements.size() != 0) {
			Integer count = 1;
			String wspDocList = "(";
			for (WspDocRequirements entry : wspDocRequirements) {
				wspDocList += ":entry" + count;
				if (count != wspDocRequirements.size()) {
					wspDocList += ",";
				}
				parameters.put("entry" + count.toString(), entry);
				count++;
			}
			wspDocList += ")";
			hql += " and o.wspDocRequirements in " + wspDocList;
		}
		return (List<ConfigDoc>)super.getList(hql, parameters);
	}
	
	// o.wspDocRequirements in ();
	
	/**
	 * Find by process not uploaded user.
	 *
	 * @param user the user
	 * @param configDocProcess the config doc process
	 * @param companyUserType the company user type
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessNotUploadedUser(Users user,  ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.parent is not null "
	 			   + "and o.companyOrUserDocument = :type "
	 			   + "and o.id not in (select x.configDoc.id from Doc x where x.forUsers.id = :userID and x.configDoc.companyOrUserDocument = :type and x.configDoc.parent.configDocProcess = :configDocProcess)" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("userID", user.getId());
	    parameters.put("type", companyUserType);
		return (List<ConfigDoc>)super.getList(hql, parameters);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ConfigDocProcessEnum> usedConfigDocProcessEnumInTasks() throws Exception {
	 	String hql = "select distinct o.workflowProcess from Tasks o order by o.workflowProcess " ;	 	
		return (List<ConfigDocProcessEnum>)super.getList(hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> findByProcessRolesNotUploadedNotBusary(String targetClass, Long targetKey,  Long processRolesId) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where o.processRoles.id = :processRolesId "
	 			   + "and o.parent is not null "
	 			   + "and o.learnerDocRequirements is null "
	 			   + "and o.id not in (select x.configDoc.id from Doc x where x.targetClass = :targetClass and x.targetKey = :targetKey and x.configDoc.processRoles.id = :processRolesId)" ;	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("processRolesId", processRolesId);
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
		return (List<ConfigDoc>)super.getList(hql, parameters);		
	}
	
	public ConfigDoc findByProcessNotUploadedLearnerDocRequirements(ConfigDocProcessEnum configDocProcess, LearnerDocRequirements learnerDocRequirements, String targetClass, Long targetKey) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.learnerDocRequirements = :learnerDocRequirements "
	 			   + "and o.parent is null "
	 			   + "and o.id not in (select x.configDoc.id from Doc x where x.targetClass = :targetClass and x.targetKey = :targetKey)";	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("learnerDocRequirements", learnerDocRequirements);
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
	    return (ConfigDoc)super.getUniqueResult(hql, parameters);
	}
	
	public ConfigDoc findByProcessNotUploadedLearnerDocRequirements(ConfigDocProcessEnum configDocProcess, LearnerDocRequirements learnerDocRequirements) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.learnerDocRequirements = :learnerDocRequirements ";	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("learnerDocRequirements", learnerDocRequirements);
	    return (ConfigDoc)super.getUniqueResult(hql, parameters);
	}
	
	public ConfigDoc findByProcessUploadedLearnerDocRequirements(ConfigDocProcessEnum configDocProcess, LearnerDocRequirements learnerDocRequirements, String targetClass, Long targetKey) throws Exception {
	 	String hql = "select o from ConfigDoc o "
	 			   + "left join fetch o.parent p "
	 			   + "where p.configDocProcess = :configDocProcess "
	 			   + "and o.learnerDocRequirements = :learnerDocRequirements "
	 			   + "and o.parent is not null "
	 			   + "and o.id in (select x.configDoc.id from Doc x where x.targetClass = :targetClass and x.targetKey = :targetKey)";	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("configDocProcess", configDocProcess);
	    parameters.put("learnerDocRequirements", learnerDocRequirements);
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
	    return (ConfigDoc)super.getUniqueResult(hql, parameters);
	}
	
}

