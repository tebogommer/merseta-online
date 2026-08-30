package haj.com.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import haj.com.entity.*;
import haj.com.entity.enums.UsersStatusEnum;
import org.primefaces.model.SortOrder;

import haj.com.bean.LearnersMentorBean;
import haj.com.bean.WorkplaceApprovalBean;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class WorkPlaceApprovalDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WorkPlaceApproval
	 * 
	 * @author TechFinium
	 * @see WorkPlaceApproval
	 * @return a list of {@link haj.com.entity.WorkPlaceApproval}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApproval> allWorkPlaceApproval() throws Exception {
		return (List<WorkPlaceApproval>) super.getList("select o from WorkPlaceApproval o");
	}

	/**
	 * Get all WorkPlaceApproval between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see WorkPlaceApproval
	 * @return a list of {@link haj.com.entity.WorkPlaceApproval}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApproval> allWorkPlaceApproval(Long from, int noRows) throws Exception {
		String hql = "select o from WorkPlaceApproval o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<WorkPlaceApproval>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see WorkPlaceApproval
	 * @return a {@link haj.com.entity.WorkPlaceApproval}
	 * @throws Exception
	 *             global exception
	 */
	public WorkPlaceApproval findByKey(Long id) throws Exception {
		String hql = "select o from WorkPlaceApproval o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (WorkPlaceApproval) super.getUniqueResult(hql, parameters);
	}

	public WorkPlaceApproval findByDiscretionaryGrant(Long id) throws Exception {
		String hql = "select o from WorkPlaceApproval o where o.mandatoryGrant.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (WorkPlaceApproval) super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<WorkPlaceApproval> findByCompanyandQualification(Company company, Qualification qualification)
			throws Exception {
		String hql = "select o from WorkPlaceApproval o where o.company.id = :companyID and o.qualification.id = :qualificationID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("qualificationID", qualification.getId());
		return (List<WorkPlaceApproval>) super.getList(hql, parameters);
	}
	
	public WorkPlaceApproval findWorkPlaceApprovalByCompanyandQualification(Company company, Qualification qualification) throws Exception {
		String hql = "select o from WorkPlaceApproval o where o.company.id = :companyID and o.qualification.id = :qualificationID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("qualificationID", qualification.getId());
		return (WorkPlaceApproval) super.getUniqueResult(hql, parameters);
	}
	
	public WorkPlaceApproval findApprovedWorkPlaceApprovalByCompanyAndQualificationAndSites(Company company, Qualification qualification,  ApprovalEnum approvalEnum, Long sitesId) throws Exception {
		String hql = "select o from WorkPlaceApproval o where o.company.id = :companyID and o.qualification.id = :qualificationID and o.approvalEnum = :approvalEnum and o.sites.id = :sitesId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("qualificationID", qualification.getId());
		parameters.put("approvalEnum", approvalEnum);
		parameters.put("sitesId", sitesId);
		return (WorkPlaceApproval) super.getUniqueResult(hql, parameters);
	}
	
	public WorkPlaceApproval findApprovedWorkPlaceApprovalByCompanyAndQualification(Company company, Qualification qualification,  ApprovalEnum approvalEnum) throws Exception {
		String hql = "select o from WorkPlaceApproval o where o.company.id = :companyID and o.qualification.id = :qualificationID and o.approvalEnum = :approvalEnum and o.sites is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("qualificationID", qualification.getId());
		parameters.put("approvalEnum", approvalEnum);
		return (WorkPlaceApproval) super.getUniqueResult(hql, parameters);
	}
	
	public WorkPlaceApproval findApprovedWorkPlaceApprovalByCompanyAndQualificationAndSites(Company company, Qualification qualification,  Long sitesId) throws Exception {
		String hql = "select o from WorkPlaceApproval o where o.company.id = :companyID and o.qualification.id = :qualificationID and o.sites.id = :sitesId  and o.approvalEnum <> :statusRejected and o.approvalEnum <> :statusWithdrawn";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("qualificationID", qualification.getId());
		parameters.put("sitesId", sitesId);
		parameters.put("statusRejected", ApprovalEnum.Rejected);
		parameters.put("statusWithdrawn", ApprovalEnum.Withdrawn);
		return (WorkPlaceApproval) super.getUniqueResult(hql, parameters);
	}
	
	public WorkPlaceApproval findApprovedWorkPlaceApprovalByCompanyAndQualification(Company company, Qualification qualification) throws Exception {
		String hql = "select o from WorkPlaceApproval o where o.company.id = :companyID and o.qualification.id = :qualificationID and o.sites is null  and o.approvalEnum <> :statusRejected and o.approvalEnum <> :statusWithdrawn";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("qualificationID", qualification.getId());
		parameters.put("statusRejected", ApprovalEnum.Rejected);
		parameters.put("statusWithdrawn", ApprovalEnum.Withdrawn);
		return (WorkPlaceApproval) super.getUniqueResult(hql, parameters);
	}
	
	public WorkPlaceApproval findWorkPlaceApprovalByCompanyandOFOCode(Company company, OfoCodes ofoCodes) throws Exception {
		String hql = "select o from WorkPlaceApproval o where o.company.id = :companyID and o.ofoCodes.id = :ofoCodesID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("ofoCodesID", ofoCodes.getId());
		return (WorkPlaceApproval) super.getUniqueResult(hql, parameters);
	}
	

	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalSites> findSitesByApproval(WorkPlaceApproval workPlaceApproval) throws Exception {
		String hql = "select o from WorkPlaceApprovalSites o where o.workPlaceApproval.id = :workPlaceApprovalID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("workPlaceApprovalID", workPlaceApproval.getId());
		return (List<WorkPlaceApprovalSites>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalSites> findSitesByApproval(WorkPlaceApproval workPlaceApproval, ApprovalEnum approvalEnum) throws Exception {
		String hql = "select o from WorkPlaceApprovalSites o where o.workPlaceApproval.id = :workPlaceApprovalID and o.approvalEnum = :approvalEnum";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("workPlaceApprovalID", workPlaceApproval.getId());
		parameters.put("approvalEnum", approvalEnum);
		return (List<WorkPlaceApprovalSites>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalSites> findSitesByNotApproval(WorkPlaceApproval workPlaceApproval, ApprovalEnum approvalEnum) throws Exception {
		String hql = "select o from WorkPlaceApprovalSites o where o.workPlaceApproval.id = :workPlaceApprovalID and o.approvalEnum <> :approvalEnum";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("workPlaceApprovalID", workPlaceApproval.getId());
		parameters.put("approvalEnum", approvalEnum);
		return (List<WorkPlaceApprovalSites>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalSites> findSitesByWorkPlaceApprovalMentors(WorkPlaceApprovalMentors workPlaceApprovalMentor) throws Exception {
		String hql = "select o from WorkPlaceApprovalSites o where o.workPlaceApprovalMentor.id = :workPlaceApprovalMentorID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("workPlaceApprovalMentorID", workPlaceApprovalMentor.getId());
		return (List<WorkPlaceApprovalSites>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalTrades> findTradesByApproval(WorkPlaceApproval workPlaceApproval) throws Exception {
		String hql = "select o from WorkPlaceApprovalTrades o where o.workPlaceApproval.id = :workPlaceApprovalID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("workPlaceApprovalID", workPlaceApproval.getId());
		return (List<WorkPlaceApprovalTrades>) super.getList(hql, parameters);
	}

	/**
	 * Counts WorkPlaceApprovalTrades by work place approval, ofo code passed
	 * and ignores WorkPlaceApprovalTrades ID passed if applicable
	 * 
	 * @param workPlaceApproval
	 * @param ofoCodeId
	 * @param workPlaceApprovalTradesId
	 * @return Integer the count
	 * @throws Exception
	 */
	public Integer countSiteByApprovalQualificationRsaIdAndId(WorkPlaceApproval workPlaceApproval,
			Long qualificationsId, String rsaId, Long workPlaceApprovalSitesId) throws Exception {
		String hql = "select count(o) from WorkPlaceApprovalSites o where o.workPlaceApproval.id = :workPlaceApprovalID and o.qualification.id = :qualificationsId and o.identityNumber = :rsaId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("workPlaceApprovalID", workPlaceApproval.getId());
		parameters.put("qualificationsId", qualificationsId);
		parameters.put("rsaId", rsaId.trim());
		if (workPlaceApprovalSitesId != null) {
			hql += " and o.id <> :workPlaceApprovalSitesId";
			parameters.put("workPlaceApprovalSitesId", workPlaceApprovalSitesId);
		}
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	/**
	 * Counts WorkPlaceApprovalTrades by work place approval, ofo code passed
	 * and ignores WorkPlaceApprovalTrades ID passed if applicable
	 * 
	 * @param workPlaceApproval
	 * @param ofoCodeId
	 * @param workPlaceApprovalTradesId
	 * @return Integer the count
	 * @throws Exception
	 */
	public Integer countTradesByApprovalOfoAndId(WorkPlaceApproval workPlaceApproval, Long ofoCodeId,
			Long workPlaceApprovalTradesId) throws Exception {
		String hql = "select count(o) from WorkPlaceApprovalTrades o where o.workPlaceApproval.id = :workPlaceApprovalID and o.ofoCodes.id = :ofoCodeId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("workPlaceApprovalID", workPlaceApproval.getId());
		parameters.put("ofoCodeId", ofoCodeId);
		if (workPlaceApprovalTradesId != null) {
			hql += " and o.id <> :workPlaceApprovalTradesId";
			parameters.put("workPlaceApprovalTradesId", workPlaceApprovalTradesId);
		}
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	public long findByCompanyandQualificationCount(Company company, Qualification qualification) throws Exception {
		String hql = "select count(o) from WorkPlaceApproval o where o.company.id = :companyID and o.qualification.id = :qualificationID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("qualificationID", qualification.getId());
		return (long) super.getUniqueResult(hql, parameters);
	}

	public long findByCompanyandOfoCodeCount(Company company, OfoCodes ofoCodes) throws Exception {
		String hql = "select count(o) from WorkPlaceApproval o where o.company.id = :companyID and o.ofoCodes.id = :ofoCodesID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("ofoCodesID", ofoCodes.getId());
		return (long) super.getUniqueResult(hql, parameters);
	}

	public long findByCompanySiteandOfoCodeCount(Company company, OfoCodes ofoCodes, Long siteId) throws Exception {
		String hql = "select count(o) from WorkPlaceApproval o where o.company.id = :companyID and o.ofoCodes.id = :ofoCodesID and o.sites.id = :siteId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("ofoCodesID", ofoCodes.getId());
		parameters.put("siteId", siteId);
		return (long) super.getUniqueResult(hql, parameters);
	}

	public long findByCompanySiteandQualificationCount(Company company, Qualification qualification, Long siteId)
			throws Exception {
		String hql = "select count(o) from WorkPlaceApproval o where o.company.id = :companyID and o.qualification.id = :qualificationID and o.sites.id = :siteId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("qualificationID", qualification.getId());
		parameters.put("siteId", siteId);
		return (long) super.getUniqueResult(hql, parameters);
	}

	public long findByCompanyQualificationAndNotRejectedCount(Company company, Qualification qualification)
			throws Exception {
		String hql = "select count(o) from WorkPlaceApproval o where o.company.id = :companyID and o.qualification.id = :qualificationID and o.sites is null and o.approvalEnum <> :statusRejected"
				+ " and o.approvalEnum <> :statusWithdrawn";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("qualificationID", qualification.getId());
		parameters.put("statusRejected", ApprovalEnum.Rejected);
		parameters.put("statusWithdrawn", ApprovalEnum.Withdrawn);
		return (long) super.getUniqueResult(hql, parameters);
	}

	public long findByCompanyOfoCodeAndNotRejectedCount(Company company, OfoCodes ofoCodes) throws Exception {
		String hql = "select count(o) from WorkPlaceApproval o where o.company.id = :companyID and o.ofoCodes.id = :ofoCodesID and o.sites is null and o.approvalEnum <> :statusRejected"
				+ " and o.approvalEnum <> :statusWithdrawn";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("ofoCodesID", ofoCodes.getId());
		parameters.put("statusRejected", ApprovalEnum.Rejected);
		parameters.put("statusWithdrawn", ApprovalEnum.Withdrawn);
		return (long) super.getUniqueResult(hql, parameters);
	}

	public long findByCompanySiteOfoCodeAndNotRejectedCount(Company company, OfoCodes ofoCodes, Long siteId)
			throws Exception {
		String hql = "select count(o) from WorkPlaceApproval o where o.company.id = :companyID and o.ofoCodes.id = :ofoCodesID and o.sites.id = :siteId and o.approvalEnum <> :statusRejected"
				+ " and o.approvalEnum <> :statusWithdrawn";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("ofoCodesID", ofoCodes.getId());
		parameters.put("siteId", siteId);
		parameters.put("statusRejected", ApprovalEnum.Rejected);
		parameters.put("statusWithdrawn", ApprovalEnum.Withdrawn);
		return (long) super.getUniqueResult(hql, parameters);
	}

	public long findByCompanySiteQualificationAndNotRejectedCount(Company company, Qualification qualification,
			Long siteId) throws Exception {
		String hql = "select count(o) from WorkPlaceApproval o where o.company.id = :companyID and o.qualification.id = :qualificationID and o.sites.id = :siteId and o.approvalEnum <> :statusRejected"
				+ " and o.approvalEnum <> :statusWithdrawn";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("qualificationID", qualification.getId());
		parameters.put("siteId", siteId);
		parameters.put("statusRejected", ApprovalEnum.Rejected);
		parameters.put("statusWithdrawn", ApprovalEnum.Withdrawn);
		return (long) super.getUniqueResult(hql, parameters);
	}

	public long findByCompanyCount(Company company) throws Exception {
		String hql = "select count(o) from WorkPlaceApproval o where o.company.id = :companyID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		return (long) super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<WorkPlaceApproval> findByCompany(Company company) throws Exception {
		String hql = "select o from WorkPlaceApproval o where o.company.id = :companyID order by o.approvalDate asc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		return (List<WorkPlaceApproval>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApproval> findApprovedByCompany(Company company) throws Exception {
		String hql = "select o from WorkPlaceApproval o where o.workPlaceApprovalNumber is not null and o.company.id = :companyID order by o.approvalDate asc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		return (List<WorkPlaceApproval>) super.getList(hql, parameters);
	}

	/**
	 * Find WorkPlaceApproval by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see WorkPlaceApproval
	 * @return a list of {@link haj.com.entity.WorkPlaceApproval}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApproval> findByName(String description) throws Exception {
		String hql = "select o from WorkPlaceApproval o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<WorkPlaceApproval>) super.getList(hql, parameters);
	}

	/**
	 * Locates WorkPlaceApprovalVistDateLog by WorkplaceApproval id orders by
	 * create date desc
	 * 
	 * @param workPlaceApproval
	 * @see WorkPlaceApprovalVisitDateLog
	 * @return List<WorkPlaceApprovalVistDateLog>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalVisitDateLog> findVisitDateLogByWorkplaceApproval(WorkPlaceApproval workPlaceApproval)
			throws Exception {
		String hql = "select o from WorkPlaceApprovalVisitDateLog o where o.workPlaceApproval.id = :workPlaceApprovalID order by o.createDate desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("workPlaceApprovalID", workPlaceApproval.getId());
		return (List<WorkPlaceApprovalVisitDateLog>) super.getList(hql, parameters);
	}

	/** Reporting Start */

	/**
	 * Results count of results based on provided search criteria
	 * 
	 * @param dateFilter
	 * @param fromDate
	 * @param toDate
	 * @param filterByStatus
	 * @param statusSelectedList
	 * @return int the count of results
	 * @throws Exception
	 */
	public int checkForResultsForFilterReport(boolean dateFilter, boolean createApproveDatefilter, Date fromDate,
			Date toDate, boolean filterByStatus, List<ApprovalEnum> statusSelectedList,
			boolean filterTradeQualification, List<Qualification> selectedQualifications,
			List<OfoCodes> selectedOfoCodes, boolean filterByProvince, List<Province> provinceList) throws Exception {
		
		String hql = "select count(o) from WorkPlaceApproval o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		
		if (filterByProvince) {
			hql += " left join o.company c left join c.residentialAddress cr left join cr.municipality crm left join crm.province crmp ";
			hql += " left join o.sites s left join s.registeredAddress sr left join sr.municipality srm left join srm.province srmp ";
		}

		// date filter check
		if (dateFilter) {
			if (createApproveDatefilter) {
				hql += " where date(o.createDate) between date(:fromDate) and date(:toDate) ";
			} else {
				hql += " where date(o.approvalDate) between date(:fromDate) and date(:toDate) ";
			}
			parameters.put("fromDate", fromDate);
			parameters.put("toDate", toDate);
		}

		// status filter check
		if (filterByStatus) {
			if (dateFilter) {
				hql += " and ";
			} else {
				hql += " where ";
			}
			hql += " ( ";
			int count = 1;
			for (ApprovalEnum approvalEnum : statusSelectedList) {
				if (count == statusSelectedList.size()) {
					hql += " o.approvalEnum = :selectedStatus" + count + " ";
				} else {
					hql += " o.approvalEnum = :selectedStatus" + count + " or ";
				}
				parameters.put("selectedStatus" + count, approvalEnum);
				count++;
			}
			hql += " ) ";
		}

		// filter by trade / qualification
		if (filterTradeQualification) {
			if (dateFilter || filterByStatus) {
				hql += " and ";
			} else {
				hql += " where ";
			}
			hql += " ( ";
			int count = 1;
			for (Qualification qualificationSelected : selectedQualifications) {
				if (count == selectedQualifications.size()) {
					hql += " o.qualification.id = :qualificationId" + count + " ";
				} else {
					hql += " o.qualification.id = :qualificationId" + count + " or ";
				}
				parameters.put("qualificationId" + count, qualificationSelected.getId());
				count++;
			}
			if (selectedQualifications.size() != 0 && selectedOfoCodes.size() != 0) {
				hql += " or ";
			}
			count = 1;
			for (OfoCodes ofoCodes : selectedOfoCodes) {
				if (count == selectedOfoCodes.size()) {
					hql += " o.ofoCodes.id = :ofoCodesId" + count + " ";
				} else {
					hql += " o.ofoCodes.id = :ofoCodesId" + count + " or ";
				}
				parameters.put("ofoCodesId" + count, ofoCodes.getId());
				count++;
			}
			hql += " ) ";
		}
		
		// filter by province
		if (filterByProvince) {
			if (dateFilter || filterByStatus || filterTradeQualification) {
				hql += " and ";
			} else {
				hql += " where ";
			}
			hql += " ( ";
			int count = 1;
			String provinces = "(";
			for (Province provinceSelected : provinceList) {
				if (count == provinceList.size()) {
					provinces += ":provinceId" + count + ")";
				} else {
					provinces += ":provinceId" + count + ",";
				}
				parameters.put("provinceId" + count, provinceSelected.getId());
				count++;
			}
			hql += "CASE WHEN o.sites IS NULL THEN crmp.id ELSE srmp.id END in " + provinces;
//					hql += "COALESCE(o.sites.registeredAddress.municipality.province.id,o.company.residentialAddress.municipality.province.id ) in " + provinces;
//					hql += " (o.sites is not null and o.sites.registeredAddress.municipality.province.id in " + provinces + " )";
//					hql += " (o.sites is null and o.company.residentialAddress.municipality.province.id in " + provinces + " )";
			hql += " ) ";
		}
		
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<WorkPlaceApproval> testProvince(List<Province> provinceList) throws Exception {
		String hql = "select o from WorkPlaceApproval o where ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
			hql += " ( ";
			int count = 1;
			String provinces = "(";
			for (Province provinceSelected : provinceList) {
				if (count == provinceList.size()) {
					provinces += ":provinceId" + count + ")";
				} else {
					provinces += ":provinceId" + count + ",";
				}
				parameters.put("provinceId" + count, provinceSelected.getId());
				count++;
			}
			hql += "CASE WHEN o.sites IS NULL THEN o.company.residentialAddress.municipality.province.id ELSE o.sites.registeredAddress.municipality.province.id END in "+ provinces;
//					hql += "COALESCE(o.sites.registeredAddress.municipality.province.id, o.company.residentialAddress.municipality.province.id ) in " + provinces;
//					hql += " (o.sites is not null and o.sites.registeredAddress.municipality.province.id in " + provinces + " )";
//					hql += " or ";
//					hql += " (o.sites is null and o.company.residentialAddress.municipality.province.id in " + provinces + " )";
			hql += " ) ";
		return(List<WorkPlaceApproval>)super.getList(hql,parameters);
}

/** Reporting End */
	
	
	public List<?> sortAndFilterWhereWPAInfo(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String hql) {
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
					else if (entry.getKey().equals("code"))
					{
						hql += " and (o.qualification.codeString like " + " :" + hvar + " or o.qualification.description like : "+hvar+") ";
					}
					else if (entry.getKey().equals("ofoCodeParent"))
					{
						hql += " and (o.ofoCodes.ofoCodeParent like " + " :" + hvar + " or o.ofoCodes.description like : "+hvar+") ";
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
					if (sortField.equals("companyName") || sortField.equals("tradingName") || sortField.equals("levyNumber"))
					{
						hql += " order by o.company." + sortField + " asc ";
					}
					else if (sortField.equals("code"))
					{
						hql += " order by o.qualification.codeString asc ";
					}
					else if (sortField.equals("ofoCodeParent"))
					{
						hql += " order by o.ofoCodes." + sortField + " asc ";
					}
					else{
						hql += " order by o." + sortField + " asc ";
					}
					
					break;
				case DESCENDING:
					if (sortField.equals("companyName") || sortField.equals("tradingName") || sortField.equals("levyNumber"))
					{
						hql += " order by o.company." + sortField + " desc ";
					}
					else if (sortField.equals("code"))
					{
						hql += " order by o.qualification.codeString desc ";
					}
					else if (sortField.equals("ofoCodeParent"))
					{
						hql += " order by o.ofoCodes." + sortField + " desc ";
					}
					else{
						hql += " order by o." + sortField + " desc ";
					}
					break;
				default:
					break;
			}
		}
		
		//hql +=" group by user_id ";
		return getList(hql, filters, startingAt, pageSize);
	}
	
	/**
	 * Count.
	 * @param filters
	 *            the filters
	 *
	 * @return the int
	 */
	public int countWhereWPAInfo(Map<String, Object> filters, String hql) {
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
					
					if (entry.getKey().equals("companyName") || entry.getKey().equals("tradingName") || entry.getKey().equals("levyNumber")) {
						hql += " and o.company." + entry.getKey() + " like " + " :" + hvar;
					}
					else if (entry.getKey().equals("code"))
					{
						hql += " and (o.qualification.codeString like " + " :" + hvar + " or o.qualification.description like : "+hvar+") ";
					}
					else if (entry.getKey().equals("ofoCodeParent"))
					{
						hql += " and (o.ofoCodes.ofoCodeParent like " + " :" + hvar + " or o.ofoCodes.description like : "+hvar+") ";
					}
					else {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}
					
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}

	public int countAllCompanyWpaWhereWpaNumberApllied() {
		return ((Long) super.getUniqueResult("select count(o) from WorkPlaceApproval o where o.workPlaceApprovalNumber <> null")).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<WorkPlaceApproval> findByStartAndEndDate(Date start) {
		String hql = "select o from WorkPlaceApproval o where o.reviewDate >= :start" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("start", start);
	   // parameters.put("end", end);
		return (List<WorkPlaceApproval>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<WorkPlaceApproval> findByStartAndEndDate(Date start, Long userId) {
		String hql = "select o from WorkPlaceApproval o where date(o.reviewDate) >= date(:start) and o.reviewUser.id = :userId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("start", start);
	    parameters.put("userId", userId);
		return (List<WorkPlaceApproval>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SitesSme> findSiteSmeByApproval(WorkPlaceApproval workPlaceApproval) throws Exception {
		String hql = "select o.sitesSme from WorkPlaceApprovalSites o where o.workPlaceApproval.id = :workPlaceApprovalID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("workPlaceApprovalID", workPlaceApproval.getId());
		return (List<SitesSme>) super.getList(hql, parameters);
	}

	public WorkPlaceApproval findWpaBycompanyQualificationSite(Company company, Qualification qualification, Sites site) {
		String hql = "select * from WorkPlaceApproval o where o.company.id = :companyID and o.ofoCodes.id = :ofoCodesID and o.sites.id = :siteId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("ofoCodesID", qualification.getId());
		parameters.put("siteId", site.getId());
		return (WorkPlaceApproval)super.getUniqueResult(hql, parameters);
	}

	public WorkPlaceApproval findWpaByCompanySiteOfoCode(Company company, OfoCodes ofoCodes, Sites site) {
		String hql = "select * from WorkPlaceApproval o where o.company.id = :companyID and o.ofoCodes.id = :ofoCodesID and o.sites.id = :siteId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("ofoCodesID", ofoCodes.getId());
		parameters.put("siteId", site.getId());
		return (WorkPlaceApproval)super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalSites> findByMentor(SitesSme sitesSme) {
		String hql = "select o from WorkPlaceApprovalSites o where o.sitesSme.id = :sitesSmeID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("sitesSmeID", sitesSme.getId());
		return (List<WorkPlaceApprovalSites>) super.getList(hql, parameters);
	}
	
	public int countWorkPlaceApprovalByCloCrmAssigned(Long userID) throws Exception {
		String hql = "select count(o) from WorkPlaceApproval o where o.company.nonSetaCompany = false and o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userID", userID);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	public List<WorkplaceApprovalBean> populateWorkplaceApprovalBean() throws Exception {
		String sql = "select " + 
				" wpa.create_date as 'createDate' " +
				", sq.qualificationid_string as 'codeString' " + 
				", sq.qualificationtitle as 'qualificationTitle' " + 
				", wpa.approval_date as 'approvalDate' " + 
				", wpa.wpa_number as 'wpaApprovalNumber' " +
				", c.company_name as 'companyName' " + 
				", c.trading_name as 'tradingName' " + 
				", c.levy_number as 'levyNumber' " + 
				", c.workplace_approval_number as 'workplaceApprovalNumber' " + 
				", sites.company_name as 'siteName' " +
				
				",CASE " + 
				"	when wpa.approval_enum = 0 then 'Approved' " + 
				"	when wpa.approval_enum = 1 then 'Rejected' " + 
				"	when wpa.approval_enum = 2 then 'Pending Manager Approval' " + 
				"	when wpa.approval_enum = 3 then 'Pending Approval' " + 
				"	when wpa.approval_enum = 4 then 'Pending Sign Off' " + 
				"	when wpa.approval_enum = 5 then 'Completed' " + 
				"	when wpa.approval_enum = 6 then 'Pending accept code of conduct' " + 
				"	when wpa.approval_enum = 7 then 'Awaiting DHET'   " + 
				"	when wpa.approval_enum = 8 then 'Pending Final Approval' " + 
				"	when wpa.approval_enum = 9 then 'Withdrawn'   " + 
				"	when wpa.approval_enum = 10 then 'N/A' " + 
				"	when wpa.approval_enum = 11 then 'Recommended' " + 
				"	when wpa.approval_enum = 12 then 'Appealed' " + 
				"	when wpa.approval_enum = 13 then 'Pending Committee Approval' " + 
				"	when wpa.approval_enum = 14 then 'Approved By ETQA Review Committee' " + 
				"	when wpa.approval_enum = 15 then 'Rejected By ETQA Review Commitee' " + 
				"	when wpa.approval_enum = 16 then 'Requested Higher Allocation' " + 
				"	when wpa.approval_enum = 17 then 'Accepted MOA' " + 
				"	when wpa.approval_enum = 18 then 'Requested Change' " + 
				"	when wpa.approval_enum = 19 then 'Rejected By MANCO Review' " + 
				"	when wpa.approval_enum = 20 then 'Rejected By Learner Review Committee' " + 
				"	when wpa.approval_enum = 21 then 'Qualification Obtained' " + 
				"	when wpa.approval_enum = 22 then 'Deactivated' " + 
				"	when wpa.approval_enum = 23 then 'Project Terminated Manager Approval' " + 
				"	when wpa.approval_enum = 24 then 'Suspend Project' " + 
				"	when wpa.approval_enum = 25 then 'Project Terminated' " + 
				"	when wpa.approval_enum = 26 then 'Pending Review Approval' " + 
				"	when wpa.approval_enum = 27 then 'Uphold' " + 
				"	when wpa.approval_enum = 28 then 'Pending Resubmission'   " + 
				"	when wpa.approval_enum = 29 then 'Awaiting NAMB' " + 
				"	when wpa.approval_enum = 30 then 'Pending Withdrawal' " + 
				"	when wpa.approval_enum = 31 then 'Pending Inverstigation' " + 
				"	when wpa.approval_enum = 32 then 'Pending Change Approval' " + 
				"	when wpa.approval_enum = 33 then 'Not Competent' " + 
				"end as 'status' " + 
								
				", CONCAT(ad.address_line_1, ' ',ad.address_line_2,' ',ad.address_line_3,' ',lt.description, ' ',ad.postcode) as 'residentialAddress' " + 
				", CONCAT(pa.address_line_1, ' ',pa.address_line_2,' ',ad.address_line_3,' ',ltp.description, ' ',pa.postcode) as 'postalAddress' " +

				"from " + 
				
				"work_place_approval wpa " + 
				"left join saqa_qualification sq on sq.id = wpa.qualification_id " +  
				"left join company c on c.id = wpa.company_id " + 
				"left join users u on u.id = wpa.review_user_id " + 
				"left join address ad on ad.id = c.residential_address_id " + 
				"left join address pa on pa.id = c.postal_address_id " + 				
				"left join municipality m on m.id = ad.municipality_id " +
				"left join towns lt on lt.id = ad.town_id " + 
				"left join towns ltp on ltp.id = pa.town_id " + 
				"left join region_town lrt on lrt.town_id = lt.id " + 
				"left join region lr on lr.id = lrt.region_id " + 
				"left join sic_code siccode on siccode.id = c.sic_code_id " +
				"left join chamber cham on cham.id = siccode.chamber_id " +		
				"left join sites sites on sites.id = wpa.sites_id " +	
				"left join ofo_codes ofo on ofo.id = wpa.ofo_codes_id ";
		
		return (List<WorkplaceApprovalBean>)super.nativeSelectSqlList(sql, WorkplaceApprovalBean.class);
	}

	@SuppressWarnings("unchecked")
	public List<Users> findUserByRegionAndRole(Long roleId, Long regionId) {
		String hql = "select (o.users) from HostingCompanyEmployees o where o.jobTitle.roles.id = :roleId"
				+ " and o.jobTitle.region.id = :regionId "
				+ " and o.users.status = :active ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("roleId", roleId);
		parameters.put("regionId", regionId);
		parameters.put("active", UsersStatusEnum.Active);
		return (List<Users>) super.getList(hql, parameters);
	}
}
