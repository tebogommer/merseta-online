package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.QdfCompany;
import haj.com.entity.QdfCompanyUsers;
import haj.com.entity.Users;

public class QdfCompanyUsersDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all QdfCompanyUsers
 	 * @author TechFinium 
 	 * @see    QdfCompanyUsers
 	 * @return a list of {@link haj.com.entity.QdfCompanyUsers}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QdfCompanyUsers> allQdfCompanyUsers() throws Exception {
		return (List<QdfCompanyUsers>)super.getList("select o from QdfCompanyUsers o");
	}

	/**
	 * Get all QdfCompanyUsers between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    QdfCompanyUsers
 	 * @return a list of {@link haj.com.entity.QdfCompanyUsers}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QdfCompanyUsers> allQdfCompanyUsers(Long from, int noRows) throws Exception {
	 	String hql = "select o from QdfCompanyUsers o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<QdfCompanyUsers>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    QdfCompanyUsers
 	 * @return a {@link haj.com.entity.QdfCompanyUsers}
 	 * @throws Exception global exception
 	 */
	public QdfCompanyUsers findByKey(Long id) throws Exception {
	 	String hql = "select o from QdfCompanyUsers o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (QdfCompanyUsers)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find QdfCompanyUsers by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    QdfCompanyUsers
  	 * @return a list of {@link haj.com.entity.QdfCompanyUsers}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QdfCompanyUsers> findByName(String description) throws Exception {
	 	String hql = "select o from QdfCompanyUsers o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<QdfCompanyUsers>)super.getList(hql, parameters);
	}

	public QdfCompanyUsers findByUserAndQdfCompany(Long qdfCompany, Long formUser) {
		String hql = "select o from QdfCompanyUsers o where o.user.id = :formUser and o.qdfCompany.id = :qdfCompany " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("formUser", formUser);
	    parameters.put("qdfCompany", qdfCompany);
		return (QdfCompanyUsers)super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<QdfCompanyUsers> findByQdfCompany(Long qdfCompanyId) { 
		boolean softDelete = true;
		String hql = "select o from QdfCompanyUsers o where o.qdfCompany.id =:qdfCompanyId and o.softDelete = :softDelete " ;
		Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("qdfCompanyId", qdfCompanyId);
	    parameters.put("softDelete", softDelete);
		return (List<QdfCompanyUsers>)super.getList(hql, parameters);
	}
	
	public Integer countUserByQdfCompany(Long qdfCompanyId, Long userId) throws Exception {
	 	String hql = "select count(o) from QdfCompanyUsers o where o.qdfCompany.id = :qdfCompanyId and o.user.id = :userId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("qdfCompanyId", qdfCompanyId);
	    parameters.put("userId", userId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
}

