package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.sars.SarsLeviesPaid;

// TODO: Auto-generated Javadoc
/**
 * The Class SarsLeviesPaidDAO.
 */
public class SarsLeviesPaidDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SarsLeviesPaid.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.sars.SarsLeviesPaid}
	 * @throws Exception global exception
	 * @see    SarsLeviesPaid
	 */
	@SuppressWarnings("unchecked")
	public List<SarsLeviesPaid> allSarsLeviesPaid() throws Exception {
		return (List<SarsLeviesPaid>)super.getList("select o from SarsLeviesPaid o");
	}
	
	@SuppressWarnings("unchecked")
	public List<SarsLeviesPaid> allSarsLeviesPaidWithNoLevyFileLink() throws Exception {
		return (List<SarsLeviesPaid>)super.getList("select o from SarsLeviesPaid o where o.sarsFiles.id is null");
	}

	/**
	 * Get all SarsLeviesPaid between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.sars.SarsLeviesPaid}
	 * @throws Exception global exception
	 * @see    SarsLeviesPaid
	 */
	@SuppressWarnings("unchecked")
	public List<SarsLeviesPaid> allSarsLeviesPaid(Long from, int noRows) throws Exception {
	 	String hql = "select o from SarsLeviesPaid o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SarsLeviesPaid>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.sars.SarsLeviesPaid}
	 * @throws Exception global exception
	 * @see    SarsLeviesPaid
	 */
	public SarsLeviesPaid findByKey(Long id) throws Exception {
	 	String hql = "select o from SarsLeviesPaid o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SarsLeviesPaid)super.getUniqueResult(hql, parameters);
	}

	public SarsLeviesPaid findBySarsFile(Long id) throws Exception {
	 	String hql = "select o from SarsLeviesPaid o where o.sarsFiles.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SarsLeviesPaid)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find by sars levy file.
	 *
	 * @param levyFile the levy file
	 * @return the sars levies paid
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SarsLeviesPaid> findBySarsLevyFile(String levyFile) throws Exception {
	 	String hql = "select o from SarsLeviesPaid o where o.levyFile = :levyFile " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("levyFile", levyFile);
		return (List<SarsLeviesPaid>)super.getList(hql, parameters);
	}
	
	/**
	 * Find SarsLeviesPaid by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.sars.SarsLeviesPaid}
	 * @throws Exception global exception
	 * @see    SarsLeviesPaid
	 */
	@SuppressWarnings("unchecked")
	public List<SarsLeviesPaid> findByName(String description) throws Exception {
	 	String hql = "select o from SarsLeviesPaid o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SarsLeviesPaid>)super.getList(hql, parameters);
	}
	
	
	/*  Total  for scheme year
select sum(a.mandatoryLevy) 
from SarsLevyDetails a, SarsFiles b
where a.sarsFiles.id = b.id
and b.forMonth between '2016-04-01' and '2017-03-31'
	 */
}

