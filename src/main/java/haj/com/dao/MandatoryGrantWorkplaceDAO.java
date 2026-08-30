package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.MandatoryGrant;
import haj.com.entity.MandatoryGrantWorkplace;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class MandatoryGrantWorkplaceDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all MandatoryGrantWorkplace
	 * @author TechFinium
	 * @see MandatoryGrantWorkplace
	 * @return a list of {@link haj.com.entity.MandatoryGrantWorkplace}
	 * @throws Exception
	 * global exception
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrantWorkplace> allMandatoryGrantWorkplace() throws Exception {
		return (List<MandatoryGrantWorkplace>) super.getList("select o from MandatoryGrantWorkplace o");
	}

	/**
	 * Get all MandatoryGrantWorkplace between from and noRows
	 * @author TechFinium
	 * @param from
	 * the from
	 * @param noRows
	 * the no rows
	 * @see MandatoryGrantWorkplace
	 * @return a list of {@link haj.com.entity.MandatoryGrantWorkplace}
	 * @throws Exception
	 * global exception
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrantWorkplace> allMandatoryGrantWorkplace(Long from, int noRows) throws Exception {
		String              hql        = "select o from MandatoryGrantWorkplace o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<MandatoryGrantWorkplace>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * @author TechFinium
	 * @param id
	 * the id
	 * @see MandatoryGrantWorkplace
	 * @return a {@link haj.com.entity.MandatoryGrantWorkplace}
	 * @throws Exception
	 * global exception
	 */
	public MandatoryGrantWorkplace findByKey(Long id) throws Exception {
		String              hql        = "select o from MandatoryGrantWorkplace o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (MandatoryGrantWorkplace) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find MandatoryGrantWorkplace by description
	 * @author TechFinium
	 * @param description
	 * the description
	 * @see MandatoryGrantWorkplace
	 * @return a list of {@link haj.com.entity.MandatoryGrantWorkplace}
	 * @throws Exception
	 * global exception
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrantWorkplace> findByName(String description) throws Exception {
		String              hql        = "select o from MandatoryGrantWorkplace o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<MandatoryGrantWorkplace>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantWorkplace> findByMandatoryGrant(MandatoryGrant mandatoryGrant) throws Exception {
		String              hql        = "select o from MandatoryGrantWorkplace o where o.mandatoryGrant.id = :mandatoryGrantID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("mandatoryGrantID", mandatoryGrant.getId());
		return (List<MandatoryGrantWorkplace>) super.getList(hql, parameters);
	}
}
