package net.xicp.zyl_me.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExternalTools {
	public static void shutdown() throws IOException
	{
		Runtime runtime = Runtime.getRuntime();
		runtime.exec("shutdown -s");
	}
	
	public static void shutdownCancel() throws IOException
	{
		Runtime runtime = Runtime.getRuntime();
		runtime.exec("shutdown -a");
	}
	
	public static void shutdownAtTime(String time) throws IOException, ParseException
	{
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date(System.currentTimeMillis());
		Date date2 = simpleDateFormat.parse(year+"-"+(month + 1) +"-" + day + " " + time.trim());
		long differece = (long) Math.ceil((date2.getTime() - date.getTime()) / 1000.0);
		Runtime runtime = Runtime.getRuntime();
		runtime.exec("shutdown -s -t " + differece);
	}
}
