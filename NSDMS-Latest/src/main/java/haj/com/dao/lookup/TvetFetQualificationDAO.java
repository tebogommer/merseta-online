package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.TvetFetQualification;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class TvetFetQualificationDAO.
 */
public class TvetFetQualificationDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TvetFetQualification.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.TvetFetQualification}
	 * @throws Exception global exception
	 * @see    TvetFetQualification
	 */
	@SuppressWarnings("unchecked")
	public List<TvetFetQualification> allTvetFetQualification() throws Exception {
		return (List<TvetFetQualification>)super.getList("select o from TvetFetQualification o");
	}

	/**
	 * Get all TvetFetQualification between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.TvetFetQualification}
	 * @throws Exception global exception
	 * @see    TvetFetQualification
	 */
	@SuppressWarnings("unchecked")
	public List<TvetFetQualification> allTvetFetQualification(Long from, int noRows) throws Exception {
	 	String hql = "select o from TvetFetQualification o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<TvetFetQualification>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.TvetFetQualification}
	 * @throws Exception global exception
	 * @see    TvetFetQualification
	 */
	public TvetFetQualification findByKey(Long id) throws Exception {
	 	String hql = "select o from TvetFetQualification o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (TvetFetQualification)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TvetFetQualification by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.TvetFetQualification}
	 * @throws Exception global exception
	 * @see    TvetFetQualification
	 */
	@SuppressWarnings("unchecked")
	public List<TvetFetQualification> findByName(String description) throws Exception {
	 	String hql = "select o from TvetFetQualification o where o.description like  :description order by o.description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<TvetFetQualification>)super.getList(hql, parameters);
	}
}

