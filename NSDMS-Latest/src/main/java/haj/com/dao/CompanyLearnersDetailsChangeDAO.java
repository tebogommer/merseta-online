package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.CompanyLearnersDetailsChange;
import haj.com.entity.enums.ApprovalEnum;

public class CompanyLearnersDetailsChangeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CompanyLearnersDetailsChange
 	 * @author TechFinium 
 	 * @see    CompanyLearnersDetailsChange
 	 * @return a list of {@link haj.com.entity.CompanyLearnersDetailsChange}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersDetailsChange> allCompanyLearnersDetailsChange() throws Exception {
		return (List<CompanyLearnersDetailsChange>)super.getList("select o from CompanyLearnersDetailsChange o");
	}

	/**
	 * Get all CompanyLearnersDetailsChange between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    CompanyLearnersDetailsChange
 	 * @return a list of {@link haj.com.entity.CompanyLearnersDetailsChange}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersDetailsChange> allCompanyLearnersDetailsChange(Long from, int noRows) throws Exception {
	 	String hql = "select o from CompanyLearnersDetailsChange o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<CompanyLearnersDetailsChange>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    CompanyLearnersDetailsChange
 	 * @return a {@link haj.com.entity.CompanyLearnersDetailsChange}
 	 * @throws Exception global exception
 	 */
	public CompanyLearnersDetailsChange findByKey(Long id) throws Exception {
	 	String hql = "select o from CompanyLearnersDetailsChange o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (CompanyLearnersDetailsChange)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CompanyLearnersDetailsChange by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    CompanyLearnersDetailsChange
  	 * @return a list of {@link haj.com.entity.CompanyLearnersDetailsChange}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersDetailsChange> findByName(String description) throws Exception {
	 	String hql = "select o from CompanyLearnersDetailsChange o where o.description like  :description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<CompanyLearnersDetailsChange>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersDetailsChange> findByUsers(Long userID) throws Exception {
	 	String hql = "select o from CompanyLearnersDetailsChange o where o.user.id = :userID order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userID",userID);
		return (List<CompanyLearnersDetailsChange>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersDetailsChange> findByUsersAndStatus(Long userID, ApprovalEnum status) throws Exception {
	 	String hql = "select o from CompanyLearnersDetailsChange o where o.user.id = :userID and o.status = :status " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userID",userID);
	    parameters.put("status",status);
		return (List<CompanyLearnersDetailsChange>)super.getList(hql, parameters);
	}
}

