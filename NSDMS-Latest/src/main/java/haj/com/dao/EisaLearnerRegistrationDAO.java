package haj.com.dao;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.EisaLearnerRegistration;

public class EisaLearnerRegistrationDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all EisaLearnerRegistration
 	 * @author TechFinium 
 	 * @see    EisaLearnerRegistration
 	 * @return a list of {@link haj.com.entity.EisaLearnerRegistration}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<EisaLearnerRegistration> allEisaLearnerRegistration() throws Exception {
		return (List<EisaLearnerRegistration>)super.getList("select o from EisaLearnerRegistration o");
	}

	/**
	 * Get all EisaLearnerRegistration between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    EisaLearnerRegistration
 	 * @return a list of {@link haj.com.entity.EisaLearnerRegistration}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<EisaLearnerRegistration> allEisaLearnerRegistration(Long from, int noRows) throws Exception {
	 	String hql = "select o from EisaLearnerRegistration o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<EisaLearnerRegistration>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    EisaLearnerRegistration
 	 * @return a {@link haj.com.entity.EisaLearnerRegistration}
 	 * @throws Exception global exception
 	 */
	public EisaLearnerRegistration findByKey(Long id) throws Exception {
	 	String hql = "select o from EisaLearnerRegistration o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (EisaLearnerRegistration)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find EisaLearnerRegistration by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    EisaLearnerRegistration
  	 * @return a list of {@link haj.com.entity.EisaLearnerRegistration}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<EisaLearnerRegistration> findByName(String description) throws Exception {
	 	String hql = "select o from EisaLearnerRegistration o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<EisaLearnerRegistration>)super.getList(hql, parameters);
	}
}

