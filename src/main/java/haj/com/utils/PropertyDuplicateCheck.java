package haj.com.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import haj.com.constants.HAJConstants;

public class PropertyDuplicateCheck {

	public static void main(String[] args) {
		PropertyDuplicateCheck.checkProps();
	}

	/**
	 * @param args
	 */
	public static void checkProps() {
		FileInputStream fis = null;
		BufferedWriter writer = null;
		String EQUALs = "=";
		try {
			fis = new FileInputStream("/Users/wesleypirie/Documents/Bitbucket/merSETA/MerSETA/src/main/resources/techfinium/lang/language.properties");
			writer = new BufferedWriter(new FileWriter(new File("/Users/wesleypirie/Documents/Bitbucket/merSETA/MerSETA/src/main/resources/techfinium/lang/language2.properties")));
			Set<String> set = new TreeSet<String>();
			Map<String, Integer> map = new TreeMap<String, Integer>();

			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String strLine;
			String keyValuePair[] = null;
			while ((strLine = br.readLine()) != null) {
				if (strLine.contains(EQUALs)) {
					keyValuePair = strLine.split(EQUALs);
					String key = keyValuePair[0].trim();
					if (key.equals("site.visit.report.workplace.suitability.description.1")) {
						System.out.println();
					}
					if (!set.add(key)) {
						if (map.containsKey(key)) {
							map.put(key, map.get(key).intValue() + 1);
						} else {
							map.put(key, 2);
						}
					} else {
						writer.write(strLine);
						writer.newLine();
					}

				}
			}
			System.out.println("Print duplicate entry with duplication count:");
			map.forEach((k, v) -> System.out.println(v + "\t" + k));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		} finally {
			try {
				fis.close();
				writer.flush();
				writer.close();
			} catch (Exception e) {
				// do nothing
			}
		}

	}

}
