package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.AlternativeIdType;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class AlternativeIdTypeDAO.
 */
public class AlternativeIdTypeDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all AlternativeIdType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.AlternativeIdType}
	 * @throws Exception global exception
	 * @see    AlternativeIdType
	 */
	@SuppressWarnings("unchecked")
	public List<AlternativeIdType> allAlternativeIdType() throws Exception {
		return (List<AlternativeIdType>)super.getList("select o from AlternativeIdType o");
	}

	/**
	 * Get all AlternativeIdType between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.AlternativeIdType}
	 * @throws Exception global exception
	 * @see    AlternativeIdType
	 */
	@SuppressWarnings("unchecked")
	public List<AlternativeIdType> allAlternativeIdType(Long from, int noRows) throws Exception {
	 	String hql = "select o from AlternativeIdType o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<AlternativeIdType>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.AlternativeIdType}
	 * @throws Exception global exception
	 * @see    AlternativeIdType
	 */
	public AlternativeIdType findByKey(Long id) throws Exception {
	 	String hql = "select o from AlternativeIdType o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (AlternativeIdType)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find AlternativeIdType by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.AlternativeIdType}
	 * @throws Exception global exception
	 * @see    AlternativeIdType
	 */
	@SuppressWarnings("unchecked")
	public List<AlternativeIdType> findByName(String description) throws Exception {
	 	String hql = "select o from AlternativeIdType o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<AlternativeIdType>)super.getList(hql, parameters);
	}
	
	
	/**
	 * Find AlternativeIdType by code.
	 *
	 * @author TechFinium
	 * @param alternativeIdType the alternative id type
	 * @return a {@link haj.com.entity.AlternativeIdType}
	 * @throws Exception global exception
	 * @see    AlternativeIdType
	 */
	 public AlternativeIdType findUniqueCode(AlternativeIdType alternativeIdType) throws Exception {
		 String hql = "select o from AlternativeIdType o where o.code = :code " ;
		 	Map<String, Object> parameters = new Hashtable<String, Object>();
		 	if (alternativeIdType.getId()!=null) {
		 		hql += " and o.id <> :id ";
		 		parameters.put("id", alternativeIdType.getId());
		 	}
		   
		    parameters.put("code", alternativeIdType.getCode());
			return (AlternativeIdType)super.getUniqueResult(hql, parameters);
	}
}

