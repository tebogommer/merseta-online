package haj.com.dao.lookup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import haj.com.entity.enums.ReportPropertiesEnum;
import haj.com.entity.lookup.ReportGenerationProperties;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class ReportGenerationPropertiesDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ReportGenerationProperties
 	 * @author TechFinium 
 	 * @see    ReportGenerationProperties
 	 * @return a list of {@link haj.com.entity.ReportGenerationProperties}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ReportGenerationProperties> allReportGenerationProperties() throws Exception {
		return (List<ReportGenerationProperties>)super.getList("select o from ReportGenerationProperties o");
	}

	/**
	 * Get all ReportGenerationProperties between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ReportGenerationProperties
 	 * @return a list of {@link haj.com.entity.ReportGenerationProperties}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ReportGenerationProperties> allReportGenerationProperties(Long from, int noRows) throws Exception {
	 	String hql = "select o from ReportGenerationProperties o " ;
	    Map<String, Object> parameters = new HashMap<>();
		return (List<ReportGenerationProperties>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ReportGenerationProperties
 	 * @return a {@link haj.com.entity.ReportGenerationProperties}
 	 * @throws Exception global exception
 	 */
	public ReportGenerationProperties findByKey(Long id) throws Exception {
	 	String hql = "select o from ReportGenerationProperties o where o.id = :id " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("id", id);
		return (ReportGenerationProperties)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ReportGenerationProperties by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ReportGenerationProperties
  	 * @return a list of {@link haj.com.entity.ReportGenerationProperties}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ReportGenerationProperties> findByName(String description) throws Exception {
	 	String hql = "select o from ReportGenerationProperties o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ReportGenerationProperties>)super.getList(hql, parameters);
	}
	
	public ReportGenerationProperties findByReportProperty(ReportPropertiesEnum reportPropertiesEnum) throws Exception {
	 	String hql = "select o from ReportGenerationProperties o where o.reportProperty = :reportProperties " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("reportProperties", reportPropertiesEnum);
		return (ReportGenerationProperties)super.getUniqueResult(hql, parameters);
	}
	
	public Integer countByReportProperty(ReportPropertiesEnum reportPropertiesEnum) throws Exception {
	 	String hql = "select count(o) from ReportGenerationProperties o where o.reportProperty = :reportPropertiesEnum " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("reportPropertiesEnum", reportPropertiesEnum);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countByReportPropertyAndNotId(ReportPropertiesEnum reportPropertiesEnum, Long id) throws Exception {
	 	String hql = "select count(o) from ReportGenerationProperties o where o.reportProperty = :reportPropertiesEnum and o.id <> :id " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("reportPropertiesEnum", reportPropertiesEnum);
	    parameters.put("id", id);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
}

