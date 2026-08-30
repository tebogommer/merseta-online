package haj.com.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;

import haj.com.bean.CompanyLearnersBean;
import haj.com.bean.LearnersMentorBean;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyLearnersProgress;
import haj.com.entity.CompanyLearnersTransfer;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.SubmissionEnum;
import haj.com.entity.enums.TradeTypeEnum;
import haj.com.entity.lookup.Chamber;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.Region;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.framework.IDataEntity;
import haj.com.provider.MySQLProvider;

public class CompanyLearnersDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CompanyLearners
	 * 
	 * @author TechFinium
	 * @see CompanyLearners
	 * @return a list of {@link haj.com.entity.CompanyLearners}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> allCompanyLearners() throws Exception {
		return (List<CompanyLearners>) super.getList("select o from CompanyLearners o");
	}

	public int countAllCompanyLearners() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from CompanyLearners o")).intValue();
	}

	public int countCompanyLearnersByStatus(Long companyId, ApprovalEnum status) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.status = :status and o.company.id = :companyId and o.companyLearnersParent is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("status", status);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countCompanyLearnersBySme(Long companyId, Long smeQualificationID) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.smeQualification.id = :smeQualificationID and o.company.id = :companyId and o.companyLearnersParent is null and o.learnerStatus not in (:withdrawn, :completed, :terminated, :qualificationObtained) ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("smeQualificationID", smeQualificationID);
		parameters.put("withdrawn", LearnerStatusEnum.Withdrawn); 
		parameters.put("completed", LearnerStatusEnum.Completed); 
		parameters.put("terminated", LearnerStatusEnum.Terminated);
		parameters.put("qualificationObtained", LearnerStatusEnum.QualificationObtained);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countCompanyLearnersBySme(Long sitessmeId) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.sitesSme.id = :sitessmeId and o.companyLearnersParent is null and o.learnerStatus not in (:withdrawn, :completed, :terminated, :qualificationObtained, :rejected) ";
		Map<String, Object> parameters = new Hashtable<String, Object>();		
		parameters.put("sitessmeId", sitessmeId);
		parameters.put("withdrawn", LearnerStatusEnum.Withdrawn); 
		parameters.put("completed", LearnerStatusEnum.Completed); 
		parameters.put("terminated", LearnerStatusEnum.Terminated);
		parameters.put("qualificationObtained", LearnerStatusEnum.QualificationObtained);
		parameters.put("rejected", LearnerStatusEnum.Rejected);
		System.out.println("hql:"+hql);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countCompanyLearnersByLearnerStatus(Long companyId, ApprovalEnum status, LearnerStatusEnum learnerStatus) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.status = :status and o.learnerStatus = :learnerStatusEnum and o.company.id = :companyId and o.companyLearnersParent is null and o.interventionType <> null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("status", status);
		parameters.put("learnerStatusEnum", learnerStatus);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countCompanyLearnersByLearnerStatus(Long companyId, ApprovalEnum status,  SubmissionEnum submissionEnum) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.status = :status and o.submissionEnum = :submissionEnum and o.company.id = :companyId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("status", status);
		parameters.put("submissionEnum", submissionEnum);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findCompanyLearnersByStatus(Long companyId, ApprovalEnum status, LearnerStatusEnum learnerStatus) throws Exception {
		String hql = "select o from CompanyLearners o where o.status = :status and o.learnerStatus = :learnerStatusEnum and o.company.id = :companyId and o.companyLearnersParent is null and o.interventionType <> null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("status", status);
		parameters.put("learnerStatusEnum", learnerStatus);
		return (List<CompanyLearners>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findCompanyLearnersByStatus(Long companyId, ApprovalEnum status) throws Exception {
		String hql = "select o from CompanyLearners o where o.status = :status and o.company.id = :companyId and o.companyLearnersParent.id is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("status", status);
		return (List<CompanyLearners>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findCompanyLearnersByStatus(Long companyId, ApprovalEnum status, SubmissionEnum submissionEnum, Long companyLearnerParentID) throws Exception {
		String hql = "select o from CompanyLearners o where o.status = :status and o.company.id = :companyId and o.submissionEnum =:submissionEnum and o.companyLearnersParent.id = :companyLearnerParentID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("status", status);
		parameters.put("submissionEnum", submissionEnum);
		parameters.put("companyLearnerParentID", companyLearnerParentID);
		return (List<CompanyLearners>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findCompanyLearnersByParent(Long companyLearnerParentID) throws Exception {
		String hql = "select o from CompanyLearners o where o.companyLearnersParent.id = :companyLearnerParentID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyLearnerParentID", companyLearnerParentID);
		return (List<CompanyLearners>) super.getList(hql, parameters);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findCompanyLearnersByStatusParentId(Long companyId, ApprovalEnum status, SubmissionEnum submissionEnum, Long companyLearnersParentID) throws Exception {
		String hql = "select o from CompanyLearners o where o.status = :status and o.company.id = :companyId and o.submissionEnum =:submissionEnum and o.companyLearnersParent = :companyLearnersParentID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("status", status);
		parameters.put("submissionEnum", submissionEnum);
		parameters.put("companyLearnersParentID", companyLearnersParentID);
		return (List<CompanyLearners>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findCompanyLearnersByStatusParentId(Long companyId, ApprovalEnum status, SubmissionEnum submissionEnum) throws Exception {
		String hql = "select o from CompanyLearners o where o.status = :status and o.company.id = :companyId and o.submissionEnum =:submissionEnum and o.companyLearnersParent is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("status", status);
		parameters.put("submissionEnum", submissionEnum);
		return (List<CompanyLearners>) super.getList(hql, parameters);
	}

	public int countAllCompanyLearnersWhereRegNumberApllied() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from CompanyLearners o where o.registrationNumber <> null")).intValue();
	}

	/**
	 * Get all CompanyLearners between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see CompanyLearners
	 * @return a list of {@link haj.com.entity.CompanyLearners}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> allCompanyLearners(Long from, int noRows) throws Exception {
		String hql = "select o from CompanyLearners o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<CompanyLearners>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see CompanyLearners
	 * @return a {@link haj.com.entity.CompanyLearners}
	 * @throws Exception
	 *             global exception
	 */
	public CompanyLearners findByKey(Long id) throws Exception {
		String hql = "select o from CompanyLearners o left join fetch o.employer left join fetch o.company where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (CompanyLearners) super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findByProvider(long companyID) throws Exception {
		String hql = "select o from CompanyLearners o left join fetch o.employer where o.company.id = :companyID and o.learnerStatus <> :terminated";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", companyID);
		parameters.put("terminated", LearnerStatusEnum.Terminated);
		return (List<CompanyLearners>) super.getList(hql, parameters);
	}


	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findByEmployer(long companyID) throws Exception {
		String hql = "select o from CompanyLearners o left join fetch o.employer where o.employer.id = :companyID and o.learnerStatus <> :terminated";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", companyID);
		parameters.put("terminated", LearnerStatusEnum.Terminated);
		return (List<CompanyLearners>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findUsersByProvider(long companyID) throws Exception {
		String hql = "select distinct o.user from CompanyLearners o where o.company.id = :companyID and o.learnerStatus <> :terminated";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", companyID);
		parameters.put("terminated", LearnerStatusEnum.Terminated);
		return (List<Users>) super.getList(hql, parameters);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see CompanyLearners
	 * @return a {@link haj.com.entity.CompanyLearners}
	 * @throws Exception
	 *             global exception
	 */
	public CompanyLearnersTransfer findTransferByKey(Long id) throws Exception {
		String hql = "select o from CompanyLearnersTransfer o left join fetch o.companyLearners cl left join fetch cl.employer left join fetch cl.company left join fetch o.transferToCompany where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (CompanyLearnersTransfer) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CompanyLearners by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see CompanyLearners
	 * @return a list of {@link haj.com.entity.CompanyLearners}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findByName(String description) throws Exception {
		String hql = "select o from CompanyLearners o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<CompanyLearners>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<IDataEntity> findByCompanyLearner(String entity, Long companyLearnerID) throws Exception {
		String hql = "select o from " + entity + " o where o.companyLearners.id = :companyLearnerID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyLearnerID", companyLearnerID);
		return (List<IDataEntity>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<IDataEntity> findByCompanyLearner(Long companyLearnerID) throws Exception {
		String hql = "select o from CompanyLearnersTransfer o left join fetch o.transferToCompany  where o.companyLearners.id = :companyLearnerID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyLearnerID", companyLearnerID);
		return (List<IDataEntity>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findByUser(Long userID) throws Exception {
		String hql = "select o from CompanyLearners o left join fetch o.employer left join fetch o.company where o.user.id = :userID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userID", userID);
		return (List<CompanyLearners>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findByUserAndQualification(Long userID, Long qualificationID) throws Exception {
		String hql = "select o from CompanyLearners o left join fetch o.employer left join fetch o.company where o.user.id = :userID and o.qualification.id = :qualificationID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userID", userID);
		parameters.put("qualificationID", qualificationID);
		return (List<CompanyLearners>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearners> sortAndFilter(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company employer, InterventionTypeEnum ite) {
		String hql = "select o from CompanyLearners o left join fetch o.employer e left join fetch o.company where o.interventionType.interventionTypeEnum = :interventionTypeEnum and o.employer.id = :employerID";
		filters.put("interventionTypeEnum", ite);
		filters.put("employerID", employer.getId());
		return (List<CompanyLearners>) sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder, filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearners> sortAndFilter(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company employer, TradeTypeEnum ite) {
		String hql = "select o from CompanyLearners o left join fetch o.employer e left join fetch o.company where o.ofoCodes.tradeTypeEnum = :tradeTypeEnum and o.employer.id = :employerID";
		filters.put("tradeTypeEnum", ite);
		filters.put("employerID", employer.getId());
		return (List<CompanyLearners>) sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder, filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearners> sortAndFilter(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company employer, Long designatedTradeID) {
		String hql = "select o from CompanyLearners o left join fetch o.employer e left join fetch o.company where o.qualification.designatedTrade.id = :designatedTradeID and o.employer.id = :employerID";
		filters.put("designatedTradeID", designatedTradeID);
		filters.put("employerID", employer.getId());
		return (List<CompanyLearners>) sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder, filters, hql);
	}
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> sortAndFilterNotDesignatedOrLearnership(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company employer, InterventionTypeEnum ite) {
		String hql = "select o from CompanyLearners o left join fetch o.employer e left join fetch o.company where o.qualification.designatedTrade is null and o.interventionType.interventionTypeEnum <> :interventionTypeEnum and o.employer.id = :employerID";
		filters.put("interventionTypeEnum", ite);
		filters.put("employerID", employer.getId());
		return (List<CompanyLearners>) sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder, filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findByDesignatedTrade(Company employer, Long designatedTradeID) throws Exception {
		String hql = "select o from CompanyLearners o left join fetch o.employer e left join fetch o.company where o.qualification.designatedTrade.id = :designatedTradeID and o.employer.id = :employerID";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("designatedTradeID", designatedTradeID);
		parameters.put("employerID", employer.getId());
		return (List<CompanyLearners>) super.getList(hql, parameters);
	}

	public int count(Map<String, Object> filters, Company employer, TradeTypeEnum ite) {
		String hql = "select count(o) from CompanyLearners o where  o.ofoCodes.tradeTypeEnum = :tradeTypeEnum and o.employer.id = :employerID";
		filters.put("tradeTypeEnum", ite);
		filters.put("employerID", employer.getId());
		return countWhere(filters, hql);
	}

	public int count(Map<String, Object> filters, Company employer, Long designatedTradeID) {
		String hql = "select count(o) from CompanyLearners o where o.qualification.designatedTrade.id = :designatedTradeID and o.employer.id = :employerID";
		filters.put("designatedTradeID", designatedTradeID);
		filters.put("employerID", employer.getId());
		return countWhere(filters, hql);
	}

	public int count(Map<String, Object> filters, Company employer, InterventionTypeEnum ite) {
		String hql = "select count(o) from CompanyLearners o where  o.interventionType.interventionTypeEnum = :interventionTypeEnum and o.employer.id = :employerID";
		filters.put("interventionTypeEnum", ite);
		filters.put("employerID", employer.getId());
		return countWhere(filters, hql);
	}

	public int countNotDesignatedTradeAndLearnership(Map<String, Object> filters, Company employer, InterventionTypeEnum ite) {
		String hql = "select count(o) from CompanyLearners o where o.qualification.designatedTrade is null and o.interventionType.interventionTypeEnum <> :interventionTypeEnum and o.employer.id = :employerID";
		filters.put("interventionTypeEnum", ite);
		filters.put("employerID", employer.getId());
		return countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearnersProgress> findCompanyLearnersProgress(Long companyLearnersID) throws Exception {
		String hql = "select o from CompanyLearnersProgress o where o.companyLearners.id = :companyLearnersID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyLearnersID", companyLearnersID);
		return (List<CompanyLearnersProgress>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Users> findRsaIdOrName(String description) throws Exception {
		String hql = "select distinct o.user from CompanyLearners o where o.user.firstName like :description or o.user.lastName like :description or o.user.rsaIDNumber like :description or o.user.passportNumber like :description ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<Users>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findUserAndQualification(Users u, Qualification qualification) throws Exception {
		String hql = "select o from CompanyLearners o where o.user.id = :userID and o.qualification.id = :qualificationID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userID", u.getId());
		parameters.put("qualificationID", qualification.getId());
		return (List<CompanyLearners>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findByUser(Users u) throws Exception {
		String hql = "select o from CompanyLearners o left join fetch o.employer left join fetch o.company where o.user.id = :id order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", u.getId());
		return (List<CompanyLearners>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findCompanyLearnersByUserAndQualification(Users u, Qualification q, InterventionType i, LearnerStatusEnum learnerStatusEnum)throws Exception {
		String hql = "select o from CompanyLearners o where o.learnerStatus = :learnerStatusEnum and o.user.id = :id and o.qualification.id = :qualificationID and o.interventionType.id = :interventionTypeID and o.company_learners_bursary_link is null order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", u.getId());
		parameters.put("qualificationID", q.getId());
		parameters.put("interventionTypeID", i.getId());
		parameters.put("learnerStatusEnum", learnerStatusEnum);
		return (List<CompanyLearners>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findCompanyLearnersByUserAndQualification(Users u, Qualification q, LearnerStatusEnum learnerStatusEnum)throws Exception {
		String hql = "select o from CompanyLearners o where o.learnerStatus = :learnerStatusEnum and o.user.id = :id and o.qualification.id = :qualificationID and o.company_learners_bursary_link is null order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", u.getId());
		parameters.put("qualificationID", q.getId());
		parameters.put("learnerStatusEnum", learnerStatusEnum);
		return (List<CompanyLearners>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findCompanyLearnersByUserAndQualification(Users u, Qualification q, InterventionType i)throws Exception {
		String hql = "select o from CompanyLearners o where o.user.id = :id and o.qualification.id = :qualificationID and o.interventionType.id = :interventionTypeID and o.company_learners_bursary_link is null order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", u.getId());
		parameters.put("qualificationID", q.getId());
		parameters.put("interventionTypeID", i.getId());
		return (List<CompanyLearners>) super.getList(hql, parameters);
	}

	/**
	 * Counts amount of learners assigned to companyId and qualificationId
	 * 
	 * @param companyId
	 * @param qualificationId
	 * @return
	 * @throws Exception
	 */
	public int countLearnersAssignedToCompanyWithQualificationId(Long companyId, Long qualificationId) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.company.id = :companyId and o.qualification.id = :qualificationId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("qualificationId", qualificationId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countLearnersAssignedToCompanyWithQualificationIdLearnerStatusEnum(Long companyId, Long qualificationId) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.company.id = :companyId and o.qualification.id = :qualificationId and o.learnerStatus not in (:withdrawn, :completed, :terminated, :qualificationObtained)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("qualificationId", qualificationId);
		parameters.put("withdrawn", LearnerStatusEnum.Withdrawn); 
		parameters.put("completed", LearnerStatusEnum.Completed); 
		parameters.put("terminated", LearnerStatusEnum.Terminated); 
		parameters.put("qualificationObtained", LearnerStatusEnum.QualificationObtained);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	/**
	 * Counts amount of learners assigned to companyId and qualificationId
	 * 
	 * @param companyId
	 * @param ofoCodeId
	 * @return int
	 * @throws Exception
	 */
	public int countLearnersAssignedToCompanyWithOfoCodeId(Long companyId, Long ofoCodeId) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.company.id = :companyId and o.ofoCodes.id = :ofoCodeId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("ofoCodeId", ofoCodeId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	public List<?> sortAndFilterWhereLearnerInfo(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String hql) {
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
					if (entry.getKey().equals("firstName") || entry.getKey().equals("lastName") || entry.getKey().equals("rsaIDNumber")) {
						hql += " and o.user." + entry.getKey() + " like " + " :" + hvar;
					} else if (entry.getKey().equals("description")) {
						hql += " and o.interventionType." + entry.getKey() + " like " + " :" + hvar;
					} else {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}

				}
			}
			filters = parms;
		}

		if (sortField != null) {
			switch (sortOrder) {
				case ASCENDING:
					if (sortField.equals("firstName") || sortField.equals("lastName") || sortField.equals("rsaIDNumber")) {
						hql += " order by o.user." + sortField + " asc ";
					} else if (sortField.equals("description")) {
						hql += " order by o.interventionType." + sortField + " asc ";
					} else {
						hql += " order by o." + sortField + " asc ";
					}

					break;
				case DESCENDING:
					if (sortField.equals("firstName") || sortField.equals("lastName") || sortField.equals("rsaIDNumber")) {
						hql += " order by o.user." + sortField + " desc ";
					} else if (sortField.equals("description")) {
						hql += " order by o.interventionType." + sortField + " desc ";
					} else {
						hql += " order by o." + sortField + " desc ";
					}
					break;
				default:
					break;
			}
		}

		//hql += " group by o.user.id ";
		return getList(hql, filters, startingAt, pageSize);
	}

	/**
	 * Count.
	 * 
	 * @param filters
	 *            the filters
	 *
	 * @return the int
	 */
	public int countWhereLearnerInfo(Map<String, Object> filters, String hql) {
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
					if (entry.equals("firstName") || entry.equals("lastName") || entry.equals("rsaIDNumber")) {
						hql += " and o.user." + entry.getKey() + " like " + " :" + hvar;
					} else if (entry.equals("description")) {
						hql += " and o.interventionType." + entry.getKey() + " like " + " :" + hvar;
					} else {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}

				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}

	public List<?> sortAndFilterSearch(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		String hql = "select o from " + entity.getName() + " o where o.id is not null and o.companyLearnersParent is not null";
		if (filters != null) {
			boolean ft = true;
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
				if (ft) {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}

			filters = parms;
		}

		if (sortField != null) {

			switch (sortOrder) {
				case ASCENDING:
					hql += " order by o." + sortField + " asc ";
					break;
				case DESCENDING:
					hql += " order by o." + sortField + " desc ";
					break;
				default:
					break;
			}
		}
		return getList(hql, filters, startingAt, pageSize);
	}

	public int countSearch(Class<?> entity, Map<String, Object> filters) {
		String hql = "select count(o) from " + entity.getName() + " o where o.id is not null and o.companyLearnersParent is not null";
		if (filters != null) {
			boolean ft = true;
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
				if (ft) {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	/*
	 * Find all CompanyLearners that 
	 * expires on the current date
	 * */
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findTodayExpiredContracts() throws Exception {
		String hql = "select o from CompanyLearners o where (DATE(o.employmentContractEndDate) <=:currentDate) and o.learnerStatus =:learnerStatus";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("currentDate", new Date());
		parameters.put("learnerStatus",LearnerStatusEnum.Registered);
		return (List<CompanyLearners>) super.getList(hql, parameters);
	}
	
	/*
	 *  user - is the learner
	 *  qualification - 
	 *  LearnerStatusEnum - Terminated
	 */
	public int countCompanyLearnersByLearnerIdQualificationAndNotLearnerStatus(Long learnerId, Long qualificationId, LearnerStatusEnum learnerStatusEnum) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.user.id = :learnerId and o.qualification.id = :qualificationId and o.learnerStatus <> :learnerStatusEnum ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("learnerId", learnerId);
		parameters.put("qualificationId", qualificationId);
		parameters.put("learnerStatusEnum", learnerStatusEnum);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findLearnersByStatus(ApprovalEnum status, LearnerStatusEnum learnerStatus) throws Exception {
		String hql = "select o from CompanyLearners o where o.status = :status and o.learnerStatus = :learnerStatusEnum and o.companyLearnersParent is null and o.interventionType <> null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("status", status);
		parameters.put("learnerStatusEnum", learnerStatus);
		return (List<CompanyLearners>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> allCompanyLearnersWhereProviderAssignedButNoApplication() throws Exception {
		return (List<CompanyLearners>) super.getList("select o from CompanyLearners o where o.trainingProviderApplication is null and o.company is not null");
	}
	
	public Integer countCompanyLearnersByEmployerId(Long companyId) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.employer.id = :companyId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	public int countLearnersAssignedToPip(Long projectImplementationPlanID) throws Exception{
		String hql = "select count(o) from CompanyLearners o where o.projectImplementationPlan.id = :projectImplementationPlanID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("projectImplementationPlanID", projectImplementationPlanID);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countLearnersAssignedToPipQualification(Long projectImplementationPlanID, Long qualificationID) throws Exception{
		String hql = "select count(o) from CompanyLearners o where o.projectImplementationPlan.id = :projectImplementationPlanID and o.qualification.id = :qualificationID and o.learnerStatus not in (:withdrawn, :rejected, :terminated)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("projectImplementationPlanID", projectImplementationPlanID);
		parameters.put("qualificationID", qualificationID);		
		parameters.put("withdrawn", LearnerStatusEnum.Withdrawn);  
		parameters.put("terminated", LearnerStatusEnum.Terminated);
		parameters.put("rejected", LearnerStatusEnum.Rejected);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countLearnersAssignedToPipSkillsProgramme(Long projectImplementationPlanID, Long skillsProgramID) throws Exception{
		String hql = "select count(o) from CompanyLearners o where o.projectImplementationPlan.id = :projectImplementationPlanID and o.skillsProgram.id = :skillsProgramID and o.learnerStatus not in (:withdrawn, :rejected, :terminated)";		
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("projectImplementationPlanID", projectImplementationPlanID);
		parameters.put("skillsProgramID", skillsProgramID);	
		parameters.put("withdrawn", LearnerStatusEnum.Withdrawn);  
		parameters.put("terminated", LearnerStatusEnum.Terminated);
		parameters.put("rejected", LearnerStatusEnum.Rejected);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countLearnersAssignedToPipSkillsSet(Long projectImplementationPlanID, Long skillsSetID) throws Exception{
		String hql = "select count(o) from CompanyLearners o where o.projectImplementationPlan.id = :projectImplementationPlanID and o.skillsSet.id = :skillsSetID and o.learnerStatus not in (:withdrawn, :rejected, :terminated)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("projectImplementationPlanID", projectImplementationPlanID);
		parameters.put("skillsSetID", skillsSetID);	
		parameters.put("withdrawn", LearnerStatusEnum.Withdrawn);  
		parameters.put("terminated", LearnerStatusEnum.Terminated);
		parameters.put("rejected", LearnerStatusEnum.Rejected);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countLearnersAssignedToPipUnitStandard(Long projectImplementationPlanID, Long unitStandardD) throws Exception{
		String hql = "select count(o) from CompanyLearners o where o.projectImplementationPlan.id = :projectImplementationPlanID and o.unitStandard.id = :unitStandardD and o.learnerStatus not in (:withdrawn, :rejected, :terminated)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("projectImplementationPlanID", projectImplementationPlanID);
		parameters.put("unitStandardD", unitStandardD);		
		parameters.put("withdrawn", LearnerStatusEnum.Withdrawn);  
		parameters.put("terminated", LearnerStatusEnum.Terminated);
		parameters.put("rejected", LearnerStatusEnum.Rejected);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	/* Used for date population - non legacy users */
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findLearnersByStatusNonLegacyAndDateNotPopulated(ApprovalEnum status, LearnerStatusEnum learnerStatus) throws Exception {
		String hql = "select o from CompanyLearners o where o.status = :status and o.learnerStatus = :learnerStatusEnum and o.user <> null and o.legacyId is null and o.dateLearnerRegistered is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("status", status);
		parameters.put("learnerStatusEnum", learnerStatus);
		return (List<CompanyLearners>) super.getList(hql, parameters);
	}
	
	/* Used for date population - legacy users */
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findLearnersByStatusLegacyAndDateNotPopulated(ApprovalEnum status, LearnerStatusEnum learnerStatus) throws Exception {
		String hql = "select o from CompanyLearners o where o.status = :status and o.learnerStatus = :learnerStatusEnum and o.user <> null and o.legacyId <> null and o.dateLearnerRegistered is null ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("status", status);
		parameters.put("learnerStatusEnum", learnerStatus);
		return (List<CompanyLearners>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findLearnersByStatusAndPipAssignedImport(List<LearnerStatusEnum> learnerStatus) throws Exception {
		String hql = "select o from CompanyLearners o where o.projectImplementationPlan is not null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		if (!learnerStatus.isEmpty()) {
			hql += " and o.learnerStatus in ( ";
			int counter = 1;
			for (LearnerStatusEnum entry : learnerStatus) {
				hql += ":status" + counter;
				parameters.put("status" + counter, entry);
				if (counter != learnerStatus.size()) {
					hql += " , ";
				}
				counter++;
			}
			hql += " ) ";
		}
		return (List<CompanyLearners>) super.getList(hql, parameters);
	}
	
	public List<CompanyLearnersBean> generateCompanyLearnerReport(Long interventionID) throws Exception {
		String sql ="select  " + 
				"	cl.create_date as createDate " + 
				"	, CONCAT(u.first_name, ' ', u.last_name) as learnerDetailes " + 
				"	, u.email as 'learnerEmail' "  + 
				"	,u.cell_number AS cellNumber " + 
				"	, u.tel_number AS telNumber " + 
				"	, u.rsa_id_number as rsaIDNumber " + 
				"	, m.municipality_desc as learnerMunicipality " + 
				
				"	, CASE " + 
				"		when cl.highest_education_enum = 0 then 'Below Matric/Standard 10' " + 
				"		when cl.highest_education_enum = 1 then 'Above Matric/Standard 10' " + 
				"		when cl.highest_education_enum = 2 then 'N1' " + 
				"		when cl.highest_education_enum = 3 then 'N2' " + 
				"		when cl.highest_education_enum = 4 then 'N3' " + 
				"		when cl.highest_education_enum = 5 then 'N4' " + 
				"		when cl.highest_education_enum = 6 then 'N5' " + 
				"		when cl.highest_education_enum = 7 then 'N6' " + 
				"		when cl.highest_education_enum = 8 then 'NCV Level 2' " + 
				"		when cl.highest_education_enum = 9 then 'NCV Level 3' " + 
				"		when cl.highest_education_enum = 10 then 'NCV Level 4' " + 
				"		when cl.highest_education_enum = 11 then 'S1' " + 
				"		when cl.highest_education_enum = 12 then 'S2' " + 
				"		when cl.highest_education_enum = 13 then 'S3' " + 
				"	end as highestEducation " + 
				
				"	, CONCAT(hq.qualificationid, ' (', hq.qualificationtitle,')') as 'highestQualification' " + 
				"	, CONCAT(e.company_name, ' (', e.levy_number,')') as 'employerInfor' " + 
				"	, em.municipality_desc as 'employerMunicipality'   " + 
				"	, CONCAT(c.company_name ,' (', c.levy_number,')') as 'providerInfor' " + 
				"	, mun.municipality_desc as 'providerMunicipality' " + 
				"	, CONCAT(tp.company_name ,' (', tpa.accreditation_number,')') as 'providerDetailes' " + 
				"	, CONCAT(cu.first_name, ' ', cu.last_name, ' (', cu.email,')') as 'createUser' " + 
				"	, it.description as 'interventionType' " + 
				"	, sq.qualificationtitle as 'qualificationTitle' " + 
				"	, sp.description as 'skillsProgrammeDescription' " + 
				"	, ss.description as 'skillsSetDescription' " + 
				"	, ls.code as 'learnershipCode' " + 
				"	, us.unitstdtitle as 'unitStandardDescription' " + 
				"	, cl.non_credid_bearing_description as 'nonCreditBearingDescription' " + 
				"	, r.description as 'region' " + 
				"	, cl.approval_date as 'approvalDate' " + 
				"	, cl.registration_number as 'registrationNumber' " + 
				"	, cl.registered_date as 'registrationDate' " + 
				"	, cl.commencement_date as 'commencementDate' " + 
				"	, cl.completion_date as 'completionDate' " + 
				"	, CASE when cl.employment_status = 0 then 'Employed' " + 
				"		when cl.employment_status = 1 then 'Unemployed' " + 
				"		end as 'employmentStatus'   " + 
				
				"	, CASE " + 
				"		when cl.status = 0 then 'Approved' " + 
				"		when cl.status = 1 then 'Rejected' " + 
				"		when cl.status = 2 then 'Pending Manager Approval' " + 
				"		when cl.status = 3 then 'Pending Approval' " + 
				"		when cl.status = 4 then 'Pending Sign Off' " + 
				"		when cl.status = 5 then 'Completed' " + 
				"		when cl.status = 6 then 'Pending accept code of conduct' " + 
				"		when cl.status = 7 then 'Awaiting DHET'   " + 
				"		when cl.status = 8 then 'Pending Final Approval' " + 
				"		when cl.status = 9 then 'Withdrawn'   " + 
				"		when cl.status = 10 then 'N/A' " + 
				"		when cl.status = 11 then 'Recommended' " + 
				"		when cl.status = 12 then 'Appealed' " + 
				"		when cl.status = 13 then 'Pending Committee Approval' " + 
				"		when cl.status = 14 then 'Approved By ETQA Review Committee' " + 
				"		when cl.status = 15 then 'Rejected By ETQA Review Commitee' " + 
				"		when cl.status = 16 then 'Requested Higher Allocation' " + 
				"		when cl.status = 17 then 'Accepted MOA' " + 
				"		when cl.status = 18 then 'Requested Change' " + 
				"		when cl.status = 19 then 'Rejected By MANCO Review' " + 
				"		when cl.status = 20 then 'Rejected By Learner Review Committee' " + 
				"		when cl.status = 21 then 'Qualification Obtained' " + 
				"		when cl.status = 22 then 'Deactivated' " + 
				"		when cl.status = 23 then 'Project Terminated Manager Approval' " + 
				"		when cl.status = 24 then 'Suspend Project' " + 
				"		when cl.status = 25 then 'Project Terminated' " + 
				"		when cl.status = 26 then 'Pending Review Approval' " + 
				"		when cl.status = 27 then 'Uphold' " + 
				"		when cl.status = 28 then 'Pending Resubmission'   " + 
				"		when cl.status = 29 then 'Awaiting NAMB' " + 
				"		when cl.status = 30 then 'Pending Withdrawal' " + 
				"		when cl.status = 31 then 'Pending Inverstigation' " + 
				"		when cl.status = 32 then 'Pending Change Approval' " + 
				"		when cl.status = 33 then 'Not Competent' " + 
				"	end as 'approvalStatus' " + 
				
				"	, CASE " + 
				"		when cl.learner_status = 0 then 'Application' " + 
				"		when cl.learner_status = 1 then 'Registered' " + 
				"		when cl.learner_status = 2 then 'Transfer Pending Approval' " + 
				"		when cl.learner_status = 3 then 'Terminated' " + 
				"		when cl.learner_status = 4 then 'Pending Termination' " + 
				"		when cl.learner_status = 5 then 'Pending Change Approval' " + 
				"		when cl.learner_status = 6 then 'Completed' " + 
				"		when cl.learner_status = 7 then 'Pending Trade Test' " + 
				"		when cl.learner_status = 8 then 'Pending ARPL Trade Test' " + 
				"		when cl.learner_status = 9 then 'Pending Review Approval' " + 
				"		when cl.learner_status = 10 then 'Qualification Obtained' " + 
				"		when cl.learner_status = 11 then 'Pending non-merSETA Qualification Approval' " + 
				"		when cl.learner_status = 12 then 'Pending Verification of Assesment Approval' " + 
				"		when cl.learner_status = 13 then 'Pending Completion Letter Approval' " + 
				"		when cl.learner_status = 14 then 'Withdrawn' " + 
				"end as 'learnerStatus' " + 
				
				"	, Case " + 
				"		when cl.legacy_id is not null then 'Yes' " + 
				"		Else 'No' " + 
				"	end as 'legacyLearner' " + 
				"	, f.description as 'fundedBy' " + 
				"	, cl.date_learner_registered as 'qmrEnteredDate' " + 
				"	, cl.date_learner_completed as 'qmrCompletedDate' " + 
				"from  " + 
				"merseta.company_learners cl   " + 
				"left join merseta.users u on u.id = cl.user_id " + 
				"left join merseta.address ad on ad.id = u.residential_address_id " + 
				"left join merseta.municipality m on m.id = ad.municipality_id " + 
				"left join merseta.users cu on cu.id = cl.create_user_id " + 
				"left join merseta.company c on c.id = cl.company_id " + 
				"left join merseta.company e on e.id = cl.employer_id " + 
				"left join merseta.address ea on ea.id = e.residential_address_id " + 
				"left join merseta.municipality em on em.id = ea.municipality_id " + 
				"left join merseta.intervention_type it on it.id = cl.intervention_type_id " + 
				"left join merseta.funding f on f.id = cl.dunding_id " + 
				"left join merseta.saqa_qualification hq on hq.id = cl.highest_qualification_id " + 
				"left join merseta.saqa_qualification sq on sq.id = cl.qualification_id " + 
				"left join merseta.skills_program sp on sp.id = cl.skills_program_id " + 
				"left join merseta.skills_set ss on ss.id = cl.skills_set_id " + 
				"left join merseta.learnership ls on ls.id = cl.learnership_id " + 
				"left join merseta.saqa_unitstandard us on us.id = cl.unit_standard_id " + 
				"left join merseta.address a on a.id = c.residential_address_id " + 
				"left join merseta.municipality mun on mun.id = a.municipality_id " + 
				"left join merseta.towns t on t.id = a.town_id " + 
				"left join merseta.region_town rt on rt.town_id = t.id " + 
				"left join merseta.region r on r.id = rt.region_id " + 
				"left join merseta.training_provider_application tpa on tpa.id = cl.training_provider_application_id " + 
				"left join merseta.company tp on tp.id = tpa.company_id " + 
				"where it.id <> :interventionID " + 
				"order by cl.id asc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("interventionID", interventionID);
		return (List<CompanyLearnersBean>)super.nativeSelectSqlList(sql, CompanyLearnersBean.class, parameters);
	}
	
	public List<CompanyLearnersBean> generateCompanyLearnerReport(LearnerStatusEnum learnerStatusEnum) throws Exception {
		String sql ="select  " + 
				" cl.create_date as 'createDate' " +
				", u.first_name as 'firstName' "  + 
				", u.middle_name as 'middleName' "  + 
				", u.last_name as 'lastName' "  + 
				", u.email as 'learnerEmail' "  + 
				", u.cell_number as 'cellNumber' " + 
				", u.tel_number as 'telNumber' " + 
				", u.rsa_id_number as 'rsaIDNumber' " + 
				", u.passport_number as 'passportNumber' " + 
				", m.municipality_desc as learnerMunicipality " + 
				", lr.description as 'region' " + 
				", er.description as 'employerRegion' " +
				", r.description as 'providerRegion' " +
				", mun.municipality_desc as 'providerMunicipality' " +
				", municipal.municipality_desc as 'employerMunicipality' " +
				", cham.description as 'chamber' " +
				", it.description as 'interventionType' " + 
				
				", sq.qualificationid_string as 'qualificationCode' " + 
				", sq.qualificationtitle as 'qualificationTitle' " + 
				", sp.programme_id as 'skillsProgrammeCode' " + 
				", sp.description as 'skillsProgrammeDescription' " + 
				", ss.programme_id as 'skillsSetCode' " + 
				", ss.description as 'skillsSetDescription' " + 
				", ls.code as 'learnershipCode' " + 
				", ls.description as 'learnershipDescription' " + 
				", us.unitstandardid as 'unitStandardCode' " + 
				", us.unitstdtitle as 'unitStandardDescription' " + 
				", cl.non_credid_bearing_description as 'nonCreditBearingDescription' " + 				
				
				", cl.approval_date as 'approvalDate' " + 
				", cl.date_learner_registered as 'dateLearnerRegistered' " + 
				", cl.registered_date as 'registrationDate' " +
				", cl.commencement_date as 'commencementDate' " + 
				", cl.completion_date as 'completionDate' " +
				
				",CONCAT(hq.qualificationid, ' (', hq.qualificationtitle,')') as 'highestQualification' " + 
				
				", CASE " + 
				"	when cl.highest_education_enum = 0 then 'Below Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 1 then 'Above Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 2 then 'N1' " + 
				"	when cl.highest_education_enum = 3 then 'N2' " + 
				"	when cl.highest_education_enum = 4 then 'N3' " + 
				"	when cl.highest_education_enum = 5 then 'N4' " + 
				"	when cl.highest_education_enum = 6 then 'N5' " + 
				"	when cl.highest_education_enum = 7 then 'N6' " + 
				"	when cl.highest_education_enum = 8 then 'NCV Level 2' " + 
				"	when cl.highest_education_enum = 9 then 'NCV Level 3' " + 
				"	when cl.highest_education_enum = 10 then 'NCV Level 4' " + 
				"	when cl.highest_education_enum = 11 then 'S1' " + 
				"	when cl.highest_education_enum = 12 then 'S2' " + 
				"	when cl.highest_education_enum = 13 then 'S3' " + 
				"end as 'highestEducation' " + 
				
				", CONCAT(e.company_name, ' (', e.levy_number,')') as 'employerInfor' " + 
				", CONCAT(pr.company_name, ' (', pr.levy_number,')' , tpa.accreditation_number) as 'providerInfor' " + 

				", cl.registration_number as 'registrationNumber' " + 	
				
				", CONCAT(cu.first_name, ' ', cu.last_name, ' (', cu.email,')') as 'createUser' " + 
				
				",CASE " + 
				"	when cl.status = 0 then 'Approved' " + 
				"	when cl.status = 1 then 'Rejected' " + 
				"	when cl.status = 2 then 'Pending Manager Approval' " + 
				"	when cl.status = 3 then 'Pending Approval' " + 
				"	when cl.status = 4 then 'Pending Sign Off' " + 
				"	when cl.status = 5 then 'Completed' " + 
				"	when cl.status = 6 then 'Pending accept code of conduct' " + 
				"	when cl.status = 7 then 'Awaiting DHET'   " + 
				"	when cl.status = 8 then 'Pending Final Approval' " + 
				"	when cl.status = 9 then 'Withdrawn'   " + 
				"	when cl.status = 10 then 'N/A' " + 
				"	when cl.status = 11 then 'Recommended' " + 
				"	when cl.status = 12 then 'Appealed' " + 
				"	when cl.status = 13 then 'Pending Committee Approval' " + 
				"	when cl.status = 14 then 'Approved By ETQA Review Committee' " + 
				"	when cl.status = 15 then 'Rejected By ETQA Review Commitee' " + 
				"	when cl.status = 16 then 'Requested Higher Allocation' " + 
				"	when cl.status = 17 then 'Accepted MOA' " + 
				"	when cl.status = 18 then 'Requested Change' " + 
				"	when cl.status = 19 then 'Rejected By MANCO Review' " + 
				"	when cl.status = 20 then 'Rejected By Learner Review Committee' " + 
				"	when cl.status = 21 then 'Qualification Obtained' " + 
				"	when cl.status = 22 then 'Deactivated' " + 
				"	when cl.status = 23 then 'Project Terminated Manager Approval' " + 
				"	when cl.status = 24 then 'Suspend Project' " + 
				"	when cl.status = 25 then 'Project Terminated' " + 
				"	when cl.status = 26 then 'Pending Review Approval' " + 
				"	when cl.status = 27 then 'Uphold' " + 
				"	when cl.status = 28 then 'Pending Resubmission'   " + 
				"	when cl.status = 29 then 'Awaiting NAMB' " + 
				"	when cl.status = 30 then 'Pending Withdrawal' " + 
				"	when cl.status = 31 then 'Pending Inverstigation' " + 
				"	when cl.status = 32 then 'Pending Change Approval' " + 
				"	when cl.status = 33 then 'Not Competent' " + 
				"end as 'approvalStatus' " + 
				
				",CASE when cl.employment_status = 0 then 'Employed' " + 
				"	when cl.employment_status = 1 then 'Unemployed' " + 
				"end as 'employmentStatus' " + 
				
				",CASE when u.urban_rural_enum = 0 then 'Urban' " + 
				"	   when u.urban_rural_enum = 1 then 'Rural' " + 
				"end as 'urbanRural' " + 
				
				",CASE when u.disability = 0 then 'Yes' " + 
				"	   when u.disability = 1 then 'No' " + 
				"end as 'disability' " +
				
				",CASE when cl.merseta_funded = 0 then 'Yes' " + 
				"	   when cl.merseta_funded = 1 then 'No' " + 
				"end as 'fundedBy' " +
				
				",CASE when cl.legacy_id is not null then 'Yes' " + 
				"	   when cl.legacy_id is null then 'No' " + 
				"end as 'legacyLearner' " +
				
				",CASE when child.ofo_code is not null then child.ofo_code " + 
				"	   when parent.ofo_code is not null then parent.ofo_code " + 
				"end as 'ofoCode' " +
				
				",CASE " + 
				"	when cl.learner_status = 0 then 'Application' " + 
				"	when cl.learner_status = 1 then 'Registered' " + 
				"	when cl.learner_status = 2 then 'Transfer Pending Approval' " + 
				"	when cl.learner_status = 3 then 'Terminated' " + 
				"	when cl.learner_status = 4 then 'Pending Termination' " + 
				"	when cl.learner_status = 5 then 'Pending Change Approval' " + 
				"	when cl.learner_status = 6 then 'Completed' " + 
				"	when cl.learner_status = 7 then 'Pending Trade Test' " + 
				"	when cl.learner_status = 8 then 'Pending ARPL Trade Test' " + 
				"	when cl.learner_status = 9 then 'Pending Review Approval' " + 
				"	when cl.learner_status = 10 then 'Qualification Obtained' " + 
				"	when cl.learner_status = 11 then 'Pending non-merSETA Qualification Approval' " + 
				"	when cl.learner_status = 12 then 'Pending Verification of Assesment Approval' " + 
				"	when cl.learner_status = 13 then 'Pending Completion Letter Approval' " + 
				"	when cl.learner_status = 14 then 'Withdrawn' " + 
				"	when cl.learner_status = 15 then 'Rejected' " + 
				"	when cl.learner_status = 16 then 'Achieved' " + 
				"	when cl.learner_status = 17 then 'Pending Lost Time' " + 
				"end as 'learnerStatus' " + 
								
				", CONCAT(ad.address_line_1, ' ',ad.address_line_2,' ',ad.address_line_3,' ',lt.description, ' ',ad.postcode) as 'residentialAddress' " + 
				", CONCAT(pa.address_line_1, ' ',pa.address_line_2,' ',ad.address_line_3,' ',ltp.description, ' ',pa.postcode) as 'postalAddress' " +
				
				
				",(SELECT GROUP_CONCAT(lang.description,' ') " + 
				"		from language lang " + 
				"		where lang.id in  " + 
				"			(select language_id from users_language where user_id = u.id and home_language = true) " + 
				"		) as 'language' " + 
				
				",(SELECT ofo.description " + 
				"	from ofo_codes ofo " + 
				"	where ofo.id = cl.ofo_codes_id) " + 
				" as 'ofoCodeDescription' " + 

				", gen.gender_name as 'gender' " + 
				", u.date_of_birth as 'dateOfBirth' " + 
				", equi.description as 'equity' " + 
				
			
				", nation.description as 'nationality' " +
				", statssa.description as 'startSaAreaCode' " +		
				", cl.date_learner_registered as 'qmrEnteredDate' " + 
				", cl.date_learner_completed as 'qmrCompletedDate' " + 

				"from " + 
				
				"company_learners cl " + 
				"left join users u on u.id = cl.user_id " + 
				"left join gender gen on gen.id = u.gender_id " + 
				"left join address ad on ad.id = u.residential_address_id " + 
				"left join address pa on pa.id = u.postal_address_id " + 				
				"left join municipality m on m.id = ad.municipality_id " +
				"left join towns lt on lt.id = ad.town_id " + 
				"left join towns ltp on ltp.id = pa.town_id " + 
				"left join region_town lrt on lrt.town_id = lt.id " + 
				"left join region lr on lr.id = lrt.region_id " + 
				
				"left join statssa_area_code statssa on statssa.id = ad.stats_saarea_code_id " + 
				
				"left join users cu on cu.id = cl.create_user_id " + 
				"left join company e on e.id = cl.employer_id " + 
				"left join address ea on ea.id = e.residential_address_id " + 
				"left join municipality municipal on municipal.id = ea.municipality_id " + 
				"left join towns et on et.id = ea.town_id " + 
				"left join region_town ert on ert.town_id = et.id " + 
				"left join region er on er.id = ert.region_id " +
				
				"left join sic_code siccode on siccode.id = e.sic_code_id " +
				"left join chamber cham on cham.id = siccode.chamber_id " +
				"left join equity equi on equi.id = u.equity_id " +
				
				"left join ofo_codes child on child.id = cl.ofo_codes_id " +
				"left join ofo_codes parent on parent.id = child.ofo_codes_id " +

				"left join nationality nation on nation.id = u.nationality_id " +

				"left join company c on c.id = cl.company_id " + 
				"left join intervention_type it on it.id = cl.intervention_type_id " + 
				"left join funding f on f.id = cl.dunding_id " + 
				"left join saqa_qualification hq on hq.id = cl.highest_qualification_id " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id " + 
				"left join skills_program sp on sp.id = cl.skills_program_id " + 
				"left join skills_set ss on ss.id = cl.skills_set_id " + 
				"left join learnership ls on ls.id = cl.learnership_id " + 
				"left join saqa_unitstandard us on us.id = cl.unit_standard_id " + 				
				
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id "+
				"left join company pr on pr.id = tpa.company_id " + 
				"left join address a on a.id = pr.residential_address_id " + 
				"left join municipality mun on mun.id = a.municipality_id " + 
				"left join towns t on t.id = a.town_id " + 
				"left join region_town rt on rt.town_id = t.id " + 
				"left join region r on r.id = rt.region_id " + 
				"where cl.intervention_type_id is not null " + 
				"and cl.learner_status = :learnerStatusEnum " + 
				"order by cl.id asc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("learnerStatusEnum", learnerStatusEnum.ordinal());
		return (List<CompanyLearnersBean>)super.nativeSelectSqlList(sql, CompanyLearnersBean.class, parameters);
	}
	
	public List<CompanyLearnersBean> generateCompanyLearnerReport(InterventionType interventionType) throws Exception {
		String sql ="select  " + 
				" cl.create_date as 'createDate' " +
				", u.first_name as 'firstName' "  + 
				", u.middle_name as 'middleName' "  + 
				", u.last_name as 'lastName' "  + 
				", u.email as 'learnerEmail' "  + 
				", u.cell_number as 'cellNumber' " + 
				", u.tel_number as 'telNumber' " + 
				", u.rsa_id_number as 'rsaIDNumber' " + 
				", u.passport_number as 'passportNumber' " + 
				", m.municipality_desc as learnerMunicipality " + 
				", lr.description as 'region' " + 
				", er.description as 'employerRegion' " +
				", r.description as 'providerRegion' " +
				", mun.municipality_desc as 'providerMunicipality' " +
				", municipal.municipality_desc as 'employerMunicipality' " +
				", cham.description as 'chamber' " +
				", it.description as 'interventionType' " + 
				
				", sq.qualificationid_string as 'qualificationCode' " + 
				", sq.qualificationtitle as 'qualificationTitle' " + 
				", sp.programme_id as 'skillsProgrammeCode' " + 
				", sp.description as 'skillsProgrammeDescription' " + 
				", ss.programme_id as 'skillsSetCode' " + 
				", ss.description as 'skillsSetDescription' " + 
				", ls.code as 'learnershipCode' " + 
				", ls.description as 'learnershipDescription' " + 
				", us.unitstandardid as 'unitStandardCode' " + 
				", us.unitstdtitle as 'unitStandardDescription' " + 
				", cl.non_credid_bearing_description as 'nonCreditBearingDescription' " + 				
				
				", cl.approval_date as 'approvalDate' " + 
				", cl.date_learner_registered as 'dateLearnerRegistered' " + 
				", cl.registered_date as 'registrationDate' " +
				", cl.commencement_date as 'commencementDate' " + 
				", cl.completion_date as 'completionDate' " +
				
				",CONCAT(hq.qualificationid, ' (', hq.qualificationtitle,')') as 'highestQualification' " + 
				
				", CASE " + 
				"	when cl.highest_education_enum = 0 then 'Below Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 1 then 'Above Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 2 then 'N1' " + 
				"	when cl.highest_education_enum = 3 then 'N2' " + 
				"	when cl.highest_education_enum = 4 then 'N3' " + 
				"	when cl.highest_education_enum = 5 then 'N4' " + 
				"	when cl.highest_education_enum = 6 then 'N5' " + 
				"	when cl.highest_education_enum = 7 then 'N6' " + 
				"	when cl.highest_education_enum = 8 then 'NCV Level 2' " + 
				"	when cl.highest_education_enum = 9 then 'NCV Level 3' " + 
				"	when cl.highest_education_enum = 10 then 'NCV Level 4' " + 
				"	when cl.highest_education_enum = 11 then 'S1' " + 
				"	when cl.highest_education_enum = 12 then 'S2' " + 
				"	when cl.highest_education_enum = 13 then 'S3' " + 
				"end as 'highestEducation' " + 
				
				", CONCAT(e.company_name, ' (', e.levy_number,')') as 'employerInfor' " + 
				", CONCAT(pr.company_name, ' (', pr.levy_number,')' , tpa.accreditation_number) as 'providerInfor' " + 

				", cl.registration_number as 'registrationNumber' " + 	
				
				", CONCAT(cu.first_name, ' ', cu.last_name, ' (', cu.email,')') as 'createUser' " + 
				
				",CASE " + 
				"	when cl.status = 0 then 'Approved' " + 
				"	when cl.status = 1 then 'Rejected' " + 
				"	when cl.status = 2 then 'Pending Manager Approval' " + 
				"	when cl.status = 3 then 'Pending Approval' " + 
				"	when cl.status = 4 then 'Pending Sign Off' " + 
				"	when cl.status = 5 then 'Completed' " + 
				"	when cl.status = 6 then 'Pending accept code of conduct' " + 
				"	when cl.status = 7 then 'Awaiting DHET'   " + 
				"	when cl.status = 8 then 'Pending Final Approval' " + 
				"	when cl.status = 9 then 'Withdrawn'   " + 
				"	when cl.status = 10 then 'N/A' " + 
				"	when cl.status = 11 then 'Recommended' " + 
				"	when cl.status = 12 then 'Appealed' " + 
				"	when cl.status = 13 then 'Pending Committee Approval' " + 
				"	when cl.status = 14 then 'Approved By ETQA Review Committee' " + 
				"	when cl.status = 15 then 'Rejected By ETQA Review Commitee' " + 
				"	when cl.status = 16 then 'Requested Higher Allocation' " + 
				"	when cl.status = 17 then 'Accepted MOA' " + 
				"	when cl.status = 18 then 'Requested Change' " + 
				"	when cl.status = 19 then 'Rejected By MANCO Review' " + 
				"	when cl.status = 20 then 'Rejected By Learner Review Committee' " + 
				"	when cl.status = 21 then 'Qualification Obtained' " + 
				"	when cl.status = 22 then 'Deactivated' " + 
				"	when cl.status = 23 then 'Project Terminated Manager Approval' " + 
				"	when cl.status = 24 then 'Suspend Project' " + 
				"	when cl.status = 25 then 'Project Terminated' " + 
				"	when cl.status = 26 then 'Pending Review Approval' " + 
				"	when cl.status = 27 then 'Uphold' " + 
				"	when cl.status = 28 then 'Pending Resubmission'   " + 
				"	when cl.status = 29 then 'Awaiting NAMB' " + 
				"	when cl.status = 30 then 'Pending Withdrawal' " + 
				"	when cl.status = 31 then 'Pending Inverstigation' " + 
				"	when cl.status = 32 then 'Pending Change Approval' " + 
				"	when cl.status = 33 then 'Not Competent' " + 
				"end as 'approvalStatus' " + 
				
				",CASE when cl.employment_status = 0 then 'Employed' " + 
				"	when cl.employment_status = 1 then 'Unemployed' " + 
				"end as 'employmentStatus' " + 
				
				",CASE when u.urban_rural_enum = 0 then 'Urban' " + 
				"	   when u.urban_rural_enum = 1 then 'Rural' " + 
				"end as 'urbanRural' " + 
				
				",CASE when u.disability = 0 then 'Yes' " + 
				"	   when u.disability = 1 then 'No' " + 
				"end as 'disability' " +
				
				",CASE when cl.merseta_funded = 0 then 'Yes' " + 
				"	   when cl.merseta_funded = 1 then 'No' " + 
				"end as 'fundedBy' " +
				
				",CASE when cl.legacy_id is not null then 'Yes' " + 
				"	   when cl.legacy_id is null then 'No' " + 
				"end as 'legacyLearner' " +
				
				",CASE when child.ofo_code is not null then child.ofo_code " + 
				"	   when parent.ofo_code is not null then parent.ofo_code " + 
				"end as 'ofoCode' " +
				
				",CASE " + 
				"	when cl.learner_status = 0 then 'Application' " + 
				"	when cl.learner_status = 1 then 'Registered' " + 
				"	when cl.learner_status = 2 then 'Transfer Pending Approval' " + 
				"	when cl.learner_status = 3 then 'Terminated' " + 
				"	when cl.learner_status = 4 then 'Pending Termination' " + 
				"	when cl.learner_status = 5 then 'Pending Change Approval' " + 
				"	when cl.learner_status = 6 then 'Completed' " + 
				"	when cl.learner_status = 7 then 'Pending Trade Test' " + 
				"	when cl.learner_status = 8 then 'Pending ARPL Trade Test' " + 
				"	when cl.learner_status = 9 then 'Pending Review Approval' " + 
				"	when cl.learner_status = 10 then 'Qualification Obtained' " + 
				"	when cl.learner_status = 11 then 'Pending non-merSETA Qualification Approval' " + 
				"	when cl.learner_status = 12 then 'Pending Verification of Assesment Approval' " + 
				"	when cl.learner_status = 13 then 'Pending Completion Letter Approval' " + 
				"	when cl.learner_status = 14 then 'Withdrawn' " + 
				"	when cl.learner_status = 15 then 'Rejected' " + 
				"	when cl.learner_status = 16 then 'Achieved' " + 
				"	when cl.learner_status = 17 then 'Pending Lost Time' " + 
				"end as 'learnerStatus' " + 
								
				", CONCAT(ad.address_line_1, ' ',ad.address_line_2,' ',ad.address_line_3,' ',lt.description, ' ',ad.postcode) as 'residentialAddress' " + 
				", CONCAT(pa.address_line_1, ' ',pa.address_line_2,' ',ad.address_line_3,' ',ltp.description, ' ',pa.postcode) as 'postalAddress' " +
				
				
				",(SELECT GROUP_CONCAT(lang.description,' ') " + 
				"		from language lang " + 
				"		where lang.id in  " + 
				"			(select language_id from users_language where user_id = u.id and home_language = true) " + 
				"		) as 'language' " + 
				
				",(SELECT ofo.description " + 
				"	from ofo_codes ofo " + 
				"	where ofo.id = cl.ofo_codes_id) " + 
				" as 'ofoCodeDescription' " + 

				", gen.gender_name as 'gender' " + 
				", u.date_of_birth as 'dateOfBirth' " + 
				", equi.description as 'equity' " + 
				
			
				", nation.description as 'nationality' " +
				", statssa.description as 'startSaAreaCode' " +		
				", cl.date_learner_registered as 'qmrEnteredDate' " + 
				", cl.date_learner_completed as 'qmrCompletedDate' " + 

				"from " + 
				
				"company_learners cl " + 
				"left join users u on u.id = cl.user_id " + 
				"left join gender gen on gen.id = u.gender_id " + 
				"left join address ad on ad.id = u.residential_address_id " + 
				"left join address pa on pa.id = u.postal_address_id " + 				
				"left join municipality m on m.id = ad.municipality_id " +
				"left join towns lt on lt.id = ad.town_id " + 
				"left join towns ltp on ltp.id = pa.town_id " + 
				"left join region_town lrt on lrt.town_id = lt.id " + 
				"left join region lr on lr.id = lrt.region_id " + 
				
				"left join statssa_area_code statssa on statssa.id = ad.stats_saarea_code_id " + 
				
				"left join users cu on cu.id = cl.create_user_id " + 
				"left join company e on e.id = cl.employer_id " + 
				"left join address ea on ea.id = e.residential_address_id " + 
				"left join municipality municipal on municipal.id = ea.municipality_id " + 
				"left join towns et on et.id = ea.town_id " + 
				"left join region_town ert on ert.town_id = et.id " + 
				"left join region er on er.id = ert.region_id " +
				
				"left join sic_code siccode on siccode.id = e.sic_code_id " +
				"left join chamber cham on cham.id = siccode.chamber_id " +
				"left join equity equi on equi.id = u.equity_id " +
				
				"left join ofo_codes child on child.id = cl.ofo_codes_id " +
				"left join ofo_codes parent on parent.id = child.ofo_codes_id " +

				"left join nationality nation on nation.id = u.nationality_id " +

				"left join company c on c.id = cl.company_id " + 
				"left join intervention_type it on it.id = cl.intervention_type_id " + 
				"left join funding f on f.id = cl.dunding_id " + 
				"left join saqa_qualification hq on hq.id = cl.highest_qualification_id " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id " + 
				"left join skills_program sp on sp.id = cl.skills_program_id " + 
				"left join skills_set ss on ss.id = cl.skills_set_id " + 
				"left join learnership ls on ls.id = cl.learnership_id " + 
				"left join saqa_unitstandard us on us.id = cl.unit_standard_id " + 				
				
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id "+
				"left join company pr on pr.id = tpa.company_id " + 
				"left join address a on a.id = pr.residential_address_id " + 
				"left join municipality mun on mun.id = a.municipality_id " + 
				"left join towns t on t.id = a.town_id " + 
				"left join region_town rt on rt.town_id = t.id " + 
				"left join region r on r.id = rt.region_id " +  
				"where it.id = :interventionID " + 
				"order by cl.id asc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("interventionID", interventionType.getId());
		return (List<CompanyLearnersBean>)super.nativeSelectSqlList(sql, CompanyLearnersBean.class, parameters);
	}
	
	public List<CompanyLearnersBean> generateCompanyLearnerReport(Region region, InterventionType interventionType, LearnerStatusEnum learnerStatusEnum) throws Exception {
		String sql ="select  " + 
				" cl.create_date as 'createDate' " +
				", u.first_name as 'firstName' "  + 
				", u.middle_name as 'middleName' "  + 
				", u.last_name as 'lastName' "  + 
				", u.email as 'learnerEmail' "  + 
				", u.cell_number as 'cellNumber' " + 
				", u.tel_number as 'telNumber' " + 
				", u.rsa_id_number as 'rsaIDNumber' " + 
				", u.passport_number as 'passportNumber' " + 
				", m.municipality_desc as learnerMunicipality " + 
				", lr.description as 'region' " + 
				", er.description as 'employerRegion' " +
				", r.description as 'providerRegion' " +
				", mun.municipality_desc as 'providerMunicipality' " +
				", municipal.municipality_desc as 'employerMunicipality' " +
				", cham.description as 'chamber' " +
				", it.description as 'interventionType' " + 
				
				", sq.qualificationid_string as 'qualificationCode' " + 
				", sq.qualificationtitle as 'qualificationTitle' " + 
				", sp.programme_id as 'skillsProgrammeCode' " + 
				", sp.description as 'skillsProgrammeDescription' " + 
				", ss.programme_id as 'skillsSetCode' " + 
				", ss.description as 'skillsSetDescription' " + 
				", ls.code as 'learnershipCode' " + 
				", ls.description as 'learnershipDescription' " + 
				", us.unitstandardid as 'unitStandardCode' " + 
				", us.unitstdtitle as 'unitStandardDescription' " + 
				", cl.non_credid_bearing_description as 'nonCreditBearingDescription' " + 				
				
				", cl.approval_date as 'approvalDate' " + 
				", cl.date_learner_registered as 'dateLearnerRegistered' " + 
				", cl.registered_date as 'registrationDate' " +
				", cl.commencement_date as 'commencementDate' " + 
				", cl.completion_date as 'completionDate' " +
				
				",CONCAT(hq.qualificationid, ' (', hq.qualificationtitle,')') as 'highestQualification' " + 
				
				", CASE " + 
				"	when cl.highest_education_enum = 0 then 'Below Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 1 then 'Above Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 2 then 'N1' " + 
				"	when cl.highest_education_enum = 3 then 'N2' " + 
				"	when cl.highest_education_enum = 4 then 'N3' " + 
				"	when cl.highest_education_enum = 5 then 'N4' " + 
				"	when cl.highest_education_enum = 6 then 'N5' " + 
				"	when cl.highest_education_enum = 7 then 'N6' " + 
				"	when cl.highest_education_enum = 8 then 'NCV Level 2' " + 
				"	when cl.highest_education_enum = 9 then 'NCV Level 3' " + 
				"	when cl.highest_education_enum = 10 then 'NCV Level 4' " + 
				"	when cl.highest_education_enum = 11 then 'S1' " + 
				"	when cl.highest_education_enum = 12 then 'S2' " + 
				"	when cl.highest_education_enum = 13 then 'S3' " + 
				"end as 'highestEducation' " + 
				
				", CONCAT(e.company_name, ' (', e.levy_number,')') as 'employerInfor' " + 
				", CONCAT(pr.company_name, ' (', pr.levy_number,')' , tpa.accreditation_number) as 'providerInfor' " + 

				", cl.registration_number as 'registrationNumber' " + 	
				
				", CONCAT(cu.first_name, ' ', cu.last_name, ' (', cu.email,')') as 'createUser' " + 
				
				",CASE " + 
				"	when cl.status = 0 then 'Approved' " + 
				"	when cl.status = 1 then 'Rejected' " + 
				"	when cl.status = 2 then 'Pending Manager Approval' " + 
				"	when cl.status = 3 then 'Pending Approval' " + 
				"	when cl.status = 4 then 'Pending Sign Off' " + 
				"	when cl.status = 5 then 'Completed' " + 
				"	when cl.status = 6 then 'Pending accept code of conduct' " + 
				"	when cl.status = 7 then 'Awaiting DHET'   " + 
				"	when cl.status = 8 then 'Pending Final Approval' " + 
				"	when cl.status = 9 then 'Withdrawn'   " + 
				"	when cl.status = 10 then 'N/A' " + 
				"	when cl.status = 11 then 'Recommended' " + 
				"	when cl.status = 12 then 'Appealed' " + 
				"	when cl.status = 13 then 'Pending Committee Approval' " + 
				"	when cl.status = 14 then 'Approved By ETQA Review Committee' " + 
				"	when cl.status = 15 then 'Rejected By ETQA Review Commitee' " + 
				"	when cl.status = 16 then 'Requested Higher Allocation' " + 
				"	when cl.status = 17 then 'Accepted MOA' " + 
				"	when cl.status = 18 then 'Requested Change' " + 
				"	when cl.status = 19 then 'Rejected By MANCO Review' " + 
				"	when cl.status = 20 then 'Rejected By Learner Review Committee' " + 
				"	when cl.status = 21 then 'Qualification Obtained' " + 
				"	when cl.status = 22 then 'Deactivated' " + 
				"	when cl.status = 23 then 'Project Terminated Manager Approval' " + 
				"	when cl.status = 24 then 'Suspend Project' " + 
				"	when cl.status = 25 then 'Project Terminated' " + 
				"	when cl.status = 26 then 'Pending Review Approval' " + 
				"	when cl.status = 27 then 'Uphold' " + 
				"	when cl.status = 28 then 'Pending Resubmission'   " + 
				"	when cl.status = 29 then 'Awaiting NAMB' " + 
				"	when cl.status = 30 then 'Pending Withdrawal' " + 
				"	when cl.status = 31 then 'Pending Inverstigation' " + 
				"	when cl.status = 32 then 'Pending Change Approval' " + 
				"	when cl.status = 33 then 'Not Competent' " + 
				"end as 'approvalStatus' " + 
				
				",CASE when cl.employment_status = 0 then 'Employed' " + 
				"	when cl.employment_status = 1 then 'Unemployed' " + 
				"end as 'employmentStatus' " + 
				
				",CASE when u.urban_rural_enum = 0 then 'Urban' " + 
				"	   when u.urban_rural_enum = 1 then 'Rural' " + 
				"end as 'urbanRural' " + 
				
				",CASE when u.disability = 0 then 'Yes' " + 
				"	   when u.disability = 1 then 'No' " + 
				"end as 'disability' " +
				
				",CASE when cl.merseta_funded = 0 then 'Yes' " + 
				"	   when cl.merseta_funded = 1 then 'No' " + 
				"end as 'fundedBy' " +
				
				",CASE when cl.legacy_id is not null then 'Yes' " + 
				"	   when cl.legacy_id is null then 'No' " + 
				"end as 'legacyLearner' " +
				
				",CASE when child.ofo_code is not null then child.ofo_code " + 
				"	   when parent.ofo_code is not null then parent.ofo_code " + 
				"end as 'ofoCode' " +
				
				",CASE " + 
				"	when cl.learner_status = 0 then 'Application' " + 
				"	when cl.learner_status = 1 then 'Registered' " + 
				"	when cl.learner_status = 2 then 'Transfer Pending Approval' " + 
				"	when cl.learner_status = 3 then 'Terminated' " + 
				"	when cl.learner_status = 4 then 'Pending Termination' " + 
				"	when cl.learner_status = 5 then 'Pending Change Approval' " + 
				"	when cl.learner_status = 6 then 'Completed' " + 
				"	when cl.learner_status = 7 then 'Pending Trade Test' " + 
				"	when cl.learner_status = 8 then 'Pending ARPL Trade Test' " + 
				"	when cl.learner_status = 9 then 'Pending Review Approval' " + 
				"	when cl.learner_status = 10 then 'Qualification Obtained' " + 
				"	when cl.learner_status = 11 then 'Pending non-merSETA Qualification Approval' " + 
				"	when cl.learner_status = 12 then 'Pending Verification of Assesment Approval' " + 
				"	when cl.learner_status = 13 then 'Pending Completion Letter Approval' " + 
				"	when cl.learner_status = 14 then 'Withdrawn' " + 
				"	when cl.learner_status = 15 then 'Rejected' " + 
				"	when cl.learner_status = 16 then 'Achieved' " + 
				"	when cl.learner_status = 17 then 'Pending Lost Time' " + 
				"end as 'learnerStatus' " + 
								
				", CONCAT(ad.address_line_1, ' ',ad.address_line_2,' ',ad.address_line_3,' ',lt.description, ' ',ad.postcode) as 'residentialAddress' " + 
				", CONCAT(pa.address_line_1, ' ',pa.address_line_2,' ',ad.address_line_3,' ',ltp.description, ' ',pa.postcode) as 'postalAddress' " +
				
				
				",(SELECT GROUP_CONCAT(lang.description,' ') " + 
				"		from language lang " + 
				"		where lang.id in  " + 
				"			(select language_id from users_language where user_id = u.id and home_language = true) " + 
				"		) as 'language' " + 
				
				",(SELECT ofo.description " + 
				"	from ofo_codes ofo " + 
				"	where ofo.id = cl.ofo_codes_id) " + 
				" as 'ofoCodeDescription' " + 

				", gen.gender_name as 'gender' " + 
				", u.date_of_birth as 'dateOfBirth' " + 
				", equi.description as 'equity' " + 
				
			
				", nation.description as 'nationality' " +
				", statssa.description as 'startSaAreaCode' " +		
				", cl.date_learner_registered as 'qmrEnteredDate' " + 
				", cl.date_learner_completed as 'qmrCompletedDate' " + 

				"from " + 
				
				"company_learners cl " + 
				"left join users u on u.id = cl.user_id " + 
				"left join gender gen on gen.id = u.gender_id " + 
				"left join address ad on ad.id = u.residential_address_id " + 
				"left join address pa on pa.id = u.postal_address_id " + 				
				"left join municipality m on m.id = ad.municipality_id " +
				"left join towns lt on lt.id = ad.town_id " + 
				"left join towns ltp on ltp.id = pa.town_id " + 
				"left join region_town lrt on lrt.town_id = lt.id " + 
				"left join region lr on lr.id = lrt.region_id " + 
				
				"left join statssa_area_code statssa on statssa.id = ad.stats_saarea_code_id " + 
				
				"left join users cu on cu.id = cl.create_user_id " + 
				"left join company e on e.id = cl.employer_id " + 
				"left join address ea on ea.id = e.residential_address_id " + 
				"left join municipality municipal on municipal.id = ea.municipality_id " + 
				"left join towns et on et.id = ea.town_id " + 
				"left join region_town ert on ert.town_id = et.id " + 
				"left join region er on er.id = ert.region_id " +
				
				"left join sic_code siccode on siccode.id = e.sic_code_id " +
				"left join chamber cham on cham.id = siccode.chamber_id " +
				"left join equity equi on equi.id = u.equity_id " +
				
				"left join ofo_codes child on child.id = cl.ofo_codes_id " +
				"left join ofo_codes parent on parent.id = child.ofo_codes_id " +

				"left join nationality nation on nation.id = u.nationality_id " +

				"left join company c on c.id = cl.company_id " + 
				"left join intervention_type it on it.id = cl.intervention_type_id " + 
				"left join funding f on f.id = cl.dunding_id " + 
				"left join saqa_qualification hq on hq.id = cl.highest_qualification_id " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id " + 
				"left join skills_program sp on sp.id = cl.skills_program_id " + 
				"left join skills_set ss on ss.id = cl.skills_set_id " + 
				"left join learnership ls on ls.id = cl.learnership_id " + 
				"left join saqa_unitstandard us on us.id = cl.unit_standard_id " + 				
				
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id "+
				"left join company pr on pr.id = tpa.company_id " + 
				"left join address a on a.id = pr.residential_address_id " + 
				"left join municipality mun on mun.id = a.municipality_id " + 
				"left join towns t on t.id = a.town_id " + 
				"left join region_town rt on rt.town_id = t.id " + 
				"left join region r on r.id = rt.region_id " +  
				"where it.id = :interventionID " + 
				"and r.id = :regionID " +
				"and cl.learner_status = :learnerStatusEnum " + 
				"order by cl.id asc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("regionID", region.getId());
		parameters.put("interventionID", interventionType.getId());
		parameters.put("learnerStatusEnum", learnerStatusEnum.ordinal());
		return (List<CompanyLearnersBean>)super.nativeSelectSqlList(sql, CompanyLearnersBean.class, parameters);
	}
	
	public List<CompanyLearnersBean> generateCompanyLearnerReport(Region region, InterventionType interventionType) throws Exception {
		String sql ="select  " + 
				" cl.create_date as 'createDate' " +
				", u.first_name as 'firstName' "  + 
				", u.middle_name as 'middleName' "  + 
				", u.last_name as 'lastName' "  + 
				", u.email as 'learnerEmail' "  + 
				", u.cell_number as 'cellNumber' " + 
				", u.tel_number as 'telNumber' " + 
				", u.rsa_id_number as 'rsaIDNumber' " + 
				", u.passport_number as 'passportNumber' " + 
				", m.municipality_desc as learnerMunicipality " + 
				", lr.description as 'region' " + 
				", er.description as 'employerRegion' " +
				", r.description as 'providerRegion' " +
				", mun.municipality_desc as 'providerMunicipality' " +
				", municipal.municipality_desc as 'employerMunicipality' " +
				", cham.description as 'chamber' " +
				", it.description as 'interventionType' " + 
				
				", sq.qualificationid_string as 'qualificationCode' " + 
				", sq.qualificationtitle as 'qualificationTitle' " + 
				", sp.programme_id as 'skillsProgrammeCode' " + 
				", sp.description as 'skillsProgrammeDescription' " + 
				", ss.programme_id as 'skillsSetCode' " + 
				", ss.description as 'skillsSetDescription' " + 
				", ls.code as 'learnershipCode' " + 
				", ls.description as 'learnershipDescription' " + 
				", us.unitstandardid as 'unitStandardCode' " + 
				", us.unitstdtitle as 'unitStandardDescription' " + 
				", cl.non_credid_bearing_description as 'nonCreditBearingDescription' " + 				
				
				", cl.approval_date as 'approvalDate' " + 
				", cl.date_learner_registered as 'dateLearnerRegistered' " + 
				", cl.registered_date as 'registrationDate' " +
				", cl.commencement_date as 'commencementDate' " + 
				", cl.completion_date as 'completionDate' " +
				
				",CONCAT(hq.qualificationid, ' (', hq.qualificationtitle,')') as 'highestQualification' " + 
				
				", CASE " + 
				"	when cl.highest_education_enum = 0 then 'Below Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 1 then 'Above Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 2 then 'N1' " + 
				"	when cl.highest_education_enum = 3 then 'N2' " + 
				"	when cl.highest_education_enum = 4 then 'N3' " + 
				"	when cl.highest_education_enum = 5 then 'N4' " + 
				"	when cl.highest_education_enum = 6 then 'N5' " + 
				"	when cl.highest_education_enum = 7 then 'N6' " + 
				"	when cl.highest_education_enum = 8 then 'NCV Level 2' " + 
				"	when cl.highest_education_enum = 9 then 'NCV Level 3' " + 
				"	when cl.highest_education_enum = 10 then 'NCV Level 4' " + 
				"	when cl.highest_education_enum = 11 then 'S1' " + 
				"	when cl.highest_education_enum = 12 then 'S2' " + 
				"	when cl.highest_education_enum = 13 then 'S3' " + 
				"end as 'highestEducation' " + 
				
				", CONCAT(e.company_name, ' (', e.levy_number,')') as 'employerInfor' " + 
				", CONCAT(pr.company_name, ' (', pr.levy_number,')' , tpa.accreditation_number) as 'providerInfor' " + 

				", cl.registration_number as 'registrationNumber' " + 	
				
				", CONCAT(cu.first_name, ' ', cu.last_name, ' (', cu.email,')') as 'createUser' " + 
				
				",CASE " + 
				"	when cl.status = 0 then 'Approved' " + 
				"	when cl.status = 1 then 'Rejected' " + 
				"	when cl.status = 2 then 'Pending Manager Approval' " + 
				"	when cl.status = 3 then 'Pending Approval' " + 
				"	when cl.status = 4 then 'Pending Sign Off' " + 
				"	when cl.status = 5 then 'Completed' " + 
				"	when cl.status = 6 then 'Pending accept code of conduct' " + 
				"	when cl.status = 7 then 'Awaiting DHET'   " + 
				"	when cl.status = 8 then 'Pending Final Approval' " + 
				"	when cl.status = 9 then 'Withdrawn'   " + 
				"	when cl.status = 10 then 'N/A' " + 
				"	when cl.status = 11 then 'Recommended' " + 
				"	when cl.status = 12 then 'Appealed' " + 
				"	when cl.status = 13 then 'Pending Committee Approval' " + 
				"	when cl.status = 14 then 'Approved By ETQA Review Committee' " + 
				"	when cl.status = 15 then 'Rejected By ETQA Review Commitee' " + 
				"	when cl.status = 16 then 'Requested Higher Allocation' " + 
				"	when cl.status = 17 then 'Accepted MOA' " + 
				"	when cl.status = 18 then 'Requested Change' " + 
				"	when cl.status = 19 then 'Rejected By MANCO Review' " + 
				"	when cl.status = 20 then 'Rejected By Learner Review Committee' " + 
				"	when cl.status = 21 then 'Qualification Obtained' " + 
				"	when cl.status = 22 then 'Deactivated' " + 
				"	when cl.status = 23 then 'Project Terminated Manager Approval' " + 
				"	when cl.status = 24 then 'Suspend Project' " + 
				"	when cl.status = 25 then 'Project Terminated' " + 
				"	when cl.status = 26 then 'Pending Review Approval' " + 
				"	when cl.status = 27 then 'Uphold' " + 
				"	when cl.status = 28 then 'Pending Resubmission'   " + 
				"	when cl.status = 29 then 'Awaiting NAMB' " + 
				"	when cl.status = 30 then 'Pending Withdrawal' " + 
				"	when cl.status = 31 then 'Pending Inverstigation' " + 
				"	when cl.status = 32 then 'Pending Change Approval' " + 
				"	when cl.status = 33 then 'Not Competent' " + 
				"end as 'approvalStatus' " + 
				
				",CASE when cl.employment_status = 0 then 'Employed' " + 
				"	when cl.employment_status = 1 then 'Unemployed' " + 
				"end as 'employmentStatus' " + 
				
				",CASE when u.urban_rural_enum = 0 then 'Urban' " + 
				"	   when u.urban_rural_enum = 1 then 'Rural' " + 
				"end as 'urbanRural' " + 
				
				",CASE when u.disability = 0 then 'Yes' " + 
				"	   when u.disability = 1 then 'No' " + 
				"end as 'disability' " +
				
				",CASE when cl.merseta_funded = 0 then 'Yes' " + 
				"	   when cl.merseta_funded = 1 then 'No' " + 
				"end as 'fundedBy' " +
				
				",CASE when cl.legacy_id is not null then 'Yes' " + 
				"	   when cl.legacy_id is null then 'No' " + 
				"end as 'legacyLearner' " +
				
				",CASE when child.ofo_code is not null then child.ofo_code " + 
				"	   when parent.ofo_code is not null then parent.ofo_code " + 
				"end as 'ofoCode' " +
				
				",CASE " + 
				"	when cl.learner_status = 0 then 'Application' " + 
				"	when cl.learner_status = 1 then 'Registered' " + 
				"	when cl.learner_status = 2 then 'Transfer Pending Approval' " + 
				"	when cl.learner_status = 3 then 'Terminated' " + 
				"	when cl.learner_status = 4 then 'Pending Termination' " + 
				"	when cl.learner_status = 5 then 'Pending Change Approval' " + 
				"	when cl.learner_status = 6 then 'Completed' " + 
				"	when cl.learner_status = 7 then 'Pending Trade Test' " + 
				"	when cl.learner_status = 8 then 'Pending ARPL Trade Test' " + 
				"	when cl.learner_status = 9 then 'Pending Review Approval' " + 
				"	when cl.learner_status = 10 then 'Qualification Obtained' " + 
				"	when cl.learner_status = 11 then 'Pending non-merSETA Qualification Approval' " + 
				"	when cl.learner_status = 12 then 'Pending Verification of Assesment Approval' " + 
				"	when cl.learner_status = 13 then 'Pending Completion Letter Approval' " + 
				"	when cl.learner_status = 14 then 'Withdrawn' " + 
				"	when cl.learner_status = 15 then 'Rejected' " + 
				"	when cl.learner_status = 16 then 'Achieved' " + 
				"	when cl.learner_status = 17 then 'Pending Lost Time' " + 
				"end as 'learnerStatus' " + 
								
				", CONCAT(ad.address_line_1, ' ',ad.address_line_2,' ',ad.address_line_3,' ',lt.description, ' ',ad.postcode) as 'residentialAddress' " + 
				", CONCAT(pa.address_line_1, ' ',pa.address_line_2,' ',ad.address_line_3,' ',ltp.description, ' ',pa.postcode) as 'postalAddress' " +
				
				
				",(SELECT GROUP_CONCAT(lang.description,' ') " + 
				"		from language lang " + 
				"		where lang.id in  " + 
				"			(select language_id from users_language where user_id = u.id and home_language = true) " + 
				"		) as 'language' " + 
				
				",(SELECT ofo.description " + 
				"	from ofo_codes ofo " + 
				"	where ofo.id = cl.ofo_codes_id) " + 
				" as 'ofoCodeDescription' " + 

				", gen.gender_name as 'gender' " + 
				", u.date_of_birth as 'dateOfBirth' " + 
				", equi.description as 'equity' " + 
				
			
				", nation.description as 'nationality' " +
				", statssa.description as 'startSaAreaCode' " +		
				", cl.date_learner_registered as 'qmrEnteredDate' " + 
				", cl.date_learner_completed as 'qmrCompletedDate' " + 

				"from " + 
				
				"company_learners cl " + 
				"left join users u on u.id = cl.user_id " + 
				"left join gender gen on gen.id = u.gender_id " + 
				"left join address ad on ad.id = u.residential_address_id " + 
				"left join address pa on pa.id = u.postal_address_id " + 				
				"left join municipality m on m.id = ad.municipality_id " +
				"left join towns lt on lt.id = ad.town_id " + 
				"left join towns ltp on ltp.id = pa.town_id " + 
				"left join region_town lrt on lrt.town_id = lt.id " + 
				"left join region lr on lr.id = lrt.region_id " + 
				
				"left join statssa_area_code statssa on statssa.id = ad.stats_saarea_code_id " + 
				
				"left join users cu on cu.id = cl.create_user_id " + 
				"left join company e on e.id = cl.employer_id " + 
				"left join address ea on ea.id = e.residential_address_id " + 
				"left join municipality municipal on municipal.id = ea.municipality_id " + 
				"left join towns et on et.id = ea.town_id " + 
				"left join region_town ert on ert.town_id = et.id " + 
				"left join region er on er.id = ert.region_id " +
				
				"left join sic_code siccode on siccode.id = e.sic_code_id " +
				"left join chamber cham on cham.id = siccode.chamber_id " +
				"left join equity equi on equi.id = u.equity_id " +
				
				"left join ofo_codes child on child.id = cl.ofo_codes_id " +
				"left join ofo_codes parent on parent.id = child.ofo_codes_id " +

				"left join nationality nation on nation.id = u.nationality_id " +

				"left join company c on c.id = cl.company_id " + 
				"left join intervention_type it on it.id = cl.intervention_type_id " + 
				"left join funding f on f.id = cl.dunding_id " + 
				"left join saqa_qualification hq on hq.id = cl.highest_qualification_id " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id " + 
				"left join skills_program sp on sp.id = cl.skills_program_id " + 
				"left join skills_set ss on ss.id = cl.skills_set_id " + 
				"left join learnership ls on ls.id = cl.learnership_id " + 
				"left join saqa_unitstandard us on us.id = cl.unit_standard_id " + 				
				
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id "+
				"left join company pr on pr.id = tpa.company_id " + 
				"left join address a on a.id = pr.residential_address_id " + 
				"left join municipality mun on mun.id = a.municipality_id " + 
				"left join towns t on t.id = a.town_id " + 
				"left join region_town rt on rt.town_id = t.id " + 
				"left join region r on r.id = rt.region_id " +  
				"where it.id = :interventionID " + 
				"and r.id = :regionID " +
				"order by cl.id asc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("regionID", region.getId());
		parameters.put("interventionID", interventionType.getId());
		return (List<CompanyLearnersBean>)super.nativeSelectSqlList(sql, CompanyLearnersBean.class, parameters);
	}
	
	public List<CompanyLearnersBean> generateCompanyLearnerReport(Region region, LearnerStatusEnum learnerStatusEnum) throws Exception {
		String sql ="select  " + 
				" cl.create_date as 'createDate' " +
				", u.first_name as 'firstName' "  + 
				", u.middle_name as 'middleName' "  + 
				", u.last_name as 'lastName' "  + 
				", u.email as 'learnerEmail' "  + 
				", u.cell_number as 'cellNumber' " + 
				", u.tel_number as 'telNumber' " + 
				", u.rsa_id_number as 'rsaIDNumber' " + 
				", u.passport_number as 'passportNumber' " + 
				", m.municipality_desc as learnerMunicipality " + 
				", lr.description as 'region' " + 
				", er.description as 'employerRegion' " +
				", r.description as 'providerRegion' " +
				", mun.municipality_desc as 'providerMunicipality' " +
				", municipal.municipality_desc as 'employerMunicipality' " +
				", cham.description as 'chamber' " +
				", it.description as 'interventionType' " + 
				
				", sq.qualificationid_string as 'qualificationCode' " + 
				", sq.qualificationtitle as 'qualificationTitle' " + 
				", sp.programme_id as 'skillsProgrammeCode' " + 
				", sp.description as 'skillsProgrammeDescription' " + 
				", ss.programme_id as 'skillsSetCode' " + 
				", ss.description as 'skillsSetDescription' " + 
				", ls.code as 'learnershipCode' " + 
				", ls.description as 'learnershipDescription' " + 
				", us.unitstandardid as 'unitStandardCode' " + 
				", us.unitstdtitle as 'unitStandardDescription' " + 
				", cl.non_credid_bearing_description as 'nonCreditBearingDescription' " + 				
				
				", cl.approval_date as 'approvalDate' " + 
				", cl.date_learner_registered as 'dateLearnerRegistered' " + 
				", cl.registered_date as 'registrationDate' " +
				", cl.commencement_date as 'commencementDate' " + 
				", cl.completion_date as 'completionDate' " +
				
				",CONCAT(hq.qualificationid, ' (', hq.qualificationtitle,')') as 'highestQualification' " + 
				
				", CASE " + 
				"	when cl.highest_education_enum = 0 then 'Below Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 1 then 'Above Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 2 then 'N1' " + 
				"	when cl.highest_education_enum = 3 then 'N2' " + 
				"	when cl.highest_education_enum = 4 then 'N3' " + 
				"	when cl.highest_education_enum = 5 then 'N4' " + 
				"	when cl.highest_education_enum = 6 then 'N5' " + 
				"	when cl.highest_education_enum = 7 then 'N6' " + 
				"	when cl.highest_education_enum = 8 then 'NCV Level 2' " + 
				"	when cl.highest_education_enum = 9 then 'NCV Level 3' " + 
				"	when cl.highest_education_enum = 10 then 'NCV Level 4' " + 
				"	when cl.highest_education_enum = 11 then 'S1' " + 
				"	when cl.highest_education_enum = 12 then 'S2' " + 
				"	when cl.highest_education_enum = 13 then 'S3' " + 
				"end as 'highestEducation' " + 
				
				", CONCAT(e.company_name, ' (', e.levy_number,')') as 'employerInfor' " + 
				", CONCAT(pr.company_name, ' (', pr.levy_number,')' , tpa.accreditation_number) as 'providerInfor' " + 

				", cl.registration_number as 'registrationNumber' " + 	
				
				", CONCAT(cu.first_name, ' ', cu.last_name, ' (', cu.email,')') as 'createUser' " + 
				
				",CASE " + 
				"	when cl.status = 0 then 'Approved' " + 
				"	when cl.status = 1 then 'Rejected' " + 
				"	when cl.status = 2 then 'Pending Manager Approval' " + 
				"	when cl.status = 3 then 'Pending Approval' " + 
				"	when cl.status = 4 then 'Pending Sign Off' " + 
				"	when cl.status = 5 then 'Completed' " + 
				"	when cl.status = 6 then 'Pending accept code of conduct' " + 
				"	when cl.status = 7 then 'Awaiting DHET'   " + 
				"	when cl.status = 8 then 'Pending Final Approval' " + 
				"	when cl.status = 9 then 'Withdrawn'   " + 
				"	when cl.status = 10 then 'N/A' " + 
				"	when cl.status = 11 then 'Recommended' " + 
				"	when cl.status = 12 then 'Appealed' " + 
				"	when cl.status = 13 then 'Pending Committee Approval' " + 
				"	when cl.status = 14 then 'Approved By ETQA Review Committee' " + 
				"	when cl.status = 15 then 'Rejected By ETQA Review Commitee' " + 
				"	when cl.status = 16 then 'Requested Higher Allocation' " + 
				"	when cl.status = 17 then 'Accepted MOA' " + 
				"	when cl.status = 18 then 'Requested Change' " + 
				"	when cl.status = 19 then 'Rejected By MANCO Review' " + 
				"	when cl.status = 20 then 'Rejected By Learner Review Committee' " + 
				"	when cl.status = 21 then 'Qualification Obtained' " + 
				"	when cl.status = 22 then 'Deactivated' " + 
				"	when cl.status = 23 then 'Project Terminated Manager Approval' " + 
				"	when cl.status = 24 then 'Suspend Project' " + 
				"	when cl.status = 25 then 'Project Terminated' " + 
				"	when cl.status = 26 then 'Pending Review Approval' " + 
				"	when cl.status = 27 then 'Uphold' " + 
				"	when cl.status = 28 then 'Pending Resubmission'   " + 
				"	when cl.status = 29 then 'Awaiting NAMB' " + 
				"	when cl.status = 30 then 'Pending Withdrawal' " + 
				"	when cl.status = 31 then 'Pending Inverstigation' " + 
				"	when cl.status = 32 then 'Pending Change Approval' " + 
				"	when cl.status = 33 then 'Not Competent' " + 
				"end as 'approvalStatus' " + 
				
				",CASE when cl.employment_status = 0 then 'Employed' " + 
				"	when cl.employment_status = 1 then 'Unemployed' " + 
				"end as 'employmentStatus' " + 
				
				",CASE when u.urban_rural_enum = 0 then 'Urban' " + 
				"	   when u.urban_rural_enum = 1 then 'Rural' " + 
				"end as 'urbanRural' " + 
				
				",CASE when u.disability = 0 then 'Yes' " + 
				"	   when u.disability = 1 then 'No' " + 
				"end as 'disability' " +
				
				",CASE when cl.merseta_funded = 0 then 'Yes' " + 
				"	   when cl.merseta_funded = 1 then 'No' " + 
				"end as 'fundedBy' " +
				
				",CASE when cl.legacy_id is not null then 'Yes' " + 
				"	   when cl.legacy_id is null then 'No' " + 
				"end as 'legacyLearner' " +
				
				",CASE when child.ofo_code is not null then child.ofo_code " + 
				"	   when parent.ofo_code is not null then parent.ofo_code " + 
				"end as 'ofoCode' " +
				
				",CASE " + 
				"	when cl.learner_status = 0 then 'Application' " + 
				"	when cl.learner_status = 1 then 'Registered' " + 
				"	when cl.learner_status = 2 then 'Transfer Pending Approval' " + 
				"	when cl.learner_status = 3 then 'Terminated' " + 
				"	when cl.learner_status = 4 then 'Pending Termination' " + 
				"	when cl.learner_status = 5 then 'Pending Change Approval' " + 
				"	when cl.learner_status = 6 then 'Completed' " + 
				"	when cl.learner_status = 7 then 'Pending Trade Test' " + 
				"	when cl.learner_status = 8 then 'Pending ARPL Trade Test' " + 
				"	when cl.learner_status = 9 then 'Pending Review Approval' " + 
				"	when cl.learner_status = 10 then 'Qualification Obtained' " + 
				"	when cl.learner_status = 11 then 'Pending non-merSETA Qualification Approval' " + 
				"	when cl.learner_status = 12 then 'Pending Verification of Assesment Approval' " + 
				"	when cl.learner_status = 13 then 'Pending Completion Letter Approval' " + 
				"	when cl.learner_status = 14 then 'Withdrawn' " + 
				"	when cl.learner_status = 15 then 'Rejected' " + 
				"	when cl.learner_status = 16 then 'Achieved' " + 
				"	when cl.learner_status = 17 then 'Pending Lost Time' " + 
				"end as 'learnerStatus' " + 
								
				", CONCAT(ad.address_line_1, ' ',ad.address_line_2,' ',ad.address_line_3,' ',lt.description, ' ',ad.postcode) as 'residentialAddress' " + 
				", CONCAT(pa.address_line_1, ' ',pa.address_line_2,' ',ad.address_line_3,' ',ltp.description, ' ',pa.postcode) as 'postalAddress' " +
				
				
				",(SELECT GROUP_CONCAT(lang.description,' ') " + 
				"		from language lang " + 
				"		where lang.id in  " + 
				"			(select language_id from users_language where user_id = u.id and home_language = true) " + 
				"		) as 'language' " + 
				
				",(SELECT ofo.description " + 
				"	from ofo_codes ofo " + 
				"	where ofo.id = cl.ofo_codes_id) " + 
				" as 'ofoCodeDescription' " + 

				", gen.gender_name as 'gender' " + 
				", u.date_of_birth as 'dateOfBirth' " + 
				", equi.description as 'equity' " + 
				
			
				", nation.description as 'nationality' " +
				", statssa.description as 'startSaAreaCode' " +		
				", cl.date_learner_registered as 'qmrEnteredDate' " + 
				", cl.date_learner_completed as 'qmrCompletedDate' " + 

				"from " + 
				
				"company_learners cl " + 
				"left join users u on u.id = cl.user_id " + 
				"left join gender gen on gen.id = u.gender_id " + 
				"left join address ad on ad.id = u.residential_address_id " + 
				"left join address pa on pa.id = u.postal_address_id " + 				
				"left join municipality m on m.id = ad.municipality_id " +
				"left join towns lt on lt.id = ad.town_id " + 
				"left join towns ltp on ltp.id = pa.town_id " + 
				"left join region_town lrt on lrt.town_id = lt.id " + 
				"left join region lr on lr.id = lrt.region_id " + 
				
				"left join statssa_area_code statssa on statssa.id = ad.stats_saarea_code_id " + 
				
				"left join users cu on cu.id = cl.create_user_id " + 
				"left join company e on e.id = cl.employer_id " + 
				"left join address ea on ea.id = e.residential_address_id " + 
				"left join municipality municipal on municipal.id = ea.municipality_id " + 
				"left join towns et on et.id = ea.town_id " + 
				"left join region_town ert on ert.town_id = et.id " + 
				"left join region er on er.id = ert.region_id " +
				
				"left join sic_code siccode on siccode.id = e.sic_code_id " +
				"left join chamber cham on cham.id = siccode.chamber_id " +
				"left join equity equi on equi.id = u.equity_id " +
				
				"left join ofo_codes child on child.id = cl.ofo_codes_id " +
				"left join ofo_codes parent on parent.id = child.ofo_codes_id " +

				"left join nationality nation on nation.id = u.nationality_id " +

				"left join company c on c.id = cl.company_id " + 
				"left join intervention_type it on it.id = cl.intervention_type_id " + 
				"left join funding f on f.id = cl.dunding_id " + 
				"left join saqa_qualification hq on hq.id = cl.highest_qualification_id " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id " + 
				"left join skills_program sp on sp.id = cl.skills_program_id " + 
				"left join skills_set ss on ss.id = cl.skills_set_id " + 
				"left join learnership ls on ls.id = cl.learnership_id " + 
				"left join saqa_unitstandard us on us.id = cl.unit_standard_id " + 				
				
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id "+
				"left join company pr on pr.id = tpa.company_id " + 
				"left join address a on a.id = pr.residential_address_id " + 
				"left join municipality mun on mun.id = a.municipality_id " + 
				"left join towns t on t.id = a.town_id " + 
				"left join region_town rt on rt.town_id = t.id " + 
				"left join region r on r.id = rt.region_id " +  
				"where r.id = :regionID " + 
				"and cl.learner_status = :learnerStatusEnum " + 
				"order by cl.id asc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("regionID", region.getId());
		parameters.put("learnerStatusEnum", learnerStatusEnum.ordinal());
		return (List<CompanyLearnersBean>)super.nativeSelectSqlList(sql, CompanyLearnersBean.class, parameters);
	}
	
	public List<CompanyLearnersBean> generateCompanyLearnerReport(Chamber chamber) throws Exception {
		String sql ="select  " + 
				" cl.create_date as 'createDate' " +
				", u.first_name as 'firstName' "  + 
				", u.middle_name as 'middleName' "  + 
				", u.last_name as 'lastName' "  + 
				", u.email as 'learnerEmail' "  + 
				", u.cell_number as 'cellNumber' " + 
				", u.tel_number as 'telNumber' " + 
				", u.rsa_id_number as 'rsaIDNumber' " + 
				", u.passport_number as 'passportNumber' " + 
				", m.municipality_desc as learnerMunicipality " + 
				", lr.description as 'region' " + 
				", er.description as 'employerRegion' " +
				", r.description as 'providerRegion' " +
				", mun.municipality_desc as 'providerMunicipality' " +
				", municipal.municipality_desc as 'employerMunicipality' " +
				", cham.description as 'chamber' " +
				", it.description as 'interventionType' " + 
				
				", sq.qualificationid_string as 'qualificationCode' " + 
				", sq.qualificationtitle as 'qualificationTitle' " + 
				", sp.programme_id as 'skillsProgrammeCode' " + 
				", sp.description as 'skillsProgrammeDescription' " + 
				", ss.programme_id as 'skillsSetCode' " + 
				", ss.description as 'skillsSetDescription' " + 
				", ls.code as 'learnershipCode' " + 
				", ls.description as 'learnershipDescription' " + 
				", us.unitstandardid as 'unitStandardCode' " + 
				", us.unitstdtitle as 'unitStandardDescription' " + 
				", cl.non_credid_bearing_description as 'nonCreditBearingDescription' " + 				
				
				", cl.approval_date as 'approvalDate' " + 
				", cl.date_learner_registered as 'dateLearnerRegistered' " + 
				", cl.registered_date as 'registrationDate' " +
				", cl.commencement_date as 'commencementDate' " + 
				", cl.completion_date as 'completionDate' " +
				
				",CONCAT(hq.qualificationid, ' (', hq.qualificationtitle,')') as 'highestQualification' " + 
				
				", CASE " + 
				"	when cl.highest_education_enum = 0 then 'Below Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 1 then 'Above Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 2 then 'N1' " + 
				"	when cl.highest_education_enum = 3 then 'N2' " + 
				"	when cl.highest_education_enum = 4 then 'N3' " + 
				"	when cl.highest_education_enum = 5 then 'N4' " + 
				"	when cl.highest_education_enum = 6 then 'N5' " + 
				"	when cl.highest_education_enum = 7 then 'N6' " + 
				"	when cl.highest_education_enum = 8 then 'NCV Level 2' " + 
				"	when cl.highest_education_enum = 9 then 'NCV Level 3' " + 
				"	when cl.highest_education_enum = 10 then 'NCV Level 4' " + 
				"	when cl.highest_education_enum = 11 then 'S1' " + 
				"	when cl.highest_education_enum = 12 then 'S2' " + 
				"	when cl.highest_education_enum = 13 then 'S3' " + 
				"end as 'highestEducation' " + 
				
				", CONCAT(e.company_name, ' (', e.levy_number,')') as 'employerInfor' " + 
				", CONCAT(pr.company_name, ' (', pr.levy_number,')' , tpa.accreditation_number) as 'providerInfor' " + 

				", cl.registration_number as 'registrationNumber' " + 	
				
				", CONCAT(cu.first_name, ' ', cu.last_name, ' (', cu.email,')') as 'createUser' " + 
				
				",CASE " + 
				"	when cl.status = 0 then 'Approved' " + 
				"	when cl.status = 1 then 'Rejected' " + 
				"	when cl.status = 2 then 'Pending Manager Approval' " + 
				"	when cl.status = 3 then 'Pending Approval' " + 
				"	when cl.status = 4 then 'Pending Sign Off' " + 
				"	when cl.status = 5 then 'Completed' " + 
				"	when cl.status = 6 then 'Pending accept code of conduct' " + 
				"	when cl.status = 7 then 'Awaiting DHET'   " + 
				"	when cl.status = 8 then 'Pending Final Approval' " + 
				"	when cl.status = 9 then 'Withdrawn'   " + 
				"	when cl.status = 10 then 'N/A' " + 
				"	when cl.status = 11 then 'Recommended' " + 
				"	when cl.status = 12 then 'Appealed' " + 
				"	when cl.status = 13 then 'Pending Committee Approval' " + 
				"	when cl.status = 14 then 'Approved By ETQA Review Committee' " + 
				"	when cl.status = 15 then 'Rejected By ETQA Review Commitee' " + 
				"	when cl.status = 16 then 'Requested Higher Allocation' " + 
				"	when cl.status = 17 then 'Accepted MOA' " + 
				"	when cl.status = 18 then 'Requested Change' " + 
				"	when cl.status = 19 then 'Rejected By MANCO Review' " + 
				"	when cl.status = 20 then 'Rejected By Learner Review Committee' " + 
				"	when cl.status = 21 then 'Qualification Obtained' " + 
				"	when cl.status = 22 then 'Deactivated' " + 
				"	when cl.status = 23 then 'Project Terminated Manager Approval' " + 
				"	when cl.status = 24 then 'Suspend Project' " + 
				"	when cl.status = 25 then 'Project Terminated' " + 
				"	when cl.status = 26 then 'Pending Review Approval' " + 
				"	when cl.status = 27 then 'Uphold' " + 
				"	when cl.status = 28 then 'Pending Resubmission'   " + 
				"	when cl.status = 29 then 'Awaiting NAMB' " + 
				"	when cl.status = 30 then 'Pending Withdrawal' " + 
				"	when cl.status = 31 then 'Pending Inverstigation' " + 
				"	when cl.status = 32 then 'Pending Change Approval' " + 
				"	when cl.status = 33 then 'Not Competent' " + 
				"end as 'approvalStatus' " + 
				
				",CASE when cl.employment_status = 0 then 'Employed' " + 
				"	when cl.employment_status = 1 then 'Unemployed' " + 
				"end as 'employmentStatus' " + 
				
				",CASE when u.urban_rural_enum = 0 then 'Urban' " + 
				"	   when u.urban_rural_enum = 1 then 'Rural' " + 
				"end as 'urbanRural' " + 
				
				",CASE when u.disability = 0 then 'Yes' " + 
				"	   when u.disability = 1 then 'No' " + 
				"end as 'disability' " +
				
				",CASE when cl.merseta_funded = 0 then 'Yes' " + 
				"	   when cl.merseta_funded = 1 then 'No' " + 
				"end as 'fundedBy' " +
				
				",CASE when cl.legacy_id is not null then 'Yes' " + 
				"	   when cl.legacy_id is null then 'No' " + 
				"end as 'legacyLearner' " +
				
				",CASE when child.ofo_code is not null then child.ofo_code " + 
				"	   when parent.ofo_code is not null then parent.ofo_code " + 
				"end as 'ofoCode' " +
				
				",CASE " + 
				"	when cl.learner_status = 0 then 'Application' " + 
				"	when cl.learner_status = 1 then 'Registered' " + 
				"	when cl.learner_status = 2 then 'Transfer Pending Approval' " + 
				"	when cl.learner_status = 3 then 'Terminated' " + 
				"	when cl.learner_status = 4 then 'Pending Termination' " + 
				"	when cl.learner_status = 5 then 'Pending Change Approval' " + 
				"	when cl.learner_status = 6 then 'Completed' " + 
				"	when cl.learner_status = 7 then 'Pending Trade Test' " + 
				"	when cl.learner_status = 8 then 'Pending ARPL Trade Test' " + 
				"	when cl.learner_status = 9 then 'Pending Review Approval' " + 
				"	when cl.learner_status = 10 then 'Qualification Obtained' " + 
				"	when cl.learner_status = 11 then 'Pending non-merSETA Qualification Approval' " + 
				"	when cl.learner_status = 12 then 'Pending Verification of Assesment Approval' " + 
				"	when cl.learner_status = 13 then 'Pending Completion Letter Approval' " + 
				"	when cl.learner_status = 14 then 'Withdrawn' " + 
				"	when cl.learner_status = 15 then 'Rejected' " + 
				"	when cl.learner_status = 16 then 'Achieved' " + 
				"	when cl.learner_status = 17 then 'Pending Lost Time' " + 
				"end as 'learnerStatus' " + 
								
				", CONCAT(ad.address_line_1, ' ',ad.address_line_2,' ',ad.address_line_3,' ',lt.description, ' ',ad.postcode) as 'residentialAddress' " + 
				", CONCAT(pa.address_line_1, ' ',pa.address_line_2,' ',ad.address_line_3,' ',ltp.description, ' ',pa.postcode) as 'postalAddress' " +
				
				
				",(SELECT GROUP_CONCAT(lang.description,' ') " + 
				"		from language lang " + 
				"		where lang.id in  " + 
				"			(select language_id from users_language where user_id = u.id and home_language = true) " + 
				"		) as 'language' " + 
				
				",(SELECT ofo.description " + 
				"	from ofo_codes ofo " + 
				"	where ofo.id = cl.ofo_codes_id) " + 
				" as 'ofoCodeDescription' " + 

				", gen.gender_name as 'gender' " + 
				", u.date_of_birth as 'dateOfBirth' " + 
				", equi.description as 'equity' " + 
				
			
				", nation.description as 'nationality' " +
				", statssa.description as 'startSaAreaCode' " +		
				", cl.date_learner_registered as 'qmrEnteredDate' " + 
				", cl.date_learner_completed as 'qmrCompletedDate' " + 

				"from " + 
				
				"company_learners cl " + 
				"left join users u on u.id = cl.user_id " + 
				"left join gender gen on gen.id = u.gender_id " + 
				"left join address ad on ad.id = u.residential_address_id " + 
				"left join address pa on pa.id = u.postal_address_id " + 				
				"left join municipality m on m.id = ad.municipality_id " +
				"left join towns lt on lt.id = ad.town_id " + 
				"left join towns ltp on ltp.id = pa.town_id " + 
				"left join region_town lrt on lrt.town_id = lt.id " + 
				"left join region lr on lr.id = lrt.region_id " + 
				
				"left join statssa_area_code statssa on statssa.id = ad.stats_saarea_code_id " + 
				
				"left join users cu on cu.id = cl.create_user_id " + 
				"left join company e on e.id = cl.employer_id " + 
				"left join address ea on ea.id = e.residential_address_id " + 
				"left join municipality municipal on municipal.id = ea.municipality_id " + 
				"left join towns et on et.id = ea.town_id " + 
				"left join region_town ert on ert.town_id = et.id " + 
				"left join region er on er.id = ert.region_id " +
				
				"left join sic_code siccode on siccode.id = e.sic_code_id " +
				"left join chamber cham on cham.id = siccode.chamber_id " +
				"left join equity equi on equi.id = u.equity_id " +
				
				"left join ofo_codes child on child.id = cl.ofo_codes_id " +
				"left join ofo_codes parent on parent.id = child.ofo_codes_id " +

				"left join nationality nation on nation.id = u.nationality_id " +

				"left join company c on c.id = cl.company_id " + 
				"left join intervention_type it on it.id = cl.intervention_type_id " + 
				"left join funding f on f.id = cl.dunding_id " + 
				"left join saqa_qualification hq on hq.id = cl.highest_qualification_id " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id " + 
				"left join skills_program sp on sp.id = cl.skills_program_id " + 
				"left join skills_set ss on ss.id = cl.skills_set_id " + 
				"left join learnership ls on ls.id = cl.learnership_id " + 
				"left join saqa_unitstandard us on us.id = cl.unit_standard_id " + 				
				
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id "+
				"left join company pr on pr.id = tpa.company_id " + 
				"left join address a on a.id = pr.residential_address_id " + 
				"left join municipality mun on mun.id = a.municipality_id " + 
				"left join towns t on t.id = a.town_id " + 
				"left join region_town rt on rt.town_id = t.id " + 
				"left join region r on r.id = rt.region_id " + 
				"where cl.intervention_type_id is not null " + 
				"and cham.id = :chamberID " + 
				"order by cl.id asc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("chamberID", chamber.getId());
		return (List<CompanyLearnersBean>)super.nativeSelectSqlList(sql, CompanyLearnersBean.class, parameters);
	}
	
	public List<CompanyLearnersBean> generateCompanyLearnerReport(Region region) throws Exception {
		String sql ="select  " + 
				" cl.create_date as 'createDate' " +
				", u.first_name as 'firstName' "  + 
				", u.middle_name as 'middleName' "  + 
				", u.last_name as 'lastName' "  + 
				", u.email as 'learnerEmail' "  + 
				", u.cell_number as 'cellNumber' " + 
				", u.tel_number as 'telNumber' " + 
				", u.rsa_id_number as 'rsaIDNumber' " + 
				", u.passport_number as 'passportNumber' " + 
				", m.municipality_desc as learnerMunicipality " + 
				", lr.description as 'region' " + 
				", er.description as 'employerRegion' " +
				", r.description as 'providerRegion' " +
				", mun.municipality_desc as 'providerMunicipality' " +
				", municipal.municipality_desc as 'employerMunicipality' " +
				", cham.description as 'chamber' " +
				", it.description as 'interventionType' " + 
				
				", sq.qualificationid_string as 'qualificationCode' " + 
				", sq.qualificationtitle as 'qualificationTitle' " + 
				", sp.programme_id as 'skillsProgrammeCode' " + 
				", sp.description as 'skillsProgrammeDescription' " + 
				", ss.programme_id as 'skillsSetCode' " + 
				", ss.description as 'skillsSetDescription' " + 
				", ls.code as 'learnershipCode' " + 
				", ls.description as 'learnershipDescription' " + 
				", us.unitstandardid as 'unitStandardCode' " + 
				", us.unitstdtitle as 'unitStandardDescription' " + 
				", cl.non_credid_bearing_description as 'nonCreditBearingDescription' " + 				
				
				", cl.approval_date as 'approvalDate' " + 
				", cl.date_learner_registered as 'dateLearnerRegistered' " + 
				", cl.registered_date as 'registrationDate' " +
				", cl.commencement_date as 'commencementDate' " + 
				", cl.completion_date as 'completionDate' " +
				
				",CONCAT(hq.qualificationid, ' (', hq.qualificationtitle,')') as 'highestQualification' " + 
				
				", CASE " + 
				"	when cl.highest_education_enum = 0 then 'Below Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 1 then 'Above Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 2 then 'N1' " + 
				"	when cl.highest_education_enum = 3 then 'N2' " + 
				"	when cl.highest_education_enum = 4 then 'N3' " + 
				"	when cl.highest_education_enum = 5 then 'N4' " + 
				"	when cl.highest_education_enum = 6 then 'N5' " + 
				"	when cl.highest_education_enum = 7 then 'N6' " + 
				"	when cl.highest_education_enum = 8 then 'NCV Level 2' " + 
				"	when cl.highest_education_enum = 9 then 'NCV Level 3' " + 
				"	when cl.highest_education_enum = 10 then 'NCV Level 4' " + 
				"	when cl.highest_education_enum = 11 then 'S1' " + 
				"	when cl.highest_education_enum = 12 then 'S2' " + 
				"	when cl.highest_education_enum = 13 then 'S3' " + 
				"end as 'highestEducation' " + 
				
				", CONCAT(e.company_name, ' (', e.levy_number,')') as 'employerInfor' " + 
				", CONCAT(pr.company_name, ' (', pr.levy_number,')' , tpa.accreditation_number) as 'providerInfor' " + 

				", cl.registration_number as 'registrationNumber' " + 	
				
				", CONCAT(cu.first_name, ' ', cu.last_name, ' (', cu.email,')') as 'createUser' " + 
				
				",CASE " + 
				"	when cl.status = 0 then 'Approved' " + 
				"	when cl.status = 1 then 'Rejected' " + 
				"	when cl.status = 2 then 'Pending Manager Approval' " + 
				"	when cl.status = 3 then 'Pending Approval' " + 
				"	when cl.status = 4 then 'Pending Sign Off' " + 
				"	when cl.status = 5 then 'Completed' " + 
				"	when cl.status = 6 then 'Pending accept code of conduct' " + 
				"	when cl.status = 7 then 'Awaiting DHET'   " + 
				"	when cl.status = 8 then 'Pending Final Approval' " + 
				"	when cl.status = 9 then 'Withdrawn'   " + 
				"	when cl.status = 10 then 'N/A' " + 
				"	when cl.status = 11 then 'Recommended' " + 
				"	when cl.status = 12 then 'Appealed' " + 
				"	when cl.status = 13 then 'Pending Committee Approval' " + 
				"	when cl.status = 14 then 'Approved By ETQA Review Committee' " + 
				"	when cl.status = 15 then 'Rejected By ETQA Review Commitee' " + 
				"	when cl.status = 16 then 'Requested Higher Allocation' " + 
				"	when cl.status = 17 then 'Accepted MOA' " + 
				"	when cl.status = 18 then 'Requested Change' " + 
				"	when cl.status = 19 then 'Rejected By MANCO Review' " + 
				"	when cl.status = 20 then 'Rejected By Learner Review Committee' " + 
				"	when cl.status = 21 then 'Qualification Obtained' " + 
				"	when cl.status = 22 then 'Deactivated' " + 
				"	when cl.status = 23 then 'Project Terminated Manager Approval' " + 
				"	when cl.status = 24 then 'Suspend Project' " + 
				"	when cl.status = 25 then 'Project Terminated' " + 
				"	when cl.status = 26 then 'Pending Review Approval' " + 
				"	when cl.status = 27 then 'Uphold' " + 
				"	when cl.status = 28 then 'Pending Resubmission'   " + 
				"	when cl.status = 29 then 'Awaiting NAMB' " + 
				"	when cl.status = 30 then 'Pending Withdrawal' " + 
				"	when cl.status = 31 then 'Pending Inverstigation' " + 
				"	when cl.status = 32 then 'Pending Change Approval' " + 
				"	when cl.status = 33 then 'Not Competent' " + 
				"end as 'approvalStatus' " + 
				
				",CASE when cl.employment_status = 0 then 'Employed' " + 
				"	when cl.employment_status = 1 then 'Unemployed' " + 
				"end as 'employmentStatus' " + 
				
				",CASE when u.urban_rural_enum = 0 then 'Urban' " + 
				"	   when u.urban_rural_enum = 1 then 'Rural' " + 
				"end as 'urbanRural' " + 
				
				",CASE when u.disability = 0 then 'Yes' " + 
				"	   when u.disability = 1 then 'No' " + 
				"end as 'disability' " +
				
				",CASE when cl.merseta_funded = 0 then 'Yes' " + 
				"	   when cl.merseta_funded = 1 then 'No' " + 
				"end as 'fundedBy' " +
				
				",CASE when cl.legacy_id is not null then 'Yes' " + 
				"	   when cl.legacy_id is null then 'No' " + 
				"end as 'legacyLearner' " +
				
				",CASE when child.ofo_code is not null then child.ofo_code " + 
				"	   when parent.ofo_code is not null then parent.ofo_code " + 
				"end as 'ofoCode' " +
				
				",CASE " + 
				"	when cl.learner_status = 0 then 'Application' " + 
				"	when cl.learner_status = 1 then 'Registered' " + 
				"	when cl.learner_status = 2 then 'Transfer Pending Approval' " + 
				"	when cl.learner_status = 3 then 'Terminated' " + 
				"	when cl.learner_status = 4 then 'Pending Termination' " + 
				"	when cl.learner_status = 5 then 'Pending Change Approval' " + 
				"	when cl.learner_status = 6 then 'Completed' " + 
				"	when cl.learner_status = 7 then 'Pending Trade Test' " + 
				"	when cl.learner_status = 8 then 'Pending ARPL Trade Test' " + 
				"	when cl.learner_status = 9 then 'Pending Review Approval' " + 
				"	when cl.learner_status = 10 then 'Qualification Obtained' " + 
				"	when cl.learner_status = 11 then 'Pending non-merSETA Qualification Approval' " + 
				"	when cl.learner_status = 12 then 'Pending Verification of Assesment Approval' " + 
				"	when cl.learner_status = 13 then 'Pending Completion Letter Approval' " + 
				"	when cl.learner_status = 14 then 'Withdrawn' " + 
				"	when cl.learner_status = 15 then 'Rejected' " + 
				"	when cl.learner_status = 16 then 'Achieved' " + 
				"	when cl.learner_status = 17 then 'Pending Lost Time' " + 
				"end as 'learnerStatus' " + 
								
				", CONCAT(ad.address_line_1, ' ',ad.address_line_2,' ',ad.address_line_3,' ',lt.description, ' ',ad.postcode) as 'residentialAddress' " + 
				", CONCAT(pa.address_line_1, ' ',pa.address_line_2,' ',ad.address_line_3,' ',ltp.description, ' ',pa.postcode) as 'postalAddress' " +
				
				
				",(SELECT GROUP_CONCAT(lang.description,' ') " + 
				"		from language lang " + 
				"		where lang.id in  " + 
				"			(select language_id from users_language where user_id = u.id and home_language = true) " + 
				"		) as 'language' " + 
				
				",(SELECT ofo.description " + 
				"	from ofo_codes ofo " + 
				"	where ofo.id = cl.ofo_codes_id) " + 
				" as 'ofoCodeDescription' " + 

				", gen.gender_name as 'gender' " + 
				", u.date_of_birth as 'dateOfBirth' " + 
				", equi.description as 'equity' " + 
				
			
				", nation.description as 'nationality' " +
				", statssa.description as 'startSaAreaCode' " +		
				", cl.date_learner_registered as 'qmrEnteredDate' " + 
				", cl.date_learner_completed as 'qmrCompletedDate' " + 

				"from " + 
				
				"company_learners cl " + 
				"left join users u on u.id = cl.user_id " + 
				"left join gender gen on gen.id = u.gender_id " + 
				"left join address ad on ad.id = u.residential_address_id " + 
				"left join address pa on pa.id = u.postal_address_id " + 				
				"left join municipality m on m.id = ad.municipality_id " +
				"left join towns lt on lt.id = ad.town_id " + 
				"left join towns ltp on ltp.id = pa.town_id " + 
				"left join region_town lrt on lrt.town_id = lt.id " + 
				"left join region lr on lr.id = lrt.region_id " + 
				
				"left join statssa_area_code statssa on statssa.id = ad.stats_saarea_code_id " + 
				
				"left join users cu on cu.id = cl.create_user_id " + 
				"left join company e on e.id = cl.employer_id " + 
				"left join address ea on ea.id = e.residential_address_id " + 
				"left join municipality municipal on municipal.id = ea.municipality_id " + 
				"left join towns et on et.id = ea.town_id " + 
				"left join region_town ert on ert.town_id = et.id " + 
				"left join region er on er.id = ert.region_id " +
				
				"left join sic_code siccode on siccode.id = e.sic_code_id " +
				"left join chamber cham on cham.id = siccode.chamber_id " +
				"left join equity equi on equi.id = u.equity_id " +
				
				"left join ofo_codes child on child.id = cl.ofo_codes_id " +
				"left join ofo_codes parent on parent.id = child.ofo_codes_id " +

				"left join nationality nation on nation.id = u.nationality_id " +

				"left join company c on c.id = cl.company_id " + 
				"left join intervention_type it on it.id = cl.intervention_type_id " + 
				"left join funding f on f.id = cl.dunding_id " + 
				"left join saqa_qualification hq on hq.id = cl.highest_qualification_id " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id " + 
				"left join skills_program sp on sp.id = cl.skills_program_id " + 
				"left join skills_set ss on ss.id = cl.skills_set_id " + 
				"left join learnership ls on ls.id = cl.learnership_id " + 
				"left join saqa_unitstandard us on us.id = cl.unit_standard_id " + 				
				
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id "+
				"left join company pr on pr.id = tpa.company_id " + 
				"left join address a on a.id = pr.residential_address_id " + 
				"left join municipality mun on mun.id = a.municipality_id " + 
				"left join towns t on t.id = a.town_id " + 
				"left join region_town rt on rt.town_id = t.id " + 
				"left join region r on r.id = rt.region_id " + 
				"where cl.intervention_type_id is not null " +  
				"and r.id = :regionID " + 
				"order by cl.id asc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("regionID", region.getId());
		return (List<CompanyLearnersBean>)super.nativeSelectSqlList(sql, CompanyLearnersBean.class, parameters);
	}
	
	public List<CompanyLearnersBean> generateCompanyLearnerReport() throws Exception {
		String sql = "select " + 
				" cl.create_date as 'createDate' " +
				", u.first_name as 'firstName' "  + 
				", u.middle_name as 'middleName' "  + 
				", u.last_name as 'lastName' "  + 
				", u.email as 'learnerEmail' "  + 
				", u.cell_number as 'cellNumber' " + 
				", u.tel_number as 'telNumber' " + 
				", u.rsa_id_number as 'rsaIDNumber' " + 
				", u.passport_number as 'passportNumber' " + 
				", m.municipality_desc as learnerMunicipality " + 
				", lr.description as 'region' " + 
				", er.description as 'employerRegion' " +
				", r.description as 'providerRegion' " +
				", mun.municipality_desc as 'providerMunicipality' " +
				", municipal.municipality_desc as 'employerMunicipality' " +
				", cham.description as 'chamber' " +
				", it.description as 'interventionType' " + 
				
				", sq.qualificationid_string as 'qualificationCode' " + 
				", sq.qualificationtitle as 'qualificationTitle' " + 
				", sp.programme_id as 'skillsProgrammeCode' " + 
				", sp.description as 'skillsProgrammeDescription' " + 
				", ss.programme_id as 'skillsSetCode' " + 
				", ss.description as 'skillsSetDescription' " + 
				", ls.code as 'learnershipCode' " + 
				", ls.description as 'learnershipDescription' " + 
				", us.unitstandardid as 'unitStandardCode' " + 
				", us.unitstdtitle as 'unitStandardDescription' " + 
				", cl.non_credid_bearing_description as 'nonCreditBearingDescription' " + 				
				
				", cl.approval_date as 'approvalDate' " + 
				", cl.date_learner_registered as 'dateLearnerRegistered' " + 
				", cl.registered_date as 'registrationDate' " +
				", cl.commencement_date as 'commencementDate' " + 
				", cl.completion_date as 'completionDate' " +
				
				",CONCAT(hq.qualificationid, ' (', hq.qualificationtitle,')') as 'highestQualification' " + 
				
				", CASE " + 
				"	when cl.highest_education_enum = 0 then 'Below Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 1 then 'Above Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 2 then 'N1' " + 
				"	when cl.highest_education_enum = 3 then 'N2' " + 
				"	when cl.highest_education_enum = 4 then 'N3' " + 
				"	when cl.highest_education_enum = 5 then 'N4' " + 
				"	when cl.highest_education_enum = 6 then 'N5' " + 
				"	when cl.highest_education_enum = 7 then 'N6' " + 
				"	when cl.highest_education_enum = 8 then 'NCV Level 2' " + 
				"	when cl.highest_education_enum = 9 then 'NCV Level 3' " + 
				"	when cl.highest_education_enum = 10 then 'NCV Level 4' " + 
				"	when cl.highest_education_enum = 11 then 'S1' " + 
				"	when cl.highest_education_enum = 12 then 'S2' " + 
				"	when cl.highest_education_enum = 13 then 'S3' " + 
				"end as 'highestEducation' " + 
				
				", CONCAT(e.company_name, ' (', e.levy_number,')') as 'employerInfor' " + 
				", CONCAT(pr.company_name, ' (', pr.levy_number,')' , tpa.accreditation_number) as 'providerInfor' " + 

				", cl.registration_number as 'registrationNumber' " + 	
				
				", CONCAT(cu.first_name, ' ', cu.last_name, ' (', cu.email,')') as 'createUser' " + 
				
				",CASE " + 
				"	when cl.status = 0 then 'Approved' " + 
				"	when cl.status = 1 then 'Rejected' " + 
				"	when cl.status = 2 then 'Pending Manager Approval' " + 
				"	when cl.status = 3 then 'Pending Approval' " + 
				"	when cl.status = 4 then 'Pending Sign Off' " + 
				"	when cl.status = 5 then 'Completed' " + 
				"	when cl.status = 6 then 'Pending accept code of conduct' " + 
				"	when cl.status = 7 then 'Awaiting DHET'   " + 
				"	when cl.status = 8 then 'Pending Final Approval' " + 
				"	when cl.status = 9 then 'Withdrawn'   " + 
				"	when cl.status = 10 then 'N/A' " + 
				"	when cl.status = 11 then 'Recommended' " + 
				"	when cl.status = 12 then 'Appealed' " + 
				"	when cl.status = 13 then 'Pending Committee Approval' " + 
				"	when cl.status = 14 then 'Approved By ETQA Review Committee' " + 
				"	when cl.status = 15 then 'Rejected By ETQA Review Commitee' " + 
				"	when cl.status = 16 then 'Requested Higher Allocation' " + 
				"	when cl.status = 17 then 'Accepted MOA' " + 
				"	when cl.status = 18 then 'Requested Change' " + 
				"	when cl.status = 19 then 'Rejected By MANCO Review' " + 
				"	when cl.status = 20 then 'Rejected By Learner Review Committee' " + 
				"	when cl.status = 21 then 'Qualification Obtained' " + 
				"	when cl.status = 22 then 'Deactivated' " + 
				"	when cl.status = 23 then 'Project Terminated Manager Approval' " + 
				"	when cl.status = 24 then 'Suspend Project' " + 
				"	when cl.status = 25 then 'Project Terminated' " + 
				"	when cl.status = 26 then 'Pending Review Approval' " + 
				"	when cl.status = 27 then 'Uphold' " + 
				"	when cl.status = 28 then 'Pending Resubmission'   " + 
				"	when cl.status = 29 then 'Awaiting NAMB' " + 
				"	when cl.status = 30 then 'Pending Withdrawal' " + 
				"	when cl.status = 31 then 'Pending Inverstigation' " + 
				"	when cl.status = 32 then 'Pending Change Approval' " + 
				"	when cl.status = 33 then 'Not Competent' " + 
				"end as 'approvalStatus' " + 
				
				",CASE when cl.employment_status = 0 then 'Employed' " + 
				"	when cl.employment_status = 1 then 'Unemployed' " + 
				"end as 'employmentStatus' " + 
				
				",CASE when u.urban_rural_enum = 0 then 'Urban' " + 
				"	   when u.urban_rural_enum = 1 then 'Rural' " + 
				"end as 'urbanRural' " + 
				
				",CASE when u.disability = 0 then 'Yes' " + 
				"	   when u.disability = 1 then 'No' " + 
				"end as 'disability' " +
				
				",CASE when cl.merseta_funded = 0 then 'Yes' " + 
				"	   when cl.merseta_funded = 1 then 'No' " + 
				"end as 'fundedBy' " +
				
				",CASE when cl.legacy_id is not null then 'Yes' " + 
				"	   when cl.legacy_id is null then 'No' " + 
				"end as 'legacyLearner' " +
				
				",CASE when child.ofo_code is not null then child.ofo_code " + 
				"	   when parent.ofo_code is not null then parent.ofo_code " + 
				"end as 'ofoCode' " +
				
				",CASE " + 
				"	when cl.learner_status = 0 then 'Application' " + 
				"	when cl.learner_status = 1 then 'Registered' " + 
				"	when cl.learner_status = 2 then 'Transfer Pending Approval' " + 
				"	when cl.learner_status = 3 then 'Terminated' " + 
				"	when cl.learner_status = 4 then 'Pending Termination' " + 
				"	when cl.learner_status = 5 then 'Pending Change Approval' " + 
				"	when cl.learner_status = 6 then 'Completed' " + 
				"	when cl.learner_status = 7 then 'Pending Trade Test' " + 
				"	when cl.learner_status = 8 then 'Pending ARPL Trade Test' " + 
				"	when cl.learner_status = 9 then 'Pending Review Approval' " + 
				"	when cl.learner_status = 10 then 'Qualification Obtained' " + 
				"	when cl.learner_status = 11 then 'Pending non-merSETA Qualification Approval' " + 
				"	when cl.learner_status = 12 then 'Pending Verification of Assesment Approval' " + 
				"	when cl.learner_status = 13 then 'Pending Completion Letter Approval' " + 
				"	when cl.learner_status = 14 then 'Withdrawn' " + 
				"	when cl.learner_status = 15 then 'Rejected' " + 
				"	when cl.learner_status = 16 then 'Achieved' " + 
				"	when cl.learner_status = 17 then 'Pending Lost Time' " + 
				"end as 'learnerStatus' " + 
								
				", CONCAT(ad.address_line_1, ' ',ad.address_line_2,' ',ad.address_line_3,' ',lt.description, ' ',ad.postcode) as 'residentialAddress' " + 
				", CONCAT(pa.address_line_1, ' ',pa.address_line_2,' ',ad.address_line_3,' ',ltp.description, ' ',pa.postcode) as 'postalAddress' " +
				
				
				",(SELECT GROUP_CONCAT(lang.description,' ') " + 
				"		from language lang " + 
				"		where lang.id in  " + 
				"			(select language_id from users_language where user_id = u.id and home_language = true) " + 
				"		) as 'language' " + 
				
				",(SELECT ofo.description " + 
				"	from ofo_codes ofo " + 
				"	where ofo.id = cl.ofo_codes_id) " + 
				" as 'ofoCodeDescription' " + 

				", gen.gender_name as 'gender' " + 
				", u.date_of_birth as 'dateOfBirth' " + 
				", equi.description as 'equity' " + 
				
			
				", nation.description as 'nationality' " +
				", statssa.description as 'startSaAreaCode' " +		
				", cl.date_learner_registered as 'qmrEnteredDate' " + 
				", cl.date_learner_completed as 'qmrCompletedDate' " + 

				"from " + 
				
				"company_learners cl " + 
				"left join users u on u.id = cl.user_id " + 
				"left join gender gen on gen.id = u.gender_id " + 
				"left join address ad on ad.id = u.residential_address_id " + 
				"left join address pa on pa.id = u.postal_address_id " + 				
				"left join municipality m on m.id = ad.municipality_id " +
				"left join towns lt on lt.id = ad.town_id " + 
				"left join towns ltp on ltp.id = pa.town_id " + 
				"left join region_town lrt on lrt.town_id = lt.id " + 
				"left join region lr on lr.id = lrt.region_id " + 
				
				"left join statssa_area_code statssa on statssa.id = ad.stats_saarea_code_id " + 
				
				"left join users cu on cu.id = cl.create_user_id " + 
				"left join company e on e.id = cl.employer_id " + 
				"left join address ea on ea.id = e.residential_address_id " + 
				"left join municipality municipal on municipal.id = ea.municipality_id " + 
				"left join towns et on et.id = ea.town_id " + 
				"left join region_town ert on ert.town_id = et.id " + 
				"left join region er on er.id = ert.region_id " +
				
				"left join sic_code siccode on siccode.id = e.sic_code_id " +
				"left join chamber cham on cham.id = siccode.chamber_id " +
				"left join equity equi on equi.id = u.equity_id " +
				
				"left join ofo_codes child on child.id = cl.ofo_codes_id " +
				"left join ofo_codes parent on parent.id = child.ofo_codes_id " +

				"left join nationality nation on nation.id = u.nationality_id " +

				"left join company c on c.id = cl.company_id " + 
				"left join intervention_type it on it.id = cl.intervention_type_id " + 
				"left join funding f on f.id = cl.dunding_id " + 
				"left join saqa_qualification hq on hq.id = cl.highest_qualification_id " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id " + 
				"left join skills_program sp on sp.id = cl.skills_program_id " + 
				"left join skills_set ss on ss.id = cl.skills_set_id " + 
				"left join learnership ls on ls.id = cl.learnership_id " + 
				"left join saqa_unitstandard us on us.id = cl.unit_standard_id " + 				
				
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id "+
				"left join company pr on pr.id = tpa.company_id " + 
				"left join address a on a.id = pr.residential_address_id " + 
				"left join municipality mun on mun.id = a.municipality_id " + 
				"left join towns t on t.id = a.town_id " + 
				"left join region_town rt on rt.town_id = t.id " + 
				"left join region r on r.id = rt.region_id " + 
				"where cl.intervention_type_id is not null ";
		return (List<CompanyLearnersBean>)super.nativeSelectSqlList(sql, CompanyLearnersBean.class);
	}
	
	public List<CompanyLearnersBean> generateCompanyLearnerByCompanyReport(Company company) throws Exception {
		String sql = "select " + 
				" cl.create_date as 'createDate' " +
				", u.first_name as 'firstName' "  + 
				", u.middle_name as 'middleName' "  + 
				", u.last_name as 'lastName' "  + 
				", u.email as 'learnerEmail' "  + 
				", u.cell_number as 'cellNumber' " + 
				", u.tel_number as 'telNumber' " + 
				", u.rsa_id_number as 'rsaIDNumber' " + 
				", u.passport_number as 'passportNumber' " + 
				", m.municipality_desc as learnerMunicipality " + 
				", lr.description as 'region' " + 
				", er.description as 'employerRegion' " +
				", r.description as 'providerRegion' " +
				", mun.municipality_desc as 'providerMunicipality' " +
				", municipal.municipality_desc as 'employerMunicipality' " +
				", cham.description as 'chamber' " +
				", it.description as 'interventionType' " + 
				
				", sq.qualificationid_string as 'qualificationCode' " + 
				", sq.qualificationtitle as 'qualificationTitle' " + 
				", sp.programme_id as 'skillsProgrammeCode' " + 
				", sp.description as 'skillsProgrammeDescription' " + 
				", ss.programme_id as 'skillsSetCode' " + 
				", ss.description as 'skillsSetDescription' " + 
				", ls.code as 'learnershipCode' " + 
				", ls.description as 'learnershipDescription' " + 
				", us.unitstandardid as 'unitStandardCode' " + 
				", us.unitstdtitle as 'unitStandardDescription' " + 
				", cl.non_credid_bearing_description as 'nonCreditBearingDescription' " + 				
				
				", cl.approval_date as 'approvalDate' " + 
				", cl.date_learner_registered as 'dateLearnerRegistered' " + 
				", cl.registered_date as 'registrationDate' " +
				", cl.commencement_date as 'commencementDate' " + 
				", cl.completion_date as 'completionDate' " +
				
				",CONCAT(hq.qualificationid, ' (', hq.qualificationtitle,')') as 'highestQualification' " + 
				
				", CASE " + 
				"	when cl.highest_education_enum = 0 then 'Below Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 1 then 'Above Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 2 then 'N1' " + 
				"	when cl.highest_education_enum = 3 then 'N2' " + 
				"	when cl.highest_education_enum = 4 then 'N3' " + 
				"	when cl.highest_education_enum = 5 then 'N4' " + 
				"	when cl.highest_education_enum = 6 then 'N5' " + 
				"	when cl.highest_education_enum = 7 then 'N6' " + 
				"	when cl.highest_education_enum = 8 then 'NCV Level 2' " + 
				"	when cl.highest_education_enum = 9 then 'NCV Level 3' " + 
				"	when cl.highest_education_enum = 10 then 'NCV Level 4' " + 
				"	when cl.highest_education_enum = 11 then 'S1' " + 
				"	when cl.highest_education_enum = 12 then 'S2' " + 
				"	when cl.highest_education_enum = 13 then 'S3' " + 
				"end as 'highestEducation' " + 
				
				", CONCAT(e.company_name, ' (', e.levy_number,')') as 'employerInfor' " + 
				", CONCAT(pr.company_name, ' (', pr.levy_number,')' , tpa.accreditation_number) as 'providerInfor' " + 

				", cl.registration_number as 'registrationNumber' " + 	
				
				", CONCAT(cu.first_name, ' ', cu.last_name, ' (', cu.email,')') as 'createUser' " + 
				
				",CASE " + 
				"	when cl.status = 0 then 'Approved' " + 
				"	when cl.status = 1 then 'Rejected' " + 
				"	when cl.status = 2 then 'Pending Manager Approval' " + 
				"	when cl.status = 3 then 'Pending Approval' " + 
				"	when cl.status = 4 then 'Pending Sign Off' " + 
				"	when cl.status = 5 then 'Completed' " + 
				"	when cl.status = 6 then 'Pending accept code of conduct' " + 
				"	when cl.status = 7 then 'Awaiting DHET'   " + 
				"	when cl.status = 8 then 'Pending Final Approval' " + 
				"	when cl.status = 9 then 'Withdrawn'   " + 
				"	when cl.status = 10 then 'N/A' " + 
				"	when cl.status = 11 then 'Recommended' " + 
				"	when cl.status = 12 then 'Appealed' " + 
				"	when cl.status = 13 then 'Pending Committee Approval' " + 
				"	when cl.status = 14 then 'Approved By ETQA Review Committee' " + 
				"	when cl.status = 15 then 'Rejected By ETQA Review Commitee' " + 
				"	when cl.status = 16 then 'Requested Higher Allocation' " + 
				"	when cl.status = 17 then 'Accepted MOA' " + 
				"	when cl.status = 18 then 'Requested Change' " + 
				"	when cl.status = 19 then 'Rejected By MANCO Review' " + 
				"	when cl.status = 20 then 'Rejected By Learner Review Committee' " + 
				"	when cl.status = 21 then 'Qualification Obtained' " + 
				"	when cl.status = 22 then 'Deactivated' " + 
				"	when cl.status = 23 then 'Project Terminated Manager Approval' " + 
				"	when cl.status = 24 then 'Suspend Project' " + 
				"	when cl.status = 25 then 'Project Terminated' " + 
				"	when cl.status = 26 then 'Pending Review Approval' " + 
				"	when cl.status = 27 then 'Uphold' " + 
				"	when cl.status = 28 then 'Pending Resubmission'   " + 
				"	when cl.status = 29 then 'Awaiting NAMB' " + 
				"	when cl.status = 30 then 'Pending Withdrawal' " + 
				"	when cl.status = 31 then 'Pending Inverstigation' " + 
				"	when cl.status = 32 then 'Pending Change Approval' " + 
				"	when cl.status = 33 then 'Not Competent' " + 
				"end as 'approvalStatus' " + 
				
				",CASE when cl.employment_status = 0 then 'Employed' " + 
				"	when cl.employment_status = 1 then 'Unemployed' " + 
				"end as 'employmentStatus' " + 
				
				",CASE when u.urban_rural_enum = 0 then 'Urban' " + 
				"	   when u.urban_rural_enum = 1 then 'Rural' " + 
				"end as 'urbanRural' " + 
				
				",CASE when u.disability = 0 then 'Yes' " + 
				"	   when u.disability = 1 then 'No' " + 
				"end as 'disability' " +
				
				",CASE when cl.merseta_funded = 0 then 'Yes' " + 
				"	   when cl.merseta_funded = 1 then 'No' " + 
				"end as 'fundedBy' " +
				
				",CASE when cl.legacy_id is not null then 'Yes' " + 
				"	   when cl.legacy_id is null then 'No' " + 
				"end as 'legacyLearner' " +
				
				",CASE when child.ofo_code is not null then child.ofo_code " + 
				"	   when parent.ofo_code is not null then parent.ofo_code " + 
				"end as 'ofoCode' " +
				
				",CASE " + 
				"	when cl.learner_status = 0 then 'Application' " + 
				"	when cl.learner_status = 1 then 'Registered' " + 
				"	when cl.learner_status = 2 then 'Transfer Pending Approval' " + 
				"	when cl.learner_status = 3 then 'Terminated' " + 
				"	when cl.learner_status = 4 then 'Pending Termination' " + 
				"	when cl.learner_status = 5 then 'Pending Change Approval' " + 
				"	when cl.learner_status = 6 then 'Completed' " + 
				"	when cl.learner_status = 7 then 'Pending Trade Test' " + 
				"	when cl.learner_status = 8 then 'Pending ARPL Trade Test' " + 
				"	when cl.learner_status = 9 then 'Pending Review Approval' " + 
				"	when cl.learner_status = 10 then 'Qualification Obtained' " + 
				"	when cl.learner_status = 11 then 'Pending non-merSETA Qualification Approval' " + 
				"	when cl.learner_status = 12 then 'Pending Verification of Assesment Approval' " + 
				"	when cl.learner_status = 13 then 'Pending Completion Letter Approval' " + 
				"	when cl.learner_status = 14 then 'Withdrawn' " + 
				"	when cl.learner_status = 15 then 'Rejected' " + 
				"	when cl.learner_status = 16 then 'Achieved' " + 
				"	when cl.learner_status = 17 then 'Pending Lost Time' " + 
				"end as 'learnerStatus' " + 
								
				", CONCAT(ad.address_line_1, ' ',ad.address_line_2,' ',ad.address_line_3,' ',lt.description, ' ',ad.postcode) as 'residentialAddress' " + 
				", CONCAT(pa.address_line_1, ' ',pa.address_line_2,' ',ad.address_line_3,' ',ltp.description, ' ',pa.postcode) as 'postalAddress' " +
				
				
				",(SELECT GROUP_CONCAT(lang.description,' ') " + 
				"		from language lang " + 
				"		where lang.id in  " + 
				"			(select language_id from users_language where user_id = u.id and home_language = true) " + 
				"		) as 'language' " + 
				
				",(SELECT ofo.description " + 
				"	from ofo_codes ofo " + 
				"	where ofo.id = cl.ofo_codes_id) " + 
				" as 'ofoCodeDescription' " + 

				", gen.gender_name as 'gender' " + 
				", u.date_of_birth as 'dateOfBirth' " + 
				", equi.description as 'equity' " + 
				
			
				", nation.description as 'nationality' " +
				", statssa.description as 'startSaAreaCode' " +		
				", cl.date_learner_registered as 'qmrEnteredDate' " + 
				", cl.date_learner_completed as 'qmrCompletedDate' " + 

				"from " + 
				
				"company_learners cl " + 
				"left join users u on u.id = cl.user_id " + 
				"left join gender gen on gen.id = u.gender_id " + 
				"left join address ad on ad.id = u.residential_address_id " + 
				"left join address pa on pa.id = u.postal_address_id " + 				
				"left join municipality m on m.id = ad.municipality_id " +
				"left join towns lt on lt.id = ad.town_id " + 
				"left join towns ltp on ltp.id = pa.town_id " + 
				"left join region_town lrt on lrt.town_id = lt.id " + 
				"left join region lr on lr.id = lrt.region_id " + 
				
				"left join statssa_area_code statssa on statssa.id = ad.stats_saarea_code_id " + 
				
				"left join users cu on cu.id = cl.create_user_id " + 
				"left join company e on e.id = cl.employer_id " + 
				"left join address ea on ea.id = e.residential_address_id " + 
				"left join municipality municipal on municipal.id = ea.municipality_id " + 
				"left join towns et on et.id = ea.town_id " + 
				"left join region_town ert on ert.town_id = et.id " + 
				"left join region er on er.id = ert.region_id " +
				
				"left join sic_code siccode on siccode.id = e.sic_code_id " +
				"left join chamber cham on cham.id = siccode.chamber_id " +
				"left join equity equi on equi.id = u.equity_id " +
				
				"left join ofo_codes child on child.id = cl.ofo_codes_id " +
				"left join ofo_codes parent on parent.id = child.ofo_codes_id " +

				"left join nationality nation on nation.id = u.nationality_id " +

				"left join company c on c.id = cl.company_id " + 
				"left join intervention_type it on it.id = cl.intervention_type_id " + 
				"left join funding f on f.id = cl.dunding_id " + 
				"left join saqa_qualification hq on hq.id = cl.highest_qualification_id " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id " + 
				"left join skills_program sp on sp.id = cl.skills_program_id " + 
				"left join skills_set ss on ss.id = cl.skills_set_id " + 
				"left join learnership ls on ls.id = cl.learnership_id " + 
				"left join saqa_unitstandard us on us.id = cl.unit_standard_id " + 				
				
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id "+
				"left join company pr on pr.id = tpa.company_id " + 
				"left join address a on a.id = pr.residential_address_id " + 
				"left join municipality mun on mun.id = a.municipality_id " + 
				"left join towns t on t.id = a.town_id " + 
				"left join region_town rt on rt.town_id = t.id " + 
				"left join region r on r.id = rt.region_id " + 
				"where cl.intervention_type_id is not null " + 
				"and (cl.company_id = :companyID or  cl.employer_id = :companyID)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("companyID", company.getId());
		return (List<CompanyLearnersBean>)super.nativeSelectSqlList(sql, CompanyLearnersBean.class, parameters);
	}
	
	public List<CompanyLearnersBean> generateCompanyLearnerReport(Chamber chamber, InterventionType interventionType, LearnerStatusEnum learnerStatusEnum) throws Exception {
		String sql ="select  " + 
				" cl.create_date as 'createDate' " +
				", u.first_name as 'firstName' "  + 
				", u.middle_name as 'middleName' "  + 
				", u.last_name as 'lastName' "  + 
				", u.email as 'learnerEmail' "  + 
				", u.cell_number as 'cellNumber' " + 
				", u.tel_number as 'telNumber' " + 
				", u.rsa_id_number as 'rsaIDNumber' " + 
				", u.passport_number as 'passportNumber' " + 
				", m.municipality_desc as learnerMunicipality " + 
				", lr.description as 'region' " + 
				", er.description as 'employerRegion' " +
				", r.description as 'providerRegion' " +
				", mun.municipality_desc as 'providerMunicipality' " +
				", municipal.municipality_desc as 'employerMunicipality' " +
				", cham.description as 'chamber' " +
				", it.description as 'interventionType' " + 
				
				", sq.qualificationid_string as 'qualificationCode' " + 
				", sq.qualificationtitle as 'qualificationTitle' " + 
				", sp.programme_id as 'skillsProgrammeCode' " + 
				", sp.description as 'skillsProgrammeDescription' " + 
				", ss.programme_id as 'skillsSetCode' " + 
				", ss.description as 'skillsSetDescription' " + 
				", ls.code as 'learnershipCode' " + 
				", ls.description as 'learnershipDescription' " + 
				", us.unitstandardid as 'unitStandardCode' " + 
				", us.unitstdtitle as 'unitStandardDescription' " + 
				", cl.non_credid_bearing_description as 'nonCreditBearingDescription' " + 				
				
				", cl.approval_date as 'approvalDate' " + 
				", cl.date_learner_registered as 'dateLearnerRegistered' " + 
				", cl.registered_date as 'registrationDate' " +
				", cl.commencement_date as 'commencementDate' " + 
				", cl.completion_date as 'completionDate' " +
				
				",CONCAT(hq.qualificationid, ' (', hq.qualificationtitle,')') as 'highestQualification' " + 
				
				", CASE " + 
				"	when cl.highest_education_enum = 0 then 'Below Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 1 then 'Above Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 2 then 'N1' " + 
				"	when cl.highest_education_enum = 3 then 'N2' " + 
				"	when cl.highest_education_enum = 4 then 'N3' " + 
				"	when cl.highest_education_enum = 5 then 'N4' " + 
				"	when cl.highest_education_enum = 6 then 'N5' " + 
				"	when cl.highest_education_enum = 7 then 'N6' " + 
				"	when cl.highest_education_enum = 8 then 'NCV Level 2' " + 
				"	when cl.highest_education_enum = 9 then 'NCV Level 3' " + 
				"	when cl.highest_education_enum = 10 then 'NCV Level 4' " + 
				"	when cl.highest_education_enum = 11 then 'S1' " + 
				"	when cl.highest_education_enum = 12 then 'S2' " + 
				"	when cl.highest_education_enum = 13 then 'S3' " + 
				"end as 'highestEducation' " + 
				
				", CONCAT(e.company_name, ' (', e.levy_number,')') as 'employerInfor' " + 
				", CONCAT(pr.company_name, ' (', pr.levy_number,')' , tpa.accreditation_number) as 'providerInfor' " + 

				", cl.registration_number as 'registrationNumber' " + 	
				
				", CONCAT(cu.first_name, ' ', cu.last_name, ' (', cu.email,')') as 'createUser' " + 
				
				",CASE " + 
				"	when cl.status = 0 then 'Approved' " + 
				"	when cl.status = 1 then 'Rejected' " + 
				"	when cl.status = 2 then 'Pending Manager Approval' " + 
				"	when cl.status = 3 then 'Pending Approval' " + 
				"	when cl.status = 4 then 'Pending Sign Off' " + 
				"	when cl.status = 5 then 'Completed' " + 
				"	when cl.status = 6 then 'Pending accept code of conduct' " + 
				"	when cl.status = 7 then 'Awaiting DHET'   " + 
				"	when cl.status = 8 then 'Pending Final Approval' " + 
				"	when cl.status = 9 then 'Withdrawn'   " + 
				"	when cl.status = 10 then 'N/A' " + 
				"	when cl.status = 11 then 'Recommended' " + 
				"	when cl.status = 12 then 'Appealed' " + 
				"	when cl.status = 13 then 'Pending Committee Approval' " + 
				"	when cl.status = 14 then 'Approved By ETQA Review Committee' " + 
				"	when cl.status = 15 then 'Rejected By ETQA Review Commitee' " + 
				"	when cl.status = 16 then 'Requested Higher Allocation' " + 
				"	when cl.status = 17 then 'Accepted MOA' " + 
				"	when cl.status = 18 then 'Requested Change' " + 
				"	when cl.status = 19 then 'Rejected By MANCO Review' " + 
				"	when cl.status = 20 then 'Rejected By Learner Review Committee' " + 
				"	when cl.status = 21 then 'Qualification Obtained' " + 
				"	when cl.status = 22 then 'Deactivated' " + 
				"	when cl.status = 23 then 'Project Terminated Manager Approval' " + 
				"	when cl.status = 24 then 'Suspend Project' " + 
				"	when cl.status = 25 then 'Project Terminated' " + 
				"	when cl.status = 26 then 'Pending Review Approval' " + 
				"	when cl.status = 27 then 'Uphold' " + 
				"	when cl.status = 28 then 'Pending Resubmission'   " + 
				"	when cl.status = 29 then 'Awaiting NAMB' " + 
				"	when cl.status = 30 then 'Pending Withdrawal' " + 
				"	when cl.status = 31 then 'Pending Inverstigation' " + 
				"	when cl.status = 32 then 'Pending Change Approval' " + 
				"	when cl.status = 33 then 'Not Competent' " + 
				"end as 'approvalStatus' " + 
				
				",CASE when cl.employment_status = 0 then 'Employed' " + 
				"	when cl.employment_status = 1 then 'Unemployed' " + 
				"end as 'employmentStatus' " + 
				
				",CASE when u.urban_rural_enum = 0 then 'Urban' " + 
				"	   when u.urban_rural_enum = 1 then 'Rural' " + 
				"end as 'urbanRural' " + 
				
				",CASE when u.disability = 0 then 'Yes' " + 
				"	   when u.disability = 1 then 'No' " + 
				"end as 'disability' " +
				
				",CASE when cl.merseta_funded = 0 then 'Yes' " + 
				"	   when cl.merseta_funded = 1 then 'No' " + 
				"end as 'fundedBy' " +
				
				",CASE when cl.legacy_id is not null then 'Yes' " + 
				"	   when cl.legacy_id is null then 'No' " + 
				"end as 'legacyLearner' " +
				
				",CASE when child.ofo_code is not null then child.ofo_code " + 
				"	   when parent.ofo_code is not null then parent.ofo_code " + 
				"end as 'ofoCode' " +
				
				",CASE " + 
				"	when cl.learner_status = 0 then 'Application' " + 
				"	when cl.learner_status = 1 then 'Registered' " + 
				"	when cl.learner_status = 2 then 'Transfer Pending Approval' " + 
				"	when cl.learner_status = 3 then 'Terminated' " + 
				"	when cl.learner_status = 4 then 'Pending Termination' " + 
				"	when cl.learner_status = 5 then 'Pending Change Approval' " + 
				"	when cl.learner_status = 6 then 'Completed' " + 
				"	when cl.learner_status = 7 then 'Pending Trade Test' " + 
				"	when cl.learner_status = 8 then 'Pending ARPL Trade Test' " + 
				"	when cl.learner_status = 9 then 'Pending Review Approval' " + 
				"	when cl.learner_status = 10 then 'Qualification Obtained' " + 
				"	when cl.learner_status = 11 then 'Pending non-merSETA Qualification Approval' " + 
				"	when cl.learner_status = 12 then 'Pending Verification of Assesment Approval' " + 
				"	when cl.learner_status = 13 then 'Pending Completion Letter Approval' " + 
				"	when cl.learner_status = 14 then 'Withdrawn' " + 
				"	when cl.learner_status = 15 then 'Rejected' " + 
				"	when cl.learner_status = 16 then 'Achieved' " + 
				"	when cl.learner_status = 17 then 'Pending Lost Time' " + 
				"end as 'learnerStatus' " + 
								
				", CONCAT(ad.address_line_1, ' ',ad.address_line_2,' ',ad.address_line_3,' ',lt.description, ' ',ad.postcode) as 'residentialAddress' " + 
				", CONCAT(pa.address_line_1, ' ',pa.address_line_2,' ',ad.address_line_3,' ',ltp.description, ' ',pa.postcode) as 'postalAddress' " +
				
				
				",(SELECT GROUP_CONCAT(lang.description,' ') " + 
				"		from language lang " + 
				"		where lang.id in  " + 
				"			(select language_id from users_language where user_id = u.id and home_language = true) " + 
				"		) as 'language' " + 
				
				",(SELECT ofo.description " + 
				"	from ofo_codes ofo " + 
				"	where ofo.id = cl.ofo_codes_id) " + 
				" as 'ofoCodeDescription' " + 

				", gen.gender_name as 'gender' " + 
				", u.date_of_birth as 'dateOfBirth' " + 
				", equi.description as 'equity' " + 
				
			
				", nation.description as 'nationality' " +
				", statssa.description as 'startSaAreaCode' " +		
				", cl.date_learner_registered as 'qmrEnteredDate' " + 
				", cl.date_learner_completed as 'qmrCompletedDate' " + 

				"from " + 
				
				"company_learners cl " + 
				"left join users u on u.id = cl.user_id " + 
				"left join gender gen on gen.id = u.gender_id " + 
				"left join address ad on ad.id = u.residential_address_id " + 
				"left join address pa on pa.id = u.postal_address_id " + 				
				"left join municipality m on m.id = ad.municipality_id " +
				"left join towns lt on lt.id = ad.town_id " + 
				"left join towns ltp on ltp.id = pa.town_id " + 
				"left join region_town lrt on lrt.town_id = lt.id " + 
				"left join region lr on lr.id = lrt.region_id " + 
				
				"left join statssa_area_code statssa on statssa.id = ad.stats_saarea_code_id " + 
				
				"left join users cu on cu.id = cl.create_user_id " + 
				"left join company e on e.id = cl.employer_id " + 
				"left join address ea on ea.id = e.residential_address_id " + 
				"left join municipality municipal on municipal.id = ea.municipality_id " + 
				"left join towns et on et.id = ea.town_id " + 
				"left join region_town ert on ert.town_id = et.id " + 
				"left join region er on er.id = ert.region_id " +
				
				"left join sic_code siccode on siccode.id = e.sic_code_id " +
				"left join chamber cham on cham.id = siccode.chamber_id " +
				"left join equity equi on equi.id = u.equity_id " +
				
				"left join ofo_codes child on child.id = cl.ofo_codes_id " +
				"left join ofo_codes parent on parent.id = child.ofo_codes_id " +

				"left join nationality nation on nation.id = u.nationality_id " +

				"left join company c on c.id = cl.company_id " + 
				"left join intervention_type it on it.id = cl.intervention_type_id " + 
				"left join funding f on f.id = cl.dunding_id " + 
				"left join saqa_qualification hq on hq.id = cl.highest_qualification_id " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id " + 
				"left join skills_program sp on sp.id = cl.skills_program_id " + 
				"left join skills_set ss on ss.id = cl.skills_set_id " + 
				"left join learnership ls on ls.id = cl.learnership_id " + 
				"left join saqa_unitstandard us on us.id = cl.unit_standard_id " + 				
				
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id "+
				"left join company pr on pr.id = tpa.company_id " + 
				"left join address a on a.id = pr.residential_address_id " + 
				"left join municipality mun on mun.id = a.municipality_id " + 
				"left join towns t on t.id = a.town_id " + 
				"left join region_town rt on rt.town_id = t.id " + 
				"left join region r on r.id = rt.region_id " +  
				"where it.id = :interventionID " + 
				"and cham.id = :chamberID " +
				"and cl.learner_status = :learnerStatusEnum " + 
				"order by cl.id asc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("chamberID", chamber.getId());
		parameters.put("interventionID", interventionType.getId());
		parameters.put("learnerStatusEnum", learnerStatusEnum.ordinal());
		return (List<CompanyLearnersBean>)super.nativeSelectSqlList(sql, CompanyLearnersBean.class, parameters);
	}
	
	public List<CompanyLearnersBean> generateCompanyLearnerReport(Chamber chamber, InterventionType interventionType) throws Exception {
		String sql ="select  " + 
				" cl.create_date as 'createDate' " +
				", u.first_name as 'firstName' "  + 
				", u.middle_name as 'middleName' "  + 
				", u.last_name as 'lastName' "  + 
				", u.email as 'learnerEmail' "  + 
				", u.cell_number as 'cellNumber' " + 
				", u.tel_number as 'telNumber' " + 
				", u.rsa_id_number as 'rsaIDNumber' " + 
				", u.passport_number as 'passportNumber' " + 
				", m.municipality_desc as learnerMunicipality " + 
				", lr.description as 'region' " + 
				", er.description as 'employerRegion' " +
				", r.description as 'providerRegion' " +
				", mun.municipality_desc as 'providerMunicipality' " +
				", municipal.municipality_desc as 'employerMunicipality' " +
				", cham.description as 'chamber' " +
				", it.description as 'interventionType' " + 
				
				", sq.qualificationid_string as 'qualificationCode' " + 
				", sq.qualificationtitle as 'qualificationTitle' " + 
				", sp.programme_id as 'skillsProgrammeCode' " + 
				", sp.description as 'skillsProgrammeDescription' " + 
				", ss.programme_id as 'skillsSetCode' " + 
				", ss.description as 'skillsSetDescription' " + 
				", ls.code as 'learnershipCode' " + 
				", ls.description as 'learnershipDescription' " + 
				", us.unitstandardid as 'unitStandardCode' " + 
				", us.unitstdtitle as 'unitStandardDescription' " + 
				", cl.non_credid_bearing_description as 'nonCreditBearingDescription' " + 				
				
				", cl.approval_date as 'approvalDate' " + 
				", cl.date_learner_registered as 'dateLearnerRegistered' " + 
				", cl.registered_date as 'registrationDate' " +
				", cl.commencement_date as 'commencementDate' " + 
				", cl.completion_date as 'completionDate' " +
				
				",CONCAT(hq.qualificationid, ' (', hq.qualificationtitle,')') as 'highestQualification' " + 
				
				", CASE " + 
				"	when cl.highest_education_enum = 0 then 'Below Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 1 then 'Above Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 2 then 'N1' " + 
				"	when cl.highest_education_enum = 3 then 'N2' " + 
				"	when cl.highest_education_enum = 4 then 'N3' " + 
				"	when cl.highest_education_enum = 5 then 'N4' " + 
				"	when cl.highest_education_enum = 6 then 'N5' " + 
				"	when cl.highest_education_enum = 7 then 'N6' " + 
				"	when cl.highest_education_enum = 8 then 'NCV Level 2' " + 
				"	when cl.highest_education_enum = 9 then 'NCV Level 3' " + 
				"	when cl.highest_education_enum = 10 then 'NCV Level 4' " + 
				"	when cl.highest_education_enum = 11 then 'S1' " + 
				"	when cl.highest_education_enum = 12 then 'S2' " + 
				"	when cl.highest_education_enum = 13 then 'S3' " + 
				"end as 'highestEducation' " + 
				
				", CONCAT(e.company_name, ' (', e.levy_number,')') as 'employerInfor' " + 
				", CONCAT(pr.company_name, ' (', pr.levy_number,')' , tpa.accreditation_number) as 'providerInfor' " + 

				", cl.registration_number as 'registrationNumber' " + 	
				
				", CONCAT(cu.first_name, ' ', cu.last_name, ' (', cu.email,')') as 'createUser' " + 
				
				",CASE " + 
				"	when cl.status = 0 then 'Approved' " + 
				"	when cl.status = 1 then 'Rejected' " + 
				"	when cl.status = 2 then 'Pending Manager Approval' " + 
				"	when cl.status = 3 then 'Pending Approval' " + 
				"	when cl.status = 4 then 'Pending Sign Off' " + 
				"	when cl.status = 5 then 'Completed' " + 
				"	when cl.status = 6 then 'Pending accept code of conduct' " + 
				"	when cl.status = 7 then 'Awaiting DHET'   " + 
				"	when cl.status = 8 then 'Pending Final Approval' " + 
				"	when cl.status = 9 then 'Withdrawn'   " + 
				"	when cl.status = 10 then 'N/A' " + 
				"	when cl.status = 11 then 'Recommended' " + 
				"	when cl.status = 12 then 'Appealed' " + 
				"	when cl.status = 13 then 'Pending Committee Approval' " + 
				"	when cl.status = 14 then 'Approved By ETQA Review Committee' " + 
				"	when cl.status = 15 then 'Rejected By ETQA Review Commitee' " + 
				"	when cl.status = 16 then 'Requested Higher Allocation' " + 
				"	when cl.status = 17 then 'Accepted MOA' " + 
				"	when cl.status = 18 then 'Requested Change' " + 
				"	when cl.status = 19 then 'Rejected By MANCO Review' " + 
				"	when cl.status = 20 then 'Rejected By Learner Review Committee' " + 
				"	when cl.status = 21 then 'Qualification Obtained' " + 
				"	when cl.status = 22 then 'Deactivated' " + 
				"	when cl.status = 23 then 'Project Terminated Manager Approval' " + 
				"	when cl.status = 24 then 'Suspend Project' " + 
				"	when cl.status = 25 then 'Project Terminated' " + 
				"	when cl.status = 26 then 'Pending Review Approval' " + 
				"	when cl.status = 27 then 'Uphold' " + 
				"	when cl.status = 28 then 'Pending Resubmission'   " + 
				"	when cl.status = 29 then 'Awaiting NAMB' " + 
				"	when cl.status = 30 then 'Pending Withdrawal' " + 
				"	when cl.status = 31 then 'Pending Inverstigation' " + 
				"	when cl.status = 32 then 'Pending Change Approval' " + 
				"	when cl.status = 33 then 'Not Competent' " + 
				"end as 'approvalStatus' " + 
				
				",CASE when cl.employment_status = 0 then 'Employed' " + 
				"	when cl.employment_status = 1 then 'Unemployed' " + 
				"end as 'employmentStatus' " + 
				
				",CASE when u.urban_rural_enum = 0 then 'Urban' " + 
				"	   when u.urban_rural_enum = 1 then 'Rural' " + 
				"end as 'urbanRural' " + 
				
				",CASE when u.disability = 0 then 'Yes' " + 
				"	   when u.disability = 1 then 'No' " + 
				"end as 'disability' " +
				
				",CASE when cl.merseta_funded = 0 then 'Yes' " + 
				"	   when cl.merseta_funded = 1 then 'No' " + 
				"end as 'fundedBy' " +
				
				",CASE when cl.legacy_id is not null then 'Yes' " + 
				"	   when cl.legacy_id is null then 'No' " + 
				"end as 'legacyLearner' " +
				
				",CASE when child.ofo_code is not null then child.ofo_code " + 
				"	   when parent.ofo_code is not null then parent.ofo_code " + 
				"end as 'ofoCode' " +
				
				",CASE " + 
				"	when cl.learner_status = 0 then 'Application' " + 
				"	when cl.learner_status = 1 then 'Registered' " + 
				"	when cl.learner_status = 2 then 'Transfer Pending Approval' " + 
				"	when cl.learner_status = 3 then 'Terminated' " + 
				"	when cl.learner_status = 4 then 'Pending Termination' " + 
				"	when cl.learner_status = 5 then 'Pending Change Approval' " + 
				"	when cl.learner_status = 6 then 'Completed' " + 
				"	when cl.learner_status = 7 then 'Pending Trade Test' " + 
				"	when cl.learner_status = 8 then 'Pending ARPL Trade Test' " + 
				"	when cl.learner_status = 9 then 'Pending Review Approval' " + 
				"	when cl.learner_status = 10 then 'Qualification Obtained' " + 
				"	when cl.learner_status = 11 then 'Pending non-merSETA Qualification Approval' " + 
				"	when cl.learner_status = 12 then 'Pending Verification of Assesment Approval' " + 
				"	when cl.learner_status = 13 then 'Pending Completion Letter Approval' " + 
				"	when cl.learner_status = 14 then 'Withdrawn' " + 
				"	when cl.learner_status = 15 then 'Rejected' " + 
				"	when cl.learner_status = 16 then 'Achieved' " + 
				"	when cl.learner_status = 17 then 'Pending Lost Time' " + 
				"end as 'learnerStatus' " + 
								
				", CONCAT(ad.address_line_1, ' ',ad.address_line_2,' ',ad.address_line_3,' ',lt.description, ' ',ad.postcode) as 'residentialAddress' " + 
				", CONCAT(pa.address_line_1, ' ',pa.address_line_2,' ',ad.address_line_3,' ',ltp.description, ' ',pa.postcode) as 'postalAddress' " +
				
				
				",(SELECT GROUP_CONCAT(lang.description,' ') " + 
				"		from language lang " + 
				"		where lang.id in  " + 
				"			(select language_id from users_language where user_id = u.id and home_language = true) " + 
				"		) as 'language' " + 
				
				",(SELECT ofo.description " + 
				"	from ofo_codes ofo " + 
				"	where ofo.id = cl.ofo_codes_id) " + 
				" as 'ofoCodeDescription' " + 

				", gen.gender_name as 'gender' " + 
				", u.date_of_birth as 'dateOfBirth' " + 
				", equi.description as 'equity' " + 
				
			
				", nation.description as 'nationality' " +
				", statssa.description as 'startSaAreaCode' " +		
				", cl.date_learner_registered as 'qmrEnteredDate' " + 
				", cl.date_learner_completed as 'qmrCompletedDate' " + 

				"from " + 
				
				"company_learners cl " + 
				"left join users u on u.id = cl.user_id " + 
				"left join gender gen on gen.id = u.gender_id " + 
				"left join address ad on ad.id = u.residential_address_id " + 
				"left join address pa on pa.id = u.postal_address_id " + 				
				"left join municipality m on m.id = ad.municipality_id " +
				"left join towns lt on lt.id = ad.town_id " + 
				"left join towns ltp on ltp.id = pa.town_id " + 
				"left join region_town lrt on lrt.town_id = lt.id " + 
				"left join region lr on lr.id = lrt.region_id " + 
				
				"left join statssa_area_code statssa on statssa.id = ad.stats_saarea_code_id " + 
				
				"left join users cu on cu.id = cl.create_user_id " + 
				"left join company e on e.id = cl.employer_id " + 
				"left join address ea on ea.id = e.residential_address_id " + 
				"left join municipality municipal on municipal.id = ea.municipality_id " + 
				"left join towns et on et.id = ea.town_id " + 
				"left join region_town ert on ert.town_id = et.id " + 
				"left join region er on er.id = ert.region_id " +
				
				"left join sic_code siccode on siccode.id = e.sic_code_id " +
				"left join chamber cham on cham.id = siccode.chamber_id " +
				"left join equity equi on equi.id = u.equity_id " +
				
				"left join ofo_codes child on child.id = cl.ofo_codes_id " +
				"left join ofo_codes parent on parent.id = child.ofo_codes_id " +

				"left join nationality nation on nation.id = u.nationality_id " +

				"left join company c on c.id = cl.company_id " + 
				"left join intervention_type it on it.id = cl.intervention_type_id " + 
				"left join funding f on f.id = cl.dunding_id " + 
				"left join saqa_qualification hq on hq.id = cl.highest_qualification_id " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id " + 
				"left join skills_program sp on sp.id = cl.skills_program_id " + 
				"left join skills_set ss on ss.id = cl.skills_set_id " + 
				"left join learnership ls on ls.id = cl.learnership_id " + 
				"left join saqa_unitstandard us on us.id = cl.unit_standard_id " + 				
				
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id "+
				"left join company pr on pr.id = tpa.company_id " + 
				"left join address a on a.id = pr.residential_address_id " + 
				"left join municipality mun on mun.id = a.municipality_id " + 
				"left join towns t on t.id = a.town_id " + 
				"left join region_town rt on rt.town_id = t.id " + 
				"left join region r on r.id = rt.region_id " +  
				"where it.id = :interventionID " + 
				"and cham.id = :chamberID " +
				"order by cl.id asc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("chamberID", chamber.getId());
		parameters.put("interventionID", interventionType.getId());
		return (List<CompanyLearnersBean>)super.nativeSelectSqlList(sql, CompanyLearnersBean.class, parameters);
	}
	
	public List<CompanyLearnersBean> generateCompanyLearnerReport(Chamber chamber, LearnerStatusEnum learnerStatusEnum) throws Exception {
		String sql ="select  " + 
				" cl.create_date as 'createDate' " +
				", u.first_name as 'firstName' "  + 
				", u.middle_name as 'middleName' "  + 
				", u.last_name as 'lastName' "  + 
				", u.email as 'learnerEmail' "  + 
				", u.cell_number as 'cellNumber' " + 
				", u.tel_number as 'telNumber' " + 
				", u.rsa_id_number as 'rsaIDNumber' " + 
				", u.passport_number as 'passportNumber' " + 
				", m.municipality_desc as learnerMunicipality " + 
				", lr.description as 'region' " + 
				", er.description as 'employerRegion' " +
				", r.description as 'providerRegion' " +
				", mun.municipality_desc as 'providerMunicipality' " +
				", municipal.municipality_desc as 'employerMunicipality' " +
				", cham.description as 'chamber' " +
				", it.description as 'interventionType' " + 
				
				", sq.qualificationid_string as 'qualificationCode' " + 
				", sq.qualificationtitle as 'qualificationTitle' " + 
				", sp.programme_id as 'skillsProgrammeCode' " + 
				", sp.description as 'skillsProgrammeDescription' " + 
				", ss.programme_id as 'skillsSetCode' " + 
				", ss.description as 'skillsSetDescription' " + 
				", ls.code as 'learnershipCode' " + 
				", ls.description as 'learnershipDescription' " + 
				", us.unitstandardid as 'unitStandardCode' " + 
				", us.unitstdtitle as 'unitStandardDescription' " + 
				", cl.non_credid_bearing_description as 'nonCreditBearingDescription' " + 				
				
				", cl.approval_date as 'approvalDate' " + 
				", cl.date_learner_registered as 'dateLearnerRegistered' " + 
				", cl.registered_date as 'registrationDate' " +
				", cl.commencement_date as 'commencementDate' " + 
				", cl.completion_date as 'completionDate' " +
				
				",CONCAT(hq.qualificationid, ' (', hq.qualificationtitle,')') as 'highestQualification' " + 
				
				", CASE " + 
				"	when cl.highest_education_enum = 0 then 'Below Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 1 then 'Above Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 2 then 'N1' " + 
				"	when cl.highest_education_enum = 3 then 'N2' " + 
				"	when cl.highest_education_enum = 4 then 'N3' " + 
				"	when cl.highest_education_enum = 5 then 'N4' " + 
				"	when cl.highest_education_enum = 6 then 'N5' " + 
				"	when cl.highest_education_enum = 7 then 'N6' " + 
				"	when cl.highest_education_enum = 8 then 'NCV Level 2' " + 
				"	when cl.highest_education_enum = 9 then 'NCV Level 3' " + 
				"	when cl.highest_education_enum = 10 then 'NCV Level 4' " + 
				"	when cl.highest_education_enum = 11 then 'S1' " + 
				"	when cl.highest_education_enum = 12 then 'S2' " + 
				"	when cl.highest_education_enum = 13 then 'S3' " + 
				"end as 'highestEducation' " + 
				
				", CONCAT(e.company_name, ' (', e.levy_number,')') as 'employerInfor' " + 
				", CONCAT(pr.company_name, ' (', pr.levy_number,')' , tpa.accreditation_number) as 'providerInfor' " + 

				", cl.registration_number as 'registrationNumber' " + 	
				
				", CONCAT(cu.first_name, ' ', cu.last_name, ' (', cu.email,')') as 'createUser' " + 
				
				",CASE " + 
				"	when cl.status = 0 then 'Approved' " + 
				"	when cl.status = 1 then 'Rejected' " + 
				"	when cl.status = 2 then 'Pending Manager Approval' " + 
				"	when cl.status = 3 then 'Pending Approval' " + 
				"	when cl.status = 4 then 'Pending Sign Off' " + 
				"	when cl.status = 5 then 'Completed' " + 
				"	when cl.status = 6 then 'Pending accept code of conduct' " + 
				"	when cl.status = 7 then 'Awaiting DHET'   " + 
				"	when cl.status = 8 then 'Pending Final Approval' " + 
				"	when cl.status = 9 then 'Withdrawn'   " + 
				"	when cl.status = 10 then 'N/A' " + 
				"	when cl.status = 11 then 'Recommended' " + 
				"	when cl.status = 12 then 'Appealed' " + 
				"	when cl.status = 13 then 'Pending Committee Approval' " + 
				"	when cl.status = 14 then 'Approved By ETQA Review Committee' " + 
				"	when cl.status = 15 then 'Rejected By ETQA Review Commitee' " + 
				"	when cl.status = 16 then 'Requested Higher Allocation' " + 
				"	when cl.status = 17 then 'Accepted MOA' " + 
				"	when cl.status = 18 then 'Requested Change' " + 
				"	when cl.status = 19 then 'Rejected By MANCO Review' " + 
				"	when cl.status = 20 then 'Rejected By Learner Review Committee' " + 
				"	when cl.status = 21 then 'Qualification Obtained' " + 
				"	when cl.status = 22 then 'Deactivated' " + 
				"	when cl.status = 23 then 'Project Terminated Manager Approval' " + 
				"	when cl.status = 24 then 'Suspend Project' " + 
				"	when cl.status = 25 then 'Project Terminated' " + 
				"	when cl.status = 26 then 'Pending Review Approval' " + 
				"	when cl.status = 27 then 'Uphold' " + 
				"	when cl.status = 28 then 'Pending Resubmission'   " + 
				"	when cl.status = 29 then 'Awaiting NAMB' " + 
				"	when cl.status = 30 then 'Pending Withdrawal' " + 
				"	when cl.status = 31 then 'Pending Inverstigation' " + 
				"	when cl.status = 32 then 'Pending Change Approval' " + 
				"	when cl.status = 33 then 'Not Competent' " + 
				"end as 'approvalStatus' " + 
				
				",CASE when cl.employment_status = 0 then 'Employed' " + 
				"	when cl.employment_status = 1 then 'Unemployed' " + 
				"end as 'employmentStatus' " + 
				
				",CASE when u.urban_rural_enum = 0 then 'Urban' " + 
				"	   when u.urban_rural_enum = 1 then 'Rural' " + 
				"end as 'urbanRural' " + 
				
				",CASE when u.disability = 0 then 'Yes' " + 
				"	   when u.disability = 1 then 'No' " + 
				"end as 'disability' " +
				
				",CASE when cl.merseta_funded = 0 then 'Yes' " + 
				"	   when cl.merseta_funded = 1 then 'No' " + 
				"end as 'fundedBy' " +
				
				",CASE when cl.legacy_id is not null then 'Yes' " + 
				"	   when cl.legacy_id is null then 'No' " + 
				"end as 'legacyLearner' " +
				
				",CASE when child.ofo_code is not null then child.ofo_code " + 
				"	   when parent.ofo_code is not null then parent.ofo_code " + 
				"end as 'ofoCode' " +
				
				",CASE " + 
				"	when cl.learner_status = 0 then 'Application' " + 
				"	when cl.learner_status = 1 then 'Registered' " + 
				"	when cl.learner_status = 2 then 'Transfer Pending Approval' " + 
				"	when cl.learner_status = 3 then 'Terminated' " + 
				"	when cl.learner_status = 4 then 'Pending Termination' " + 
				"	when cl.learner_status = 5 then 'Pending Change Approval' " + 
				"	when cl.learner_status = 6 then 'Completed' " + 
				"	when cl.learner_status = 7 then 'Pending Trade Test' " + 
				"	when cl.learner_status = 8 then 'Pending ARPL Trade Test' " + 
				"	when cl.learner_status = 9 then 'Pending Review Approval' " + 
				"	when cl.learner_status = 10 then 'Qualification Obtained' " + 
				"	when cl.learner_status = 11 then 'Pending non-merSETA Qualification Approval' " + 
				"	when cl.learner_status = 12 then 'Pending Verification of Assesment Approval' " + 
				"	when cl.learner_status = 13 then 'Pending Completion Letter Approval' " + 
				"	when cl.learner_status = 14 then 'Withdrawn' " + 
				"	when cl.learner_status = 15 then 'Rejected' " + 
				"	when cl.learner_status = 16 then 'Achieved' " + 
				"	when cl.learner_status = 17 then 'Pending Lost Time' " + 
				"end as 'learnerStatus' " + 
								
				", CONCAT(ad.address_line_1, ' ',ad.address_line_2,' ',ad.address_line_3,' ',lt.description, ' ',ad.postcode) as 'residentialAddress' " + 
				", CONCAT(pa.address_line_1, ' ',pa.address_line_2,' ',ad.address_line_3,' ',ltp.description, ' ',pa.postcode) as 'postalAddress' " +
				
				
				",(SELECT GROUP_CONCAT(lang.description,' ') " + 
				"		from language lang " + 
				"		where lang.id in  " + 
				"			(select language_id from users_language where user_id = u.id and home_language = true) " + 
				"		) as 'language' " + 
				
				",(SELECT ofo.description " + 
				"	from ofo_codes ofo " + 
				"	where ofo.id = cl.ofo_codes_id) " + 
				" as 'ofoCodeDescription' " + 

				", gen.gender_name as 'gender' " + 
				", u.date_of_birth as 'dateOfBirth' " + 
				", equi.description as 'equity' " + 
				
			
				", nation.description as 'nationality' " +
				", statssa.description as 'startSaAreaCode' " +		
				", cl.date_learner_registered as 'qmrEnteredDate' " + 
				", cl.date_learner_completed as 'qmrCompletedDate' " + 

				"from " + 
				
				"company_learners cl " + 
				"left join users u on u.id = cl.user_id " + 
				"left join gender gen on gen.id = u.gender_id " + 
				"left join address ad on ad.id = u.residential_address_id " + 
				"left join address pa on pa.id = u.postal_address_id " + 				
				"left join municipality m on m.id = ad.municipality_id " +
				"left join towns lt on lt.id = ad.town_id " + 
				"left join towns ltp on ltp.id = pa.town_id " + 
				"left join region_town lrt on lrt.town_id = lt.id " + 
				"left join region lr on lr.id = lrt.region_id " + 
				
				"left join statssa_area_code statssa on statssa.id = ad.stats_saarea_code_id " + 
				
				"left join users cu on cu.id = cl.create_user_id " + 
				"left join company e on e.id = cl.employer_id " + 
				"left join address ea on ea.id = e.residential_address_id " + 
				"left join municipality municipal on municipal.id = ea.municipality_id " + 
				"left join towns et on et.id = ea.town_id " + 
				"left join region_town ert on ert.town_id = et.id " + 
				"left join region er on er.id = ert.region_id " +
				
				"left join sic_code siccode on siccode.id = e.sic_code_id " +
				"left join chamber cham on cham.id = siccode.chamber_id " +
				"left join equity equi on equi.id = u.equity_id " +
				
				"left join ofo_codes child on child.id = cl.ofo_codes_id " +
				"left join ofo_codes parent on parent.id = child.ofo_codes_id " +

				"left join nationality nation on nation.id = u.nationality_id " +

				"left join company c on c.id = cl.company_id " + 
				"left join intervention_type it on it.id = cl.intervention_type_id " + 
				"left join funding f on f.id = cl.dunding_id " + 
				"left join saqa_qualification hq on hq.id = cl.highest_qualification_id " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id " + 
				"left join skills_program sp on sp.id = cl.skills_program_id " + 
				"left join skills_set ss on ss.id = cl.skills_set_id " + 
				"left join learnership ls on ls.id = cl.learnership_id " + 
				"left join saqa_unitstandard us on us.id = cl.unit_standard_id " + 				
				
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id "+
				"left join company pr on pr.id = tpa.company_id " + 
				"left join address a on a.id = pr.residential_address_id " + 
				"left join municipality mun on mun.id = a.municipality_id " + 
				"left join towns t on t.id = a.town_id " + 
				"left join region_town rt on rt.town_id = t.id " + 
				"left join region r on r.id = rt.region_id " +  
				"where cham.id = :chamberID " + 
				"and cl.learner_status = :learnerStatusEnum " + 
				"order by cl.id asc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("chamberID", chamber.getId());
		parameters.put("learnerStatusEnum", learnerStatusEnum.ordinal());
		return (List<CompanyLearnersBean>)super.nativeSelectSqlList(sql, CompanyLearnersBean.class, parameters);
	}
	
	public List<LearnersMentorBean> generateLearnersMentorBeanBy(Company company) throws Exception {
		String sql = "select " + 
				" cl.create_date as 'createDate' " +
				", u.first_name as 'firstName' "  + 
				", u.middle_name as 'middleName' "  + 
				", u.last_name as 'lastName' "  + 
				", u.email as 'learnerEmail' "  + 
				", u.cell_number as 'cellNumber' " + 
				", u.tel_number as 'telNumber' " + 
				", u.rsa_id_number as 'rsaIDNumber' " + 
				", u.passport_number as 'passportNumber' " + 
				", m.municipality_desc as learnerMunicipality " + 
				", lr.description as 'region' " + 
				", er.description as 'employerRegion' " +
				", r.description as 'providerRegion' " +
				", mun.municipality_desc as 'providerMunicipality' " +
				", municipal.municipality_desc as 'employerMunicipality' " +
				", cham.description as 'chamber' " +
				", it.description as 'interventionType' " + 
				
				", sq.qualificationid_string as 'qualificationCode' " + 
				", sq.qualificationtitle as 'qualificationTitle' " + 
				", sp.programme_id as 'skillsProgrammeCode' " + 
				", sp.description as 'skillsProgrammeDescription' " + 
				", ss.programme_id as 'skillsSetCode' " + 
				", ss.description as 'skillsSetDescription' " + 
				", ls.code as 'learnershipCode' " + 
				", ls.description as 'learnershipDescription' " + 
				", us.unitstandardid as 'unitStandardCode' " + 
				", us.unitstdtitle as 'unitStandardDescription' " + 
				", cl.non_credid_bearing_description as 'nonCreditBearingDescription' " + 				
				
				", cl.approval_date as 'approvalDate' " + 
				", cl.date_learner_registered as 'dateLearnerRegistered' " + 
				", cl.registered_date as 'registrationDate' " +
				", cl.commencement_date as 'commencementDate' " + 
				", cl.completion_date as 'completionDate' " +
				
				",CONCAT(hq.qualificationid, ' (', hq.qualificationtitle,')') as 'highestQualification' " + 
				
				", CASE " + 
				"	when cl.highest_education_enum = 0 then 'Below Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 1 then 'Above Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 2 then 'N1' " + 
				"	when cl.highest_education_enum = 3 then 'N2' " + 
				"	when cl.highest_education_enum = 4 then 'N3' " + 
				"	when cl.highest_education_enum = 5 then 'N4' " + 
				"	when cl.highest_education_enum = 6 then 'N5' " + 
				"	when cl.highest_education_enum = 7 then 'N6' " + 
				"	when cl.highest_education_enum = 8 then 'NCV Level 2' " + 
				"	when cl.highest_education_enum = 9 then 'NCV Level 3' " + 
				"	when cl.highest_education_enum = 10 then 'NCV Level 4' " + 
				"	when cl.highest_education_enum = 11 then 'S1' " + 
				"	when cl.highest_education_enum = 12 then 'S2' " + 
				"	when cl.highest_education_enum = 13 then 'S3' " + 
				"end as 'highestEducation' " + 
				
				", CONCAT(e.company_name, ' (', e.levy_number,')') as 'employerInfor' " + 
				", CONCAT(pr.company_name, ' (', pr.levy_number,')' , tpa.accreditation_number) as 'providerInfor' " + 

				", cl.registration_number as 'registrationNumber' " + 	
				
				", CONCAT(cu.first_name, ' ', cu.last_name, ' (', cu.email,')') as 'createUser' " + 
				
				",CASE " + 
				"	when cl.status = 0 then 'Approved' " + 
				"	when cl.status = 1 then 'Rejected' " + 
				"	when cl.status = 2 then 'Pending Manager Approval' " + 
				"	when cl.status = 3 then 'Pending Approval' " + 
				"	when cl.status = 4 then 'Pending Sign Off' " + 
				"	when cl.status = 5 then 'Completed' " + 
				"	when cl.status = 6 then 'Pending accept code of conduct' " + 
				"	when cl.status = 7 then 'Awaiting DHET'   " + 
				"	when cl.status = 8 then 'Pending Final Approval' " + 
				"	when cl.status = 9 then 'Withdrawn'   " + 
				"	when cl.status = 10 then 'N/A' " + 
				"	when cl.status = 11 then 'Recommended' " + 
				"	when cl.status = 12 then 'Appealed' " + 
				"	when cl.status = 13 then 'Pending Committee Approval' " + 
				"	when cl.status = 14 then 'Approved By ETQA Review Committee' " + 
				"	when cl.status = 15 then 'Rejected By ETQA Review Commitee' " + 
				"	when cl.status = 16 then 'Requested Higher Allocation' " + 
				"	when cl.status = 17 then 'Accepted MOA' " + 
				"	when cl.status = 18 then 'Requested Change' " + 
				"	when cl.status = 19 then 'Rejected By MANCO Review' " + 
				"	when cl.status = 20 then 'Rejected By Learner Review Committee' " + 
				"	when cl.status = 21 then 'Qualification Obtained' " + 
				"	when cl.status = 22 then 'Deactivated' " + 
				"	when cl.status = 23 then 'Project Terminated Manager Approval' " + 
				"	when cl.status = 24 then 'Suspend Project' " + 
				"	when cl.status = 25 then 'Project Terminated' " + 
				"	when cl.status = 26 then 'Pending Review Approval' " + 
				"	when cl.status = 27 then 'Uphold' " + 
				"	when cl.status = 28 then 'Pending Resubmission'   " + 
				"	when cl.status = 29 then 'Awaiting NAMB' " + 
				"	when cl.status = 30 then 'Pending Withdrawal' " + 
				"	when cl.status = 31 then 'Pending Inverstigation' " + 
				"	when cl.status = 32 then 'Pending Change Approval' " + 
				"	when cl.status = 33 then 'Not Competent' " + 
				"end as 'approvalStatus' " + 
				
				",CASE when cl.employment_status = 0 then 'Employed' " + 
				"	when cl.employment_status = 1 then 'Unemployed' " + 
				"end as 'employmentStatus' " + 
				
				",CASE when u.urban_rural_enum = 0 then 'Urban' " + 
				"	   when u.urban_rural_enum = 1 then 'Rural' " + 
				"end as 'urbanRural' " + 
				
				",CASE when u.disability = 0 then 'Yes' " + 
				"	   when u.disability = 1 then 'No' " + 
				"end as 'disability' " +
				
				",CASE when cl.merseta_funded = 0 then 'Yes' " + 
				"	   when cl.merseta_funded = 1 then 'No' " + 
				"end as 'fundedBy' " +
				
				",CASE when cl.legacy_id is not null then 'Yes' " + 
				"	   when cl.legacy_id is null then 'No' " + 
				"end as 'legacyLearner' " +
				
				",CASE when child.ofo_code is not null then child.ofo_code " + 
				"	   when parent.ofo_code is not null then parent.ofo_code " + 
				"end as 'ofoCode' " +
				
				",CASE " + 
				"	when cl.learner_status = 0 then 'Application' " + 
				"	when cl.learner_status = 1 then 'Registered' " + 
				"	when cl.learner_status = 2 then 'Transfer Pending Approval' " + 
				"	when cl.learner_status = 3 then 'Terminated' " + 
				"	when cl.learner_status = 4 then 'Pending Termination' " + 
				"	when cl.learner_status = 5 then 'Pending Change Approval' " + 
				"	when cl.learner_status = 6 then 'Completed' " + 
				"	when cl.learner_status = 7 then 'Pending Trade Test' " + 
				"	when cl.learner_status = 8 then 'Pending ARPL Trade Test' " + 
				"	when cl.learner_status = 9 then 'Pending Review Approval' " + 
				"	when cl.learner_status = 10 then 'Qualification Obtained' " + 
				"	when cl.learner_status = 11 then 'Pending non-merSETA Qualification Approval' " + 
				"	when cl.learner_status = 12 then 'Pending Verification of Assesment Approval' " + 
				"	when cl.learner_status = 13 then 'Pending Completion Letter Approval' " + 
				"	when cl.learner_status = 14 then 'Withdrawn' " + 
				"	when cl.learner_status = 15 then 'Rejected' " + 
				"	when cl.learner_status = 16 then 'Achieved' " + 
				"	when cl.learner_status = 17 then 'Pending Lost Time' " + 
				"end as 'learnerStatus' " + 
								
				", CONCAT(ad.address_line_1, ' ',ad.address_line_2,' ',ad.address_line_3,' ',lt.description, ' ',ad.postcode) as 'residentialAddress' " + 
				", CONCAT(pa.address_line_1, ' ',pa.address_line_2,' ',ad.address_line_3,' ',ltp.description, ' ',pa.postcode) as 'postalAddress' " +
				
				
				",(SELECT GROUP_CONCAT(lang.description,' ') " + 
				"		from language lang " + 
				"		where lang.id in  " + 
				"			(select language_id from users_language where user_id = u.id and home_language = true) " + 
				"		) as 'language' " + 
				
				",(SELECT ofo.description " + 
				"	from ofo_codes ofo " + 
				"	where ofo.id = cl.ofo_codes_id) " + 
				" as 'ofoCodeDescription' " + 

				", gen.gender_name as 'gender' " + 
				", u.date_of_birth as 'dateOfBirth' " + 
				", equi.description as 'equity' " + 
				
			
				", nation.description as 'nationality' " +
				", statssa.description as 'startSaAreaCode' " +		
				", cl.date_learner_registered as 'qmrEnteredDate' " + 
				", cl.date_learner_completed as 'qmrCompletedDate' " + 

				"from " + 
				
				"company_learners cl " + 
				"left join users u on u.id = cl.user_id " + 
				"left join gender gen on gen.id = u.gender_id " + 
				"left join address ad on ad.id = u.residential_address_id " + 
				"left join address pa on pa.id = u.postal_address_id " + 				
				"left join municipality m on m.id = ad.municipality_id " +
				"left join towns lt on lt.id = ad.town_id " + 
				"left join towns ltp on ltp.id = pa.town_id " + 
				"left join region_town lrt on lrt.town_id = lt.id " + 
				"left join region lr on lr.id = lrt.region_id " + 
				
				"left join statssa_area_code statssa on statssa.id = ad.stats_saarea_code_id " + 
				
				"left join users cu on cu.id = cl.create_user_id " + 
				"left join company e on e.id = cl.employer_id " + 
				"left join address ea on ea.id = e.residential_address_id " + 
				"left join municipality municipal on municipal.id = ea.municipality_id " + 
				"left join towns et on et.id = ea.town_id " + 
				"left join region_town ert on ert.town_id = et.id " + 
				"left join region er on er.id = ert.region_id " +
				
				"left join sic_code siccode on siccode.id = e.sic_code_id " +
				"left join chamber cham on cham.id = siccode.chamber_id " +
				"left join equity equi on equi.id = u.equity_id " +
				
				"left join ofo_codes child on child.id = cl.ofo_codes_id " +
				"left join ofo_codes parent on parent.id = child.ofo_codes_id " +

				"left join nationality nation on nation.id = u.nationality_id " +

				"left join company c on c.id = cl.company_id " + 
				"left join intervention_type it on it.id = cl.intervention_type_id " + 
				"left join funding f on f.id = cl.dunding_id " + 
				"left join saqa_qualification hq on hq.id = cl.highest_qualification_id " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id " + 
				"left join skills_program sp on sp.id = cl.skills_program_id " + 
				"left join skills_set ss on ss.id = cl.skills_set_id " + 
				"left join learnership ls on ls.id = cl.learnership_id " + 
				"left join saqa_unitstandard us on us.id = cl.unit_standard_id " + 				
				
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id "+
				"left join company pr on pr.id = tpa.company_id " + 
				"left join address a on a.id = pr.residential_address_id " + 
				"left join municipality mun on mun.id = a.municipality_id " + 
				"left join towns t on t.id = a.town_id " + 
				"left join region_town rt on rt.town_id = t.id " + 
				"left join region r on r.id = rt.region_id " + 
				"where cl.intervention_type_id is not null " + 
				"and (cl.company_id = :companyID or  cl.employer_id = :companyID)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("companyID", company.getId());
		return (List<LearnersMentorBean>)super.nativeSelectSqlList(sql, LearnersMentorBean.class, parameters);
	}
	
	public List<LearnersMentorBean> generateLearnersMentorBean() throws Exception {
		String sql = "select " + 
				" cl.create_date as 'createDate' " +
				", u.first_name as 'firstName' "  + 
				", u.middle_name as 'middleName' "  + 
				", u.last_name as 'lastName' "  + 
				", u.email as 'learnerEmail' "  + 
				", u.cell_number as 'cellNumber' " + 
				", u.tel_number as 'telNumber' " + 
				", u.rsa_id_number as 'rsaIDNumber' " + 
				", u.passport_number as 'passportNumber' " + 
				", m.municipality_desc as learnerMunicipality " + 
				", lr.description as 'region' " + 
				", er.description as 'employerRegion' " +
				", r.description as 'providerRegion' " +
				", mun.municipality_desc as 'providerMunicipality' " +
				", municipal.municipality_desc as 'employerMunicipality' " +
				", cham.description as 'chamber' " +
				", it.description as 'interventionType' " + 
				
				", sme.first_name as 'mentorFirstName' "  + 
				", sme.last_name as 'mentorLastName' "  + 
				", sme.identity_number as 'mentorRsaIDNumber' "  + 
				", sme.passport_number as 'mentorPassportNumber' "  + 
				", site.company_name as 'siteName' "  + 

				", sq.qualificationid_string as 'qualificationCode' " + 
				", sq.qualificationtitle as 'qualificationTitle' " + 
				", sp.programme_id as 'skillsProgrammeCode' " + 
				", sp.description as 'skillsProgrammeDescription' " + 
				", ss.programme_id as 'skillsSetCode' " + 
				", ss.description as 'skillsSetDescription' " + 
				", ls.code as 'learnershipCode' " + 
				", ls.description as 'learnershipDescription' " + 
				", us.unitstandardid as 'unitStandardCode' " + 
				", us.unitstdtitle as 'unitStandardDescription' " + 
				", cl.non_credid_bearing_description as 'nonCreditBearingDescription' " + 				
				
				", cl.approval_date as 'approvalDate' " + 
				", cl.date_learner_registered as 'dateLearnerRegistered' " + 
				", cl.registered_date as 'registrationDate' " +
				", cl.commencement_date as 'commencementDate' " + 
				", cl.completion_date as 'completionDate' " +
				
				",CONCAT(hq.qualificationid, ' (', hq.qualificationtitle,')') as 'highestQualification' " + 
				
				", CASE " + 
				"	when cl.highest_education_enum = 0 then 'Below Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 1 then 'Above Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 2 then 'N1' " + 
				"	when cl.highest_education_enum = 3 then 'N2' " + 
				"	when cl.highest_education_enum = 4 then 'N3' " + 
				"	when cl.highest_education_enum = 5 then 'N4' " + 
				"	when cl.highest_education_enum = 6 then 'N5' " + 
				"	when cl.highest_education_enum = 7 then 'N6' " + 
				"	when cl.highest_education_enum = 8 then 'NCV Level 2' " + 
				"	when cl.highest_education_enum = 9 then 'NCV Level 3' " + 
				"	when cl.highest_education_enum = 10 then 'NCV Level 4' " + 
				"	when cl.highest_education_enum = 11 then 'S1' " + 
				"	when cl.highest_education_enum = 12 then 'S2' " + 
				"	when cl.highest_education_enum = 13 then 'S3' " + 
				"end as 'highestEducation' " + 
				
				", CONCAT(e.company_name, ' (', e.levy_number,')') as 'employerInfor' " + 
				", CONCAT(pr.company_name, ' (', pr.levy_number,')' , tpa.accreditation_number) as 'providerInfor' " + 

				", cl.registration_number as 'registrationNumber' " + 	
				
				", CONCAT(cu.first_name, ' ', cu.last_name, ' (', cu.email,')') as 'createUser' " + 
				
				",CASE " + 
				"	when cl.status = 0 then 'Approved' " + 
				"	when cl.status = 1 then 'Rejected' " + 
				"	when cl.status = 2 then 'Pending Manager Approval' " + 
				"	when cl.status = 3 then 'Pending Approval' " + 
				"	when cl.status = 4 then 'Pending Sign Off' " + 
				"	when cl.status = 5 then 'Completed' " + 
				"	when cl.status = 6 then 'Pending accept code of conduct' " + 
				"	when cl.status = 7 then 'Awaiting DHET'   " + 
				"	when cl.status = 8 then 'Pending Final Approval' " + 
				"	when cl.status = 9 then 'Withdrawn'   " + 
				"	when cl.status = 10 then 'N/A' " + 
				"	when cl.status = 11 then 'Recommended' " + 
				"	when cl.status = 12 then 'Appealed' " + 
				"	when cl.status = 13 then 'Pending Committee Approval' " + 
				"	when cl.status = 14 then 'Approved By ETQA Review Committee' " + 
				"	when cl.status = 15 then 'Rejected By ETQA Review Commitee' " + 
				"	when cl.status = 16 then 'Requested Higher Allocation' " + 
				"	when cl.status = 17 then 'Accepted MOA' " + 
				"	when cl.status = 18 then 'Requested Change' " + 
				"	when cl.status = 19 then 'Rejected By MANCO Review' " + 
				"	when cl.status = 20 then 'Rejected By Learner Review Committee' " + 
				"	when cl.status = 21 then 'Qualification Obtained' " + 
				"	when cl.status = 22 then 'Deactivated' " + 
				"	when cl.status = 23 then 'Project Terminated Manager Approval' " + 
				"	when cl.status = 24 then 'Suspend Project' " + 
				"	when cl.status = 25 then 'Project Terminated' " + 
				"	when cl.status = 26 then 'Pending Review Approval' " + 
				"	when cl.status = 27 then 'Uphold' " + 
				"	when cl.status = 28 then 'Pending Resubmission'   " + 
				"	when cl.status = 29 then 'Awaiting NAMB' " + 
				"	when cl.status = 30 then 'Pending Withdrawal' " + 
				"	when cl.status = 31 then 'Pending Inverstigation' " + 
				"	when cl.status = 32 then 'Pending Change Approval' " + 
				"	when cl.status = 33 then 'Not Competent' " + 
				"end as 'approvalStatus' " + 
				
				",CASE when cl.employment_status = 0 then 'Employed' " + 
				"	when cl.employment_status = 1 then 'Unemployed' " + 
				"end as 'employmentStatus' " + 
				
				",CASE when u.urban_rural_enum = 0 then 'Urban' " + 
				"	   when u.urban_rural_enum = 1 then 'Rural' " + 
				"end as 'urbanRural' " + 
				
				",CASE when u.disability = 0 then 'Yes' " + 
				"	   when u.disability = 1 then 'No' " + 
				"end as 'disability' " +
				
				",CASE when cl.merseta_funded = 0 then 'Yes' " + 
				"	   when cl.merseta_funded = 1 then 'No' " + 
				"end as 'fundedBy' " +
				
				",CASE when cl.legacy_id is not null then 'Yes' " + 
				"	   when cl.legacy_id is null then 'No' " + 
				"end as 'legacyLearner' " +
				
				",CASE when child.ofo_code is not null then child.ofo_code " + 
				"	   when parent.ofo_code is not null then parent.ofo_code " + 
				"end as 'ofoCode' " +
				
				",CASE " + 
				"	when cl.learner_status = 0 then 'Application' " + 
				"	when cl.learner_status = 1 then 'Registered' " + 
				"	when cl.learner_status = 2 then 'Transfer Pending Approval' " + 
				"	when cl.learner_status = 3 then 'Terminated' " + 
				"	when cl.learner_status = 4 then 'Pending Termination' " + 
				"	when cl.learner_status = 5 then 'Pending Change Approval' " + 
				"	when cl.learner_status = 6 then 'Completed' " + 
				"	when cl.learner_status = 7 then 'Pending Trade Test' " + 
				"	when cl.learner_status = 8 then 'Pending ARPL Trade Test' " + 
				"	when cl.learner_status = 9 then 'Pending Review Approval' " + 
				"	when cl.learner_status = 10 then 'Qualification Obtained' " + 
				"	when cl.learner_status = 11 then 'Pending non-merSETA Qualification Approval' " + 
				"	when cl.learner_status = 12 then 'Pending Verification of Assesment Approval' " + 
				"	when cl.learner_status = 13 then 'Pending Completion Letter Approval' " + 
				"	when cl.learner_status = 14 then 'Withdrawn' " + 
				"	when cl.learner_status = 15 then 'Rejected' " + 
				"	when cl.learner_status = 16 then 'Achieved' " + 
				"	when cl.learner_status = 17 then 'Pending Lost Time' " + 
				"end as 'learnerStatus' " + 
								
				", CONCAT(ad.address_line_1, ' ',ad.address_line_2,' ',ad.address_line_3,' ',lt.description, ' ',ad.postcode) as 'residentialAddress' " + 
				", CONCAT(pa.address_line_1, ' ',pa.address_line_2,' ',ad.address_line_3,' ',ltp.description, ' ',pa.postcode) as 'postalAddress' " +
				
				
				",(SELECT GROUP_CONCAT(lang.description,' ') " + 
				"		from language lang " + 
				"		where lang.id in  " + 
				"			(select language_id from users_language where user_id = u.id and home_language = true) " + 
				"		) as 'language' " + 
				
				",(SELECT ofo.description " + 
				"	from ofo_codes ofo " + 
				"	where ofo.id = cl.ofo_codes_id) " + 
				" as 'ofoCodeDescription' " + 

				", gen.gender_name as 'gender' " + 
				", u.date_of_birth as 'dateOfBirth' " + 
				", equi.description as 'equity' " + 
				
			
				", nation.description as 'nationality' " +
				", statssa.description as 'startSaAreaCode' " +		
				", cl.date_learner_registered as 'qmrEnteredDate' " + 
				", cl.date_learner_completed as 'qmrCompletedDate' " + 

				"from " + 
				
				"company_learners cl " + 
				"left join users u on u.id = cl.user_id " + 
				"left join gender gen on gen.id = u.gender_id " + 
				"left join address ad on ad.id = u.residential_address_id " + 
				"left join address pa on pa.id = u.postal_address_id " + 				
				"left join municipality m on m.id = ad.municipality_id " +
				"left join towns lt on lt.id = ad.town_id " + 
				"left join towns ltp on ltp.id = pa.town_id " + 
				"left join region_town lrt on lrt.town_id = lt.id " + 
				"left join region lr on lr.id = lrt.region_id " + 
				
				"left join statssa_area_code statssa on statssa.id = ad.stats_saarea_code_id " + 
				
				"left join users cu on cu.id = cl.create_user_id " + 
				"left join company e on e.id = cl.employer_id " + 
				"left join address ea on ea.id = e.residential_address_id " + 
				"left join municipality municipal on municipal.id = ea.municipality_id " + 
				"left join towns et on et.id = ea.town_id " + 
				"left join region_town ert on ert.town_id = et.id " + 
				"left join region er on er.id = ert.region_id " +
				
				"left join sic_code siccode on siccode.id = e.sic_code_id " +
				"left join chamber cham on cham.id = siccode.chamber_id " +
				"left join equity equi on equi.id = u.equity_id " +
				
				"left join ofo_codes child on child.id = cl.ofo_codes_id " +
				"left join ofo_codes parent on parent.id = child.ofo_codes_id " +

				"left join nationality nation on nation.id = u.nationality_id " +

				"left join company c on c.id = cl.company_id " + 
				"left join intervention_type it on it.id = cl.intervention_type_id " + 
				"left join funding f on f.id = cl.dunding_id " + 
				"left join saqa_qualification hq on hq.id = cl.highest_qualification_id " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id " + 
				"left join skills_program sp on sp.id = cl.skills_program_id " + 
				"left join skills_set ss on ss.id = cl.skills_set_id " + 
				"left join learnership ls on ls.id = cl.learnership_id " + 
				"left join saqa_unitstandard us on us.id = cl.unit_standard_id " + 				
				
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id "+
				"left join company pr on pr.id = tpa.company_id " + 
				"left join address a on a.id = pr.residential_address_id " + 
				"left join municipality mun on mun.id = a.municipality_id " + 
				"left join towns t on t.id = a.town_id " + 
				"left join region_town rt on rt.town_id = t.id " + 
				"left join region r on r.id = rt.region_id " + 
				"left join sites_sme sme on sme.id = cl.sites_sme_id " +
				"left join sites site on site.id = cl.site_id " +
				"where cl.intervention_type_id is not null " +
				"and cl.sites_sme_id is not null ";
		return (List<LearnersMentorBean>)super.nativeSelectSqlList(sql, LearnersMentorBean.class);
	}
	
	public List<LearnersMentorBean> generateLearnersMentorBean(Long companyID) throws Exception {
		String sql = "select " + 
				" cl.create_date as 'createDate' " +
				", u.first_name as 'firstName' "  + 
				", u.middle_name as 'middleName' "  + 
				", u.last_name as 'lastName' "  + 
				", u.email as 'learnerEmail' "  + 
				", u.cell_number as 'cellNumber' " + 
				", u.tel_number as 'telNumber' " + 
				", u.rsa_id_number as 'rsaIDNumber' " + 
				", u.passport_number as 'passportNumber' " + 
				", m.municipality_desc as learnerMunicipality " + 
				", lr.description as 'region' " + 
				", er.description as 'employerRegion' " +
				", r.description as 'providerRegion' " +
				", mun.municipality_desc as 'providerMunicipality' " +
				", municipal.municipality_desc as 'employerMunicipality' " +
				", cham.description as 'chamber' " +
				", it.description as 'interventionType' " + 
				
				", sme.first_name as 'mentorFirstName' "  + 
				", sme.last_name as 'mentorLastName' "  + 
				", sme.identity_number as 'mentorRsaIDNumber' "  + 
				", sme.passport_number as 'mentorPassportNumber' "  + 
				", site.company_name as 'siteName' "  + 

				", sq.qualificationid_string as 'qualificationCode' " + 
				", sq.qualificationtitle as 'qualificationTitle' " + 
				", sp.programme_id as 'skillsProgrammeCode' " + 
				", sp.description as 'skillsProgrammeDescription' " + 
				", ss.programme_id as 'skillsSetCode' " + 
				", ss.description as 'skillsSetDescription' " + 
				", ls.code as 'learnershipCode' " + 
				", ls.description as 'learnershipDescription' " + 
				", us.unitstandardid as 'unitStandardCode' " + 
				", us.unitstdtitle as 'unitStandardDescription' " + 
				", cl.non_credid_bearing_description as 'nonCreditBearingDescription' " + 				
				
				", cl.approval_date as 'approvalDate' " + 
				", cl.date_learner_registered as 'dateLearnerRegistered' " + 
				", cl.registered_date as 'registrationDate' " +
				", cl.commencement_date as 'commencementDate' " + 
				", cl.completion_date as 'completionDate' " +
				
				",CONCAT(hq.qualificationid, ' (', hq.qualificationtitle,')') as 'highestQualification' " + 
				
				", CASE " + 
				"	when cl.highest_education_enum = 0 then 'Below Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 1 then 'Above Matric/Standard 10' " + 
				"	when cl.highest_education_enum = 2 then 'N1' " + 
				"	when cl.highest_education_enum = 3 then 'N2' " + 
				"	when cl.highest_education_enum = 4 then 'N3' " + 
				"	when cl.highest_education_enum = 5 then 'N4' " + 
				"	when cl.highest_education_enum = 6 then 'N5' " + 
				"	when cl.highest_education_enum = 7 then 'N6' " + 
				"	when cl.highest_education_enum = 8 then 'NCV Level 2' " + 
				"	when cl.highest_education_enum = 9 then 'NCV Level 3' " + 
				"	when cl.highest_education_enum = 10 then 'NCV Level 4' " + 
				"	when cl.highest_education_enum = 11 then 'S1' " + 
				"	when cl.highest_education_enum = 12 then 'S2' " + 
				"	when cl.highest_education_enum = 13 then 'S3' " + 
				"end as 'highestEducation' " + 
				
				", CONCAT(e.company_name, ' (', e.levy_number,')') as 'employerInfor' " + 
				", CONCAT(pr.company_name, ' (', pr.levy_number,')' , tpa.accreditation_number) as 'providerInfor' " + 

				", cl.registration_number as 'registrationNumber' " + 	
				
				", CONCAT(cu.first_name, ' ', cu.last_name, ' (', cu.email,')') as 'createUser' " + 
				
				",CASE " + 
				"	when cl.status = 0 then 'Approved' " + 
				"	when cl.status = 1 then 'Rejected' " + 
				"	when cl.status = 2 then 'Pending Manager Approval' " + 
				"	when cl.status = 3 then 'Pending Approval' " + 
				"	when cl.status = 4 then 'Pending Sign Off' " + 
				"	when cl.status = 5 then 'Completed' " + 
				"	when cl.status = 6 then 'Pending accept code of conduct' " + 
				"	when cl.status = 7 then 'Awaiting DHET'   " + 
				"	when cl.status = 8 then 'Pending Final Approval' " + 
				"	when cl.status = 9 then 'Withdrawn'   " + 
				"	when cl.status = 10 then 'N/A' " + 
				"	when cl.status = 11 then 'Recommended' " + 
				"	when cl.status = 12 then 'Appealed' " + 
				"	when cl.status = 13 then 'Pending Committee Approval' " + 
				"	when cl.status = 14 then 'Approved By ETQA Review Committee' " + 
				"	when cl.status = 15 then 'Rejected By ETQA Review Commitee' " + 
				"	when cl.status = 16 then 'Requested Higher Allocation' " + 
				"	when cl.status = 17 then 'Accepted MOA' " + 
				"	when cl.status = 18 then 'Requested Change' " + 
				"	when cl.status = 19 then 'Rejected By MANCO Review' " + 
				"	when cl.status = 20 then 'Rejected By Learner Review Committee' " + 
				"	when cl.status = 21 then 'Qualification Obtained' " + 
				"	when cl.status = 22 then 'Deactivated' " + 
				"	when cl.status = 23 then 'Project Terminated Manager Approval' " + 
				"	when cl.status = 24 then 'Suspend Project' " + 
				"	when cl.status = 25 then 'Project Terminated' " + 
				"	when cl.status = 26 then 'Pending Review Approval' " + 
				"	when cl.status = 27 then 'Uphold' " + 
				"	when cl.status = 28 then 'Pending Resubmission'   " + 
				"	when cl.status = 29 then 'Awaiting NAMB' " + 
				"	when cl.status = 30 then 'Pending Withdrawal' " + 
				"	when cl.status = 31 then 'Pending Inverstigation' " + 
				"	when cl.status = 32 then 'Pending Change Approval' " + 
				"	when cl.status = 33 then 'Not Competent' " + 
				"end as 'approvalStatus' " + 
				
				",CASE when cl.employment_status = 0 then 'Employed' " + 
				"	when cl.employment_status = 1 then 'Unemployed' " + 
				"end as 'employmentStatus' " + 
				
				",CASE when u.urban_rural_enum = 0 then 'Urban' " + 
				"	   when u.urban_rural_enum = 1 then 'Rural' " + 
				"end as 'urbanRural' " + 
				
				",CASE when u.disability = 0 then 'Yes' " + 
				"	   when u.disability = 1 then 'No' " + 
				"end as 'disability' " +
				
				",CASE when cl.merseta_funded = 0 then 'Yes' " + 
				"	   when cl.merseta_funded = 1 then 'No' " + 
				"end as 'fundedBy' " +
				
				",CASE when cl.legacy_id is not null then 'Yes' " + 
				"	   when cl.legacy_id is null then 'No' " + 
				"end as 'legacyLearner' " +
				
				",CASE when child.ofo_code is not null then child.ofo_code " + 
				"	   when parent.ofo_code is not null then parent.ofo_code " + 
				"end as 'ofoCode' " +
				
				",CASE " + 
				"	when cl.learner_status = 0 then 'Application' " + 
				"	when cl.learner_status = 1 then 'Registered' " + 
				"	when cl.learner_status = 2 then 'Transfer Pending Approval' " + 
				"	when cl.learner_status = 3 then 'Terminated' " + 
				"	when cl.learner_status = 4 then 'Pending Termination' " + 
				"	when cl.learner_status = 5 then 'Pending Change Approval' " + 
				"	when cl.learner_status = 6 then 'Completed' " + 
				"	when cl.learner_status = 7 then 'Pending Trade Test' " + 
				"	when cl.learner_status = 8 then 'Pending ARPL Trade Test' " + 
				"	when cl.learner_status = 9 then 'Pending Review Approval' " + 
				"	when cl.learner_status = 10 then 'Qualification Obtained' " + 
				"	when cl.learner_status = 11 then 'Pending non-merSETA Qualification Approval' " + 
				"	when cl.learner_status = 12 then 'Pending Verification of Assesment Approval' " + 
				"	when cl.learner_status = 13 then 'Pending Completion Letter Approval' " + 
				"	when cl.learner_status = 14 then 'Withdrawn' " + 
				"	when cl.learner_status = 15 then 'Rejected' " + 
				"	when cl.learner_status = 16 then 'Achieved' " + 
				"	when cl.learner_status = 17 then 'Pending Lost Time' " + 
				"end as 'learnerStatus' " + 
								
				", CONCAT(ad.address_line_1, ' ',ad.address_line_2,' ',ad.address_line_3,' ',lt.description, ' ',ad.postcode) as 'residentialAddress' " + 
				", CONCAT(pa.address_line_1, ' ',pa.address_line_2,' ',ad.address_line_3,' ',ltp.description, ' ',pa.postcode) as 'postalAddress' " +
				
				
				",(SELECT GROUP_CONCAT(lang.description,' ') " + 
				"		from language lang " + 
				"		where lang.id in  " + 
				"			(select language_id from users_language where user_id = u.id and home_language = true) " + 
				"		) as 'language' " + 
				
				",(SELECT ofo.description " + 
				"	from ofo_codes ofo " + 
				"	where ofo.id = cl.ofo_codes_id) " + 
				" as 'ofoCodeDescription' " + 

				", gen.gender_name as 'gender' " + 
				", u.date_of_birth as 'dateOfBirth' " + 
				", equi.description as 'equity' " + 
				
			
				", nation.description as 'nationality' " +
				", statssa.description as 'startSaAreaCode' " +		
				", cl.date_learner_registered as 'qmrEnteredDate' " + 
				", cl.date_learner_completed as 'qmrCompletedDate' " + 

				"from " + 
				
				"company_learners cl " + 
				"left join users u on u.id = cl.user_id " + 
				"left join gender gen on gen.id = u.gender_id " + 
				"left join address ad on ad.id = u.residential_address_id " + 
				"left join address pa on pa.id = u.postal_address_id " + 				
				"left join municipality m on m.id = ad.municipality_id " +
				"left join towns lt on lt.id = ad.town_id " + 
				"left join towns ltp on ltp.id = pa.town_id " + 
				"left join region_town lrt on lrt.town_id = lt.id " + 
				"left join region lr on lr.id = lrt.region_id " + 
				
				"left join statssa_area_code statssa on statssa.id = ad.stats_saarea_code_id " + 
				
				"left join users cu on cu.id = cl.create_user_id " + 
				"left join company e on e.id = cl.employer_id " + 
				"left join address ea on ea.id = e.residential_address_id " + 
				"left join municipality municipal on municipal.id = ea.municipality_id " + 
				"left join towns et on et.id = ea.town_id " + 
				"left join region_town ert on ert.town_id = et.id " + 
				"left join region er on er.id = ert.region_id " +
				
				"left join sic_code siccode on siccode.id = e.sic_code_id " +
				"left join chamber cham on cham.id = siccode.chamber_id " +
				"left join equity equi on equi.id = u.equity_id " +
				
				"left join ofo_codes child on child.id = cl.ofo_codes_id " +
				"left join ofo_codes parent on parent.id = child.ofo_codes_id " +

				"left join nationality nation on nation.id = u.nationality_id " +

				"left join company c on c.id = cl.company_id " + 
				"left join intervention_type it on it.id = cl.intervention_type_id " + 
				"left join funding f on f.id = cl.dunding_id " + 
				"left join saqa_qualification hq on hq.id = cl.highest_qualification_id " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id " + 
				"left join skills_program sp on sp.id = cl.skills_program_id " + 
				"left join skills_set ss on ss.id = cl.skills_set_id " + 
				"left join learnership ls on ls.id = cl.learnership_id " + 
				"left join saqa_unitstandard us on us.id = cl.unit_standard_id " + 				
				
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id "+
				"left join company pr on pr.id = tpa.company_id " + 
				"left join address a on a.id = pr.residential_address_id " + 
				"left join municipality mun on mun.id = a.municipality_id " + 
				"left join towns t on t.id = a.town_id " + 
				"left join region_town rt on rt.town_id = t.id " + 
				"left join region r on r.id = rt.region_id " + 
				"left join sites_sme sme on sme.id = cl.sites_sme_id " +
				"left join sites site on site.id = cl.site_id " +
				"where cl.intervention_type_id is not null " +
				"and cl.sites_sme_id is not null " +
				"and cl.employer_id = :companyID ";
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("companyID", companyID);
		return (List<LearnersMentorBean>)super.nativeSelectSqlList(sql, LearnersMentorBean.class, parameters);
	}
}