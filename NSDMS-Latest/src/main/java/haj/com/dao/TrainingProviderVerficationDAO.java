package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;

import haj.com.entity.TrainingProviderVerfication;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class TrainingProviderVerficationDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TrainingProviderVerfication
	 * @author TechFinium
	 * @see TrainingProviderVerfication
	 * @return a list of {@link haj.com.entity.TrainingProviderVerfication}
	 * @throws Exception
	 * global exception
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> allTrainingProviderVerfication() throws Exception {
		return (List<TrainingProviderVerfication>) super.getList("select o from TrainingProviderVerfication o");
	}

	/**
	 * Get all TrainingProviderVerfication between from and noRows
	 * @author TechFinium
	 * @param from
	 * the from
	 * @param noRows
	 * the no rows
	 * @see TrainingProviderVerfication
	 * @return a list of {@link haj.com.entity.TrainingProviderVerfication}
	 * @throws Exception
	 * global exception
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> allTrainingProviderVerfication(Long from, int noRows) throws Exception {
		String              hql        = "select o from TrainingProviderVerfication o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<TrainingProviderVerfication>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * @author TechFinium
	 * @param id
	 * the id
	 * @see TrainingProviderVerfication
	 * @return a {@link haj.com.entity.TrainingProviderVerfication}
	 * @throws Exception
	 * global exception
	 */
	public TrainingProviderVerfication findByKey(Long id) throws Exception {
		String              hql        = "select o from TrainingProviderVerfication o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (TrainingProviderVerfication) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TrainingProviderVerfication by description
	 * @author TechFinium
	 * @param description
	 * the description
	 * @see TrainingProviderVerfication
	 * @return a list of {@link haj.com.entity.TrainingProviderVerfication}
	 * @throws Exception
	 * global exception
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> findByName(String description) throws Exception {
		String              hql        = "select o from TrainingProviderVerfication o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<TrainingProviderVerfication>) super.getList(hql, parameters);
	}

	public int countTrainingProviderVerficationByStatus(Long trainingProviderId, ApprovalEnum status) throws Exception {
		String              hql        = "select count(o) from TrainingProviderVerfication o where o.status = :status and o.trainingProvider.id = :trainingProviderId and o.trainingProviderVerficationParent is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("trainingProviderId", trainingProviderId);
		parameters.put("status", status);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> findByProviderAnStatus(Long trainingProviderID, ApprovalEnum status) throws Exception {
		String              hql        = "select o from TrainingProviderVerfication o where o.status = :status and o.trainingProvider.id = :trainingProviderID and o.trainingProviderVerficationParent is null ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("status", status);
		parameters.put("trainingProviderID", trainingProviderID);
		return (List<TrainingProviderVerfication>) super.getList(hql, parameters);
	}

	public int countLeaners(Long trainingProviderID, ApprovalEnum status) {
		String              hql        = "select o from TrainingProviderVerfication o where o.status = :status and o.trainingProvider.id = :trainingProviderID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("status", status);
		parameters.put("trainingProviderID", trainingProviderID);
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	public Integer countByStatusAndProvider(Long id, ApprovalEnum status) throws Exception {
		String              hql        = "select count(o) from TrainingProviderVerfication o where o.status = :status and o.trainingProvider.id = :id";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		parameters.put("status", status);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> findByProviderAnStatusTenPercent(Long trainingProviderID, ApprovalEnum status, int answer) {
		String              hql        = "select o from TrainingProviderVerfication o where o.status = :status and o.trainingProvider.id = :trainingProviderID ORDER BY RAND() ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("trainingProviderID", trainingProviderID);
		parameters.put("status", status);
		return (List<TrainingProviderVerfication>) super.getList(hql, parameters, answer);
		// return (List<TrainingProviderVerfication>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> findByTrainingProviderVerficationParentID(Long trainingProviderVerficationParentID) {
		String              hql        = "select o from TrainingProviderVerfication o where o.trainingProviderVerficationParent.id = :trainingProviderVerficationParentID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("trainingProviderVerficationParentID", trainingProviderVerficationParentID);
		return (List<TrainingProviderVerfication>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> findByCompanyLearner(Long companyLearnersID) {
		String              hql        = "select o from TrainingProviderVerfication o where o.companyLearners.id = :companyLearnersID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyLearnersID", companyLearnersID);
		return (List<TrainingProviderVerfication>) super.getList(hql, parameters);
	}

	public TrainingProviderVerfication findTrainingProviderVerficationByCompanyLearner(Long companyLearnersID) throws Exception {
		String              hql        = "select o from TrainingProviderVerfication o left join fetch o.companyLearners cl where o.companyLearners.id = :companyLearnersID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyLearnersID", companyLearnersID);
		return (TrainingProviderVerfication) super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> findByUserAndStatus(Long createUserID, ApprovalEnum status) {
		String              hql        = "select o from TrainingProviderVerfication o where o.status = :status and o.createUser.id = :createUserID and o.companyLearners <> null ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("createUserID", createUserID);
		parameters.put("status", status);
		return (List<TrainingProviderVerfication>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> findByCompanyAndStatus(Long trainingProviderID, ApprovalEnum status) {
		String              hql        = "select o from TrainingProviderVerfication o where o.status = :status and o.trainingProvider.id = :trainingProviderID and o.companyLearners <> null ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("trainingProviderID", trainingProviderID);
		parameters.put("status", status);
		return (List<TrainingProviderVerfication>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> findByCompanyAndUserAndStatus(Long trainingProviderID, Long createUserID, ApprovalEnum status) {
		String              hql        = "select o from TrainingProviderVerfication o where o.status = :status and o.trainingProvider.id = :trainingProviderID and o.createUser.id = :createUserID and o.companyLearners <> null ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("trainingProviderID", trainingProviderID);
		parameters.put("createUserID", createUserID);
		parameters.put("status", status);
		return (List<TrainingProviderVerfication>) super.getList(hql, parameters);
	}

	public Long findByScheduledEventCount(Long scheduledEventID) {
		String              hql        = "select count(o) from TrainingProviderVerfication o where o.scheduledEvent.id = :scheduledEventID and o.companyLearners <> null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("scheduledEventID", scheduledEventID);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> findByScheduledEventRandom(Long scheduledEventID, int size) {
		String              hql        = "select o from TrainingProviderVerfication o where o.scheduledEvent.id = :scheduledEventID and o.companyLearners <> null ORDER BY rand()";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("scheduledEventID", scheduledEventID);
		return (List<TrainingProviderVerfication>) super.getList(hql, parameters, size);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> findByScheduledEvent(Long scheduledEventID) {
		String              hql        = "select o from TrainingProviderVerfication o where o.scheduledEvent.id = :scheduledEventID and o.companyLearners <> null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("scheduledEventID", scheduledEventID);
		return (List<TrainingProviderVerfication>) super.getList(hql, parameters);
	}

	public int countTrainingProviderVerficationByStatus(ApprovalEnum status) throws Exception {
		String              hql        = "select count(o) from TrainingProviderVerfication o where o.status = :status ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("status", status);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	public Integer countAllResults() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from TrainingProviderVerfication o where o.companyLearners <> null and o.generateAddEnum is null")).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> findAllTrainingProviderVerficationWithErrors() {
		String              hql        = "select o from TrainingProviderVerfication o where o.companyLearners <> null and o.generateAddEnum is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<TrainingProviderVerfication>) super.getList(hql, parameters);
	}
}
