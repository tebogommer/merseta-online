package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.RelationshipToCompany;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class RelationshipToCompanyDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all RelationshipToCompany
 	 * @author TechFinium 
 	 * @see    RelationshipToCompany
 	 * @return a list of {@link haj.com.entity.RelationshipToCompany}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<RelationshipToCompany> allRelationshipToCompany() throws Exception {
		return (List<RelationshipToCompany>)super.getList("select o from RelationshipToCompany o");
	}

	/**
	 * Get all RelationshipToCompany between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    RelationshipToCompany
 	 * @return a list of {@link haj.com.entity.RelationshipToCompany}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<RelationshipToCompany> allRelationshipToCompany(Long from, int noRows) throws Exception {
	 	String hql = "select o from RelationshipToCompany o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<RelationshipToCompany>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    RelationshipToCompany
 	 * @return a {@link haj.com.entity.RelationshipToCompany}
 	 * @throws Exception global exception
 	 */
	public RelationshipToCompany findByKey(Long id) throws Exception {
	 	String hql = "select o from RelationshipToCompany o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (RelationshipToCompany)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find RelationshipToCompany by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    RelationshipToCompany
  	 * @return a list of {@link haj.com.entity.RelationshipToCompany}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<RelationshipToCompany> findByName(String description) throws Exception {
	 	String hql = "select o from RelationshipToCompany o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<RelationshipToCompany>)super.getList(hql, parameters);
	}
}

