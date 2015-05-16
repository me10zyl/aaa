package net.xicp.zyl_me.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class InputStreamUtil {
	public static byte[] getBytes(String path) throws IOException
	{
		InputStream is = InputStreamUtil.class.getClassLoader().getResourceAsStream(path);
		return getBytes(is);
	}
	
	public static String getString(String path) throws UnsupportedEncodingException, IOException
	{
		return new String(getBytes(path),"UTF-8");
	}
	
	public static byte[] getBytes(InputStream is) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while((len = is.read(buffer)) != -1)
		{
			baos.write(buffer,0,len);
		}
		baos.close();
		is.close();
		return baos.toByteArray();
	}
	
	public static String getString(InputStream is) throws UnsupportedEncodingException, IOException
	{
		return new String(getBytes(is),"UTF-8");
	}
}
