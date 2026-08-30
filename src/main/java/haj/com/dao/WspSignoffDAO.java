package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.WspSignoff;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class WspSignoffDAO.
 */
public class WspSignoffDAO extends AbstractDAO {

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
	 * Get all WspSignoff.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.WspSignoff}
	 * @throws Exception
	 *             global exception
	 * @see WspSignoff
	 */
	@SuppressWarnings("unchecked")
	public List<WspSignoff> allWspSignoff() throws Exception {
		return (List<WspSignoff>) super.getList("select o from WspSignoff o");
	}

	/**
	 * Get all WspSignoff between from and noRows.
	 *
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return a list of {@link haj.com.entity.WspSignoff}
	 * @throws Exception
	 *             global exception
	 * @see WspSignoff
	 */
	@SuppressWarnings("unchecked")
	public List<WspSignoff> allWspSignoff(Long from, int noRows) throws Exception {
		String hql = "select o from WspSignoff o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<WspSignoff>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.WspSignoff}
	 * @throws Exception
	 *             global exception
	 * @see WspSignoff
	 */
	public WspSignoff findByKey(Long id) throws Exception {
		String hql = "select o from WspSignoff o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (WspSignoff) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WspSignoff by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.WspSignoff}
	 * @throws Exception
	 *             global exception
	 * @see WspSignoff
	 */
	@SuppressWarnings("unchecked")
	public List<WspSignoff> findByName(String description) throws Exception {
		String hql = "select o from WspSignoff o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<WspSignoff>) super.getList(hql, parameters);
	}

	/**
	 * Find by wsp.
	 *
	 * @param wsp
	 *            the wsp
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspSignoff> findByWsp(Wsp wsp) throws Exception {
		String hql = "select o from WspSignoff o left join fetch o.user left join fetch o.wsp where o.wsp.id = :wspID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		return (List<WspSignoff>) super.getList(hql, parameters);
	}
	
	/**
	 * Find by wsp.
	 *
	 * @param wsp
	 *            the wsp
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspSignoff> findByWspLastestFirst(Wsp wsp) throws Exception {
		String hql = "select o from WspSignoff o left join fetch o.user left join fetch o.wsp where o.wsp.id = :wspID order by o.signOffDate desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		return (List<WspSignoff>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<WspSignoff> findCompletedSignOffs() throws Exception {
		String hql = "select o from WspSignoff o left join fetch o.user left join fetch o.wsp where o.wsp.wspStatusEnum not in (:pendingSignOffId, :pendingId)";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("pendingId", WspStatusEnum.Draft);
		parameters.put("pendingSignOffId", WspStatusEnum.PendingSignOff);

		return (List<WspSignoff>) super.getList(hql, parameters);
	}

	public String getApplicationType(Long wspId) throws Exception {
		String sql = "select (case when sum(case when funding_id =7 then 1 else 0 end) > 0 and sum(case when funding_id =6 then 1 else 0 end) > 0 then 'BOTH' when sum(case when funding_id =7 then 1 else 0 end) > sum(case when funding_id =6 then 1 else 0 end) then 'DG FUNDING' when sum(case when funding_id =7 then 1 else 0 end) < sum(case when funding_id =6 then 1 else 0 end) then 'MG FUNDING' else 'NONE' end) AS fundingType from mandatory_grant_detail where wsp_id = :wspId";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		
		return super.nativeSelectSqlUniqueResultNoTransform(sql, String.class, parameters);
	}

	/**
	 * Find by wsp and user.
	 *
	 * @param wsp
	 *            the wsp
	 * @param user
	 *            the user
	 * @return the wsp signoff
	 * @throws Exception
	 *             the exception
	 */
	public WspSignoff findByWspAndUser(Wsp wsp, Users user) throws Exception {
		String hql = "select o from WspSignoff o left join fetch o.user left join fetch o.wsp where o.wsp.id = :wspID and o.user.id = :userId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("userId", user.getId());
		return (WspSignoff) super.getUniqueResult(hql, parameters);
	}
}
