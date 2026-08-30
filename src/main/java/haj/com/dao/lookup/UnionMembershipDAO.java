package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.UnionMembership;

public class UnionMembershipDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all UnionMembership
 	 * @author TechFinium 
 	 * @see    UnionMembership
 	 * @return a list of {@link haj.com.entity.UnionMembership}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UnionMembership> allUnionMembership() throws Exception {
		return (List<UnionMembership>)super.getList("select o from UnionMembership o");
	}

	/**
	 * Get all UnionMembership between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    UnionMembership
 	 * @return a list of {@link haj.com.entity.UnionMembership}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UnionMembership> allUnionMembership(Long from, int noRows) throws Exception {
	 	String hql = "select o from UnionMembership o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<UnionMembership>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    UnionMembership
 	 * @return a {@link haj.com.entity.UnionMembership}
 	 * @throws Exception global exception
 	 */
	public UnionMembership findByKey(Long id) throws Exception {
	 	String hql = "select o from UnionMembership o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (UnionMembership)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find UnionMembership by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    UnionMembership
  	 * @return a list of {@link haj.com.entity.UnionMembership}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UnionMembership> findByName(String description) throws Exception {
	 	String hql = "select o from UnionMembership o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<UnionMembership>)super.getList(hql, parameters);
	}
}

