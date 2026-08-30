package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.HostingCompanyEmployeeTemplates;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyEmployeeTemplatesDAO.
 */
public class HostingCompanyEmployeeTemplatesDAO extends AbstractDAO  {

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
	public List<HostingCompanyEmployeeTemplates> allHostingCompanyEmployeeTemplates() throws Exception {
		return (List<HostingCompanyEmployeeTemplates>)super.getList("select o from HostingCompanyEmployeeTemplates o " + leftJoins);
	}
	
	/**
	 * Find by key.
	 *
	 * @param id the id
	 * @return the hosting company templates
	 * @throws Exception the exception
	 */
	public HostingCompanyEmployeeTemplates findByKey(Long id) throws Exception {
	 	String hql = "select o from HostingCompanyEmployeeTemplates o"+leftJoins+" where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (HostingCompanyEmployeeTemplates)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by name.
	 *
	 * @param description the description
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyEmployeeTemplates> findByName(String description) throws Exception {
	 	String hql = "select o from HostingCompanyEmployeeTemplates o"+leftJoins+" where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<HostingCompanyEmployeeTemplates>)super.getList(hql, parameters);
	}

	/**
	 * Find by company.
	 *
	 * @param hostingCompanyId the hosting company id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyEmployeeTemplates> findByCompany(Long hostingCompanyId) throws Exception {
	 	String hql = "select o from HostingCompanyEmployeeTemplates o"+leftJoins+"where o.hostingCompany.id = :hostingCompanyId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyId", hostingCompanyId);
		return (List<HostingCompanyEmployeeTemplates>)super.getList(hql, parameters);
	}
	
	/**
	 * Find roots.
	 *
	 * @param hostingCompanyId the hosting company id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyEmployeeTemplates> findRoots(Long hostingCompanyId) throws Exception {
	 	String hql = "select o from HostingCompanyEmployeeTemplates o where o.hostingCompany.id = :hostingCompanyId and o.parentTemplate is null" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyId", hostingCompanyId);
		return (List<HostingCompanyEmployeeTemplates>)super.getList(hql, parameters);
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
	public List<HostingCompanyEmployeeTemplates> findByParent(Long hostingCompanyId, Long parentId) throws Exception {
	 	String hql = "select o from HostingCompanyEmployeeTemplates o"+leftJoins+" where o.hostingCompany.id = :hostingCompanyId and o.parentTemplate.id =:parentId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyId", hostingCompanyId);
	    parameters.put("parentId", parentId);
		return (List<HostingCompanyEmployeeTemplates>)super.getList(hql, parameters);
	}
	
	/**
	 * Find roots not in B type.
	 *
	 * @param hostingCompanyId the hosting company id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyEmployeeTemplates> findRootsNotInBType(Long hostingCompanyId) throws Exception {
	 	String hql = "select o from HostingCompanyEmployeeTemplates o "+leftJoins+" where o.hostingCompany.id = :hostingCompanyId and o.parentTemplate is null" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyId", hostingCompanyId);
		return (List<HostingCompanyEmployeeTemplates>)super.getList(hql, parameters);
	}
	
// 	Original copy of method above: findRootsNotInBType
//	@SuppressWarnings("unchecked")
//	public List<HostingCompanyEmployeeTemplates> findRootsNotInBType(Long hostingCompanyId, Long businessTypeId) throws Exception {
//	 	String hql = "select o from HostingCompanyEmployeeTemplates o "+leftJoins+" where o.hostingCompany.id = :hostingCompanyId and o.parentHostingCompanyEmployeeTemplates is null and o.businessType is null"
//	 			+ " and o.id not in (select x.originalHostingCompanyEmployeeTemplates.id from HostingCompanyEmployeeTemplates x where x.businessType.id = :businessTypeId)" ;
//	    Map<String, Object> parameters = new Hashtable<String, Object>();
//	    parameters.put("hostingCompanyId", hostingCompanyId);
//	    parameters.put("businessTypeId", businessTypeId);
//		return (List<HostingCompanyEmployeeTemplates>)super.getList(hql, parameters);
//	}
	
// 	Original copy of method: findRoots by business type	
//	@SuppressWarnings("unchecked")
//	public List<HostingCompanyEmployeeTemplates> findRoots(Long hostingCompanyId, Long businessTypeId)  throws Exception {
//	 	String hql = "select o from HostingCompanyEmployeeTemplates o"+leftJoins+" where o.hostingCompany.id = :hostingCompanyId and o.parentHostingCompanyEmployeeTemplates is null and o.businessType.id = :businessTypeId" ;
//	    Map<String, Object> parameters = new Hashtable<String, Object>();
//	    parameters.put("hostingCompanyId", hostingCompanyId);
//	    parameters.put("businessTypeId", businessTypeId);
//		return (List<HostingCompanyEmployeeTemplates>)super.getList(hql, parameters);
//	}
	
//	concept of business type not required	
//	@SuppressWarnings("unchecked")
//	public List<HostingCompanyEmployeeTemplates> findByBusinessType(Long hostingCompanyId, Long businessTypeId)  throws Exception {
//	 	String hql = "select o from HostingCompanyEmployeeTemplates o"+leftJoins+" where o.hostingCompany.id = :hostingCompanyId  and o.businessType.id = :businessTypeId" ;
//	    Map<String, Object> parameters = new Hashtable<String, Object>();
//	    parameters.put("hostingCompanyId", hostingCompanyId);
//	    parameters.put("businessTypeId", businessTypeId);
//		return (List<HostingCompanyEmployeeTemplates>)super.getList(hql, parameters);
//	}
	
	

	
//	concept of business type not required
//	@SuppressWarnings("unchecked")
//	public List<HostingCompanyEmployeeTemplates> findByParent(Long hostingCompanyId, Long parentId, Long businessTypeId) {
//	 	String hql = "select o from HostingCompanyEmployeeTemplates o"+leftJoins+" where o.hostingCompany.id = :hostingCompanyId and o.parentHostingCompanyEmployeeTemplates.id =:parentId and o.businessType = :businessTypeId" ;
//	    Map<String, Object> parameters = new Hashtable<String, Object>();
//	    parameters.put("hostingCompanyId", hostingCompanyId);
//	    parameters.put("parentId", parentId);
//	    parameters.put("businessTypeId", businessTypeId);
//		return (List<HostingCompanyEmployeeTemplates>)super.getList(hql, parameters);
//	}

/*
	@SuppressWarnings("unchecked")
	public List<HostingCompanyEmployeeTemplates> byField(long key) throws Exception  {
		String hql = "select o from HostingCompanyEmployeeTemplates o where o.key = :key";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("key", key);
	    return (List<HostingCompanyEmployeeTemplates>)super.getList(hql, parameters);
	}
*/

	



}

