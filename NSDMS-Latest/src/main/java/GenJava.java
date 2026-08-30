import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import haj.com.datatakeon.GenericDAO;
import haj.com.sars.SARSConstants;
import haj.com.sars.SarsLevyDetails;
import haj.com.service.SarsEmployerDetailService;
import haj.com.service.SarsLevyDetailsService;
import haj.com.utils.CSVUtil;

public class GenJava {

	public static String convertToJava(String text) {
		String j = text.trim().replaceAll("_", " ");
		j = WordUtils.capitalizeFully(j).replaceAll(" ", "");
		j = j.substring(0, 1).toLowerCase() + j.substring(1);
		return "private String " + j.trim() + ";";
	}

	public static void convertLevyHeader() {
		String x = "ARRIVAL_DATE_1 SETA_CODE REF_NO ARRIVAL_DATE_2 MANDATORY_LEVY DISCRETIONARY_LEVY ADMIN_LEVY INTEREST PENALTY TOTAL SARS_CONTROL_DIGIT_1	 SARS_CONTROL_DIGIT_2 SCHEME_YEAR";
		String[] y = x.split(" ");
		String header = "";
		for (String s : y) {

			if (!StringUtils.isBlank(s.trim())) {
				System.out.println("@CSVAnnotation(name = \"" + s.trim() + "\", className = String.class)");
				System.out.println("@Column(name = \"" + s.trim().toLowerCase() + "\")");
				System.out.println(convertToJava(s));
				System.out.println();
				header = header + s.trim() + "|";
			}
		}
		System.out.println(header);
	}

	public static void convertEmployerHeader() {
		String x = "REF_NO	SARS_DATA	SARS_DATA	SARS_DATA	REGISTERED_NAME_OF_ENTITY	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	COMPANY_REGISTRATION_NUMBER	SARS_CODE	SARS_CODE	SARS_CODE	SARS_CODE	SARS_CODE	SARS_CODE	SARS_CODE	SARS_CODE	SARS_CODE	SARS_CODE	SARS_CODE	SARS_CODE	SARS_CODE	SARS_CODE	SARS_CODE	SARS_CODE	TRADING_NAME	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SIC_CODE_2	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	SARS_DATA	TRADING_STATUS	SARS_DATA	SARS_DATA	SARS_DATA";
		x = x.replaceAll("\t", " ");
		System.out.println(x);
		String[] y = x.split(" ");
		String header = "";
		int code = 1, data = 1;
		boolean jpa = false;
		for (String s : y) {
			jpa = true;
			if (!StringUtils.isBlank(s.trim())) {
				if ("SARS_CODE".equals(s.trim())) {
					s = s.trim() + "_" + code;
					code++;
					jpa = false;
				}
				if ("SARS_DATA".equals(s.trim())) {
					s = s.trim() + "_" + data;
					data++;
					jpa = false;
				}

				System.out.println("@CSVAnnotation(name = \"" + s.trim() + "\", className = String.class)");
				if (jpa) System.out.println("@Column(name = \"" + s.trim().toLowerCase() + "\")");
				else System.out.println("@Transient");
				System.out.println(convertToJava(s));
				System.out.println();
				header = header + s.trim() + "|";
			}
		}
		System.out.println(header);
	}

	@SuppressWarnings("unchecked")
	public static void loadLevies(String file) {
		try {
			CSVUtil csvUtil = new CSVUtil();

			String sdl = new String(Files.readAllBytes(new File(file).toPath()), Charset.forName("UTF-8"));
			String sdlF = SARSConstants.LEVY_HEADING + "\n" + sdl;

			sdlF = sdlF.replaceAll("\\|", ";");
			GenericDAO dao = new GenericDAO();
			List<SarsLevyDetails> levies = (List<SarsLevyDetails>) (List<?>) csvUtil.getObjects(SarsLevyDetails.class, new ByteArrayInputStream(sdlF.getBytes()), ";");
			levies.forEach(a -> {
				try {
					dao.create(a);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadLevies() throws Exception {
		try (Stream<Path> stream = Files.list(Paths.get("/Users/hendrik/Downloads/17-sdl/"))) {
			List<String> l = stream.map(String::valueOf).filter(path -> (path.endsWith(".SDL") && !path.contains("Employers"))).sorted() // .collect(Collectors.joining("; "));
					.collect(Collectors.toList());

			l.forEach(a -> {
				loadLevies(a);
			});
		}
	}

	public static void resolveFK() throws Exception {
		SarsLevyDetailsService service = new SarsLevyDetailsService();
		SarsEmployerDetailService empservice = new SarsEmployerDetailService();
		// List<SarsLevyDetails> l = service.allSarsLevyDetailsNoFK();
		// for (SarsLevyDetails sarsLevyDetails : l) {
		// sarsLevyDetails.setSarsEmployerDetail(empservice.findByRefNo(sarsLevyDetails.getRefNo()));
		// service.update(sarsLevyDetails);
		// }
	}

	public static void main(String[] args) {
		// convertEmployerHeader();
		System.out.println("Start");

		try {
			resolveFK();
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * try { String sdl = new String (Files.readAllBytes(new
		 * File("/Users/hendrik/Downloads/17-sdl/17_Employers_201708.SDL").toPath()),
		 * Charset.forName("UTF-8"));
		 * 
		 * String sdlF = SARSConstants.EMPLOYER_HEADING + "\n"+sdl;
		 * 
		 * sdlF = sdlF.replaceAll( "\\|", ";"); List<SarsEmployerDetail> levies =
		 * (List<SarsEmployerDetail>) (List<?>)
		 * CSVUtil.getObjects(SarsEmployerDetail.class, new
		 * ByteArrayInputStream(sdlF.getBytes()), ";"); //
		 * System.out.println(levies.size()); GenericDAO dao = new GenericDAO();
		 * levies.forEach(a->{ try { dao.create(a); } catch (Exception e) {
		 * e.printStackTrace(); } }); } catch (Exception e) { e.printStackTrace(); }
		 */
		System.out.println("Done");
	}

}
