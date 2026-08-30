package haj.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import haj.com.entity.ProjectImplementationPlanLearners;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class ProjectImplementationPlanLearnersDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ProjectImplementationPlanLearners
 	 * @author TechFinium 
 	 * @see    ProjectImplementationPlanLearners
 	 * @return a list of {@link haj.com.entity.ProjectImplementationPlanLearners}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlanLearners> allProjectImplementationPlanLearners() throws Exception {
		return (List<ProjectImplementationPlanLearners>)super.getList("select o from ProjectImplementationPlanLearners o");
	}
	
	public Integer countAllProjectImplementationPlanLearners() throws Exception{
		return ((Long)super.getUniqueResult("select count(o) from ProjectImplementationPlanLearners o")).intValue();
	}

	/**
	 * Get all ProjectImplementationPlanLearners between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ProjectImplementationPlanLearners
 	 * @return a list of {@link haj.com.entity.ProjectImplementationPlanLearners}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlanLearners> allProjectImplementationPlanLearners(Long from, int noRows) throws Exception {
	 	String hql = "select o from ProjectImplementationPlanLearners o " ;
	    Map<String, Object> parameters = new HashMap<>();
	    
		return (List<ProjectImplementationPlanLearners>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ProjectImplementationPlanLearners
 	 * @return a {@link haj.com.entity.ProjectImplementationPlanLearners}
 	 * @throws Exception global exception
 	 */
	public ProjectImplementationPlanLearners findByKey(Long id) throws Exception {
	 	String hql = "select o from ProjectImplementationPlanLearners o where o.id = :id " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("id", id);
		return (ProjectImplementationPlanLearners)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ProjectImplementationPlanLearners by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ProjectImplementationPlanLearners
  	 * @return a list of {@link haj.com.entity.ProjectImplementationPlanLearners}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlanLearners> findByName(String description) throws Exception {
	 	String hql = "select o from ProjectImplementationPlanLearners o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ProjectImplementationPlanLearners>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlanLearners> findNextOpenLinkByPipId(Long pipId) throws Exception {
	 	String hql = "select o from ProjectImplementationPlanLearners o where o.projectImplementationPlan.id = :pipId and o.companyLearners is null order by o.learnerNumber asc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("pipId", pipId);
		return (List<ProjectImplementationPlanLearners>)super.getList(hql, parameters, 1);
	}
	
	public Integer countByCompanyLearnerAndPip(Long companyLearnerId, Long pipId) throws Exception {
	 	String hql = "select count(o) from ProjectImplementationPlanLearners o where o.projectImplementationPlan.id = :pipId and o.companyLearners.id = :companyLearnerId " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("pipId", pipId);
	    parameters.put("companyLearnerId", companyLearnerId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public ProjectImplementationPlanLearners findByCompanyLearnerAndPip(Long companyLearnerId, Long pipId) throws Exception {
		String hql = "select o from ProjectImplementationPlanLearners o left join fetch o.companyLearners cl where o.projectImplementationPlan.id = :pipId and o.companyLearners.id = :companyLearnerId " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("pipId", pipId);
	    parameters.put("companyLearnerId", companyLearnerId);
		return (ProjectImplementationPlanLearners)super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlanLearners> findAllEntriesLinkedToCompanyByWsp(Long companyId) throws Exception {
	 	String hql = "select o from ProjectImplementationPlanLearners o where o.wsp.company.id = :companyId and o.wsp.finYear <> 2019 order by o.learnerNumber asc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("companyId", companyId);
		return (List<ProjectImplementationPlanLearners>)super.getList(hql, parameters);
	}
}

