package haj.com.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.NonSetaCompany;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderMonitoring;
import haj.com.entity.Users;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class TrainingProviderApplicationDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TrainingProviderApplication
	 * 
	 * @author TechFinium
	 * @see TrainingProviderApplication
	 * @return a list of {@link haj.com.entity.TrainingProviderApplication}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplication() throws Exception {
		return (List<TrainingProviderApplication>) super.getList("select o from TrainingProviderApplication o");
	}

	/**
	 * Get all TrainingProviderApplication between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see TrainingProviderApplication
	 * @return a list of {@link haj.com.entity.TrainingProviderApplication}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplication(Long from, int noRows) throws Exception {
		String hql = "select o from TrainingProviderApplication o ";
		Map<String, Object> parameters = new HashMap<>();

		return (List<TrainingProviderApplication>) super.getList(hql, parameters, from.intValue(), noRows);
	}
	
	/**
	 * Find Last Approved TrainingProviderApplication
 	 * @author TechFinium 
 	 */
	@SuppressWarnings("unchecked")
	public TrainingProviderApplication findLastApproved() throws Exception {
		ApprovalEnum approvalStatus=ApprovalEnum.Approved;
	 	String hql = "select o from TrainingProviderApplication o where o.approvalStatus =:approvalStatus order by o.createDate desc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("approvalStatus", approvalStatus);
		return (TrainingProviderApplication) super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> findListLastApproved() throws Exception {
		ApprovalEnum approvalStatus=ApprovalEnum.Approved;
	 	String hql = "select o from TrainingProviderApplication o where o.approvalStatus =:approvalStatus order by o.createDate desc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("approvalStatus", approvalStatus);
		return (List<TrainingProviderApplication>) super.getList(hql, parameters);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see TrainingProviderApplication
	 * @return a {@link haj.com.entity.TrainingProviderApplication}
	 * @throws Exception
	 *             global exception
	 */
	public TrainingProviderApplication findByKey(Long id) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.id = :id ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		return (TrainingProviderApplication) super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> findByAccreditationNumber(String accreditationNumber) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.accreditationNumber = :accreditationNumber order by o.createDate desc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("accreditationNumber", accreditationNumber.trim());
		return (List<TrainingProviderApplication>) super.getList(hql, parameters);
	}
	
	public TrainingProviderApplication findByCertificateNumber(String certificateNumber) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.certificateNumber = :certificateNumber ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("certificateNumber", certificateNumber);
		return (TrainingProviderApplication) super.getUniqueResult(hql, parameters);
	}
	
	public TrainingProviderApplication findByCertificateNumberOrAccreditationNumber(String certificateNumber, ApprovalEnum approvalStatus) throws Exception {
		String hql = "select o from TrainingProviderApplication o where (o.certificateNumber = :certificateNumber or o.accreditationNumber = :certificateNumber) and o.approvalStatus = :approvalStatus";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("certificateNumber", certificateNumber);
		parameters.put("approvalStatus", approvalStatus);
		return (TrainingProviderApplication) super.getUniqueResult(hql, parameters);
	}
	
	public TrainingProviderApplication findByCertificateNumberOrAccreditationNumberAndInStatus(String certificateNumber, List<ApprovalEnum> approvalStatusList) throws Exception {
		String hql = "select o from TrainingProviderApplication o where (o.certificateNumber = :certificateNumber or o.accreditationNumber = :certificateNumber) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("certificateNumber", certificateNumber);
		if (approvalStatusList != null && !approvalStatusList.isEmpty()) {
			int counter = 1;
			hql += " and o.approvalStatus in ( ";
			for (ApprovalEnum approvalEnum : approvalStatusList) {
				hql += ":approvalEnum"+counter;
				parameters.put("approvalEnum"+counter, approvalEnum);
				if (counter == approvalStatusList.size()) {
					hql += " ) ";
				} else {
					hql += " , ";
				}
				counter++;
			}
		}
		return (TrainingProviderApplication) super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication>  findByCertificateNumberOrAccreditationNumberList(String certificateNumber,ApprovalEnum approvalStatus) throws Exception {
		String hql = "select o from TrainingProviderApplication o where (o.certificateNumber = :certificateNumber or o.accreditationNumber = :certificateNumber) and o.approvalStatus = :approvalStatus order by o.createDate desc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("certificateNumber", certificateNumber);
		parameters.put("approvalStatus", approvalStatus);
		return (List<TrainingProviderApplication>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> findByCompany(Company company) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.company.id = :companyID order by o.createDate desc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("companyID", company.getId());
		return (List<TrainingProviderApplication>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> findByCompanyAndStatus(Company company,ApprovalEnum approvalStatus) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.company.id = :companyID and  o.approvalStatus = :approvalStatus order by o.createDate desc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("companyID", company.getId());
		parameters.put("approvalStatus",approvalStatus);
		return (List<TrainingProviderApplication>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> findByApplicatitionStatus(ApprovalEnum approvalStatus) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.approvalStatus = :approvalStatus ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("approvalStatus",approvalStatus);
		return (List<TrainingProviderApplication>) super.getList(hql, parameters);
	}
	
	

	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> findByUserAndCompany(Users users, Company company) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.users.id = :usersID and o.company.id = :companyID";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("usersID", users.getId());
		parameters.put("companyID", company.getId());
		return (List<TrainingProviderApplication>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> findByUserAndCompanyAndApplicationtype(Users users, Company company,AccreditationApplicationTypeEnum accreditationApplicationTypeEnum) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.users.id = :usersID and o.company.id = :companyID and o.accreditationApplicationTypeEnum =:appType";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("usersID", users.getId());
		parameters.put("companyID", company.getId());
		parameters.put("appType", accreditationApplicationTypeEnum);
		return (List<TrainingProviderApplication>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> findByUser(Users users) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.users.id = :usersID ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("usersID", users.getId());
		return (List<TrainingProviderApplication>) super.getList(hql, parameters);
	}
	
	public int countAllApprovedApplication() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from TrainingProviderApplication o where o.accreditationNumber <> null")).intValue();
	}

	/**
	 * Find TrainingProviderApplication by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see TrainingProviderApplication
	 * @return a list of {@link haj.com.entity.TrainingProviderApplication}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> findByName(String description) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<TrainingProviderApplication>) super.getList(hql, parameters);
	}
	
	public List<?> sortAndFilterWhereTPInfo(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String hql) {
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
					if (entry.getKey().equals("companyName") || entry.getKey().equals("tradingName") || entry.getKey().equals("levyNumber")) {
						hql += " and o.company." + entry.getKey() + " like " + " :" + hvar;
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
					if (sortField.equals("companyName") || sortField.equals("tradingName") ||sortField.equals("levyNumber")) {
						hql += " order by o.company." + sortField + " asc ";
					}
					else{
						hql += " order by o." + sortField + " asc ";
					}
					
					break;
				case DESCENDING:
					if (sortField.equals("companyName") || sortField.equals("tradingName") ||sortField.equals("levyNumber")) {
						hql += " order by o.company." + sortField + " desc ";
					}
					
					else{
						hql += " order by o." + sortField + " desc ";
					}
					break;
				default:
					break;
			}
		}
		
		return getList(hql, filters, startingAt, pageSize);
	}
	
	/**
	 * Count.
	 * @param filters
	 *            the filters
	 *
	 * @return the int
	 */
	public int countWhereTPInfo(Map<String, Object> filters, String hql) {
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
					if (entry.getKey().equals("firstName")) {
						hql += " and o.user." + entry.getKey() + " like " + " :" + hvar;
					}else{
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}
					
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}

	/**
	 * Locates TrainingProviderApplication object by NonSetaCompany ID 
	 * 
	 * @param nonSetaCompanyId
	 * 		The NonSetaCompany Object ID
	 * @see NonSetaCompany
	 * @return TrainingProviderApplication
	 * @throws Exception
	 */
	public TrainingProviderApplication findByNonMersetaCompanyId(Long nonSetaCompanyId) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.nonSetaCompany.id = :nonSetaCompanyId ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("nonSetaCompanyId", nonSetaCompanyId);
		return (TrainingProviderApplication) super.getUniqueResult(hql, parameters);
	}
	
	public Users findUserByCompany(Company company) throws Exception {
		String hql = "select u from TrainingProviderApplication o inner join Users u on u.id = o.users.id where o.company.id = :companyID ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("companyID", company.getId());
		return (Users) super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findUsersByCompany(Company company) throws Exception {
		String hql = "select u from TrainingProviderApplication o inner join Users u on u.id = o.users.id where o.company.id = :companyID ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("companyID", company.getId());
		return (List<Users>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> findTrainingProviderApplicationStartDateBy() throws Exception {
		String hql = "select tpa from Company o inner join TrainingProviderMonitoring tpm on tpm.company.id = o.id " 
				+ " inner join TrainingProviderApplication tpa on o.id = tpa.company.id ";
		return (List<TrainingProviderApplication>) super.getList(hql);
	}
	
	public TrainingProviderMonitoring findAllApplicaions() throws Exception {
	 	String hql = "select o from Company o inner join TrainingProviderMonitoring tpm on tpm.company.id = o.id " 
				+ " inner join TrainingProviderApplication tpa on o.id = tpa.company.id where "
				+ " ((DATE_FORMAT(tpa.startDate, '%Y%m%d') + 10000) < (select DATE_FORMAT(tpmt.monitoringDate, '%Y%m%d') " 
				+ " from Company ot inner join TrainingProviderMonitoring tpmt on tpmt.company.id = ot.id " 
				+ " inner join TrainingProviderApplication tpat on ot.id = tpat.company.id order by tpmt.monitoringDate asc)) ";

	    return (TrainingProviderMonitoring)super.getUniqueResult(hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> findAllTrainingProviders() throws Exception {
		String hql = "select distinct (o.company) from TrainingProviderApplication o inner join Company c on o.company.id = c.id";
		Map<String, Object> parameters = new HashMap<>();
		return (List<Company>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> locateTrainingProviderApplicationByStatusDateAndExpiry(ApprovalEnum status, Date date, List<AccreditationApplicationTypeEnum> accreditationApplicationTypeEnumList) throws Exception {
		String hql = " select distinct(o.company) "
				+ "	from TrainingProviderApplication o "
				+ "	where o.approvalStatus = :approvedStatus and DATE(o.expiriyDate) > DATE(:date) " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("approvedStatus", status);
	    parameters.put("date", date);  
	    if (accreditationApplicationTypeEnumList != null && accreditationApplicationTypeEnumList.size() != 0) {
	    	hql += " and ( ";
			int count = 1;
			for (AccreditationApplicationTypeEnum accreditationApplicationTypeEnum : accreditationApplicationTypeEnumList) {
				if (count == accreditationApplicationTypeEnumList.size()) {
					hql += " o.accreditationApplicationTypeEnum = :applicationType" + count + " ";
					parameters.put("applicationType" + count, accreditationApplicationTypeEnum); 
				} else {
					hql += " o.accreditationApplicationTypeEnum = :applicationType" + count + "  or ";
					parameters.put("applicationType" + count, accreditationApplicationTypeEnum);  
				}
				count++;
			}
			hql += " ) ";
		}
		return (List<Company>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> locateTrainingProviderApplicationByStatusDateAndExpiryAndRegionId(ApprovalEnum status, Date date, List<AccreditationApplicationTypeEnum> accreditationApplicationTypeEnumList, Long regionId) throws Exception {
		String hql = "select distinct(o.company) "
				+ "from TrainingProviderApplication o "
				+ "where o.approvalStatus = :approvedStatus and DATE(o.expiriyDate) > DATE(:date) "
				+ "o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId) " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("approvedStatus", status);
	    parameters.put("date", date); 
	    parameters.put("regionId", regionId);
	    if (accreditationApplicationTypeEnumList != null && accreditationApplicationTypeEnumList.size() != 0) {
	    	hql += " and ( ";
			int count = 1;
			for (AccreditationApplicationTypeEnum accreditationApplicationTypeEnum : accreditationApplicationTypeEnumList) {
				if (count == accreditationApplicationTypeEnumList.size()) {
					hql += " o.accreditationApplicationTypeEnum = :applicationType" + count + " ";
					parameters.put("applicationType" + count, accreditationApplicationTypeEnum); 
				} else {
					hql += " o.accreditationApplicationTypeEnum = :applicationType" + count + "  or ";
					parameters.put("applicationType" + count, accreditationApplicationTypeEnum);  
				}
				count++;
			}
			hql += " ) ";
		}
		return (List<Company>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> testTpApplicationByRegionId(Long regionId) throws Exception {
		String hql = "select distinct(o.company) "
					+ "from TrainingProviderApplication o "
					+ "where o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId) " ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("regionId", regionId);
		return (List<Company>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public TrainingProviderApplication  findByCompanyAndStatusAndType(Company company,ApprovalEnum approvalStatus, AccreditationApplicationTypeEnum accreditationApplicationTypeEnum) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.company.id = :companyID and  o.approvalStatus = :approvalStatus and  o.accreditationApplicationTypeEnum = :accreditationApplicationTypeEnum order by o.createDate desc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("companyID", company.getId());
		parameters.put("approvalStatus",approvalStatus);
		parameters.put("accreditationApplicationTypeEnum",accreditationApplicationTypeEnum);
		return (TrainingProviderApplication) super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication>   findByCompanyAndStatusAndTypeList(Company company,ApprovalEnum approvalStatus, AccreditationApplicationTypeEnum accreditationApplicationTypeEnum) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.company.id = :companyID and  o.approvalStatus = :approvalStatus and  o.accreditationApplicationTypeEnum = :accreditationApplicationTypeEnum order by o.createDate desc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("companyID", company.getId());
		parameters.put("approvalStatus",approvalStatus);
		parameters.put("accreditationApplicationTypeEnum",accreditationApplicationTypeEnum);
		return (List<TrainingProviderApplication>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> findAllApprovedApplications() throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.id is not null and o.accreditationApplicationTypeEnum = :applicationType and o.approvalStatus = :approvalStatus";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("applicationType", AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL);
		parameters.put("approvalStatus", ApprovalEnum.Approved);
		return (List<TrainingProviderApplication>) super.getList(hql, parameters);
	}
	
	public Integer countTrainingProviderApplicationByComapnyID(Long companyID) throws Exception {
	 	String hql = "select count(o) from TrainingProviderApplication o where o.approvalStatus <> :approvalStatus and o.company.id = :companyID " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("approvalStatus", ApprovalEnum.Rejected);
	    parameters.put("companyID", companyID);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication>  findByCompanyIdAndStatus(Long companyId, ApprovalEnum approvalStatus) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.company.id = :companyID and o.approvalStatus = :approvalStatus ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("companyID", companyId);
		parameters.put("approvalStatus",approvalStatus);
		return (List<TrainingProviderApplication>) super.getList(hql, parameters);
	}
	
	public Integer countLegacyProviderApllicationsLinkedToOpenApplications(Long legacyProviderAccId, List<ApprovalEnum> statusList) throws Exception {
	 	String hql = "select count(o) from TrainingProviderApplication o where o.legacyProviderAccreditation.id = :legacyProviderAccId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("legacyProviderAccId", legacyProviderAccId);
	    if (!statusList.isEmpty()) {
	    	hql += " and o.approvalStatus in ( ";
	    	Integer counter = 1;
	    	for (ApprovalEnum status : statusList) {
	    		if (counter == statusList.size()) {
					hql += ":status" + counter.toString();
				} else {
					hql += ":status" + counter.toString() + ",";
				}
	    		parameters.put("status" + counter.toString(), status);
				counter++;
			}
	    	hql += " ) ";
		}
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}

	public TrainingProviderApplication findCompanyByStatusAndAccreditationNumber(ApprovalEnum approvalStatus, String accreditationNumber) throws Exception{
		String hql = "select o from TrainingProviderApplication o where o.approvalStatus = :approvalStatus and o.accreditationNumber = :accreditationNumber ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("approvalStatus", approvalStatus);
		parameters.put("accreditationNumber", accreditationNumber);
		return (TrainingProviderApplication) super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplicationUnitStandard(Long unitStandardID, Boolean accept) {
		String hql = "select o from TrainingProviderApplication o where o.company.id in (select x.company.id from CompanyUnitStandard x where x.accept = :accept and x.unitStandard.id = :unitStandardID)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("unitStandardID", unitStandardID);
		parameters.put("accept", accept);
		return (List<TrainingProviderApplication>) super.getList(hql, parameters);
	}
	
	public Integer countTrainingProviderApplicationUnitStandard(Long unitStandardID, Boolean accept) {
		String hql = "select count(o) from TrainingProviderApplication o where o.company.id in (select x.company.id from CompanyUnitStandard x where x.accept = :accept and x.unitStandard.id = :unitStandardID)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("unitStandardID", unitStandardID);
		parameters.put("accept", accept);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countTrainingProviderApplicationUnitStandard(Long unitStandardID, Boolean accept, ApprovalEnum approvalStatus) {
		String hql = "select count(o) from TrainingProviderApplication o where o.approvalStatus = :approvalStatus and o.company.id in (select x.company.id from CompanyUnitStandard x where x.accept = :accept and x.unitStandard.id = :unitStandardID)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("unitStandardID", unitStandardID);
		parameters.put("accept", accept);
		parameters.put("approvalStatus", approvalStatus);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplicationQualifications(Long qualificationID, Boolean accept) {
		String hql = "select o from TrainingProviderApplication o where o.company.id in (select x.company.id from CompanyQualifications x where x.accept = :accept and x.qualification.id = :qualificationID)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("qualificationID", qualificationID);
		parameters.put("accept", accept);
		return (List<TrainingProviderApplication>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplicationQualifications(Long qualificationID, Boolean accept, ApprovalEnum approvalStatus) {
		String hql = "select o from TrainingProviderApplication o where o.approvalStatus = :approvalStatus and o.company.id in (select x.company.id from CompanyQualifications x where x.accept = :accept and x.qualification.id = :qualificationID)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("qualificationID", qualificationID);
		parameters.put("accept", accept);
		parameters.put("approvalStatus", approvalStatus);
		return (List<TrainingProviderApplication>) super.getList(hql, parameters);
	}
	
	public Integer countTrainingProviderApplicationQualifications(Long qualificationID, Boolean accept, ApprovalEnum approvalStatus) {
		String hql = "select count(o) from TrainingProviderApplication o where o.approvalStatus = :approvalStatus and o.company.id in (select x.company.id from CompanyQualifications x where x.accept = :accept and x.qualification.id = :qualificationID)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("qualificationID", qualificationID);
		parameters.put("accept", accept);
		parameters.put("approvalStatus", approvalStatus);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countTrainingProviderApplicationQualifications(Long companyID,Long qualificationID, Boolean accept, ApprovalEnum approvalStatus) {
		String hql = "select count(o) from TrainingProviderApplication o where o.approvalStatus = :approvalStatus and o.company.id = :companyID and o.company.id in (select x.company.id from CompanyQualifications x where x.accept = :accept and x.qualification.id = :qualificationID)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("qualificationID", qualificationID);
		parameters.put("accept", accept);
		parameters.put("approvalStatus", approvalStatus);
		parameters.put("companyID", companyID);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countTrainingProviderApplicationQualifications(Long companyID,Long qualificationID,  Long unitStandardID, Boolean accept, ApprovalEnum approvalStatus) {
		String hql = "select count(o) from TrainingProviderApplication o where o.approvalStatus = :approvalStatus and o.company.id = :companyID and o.company.id in (select x.company.id from CompanyQualifications x where x.accept = :accept and x.qualification.id = :qualificationID)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("qualificationID", qualificationID);
		parameters.put("accept", accept);
		parameters.put("approvalStatus", approvalStatus);
		parameters.put("companyID", companyID);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countTrainingProviderApplicationUnitStandard(Long companyID,Long unitStandardID, Boolean accept, ApprovalEnum approvalStatus) {
		String hql = "select count(o) from TrainingProviderApplication o where o.approvalStatus = :approvalStatus and o.company.id = :companyID and o.company.id in (select x.company.id from CompanyUnitStandard x where x.accept = :accept and x.unitStandard.id = :unitStandardID)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("companyID", companyID);
		parameters.put("unitStandardID", unitStandardID);
		parameters.put("accept", accept);
		parameters.put("approvalStatus", approvalStatus);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}

	public Integer countByTrainingProviderApplication(Long companyID,Long unitStandardID, Boolean accept, ApprovalEnum approvalStatus) {
		String hql = "select count(o) from TrainingProviderApplication o where o.approvalStatus = :approvalStatus and o.company.id = :companyID and o.company.id in (select x.company.id from CompanyUnitStandard x where x.accept = :accept and x.unitStandard.id = :unitStandardID)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("companyID", companyID);
		parameters.put("unitStandardID", unitStandardID);
		parameters.put("accept", accept);
		parameters.put("approvalStatus", approvalStatus);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countTrainingProviderApplicationQualifications(Long companyID, Long qualificationID, Boolean accept) {
		String hql = "select count(o) from TrainingProviderApplication o where o.company.id = :companyID and o.company.id in (select x.company.id from CompanyQualifications x where x.accept = :accept and x.qualification.id = :qualificationID)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("companyID", companyID);
		parameters.put("qualificationID", qualificationID);
		parameters.put("accept", accept);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countProviderApllicationsByOpenStatusApplicationTypeAndCompanyId(AccreditationApplicationTypeEnum applicationType, Long companyId, List<ApprovalEnum> statusList) throws Exception {
	 	String hql = "select count(o) from TrainingProviderApplication o where o.accreditationApplicationTypeEnum = :applicationType and o.company.id = :companyId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("applicationType", applicationType);
	    parameters.put("companyId", companyId);
	    if (!statusList.isEmpty()) {
	    	hql += " and o.approvalStatus in ( ";
	    	Integer counter = 1;
	    	for (ApprovalEnum status : statusList) {
	    		if (counter == statusList.size()) {
					hql += ":status" + counter.toString();
				} else {
					hql += ":status" + counter.toString() + ",";
				}
	    		parameters.put("status" + counter.toString(), status);
				counter++;
			}
	    	hql += " ) ";
		}
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countByCompanyIdSiteIdApplicationTypeAndStatusList(AccreditationApplicationTypeEnum applicationType, Long companyId, Long trainingSiteId, List<ApprovalEnum> statusList) throws Exception {
		String hql = "select count(o) from TrainingProviderApplication o where o.accreditationApplicationTypeEnum = :applicationType and o.company.id = :companyId and o.trainingSite.id = :trainingSiteId " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("applicationType", applicationType);
	    parameters.put("companyId", companyId);
	    parameters.put("trainingSiteId", trainingSiteId);
	    if (!statusList.isEmpty()) {
	    	hql += " and o.approvalStatus in ( ";
	    	Integer counter = 1;
	    	for (ApprovalEnum status : statusList) {
	    		if (counter == statusList.size()) {
					hql += ":status" + counter.toString();
				} else {
					hql += ":status" + counter.toString() + ",";
				}
	    		parameters.put("status" + counter.toString(), status);
				counter++;
			}
	    	hql += " ) ";
		}
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countProviderApllicationsByOpenStatusApplicationTypeAndCompanyIdWithNoSiteAssigned(AccreditationApplicationTypeEnum applicationType, Long companyId, List<ApprovalEnum> statusList) throws Exception {
	 	String hql = "select count(o) from TrainingProviderApplication o where o.accreditationApplicationTypeEnum = :applicationType and o.company.id = :companyId and o.trainingSite is null " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("applicationType", applicationType);
	    parameters.put("companyId", companyId);
	    if (!statusList.isEmpty()) {
	    	hql += " and o.approvalStatus in ( ";
	    	Integer counter = 1;
	    	for (ApprovalEnum status : statusList) {
	    		if (counter == statusList.size()) {
					hql += ":status" + counter.toString();
				} else {
					hql += ":status" + counter.toString() + ",";
				}
	    		parameters.put("status" + counter.toString(), status);
				counter++;
			}
	    	hql += " ) ";
		}
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> locateTrainingProviderApplicationByStatusDateAndAccreSearch(ApprovalEnum status, Date date, String desc) throws Exception {
		String hql = " select o "
				+ "	from TrainingProviderApplication o "
				+ "	where o.approvalStatus = :approvedStatus and DATE(o.expiriyDate) > DATE(:date) "
				+ "and (o.accreditationNumber like  :desc)" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("approvedStatus", status);
	    parameters.put("date", date);  
	    parameters.put("desc" , "" + desc.trim() + "%");
		return (List<TrainingProviderApplication>)super.getList(hql, parameters);
	}
	
	
	
}