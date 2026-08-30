package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.TrainingReportedAtrPtr;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class TrainingReportedAtrPtrDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TrainingReportedAtrPtr
 	 * @author TechFinium 
 	 * @see    TrainingReportedAtrPtr
 	 * @return a list of {@link haj.com.entity.TrainingReportedAtrPtr}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingReportedAtrPtr> allTrainingReportedAtrPtr() throws Exception {
		return (List<TrainingReportedAtrPtr>)super.getList("select o from TrainingReportedAtrPtr o");
	}

	/**
	 * Get all TrainingReportedAtrPtr between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    TrainingReportedAtrPtr
 	 * @return a list of {@link haj.com.entity.TrainingReportedAtrPtr}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingReportedAtrPtr> allTrainingReportedAtrPtr(Long from, int noRows) throws Exception {
	 	String hql = "select o from TrainingReportedAtrPtr o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<TrainingReportedAtrPtr>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    TrainingReportedAtrPtr
 	 * @return a {@link haj.com.entity.TrainingReportedAtrPtr}
 	 * @throws Exception global exception
 	 */
	public TrainingReportedAtrPtr findByKey(Long id) throws Exception {
	 	String hql = "select o from TrainingReportedAtrPtr o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (TrainingReportedAtrPtr)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TrainingReportedAtrPtr by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    TrainingReportedAtrPtr
  	 * @return a list of {@link haj.com.entity.TrainingReportedAtrPtr}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingReportedAtrPtr> findByName(String description) throws Exception {
	 	String hql = "select o from TrainingReportedAtrPtr o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<TrainingReportedAtrPtr>)super.getList(hql, parameters);
	}
}

