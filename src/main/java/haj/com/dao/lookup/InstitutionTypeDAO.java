package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.InstitutionType;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class InstitutionTypeDAO.
 */
public class InstitutionTypeDAO extends AbstractDAO {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all InstitutionType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.InstitutionType}
	 * @throws Exception             global exception
	 * @see InstitutionType
	 */
	@SuppressWarnings("unchecked")
	public List<InstitutionType> allInstitutionType() throws Exception {
		return (List<InstitutionType>) super.getList("select o from InstitutionType o");
	}

	/**
	 * Get all InstitutionType between from and noRows.
	 *
	 * @author TechFinium
	 * @param from            the from
	 * @param noRows            the no rows
	 * @return a list of {@link haj.com.entity.InstitutionType}
	 * @throws Exception             global exception
	 * @see InstitutionType
	 */
	@SuppressWarnings("unchecked")
	public List<InstitutionType> allInstitutionType(Long from, int noRows) throws Exception {
		String hql = "select o from InstitutionType o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<InstitutionType>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id            the id
	 * @return a {@link haj.com.entity.InstitutionType}
	 * @throws Exception             global exception
	 * @see InstitutionType
	 */
	public InstitutionType findByKey(Long id) throws Exception {
		String hql = "select o from InstitutionType o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (InstitutionType) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find InstitutionType by description.
	 *
	 * @author TechFinium
	 * @param description            the description
	 * @return a list of {@link haj.com.entity.InstitutionType}
	 * @throws Exception             global exception
	 * @see InstitutionType
	 */
	@SuppressWarnings("unchecked")
	public List<InstitutionType> findByName(String description) throws Exception {
		String hql = "select o from InstitutionType o where o.description like  :description order by o.description ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<InstitutionType>) super.getList(hql, parameters);
	}
}
