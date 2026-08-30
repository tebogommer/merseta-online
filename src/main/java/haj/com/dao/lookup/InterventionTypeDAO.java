package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.lookup.InterventionType;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class InterventionTypeDAO.
 */
public class InterventionTypeDAO extends AbstractDAO  {

	
	private static final String andCanSelectInterventionType = " and o.canSelect = true ";
	private static final String canSelectInterventionType = " o.canSelect = true ";
	
	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all InterventionType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.InterventionType}
	 * @throws Exception global exception
	 * @see    InterventionType
	 */
	@SuppressWarnings("unchecked")
	public List<InterventionType> allInterventionType() throws Exception {
		return (List<InterventionType>)super.getList("select o from InterventionType o where " + canSelectInterventionType);
	}

	@SuppressWarnings("unchecked")
	public List<InterventionType> allInterventionTypeNoARPL() throws Exception {
		Long appeClass2018 = 45L;
		Long arplID = 25L;
		Long rplID = 36L;
		String hql = "select o from InterventionType o where o.id not in(:arplID, :rplID, :appeClass2018) "+ andCanSelectInterventionType+" order by o.description" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("arplID",arplID);
	    parameters.put("rplID",rplID);
	    parameters.put("appeClass2018",appeClass2018);
		return (List<InterventionType>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<InterventionType> allInterventionTypeWithARPL() throws Exception {
		Long appeClass2018 = 45L;
		//Long arplID = 25L;
		//Long rplID = 36L;
		String hql = "select o from InterventionType o where o.id not in(:appeClass2018) "+ andCanSelectInterventionType+" order by o.description" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    //parameters.put("arplID",arplID);
	   // parameters.put("rplID",rplID);
	    parameters.put("appeClass2018",appeClass2018);
		return (List<InterventionType>)super.getList(hql, parameters);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<InterventionType> allInterventionTypeByNonMerseta(Boolean registrationByNonMerseta) throws Exception {
		String hql = "select o from InterventionType o where o.registrationByNonMerseta = :registrationByNonMerseta "+ andCanSelectInterventionType+" order by o.description" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("registrationByNonMerseta",registrationByNonMerseta);
		return (List<InterventionType>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<InterventionType> allInterventionTypeWithNoAccreditaionNoARPL(Boolean forSdpAccreditaion) throws Exception {
		Long appeClass2018 = 45L;
		Long arplID = 25L;
		Long rplID = 36L;
		String hql = "select o from InterventionType o where o.id not in(:arplID, :rplID, :appeClass2018) and o.forSdpAccreditaion <> :forSdpAccreditaion "+ andCanSelectInterventionType+" order by o.description" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("arplID",arplID);
	    parameters.put("rplID",rplID);
	    parameters.put("appeClass2018",appeClass2018);
	    parameters.put("forSdpAccreditaion",forSdpAccreditaion);
		return (List<InterventionType>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<InterventionType> allInterventionTypeWithNoAccreditaion(Boolean forSdpAccreditaion) throws Exception {	
		String hql = "select o from InterventionType o where o.forSdpAccreditaion <> :forSdpAccreditaion "+ andCanSelectInterventionType+" order by o.description" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("forSdpAccreditaion",forSdpAccreditaion);
		return (List<InterventionType>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<InterventionType> allInterventionTypeWithNoAccreditaionWithARPL(Boolean forSdpAccreditaion) throws Exception {
		Long appeClass2018 = 45L;
		//Long arplID = 25L;
		//Long rplID = 36L;
		String hql = "select o from InterventionType o where o.id not in(:appeClass2018) and o.forSdpAccreditaion <> :forSdpAccreditaion "+ andCanSelectInterventionType+" order by o.description" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    //parameters.put("arplID",arplID);
	   // parameters.put("rplID",rplID);
	    parameters.put("appeClass2018",appeClass2018);
	    parameters.put("forSdpAccreditaion",forSdpAccreditaion);
		return (List<InterventionType>)super.getList(hql, parameters);
	}
	
	/**
	 * Get all InterventionType between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.InterventionType}
	 * @throws Exception global exception
	 * @see    InterventionType
	 */
	@SuppressWarnings("unchecked")
	public List<InterventionType> allInterventionType(Long from, int noRows) throws Exception {
	 	String hql = "select o from InterventionType o where " + canSelectInterventionType  ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<InterventionType>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.InterventionType}
	 * @throws Exception global exception
	 * @see    InterventionType
	 */
	public InterventionType findByKey(Long id) throws Exception {
	 	String hql = "select o from InterventionType o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (InterventionType)super.getUniqueResult(hql, parameters);
	}

	public InterventionType findByKey(List<Long> id) throws Exception {
		String hql = "select o from InterventionType o where o.id in :id " ;
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (InterventionType)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find InterventionType by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.InterventionType}
	 * @throws Exception global exception
	 * @see    InterventionType
	 */
	@SuppressWarnings("unchecked")
	public List<InterventionType> findByName(String description) throws Exception {
	 	String hql = "select o from InterventionType o where o.description like  :description "+andCanSelectInterventionType+" order by o.description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<InterventionType>)super.getList(hql, parameters);
	}
	
	/**
	 * Find InterventionType by Intervention Type Enum.
	 *
	 * @author TechFinium
	 * @param InterventionTypeEnum
	 * @return a list of {@link haj.com.entity.InterventionType}
	 * @throws Exception global exception
	 * @see    InterventionType
	 */
	@SuppressWarnings("unchecked")
	public List<InterventionType> findByInterventionType(InterventionTypeEnum interventionType) throws Exception {
	 	String hql = "select o from InterventionType o where o.interventionTypeEnum = :interventionType "+andCanSelectInterventionType+" order by o.description" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("interventionType",interventionType);
		return (List<InterventionType>)super.getList(hql, parameters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the intervention type
	 * @throws Exception the exception
	 */
	public InterventionType findByCode(String code) throws Exception {
		String hql = "select o from InterventionType o where o.code = :code " + andCanSelectInterventionType;
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (InterventionType) super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find object by  code.
	 *
	 * @author TechFinium
	 * @param interventionType the intervention type
	 * @return a {@link haj.com.entity.InterventionType}
	 * @throws Exception global exception
	 * @see    InterventionType
	 */
	public InterventionType findUniqueCode(InterventionType interventionType) throws Exception {
	 	String hql = "select o from InterventionType o where o.code = :code " + andCanSelectInterventionType ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (interventionType.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", interventionType.getId());
	 	}
	    parameters.put("code", interventionType.getCode());
		return (InterventionType)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find InterventionType by InterventionTypeEnum.
	 *
	 * @author TechFinium
	 * @param InterventionTypeEnum type
	 * @return a list of {@link haj.com.entity.InterventionType}
	 * @throws Exception global exception
	 * @see    InterventionType
	 */
	@SuppressWarnings("unchecked")
	public List<InterventionType> findByInterventionTypeEnum(InterventionTypeEnum type) throws Exception {
	 	String hql = "select o from InterventionType o where o.interventionTypeEnum = :interventionEnum" + andCanSelectInterventionType;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("interventionEnum", type);
		return (List<InterventionType>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<InterventionType> allInterventionTypePivotal() throws Exception {
		String hql = "select o from InterventionType o where o.pivotNonPivot = :pivotNonPivot" + andCanSelectInterventionType;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("pivotNonPivot", PivotNonPivotEnum.Pivotal);
		return (List<InterventionType>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<InterventionType> allInterventionTypeNonPivotal() throws Exception {
		String hql = "select o from InterventionType o where o.pivotNonPivot = :pivotNonPivot" + andCanSelectInterventionType;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("pivotNonPivot", PivotNonPivotEnum.NonPivotal);
		return (List<InterventionType>)super.getList(hql, parameters);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<InterventionType> findByDescription(String description) throws Exception {
	 	String hql = "select o from InterventionType o where upper(o.description) = :description" + andCanSelectInterventionType;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", description.trim().toUpperCase());
		return (List<InterventionType>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<InterventionType> allInterventionTypeWspSelection() throws Exception {
		return (List<InterventionType>)super.getList("select o from InterventionType o where o.wspSelection = true");
	}
	
	@SuppressWarnings("unchecked")
	public List<InterventionType> allInterventionTypeAtrSelection() throws Exception {
		return (List<InterventionType>)super.getList("select o from InterventionType o where o.atrSelection = true ");
	}
	
}

