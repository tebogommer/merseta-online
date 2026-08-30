package haj.com.dao.lookup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.WithdrawReasonMultipleSelection;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.WithdrawReasons;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.service.WithdrawReasonMultipleSelectionService;

// TODO: Auto-generated Javadoc
/**
 * The Class WithdrawReasonsDAO.
 */
public class WithdrawReasonsDAO extends AbstractDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WithdrawReasons.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.WithdrawReasons}
	 * @throws Exception
	 *             global exception
	 * @see WithdrawReasons
	 */
	@SuppressWarnings("unchecked")
	public List<WithdrawReasons> allWithdrawReasons() throws Exception {
		return (List<WithdrawReasons>) super.getList("select o from WithdrawReasons o");
	}

	/**
	 * Get all WithdrawReasons between from and noRows.
	 *
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return a list of {@link haj.com.entity.WithdrawReasons}
	 * @throws Exception
	 *             global exception
	 * @see WithdrawReasons
	 */
	@SuppressWarnings("unchecked")
	public List<WithdrawReasons> allWithdrawReasons(Long from, int noRows) throws Exception {
		String hql = "select o from WithdrawReasons o ";
		Map<String, Object> parameters = new HashMap<>();

		return (List<WithdrawReasons>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.WithdrawReasons}
	 * @throws Exception
	 *             global exception
	 * @see WithdrawReasons
	 */
	public WithdrawReasons findByKey(Long id) throws Exception {
		String hql = "select o from WithdrawReasons o where o.id = :id ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		return (WithdrawReasons) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WithdrawReasons by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.WithdrawReasons}
	 * @throws Exception
	 *             global exception
	 * @see WithdrawReasons
	 */
	@SuppressWarnings("unchecked")
	public List<WithdrawReasons> findByName(String description) throws Exception {
		String hql = "select o from WithdrawReasons o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<WithdrawReasons>) super.getList(hql, parameters);
	}

	/**
	 * Find by process.
	 *
	 * @param process
	 *            the process
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<WithdrawReasons> findByProcess(ConfigDocProcessEnum process) {
		String hql = "select o from WithdrawReasons o where o.forProcess = :process";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("process", process);
		return (List<WithdrawReasons>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WithdrawReasons> findByProcessAndSoftDelete(ConfigDocProcessEnum process, Boolean softDelete) {
		String hql = "select o from WithdrawReasons o where o.forProcess = :process and o.softDeleted = :softDelete";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("process", process);
		parameters.put("softDelete", softDelete);
		return (List<WithdrawReasons>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WithdrawReasons> findByProcessSeniorManager(ConfigDocProcessEnum process, Boolean booleanValue) {
		String hql = "select o from WithdrawReasons o where o.forProcess = :process and o.forSeniorManager = :booleanValue";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("process", process);
		parameters.put("booleanValue", booleanValue);
		return (List<WithdrawReasons>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WithdrawReasons> findByBooleans(ConfigDocProcessEnum process, Boolean forSeniorManager, Boolean forManager,Boolean forExecutive,Boolean forCrm) {
		String hql = "select o from WithdrawReasons o where o.forProcess = :process and o.forSeniorManager = :forSeniorManager";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("process", process);
		parameters.put("forSeniorManager", forSeniorManager);
		parameters.put("forManager", forManager);
		parameters.put("forExecutive", forExecutive);
		parameters.put("forCrm", forCrm);
		return (List<WithdrawReasons>) super.getList(hql, parameters);
	}
	

	/**
	 * Find by process and boolean value
	 *
	 * @param process
	 *            the process
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<WithdrawReasons> findByProcessAndBooleanSelection(ConfigDocProcessEnum process, Boolean booleanValue) throws Exception {
		String hql = "select o from WithdrawReasons o where o.forProcess = :process and o.forCrm = :booleanValue";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("process", process);
		parameters.put("booleanValue", booleanValue);
		return (List<WithdrawReasons>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WithdrawReasons> findByProcessAndBooleanSelectionAndSoftDelete(ConfigDocProcessEnum process, Boolean booleanValue, Boolean softDelete) throws Exception {
		String hql = "select o from WithdrawReasons o where o.forProcess = :process and o.forCrm = :booleanValue and o.softDeleted = :softDelete";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("process", process);
		parameters.put("booleanValue", booleanValue);
		parameters.put("softDelete", softDelete);
		return (List<WithdrawReasons>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<WithdrawReasons> findLinkedByMultipleSelection(Long targetKey, String targetClass) throws Exception {
		String hql = "select o from WithdrawReasons o where o.id in "
				+ "(select x.rejectReason from WithdrawReasonMultipleSelection x where x.targetClass = :targetClass and x.targetKey = :targetKey) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("targetKey", targetKey);
		parameters.put("targetClass", targetClass.trim());
		return (List<WithdrawReasons>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<WithdrawReasons> locateReasonsSelectedByTargetKeyClassAndProcess(Long targetKey, String targetClass, ConfigDocProcessEnum process) throws Exception {
		String hql = "select o from WithdrawReasons o where o.id in "
				+ "(select x.rejectReason from WithdrawReasonMultipleSelection x where "
				+ "x.targetClass = :targetClass and " + "x.targetKey = :targetKey and " + "x.forProcess = :process) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("targetKey", targetKey);
		parameters.put("targetClass", targetClass.trim());
		parameters.put("process", process);
		return (List<WithdrawReasons>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WithdrawReasons> findByTargetKeyClassAndProcessAndResolveWithdrawDate(Long targetKey, String targetClass,ConfigDocProcessEnum process) throws Exception {
		String hql = "select o from WithdrawReasonMultipleSelection o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.forProcess = :process";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("targetKey", targetKey);
		parameters.put("targetClass", targetClass.trim());
		parameters.put("process", process);
		List<WithdrawReasonMultipleSelection> rrmList=(List<WithdrawReasonMultipleSelection>) super.getList(hql, parameters);
		 List<WithdrawReasons> list=new ArrayList<>();
		if(rrmList !=null && rrmList.size()>0)
		{
			for(WithdrawReasonMultipleSelection rrm:rrmList)
			{
				rrm.getWithdrawReasons().setRejectDate(rrm.getCreateDate());
				list.add(rrm.getWithdrawReasons());
			}
		}
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<WithdrawReasons> locateReasonsSelectedByTargetKeyClassAndProcessResolveAditionalInfo(Long targetKey, String targetClass,ConfigDocProcessEnum process) throws Exception {
		WithdrawReasonMultipleSelectionService withdrawReasonMultipleSelectionService=new WithdrawReasonMultipleSelectionService();
		List<WithdrawReasons>  list=new ArrayList<>();
		List<WithdrawReasonMultipleSelection> rrmList= withdrawReasonMultipleSelectionService.findByTargetKeyClassNameAndProcess(targetKey, targetClass, process);
		for(WithdrawReasonMultipleSelection rrm:rrmList)
		{
			
			WithdrawReasons WithdrawReasons=rrm.getWithdrawReasons();
			WithdrawReasons.setRejectDate(rrm.getCreateDate());
			if(rrm.getAdditionalInformation() !=null)
			{
				WithdrawReasons.setAdditionalInformation(rrm.getAdditionalInformation());
			}
			list.add(WithdrawReasons);
			
		}
		
		return list;
	}


	public long countReasonsSelectedByTargetKeyClassAndProcess(Long targetKey, String targetClass, ConfigDocProcessEnum process) throws Exception {
		String hql = "select count(o) from WithdrawReasons o where o.id in "
				+ "(select x.rejectReason from WithdrawReasonMultipleSelection x where "
				+ "x.targetClass = :targetClass and " + "x.targetKey = :targetKey and " + "x.forProcess = :process) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("targetKey", targetKey);
		parameters.put("targetClass", targetClass.trim());
		parameters.put("process", process);
		return (long) super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WithdrawReasons> locateReasonsForDgVerificationByVerificationIdAndLastestEntry(Long dgVerificationID, Boolean lastestEntry) throws Exception {
		String hql = "select o from WithdrawReasons o where o.id in "
				+ "(select x.rejectReason from DgVerificationRejectionInformation x where "
				+ "x.dgVerification.id = :dgVerificationID and x.latestEntry = :lastestEntry )";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("dgVerificationID", dgVerificationID);
		parameters.put("lastestEntry", lastestEntry);
		return (List<WithdrawReasons>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WithdrawReasons> locateReasonsForDgVerificationByVerificationIdRoleAndLastestEntry(Long dgVerificationID, Long roleId, Boolean lastestEntry) throws Exception {
		String hql = "select o from WithdrawReasons o where o.id in "
				+ "(select x.rejectReason from DgVerificationRejectionInformation x where "
				+ "x.dgVerification.id = :dgVerificationID and x.latestEntry = :lastestEntry and x.role.id = :roleId )";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("dgVerificationID", dgVerificationID);
		parameters.put("lastestEntry", lastestEntry);
		parameters.put("roleId", roleId);
		return (List<WithdrawReasons>) super.getList(hql, parameters);
	}
	
}