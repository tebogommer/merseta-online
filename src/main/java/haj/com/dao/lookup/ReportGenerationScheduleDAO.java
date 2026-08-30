package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.ReportGenerationSchedule;

public class ReportGenerationScheduleDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ReportGenerationSchedule
 	 * @author TechFinium 
 	 * @see    ReportGenerationSchedule
 	 * @return a list of {@link haj.com.entity.ReportGenerationSchedule}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ReportGenerationSchedule> allReportGenerationSchedule() throws Exception {
		return (List<ReportGenerationSchedule>)super.getList("select o from ReportGenerationSchedule o");
	}

	/**
	 * Get all ReportGenerationSchedule between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ReportGenerationSchedule
 	 * @return a list of {@link haj.com.entity.ReportGenerationSchedule}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ReportGenerationSchedule> allReportGenerationSchedule(Long from, int noRows) throws Exception {
	 	String hql = "select o from ReportGenerationSchedule o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ReportGenerationSchedule>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ReportGenerationSchedule
 	 * @return a {@link haj.com.entity.ReportGenerationSchedule}
 	 * @throws Exception global exception
 	 */
	public ReportGenerationSchedule findByKey(Long id) throws Exception {
	 	String hql = "select o from ReportGenerationSchedule o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ReportGenerationSchedule)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ReportGenerationSchedule by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ReportGenerationSchedule
  	 * @return a list of {@link haj.com.entity.ReportGenerationSchedule}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ReportGenerationSchedule> findByName(String description) throws Exception {
	 	String hql = "select o from ReportGenerationSchedule o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ReportGenerationSchedule>)super.getList(hql, parameters);
	}
}

