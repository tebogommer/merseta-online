package haj.com.dao;

import java.math.BigInteger;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.entity.SkillsRegistration;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.SkillsTypeEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class SkillsRegistrationDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SkillsRegistration
 	 * @author TechFinium 
 	 * @see    SkillsRegistration
 	 * @return a list of {@link haj.com.entity.SkillsRegistration}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SkillsRegistration> allSkillsRegistration() throws Exception {
		return (List<SkillsRegistration>)super.getList("select o from SkillsRegistration o");
	}

	/**
	 * Get all SkillsRegistration between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    SkillsRegistration
 	 * @return a list of {@link haj.com.entity.SkillsRegistration}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SkillsRegistration> allSkillsRegistration(Long from, int noRows) throws Exception {
	 	String hql = "select o from SkillsRegistration o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SkillsRegistration>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    SkillsRegistration
 	 * @return a {@link haj.com.entity.SkillsRegistration}
 	 * @throws Exception global exception
 	 */
	public SkillsRegistration findByKey(Long id) throws Exception {
		
	 	String hql = "select o from SkillsRegistration o "
	 				+ "left join fetch o.company c "
	 				+ "left join fetch o.qualification q "
	 				+ "left join fetch o.ofoCodes ofo "
	 				+ "left join fetch o.skillsIdentification si "
	 				+ "where o.id = :id ";
	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SkillsRegistration)super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsRegistration> findByCompany(Long companyID) throws Exception {
		
	 	String hql = "select o from SkillsRegistration o "
	 				+ "left join fetch o.company c "
	 				+ "left join fetch o.qualification q "
	 				+ "left join fetch o.ofoCodes ofo "
	 				+ "left join fetch o.skillsIdentification si "
	 				+ "where o.company.id = :companyID";
	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyID", companyID);
		return (List<SkillsRegistration>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsRegistration> findByUser(Long userId) throws Exception {
		
	 	String hql = "select o from SkillsRegistration o "
	 				+ "left join fetch o.company c "
	 				+ "left join fetch o.qualification q "
	 				+ "left join fetch o.ofoCodes ofo "
	 				+ "left join fetch o.skillsIdentification si "
	 				+ "where o.user.id = :userId";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userId", userId);
		return (List<SkillsRegistration>)super.getList(hql, parameters);
	}

	/**
	 * Find SkillsRegistration by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SkillsRegistration
  	 * @return a list of {@link haj.com.entity.SkillsRegistration}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SkillsRegistration> findByName(String description) throws Exception {
	 	String hql = "select o from SkillsRegistration o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SkillsRegistration>)super.getList(hql, parameters);
	}
	
	public boolean findSkillsByUnitStandard(String standard, SkillsTypeEnum ste) throws Exception {
	 	String hql = "select count(*) = 0 from (select count(*) > 0 from #TABLE# group by #TABLE_ID# having group_concat(unit_standards_id) = :usID) as d" ;
	 	switch (ste) {
			case SkillsSet:
				hql = hql.replace("#TABLE#", "skills_set_unit_standards");//skills_program_id skills_program_unit_standards
				hql = hql.replace("#TABLE_ID#", "skills_set_id");
				break;

			default:
				hql = hql.replace("#TABLE#", "skills_program_unit_standards");//skills_program_id skills_program_unit_standards
				hql = hql.replace("#TABLE_ID#", "skills_program_id");
				break;
		}
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("usID", standard);
	    BigInteger bi = ((java.math.BigInteger)super.nativeSelectSqlUniqueResult(hql, parameters));
		return (bi.intValue() == 1);		
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsRegistration> findListLastApproved() throws Exception {
		ApprovalEnum approvalEnum=ApprovalEnum.Approved;
	 	String hql = "select o from SkillsRegistration o where o.approvalEnum =:approvalEnum order by o.approvalDate desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("approvalEnum", approvalEnum);
		return (List<SkillsRegistration>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsRegistration> skillsByYearAndMonth(Class<SkillsRegistration> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String searchType) throws Exception {
		
		String hql = "select o from SkillsRegistration o where month(createDate) =:month and year(createDate) =:year";
		if(searchType.equalsIgnoreCase("MON")){
			hql = "select o from SkillsRegistration o where month(createDate) =:month";
		}
		else if(searchType.equalsIgnoreCase("YR")){
			hql = "select o from SkillsRegistration o where year(createDate) =:year";
		}
		return (List<SkillsRegistration>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countSkillsByYearAndMonth(Map<String, Object> filters, String searchType) throws Exception {
		String hql = "select count(o) from SkillsRegistration o where month(createDate) =:month and year(createDate) =:year";
		if(searchType.equalsIgnoreCase("MON")){
			hql = "select count(o) from SkillsRegistration o where month(createDate) =:month";
		}
		else if(searchType.equalsIgnoreCase("YR")){
			hql = "select count(o) from SkillsRegistration o where year(createDate) =:year";
		}
		
		return countWhere(filters, hql);
	}
}

