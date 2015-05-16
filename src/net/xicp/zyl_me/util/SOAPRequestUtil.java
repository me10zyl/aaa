package net.xicp.zyl_me.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import net.xicp.zyl_me.entity.Response;

public class SOAPRequestUtil {
	public enum RequestAction {
		login, keepSession, logout
	}

	public static Response request(RequestAction action, String message) throws IOException {
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
				throw new RuntimeException("Çë´«ÈërequestAction!");
			}
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; MS Web Services Client Protocol 4.0.30319.18408)");
			connection.setRequestProperty("Pragma", null);
			connection.setRequestProperty("Expect", "100-Continue");
			os = connection.getOutputStream();
			os.write(message.getBytes());
			is = connection.getInputStream();
			responseCode = connection.getResponseCode();
			responseStr = InputStreamUtil.getString(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}
			if (is != null) {
				is.close();
			}
		}
		response = new Response(responseCode, responseStr);
		return response;
	}
}
