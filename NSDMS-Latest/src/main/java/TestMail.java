import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;

public class TestMail {

	public static void main(String[] args) {
		try {

			System.out.println("TEST MAIL");//changed
			String mailServer = "mail.merseta.org.za";// ((java.util.Properties)
			String uid = "nsdms@merseta.org.za";// ((java.util.Properties)
			final String pwd = "Merseta1!";// ((java.util.Properties)
			int portS = 443;// ((java.util.Properties)
			String port = "443";

			HtmlEmail email = new HtmlEmail();
			email.setHostName(mailServer);						
			email.setAuthenticator(new DefaultAuthenticator(uid, pwd));
			email.setDebug(true);
			email.addTo("test@a.com");
			email.setFrom(uid);
			email.setSubject("subject");
			email.setHtmlMsg("<h1>hello</h1>");
			email.send();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
