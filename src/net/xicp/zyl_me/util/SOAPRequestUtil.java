package net.xicp.zyl_me.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JOptionPane;

import net.xicp.zyl_me.entity.Response;

public class SOAPRequestUtil {
	public enum RequestAction {
		login, keepSession, logout
	}

	public static Response request(RequestAction action, String message) {
		Response response = null;
		HttpURLConnection connection = null;
		OutputStream os = null;
		InputStream is = null;
		String responseStr = null;
		int responseCode = 0;
		try {
			connection = (HttpURLConnection) new URL("http://100.0.0.10/NSUAAAWS/Default.asmx").openConnection();
			switch (action) {
			case login:
				connection.setRequestProperty("SOAPAction", "http://tempuri.org/Login");
				break;
			case keepSession:
				connection.setRequestProperty("SOAPAction", "http://tempuri.org/KeepSession");
				break;
			case logout:
				connection.setRequestProperty("SOAPAction", "http://tempuri.org/Logout");
				break;
			default:
				throw new RuntimeException("请传入requestAction!");
			}
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; MS Web Services Client Protocol 4.0.30319.18408)");
			connection.addRequestProperty("Expect", "100-Continue");
			connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(5000);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			os = connection.getOutputStream();
			os.write(message.getBytes());
			os.flush();
			is = connection.getInputStream();
			responseStr = InputStreamUtil.getString(is);
			responseCode = connection.getResponseCode();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "无法连接到服务器");
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		response = new Response(responseCode, responseStr);
		return response;
	}
}
