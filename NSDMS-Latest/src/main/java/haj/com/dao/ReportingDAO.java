package haj.com.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.annotations.LegacyReportingAnnotation;
import haj.com.annotations.LegacyReportingParamsAnnotation;
import haj.com.bean.EmployeeReportBean;
import haj.com.bean.LegacyReportingBean;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.LegacyReporting;
import haj.com.entity.LegacyReportingParams;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.report.bean.ByChamberReportBean;
import haj.com.service.LegacyReportingService;
import haj.com.utils.ReflectionUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class AddressDAO.
 */
public class ReportingDAO extends AbstractDAO {

	private static final String leftJoins = " " + "left join fetch o.municipality h left join fetch o.town" + " ";
	private LegacyReportingService legacyReportingService = new LegacyReportingService();

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	public Map<String, List<Object>> runReports(Class<?> reportingClass) throws Exception {
		return extractReports(reportingClass);
	}

	private Map<String, List<Object>> extractReports(Class<?> reportingClass) throws Exception {
		Map<String, List<Object>> legacyReportingBeans = new HashMap<>();
		LegacyReportingAnnotation[] legacyReportingAnnotations = ReflectionUtils.getAnnotationsOnType(LegacyReportingAnnotation.class, reportingClass);
		for (LegacyReportingAnnotation legacyReportingAnnotation : legacyReportingAnnotations) {
			Map<String, Object> parameters = new HashMap<>();
			if (legacyReportingAnnotation.params().length > 0) {
				parameters = processParams(reportingClass, legacyReportingAnnotation.params());
			}
			if (!legacyReportingAnnotation.singleResult()) {
				List<Object> result = runReportQuery(legacyReportingAnnotation.returnType(), legacyReportingAnnotation.singleResult(), legacyReportingAnnotation.query(), parameters);
				if (legacyReportingAnnotation.key().isEmpty()) legacyReportingBeans.put(legacyReportingAnnotation.returnType().getName(), result);
				else legacyReportingBeans.put(legacyReportingAnnotation.key(), result);
			} else {
				Long result = runReportQuery(legacyReportingAnnotation.returnType(), legacyReportingAnnotation.singleResult(), legacyReportingAnnotation.query(), parameters);
				Object obj = LegacyReportingBean.class.newInstance();
				ReflectionUtils.setFieldValue(obj, "name", legacyReportingAnnotation.name());
				ReflectionUtils.setFieldValue(obj, "result", result.intValue());
				if (legacyReportingBeans.containsKey(legacyReportingAnnotation.key())) {
					legacyReportingBeans.get(legacyReportingAnnotation.key()).add(obj);
				} else {
					List<Object> results = new ArrayList<>();
					results.add(obj);
					legacyReportingBeans.put(legacyReportingAnnotation.key(), results);
				}
			}
		}
		return legacyReportingBeans;
	}

	private Map<String, Object> processParams(Class<?> reportingClass, LegacyReportingParamsAnnotation[] params) {
		Map<String, Object> parameters = new HashMap<>();
		for (LegacyReportingParamsAnnotation legacyReportingParamsAnnotation : params) {
			parameters.put(legacyReportingParamsAnnotation.paramName(), ReflectionUtils.getFieldValue(reportingClass, legacyReportingParamsAnnotation.fieldName()));
		}
		return parameters;
	}

	@SuppressWarnings("unchecked")
	private <T> T runReportQuery(Class<?> returnType, boolean singleResult, String hql, Map<String, Object> parameters) throws Exception {
		if (singleResult) return (T) super.getUniqueResult(hql, parameters);
		else return (T) super.getList(hql, parameters);
	}

	public Map<String, List<Object>> runReportsDB(Class<?> reportingClass) throws Exception {
		return extractReportsDB(reportingClass);
	}

	private Map<String, List<Object>> extractReportsDB(Class<?> reportingClass) throws Exception {
		Map<String, List<Object>> legacyReportingBeans = new HashMap<>();
		List<LegacyReporting> legacyReportings = legacyReportingService.findByClassName(reportingClass.getName());
		for (LegacyReporting legacyReportingAnnotation : legacyReportings) {
			Map<String, Object> parameters = new HashMap<>();
			if (legacyReportingAnnotation.getLegacyReportingParams().size() > 0) {
				parameters = processParamsDB(reportingClass, legacyReportingAnnotation.getLegacyReportingParams());
			}
			if (!legacyReportingAnnotation.getSingleResult()) {
				List<Object> result = runReportQuery(Class.forName(legacyReportingAnnotation.getReturnType()), legacyReportingAnnotation.getSingleResult(), legacyReportingAnnotation.getQuery(), parameters);
				if (legacyReportingAnnotation.getKey().isEmpty()) legacyReportingBeans.put(legacyReportingAnnotation.getReturnType(), result);
				else legacyReportingBeans.put(legacyReportingAnnotation.getKey(), result);
			} else {
				Long result = runReportQuery(Class.forName(legacyReportingAnnotation.getReturnType()), legacyReportingAnnotation.getSingleResult(), legacyReportingAnnotation.getQuery(), parameters);
				Object obj = LegacyReportingBean.class.newInstance();
				ReflectionUtils.setFieldValue(obj, "name", legacyReportingAnnotation.getName());
				ReflectionUtils.setFieldValue(obj, "result", result.intValue());
				if (legacyReportingBeans.containsKey(legacyReportingAnnotation.getKey())) {
					legacyReportingBeans.get(legacyReportingAnnotation.getKey()).add(obj);
				} else {
					List<Object> results = new ArrayList<>();
					results.add(obj);
					legacyReportingBeans.put(legacyReportingAnnotation.getKey(), results);
				}
			}
		}
		return legacyReportingBeans;
	}

