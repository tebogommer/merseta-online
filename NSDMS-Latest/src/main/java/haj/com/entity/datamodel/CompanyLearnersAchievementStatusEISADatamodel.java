package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.AdministrationOfAQP;
import haj.com.entity.AdministrationOfAQPLearners;
import haj.com.entity.CompanyLearnersAchievementStatusEISA;
import haj.com.service.CompanyLearnersAchievementStatusEISAService;

public class CompanyLearnersAchievementStatusEISADatamodel extends LazyDataModel<CompanyLearnersAchievementStatusEISA> {

	private static final long serialVersionUID = 1L;
	private List<CompanyLearnersAchievementStatusEISA> retorno = new ArrayList<CompanyLearnersAchievementStatusEISA>();
	private CompanyLearnersAchievementStatusEISAService service = new CompanyLearnersAchievementStatusEISAService();
	private AdministrationOfAQP administrationOfAQP;

	public CompanyLearnersAchievementStatusEISADatamodel(AdministrationOfAQP administrationOfAQP) {
		super();
		this.administrationOfAQP = administrationOfAQP;
	}

	@Override
	public List<CompanyLearnersAchievementStatusEISA> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			filters.put("administrationOfAQPID", administrationOfAQP.getId());
			retorno = service.allCompanyLearnersAchievementStatusEISA(first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(CompanyLearnersAchievementStatusEISA obj) {
		return obj.getId();
	}

	@Override
	public CompanyLearnersAchievementStatusEISA getRowData(String rowKey) {
		for (CompanyLearnersAchievementStatusEISA obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
