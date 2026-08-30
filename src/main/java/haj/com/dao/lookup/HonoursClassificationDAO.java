package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.HonoursClassification;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class HonoursClassificationDAO.
 */
public class HonoursClassificationDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all HonoursClassification.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.HonoursClassification}
	 * @throws Exception global exception
	 * @see    HonoursClassification
	 */
	@SuppressWarnings("unchecked")
	public List<HonoursClassification> allHonoursClassification() throws Exception {
		return (List<HonoursClassification>)super.getList("select o from HonoursClassification o");
	}

	/**
	 * Get all HonoursClassification between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.HonoursClassification}
	 * @throws Exception global exception
	 * @see    HonoursClassification
	 */
	@SuppressWarnings("unchecked")
	public List<HonoursClassification> allHonoursClassification(Long from, int noRows) throws Exception {
	 	String hql = "select o from HonoursClassification o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<HonoursClassification>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.HonoursClassification}
	 * @throws Exception global exception
	 * @see    HonoursClassification
	 */
	public HonoursClassification findByKey(Long id) throws Exception {
	 	String hql = "select o from HonoursClassification o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (HonoursClassification)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find HonoursClassification by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.HonoursClassification}
	 * @throws Exception global exception
	 * @see    HonoursClassification
	 */
	@SuppressWarnings("unchecked")
	public List<HonoursClassification> findByName(String description) throws Exception {
	 	String hql = "select o from HonoursClassification o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<HonoursClassification>)super.getList(hql, parameters);
	}
	
	/**
	 * *
	 * Find HounoursClassfication by UniqueId.
	 *
	 * @param honoursClassification the honours classification
	 * @return the honours classification
	 * @throws Exception the exception
	 */
	public HonoursClassification findUniqueCode(HonoursClassification honoursClassification) throws Exception {
	 	String hql = "select o from HonoursClassification o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (honoursClassification.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", honoursClassification.getId());
	 	}
	   
	    parameters.put("code", honoursClassification.getCode());
		return (HonoursClassification)super.getUniqueResult(hql, parameters);
	}
}

