package haj.com.datatakeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import haj.com.entity.datatakeon.TakOnUnitstandard;
import haj.com.entity.lookup.NqfLevels;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.IDataEntity;
import haj.com.service.NqfLevelsService;

// TODO: Auto-generated Javadoc
/**
 * The Class UnitStandardTakeOn.
 */
public class UnitStandardTakeOn {

	/** The dao. */
	private GenericDAO dao = new GenericDAO();
	
	/** The nqf levels service. */
	private NqfLevelsService nqfLevelsService =  new NqfLevelsService();
	
	/**
	 * Load unit standards.
	 */
	public void loadUnitStandards() {
		try {
			Map<String,NqfLevels>  m = new HashMap<String,NqfLevels>();
			List<NqfLevels> nl = nqfLevelsService.allNqfLevels();
			for (NqfLevels nqfLevel : nl) {
				m.put(nqfLevel.getDescription().trim(), nqfLevel);
			}
			
			UnitStandards us = null;
			List<IDataEntity> entityList =  new ArrayList<IDataEntity>();
			Map<String,TakOnUnitstandard>  m2 = new HashMap<String,TakOnUnitstandard>();
			List<TakOnUnitstandard> l = dao.allUnitstandard();
			for (TakOnUnitstandard uso : l) {
				if (!m2.containsKey(uso.getUnitStdDesc().trim().toUpperCase())) {
					m2.put(uso.getUnitStdDesc().trim().toUpperCase(), uso);
					us = new UnitStandards();
			//		us.setCode(""+uso.getUnitStdCode().intValue());
			//		us.setTitle(uso.getUnitStdDesc().trim());
			//		us.setNqfLevel(m.get(uso.getNqflevel().trim()));
			//		us.setCredits(uso.getCreditValue().intValue());
					entityList.add(us);
				}
			}
			dao.createBatch(entityList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.out.println("Start");
		UnitStandardTakeOn us = new UnitStandardTakeOn();
		us.loadUnitStandards();
		System.out.println("Done");
		System.exit(0);

	}

}
