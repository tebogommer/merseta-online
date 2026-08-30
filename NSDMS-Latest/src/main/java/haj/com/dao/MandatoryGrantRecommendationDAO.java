package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.MandatoryGrant;
import haj.com.entity.MandatoryGrantRecommendation;
import haj.com.entity.Signoff;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class MandatoryGrantRecommendationDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all MandatoryGrantRecommendation
 	 * @author TechFinium 
 	 * @see    MandatoryGrantRecommendation
 	 * @return a list of {@link haj.com.entity.MandatoryGrantRecommendation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrantRecommendation> allMandatoryGrantRecommendation() throws Exception {
		return (List<MandatoryGrantRecommendation>)super.getList("select o from MandatoryGrantRecommendation o");
	}

	/**
	 * Get all MandatoryGrantRecommendation between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    MandatoryGrantRecommendation
 	 * @return a list of {@link haj.com.entity.MandatoryGrantRecommendation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrantRecommendation> allMandatoryGrantRecommendation(Long from, int noRows) throws Exception {
	 	String hql = "select o from MandatoryGrantRecommendation o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<MandatoryGrantRecommendation>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    MandatoryGrantRecommendation
 	 * @return a {@link haj.com.entity.MandatoryGrantRecommendation}
 	 * @throws Exception global exception
 	 */
	public MandatoryGrantRecommendation findByKey(Long id) throws Exception {
	 	String hql = "select o from MandatoryGrantRecommendation o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (MandatoryGrantRecommendation)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find MandatoryGrantRecommendation by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    MandatoryGrantRecommendation
  	 * @return a list of {@link haj.com.entity.MandatoryGrantRecommendation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrantRecommendation> findByName(String description) throws Exception {
	 	String hql = "select o from MandatoryGrantRecommendation o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<MandatoryGrantRecommendation>)super.getList(hql, parameters);
	}
	@SuppressWarnings("unchecked")
	public List<MandatoryGrantRecommendation> findByMG(MandatoryGrant mandatoryGrant) throws Exception {
	 	String hql = "select o from MandatoryGrantRecommendation o where o.mandatoryGrant.id = :mandatoryGrantID order by o.id desc" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("mandatoryGrantID", mandatoryGrant.getId());
		return (List<MandatoryGrantRecommendation>)super.getList(hql, parameters, 1);
	}
	
	@SuppressWarnings("unchecked")
	public MandatoryGrantRecommendation findByMandatoryGrant(MandatoryGrant mandatoryGrant) throws Exception {
	 	String hql = "select o from MandatoryGrantRecommendation o where o.mandatoryGrant.id = :mandatoryGrantID order by o.id desc" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("mandatoryGrantID", mandatoryGrant.getId());
	    return (MandatoryGrantRecommendation)super.getUniqueResult(hql, parameters);
	}
}

