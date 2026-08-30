package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.CompanyUsers;
import haj.com.entity.NonSetaCompanyUsers;
import haj.com.entity.enums.ConfigDocProcessEnum;

public class NonSetaCompanyUsersDAO extends AbstractDAO  {
	
	private static final String leftJoinsNoVar = " " + "left join fetch o.nonSetaCompany left join fetch o.user" + " ";
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all NonSetaCompanyUsers
 	 * @author TechFinium 
 	 * @see    NonSetaCompanyUsers
 	 * @return a list of {@link haj.com.entity.NonSetaCompanyUsers}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NonSetaCompanyUsers> allNonSetaCompanyUsers() throws Exception {
		return (List<NonSetaCompanyUsers>)super.getList("select o from NonSetaCompanyUsers o");
	}

	/**
	 * Get all NonSetaCompanyUsers between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    NonSetaCompanyUsers
 	 * @return a list of {@link haj.com.entity.NonSetaCompanyUsers}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NonSetaCompanyUsers> allNonSetaCompanyUsers(Long from, int noRows) throws Exception {
	 	String hql = "select o from NonSetaCompanyUsers o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<NonSetaCompanyUsers>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    NonSetaCompanyUsers
 	 * @return a {@link haj.com.entity.NonSetaCompanyUsers}
 	 * @throws Exception global exception
 	 */
	public NonSetaCompanyUsers findByKey(Long id) throws Exception {
	 	String hql = "select o from NonSetaCompanyUsers o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (NonSetaCompanyUsers)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find NonSetaCompanyUsers by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    NonSetaCompanyUsers
  	 * @return a list of {@link haj.com.entity.NonSetaCompanyUsers}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NonSetaCompanyUsers> findByName(String description) throws Exception {
	 	String hql = "select o from NonSetaCompanyUsers o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<NonSetaCompanyUsers>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<NonSetaCompanyUsers> findTPContact(Long id, ConfigDocProcessEnum configDocProcessEnum) {
		 String hql = "select o from NonSetaCompanyUsers o" + leftJoinsNoVar + "where o.nonSetaCompany.id = :companyId and o.companyUserType = :configDocProcessEnum and o.designation is not null";
        Map<String, Object> parameters = new Hashtable<String, Object>();
	        parameters.put("companyId", id);
	        parameters.put("configDocProcessEnum", configDocProcessEnum);
	        return (List<NonSetaCompanyUsers>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<NonSetaCompanyUsers> findTPAssessorMod(Long id, ConfigDocProcessEnum configDocProcessEnum) {
		String hql = "select o from NonSetaCompanyUsers o" + leftJoinsNoVar + "where o.nonSetaCompany.id = :companyId and o.companyUserType = :configDocProcessEnum and o.assessorModType is not null";
        Map<String, Object> parameters = new Hashtable<String, Object>();
        parameters.put("companyId", id);
        parameters.put("configDocProcessEnum", configDocProcessEnum);
        return (List<NonSetaCompanyUsers>) super.getList(hql, parameters);
	}
	
	public Integer countTPContact(Long nonSetaCompanyId, ConfigDocProcessEnum configDocProcessEnum) {
		String hql = "select count(o) from NonSetaCompanyUsers o" + leftJoinsNoVar + "where o.nonSetaCompany.id = :nonSetaCompanyId and o.companyUserType = :configDocProcessEnum and o.designation is not null";
        Map<String, Object> parameters = new Hashtable<String, Object>();
        parameters.put("nonSetaCompanyId", nonSetaCompanyId);
        parameters.put("configDocProcessEnum", configDocProcessEnum);
        return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countTPAssessorMod(Long nonSetaCompanyId, ConfigDocProcessEnum configDocProcessEnum) {
		String hql = "select count(o) from NonSetaCompanyUsers o" + leftJoinsNoVar + "where o.nonSetaCompany.id = :nonSetaCompanyId and o.companyUserType = :configDocProcessEnum and o.assessorModType is not null";
        Map<String, Object> parameters = new Hashtable<String, Object>();
        parameters.put("nonSetaCompanyId", nonSetaCompanyId);
        parameters.put("configDocProcessEnum", configDocProcessEnum);
        return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Long findByUserTypeCount(Long userId) throws Exception {
		String hql = "select count(distinct o.nonSetaCompany) from NonSetaCompanyUsers o where o.user.id = :userID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userID", userId);
		return (Long) super.getUniqueResult(hql, parameters);
	}
	
	public Long findByUserTypeAndNonSetaCompanyCount(Long userId, Long nonSetaCompanyId) throws Exception {
		String hql = "select count(distinct o.nonSetaCompany) from NonSetaCompanyUsers o where o.user.id = :userID and o.nonSetaCompany.id = :nonSetaCompanyId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userID", userId);
		parameters.put("nonSetaCompanyId", nonSetaCompanyId);
		return (Long) super.getUniqueResult(hql, parameters);
	}
}

