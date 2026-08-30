package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.LegacyProviderAccreditation;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class LegacyProviderAccreditationDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyProviderAccreditation
 	 * @author TechFinium 
 	 * @see    LegacyProviderAccreditation
 	 * @return a list of {@link haj.com.entity.LegacyProviderAccreditation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderAccreditation> allLegacyProviderAccreditation() throws Exception {
		return (List<LegacyProviderAccreditation>)super.getList("select o from LegacyProviderAccreditation o");
	}

	/**
	 * Get all LegacyProviderAccreditation between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyProviderAccreditation
 	 * @return a list of {@link haj.com.entity.LegacyProviderAccreditation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderAccreditation> allLegacyProviderAccreditation(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyProviderAccreditation o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<LegacyProviderAccreditation>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyProviderAccreditation
 	 * @return a {@link haj.com.entity.LegacyProviderAccreditation}
 	 * @throws Exception global exception
 	 */
	public LegacyProviderAccreditation findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyProviderAccreditation o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyProviderAccreditation)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyProviderAccreditation by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyProviderAccreditation
  	 * @return a list of {@link haj.com.entity.LegacyProviderAccreditation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderAccreditation> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyProviderAccreditation o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyProviderAccreditation>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyProviderAccreditation> searchProvider(String accreditationNo) throws Exception {
	 	String hql = "select o from LegacyProviderAccreditation o where o.accreditationNo = :accreditationNo " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("accreditationNo", accreditationNo);
		return (List<LegacyProviderAccreditation>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyProviderAccreditation> findBySdlNumber(String sdlNumber) throws Exception {
	 	String hql = "select o from LegacyProviderAccreditation o where o.sdlNumber = :sdlNumber " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("sdlNumber", sdlNumber);
		return (List<LegacyProviderAccreditation>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyProviderAccreditation> searchProviderVersionTwo(String accreditationNo) throws Exception {
	 	String hql = "select o from LegacyProviderAccreditation o "
	 			+ " where UPPER(o.accreditationNo) = :accreditationNo "
	 			+ "and ( "
				+ " UPPER(o.providerStatus) = :accFull "
				+ " or UPPER(o.providerStatus) = :accPro "
				+ " or UPPER(o.providerStatus) = :programeApprovalStatus "
				+ ") "
				+ "and o.validStatus = true ";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("accreditationNo", accreditationNo.trim().toUpperCase());
	    parameters.put("accFull", "ACCREDITED - FULL");
	    parameters.put("accPro", "ACCREDITED - PROVISIONAL");
	    parameters.put("programeApprovalStatus", "PROGRAMME APPROVAL");
		return (List<LegacyProviderAccreditation>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyProviderAccreditation o")).intValue();
	}
	
	public int countEntriesByAccNumber(String accreditationNo) throws Exception {
	 	String hql = "select count(o) from LegacyProviderAccreditation o where o.accreditationNo = :accreditationNo ";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("accreditationNo", accreditationNo);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
}

