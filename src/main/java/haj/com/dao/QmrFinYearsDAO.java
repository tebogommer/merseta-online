package haj.com.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import haj.com.entity.QmrFinYears;
import haj.com.entity.enums.FinYearQuartersEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class QmrFinYearsDAO extends AbstractDAO  {

	// Generic Dao
	private QmrReportingDAO qmrReportingDAO = new QmrReportingDAO();
	
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all QmrFinYears
 	 * @author TechFinium 
 	 * @see    QmrFinYears
 	 * @return a list of {@link haj.com.entity.QmrFinYears}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QmrFinYears> allQmrFinYears() throws Exception {
		return (List<QmrFinYears>)super.getList("select o from QmrFinYears o");
	}

	/**
	 * Get all QmrFinYears between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    QmrFinYears
 	 * @return a list of {@link haj.com.entity.QmrFinYears}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QmrFinYears> allQmrFinYears(Long from, int noRows) throws Exception {
	 	String hql = "select o from QmrFinYears o " ;
	    Map<String, Object> parameters = new HashMap<>();
		return (List<QmrFinYears>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    QmrFinYears
 	 * @return a {@link haj.com.entity.QmrFinYears}
 	 * @throws Exception global exception
 	 */
	public QmrFinYears findByKey(Long id) throws Exception {
	 	String hql = "select o from QmrFinYears o where o.id = :id " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("id", id);
		return (QmrFinYears)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find QmrFinYears by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    QmrFinYears
  	 * @return a list of {@link haj.com.entity.QmrFinYears}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QmrFinYears> findByName(String description) throws Exception {
	 	String hql = "select o from QmrFinYears o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<QmrFinYears>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> findDistinctFinYearsStart() throws Exception {
	 	String hql = "select Distinct(o.finYearStart) from QmrFinYears o order by o.finYearStart asc " ;
		return (List<Integer>)super.getList(hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<QmrFinYears> findByFinYearsStart(Integer finYearStart) throws Exception {
	 	String hql = "select o from QmrFinYears o where o.finYearStart = :finYearStart order by o.finYearQuarters asc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("finYearStart", finYearStart);
		return (List<QmrFinYears>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<QmrFinYears> findByDateForGeneratione(Date generationDate) throws Exception {
	 	String hql = "select o from QmrFinYears o where o.dateForGeneration = :generationDate and (o.dataGenerated is null or o.dataGenerated = false)" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("generationDate", generationDate);
		return (List<QmrFinYears>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<QmrFinYears> findListByFinYearAndQuarter(Long finYearId, FinYearQuartersEnum finYearQuartersEnum) throws Exception {
	 	String hql = "select o from QmrFinYears o where o.financialYears.id = :finYearId and o.finYearQuarters = :finYearQuartersEnum " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("finYearId", finYearId);
	    parameters.put("finYearQuartersEnum", finYearQuartersEnum);
	    return (List<QmrFinYears>)super.getList(hql, parameters);
	}
	
	// qmrReportingDAO
	public void test() throws Exception{
		
	}
	
	
}