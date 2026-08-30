package haj.com.service.lookup;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.DGYearDAO;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.DGWindowTypeEnum;
import haj.com.entity.lookup.DGYear;
import haj.com.framework.AbstractService;
import haj.com.service.DocService;

public class DGYearService extends AbstractService {
	/** The dao. */
	private DGYearDAO dao = new DGYearDAO();

	/**
	 * Get all DGYear
	 * @author TechFinium
	 * @see DGYear
	 * @return a list of {@link haj.com.entity.DGYear}
	 * @throws Exception
	 * the exception
	 */
	public List<DGYear> allDGYear() throws Exception {
		return dao.allDGYear();
	}

	/**
	 * Create or update DGYear.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see DGYear
	 */
	public void create(DGYear entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update DGYear.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see DGYear
	 */
	public void update(DGYear entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete DGYear.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see DGYear
	 */
	public void delete(DGYear entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 * @author TechFinium
	 * @param id
	 * the id
	 * @return a {@link haj.com.entity.DGYear}
	 * @throws Exception
	 * the exception
	 * @see DGYear
	 */
	public DGYear findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find DGYear by description.
	 * @author TechFinium
	 * @param desc
	 * the desc
	 * @return a list of {@link haj.com.entity.DGYear}
	 * @throws Exception
	 * the exception
	 * @see DGYear
	 */
	public List<DGYear> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load DGYear
	 * @param first
	 * from key
	 * @param pageSize
	 * no of rows to fetch
	 * @return a list of {@link haj.com.entity.DGYear}
	 * @throws Exception
	 * the exception
	 */
	public List<DGYear> allDGYear(int first, int pageSize) throws Exception {
		return dao.allDGYear(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of DGYear for lazy load
	 * @author TechFinium
	 * @return Number of rows in the DGYear
	 * @throws Exception
	 * the exception
	 */
	public Long count() throws Exception {
		return dao.count(DGYear.class);
	}

	@SuppressWarnings("unchecked")
	public List<DGYear> allDGYear(Class<DGYear> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return resolveDocs((List<DGYear>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));

	}

	public int count(Class<DGYear> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public List<DGYear> allDGYear(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from DGYear o where o.dgWindowType in (1, 2)";
		return resolveDocs((List<DGYear>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int count(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from DGYear o where o.dgWindowType in (1, 2)";
		if (filters == null) filters = new HashMap<String, Object>();
		return dao.countWhere(filters, hql);
	}

	public List<DGYear> findByDate(Date searchDate) throws Exception {
		return dao.findByDate(searchDate);
	}

	public List<DGYear> findByDatePivitol(Date searchDate) throws Exception {
		return dao.findByDatePivitol(searchDate);
	}

	public DGYear findLatestDGYear() throws Exception {
		return dao.findLatestDGYear().isEmpty() ? null : dao.findLatestDGYear().get(0);
	}

	public void generateDGYear(DGYear dgYear, Users user) throws Exception {
		if (dgYear.getDocs() == null || dgYear.getDocs().isEmpty()) {
			throw new Exception("Please approval documentation");
		}
		create(dgYear);
		/*
		 * for (Doc doc : dgYear.getDocs()) {
		 * 
		 * doc.setTargetClass(DGYear.class.getName()); doc.setTargetKey(dgYear.getId());
		 * 
		 * dataEntitiesCreate.add(new DocByte(doc.getData(), doc)); dataEntitiesCreate.add(new DocumentTracker(doc, user, new java.util.Date(), DocumentTrackerEnum.Upload)); } DocService.instance().save(dgYear.getDoc(), user, dgYear.getDocs());
		 */
	}

	public Long findTotalDGYearByFinYear(Integer finYear) throws Exception {
		return dao.findTotalDGYearByFinYear(finYear);
	}

	public List<DGYear> resolveDocs(List<DGYear> dgYears) throws Exception {
		for (DGYear dgYear : dgYears) {
			resolveDocs(dgYear);
		}
		return dgYears;
	}

	public void resolveDocs(DGYear dgYear) throws Exception {
		dgYear.setDocs(DocService.instance().searchByTargetKeyAndClass(dgYear.getClass().getName(), dgYear.getId()));
	}
}
