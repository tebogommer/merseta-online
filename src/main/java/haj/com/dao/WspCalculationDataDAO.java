package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.bean.WspCalData;
import haj.com.entity.WspCalculationData;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class WspCalculationDataDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WspCalculationData
 	 * @author TechFinium 
 	 * @see    WspCalculationData
 	 * @return a list of {@link haj.com.entity.WspCalculationData}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspCalculationData> allWspCalculationData() throws Exception {
		return (List<WspCalculationData>)super.getList("select o from WspCalculationData o");
	}

	/**
	 * Get all WspCalculationData between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WspCalculationData
 	 * @return a list of {@link haj.com.entity.WspCalculationData}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspCalculationData> allWspCalculationData(Long from, int noRows) throws Exception {
	 	String hql = "select o from WspCalculationData o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WspCalculationData>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WspCalculationData
 	 * @return a {@link haj.com.entity.WspCalculationData}
 	 * @throws Exception global exception
 	 */
	public WspCalculationData findByKey(Long id) throws Exception {
	 	String hql = "select o from WspCalculationData o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WspCalculationData)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WspCalculationData by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WspCalculationData
  	 * @return a list of {@link haj.com.entity.WspCalculationData}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspCalculationData> findByName(String description) throws Exception {
	 	String hql = "select o from WspCalculationData o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WspCalculationData>)super.getList(hql, parameters);
	}
	
	/**
	 * Find WspCalculationData by company
 	 * @author TechFinium 
 	 * @param companyId the company 
 	 * @see    WspCalculationData
  	 * @return a count
 	 * @throws Exception global exception
 	 */
	public Long findByCompanyCount(Long companyId) throws Exception {
	 	String hql = "select count(o) from WspCalculationData o where o.company.id = :companyId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
		return (Long)super.getUniqueResult(hql, parameters);
	}

	public List<WspCalData> calPercentageByCompany(Long companyId) throws Exception {
		String hql = "select company_name as companyName "
//				+",levy_number as levyNumber "
//				+",SUM(case when type_report = 'WCD' THEN total_plan_train else 0 end) as totalCalcData "
//				+",SUM(case when type_report = 'MGD' THEN total_plan_train else 0 end) as totalTrainData "
				+",SUM(case when type_report = 'MGD' THEN total_plan_train else 0 end)/(SUM(case when type_report = 'WCD' THEN total_plan_train else 0 end))*100 as percentage "
				+"from ( "
				+" select c.company_name , c.levy_number, c.id, it.description as intervention_type_description, count(mgd.id) as total_plan_train, 'MGD' as type_report "
				+" from mandatory_grant_detail mgd "
				+" left join wsp w on w.id = mgd.wsp_id left join company c on c.id = w.company_id left join intervention_type it on it.id = mgd.intervention_type_id "
				+" where "
				+" mgd.wsp_report = 3 and mgd.funding_id = 6 and c.id = :companyId and mgd.imported = true "
				+" group by c.levy_number,c.company_name, mgd.intervention_type_id, id "
				+" union "
				+" select c.company_name, c.levy_number, c.id, wcd.description as intervention_type_description, SUM(wcd.female + wcd.male) as total_plan_train, 'WCD' as type_report "
				+" from wsp_calculation_data  wcd "
				+" inner join company c on c.id = wcd.company_id "
				+" where "
				+" wcd.company_id = :companyId "
				+" group by c.levy_number,c.company_name,wcd.description, id "
				+") as derived "
				+"group by company_name, levy_number, id "
				+"order by percentage desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (List<WspCalData>) super.nativeSelectSqlList(hql, WspCalData.class, parameters);
	}
	
	public List<WspCalData> calcDeviationAmountByCompanyAndFinYear(Long companyId, Integer currentFinYear, Integer previousFinYear) throws Exception {
		String hql = "select company_name as companyName " + 
//				"	,levy_number as levyNumber " + 
//				"	,SUM(case when type_report = 'WCD' THEN total_plan_train else 0 end) as totalCalcData " + 
//				"	,SUM(case when type_report = 'MGD' THEN total_plan_train else 0 end) as totalTrainData " + 
				"	,SUM(case when type_report = 'MGD' THEN total_plan_train else 0 end)/(SUM(case when type_report = 'WCD' THEN total_plan_train else 0 end))*100 as percentage " + 
				"	from ( " + 
				"		select c.company_name , c.levy_number, c.id, it.description as intervention_type_description, count(mgd.id) as total_plan_train, 'MGD' as type_report " + 
				"		from mandatory_grant_detail mgd " + 
				"		left join wsp w on w.id = mgd.wsp_id " + 
				"		left join company c on c.id = w.company_id " + 
				"		left join intervention_type it on it.id = mgd.intervention_type_id " + 
				"		where " + 
				"		mgd.wsp_report = 3 and mgd.funding_id = 6 and c.id = :companyId and mgd.imported = true and w.fin_year = :currentFinYear" + 
				"		group by c.levy_number,c.company_name, mgd.intervention_type_id, id " + 
				"	union  " + 
				"		select c.company_name , c.levy_number, c.id, it.description as intervention_type_description, count(mgd.id) as total_plan_train, 'WCD' as type_report  " + 
				"		from mandatory_grant_detail mgd " + 
				"		left join wsp w on w.id = mgd.wsp_id " + 
				"		left join company c on c.id = w.company_id " + 
				"		left join intervention_type it on it.id = mgd.intervention_type_id " + 
				"		where " + 
				"		mgd.wsp_report = 1 and mgd.funding_id = 6 and c.id = :companyId and mgd.imported = true and w.fin_year = :previousFinYear" + 
				"		group by c.levy_number,c.company_name, mgd.intervention_type_id, id " + 
				"	) as derived " + 
				"	group by company_name, levy_number, id " + 
				"	order by percentage desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("currentFinYear", currentFinYear);
		parameters.put("previousFinYear", previousFinYear);
		return (List<WspCalData>) super.nativeSelectSqlList(hql, WspCalData.class, parameters);
	}
	
	public List<WspCalData> calcDeviationAmountByCompanyAndFinYearByStatus(Long companyId, Integer currentFinYear, Integer previousFinYear) throws Exception {
		String hql = "select company_name as companyName " + 
//				"	,levy_number as levyNumber " + 
//				"	,SUM(case when type_report = 'WCD' THEN total_plan_train else 0 end) as totalCalcData " + 
//				"	,SUM(case when type_report = 'MGD' THEN total_plan_train else 0 end) as totalTrainData " + 
				"	,SUM(case when type_report = 'MGD' THEN total_plan_train else 0 end)/(SUM(case when type_report = 'WCD' THEN total_plan_train else 0 end))*100 as percentage " + 
				"	from ( " + 
				"		select c.company_name , c.levy_number, c.id, it.description as intervention_type_description, count(mgd.id) as total_plan_train, 'MGD' as type_report " + 
				"		from mandatory_grant_detail mgd " + 
				"		left join wsp w on w.id = mgd.wsp_id " + 
				"		left join company c on c.id = w.company_id " + 
				"		left join intervention_type it on it.id = mgd.intervention_type_id " + 
				"		where " + 
				"		mgd.wsp_report = 3 and mgd.funding_id = 6 and c.id = :companyId and mgd.imported = true and w.fin_year = :currentFinYear" + 
				"		group by c.levy_number,c.company_name, mgd.intervention_type_id, id " + 
				"	union  " + 
				"		select c.company_name , c.levy_number, c.id, it.description as intervention_type_description, count(mgd.id) as total_plan_train, 'WCD' as type_report  " + 
				"		from mandatory_grant_detail mgd " + 
				"		left join wsp w on w.id = mgd.wsp_id " + 
				"		left join company c on c.id = w.company_id " + 
				"		left join intervention_type it on it.id = mgd.intervention_type_id " + 
				"		where " + 
				"		mgd.wsp_report = 1 and mgd.funding_id = 6 and c.id = :companyId and mgd.imported = true and w.fin_year = :previousFinYear" + 
				"		group by c.levy_number,c.company_name, mgd.intervention_type_id, id " + 
				"	) as derived " + 
				"	group by company_name, levy_number, id " + 
				"	order by percentage desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("currentFinYear", currentFinYear);
		parameters.put("previousFinYear", previousFinYear);
		parameters.put("approvedStatus", WspStatusEnum.Approved);
		parameters.put("rejectedStatus", WspStatusEnum.Rejected);
		return (List<WspCalData>) super.nativeSelectSqlList(hql, WspCalData.class, parameters);
	}
	
	public List<WspCalData> calcDeviationAmountByCompanyIdsAndFinYearsByStatus(Long currentFinYearCompany, Integer currentFinYear, Long previousFinYearCompany, Integer previousFinYear) throws Exception {
		String hql = "select company_name as companyName " + 
				"	,SUM(case when type_report = 'MGD' THEN total_plan_train else 0 end)/(SUM(case when type_report = 'WCD' THEN total_plan_train else 0 end))*100 as percentage " + 
				"	from ( " + 
				"		select c.company_name , c.levy_number, c.id, it.description as intervention_type_description, count(mgd.id) as total_plan_train, 'MGD' as type_report " + 
				"		from mandatory_grant_detail mgd " + 
				"		left join wsp w on w.id = mgd.wsp_id " + 
				"		left join company c on c.id = w.company_id " + 
				"		left join intervention_type it on it.id = mgd.intervention_type_id " + 
				"		where " + 
				"		mgd.wsp_report = 3 and mgd.funding_id = 6 and c.id = :currentFinYearCompany and mgd.imported = true and w.fin_year = :currentFinYear" + 
				"		group by c.levy_number,c.company_name, mgd.intervention_type_id, id " + 
				"	union  " + 
				"		select c.company_name , c.levy_number, c.id, it.description as intervention_type_description, count(mgd.id) as total_plan_train, 'WCD' as type_report  " + 
				"		from mandatory_grant_detail mgd " + 
				"		left join wsp w on w.id = mgd.wsp_id " + 
				"		left join company c on c.id = w.company_id " + 
				"		left join intervention_type it on it.id = mgd.intervention_type_id " + 
				"		where " + 
				"		mgd.wsp_report = 1 and mgd.funding_id = 6 and c.id = :previousFinYearCompany and mgd.imported = true and w.fin_year = :previousFinYear and w.wspStatusEnum in (:approvedStatus, :rejectedStatus)" + 
				"		group by c.levy_number,c.company_name, mgd.intervention_type_id, id " + 
				"	) as derived " + 
				"	group by company_name, levy_number, id " + 
				"	order by percentage desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("currentFinYearCompany", currentFinYearCompany);
		parameters.put("previousFinYearCompany", previousFinYearCompany);
		parameters.put("currentFinYear", currentFinYear);
		parameters.put("previousFinYear", previousFinYear);
		parameters.put("approvedStatus", WspStatusEnum.Approved);
		parameters.put("rejectedStatus", WspStatusEnum.Rejected);
		return (List<WspCalData>) super.nativeSelectSqlList(hql, WspCalData.class, parameters);
	}
}

