package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.CompanyLearnersChange;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class CompanyLearnersChangeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CompanyLearnersChange
 	 * @author TechFinium 
 	 * @see    CompanyLearnersChange
 	 * @return a list of {@link haj.com.entity.CompanyLearnersChange}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersChange> allCompanyLearnersChange() throws Exception {
		return (List<CompanyLearnersChange>)super.getList("select o from CompanyLearnersChange o");
	}

	/**
	 * Get all CompanyLearnersChange between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    CompanyLearnersChange
 	 * @return a list of {@link haj.com.entity.CompanyLearnersChange}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersChange> allCompanyLearnersChange(Long from, int noRows) throws Exception {
	 	String hql = "select o from CompanyLearnersChange o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<CompanyLearnersChange>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    CompanyLearnersChange
 	 * @return a {@link haj.com.entity.CompanyLearnersChange}
 	 * @throws Exception global exception
 	 */
	public CompanyLearnersChange findByKey(Long id) throws Exception {
	 	String hql = "select o from CompanyLearnersChange o left join fetch o.companyLearners cl left join fetch cl.company where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (CompanyLearnersChange)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CompanyLearnersChange by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    CompanyLearnersChange
  	 * @return a list of {@link haj.com.entity.CompanyLearnersChange}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersChange> findByName(String description) throws Exception {
	 	String hql = "select o from CompanyLearnersChange o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<CompanyLearnersChange>)super.getList(hql, parameters);
	}
}

