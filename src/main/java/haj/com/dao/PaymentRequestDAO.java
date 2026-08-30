package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.PaymentRequest;

public class PaymentRequestDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all PaymentRequest
 	 * @author TechFinium 
 	 * @see    PaymentRequest
 	 * @return a list of {@link haj.com.entity.PaymentRequest}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<PaymentRequest> allPaymentRequest() throws Exception {
		return (List<PaymentRequest>)super.getList("select o from PaymentRequest o");
	}

	/**
	 * Get all PaymentRequest between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    PaymentRequest
 	 * @return a list of {@link haj.com.entity.PaymentRequest}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<PaymentRequest> allPaymentRequest(Long from, int noRows) throws Exception {
	 	String hql = "select o from PaymentRequest o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<PaymentRequest>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    PaymentRequest
 	 * @return a {@link haj.com.entity.PaymentRequest}
 	 * @throws Exception global exception
 	 */
	public PaymentRequest findByKey(Long id) throws Exception {
	 	String hql = "select o from PaymentRequest o left join fetch o.projectImplementationPlan pip left join fetch pip.wsp where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (PaymentRequest)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find PaymentRequest by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    PaymentRequest
  	 * @return a list of {@link haj.com.entity.PaymentRequest}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<PaymentRequest> findByName(String description) throws Exception {
	 	String hql = "select o from PaymentRequest o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<PaymentRequest>)super.getList(hql, parameters);
	}
}

