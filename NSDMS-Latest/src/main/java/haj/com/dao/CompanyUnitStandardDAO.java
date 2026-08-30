package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Company;
import haj.com.entity.CompanyUnitStandard;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyUnitStandardDAO.
 */
public class CompanyUnitStandardDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CompanyUnitStandard.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CompanyUnitStandard}
	 * @throws Exception global exception
	 * @see    CompanyUnitStandard
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUnitStandard> allCompanyUnitStandard() throws Exception {
		return (List<CompanyUnitStandard>)super.getList("select o from CompanyUnitStandard o");
	}

	/**
	 * Get all CompanyUnitStandard between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.CompanyUnitStandard}
	 * @throws Exception global exception
	 * @see    CompanyUnitStandard
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUnitStandard> allCompanyUnitStandard(Long from, int noRows) throws Exception {
	 	String hql = "select o from CompanyUnitStandard o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<CompanyUnitStandard>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.CompanyUnitStandard}
	 * @throws Exception global exception
	 * @see    CompanyUnitStandard
	 */
	public CompanyUnitStandard findByKey(Long id) throws Exception {
	 	String hql = "select o from CompanyUnitStandard o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (CompanyUnitStandard)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CompanyUnitStandard by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.CompanyUnitStandard}
	 * @throws Exception global exception
	 * @see    CompanyUnitStandard
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUnitStandard> findByName(String description) throws Exception {
	 	String hql = "select o from CompanyUnitStandard o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<CompanyUnitStandard>)super.getList(hql, parameters);
	}
	
	/**
	 * Find CompanyUnitStandard by company.
	 *
	 * @author TechFinium
	 * @param company the company
	 * @return a list of {@link haj.com.entity.CompanyUnitStandard}
	 * @throws Exception global exception
	 * @see    CompanyUnitStandard
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUnitStandard> findByCompany(Company company) throws Exception {
	 	String hql = "select o from CompanyUnitStandard o left join fetch o.unitStandard where o.company.id = :companyId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", company.getId());
		return (List<CompanyUnitStandard>)super.getList(hql, parameters);
	}
	
	public List<CompanyUnitStandard>findByTargetClassAndTargetKey(String targetClass,Long targetKey)  throws Exception {
		String hql = "select o from CompanyUnitStandard o left join fetch o.unitStandard where o.targetClass = :targetClass and o. targetKey = :targetKey" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetKey",targetKey);
	    parameters.put("targetClass",targetClass);
		return (List<CompanyUnitStandard>)super.getList(hql, parameters);
	}
	
	public List<CompanyUnitStandard>findByTargetClassAndTargetKeyAndAccept(String targetClass,Long targetKey,Boolean accept)  throws Exception {
		String hql = "select o from CompanyUnitStandard o left join fetch o.unitStandard where o.targetClass = :targetClass and o.targetKey = :targetKey and o.accept =:accept" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetKey",targetKey);
	    parameters.put("targetClass",targetClass);
	    parameters.put("accept",accept);
		return (List<CompanyUnitStandard>)super.getList(hql, parameters);
	}
	
	/**
	 * Find CompanyUnitStandard by company.
	 *
	 * @author TechFinium
	 * @param company the company
	 * @param unitStandards the unit standards
	 * @return a list of {@link haj.com.entity.CompanyUnitStandard}
	 * @throws Exception global exception
	 * @see    CompanyUnitStandard
	 */
	public CompanyUnitStandard findByCompany(Company company, UnitStandards unitStandards) throws Exception {
	 	String hql = "select o from CompanyUnitStandard o left join fetch o.unitStandard where o.company.id = :companyId and o.unitStandard.id = :unitStandardsId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", company.getId());
	    parameters.put("unitStandardsId", unitStandards.getId());
		return (CompanyUnitStandard)super.getUniqueResult(hql, parameters);
	}
	
	public Integer countfindByCompany(Company company, UnitStandards unitStandards, Boolean accept) throws Exception {
	 	String hql = "select count(o) from CompanyUnitStandard o where o.company.id = :companyId and o.unitStandard.id =:unitStandardsId and o.accept =:accept";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", company.getId());
	    parameters.put("unitStandardsId", unitStandards.getId());
	    parameters.put("accept", accept);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}

	public Integer countCompanyUnitStandard(Company company, Boolean accept) throws Exception {
	 	String hql = "select count(o) from CompanyUnitStandard o where o.company.id = :companyId and o.accept =:accept";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", company.getId());
	    parameters.put("accept", accept);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countCompanyUnitStandardAccptedByTargetClassAndKey(String targetClass, Long targetKey, UnitStandards unitStandards, Boolean accept) throws Exception {
	 	String hql = "select count(o) from CompanyUnitStandard o where o.unitStandard.id = :unitStandardsId and o.accept = :accept and o.targetClass = :targetClass  and o.targetKey = :targetKey";
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("unitStandardsId", unitStandards.getId());
	    parameters.put("accept", accept);
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countUnitStandardTargetClassAndKey(String targetClass, Long targetKey, UnitStandards unitStandards) throws Exception {
	 	String hql = "select count(o) from CompanyUnitStandard o where o.unitStandard.id = :unitStandardsId and o.targetClass = :targetClass  and o.targetKey = :targetKey";
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("unitStandardsId", unitStandards.getId());
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
}