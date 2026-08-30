package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyAssessorAccreditation;

public class LegacyAssessorAccreditationDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyAssessorAccreditation
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorAccreditation
	 * @return a list of {@link haj.com.entity.LegacyAssessorAccreditation}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorAccreditation> findByIdNo(String idNo) throws Exception {
		Map<String, Object> parameters = new Hashtable<String, Object>();
		String hql="select o from LegacyAssessorAccreditation o where o.idNo = :idNo ";
		parameters.put("idNo", idNo);
		return (List<LegacyAssessorAccreditation>) super.getList(hql, parameters,10);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorAccreditation> findByIdRegistrationNumber(String assessorRegNo) throws Exception {
		Map<String, Object> parameters = new Hashtable<String, Object>();
		String hql="select o from LegacyAssessorAccreditation o where o.assessorRegNo = :assessorRegNo ";
		parameters.put("assessorRegNo", assessorRegNo);
		return (List<LegacyAssessorAccreditation>) super.getList(hql, parameters,10);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorAccreditation> findByIdNoStatusAndProcessed(String idNo,String status,Boolean processed) throws Exception {
		Map<String, Object> parameters = new Hashtable<String, Object>();
		String hql="select o from LegacyAssessorAccreditation o where o.idNo = :idNo and o.assessorStatusDesc = :assessorStatusDesc and (o.processed is null or o.processed = :processed)";
		parameters.put("idNo", idNo);
		parameters.put("assessorStatusDesc", status);
		parameters.put("processed", processed);
		return (List<LegacyAssessorAccreditation>) super.getList(hql, parameters,10);
	}
	

	@SuppressWarnings("unchecked")
	public List<LegacyAssessorAccreditation> findRegisteredOrExpiredLegacyAccreditation(String idNo,Boolean processed) throws Exception {
		Map<String, Object> parameters = new Hashtable<String, Object>();
		String hql="select o from LegacyAssessorAccreditation o where o.idNo = :idNo and (o.assessorStatusDesc = :registered or o.assessorStatusDesc = :expired) and (o.processed is null or o.processed = :processed)";
		parameters.put("idNo", idNo);
		parameters.put("registered", "Registered");
		parameters.put("expired", "Expired");
		parameters.put("processed", processed);
		return (List<LegacyAssessorAccreditation>) super.getList(hql, parameters,10);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorAccreditation> findRegisteredOrExpiredLegacyAccreditationVersionTwo(String idNo,Boolean processed) throws Exception {
		Map<String, Object> parameters = new Hashtable<String, Object>();
		String hql="select o from LegacyAssessorAccreditation o where o.idNo = :idNo "
				+ "and ( "
				+ " UPPER(o.assessorStatusDesc) = :registered "
				+ " or UPPER(o.assessorStatusDesc) = :expired "
				+ " or UPPER(o.assessorStatusDesc) = :extended  "
				+ " or UPPER(o.assessorStatusDesc) = :reregistered  "
				+ ") "
				+ "and (o.processed is null or o.processed = :processed) "
				+ "and o.appearsOnHomeAffairsData = true "
				+ "and o.aliveOnHomeAffairsData = true "
				+ "and o.onPersonsFile = true "
				+ "and o.validRsaIdNumber = true "
				+ "and o.validStatus = true ";
		parameters.put("idNo", idNo);
		parameters.put("registered", "REGISTERED");
		parameters.put("expired", "EXPIRED");
		parameters.put("extended", "EXTENDED");
		parameters.put("reregistered", "REREGISTERED");
		
		parameters.put("processed", processed);
		return (List<LegacyAssessorAccreditation>) super.getList(hql, parameters,10);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorAccreditation> findRegisteredOrExpiredLegacyAccreditationVersionTwoPassport(String idNo, Boolean processed) throws Exception {
		Map<String, Object> parameters = new Hashtable<String, Object>();
		String hql="select o from LegacyAssessorAccreditation o where UPPER(o.idNo) = :idNo "
				+ "and ( "
				+ " UPPER(o.assessorStatusDesc) = :registered "
				+ " or UPPER(o.assessorStatusDesc) = :expired "
				+ " or UPPER(o.assessorStatusDesc) = :extended  "
				+ " or UPPER(o.assessorStatusDesc) = :reregistered  "
				+ ") "
				+ "and (o.processed is null or o.processed = :processed) "
				+ "and o.onPersonsFileAlternativeId = true "
				+ "and o.validStatus = true ";
		parameters.put("idNo", idNo.trim().toUpperCase());
		parameters.put("registered", "REGISTERED");
		parameters.put("expired", "EXPIRED");
		parameters.put("extended", "EXTENDED");
		parameters.put("reregistered", "REREGISTERED");
		parameters.put("processed", processed);
		return (List<LegacyAssessorAccreditation>) super.getList(hql, parameters, 10);
	}
	
	
	
	/**
	 * Get all LegacyAssessorAccreditation
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorAccreditation
	 * @return a list of {@link haj.com.entity.LegacyAssessorAccreditation}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorAccreditation> allLegacyAssessorAccreditation() throws Exception {
		return (List<LegacyAssessorAccreditation>) super.getList("select o from LegacyAssessorAccreditation o");
	}

	/**
	 * Get all LegacyAssessorAccreditation between from and noRows
	 * 
	 * @author TechFinium
	 * @param from   the from
	 * @param noRows the no rows
	 * @see LegacyAssessorAccreditation
	 * @return a list of {@link haj.com.entity.LegacyAssessorAccreditation}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorAccreditation> allLegacyAssessorAccreditation(Long from, int noRows) throws Exception {
		String hql = "select o from LegacyAssessorAccreditation o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<LegacyAssessorAccreditation>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id the id
	 * @see LegacyAssessorAccreditation
	 * @return a {@link haj.com.entity.LegacyAssessorAccreditation}
	 * @throws Exception global exception
	 */
	public LegacyAssessorAccreditation findByKey(Long id) throws Exception {
		String hql = "select o from LegacyAssessorAccreditation o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (LegacyAssessorAccreditation) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyAssessorAccreditation by description
	 * 
	 * @author TechFinium
	 * @param description the description
	 * @see LegacyAssessorAccreditation
	 * @return a list of {@link haj.com.entity.LegacyAssessorAccreditation}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorAccreditation> findByName(String description) throws Exception {
		String hql = "select o from LegacyAssessorAccreditation o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<LegacyAssessorAccreditation>) super.getList(hql, parameters);
	}

	public Integer countAllResults() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from LegacyAssessorAccreditation o")).intValue();
	}
	
    
    public Integer countAllLegacyAssessorAccreditationNotProcessed() throws Exception {
        String hql = "select count(o) from LegacyAssessorAccreditation o where o.processed = false";
        return ((Long) super.getUniqueResult(hql)).intValue();
    }

    @SuppressWarnings("unchecked")
    public List<LegacyAssessorAccreditation> allLegacyAssessorAccreditationNotProcessed(int numberOfEntries) throws Exception {
        String hql = "select o from LegacyAssessorAccreditation o where o.processed = false";
        return (List<LegacyAssessorAccreditation>) super.getList(hql, numberOfEntries);
    }
    
    // count all entries by processed value
 	public Integer countAllLegacyAssessorAccreditationByProcessedValue(Boolean value) throws Exception {
 	 	String hql = "select count(o) from LegacyAssessorAccreditation o where o.processed = :value" ;
 	 	Map<String, Object> parameters = new Hashtable<String, Object>();
 	    parameters.put("value", value);
 		return ((Long)super.getUniqueResult(hql,parameters)).intValue();
 	}
 	
 	// count all on appearsOnHomeAffairsData
  	public Integer countAllLegacyAssessorAccreditationByAppearsOnHomeAffairsDataValue(Boolean value) throws Exception {
  	 	String hql = "select count(o) from LegacyAssessorAccreditation o where o.appearsOnHomeAffairsData = :value and o.processed = true";
  	 	Map<String, Object> parameters = new Hashtable<String, Object>();
  	    parameters.put("value", value);
  		return ((Long)super.getUniqueResult(hql,parameters)).intValue();
  	}
 	
 // count all ValidRSAID
   	public Integer countAllLegacyAssessorAccreditationByValidRSAIDValue(Boolean value) throws Exception {
   	 	String hql = "select count(o) from LegacyAssessorAccreditation o where o.validRsaIdNumber = :value and o.processed = true";
   	 	Map<String, Object> parameters = new Hashtable<String, Object>();
   	    parameters.put("value", value);
   		return ((Long)super.getUniqueResult(hql,parameters)).intValue();
   	}
}
