package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.HostingCompanyTemplates;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyTemplatesDAO.
 */
public class HostingCompanyTemplatesDAO extends AbstractDAO  {

	/** The Constant leftJoins. */
	private static final String leftJoins = " " 
			+ "left join fetch o.user tu "
			+ "left join fetch o.hostingCompany thc "
 			+ "left join fetch o.parentTemplate tp "
			+ "left join fetch o.originalTemplate to " 
			+ " ";
	
	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * All hosting company templates.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyTemplates> allHostingCompanyTemplates() throws Exception {
		return (List<HostingCompanyTemplates>)super.getList("select o from HostingCompanyTemplates o " + leftJoins);
	}
	
	/**
	 * Find by key.
	 *
	 * @param id the id
	 * @return the hosting company templates
	 * @throws Exception the exception
	 */
	public HostingCompanyTemplates findByKey(Long id) throws Exception {
	 	String hql = "select o from HostingCompanyTemplates o"+leftJoins+" where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (HostingCompanyTemplates)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by name.
	 *
	 * @param description the description
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyTemplates> findByName(String description) throws Exception {
	 	String hql = "select o from HostingCompanyTemplates o"+leftJoins+" where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<HostingCompanyTemplates>)super.getList(hql, parameters);
	}

	/**
	 * Find by company.
	 *
	 * @param hostingCompanyId the hosting company id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyTemplates> findByCompany(Long hostingCompanyId) throws Exception {
	 	String hql = "select o from HostingCompanyTemplates o"+leftJoins+"where o.hostingCompany.id = :hostingCompanyId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyId", hostingCompanyId);
		return (List<HostingCompanyTemplates>)super.getList(hql, parameters);
	}
	
	/**
	 * Find roots.
	 *
	 * @param hostingCompanyId the hosting company id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyTemplates> findRoots(Long hostingCompanyId) throws Exception {
	 	String hql = "select o from HostingCompanyTemplates o where o.hostingCompany.id = :hostingCompanyId and o.parentTemplate is null" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyId", hostingCompanyId);
		return (List<HostingCompanyTemplates>)super.getList(hql, parameters);
	}
	
	/**
	 * Find by parent.
	 *
	 * @param hostingCompanyId the hosting company id
	 * @param parentId the parent id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyTemplates> findByParent(Long hostingCompanyId, Long parentId) throws Exception {
	 	String hql = "select o from HostingCompanyTemplates o"+leftJoins+" where o.hostingCompany.id = :hostingCompanyId and o.parentTemplate.id =:parentId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyId", hostingCompanyId);
	    parameters.put("parentId", parentId);
		return (List<HostingCompanyTemplates>)super.getList(hql, parameters);
	}
	
	/**
	 * Find roots not in B type.
	 *
	 * @param hostingCompanyId the hosting company id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyTemplates> findRootsNotInBType(Long hostingCompanyId) throws Exception {
	 	String hql = "select o from HostingCompanyTemplates o "+leftJoins+" where o.hostingCompany.id = :hostingCompanyId and o.parentTemplate is null" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyId", hostingCompanyId);
		return (List<HostingCompanyTemplates>)super.getList(hql, parameters);
	}
	
// 	Original copy of method above: findRootsNotInBType
//	@SuppressWarnings("unchecked")
//	public List<HostingCompanyTemplates> findRootsNotInBType(Long hostingCompanyId, Long businessTypeId) throws Exception {
//	 	String hql = "select o from HostingCompanyTemplates o "+leftJoins+" where o.hostingCompany.id = :hostingCompanyId and o.parentHostingCompanyTemplates is null and o.businessType is null"
//	 			+ " and o.id not in (select x.originalHostingCompanyTemplates.id from HostingCompanyTemplates x where x.businessType.id = :businessTypeId)" ;
//	    Map<String, Object> parameters = new Hashtable<String, Object>();
//	    parameters.put("hostingCompanyId", hostingCompanyId);
//	    parameters.put("businessTypeId", businessTypeId);
//		return (List<HostingCompanyTemplates>)super.getList(hql, parameters);
//	}
	
// 	Original copy of method: findRoots by business type	
//	@SuppressWarnings("unchecked")
//	public List<HostingCompanyTemplates> findRoots(Long hostingCompanyId, Long businessTypeId)  throws Exception {
//	 	String hql = "select o from HostingCompanyTemplates o"+leftJoins+" where o.hostingCompany.id = :hostingCompanyId and o.parentHostingCompanyTemplates is null and o.businessType.id = :businessTypeId" ;
//	    Map<String, Object> parameters = new Hashtable<String, Object>();
//	    parameters.put("hostingCompanyId", hostingCompanyId);
//	    parameters.put("businessTypeId", businessTypeId);
//		return (List<HostingCompanyTemplates>)super.getList(hql, parameters);
//	}
	
//	concept of business type not required	
//	@SuppressWarnings("unchecked")
//	public List<HostingCompanyTemplates> findByBusinessType(Long hostingCompanyId, Long businessTypeId)  throws Exception {
//	 	String hql = "select o from HostingCompanyTemplates o"+leftJoins+" where o.hostingCompany.id = :hostingCompanyId  and o.businessType.id = :businessTypeId" ;
//	    Map<String, Object> parameters = new Hashtable<String, Object>();
//	    parameters.put("hostingCompanyId", hostingCompanyId);
//	    parameters.put("businessTypeId", businessTypeId);
//		return (List<HostingCompanyTemplates>)super.getList(hql, parameters);
//	}
	
	

	
//	concept of business type not required
//	@SuppressWarnings("unchecked")
//	public List<HostingCompanyTemplates> findByParent(Long hostingCompanyId, Long parentId, Long businessTypeId) {
//	 	String hql = "select o from HostingCompanyTemplates o"+leftJoins+" where o.hostingCompany.id = :hostingCompanyId and o.parentHostingCompanyTemplates.id =:parentId and o.businessType = :businessTypeId" ;
//	    Map<String, Object> parameters = new Hashtable<String, Object>();
//	    parameters.put("hostingCompanyId", hostingCompanyId);
//	    parameters.put("parentId", parentId);
//	    parameters.put("businessTypeId", businessTypeId);
//		return (List<HostingCompanyTemplates>)super.getList(hql, parameters);
//	}

/*
	@SuppressWarnings("unchecked")
	public List<HostingCompanyTemplates> byField(long key) throws Exception  {
		String hql = "select o from HostingCompanyTemplates o where o.key = :key";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("key", key);
	    return (List<HostingCompanyTemplates>)super.getList(hql, parameters);
	}
*/

	



}

