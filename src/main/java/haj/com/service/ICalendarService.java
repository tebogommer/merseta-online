package haj.com.service;

import java.util.Date;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VAlarm;
import biweekly.component.VEvent;
import biweekly.parameter.Related;
import biweekly.property.Trigger;
import biweekly.util.Duration;
import haj.com.framework.AbstractService;

public class ICalendarService extends AbstractService {

	private SendMail sendMail = new SendMail();

	public String createMeetingBooking(String summary, String description, String location, Date startDateTime, int durationHours, int alarmMinutes) throws Exception {
		ICalendar ical = new ICalendar();
		VEvent event = new VEvent();
		event.setSummary(summary);
		event.setCreated(new Date());
		event.setDescription(description);
		event.setLocation(location);
		event.setDateStart(startDateTime);

		Duration duration = new Duration.Builder().hours(durationHours).build();
		event.setDuration(duration);

		// 15 minutes before the start time
		Duration duration2 = Duration.builder().prior(true).minutes(alarmMinutes).build();
		Trigger trigger = new Trigger(duration2, Related.START);
		VAlarm alarm = VAlarm.display(trigger, summary + " (in " + alarmMinutes + " minutes)");
		event.addAlarm(alarm);
		ical.addEvent(event);

		String str = Biweekly.write(ical).go();
		return str;
	}


	public void sendMeetingBooking(String summary, String description, String location, Date startDateTime,
		int durationHours, int alarmMinutes,
		String to_address,String subject, String text) throws Exception {
		String ics = createMeetingBooking(summary, description, location, startDateTime, durationHours, alarmMinutes);
		sendMail.sendMeeting(to_address, subject, text, ics);
	}
}
