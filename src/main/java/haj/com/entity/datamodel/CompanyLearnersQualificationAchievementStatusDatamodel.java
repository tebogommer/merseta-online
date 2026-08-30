package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.AdministrationOfAQP;
import haj.com.entity.CompanyLearnersQualificationAchievementStatus;
import haj.com.service.CompanyLearnersQualificationAchievementStatusService;

public class CompanyLearnersQualificationAchievementStatusDatamodel extends LazyDataModel<CompanyLearnersQualificationAchievementStatus> {

	private static final long serialVersionUID = 1L;
	private List<CompanyLearnersQualificationAchievementStatus> retorno = new ArrayList<CompanyLearnersQualificationAchievementStatus>();
	private CompanyLearnersQualificationAchievementStatusService service = new CompanyLearnersQualificationAchievementStatusService();
	private AdministrationOfAQP administrationOfAQP;

	public CompanyLearnersQualificationAchievementStatusDatamodel(AdministrationOfAQP administrationOfAQP) {
		super();
		this.administrationOfAQP = administrationOfAQP;
	}

	@Override
	public List<CompanyLearnersQualificationAchievementStatus> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			filters.put("administrationOfAQPID", administrationOfAQP.getId());
			retorno = service.allCompanyLearnersQualificationAchievementStatus(first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(CompanyLearnersQualificationAchievementStatus obj) {
		return obj.getId();
	}

	@Override
	public CompanyLearnersQualificationAchievementStatus getRowData(String rowKey) {
		for (CompanyLearnersQualificationAchievementStatus obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
