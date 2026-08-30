package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.LegacyApprenticeship;
import haj.com.entity.lookup.LegacyApprenticeshipTradeTest;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.report.bean.ByChamberReportBean;

public class LegacyApprenticeshipTradeTestDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyApprenticeshipTradeTest
 	 * @author TechFinium 
 	 * @see    LegacyApprenticeshipTradeTest
 	 * @return a list of {@link haj.com.entity.LegacyApprenticeshipTradeTest}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyApprenticeshipTradeTest> allLegacyApprenticeshipTradeTest() throws Exception {
		return (List<LegacyApprenticeshipTradeTest>)super.getList("select o from LegacyApprenticeshipTradeTest o");
	}

	/**
	 * Get all LegacyApprenticeshipTradeTest between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyApprenticeshipTradeTest
 	 * @return a list of {@link haj.com.entity.LegacyApprenticeshipTradeTest}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyApprenticeshipTradeTest> allLegacyApprenticeshipTradeTest(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyApprenticeshipTradeTest o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyApprenticeshipTradeTest>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyApprenticeshipTradeTest
 	 * @return a {@link haj.com.entity.LegacyApprenticeshipTradeTest}
 	 * @throws Exception global exception
 	 */
	public LegacyApprenticeshipTradeTest findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyApprenticeshipTradeTest o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyApprenticeshipTradeTest)super.getUniqueResult(hql, parameters);
	}
	
	public Integer countByRsaIdNumber(String idNumber) throws Exception {
	 	String hql = "select count(o) from LegacyApprenticeshipTradeTest o where o.idNo = :idNumber " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("idNumber", idNumber.trim());
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countByLegacyApprenticeship(String idNumber, Long qualificationID) throws Exception {
	 	String hql = "select count(o) from LegacyApprenticeshipTradeTest o where o.idNo = :idNumber and o.qualification.id = :qualificationID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("idNumber", idNumber.trim());
	    parameters.put("qualificationID", qualificationID);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	/**
	 * Find LegacyApprenticeshipTradeTest by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyApprenticeshipTradeTest
  	 * @return a list of {@link haj.com.entity.LegacyApprenticeshipTradeTest}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyApprenticeshipTradeTest> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyApprenticeshipTradeTest o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyApprenticeshipTradeTest>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyApprenticeshipTradeTest o")).intValue();
	}
	
	public Integer countAllLegacyApprenticeshipTradeTestNotProcessed() throws Exception {
		String hql = "select count(o) from LegacyApprenticeship o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacyApprenticeshipTradeTest> allLegacyApprenticeshipTradeTestNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyApprenticeship o where o.processed = false";
		return (List<LegacyApprenticeshipTradeTest>) super.getList(hql, numberOfEntries);
	}
	
	public List<ByChamberReportBean> distinctIdNumbersFromTradeTestAndApprenticeship() throws Exception {
		String sql ="select distinct(idNumber) as description from ( " + 
				"select  " + 
				"	id_no as idNumber " + 
				"from " + 
				"	legacy_apprenticeship " + 
				"union " + 
				"select " + 
				"	id_no  as idNumber " + 
				"from " + 
				"	legacy_apprenticeship_trade_test " + 
				") as idNumberCheck";
		return (List<ByChamberReportBean>)super.nativeSelectSqlList(sql, ByChamberReportBean.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyApprenticeshipTradeTest> findByIdNo(String idNo, Long qualificationID) throws Exception {
	 	String hql = "select o from LegacyApprenticeshipTradeTest o where o.idNo like  :idNo and o.qualification.id = :qualificationID " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("idNo", idNo);
	    parameters.put("qualificationID", qualificationID);
		return (List<LegacyApprenticeshipTradeTest>)super.getList(hql, parameters);
	}
}

