package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Company;
import haj.com.entity.lookup.CompanyUserPosition;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyUserPositionDAO.
 */
public class CompanyUserPositionDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CompanyUserPosition.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CompanyUserPosition}
	 * @throws Exception global exception
	 * @see    CompanyUserPosition
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUserPosition> allCompanyUserPosition() throws Exception {
		return (List<CompanyUserPosition>)super.getList("select o from CompanyUserPosition o");
	}

	/**
	 * Get all CompanyUserPosition between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.CompanyUserPosition}
	 * @throws Exception global exception
	 * @see    CompanyUserPosition
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUserPosition> allCompanyUserPosition(Long from, int noRows) throws Exception {
	 	String hql = "select o from CompanyUserPosition o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<CompanyUserPosition>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyUserPosition> allCompanyUserPositionNotUsed(Company company) throws Exception {
	 	String hql = "select o from CompanyUserPosition o where o.id not in (select k.position.id from CompanyUsers k where k.position is not null and k.company.id = :companyId)" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", company.getId());	    
		return (List<CompanyUserPosition>)super.getList(hql, parameters);
	}

	
	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.CompanyUserPosition}
	 * @throws Exception global exception
	 * @see    CompanyUserPosition
	 */
	public CompanyUserPosition findByKey(Long id) throws Exception {
	 	String hql = "select o from CompanyUserPosition o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (CompanyUserPosition)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CompanyUserPosition by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.CompanyUserPosition}
	 * @throws Exception global exception
	 * @see    CompanyUserPosition
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUserPosition> findByName(String description) throws Exception {
	 	String hql = "select o from CompanyUserPosition o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<CompanyUserPosition>)super.getList(hql, parameters);
	}
	
	/**
	 * Find unique code.
	 *
	 * @param companyUserPosition the company user position
	 * @return the company user position
	 * @throws Exception the exception
	 */
	public CompanyUserPosition findUniqueCode(CompanyUserPosition companyUserPosition) throws Exception {
	 	String hql = "select o from CompanyUserPosition o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (companyUserPosition.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", companyUserPosition.getId());
	 	}
	   
	    parameters.put("code", companyUserPosition.getCode());
		return (CompanyUserPosition)super.getUniqueResult(hql, parameters);
	}
}