	private Map<String, Object> processParamsDB(Class<?> reportingClass, List<LegacyReportingParams> params) {
		Map<String, Object> parameters = new HashMap<>();
		for (LegacyReportingParams legacyReportingParamsAnnotation : params) {
			parameters.put(legacyReportingParamsAnnotation.getParamName(), ReflectionUtils.getFieldValue(reportingClass, legacyReportingParamsAnnotation.getFieldName()));
		}
		return parameters;
	}
		
	public List<EmployeeReportBean> allEmployeeList() throws Exception {
		String sql = " select chamberName, companySize, sum(isActive) as numberOfCompanyActive, sum(notActive) as numberOfCompanyInactive from ( "
				+ " select ch.description as chamberName,  "
				+ " CASE "
				+ "     WHEN c.numberOfEmployees > 0 and c.numberOfEmployees < 50 THEN 'Small Company' "
				+ "     WHEN c.numberOfEmployees > 49 and c.numberOfEmployees < 150 THEN 'Medium Company' "
				+ "     WHEN c.numberOfEmployees > 149 THEN 'Large Company Company' "
				+ "     ELSE 'Invalid' "
				+ " END AS companySize,  "
				+ " case "
				+ " 		when c.company_status = 0 then 'Awaiting merSETA Approval'  "
				+ " 		when c.company_status = 1 then 'Active'  "
				+ " 		when c.company_status = 2 then 'In-Active'  "
				+ " 		when c.company_status = 3 then 'Rejected'  "
				+ " 		when c.company_status = 4 then 'Approved'  "
				+ " 		when c.company_status = 5 then 'Pending Change Approval'  "
				+ " 		when c.company_status = 6 then 'Non-merSETA Company' "
				+ " 		else 'N/A' "
				+ "         end as Company_Status "
				+ "         , case c.company_status when 1 then 1 else 0 end as isActive "
				+ "         , case c.company_status when 2 then 1 else 0 end as notActive "
				+ " from company c "
				+ " inner join sic_code sc on c.sic_code_id = sc.id "
				+ " inner join chamber ch on sc.chamber_id = ch.id "
				+ " where c.numberOfEmployees is not null and company_status in (1, 2) "
				+ " ) as f group by chamberName, companySize order by chamberName ";
		return (List<EmployeeReportBean>)super.nativeSelectSqlList(sql, EmployeeReportBean.class);
	}
	
	public List<EmployeeReportBean> allCompanyList() throws Exception {
		String sql =  " select chamberName, companySize "
				+ " , count(levyPaying) as levyPaying "
				+ " , count(notLevyPaying) as notLevyPaying "
				+ "  from ( "
				+ " select ch.description as chamberName,  "
				+ " CASE "
				+ "     WHEN c.numberOfEmployees > 0 and c.numberOfEmployees < 50 THEN 'Small Company' "
				+ "     WHEN c.numberOfEmployees > 49 and c.numberOfEmployees < 150 THEN 'Medium Company' "
				+ "     WHEN c.numberOfEmployees > 149 THEN 'Large Company Company' "
				+ "     ELSE 'Invalid' "
				+ " END AS companySize "
				+ " , (select c.levy_number where c.levy_number like 'L%') as levyPaying "
				+ " , (select c.levy_number where c.levy_number like 'N%') as notLevyPaying "
				+ "     from company c "
				+ " inner join sic_code sc on c.sic_code_id = sc.id "
				+ " inner join chamber ch on sc.chamber_id = ch.id "
				+ " where c.numberOfEmployees is not null "
				+ " ) as f group by chamberName, companySize order by chamberName ";
		return (List<EmployeeReportBean>)super.nativeSelectSqlList(sql, EmployeeReportBean.class);
	}
	
