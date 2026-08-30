package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.HomeAffairs;

public class HomeAffairsDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all HomeAffairs
	 * 
	 * @author TechFinium
	 * @see HomeAffairs
	 * @return a list of {@link haj.com.entity.HomeAffairs}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<HomeAffairs> allHomeAffairs() throws Exception {
		return (List<HomeAffairs>) super.getList("select o from HomeAffairs o");
	}

	/**
	 * Get all HomeAffairs between from and noRows
	 * 
	 * @author TechFinium
	 * @param from   the from
	 * @param noRows the no rows
	 * @see HomeAffairs
	 * @return a list of {@link haj.com.entity.HomeAffairs}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<HomeAffairs> allHomeAffairs(Long from, int noRows) throws Exception {
		String hql = "select o from HomeAffairs o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<HomeAffairs>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id the id
	 * @see HomeAffairs
	 * @return a {@link haj.com.entity.HomeAffairs}
	 * @throws Exception global exception
	 */
	public HomeAffairs findByKey(Long id) throws Exception {
		String hql = "select o from HomeAffairs o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (HomeAffairs) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find HomeAffairs by description
	 * 
	 * @author TechFinium
	 * @param description the description
	 * @see HomeAffairs
	 * @return a list of {@link haj.com.entity.HomeAffairs}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<HomeAffairs> findByName(String description) throws Exception {
		String hql = "select o from HomeAffairs o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<HomeAffairs>) super.getList(hql, parameters);
	}

	public Integer countAllResults() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from HomeAffairs o")).intValue();
	}

	public Integer findByDhaIdNumber(String idNo) throws Exception {
		String hql = "select count(o) from HomeAffairs o where o.dhaIDNumber = :idNo ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("idNo", idNo.trim());
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer findByDhaIdNumberAndDeathStatus(String idNo) throws Exception {
		String hql = "select count(o) from HomeAffairs o where o.dhaIDNumber = :idNo and o.deathStatus = :deathStatus";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("idNo", idNo.trim());
		parameters.put("deathStatus", "DECEASED");
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer findByDhaIdNumberAndDeathStatusAlive(String idNo) throws Exception {
		String hql = "select count(o) from HomeAffairs o where o.dhaIDNumber = :idNo and o.deathStatus = :deathStatus";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("idNo", idNo.trim());
		parameters.put("deathStatus", "ALIVE");
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	public Integer findByDhaIdNumberDead(String idNo) throws Exception {
		String hql = "select count(o) from HomeAffairs o where o.dhaIDNumber = :idNo and o.deathStatus = :deathStatus";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("idNo", idNo.trim());
		parameters.put("deathStatus", "DECEASED");
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer findByFullNamesAndIdNumber(String idNo, String fullName, String surname, String maidenName) throws Exception {
		String hql = "select count(o) from HomeAffairs o "
				+ "where o.dhaIDNumber = :idNo "
				+ "and upper(o.fullNames) = :fullName "
				+ "and upper(o.surname) = :surname "
				+ "and upper(o.maidenName) = :maidenName ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("idNo", idNo.trim());
		parameters.put("fullName", fullName.trim().toUpperCase());
		parameters.put("surname", surname.trim().toUpperCase());
		parameters.put("maidenName", maidenName.trim().toUpperCase());
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
}
