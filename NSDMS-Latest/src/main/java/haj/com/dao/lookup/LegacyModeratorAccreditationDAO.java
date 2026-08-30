package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyAssessorAccreditation;
import haj.com.entity.lookup.LegacyModeratorAccreditation;
import haj.com.entity.lookup.LegacyModeratorAccreditation;

public class LegacyModeratorAccreditationDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyModeratorAccreditation
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorAccreditation
	 * @return a list of {@link haj.com.entity.LegacyModeratorAccreditation}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorAccreditation> allLegacyModeratorAccreditation() throws Exception {
		return (List<LegacyModeratorAccreditation>) super.getList("select o from LegacyModeratorAccreditation o");
	}

	/**
	 * Get all LegacyModeratorAccreditation between from and noRows
	 * 
	 * @author TechFinium
	 * @param from   the from
	 * @param noRows the no rows
	 * @see LegacyModeratorAccreditation
	 * @return a list of {@link haj.com.entity.LegacyModeratorAccreditation}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorAccreditation> allLegacyModeratorAccreditation(Long from, int noRows) throws Exception {
		String hql = "select o from LegacyModeratorAccreditation o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<LegacyModeratorAccreditation>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	@SuppressWarnings("unchecked")
	public List<LegacyModeratorAccreditation> findByIdNoStatusAndProcessed(String idNo, String status,
			Boolean processed) throws Exception {
		Map<String, Object> parameters = new Hashtable<String, Object>();
		String hql = "select o from LegacyModeratorAccreditation o where o.idNo = :idNo and o.moderatorStatusDesc = :moderatorStatusDesc and (o.processed is null or o.processed = :processed)";
		parameters.put("idNo", idNo);
		parameters.put("moderatorStatusDesc", status);
		parameters.put("processed", processed);
		return (List<LegacyModeratorAccreditation>) super.getList(hql, parameters, 10);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorAccreditation> findRegisteredOrExpiredLegacyAccreditation(String idNo,Boolean processed) throws Exception {
		Map<String, Object> parameters = new Hashtable<String, Object>();
		String hql = "select o from LegacyModeratorAccreditation o where o.idNo = :idNo and (o.moderatorStatusDesc = :registered or o.moderatorStatusDesc = :expired) and (o.processed is null or o.processed = :processed)";
		parameters.put("idNo", idNo);
		parameters.put("registered", "Registered");
		parameters.put("expired", "Expired");
		parameters.put("processed", processed);
		return (List<LegacyModeratorAccreditation>) super.getList(hql, parameters, 10);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorAccreditation> findRegisteredOrExpiredLegacyAccreditationVersionTwo(String idNo,Boolean processed) throws Exception {
		Map<String, Object> parameters = new Hashtable<String, Object>();
		String hql = "select o from LegacyModeratorAccreditation o where o.idNo = :idNo "
				+ "and ( UPPER(o.moderatorStatusDesc) = :registered or UPPER(o.moderatorStatusDesc) = :expired) "
				+ "and (o.processed is null or o.processed = :processed)"
				+ "and o.appearsOnHomeAffairsData = true "
				+ "and o.aliveOnHomeAffairsData = true "
				+ "and o.onPersonsFile = true "
				+ "and o.validRsaIdNumber = true "
				+ "and o.validStatus = true ";
		parameters.put("idNo", idNo);
		parameters.put("registered", "REGISTERED");
		parameters.put("expired", "EXPIRED");
		parameters.put("processed", processed);
		return (List<LegacyModeratorAccreditation>) super.getList(hql, parameters, 10);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorAccreditation> findRegisteredOrExpiredLegacyAccreditationVersionTwoPassport(String idNo,Boolean processed) throws Exception {
		Map<String, Object> parameters = new Hashtable<String, Object>();
		String hql = "select o from LegacyModeratorAccreditation o where UPPER(o.idNo) = :idNo "
				+ "and ( UPPER(o.moderatorStatusDesc) = :registered or UPPER(o.moderatorStatusDesc) = :expired ) "
				+ "and (o.processed is null or o.processed = :processed)"
				+ "and o.onPersonsFileAlternativeId = true "
				+ "and o.validStatus = true ";
		parameters.put("idNo", idNo.trim().toUpperCase());
		parameters.put("registered", "REGISTERED");
		parameters.put("expired", "EXPIRED");
		parameters.put("processed", processed);
		return (List<LegacyModeratorAccreditation>) super.getList(hql, parameters, 10);
	}
	
	/*

				+ "and (o.processed is null or o.processed = :processed) "
				
		parameters.put("idNo", idNo);
		parameters.put("registered", "Registered");
		parameters.put("expired", "Expired");
		parameters.put("processed", processed);
		return (List<LegacyAssessorAccreditation>) super.getList(hql, parameters,10);
	}
	
	 */
	

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id the id
	 * @see LegacyModeratorAccreditation
	 * @return a {@link haj.com.entity.LegacyModeratorAccreditation}
	 * @throws Exception global exception
	 */
	public LegacyModeratorAccreditation findByKey(Long id) throws Exception {
		String hql = "select o from LegacyModeratorAccreditation o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (LegacyModeratorAccreditation) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyModeratorAccreditation by description
	 * 
	 * @author TechFinium
	 * @param description the description
	 * @see LegacyModeratorAccreditation
	 * @return a list of {@link haj.com.entity.LegacyModeratorAccreditation}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorAccreditation> findByName(String description) throws Exception {
		String hql = "select o from LegacyModeratorAccreditation o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<LegacyModeratorAccreditation>) super.getList(hql, parameters);
	}

	public Integer countAllResults() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from LegacyModeratorAccreditation o")).intValue();
	}

	public Integer countAllLegacyModeratorAccreditationNotProcessed() throws Exception {
		String hql = "select count(o) from LegacyModeratorAccreditation o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacyModeratorAccreditation> allLegacyModeratorAccreditationNotProcessed(int numberOfEntries)
			throws Exception {
		String hql = "select o from LegacyModeratorAccreditation o where o.processed = false";
		return (List<LegacyModeratorAccreditation>) super.getList(hql, numberOfEntries);
	}
}
