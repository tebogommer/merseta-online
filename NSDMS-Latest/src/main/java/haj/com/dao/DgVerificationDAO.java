package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.bean.DgVerificationReportBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.DgVerification;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CloRecommendationEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class DgVerificationDAO extends AbstractDAO  {

	/** The Constant leftJoins. */
	private static final String leftJoins = " " + " left join fetch o.wsp dgwsp " + " ";
	
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DgVerification
 	 * @author TechFinium 
 	 * @see    DgVerification
 	 * @return a list of {@link haj.com.entity.DgVerification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DgVerification> allDgVerification() throws Exception {
		return (List<DgVerification>)super.getList("select o from DgVerification o " +leftJoins);
	}

	/**
	 * Get all DgVerification between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    DgVerification
 	 * @return a list of {@link haj.com.entity.DgVerification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DgVerification> allDgVerification(Long from, int noRows) throws Exception {
	 	String hql = "select o from DgVerification o " +leftJoins ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<DgVerification>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    DgVerification
 	 * @return a {@link haj.com.entity.DgVerification}
 	 * @throws Exception global exception
 	 */
	public DgVerification findByKey(Long id) throws Exception {
	 	String hql = "select o from DgVerification o "+leftJoins+" where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (DgVerification)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    DgVerification
 	 * @return a {@link haj.com.entity.DgVerification}
 	 * @throws Exception global exception
 	 */
	public DgVerification findByWsp(Long wspId) throws Exception {
	 	String hql = "select o from DgVerification o "+leftJoins+" where o.wsp.id = :wspId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("wspId", wspId);
		return (DgVerification)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DgVerification by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    DgVerification
  	 * @return a list of {@link haj.com.entity.DgVerification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DgVerification> findByName(String description) throws Exception {
	 	String hql = "select o from DgVerification o "+leftJoins+" where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<DgVerification>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Wsp> findByWspByVerificationCompany(String description) throws Exception {
	 	String hql = "select o.wsp from DgVerification o where (o.wsp.company.companyName like :companyName OR o.wsp.company.tradingName like  :companyName OR o.wsp.company.levyNumber like  :companyName) and o.wsp.id not in "
	 			+ "(select x.wsp.id from DgAllocationParent x where x.wsp.company.companyName like :companyName OR x.wsp.company.tradingName like  :companyName OR x.wsp.company.levyNumber like :companyName)"
	 			+ " order by o.wsp.company.companyName" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyName", ""+description.trim()+"%");
		return (List<Wsp>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Wsp> findByWspByVerificationCompanyWspApproved(String description) throws Exception {
	 	String hql = "select o.wsp from DgVerification o where (o.wsp.company.companyName like :companyName OR o.wsp.company.tradingName like  :companyName OR o.wsp.company.levyNumber like  :companyName) and o.wsp.id not in "
	 			+ "(select x.wsp.id from DgAllocationParent x where x.wsp.company.companyName like :companyName OR x.wsp.company.tradingName like  :companyName OR x.wsp.company.levyNumber like :companyName) "
	 			+ "and o.status =:dgStatus "
	 			+ "order by o.wsp.company.companyName" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyName", ""+description.trim()+"%");
	    parameters.put("dgStatus",  ApprovalEnum.Approved);
		return (List<Wsp>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Wsp> findByWspByVerificationCompanyWspApprovedAndFinYear(String description, Integer finYear) throws Exception {
	 	String hql = "select o.wsp from DgVerification o where (o.wsp.company.companyName like :companyName OR o.wsp.company.tradingName like  :companyName OR o.wsp.company.levyNumber like  :companyName) and o.wsp.id not in "
	 			+ "(select x.wsp.id from DgAllocationParent x where x.wsp.company.companyName like :companyName OR x.wsp.company.tradingName like  :companyName OR x.wsp.company.levyNumber like :companyName) "
	 			+ "and o.status =:dgStatus "
	 			+ "and o.wsp.finYear =:finYear "
	 			+ "order by o.wsp.company.companyName" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyName", ""+description.trim()+"%");
	    parameters.put("dgStatus",  ApprovalEnum.Approved);
	    parameters.put("finYear",  finYear);
		return (List<Wsp>)super.getList(hql, parameters);
	}
	
	/**
	 * Find object by wsp Id
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    DgVerification
 	 * @return a {@link haj.com.entity.DgVerification}
 	 * @throws Exception global exception
 	 */
	public DgVerification findByWspId(Long wspId) throws Exception {
	 	String hql = "select o from DgVerification o "+leftJoins+" where o.wsp.id = :wspId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("wspId", wspId);
		return (DgVerification)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Counts DgVerification by status where wsp is not null and optional: fin year against wsp
	 * @param status
	 * @param finYear
	 * @return Integer
	 * @throws Exception
	 */
	public Integer countDgByStatusAndFinYear(ApprovalEnum status, List<Integer> finYear) throws Exception {
		String hql = "select count(o) from DgVerification o where o.wsp is not null and o.status = :status ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("status", status);
		if (finYear != null) {
			int count = 1;
			for (Integer year : finYear) {
				if (count == finYear.size()) {
					hql += "o.wsp.finYear = :finYear" + count;
				}else {
					hql += "o.wsp.finYear = :finYear" + count + " and ";
				}
				parameters.put("finYear"+ count, year);
				count++;
			}
		}
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countDgByStatusAndFinYear(ApprovalEnum status, Integer finYear) throws Exception {
		String hql = "select count(o) from DgVerification o where o.wsp is not null and o.status = :status and o.wsp.finYear = :finYear";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("status", status);
		parameters.put("finYear", finYear);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	/**
	 * Counts DgVerification  where wsp is not null and status is null and optional: fin year against wsp
	 * @param status
	 * @param finYear
	 * @return Integer
	 * @throws Exception
	 */
	public Integer countDgByStatusNullAndFinYear(List<Integer> finYear) throws Exception {
		String hql = "select count(o) from DgVerification o where o.wsp is not null and o.status is null ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		if (finYear != null) {
			int count = 1;
			for (Integer year : finYear) {
				if (count == finYear.size()) {
					hql += "o.wsp.finYear = :finYear" + count;
				}else {
					hql += "o.wsp.finYear = :finYear" + count + " and ";
				}
				parameters.put("finYear"+ count, year);
				count++;
			}
		}
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countDgByStatusNullAndFinYear(Integer finYear) throws Exception {
		String hql = "select count(o) from DgVerification o where o.wsp is not null and o.status is null and o.wsp.finYear = :finYear";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	/**
	 * Counts DgVerification where wsp is not null and optional: fin year against wsp
	 * @param status
	 * @param finYear
	 * @return Integer
	 * @throws Exception
	 */
	public Integer countAllDgFinYear(List<Integer> finYear) throws Exception {
		String hql = "select count(o) from DgVerification o where o.wsp is not null ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		if (finYear != null) {
			int count = 1;
			hql += " and ";
			for (Integer year : finYear) {
				if (count == finYear.size()) {
					hql += "o.wsp.finYear = :finYear" + count;
				}else {
					hql += "o.wsp.finYear = :finYear" + count + " and ";
				}
				parameters.put("finYear"+ count, year);
				count++;
			}
		}
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countAllDgFinYear(Integer finYear) throws Exception {
		String hql = "select count(o) from DgVerification o where o.wsp is not null and o.wsp.finYear = :finYear";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	/**
	 * Find DgVerification by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    DgVerification
  	 * @return a list of {@link haj.com.entity.DgVerification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DgVerification> findDgVerificationWithSdfAndCrmDecisiion(CloRecommendationEnum cloRecommendation) throws Exception {
	 	String hql = "select o from DgVerification o "+ leftJoins + " where o.status = :status and o.crmDecision = :crmDecision and o.withSdf = :withSdf" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("status", ApprovalEnum.PendingFinalApproval);
	    parameters.put("crmDecision", cloRecommendation);
	    parameters.put("withSdf", true);	    
		return (List<DgVerification>)super.getList(hql, parameters);
	}
	
	
	
	/*
	 * Reporting Start
	 */
	
	/**
	 * DG Verification Report Summary
	 * Uses Native SQL to generate report and map to DgVerificationReportBean
	 * @return DgVerificationReportBean
	 * @param finYear
	 * @throws Exception
	 */
	public List<DgVerificationReportBean> getAllVerificationsByFinYear(Integer finYear) throws Exception {
		String sql ="select  " + 
				"	c.levy_number as levyNumber " + 
				"	, c.company_name as organisationName " + 
				"	, c.trading_name as tradingName " + 
				"	, c.numberOfEmployees as numberOfEmployees " + 
				"	, ot.description as organisationType " + 
				"	, r.description as region " + 
				"	, dg.create_date as dateVerificationGenerated " + 
				"	, case " + 
				"		when dg.status is null then 'Not Started - In Progress'  " + 
				"		when dg.status = 0 then 'Approved'  " + 
				"		when dg.status = 1 then 'Rejected'  " + 
				"		when dg.status = 2 then 'Pending Manager Approval'  " + 
				"		when dg.status = 3 then 'Pending Approval'  " + 
				"		when dg.status = 4 then 'Pending Sign Off'  " + 
				"		when dg.status = 5 then 'Completed'  " + 
				"		when dg.status = 6 then 'Pending accept code of conduct'  " + 
				"		when dg.status = 7 then 'Awaiting DHET'  " + 
				"		when dg.status = 8 then 'Pending Final Approval'  " + 
				"		when dg.status = 9 then 'Withdrawn'  " + 
				"		when dg.status = 10 then 'N/A'  " + 
				"		when dg.status = 11 then 'Recommended'  " + 
				"		when dg.status = 12 then 'Appealed'  " + 
				"		end as dgVerificationStatus " + 
				"	, case " + 
				"		when dg.with_sdf is null then 'Not With SDF' " + 
				"		when dg.with_sdf = false then 'Not With SDF' " + 
				"		when dg.with_sdf = true then 'Currently With SDF' " + 
				"	    end as dgVerificationWithSdf " + 
				"	, case " + 
				"		when dg.application_appealed = false then 'Not Appealed' " + 
				"		when dg.application_appealed = true then 'Application Appealed' " + 
				"	    end as dgVerificationApplicationAppealed " + 
				"	, dg.date_appealed as dateApplicationAppealed " +
				"	, uClo.first_name as cloFirstName " + 
				"	, uClo.last_name as cloLastName " + 
				"	, uCloOnVerification.first_name as cloFirstNameOnVerification " + 
				"	, uCloOnVerification.last_name as cloLastNameOnVerification " + 
				"	, case " + 
				"		when dg.clo_recommendation is null then 'Not Provided Yet' " + 
				"		when dg.clo_recommendation = 0 then 'Approval'  " + 
				"		when dg.clo_recommendation = 1 then 'Rejection' " + 
				"		end as cloRecommendationOnSubmission " + 
				"	, (SELECT GROUP_CONCAT(rrClo.description, '. ') " + 
				"		from reject_reasons rrClo " + 
				"		where rrClo.id in  " + 
				"			(select reject_reason_id from dg_verification_rejection_information where dg_verification_id = dg.id and role_id = :cloRolePassed ) " + 
				"		) as cloRejectionReasons " + 
				"	, uCrm.first_name as crmFirstName " + 
				"	, uCrm.last_name as crmLastName " + 
				"	, uCrmOnVerification.first_name as crmFirstNameOnVerification " + 
				"	, uCrmOnVerification.last_name as crmLastNameOnVerification " + 
				"	, case " + 
				"		when dg.crm_decision is null then 'Not Provided Yet' " + 
				"		when dg.crm_decision = 0 then 'Approval'  " + 
				"		when dg.crm_decision = 1 then 'Rejection' " + 
				"		end as crmDecision " + 
				"	, (SELECT GROUP_CONCAT(rrCrm.description, '. ') " + 
				"		from reject_reasons rrCrm " + 
				"		where rrCrm.id in  " + 
				"			(select reject_reason_id from dg_verification_rejection_information where dg_verification_id = dg.id and role_id = :crmRolePassed ) " + 
				"		) as crmRejectionReasons " + 
				"	, dg.crm_approval_rejection_date as crmDecisionDateStamp " + 
				"	, dg.final_response as finalResponse " + 
				"	, dg.crm_appealed_date_approved as dateCrmAppealedDateApproved " + 
				"	, dg.approved_date as finalApprovalDate " + 
				"from dg_verification dg " + 
				"left join wsp w on w.id = dg.wsp_id " + 
				"left join company c on c.id = w.company_id " + 
				"left join organisation_type ot on ot.id = c.organisation_type_id " + 
				"left join address resAdd on resAdd.id = c.residential_address_id " + 
				"left join region_town rt on rt.town_id = resAdd.town_id " + 
				"left join region r on r.id = rt.region_id " + 
				"left join hosting_company_employees hceClo on hceClo.id = rt.clo_id " + 
				"left join hosting_company_employees hceCrm on hceCrm.id = rt.crm_id " + 
				"left join job_title jtClo on jtClo.id = hceClo.job_title_id " + 
				"left join job_title jtCrm on jtCrm.id = hceCrm.job_title_id " + 
				"left join users uClo on uClo.id = hceClo.user_id " + 
				"left join users uCrm on uCrm.id = hceCrm.user_id " + 
				"left join users uCloOnVerification on uCloOnVerification.id = dg.clo_user_id " + 
				"left join users uCrmOnVerification on uCrmOnVerification.id = dg.crm_user_id " + 
				"left join towns tw on tw.id = resAdd.town_id " + 
				"left join municipality mc on mc.id = resAdd.municipality_id " + 
				"where w.fin_year = :finYearPassed " + 
				"order by  " + 
				"c.levy_number asc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("finYearPassed", finYear);
		parameters.put("cloRolePassed", HAJConstants.ROLES_CLIENT_LIAISON_OFFICER_ID);
		parameters.put("crmRolePassed", HAJConstants.ROLES_CLIENT_RELATIONS_MANAGER_ID);
		return (List<DgVerificationReportBean>)super.nativeSelectSqlList(sql, DgVerificationReportBean.class, parameters);
	}
	
	public List<DgVerificationReportBean> getAllVerificationsByFinYearWithCloCrmId(Integer finYear, Long userId) throws Exception {
		String sql ="select  " + 
				"	c.levy_number as levyNumber " + 
				"	, c.company_name as organisationName " + 
				"	, c.trading_name as tradingName " + 
				"	, c.numberOfEmployees as numberOfEmployees " + 
				"	, ot.description as organisationType " + 
				"	, r.description as region " + 
				"	, dg.create_date as dateVerificationGenerated " + 
				"	, case " + 
				"		when dg.status is null then 'Not Started - In Progress'  " + 
				"		when dg.status = 0 then 'Approved'  " + 
				"		when dg.status = 1 then 'Rejected'  " + 
				"		when dg.status = 2 then 'Pending Manager Approval'  " + 
				"		when dg.status = 3 then 'Pending Approval'  " + 
				"		when dg.status = 4 then 'Pending Sign Off'  " + 
				"		when dg.status = 5 then 'Completed'  " + 
				"		when dg.status = 6 then 'Pending accept code of conduct'  " + 
				"		when dg.status = 7 then 'Awaiting DHET'  " + 
				"		when dg.status = 8 then 'Pending Final Approval'  " + 
				"		when dg.status = 9 then 'Withdrawn'  " + 
				"		when dg.status = 10 then 'N/A'  " + 
				"		when dg.status = 11 then 'Recommended'  " + 
				"		when dg.status = 12 then 'Appealed'  " + 
				"		end as dgVerificationStatus " + 
				"	, case " + 
				"		when dg.with_sdf is null then 'Not With SDF' " + 
				"		when dg.with_sdf = false then 'Not With SDF' " + 
				"		when dg.with_sdf = true then 'Currently With SDF' " + 
				"	    end as dgVerificationWithSdf " + 
				"	, case " + 
				"		when dg.application_appealed = false then 'Not Appealed' " + 
				"		when dg.application_appealed = true then 'Application Appealed' " + 
				"	    end as dgVerificationApplicationAppealed " + 
				"	, dg.date_appealed as dateApplicationAppealed " +
				"	, uClo.first_name as cloFirstName " + 
				"	, uClo.last_name as cloLastName " + 
				"	, uCloOnVerification.first_name as cloFirstNameOnVerification " + 
				"	, uCloOnVerification.last_name as cloLastNameOnVerification " + 
				"	, case " + 
				"		when dg.clo_recommendation is null then 'Not Provided Yet' " + 
				"		when dg.clo_recommendation = 0 then 'Approval'  " + 
				"		when dg.clo_recommendation = 1 then 'Rejection' " + 
				"		end as cloRecommendationOnSubmission " + 
				"	, (SELECT GROUP_CONCAT(rrClo.description, '. ') " + 
				"		from reject_reasons rrClo " + 
				"		where rrClo.id in  " + 
				"			(select reject_reason_id from dg_verification_rejection_information where dg_verification_id = dg.id and role_id = :cloRolePassed ) " + 
				"		) as cloRejectionReasons " + 
				"	, uCrm.first_name as crmFirstName " + 
				"	, uCrm.last_name as crmLastName " + 
				"	, uCrmOnVerification.first_name as crmFirstNameOnVerification " + 
				"	, uCrmOnVerification.last_name as crmLastNameOnVerification " + 
				"	, case " + 
				"		when dg.crm_decision is null then 'Not Provided Yet' " + 
				"		when dg.crm_decision = 0 then 'Approval'  " + 
				"		when dg.crm_decision = 1 then 'Rejection' " + 
				"		end as crmDecision " + 
				"	, (SELECT GROUP_CONCAT(rrCrm.description, '. ') " + 
				"		from reject_reasons rrCrm " + 
				"		where rrCrm.id in  " + 
				"			(select reject_reason_id from dg_verification_rejection_information where dg_verification_id = dg.id and role_id = :crmRolePassed ) " + 
				"		) as crmRejectionReasons " + 
				"	, dg.crm_approval_rejection_date as crmDecisionDateStamp " + 
				"	, dg.final_response as finalResponse " + 
				"	, dg.crm_appealed_date_approved as dateCrmAppealedDateApproved " + 
				"	, dg.approved_date as finalApprovalDate " + 
				"from dg_verification dg " + 
				"left join wsp w on w.id = dg.wsp_id " + 
				"left join company c on c.id = w.company_id " + 
				"left join organisation_type ot on ot.id = c.organisation_type_id " + 
				"left join address resAdd on resAdd.id = c.residential_address_id " + 
				"left join region_town rt on rt.town_id = resAdd.town_id " + 
				"left join region r on r.id = rt.region_id " + 
				"left join hosting_company_employees hceClo on hceClo.id = rt.clo_id " + 
				"left join hosting_company_employees hceCrm on hceCrm.id = rt.crm_id " + 
				"left join job_title jtClo on jtClo.id = hceClo.job_title_id " + 
				"left join job_title jtCrm on jtCrm.id = hceCrm.job_title_id " + 
				"left join users uClo on uClo.id = hceClo.user_id " + 
				"left join users uCrm on uCrm.id = hceCrm.user_id " + 
				"left join users uCloOnVerification on uCloOnVerification.id = dg.clo_user_id " + 
				"left join users uCrmOnVerification on uCrmOnVerification.id = dg.crm_user_id " + 
				"left join towns tw on tw.id = resAdd.town_id " + 
				"left join municipality mc on mc.id = resAdd.municipality_id " + 
				"where w.fin_year = :finYearPassed and (uClo.id = :userId or uCrm.id = :userId) " + 
				"order by  " + 
				"c.levy_number asc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("finYearPassed", finYear);
		parameters.put("userId", userId);
		parameters.put("cloRolePassed", HAJConstants.ROLES_CLIENT_LIAISON_OFFICER_ID);
		parameters.put("crmRolePassed", HAJConstants.ROLES_CLIENT_RELATIONS_MANAGER_ID);
		return (List<DgVerificationReportBean>)super.nativeSelectSqlList(sql, DgVerificationReportBean.class, parameters);
	}
	
	/*
	 * Reporting End
	 */
	
}