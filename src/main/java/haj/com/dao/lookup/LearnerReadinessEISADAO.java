package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.LearnerReadinessEISA;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class LearnerReadinessEISADAO.
 */
public class LearnerReadinessEISADAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LearnerReadinessEISA.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.LearnerReadinessEISA}
	 * @throws Exception global exception
	 * @see    LearnerReadinessEISA
	 */
	@SuppressWarnings("unchecked")
	public List<LearnerReadinessEISA> allLearnerReadinessEISA() throws Exception {
		return (List<LearnerReadinessEISA>)super.getList("select o from LearnerReadinessEISA o");
	}

	/**
	 * Get all LearnerReadinessEISA between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.LearnerReadinessEISA}
	 * @throws Exception global exception
	 * @see    LearnerReadinessEISA
	 */
	@SuppressWarnings("unchecked")
	public List<LearnerReadinessEISA> allLearnerReadinessEISA(Long from, int noRows) throws Exception {
	 	String hql = "select o from LearnerReadinessEISA o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LearnerReadinessEISA>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LearnerReadinessEISA}
	 * @throws Exception global exception
	 * @see    LearnerReadinessEISA
	 */
	public LearnerReadinessEISA findByKey(Long id) throws Exception {
	 	String hql = "select o from LearnerReadinessEISA o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LearnerReadinessEISA)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LearnerReadinessEISA by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.LearnerReadinessEISA}
	 * @throws Exception global exception
	 * @see    LearnerReadinessEISA
	 */
	@SuppressWarnings("unchecked")
	public List<LearnerReadinessEISA> findByName(String description) throws Exception {
	 	String hql = "select o from LearnerReadinessEISA o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LearnerReadinessEISA>)super.getList(hql, parameters);
	}
}

