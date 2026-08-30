package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.TradeAppraisalsChecklist;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class TradeAppraisalsChecklistDAO.
 */
public class TradeAppraisalsChecklistDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TradeAppraisalsChecklist.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.TradeAppraisalsChecklist}
	 * @throws Exception global exception
	 * @see    TradeAppraisalsChecklist
	 */
	@SuppressWarnings("unchecked")
	public List<TradeAppraisalsChecklist> allTradeAppraisalsChecklist() throws Exception {
		return (List<TradeAppraisalsChecklist>)super.getList("select o from TradeAppraisalsChecklist o");
	}

	/**
	 * Get all TradeAppraisalsChecklist between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.TradeAppraisalsChecklist}
	 * @throws Exception global exception
	 * @see    TradeAppraisalsChecklist
	 */
	@SuppressWarnings("unchecked")
	public List<TradeAppraisalsChecklist> allTradeAppraisalsChecklist(Long from, int noRows) throws Exception {
	 	String hql = "select o from TradeAppraisalsChecklist o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<TradeAppraisalsChecklist>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.TradeAppraisalsChecklist}
	 * @throws Exception global exception
	 * @see    TradeAppraisalsChecklist
	 */
	public TradeAppraisalsChecklist findByKey(Long id) throws Exception {
	 	String hql = "select o from TradeAppraisalsChecklist o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (TradeAppraisalsChecklist)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TradeAppraisalsChecklist by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.TradeAppraisalsChecklist}
	 * @throws Exception global exception
	 * @see    TradeAppraisalsChecklist
	 */
	@SuppressWarnings("unchecked")
	public List<TradeAppraisalsChecklist> findByName(String description) throws Exception {
	 	String hql = "select o from TradeAppraisalsChecklist o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<TradeAppraisalsChecklist>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<TradeAppraisalsChecklist> findByWorkPlaceApproval(Long workPlaceApprovalID) {
		String hql = "select o from TradeAppraisalsChecklist o where o.workPlaceApproval.id = :workPlaceApprovalID  order by o.createDate asc" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("workPlaceApprovalID", workPlaceApprovalID);
		return (List<TradeAppraisalsChecklist>)super.getList(hql, parameters);
	}
}

