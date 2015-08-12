package com.anemortalkid.eventman.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;

public class ICalendarExample {

	private static final String $1 = "janmonterrubio@gmail.com";

	public static VEvent getMeetingICS() {

		// Initilize values
		String calFile = "TestCalendar.ics";

		// start time
		java.util.Calendar startCal = java.util.Calendar.getInstance();
		startCal.set(2015, 8, 13, 20, 20, 00);

		// end time
		java.util.Calendar endCal = java.util.Calendar.getInstance();
		endCal.set(2015, 8, 15, 20, 20, 00);

		String subject = "Meeting Subject";
		String location = "Location - Yoloville";
		String description = "This goes in decription section of the metting like agenda etc.";

		String hostEmail = $1;

		// Creating a new calendar
		net.fortuna.ical4j.model.Calendar calendar = new net.fortuna.ical4j.model.Calendar();
		calendar.getProperties().add(
				new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);

		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd'T'hhmmss'Z'");
		String strDate = sdFormat.format(startCal.getTime());

		net.fortuna.ical4j.model.Date startDt = null;
		try {
			startDt = new net.fortuna.ical4j.model.Date(strDate,
					"yyyyMMdd'T'hhmmss'Z'");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long diff = endCal.getTimeInMillis() - startCal.getTimeInMillis();
		int min = (int) (diff / (1000 * 60));

		Dur dur = new Dur(0, 0, min, 0);

		// Creating a meeting event
		VEvent meeting = new VEvent(startDt, dur, subject);

		meeting.getProperties().add(new Location(location));
		meeting.getProperties().add(new Description());

		try {
			meeting.getProperties().getProperty(Property.DESCRIPTION)
					.setValue(description);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {
			meeting.getProperties().add(new Organizer("MAILTO:" + hostEmail));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		calendar.getComponents().add(meeting);

		FileOutputStream fout = null;

		try {
			fout = new FileOutputStream(calFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		CalendarOutputter outputter = new CalendarOutputter();
		outputter.setValidating(false);

		try {
			outputter.output(calendar, fout);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			e.printStackTrace();
		}

		System.out.println(meeting);
		return meeting;
	}

	public static void main(String[] args) {
		getMeetingICS();

		// compare to
		StringBuffer buffer = new StringBuffer()
				.append("BEGIN:VCALENDAR\n"
						+ "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n"
						+ "VERSION:2.0\n"
						+ "METHOD:REQUEST\n"
						+ "BEGIN:VEVENT\n"
						+ "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:xx@xx.com\n"
						+ "ORGANIZER:MAILTO:xx@xx.com\n"
						+ "DTSTART:20051208T053000Z\n"
						+ "DTEND:20051208T060000Z\n"
						+ "LOCATION:Conference room\n"
						+ "TRANSP:OPAQUE\n"
						+ "SEQUENCE:0\n"
						+ "UID:040000008200E00074C5B7101A82E00800000000002FF466CE3AC5010000000000000000100\n"
						+ " 000004377FE5C37984842BF9440448399EB02\n"
						+ "DTSTAMP:20051206T120102Z\n"
						+ "CATEGORIES:Meeting\n"
						+ "DESCRIPTION:This the description of the meeting.\n\n"
						+ "SUMMARY:Test meeting request\n" + "PRIORITY:5\n"
						+ "CLASS:PUBLIC\n" + "BEGIN:VALARM\n"
						+ "TRIGGER:PT1440M\n" + "ACTION:DISPLAY\n"
						+ "DESCRIPTION:Reminder\n" + "END:VALARM\n"
						+ "END:VEVENT\n" + "END:VCALENDAR");
		System.out.println(buffer.toString());
	}
}
