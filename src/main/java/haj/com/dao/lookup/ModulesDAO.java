package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Company;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.lookup.Modules;
import haj.com.entity.lookup.ModulesUnitStandards;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class ModulesDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Modules
	 * 
	 * @author TechFinium
	 * @see Modules
	 * @return a list of {@link haj.com.entity.Modules}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<Modules> allModules() throws Exception {
		return (List<Modules>) super.getList("select o from Modules o");
	}

	/**
	 * Get all Modules between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see Modules
	 * @return a list of {@link haj.com.entity.Modules}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<Modules> allModules(Long from, int noRows) throws Exception {
		String hql = "select o from Modules o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<Modules>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	@SuppressWarnings("unchecked")
	public List<ModulesUnitStandards> allModulesUnitStandards(Modules modules) throws Exception {
		String hql = "select o from ModulesUnitStandards o where o.modules.id = :moduleID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("moduleID", modules.getId());
		return (List<ModulesUnitStandards>) super.getList(hql, parameters);
	}
	
	

	@SuppressWarnings("unchecked")
	public List<Modules> allModulesByCompanyUnitStandard(Company company) throws Exception {
		String hql = "select distinct(o.modules) from ModulesUnitStandards o where "
					+ "(o.unitStandards.id in (select x.unitStandard.id from CompanyUnitStandard x where x.company.id = :companyId) or "
					+ "o.unitStandards.code in (select y.unitstandardid from SaqaUsQualification y where y.saqaQualification.id in "
					+ "(select z.qualification.id from CompanyQualifications z where z.company.id = :companyId)) )and "
					+ "(o.modules.id not in (select a.modules.id from CoursewareDistibution a where a.company.id = :companyId) or "
					+ "o.modules.id in (select a.modules.id from CoursewareDistibution a where a.company.id = :companyId and a.approvalEnum = :status))";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", company.getId());
		parameters.put("status", ApprovalEnum.Rejected);
		return (List<Modules>) super.getList(hql, parameters);
	}
	
	/*@SuppressWarnings("unchecked")
	public List<Modules> allModulesByCompanyUnitStandard(Company company) throws Exception {
		String hql = "select distinct(o.modules) from ModulesUnitStandards o where "
					+ "(o.unitStandards.id in (select x.unitStandard.id from CompanyUnitStandard x where x.company.id = :companyId) or "
					+ "o.unitStandards.code in (select y.unitstandardid from SaqaUsQualification y where y.saqaQualification.id in "
					+ "(select z.qualification.id from CompanyQualifications z where z.company.id = :companyId)) )";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", company.getId());
		return (List<Modules>) super.getList(hql, parameters);
	}*/
	
	@SuppressWarnings("unchecked")
	public List<Modules> allModulesByCompanyUnitStandardAndStatus(Company company, ApprovalEnum status) throws Exception {
		String hql = "select distinct(o.modules) from ModulesUnitStandards o where "
					+ "(o.unitStandards.id in (select x.unitStandard.id from CompanyUnitStandard x where x.company.id = :companyId) or "
					+ "o.unitStandards.code in (select y.unitstandardid from SaqaUsQualification y where y.saqaQualification.id in "
					+ "(select z.qualification.id from CompanyQualifications z where z.company.id = :companyId)) )and "
					+ "o.modules.id in (select a.modules.id from CoursewareDistibution a where a.company.id = :companyId and a.approvalEnum = :status)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", company.getId());
		parameters.put("status", status);
		return (List<Modules>) super.getList(hql, parameters);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see Modules
	 * @return a {@link haj.com.entity.Modules}
	 * @throws Exception
	 *             global exception
	 */
	public Modules findByKey(Long id) throws Exception {
		String hql = "select o from Modules o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Modules) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Modules by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see Modules
	 * @return a list of {@link haj.com.entity.Modules}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<Modules> findByName(String description) throws Exception {
		String hql = "select o from Modules o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<Modules>) super.getList(hql, parameters);
	}
}
