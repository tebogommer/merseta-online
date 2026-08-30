package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.OfoCodes;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class OfoCodesDAO.
 */
public class OfoCodesDAO extends AbstractDAO {

	private static final String leftJoins = " " 
			+ "left join fetch o.ofoCodes ofo left join fetch o.occupationCategory occ"
			+ " ";
	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all OfoCodes.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.OfoCodes}
	 * @throws Exception
	 *             the exception
	 * @see OfoCodes
	 */
	@SuppressWarnings("unchecked")
	public List<OfoCodes> allOfoCodes() throws Exception {
		return (List<OfoCodes>) super.getList("select o from OfoCodes o");
	}
	
	@SuppressWarnings("unchecked")
	public List<OfoCodes> allOfoCodesActiveWithOfoCode() throws Exception {
		return (List<OfoCodes>) super.getList("select o from OfoCodes o where o.active = true and o.ofoCode is not null");
	}
	
	@SuppressWarnings("unchecked")
	public List<OfoCodes> allSpecialOfoCodesActiveWithOfoCode() throws Exception {
		return (List<OfoCodes>) super.getList("select o from OfoCodes o where o.active = true and o.ofoCode is null and o.specialisationCode is not null and o.ofoCodes is null");
	}

	/**
	 * All ofo codes.
	 *
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<OfoCodes> allOfoCodes(Long from, int noRows) throws Exception {
		String hql = "select o from OfoCodes o ";
		Map<String, Object> parameters = new HashMap<>();
		return (List<OfoCodes>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return OfoCodes
	 * @throws Exception
	 *             the exception
	 * @see OfoCodes
	 */
	public OfoCodes findByKey(Long id) throws Exception {
		String hql = "select o from OfoCodes o "+leftJoins+" where o.id = :id ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		return (OfoCodes) super.getUniqueResult(hql, parameters);
	}
	
	public OfoCodes findByOfoCode(String ofoCode) throws Exception {
		String hql = "select o from OfoCodes o "+leftJoins+" where o.ofoCode = :ofoCode ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("ofoCode", ofoCode.trim());
		return (OfoCodes) super.getUniqueResult(hql, parameters);
	}
	
	public int findByLinkedOfoCode(Long ofoCodeParentId) throws Exception {
		String hql = "select count(o) from OfoCodes o where o.ofoCodes.id = :ofoCodeParentId ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("ofoCodeParentId", ofoCodeParentId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	/**
	 * Find by code.
	 *
	 * @param ofoCode the ofo code
	 * @return the ofo codes
	 * @throws Exception the exception
	 */
	public OfoCodes findByCode(String ofoCode) throws Exception {
		String hql = "select o from OfoCodes o where o.ofoCode = :ofoCode and o.active = :filterValue";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("ofoCode", ofoCode);
		parameters.put("filterValue", true);
		return (OfoCodes) super.getUniqueResult(hql, parameters);
	}

	public OfoCodes findBySpecialisation(String ofoCode) throws Exception {
		String hql = "select o from OfoCodes o where o.specialisationCode = :ofoCode and o.active = :filterValue";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("ofoCode", ofoCode);
		parameters.put("filterValue", true);
		return (OfoCodes) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find OfoCodes by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.OfoCodes}
	 * @throws Exception
	 *             the exception
	 * @see OfoCodes
	 */
	@SuppressWarnings("unchecked")
	public List<OfoCodes> findByName(String description) throws Exception {		
		String hql = "select o from OfoCodes o "
					+"left join fetch o.ofoCodes oc "
					+ "where (o.description like :description "				
					+ "or o.ofoCode like :description "				
					+ "or o.ofoCodes.ofoCode like :description) "
					+ "and o.active = :filterValue "				
					+ "order by o.description";		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("description", "%" + description.trim() + "%");
		parameters.put("filterValue", true);
		return (List<OfoCodes>) super.getList(hql, parameters);
	}

	public List<OfoCodes> findByNewestOLOCodes(String description) throws Exception {
		String hql = "select o from OfoCodes o "
				+"left join fetch o.ofoCodes oc "
				+ "where (o.description like :description "
				+ "or o.ofoCode like :description "
				+ "or o.ofoCodes.ofoCode like :description) "
				+ "and o.active = :filterValue "
				+ "order by o.description";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("description", "%" + description.trim() + "%");
		parameters.put("filterValue", true);
		return (List<OfoCodes>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<OfoCodes> findByNameAndYear(String description, Integer year) throws Exception {		
		String hql = "select o from OfoCodes o "
					+"left join fetch o.ofoCodes oc "
					+ "where (o.description like :description "				
					+ "or o.ofoCode like :description "				
					+ "or o.ofoCodes.ofoCode like :description) "
					+ "and o.year = :filterValue "				
					+ "order by o.description";		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("description", "%" + description.trim() + "%");
		parameters.put("year", year);
		return (List<OfoCodes>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<OfoCodes> findByNameAtr(String description) throws Exception {		
		String hql = "select o from OfoCodes o "
					+"left join fetch o.ofoCodes oc "
					+ "where (o.description like :description "				
					+ "or o.ofoCode like :description "				
					+ "or o.ofoCodes.ofoCode like :description) "
					+ "and o.active = :filterValue "				
					+ "order by o.description";		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("description", "%" + description.trim() + "%");
		parameters.put("filterValue", false);
		return (List<OfoCodes>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<OfoCodes> findByNameAtrAndYear(String description, Integer year) throws Exception {		
		String hql = "select o from OfoCodes o "
					+"left join fetch o.ofoCodes oc "
					+ "where (o.description like :description "				
					+ "or o.ofoCode like :description "				
					+ "or o.ofoCodes.ofoCode like :description) "
					+ "and o.year = :year "				
					+ "order by o.description";		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("description", "%" + description.trim() + "%");
		parameters.put("year", year);
		return (List<OfoCodes>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<OfoCodes> findByTrade(String description,  boolean trade) throws Exception {		
		String hql = "select o from OfoCodes o "					
					+ "where o.trade is true "
					+ "and o.description like :description "
					+ "and o.active = :filterValue "	
					+ "order by o.description asc";		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("description", "%" + description.trim() + "%");
		parameters.put("filterValue", true);
		return (List<OfoCodes>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<OfoCodes> findByTradeWpaVersion(String description,  boolean trade) throws Exception {		
		String hql = "select o from OfoCodes o "					
					+ "where o.trade is true "
					+ "and (o.description like :description or o.ofoCode like :description) "
					+ "and o.active = :filterValue "
					+ "order by o.description asc";		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("description", "%" + description.trim() + "%");
		parameters.put("filterValue", true);
		return (List<OfoCodes>) super.getList(hql, parameters);
	}
	

	@SuppressWarnings("unchecked")
	public List<OfoCodes> findByNameNoSpes(String description) throws Exception {		
		String hql = "select o from OfoCodes o "
					+"left join fetch o.ofoCodes oc "
					+ "where (o.description like :description "				
					+ "or o.ofoCode like :description) "	
					+ "and o.active = :activeValue "
					+ "order by o.description";		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("description", "%" + description.trim() + "%");
		parameters.put("activeValue", true);
		return (List<OfoCodes>) super.getList(hql, parameters);
	}
	

	@SuppressWarnings("unchecked")
	public List<OfoCodes> findChild(String description) throws Exception {
		String hql = "select o from OfoCodes o where o.ofoCodes.ofoCode like :description and o.active = :filterValue order by o.description";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("description", "%" + description.trim() + "%");
		parameters.put("activeValue", true);
		return (List<OfoCodes>) super.getList(hql, parameters);
	}
	
	
	/**
	 * Find OfoCodes by code.
	 *
	 * @author TechFinium
	 * @param ofoCodes the ofo codes
	 * @return a  {@link haj.com.entity.OfoCodes}
	 * @throws Exception             the exception
	 * @see OfoCodes
	 */
	 public OfoCodes findUniqueCode(OfoCodes ofoCodes) throws Exception {
		 	String hql = "select o from OfoCodes o where o.ofoCode = :ofoCode " ;
		 	Map<String, Object> parameters = new HashMap<>();
		 	if (ofoCodes.getId()!=null) {
		 		hql += " and o.id <> :id ";
		 		parameters.put("id", ofoCodes.getId());
		 	}		   
		    parameters.put("ofoCode", ofoCodes.getOfoCode());
			return (OfoCodes)super.getUniqueResult(hql, parameters);
		}
}
