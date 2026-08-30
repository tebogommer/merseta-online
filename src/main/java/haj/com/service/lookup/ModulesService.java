package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.ModulesDAO;
import haj.com.entity.Company;
import haj.com.entity.CoursewareDistibution;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.lookup.Modules;
import haj.com.entity.lookup.ModulesUnitStandards;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.DocService;

public class ModulesService extends AbstractService {
	/** The dao. */
	private ModulesDAO dao = new ModulesDAO();

	/**
	 * Get all Modules
	 * 
	 * @author TechFinium
	 * @see Modules
	 * @return a list of {@link haj.com.entity.Modules}
	 * @throws Exception
	 *             the exception
	 */
	public List<Modules> allModules() throws Exception {
		return dao.allModules();
	}

	/**
	 * Create or update Modules.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Modules
	 */
	public void create(Modules entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	public void create(Modules entity, List<UnitStandards> unitStandards) throws Exception {
		if (entity.getId() == null) {
			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			dataEntities.add(entity);
			for (UnitStandards unitStandard : unitStandards) {
				dataEntities.add(new ModulesUnitStandards(entity, unitStandard));
			}
			this.dao.createBatch(dataEntities);
		} else {
			this.dao.update(entity);

			List<ModulesUnitStandards> list = allModulesUnitStandards(entity);

			dao.deleteBatch((List<IDataEntity>) (List<?>) list);

			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			for (UnitStandards unitStandard : unitStandards) {
				dataEntities.add(new ModulesUnitStandards(entity, unitStandard));
			}
			this.dao.createBatch(dataEntities);
		}
	}

	public List<Modules> resolveUnitStandards(List<Modules> modules) throws Exception {
		for (Modules module : modules) {
			module.setModulesUnitStandards(allModulesUnitStandards(module));
			resolveDoc(module);
		}
		return modules;
	}

	/**
	 * Update Modules.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Modules
	 */
	public void update(Modules entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete Modules.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Modules
	 */
	public void delete(Modules entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.Modules}
	 * @throws Exception
	 *             the exception
	 * @see Modules
	 */
	public Modules findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find Modules by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.Modules}
	 * @throws Exception
	 *             the exception
	 * @see Modules
	 */
	public List<Modules> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load Modules
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.Modules}
	 * @throws Exception
	 *             the exception
	 */
	public List<Modules> allModules(int first, int pageSize) throws Exception {
		return dao.allModules(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of Modules for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the Modules
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(Modules.class);
	}

	/**
	 * Lazy load Modules with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            Modules class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.Modules} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Modules> allModules(Class<Modules> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return resolveUnitStandards((List<Modules>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));

	}

	/**
	 * Get count of Modules for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            Modules class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the Modules entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<Modules> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public List<ModulesUnitStandards> allModulesUnitStandards(Modules modules) throws Exception {
		return dao.allModulesUnitStandards(modules);
	}
	
	public List<Modules> allModulesByCompanyUnitStandard(Company company) throws Exception {
		return resolveUnitStandards(dao.allModulesByCompanyUnitStandard(company));
	}
	
	public List<Modules> allModulesByCompanyUnitStandardAndStatus(Company company,ApprovalEnum satus) throws Exception {
		return resolveUnitStandards(dao.allModulesByCompanyUnitStandardAndStatus(company, satus));
	}
	
	


	public void resolveEverythingForCourseware(List<CoursewareDistibution> modules) throws Exception {
		// searchByModule
		for (CoursewareDistibution module : modules) {
			resolveCoursewareData(module);
		}
	}

	public void resolveCoursewareData(CoursewareDistibution module) throws Exception {
		resolveDoc(module.getModules());
		module.getModules().setModulesUnitStandards(allModulesUnitStandards(module.getModules()));
	}

	
	public void resolveDoc(List<Modules> modules) throws Exception {
		// searchByModule
		for (Modules module : modules) {
			resolveDoc(module);
		}
	}

	private void resolveDoc(Modules modules) throws Exception {
		// searchByModule
		modules.setDocs(DocService.instance().searchByModule(modules));

	}
}
