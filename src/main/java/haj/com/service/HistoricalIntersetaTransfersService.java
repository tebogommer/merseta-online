package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.IngterSetaTransferBean;
import haj.com.dao.HistoricalIntersetaTransfersDAO;
import haj.com.entity.HistoricalIntersetaTransfers;
import haj.com.framework.AbstractService;

public class HistoricalIntersetaTransfersService extends AbstractService {
	/** The dao. */
	private HistoricalIntersetaTransfersDAO dao = new HistoricalIntersetaTransfersDAO();

	/**
	 * Get all HistoricalIntersetaTransfers
 	 * @author TechFinium
 	 * @see   HistoricalIntersetaTransfers
 	 * @return a list of {@link haj.com.entity.HistoricalIntersetaTransfers}
	 * @throws Exception the exception
 	 */
	public List<HistoricalIntersetaTransfers> allHistoricalIntersetaTransfers() throws Exception {
	  	return dao.allHistoricalIntersetaTransfers();
	}


	/**
	 * Create or update HistoricalIntersetaTransfers.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     HistoricalIntersetaTransfers
	 */
	public void create(HistoricalIntersetaTransfers entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  HistoricalIntersetaTransfers.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    HistoricalIntersetaTransfers
	 */
	public void update(HistoricalIntersetaTransfers entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  HistoricalIntersetaTransfers.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    HistoricalIntersetaTransfers
	 */
	public void delete(HistoricalIntersetaTransfers entity) throws Exception  {
		this.dao.delete(entity);
	}

	public List<IngterSetaTransferBean> summaryBySchemeYear() throws Exception {
		return dao.summaryBySchemeYear();
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.HistoricalIntersetaTransfers}
	 * @throws Exception the exception
	 * @see    HistoricalIntersetaTransfers
	 */
	public HistoricalIntersetaTransfers findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find HistoricalIntersetaTransfers by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.HistoricalIntersetaTransfers}
	 * @throws Exception the exception
	 * @see    HistoricalIntersetaTransfers
	 */
	public List<HistoricalIntersetaTransfers> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load HistoricalIntersetaTransfers
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.HistoricalIntersetaTransfers}
	 * @throws Exception the exception
	 */
	public List<HistoricalIntersetaTransfers> allHistoricalIntersetaTransfers(int first, int pageSize) throws Exception {
		return dao.allHistoricalIntersetaTransfers(Long.valueOf(first), pageSize);
	}


	/**
	 * Get count of HistoricalIntersetaTransfers for lazy load
	 * @author TechFinium
	 * @return Number of rows in the HistoricalIntersetaTransfers
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(HistoricalIntersetaTransfers.class);
	}

    /**
     * Lazy load HistoricalIntersetaTransfers with pagination, filter, sorting
     * @author TechFinium
     * @param class1 HistoricalIntersetaTransfers class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.HistoricalIntersetaTransfers} containing data
     * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	public List<HistoricalIntersetaTransfers> allHistoricalIntersetaTransfers(Class<HistoricalIntersetaTransfers> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<HistoricalIntersetaTransfers>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);

	}

    /**
     * Get count of HistoricalIntersetaTransfers for lazy load with filters
     * @author TechFinium
     * @param entity HistoricalIntersetaTransfers class
     * @param filters the filters
     * @return Number of rows in the HistoricalIntersetaTransfers entity
     * @throws Exception the exception
     */
	public int count(Class<HistoricalIntersetaTransfers> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}

	public List<IngterSetaTransferBean> breakDownBySchemeYearandTransactionType(IngterSetaTransferBean ingterSetaTransferBean) throws Exception {
		return doTotal(dao.breakDownBySchemeYearandTransactionType(ingterSetaTransferBean.getSchemeYear(), ingterSetaTransferBean.getDescription()));
	}


	private List<IngterSetaTransferBean> doTotal(List<IngterSetaTransferBean> list) {
		double tot = 0.0;
		for (IngterSetaTransferBean ib : list) {
			tot+=ib.getAmount()==null?0.0:ib.getAmount().doubleValue();
		}
		list.add(new IngterSetaTransferBean(null,null,"Total",tot) );
		return list;
	}


	public Double totalMandatoryPerSchemeYearAndLNumber(Integer mersetaSchemeYear, String refNo)  throws Exception {
		return dao.totalMandatoryPerSchemeYearAndLNumber(mersetaSchemeYear, refNo);
	}

	public List<IngterSetaTransferBean> mandatoryBySchemeYear(Integer mersetaSchemeYear) throws Exception {
		return dao.mandatoryBySchemeYear(mersetaSchemeYear);
	}
}
