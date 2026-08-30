package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.ApplicantType;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class ApplicantTypeDAO.
 */
public class ApplicantTypeDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ApplicantType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.ApplicantType}
	 * @throws Exception global exception
	 * @see    ApplicantType
	 */
	@SuppressWarnings("unchecked")
	public List<ApplicantType> allApplicantType() throws Exception {
		return (List<ApplicantType>)super.getList("select o from ApplicantType o");
	}

	/**
	 * Get all ApplicantType between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.ApplicantType}
	 * @throws Exception global exception
	 * @see    ApplicantType
	 */
	@SuppressWarnings("unchecked")
	public List<ApplicantType> allApplicantType(Long from, int noRows) throws Exception {
	 	String hql = "select o from ApplicantType o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ApplicantType>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ApplicantType}
	 * @throws Exception global exception
	 * @see    ApplicantType
	 */
	public ApplicantType findByKey(Long id) throws Exception {
	 	String hql = "select o from ApplicantType o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ApplicantType)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ApplicantType by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.ApplicantType}
	 * @throws Exception global exception
	 * @see    ApplicantType
	 */
	@SuppressWarnings("unchecked")
	public List<ApplicantType> findByName(String description) throws Exception {
	 	String hql = "select o from ApplicantType o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ApplicantType>)super.getList(hql, parameters);
	}
}

