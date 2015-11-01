package io.github.iTitus.gimmetime.client.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.github.iTitus.gimmetime.common.handler.ConfigHandler;
import io.github.iTitus.gimmetime.common.util.StringUtil;

public class TimeUtil {

	public static DateFormat dateFormat = new SimpleDateFormat();

	public static int convertToAMPMIfNecessary(int hour) {
		if (!ConfigHandler.am_pm || (hour <= 12 && hour > 0))
			return hour;
		if (hour == 0)
			return 12;
		return hour - 12;
	}

	public static String[] getAllHours() {

		String[] hours = new String[24];

		for (int i = 0; i < hours.length; i++) {
			hours[i] = make2Digits(convertToAMPMIfNecessary(i))
					+ (ConfigHandler.am_pm ? ((i >= 12) ? " PM" : " AM") : "");
		}

		return hours;
	}

	public static String[] getAllMins() {

		String[] mins = new String[60];

		for (int i = 0; i < mins.length; i++) {
			mins[i] = make2Digits(i);
		}

		return mins;
	}

	public static String getAMPM() {
		return (isPM()) ? ("PM") : ("AM");
	}

	public static int getHour() {
		return Calendar.getInstance().get(Calendar.HOUR);
	}

	public static int getHourOfDay() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}

	public static int getMin() {
		return Calendar.getInstance().get(Calendar.MINUTE);
	}

	public static int getSec() {
		return Calendar.getInstance().get(Calendar.SECOND);
	}

	public static int getMillis() {
		return Calendar.getInstance().get(Calendar.MILLISECOND);
	}

	public static String getTime() {
		return getTime(ConfigHandler.separator, ConfigHandler.am_pm, ConfigHandler.seconds);
	}

	public static String getTime(String separator, boolean am_pm, boolean seconds) {
		StringBuilder sb = new StringBuilder();
		sb.append(make2Digits(am_pm ? getHour() : getHourOfDay()));
		sb.append(separator);
		sb.append(make2Digits(getMin()));
		if (seconds) {
			sb.append(separator);
			sb.append(make2Digits(getSec()));
		}
		if (am_pm)
			sb.append(" " + getAMPM());
		return sb.toString();
	}

	public static String getAlarmTimeString() {
		return getTimeString(getHourOfDay(), getMin(), ConfigHandler.am_pm);
	}

	public static String getTimeString(int hour, int min) {
		return getTimeString(hour, min, ConfigHandler.am_pm);
	}

	public static String getTimeString(int hour, int min, boolean am_pm) {
		StringBuilder sb = new StringBuilder();
		sb.append(make2Digits(convertToAMPMIfNecessary(hour)));
		sb.append(ConfigHandler.separator);
		sb.append(make2Digits(min));
		if (am_pm)
			sb.append(" " + ((hour >= 12) ? ("PM") : ("AM")));
		return sb.toString();
	}

	public static boolean isHour(int hour) {
		return getHourOfDay() == hour;
	}

	public static boolean isMin(int min) {
		return getMin() == min;
	}

	public static boolean isPM() {
		return Calendar.getInstance().get(Calendar.AM_PM) == Calendar.PM;
	}

	public static String make2Digits(int number) {
		return StringUtil.makeNDigits(number + "", 2, '0');
	}

}
