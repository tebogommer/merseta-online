package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Company;
import haj.com.entity.lookup.SDFType;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class SDFTypeDAO.
 */
public class SDFTypeDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SDFType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SDFType}
	 * @throws Exception global exception
	 * @see    SDFType
	 */
	@SuppressWarnings("unchecked")
	public List<SDFType> allSDFType() throws Exception {
		return (List<SDFType>)super.getList("select o from SDFType o");
	}

	/**
	 * Get all SDFType between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.SDFType}
	 * @throws Exception global exception
	 * @see    SDFType
	 */
	@SuppressWarnings("unchecked")
	public List<SDFType> allSDFType(Long from, int noRows) throws Exception {
	 	String hql = "select o from SDFType o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SDFType>)super.getList(hql, parameters,from.intValue(),noRows);
	}
	
	/**
	 * Get all SDFType not used for company.
	 *
	 * @author TechFinium
	 * @param company the company
	 * @return a list of {@link haj.com.entity.SDFType}
	 * @throws Exception global exception
	 * @see    SDFType
	 */
	@SuppressWarnings("unchecked")
	public List<SDFType> allSDFTypeNotUsed(Company company) throws Exception {
	 	String hql = "select o from SDFType o where o.id not in (select x.sdfType.id from SDFCompany x where x.company.id = :companyId)" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", company.getId());
		return (List<SDFType>)super.getList(hql, parameters);
	}
	
	/**
	 * Get all SDFType not used for company.
	 *
	 * @author TechFinium
	 * @param company the company
	 * @return a list of {@link haj.com.entity.SDFType}
	 * @throws Exception global exception
	 * @see    SDFType
	 */
	@SuppressWarnings("unchecked")
	public List<SDFType> allSDFTypeNotUsedAndNoReplacementSDF(Company company) throws Exception {
	 	String hql = "select o from SDFType o where o.id not in (select x.sdfType.id from SDFCompany x where x.company.id = :companyId) and o.id <> 13" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", company.getId());
		return (List<SDFType>)super.getList(hql, parameters);
	}
	
	/**
	 * All SDF type not used count.
	 *
	 * @param company the company
	 * @return the long
	 * @throws Exception the exception
	 */
	public Long allSDFTypeUsedCount(Company company, long yesNoLookup) throws Exception {
		String hql = "select count(o) from SDFType o where o.id not in (select x.sdfType.id from SDFCompany x where x.company.id = :companyId and x.sdfType.id = :signId) and o.id = :signId";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", company.getId());
		parameters.put("signId", yesNoLookup);
		return (Long)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * All SDF type not used count.
	 *
	 * @param company the company
	 * @return the long
	 * @throws Exception the exception
	 */
	public Long allSDFTypeUsedCount(Company company) throws Exception {
		String hql = "select count(o) from SDFType o where o.id not in (select x.sdfType.id from SDFCompany x where x.company.id = :companyId and x.sdfType.signOnRecognition.id = :signId) and o.signOnRecognition.id = :signId";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", company.getId());
		parameters.put("signId", company.getRecognitionAgreement().getId());
		return (Long)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SDFType}
	 * @throws Exception global exception
	 * @see    SDFType
	 */
	public SDFType findByKey(Long id) throws Exception {
	 	String hql = "select o from SDFType o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SDFType)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SDFType by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.SDFType}
	 * @throws Exception global exception
	 * @see    SDFType
	 */
	@SuppressWarnings("unchecked")
	public List<SDFType> findByName(String description) throws Exception {
	 	String hql = "select o from SDFType o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SDFType>)super.getList(hql, parameters);
	}
	
	/**
	 * Find object bycode.
	 *
	 * @author TechFinium
	 * @param sDFType the s DF type
	 * @return a {@link haj.com.entity.SDFType}
	 * @throws Exception global exception
	 * @see    SDFType
	 */
	
    public SDFType findUniqueCode(SDFType sDFType) throws Exception {
	 	String hql = "select o from SDFType o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (sDFType.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", sDFType.getId());
	 	}
	   
	    parameters.put("code", sDFType.getCode());
		return (SDFType)super.getUniqueResult(hql, parameters);
	}
}