	public List<EmployeeReportBean> allGrantList() throws Exception {
		String sql =    " select c.company_name as companyName, w.fin_year as grantYear, w.wsp_type as grantType, p.province_desc as province, r.description as region from wsp w "
				+ " inner join company c on w.company_id = c.id "
				+ " inner join address a on c.id = a.company_id "
				+ " inner join municipality m on a.municipality_id = m.id "
				+ " inner join province p on m.provinces_idprovinces = p.id "
				+ " left join region_town rt on rt.town_id = a.town_id "
				+ " inner join region r on rt.region_id = r.id "
				+ " where wsp_status_enum = 0 ";
		return (List<EmployeeReportBean>)super.nativeSelectSqlList(sql, EmployeeReportBean.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<EmployeeReportBean> allCRMList(Class<EmployeeReportBean> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String sql =     " select "
				+ " 	/* Company Information */ "
				+ " 	c.company_name as company_name, "
				+ " 	c.levy_number as equity_id, "
				+ " 	 "
				+ " 	/* Clo Information */ "
				+ " 	uClo.id as clo_user_id, "
				+ " 	uClo.first_name as clo_first_name, "
				+ " 	uClo.last_name as clo_last_name, "
				+ " 	uClo.email as clo_email, "
				+ " 	jtClo.description as clo_job_title, "
				+ " 	hceClo.id as clo_hosting_company_employee_link_id, "
				+ " 	 "
				+ " 	/* CRM Information */	 "
				+ " 	uCrm.id as crm_user_id, "
				+ " 	uCrm.first_name as crm_first_name, "
				+ " 	uCrm.last_name as crm_last_name, "
				+ " 	uCrm.email as crm_email, "
				+ " 	jtCrm.description as crm_job_title, "
				+ " 	hceCrm.id as crm_hosting_company_employee_link_id "
				+ " 	 "
				+ " 	from company c "
				+ " 	left join address resAdd on resAdd.id = c.residential_address_id "
				+ " 	left join region_town rt on rt.town_id = resAdd.town_id "
				+ " 	left join hosting_company_employees hceClo on hceClo.id = rt.clo_id "
				+ " 	left join hosting_company_employees hceCrm on hceCrm.id = rt.crm_id "
				+ " 	left join job_title jtClo on jtClo.id = hceClo.job_title_id "
				+ " 	left join job_title jtCrm on jtCrm.id = hceCrm.job_title_id "
				+ " 	left join users uClo on uClo.id = hceClo.user_id "
				+ " 	left join users uCrm on uCrm.id = hceCrm.user_id "
				+ " 	where "
				+ " 	c.residential_address_id is not null and uClo.id = 69 ";
		return (List<EmployeeReportBean>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, sql);
	}
	/*
	 * @SuppressWarnings("unchecked") public List<Company>
	 * companiesInCloRegion(Class<Company> class1, int first, int pageSize, String
	 * sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception
	 * { String hql = " select o.wsp.company from DgVerification o " + " where " +
	 * " o.wsp.finYear = :FinYear " + " and " +
	 * " o.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID)"
	 * ; filters.put("userID", u.getId()); filters.put("FinYear", finYear); return
	 * (List<Company>) super.sortAndFilterWhere(first, pageSize, sortField,
	 * sortOrder, filters, hql); }
	 * 
	 * @SuppressWarnings("unchecked") public int
	 * countAllcompaniesInCloRegion(Class<Company> class1, Map<String, Object>
	 * filters) throws Exception { String hql =
	 * " select count(o) from DgVerification o " + " where " +
	 * " o.wsp.finYear = :FinYear " + " and " +
	 * " o.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID)"
	 * ; filters.put("userID", u.getId()); filters.put("FinYear", finYear); return
	 * countWhere(filters, hql); }
	 */
	

	public int countAllCRM(Class<EmployeeReportBean> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Company o where o.residentialAddress.id is not null and uCrm.id = 63 ";
		Map<String, Object> parameters = new Hashtable<String, Object>();	
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public List<EmployeeReportBean> allCLOList() throws Exception {
		String sql =     " select "			
				+ " 	c.company_name as company_name, "
				+ " 	c.levy_number as equity_id, "
				+ " 	uClo.id as clo_user_id, "
				+ " 	uClo.first_name as clo_first_name, "
				+ " 	uClo.last_name as clo_last_name, "
				+ " 	uClo.email as clo_email, "
				+ " 	jtClo.description as clo_job_title, "
				+ " 	hceClo.id as clo_hosting_company_employee_link_id, "
				+ " 	uCrm.id as crm_user_id, "
				+ " 	uCrm.first_name as crm_first_name, "
				+ " 	uCrm.last_name as crm_last_name, "
				+ " 	uCrm.email as crm_email, "
				+ " 	jtCrm.description as crm_job_title, "
				+ " 	hceCrm.id as crm_hosting_company_employee_link_id "
				+ " 	from company c "
				+ " 	left join address resAdd on resAdd.id = c.residential_address_id "
				+ " 	left join region_town rt on rt.town_id = resAdd.town_id "
				+ " 	left join hosting_company_employees hceClo on hceClo.id = rt.clo_id "
				+ " 	left join hosting_company_employees hceCrm on hceCrm.id = rt.crm_id "
				+ " 	left join job_title jtClo on jtClo.id = hceClo.job_title_id "
				+ " 	left join job_title jtCrm on jtCrm.id = hceCrm.job_title_id "
				+ " 	left join users uClo on uClo.id = hceClo.user_id "
				+ " 	left join users uCrm on uCrm.id = hceCrm.user_id "
				+ " 	where "
				+ " 	c.residential_address_id is not null and uClo.id = 69 ";
		return (List<EmployeeReportBean>)super.nativeSelectSqlList(sql, EmployeeReportBean.class);
	}
}
