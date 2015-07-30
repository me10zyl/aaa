import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import net.xicp.zyl_me.util.SystemUtil;

import org.junit.Test;


public class SystemUtilTest {
	@Test
	public void testGetIPAddress() throws UnknownHostException, SocketException
	{
		String ipAddress = SystemUtil.getIPAddress();
		System.out.println(ipAddress);
	}
	
	@Test
	public void testGetWiFiIPAddress() throws SocketException, UnknownHostException
	{
		ArrayList<InetAddress> wiFiIPAddress = SystemUtil.getWiFiIPAddress();
//		for(InetAddress wifInetAddress : wiFiIPAddress)
//		{
//			System.out.println(wifInetAddress.getHostAddress());
//		}
		System.out.println(SystemUtil.getWiFiIPAddressStr());
	}
}
