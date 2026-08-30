package haj.com.service.lookup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Transient;

import org.primefaces.model.SortOrder;

import haj.com.annotations.CSVAnnotation;
import haj.com.bean.ColumnBean;
import haj.com.bean.ColumnBeans;
import haj.com.bean.EmployeeReportBean;
import haj.com.dao.ReportingDAO;
import haj.com.entity.Company;
import haj.com.entity.MailLog;
import haj.com.entity.Users;
import haj.com.framework.AbstractService;
import haj.com.utils.ReflectionUtils;

public class ReportingService extends AbstractService {

	private ReportingDAO dao = new ReportingDAO();

	public Map<String, List<Object>> runReports(Class<?> reportingClass, boolean runAsSingle) throws Exception {
		return dao.runReports(reportingClass);
	}

	public Map<String, List<Object>> runReportsDB(Class<?> reportingClass) throws Exception {
		return dao.runReportsDB(reportingClass);
	}

	public List<Object> extractValues(Map<String, List<Object>> objs, String key) throws Exception {
		List<Object> objects = objs.remove(key);// objs.get(key);
		objs.keySet().remove(key);
		return objects;
	}

	public Map<String, ColumnBeans> convertToColumns(Map<String, List<Object>> objs) throws Exception {
		Map<String, ColumnBeans> cols = new HashMap<>();

		for (Entry<String, List<Object>> entry : objs.entrySet()) {
			List<ColumnBean> objects = new ArrayList<>();
			List<Object> value = entry.getValue();
			if (!value.isEmpty()) {
				Object obj = value.get(0);
				List<Field> fields = ReflectionUtils.getFieldsWithAnnotations(obj.getClass().getDeclaredFields(), CSVAnnotation.class);
				for (Field field : fields) {
					CSVAnnotation csvAnnotation = field.getAnnotation(CSVAnnotation.class);
					Transient transientannotation = field.getAnnotation(Transient.class);
					if (transientannotation == null) {
						ColumnBean cb = new ColumnBean();
						cb.setTitle(csvAnnotation.name());
						cb.setVal(field.getName());
						objects.add(cb);
					}
				}
				cols.put(entry.getKey(), new ColumnBeans(value, objects, entry.getKey().replaceAll(" ", "")));
			}
		}
		return cols;
	}

	public List<EmployeeReportBean> allEmployeeList() throws Exception {
		return dao.allEmployeeList();
	}
	
	public List<EmployeeReportBean> allCompanyList() throws Exception {
		return dao.allCompanyList();
	}
	
	public List<EmployeeReportBean> allGrantList() throws Exception {
		return dao.allGrantList();
	}
	
	public List<EmployeeReportBean> allCLOList() throws Exception {
		return dao.allCLOList();
	}
	
	
	public List<EmployeeReportBean> allCRMList(Class<EmployeeReportBean> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return dao.allCRMList(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/*
	 * public List<Company> companiesInCloRegion(Class<Company> class1, int first,
	 * int pageSize, String sortField, SortOrder sortOrder, Map<String, Object>
	 * filters) throws Exception { return dao.companiesInCloRegion(class1, first,
	 * pageSize, sortField, sortOrder, filters); }
	 * 
	 * public int countAllcompaniesInCloRegion(Class<Company> class1, Map<String,
	 * Object> filters) throws Exception { return
	 * dao.countAllcompaniesInCloRegion(class1, filters); }
	 */
	public int countAllCRM(Class<EmployeeReportBean> entity, Map<String, Object> filters) throws Exception {
		return dao.countAllCRM(entity, filters);
	}

}
