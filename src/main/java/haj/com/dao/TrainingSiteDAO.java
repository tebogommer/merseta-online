package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.TrainingSite;

public class TrainingSiteDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TrainingSite
 	 * @author TechFinium 
 	 * @see    TrainingSite
 	 * @return a list of {@link haj.com.entity.TrainingSite}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingSite> allTrainingSite() throws Exception {
		return (List<TrainingSite>)super.getList("select o from TrainingSite o");
	}

	/**
	 * Get all TrainingSite between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    TrainingSite
 	 * @return a list of {@link haj.com.entity.TrainingSite}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingSite> allTrainingSite(Long from, int noRows) throws Exception {
	 	String hql = "select o from TrainingSite o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<TrainingSite>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    TrainingSite
 	 * @return a {@link haj.com.entity.TrainingSite}
 	 * @throws Exception global exception
 	 */
	public TrainingSite findByKey(Long id) throws Exception {
	 	String hql = "select o from TrainingSite o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (TrainingSite)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TrainingSite by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    TrainingSite
  	 * @return a list of {@link haj.com.entity.TrainingSite}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingSite> findByName(String description) throws Exception {
	 	String hql = "select o from TrainingSite o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<TrainingSite>)super.getList(hql, parameters);
	}
}

