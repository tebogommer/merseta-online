package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.OfoQualificationLink;

public class OfoQualificationLinkDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all OfoQualificationLink
 	 * @author TechFinium 
 	 * @see    OfoQualificationLink
 	 * @return a list of {@link haj.com.entity.OfoQualificationLink}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<OfoQualificationLink> allOfoQualificationLink() throws Exception {
		return (List<OfoQualificationLink>)super.getList("select o from OfoQualificationLink o");
	}

	/**
	 * Get all OfoQualificationLink between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    OfoQualificationLink
 	 * @return a list of {@link haj.com.entity.OfoQualificationLink}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<OfoQualificationLink> allOfoQualificationLink(Long from, int noRows) throws Exception {
	 	String hql = "select o from OfoQualificationLink o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<OfoQualificationLink>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    OfoQualificationLink
 	 * @return a {@link haj.com.entity.OfoQualificationLink}
 	 * @throws Exception global exception
 	 */
	public OfoQualificationLink findByKey(Long id) throws Exception {
	 	String hql = "select o from OfoQualificationLink o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (OfoQualificationLink)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find OfoQualificationLink by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    OfoQualificationLink
  	 * @return a list of {@link haj.com.entity.OfoQualificationLink}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<OfoQualificationLink> findByName(String description) throws Exception {
	 	String hql = "select o from OfoQualificationLink o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<OfoQualificationLink>)super.getList(hql, parameters);
	}
	
	public Integer countFindByOfoQualificationId(Long ofoCodeId, Long qualificationId, Long entityId) throws Exception {
	 	String hql = "select count(o) from OfoQualificationLink o where "
	 			+ "o.ofoCodes.id = :ofoCodeId and " 
	 			+ "o.qualification.id = :qualificationId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("ofoCodeId", ofoCodeId);
	    parameters.put("qualificationId", qualificationId);
	    if (entityId != null) {
	    	hql += " and o.id <> :entityId";
	    	parameters.put("entityId", entityId);
		}
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
}

