package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.CompanyLearnersTransfer;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class CompanyLearnersTransferDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CompanyLearnersTransfer
 	 * @author TechFinium 
 	 * @see    CompanyLearnersTransfer
 	 * @return a list of {@link haj.com.entity.CompanyLearnersTransfer}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTransfer> allCompanyLearnersTransfer() throws Exception {
		return (List<CompanyLearnersTransfer>)super.getList("select o from CompanyLearnersTransfer o");
	}

	/**
	 * Get all CompanyLearnersTransfer between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    CompanyLearnersTransfer
 	 * @return a list of {@link haj.com.entity.CompanyLearnersTransfer}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTransfer> allCompanyLearnersTransfer(Long from, int noRows) throws Exception {
	 	String hql = "select o from CompanyLearnersTransfer o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<CompanyLearnersTransfer>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    CompanyLearnersTransfer
 	 * @return a {@link haj.com.entity.CompanyLearnersTransfer}
 	 * @throws Exception global exception
 	 */
	public CompanyLearnersTransfer findByKey(Long id) throws Exception {
	 	String hql = "select o from CompanyLearnersTransfer o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (CompanyLearnersTransfer)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find object by company learner id
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    CompanyLearnersTransfer
 	 * @return List<CompanyLearnersTransfer>
 	 * @throws Exception global exception
 	 */
	public List<CompanyLearnersTransfer> findByCompanyLearnerID(Long id) throws Exception {
	 	String hql = "select o from CompanyLearnersTransfer o where o.companyLearners.id = :id order by o.createDate desc" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (List<CompanyLearnersTransfer>)super.getList(hql, parameters);
	}

	/**
	 * Find CompanyLearnersTransfer by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    CompanyLearnersTransfer
  	 * @return a list of {@link haj.com.entity.CompanyLearnersTransfer}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTransfer> findByName(String description) throws Exception {
	 	String hql = "select o from CompanyLearnersTransfer o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<CompanyLearnersTransfer>)super.getList(hql, parameters);
	}
}

