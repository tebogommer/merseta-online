package haj.com.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import haj.com.annotations.ResolveData;
import haj.com.dao.ResolveDataDAO;
import haj.com.entity.Doc;
import haj.com.entity.Tasks;
import haj.com.framework.AbstractService;
import haj.com.utils.ReflectionUtils;

public class ResolveDataService extends AbstractService {

	/** The dao. */
	private ResolveDataDAO dao = new ResolveDataDAO();
	private List<String> alreadyVisited = new ArrayList<>();
	private String hql = "";
	private String hqlInner = "x";

	public void resolveDocs(Class<?> c) throws Exception {
		List<String> targetClasses = findAllAvailableTargetClasses();
		Map<String, List<Field>> toReturn = new LinkedHashMap<>();
		for (String targetClass : targetClasses) {
			alreadyVisited = new ArrayList<>();
			if (targetClass != null) {
				Class<?> obj = Class.forName(targetClass);
				List<Field> f = Arrays.asList(obj.getDeclaredFields());
				toReturn.put(targetClass, recursiveCompanyCheck(f, c, ""));
			}
		}
		System.out.println();
		System.out.println("-------------------------------------");

		for (Entry<String, List<Field>> field : toReturn.entrySet()) {
			System.out.println(field.getKey());
			field.getValue().forEach(f -> System.out.println(f.getDeclaringClass().getName() + "." + f.getName()));
			System.out.println("-------------------------------------");
		}
	}

	public String resolveDocs2(Class<?> c) throws Exception {
		List<String> targetClasses = findAllAvailableTargetClasses();
		hql = "select o from Doc o left join fetch o.users usr left join fetch o.company comp left join fetch o.doc dc left join fetch o.configDoc cd where ";
		getWhereClause(targetClasses);
		return hql;
	}

	public String resolveTasks(Class<?> c) throws Exception {
		List<String> targetClasses = findAllAvailableTargetClasses();
		hql = "select o from Tasks o left join fetch o.processRole pr left join fetch pr.roles roles left join fetch pr.hostingCompanyProcess hcp left join fetch o.actionUser au left join fetch o.createUser cu where ";
		getWhereClause(targetClasses);
		return hql;
	}

	private void getWhereClause(List<String> targetClasses) throws ClassNotFoundException, Exception {
		for (String targetClass : targetClasses) {
			alreadyVisited = new ArrayList<>();
			if (targetClass != null) {
				Class<?> obj = Class.forName(targetClass);
				if (obj.isAnnotationPresent(ResolveData.class)) {

					if (hql.contains("(o.targetClass =")) {
						hql += " or";
					}
					hql += " (o.targetClass = '" + targetClass + "'";

					ResolveData annotation = (ResolveData) ReflectionUtils.getAnnotationOnType(ResolveData.class, obj);
					int count = 0;
					boolean addOr = annotation.paths().length > 1;
					for (String string : annotation.paths()) {
						if (count == 0) {
							if (addOr) hql += " and (o.targetKey in (";
							else hql += " and o.targetKey in (";
							hql += addStatement(targetClass, string, count);
						} else {
							hql += " or o.targetKey in (";
							hql += addStatement(targetClass, string, count);
						}
						if (addOr) {
							hql += ")";
						}
						count++;
					}
					hql += "))";

				}
			}
		}
	}

	public List<Tasks> findCompanyTasks(long companyID, Class<?> c) throws Exception {
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("queryParam", companyID);
		List<Tasks> docs = (List<Tasks>) (List<?>) dao.queryDB(resolveTasks(c), parameters);
		return docs;
	}

	public List<Doc> findCompanyDocuments(long companyID, Class<?> c) throws Exception {
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("queryParam", companyID);
		List<Doc> docs = (List<Doc>) (List<?>) dao.queryDB(resolveDocs2(c), parameters);
		return docs;
	}

	private String addStatement(String targetClass, String string, int count) {
		String prefix = hqlInner + count;
		return "select " + prefix + ".id from " + targetClass + " " + prefix + " where " + prefix + "." + string + " = :queryParam";
	}

	private List<Field> recursiveCompanyCheck(List<Field> f, Class<?> c, String path) {
		List<Field> toReturn = new ArrayList<>();

		for (Field field : f) {
			if (path.equals("")) {
				path = "o";
			}
			try {
				if (!alreadyVisited.contains(field.getDeclaringClass().getName() + field.getName())) {
					alreadyVisited.add(field.getDeclaringClass().getName() + field.getName());
					if (field.getType().equals(c)) {
						path += "." + field.getName();
						toReturn.add(field);
						if (!path.equals("o.")) System.out.println(path);
					} else if (field.getType().getPackage().equals(c.getPackage())) {
						path += "." + field.getName();
						toReturn.addAll(recursiveCompanyCheck(Arrays.asList(field.getType().getDeclaredFields()), c, path));
					}
				}
			} catch (Exception e) {
				path = "o";
				// System.out.println("FIELD NOT A CLASS");
			}
		}
		return toReturn;
	}

	public List<String> findAllAvailableTargetClasses() {
		return dao.findAllAvailableTargetClasses();
	}
}
