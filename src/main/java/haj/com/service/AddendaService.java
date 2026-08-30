package haj.com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.AddendaDAO;
import haj.com.entity.Addenda;
import haj.com.entity.lookup.DGYear;
import haj.com.framework.AbstractService;

public class AddendaService extends AbstractService {
	/** The dao. */
	private AddendaDAO dao = new AddendaDAO();

	/**
	 * Get all Addenda
	 * @author TechFinium
	 * @see Addenda
	 * @return a list of {@link haj.com.entity.Addenda}
	 * @throws Exception
	 * the exception
	 */
	public List<Addenda> allAddenda() throws Exception {
		return dao.allAddenda();
	}

	/**
	 * Create or update Addenda.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see Addenda
	 */
	public void create(Addenda entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update Addenda.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see Addenda
	 */
	public void update(Addenda entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete Addenda.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see Addenda
	 */
	public void delete(Addenda entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 * @author TechFinium
	 * @param id
	 * the id
	 * @return a {@link haj.com.entity.Addenda}
	 * @throws Exception
	 * the exception
	 * @see Addenda
	 */
	public Addenda findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find Addenda by description.
	 * @author TechFinium
	 * @param desc
	 * the desc
	 * @return a list of {@link haj.com.entity.Addenda}
	 * @throws Exception
	 * the exception
	 * @see Addenda
	 */
	public List<Addenda> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load Addenda
	 * @param first
	 * from key
	 * @param pageSize
	 * no of rows to fetch
	 * @return a list of {@link haj.com.entity.Addenda}
	 * @throws Exception
	 * the exception
	 */
	public List<Addenda> allAddenda(int first, int pageSize) throws Exception {
		return dao.allAddenda(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of Addenda for lazy load
	 * @author TechFinium
	 * @return Number of rows in the Addenda
	 * @throws Exception
	 * the exception
	 */
	public Long count() throws Exception {
		return dao.count(Addenda.class);
	}

	@SuppressWarnings("unchecked")
	public List<Addenda> allAddenda(Class<Addenda> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<Addenda>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	public int count(Class<Addenda> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public List<Addenda> allAddenda(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from Addenda o where o.activeContracts.id = :activeContractsID";
		return resolveDocs((List<Addenda>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int count(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Addenda o where o.activeContracts.id = :activeContractsID";
		return dao.countWhere(filters, hql);
	}

	public List<Addenda> resolveDocs(List<Addenda> dgYears) throws Exception {
		for (Addenda dgYear : dgYears) {
			resolveDocs(dgYear);
		}
		return dgYears;
	}

	public void resolveDocs(Addenda dgYear) throws Exception {
		dgYear.setDocs(DocService.instance().searchByTargetKeyAndClass(dgYear.getClass().getName(), dgYear.getId()));
	}
}
