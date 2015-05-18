import java.net.SocketException;
import java.net.UnknownHostException;

import net.xicp.zyl_me.util.SystemUtil;

import org.junit.Test;


public class SystemUtilTest {
	@Test
	public void testGetIPAddress() throws UnknownHostException, SocketException
	{
		String ipAddress = SystemUtil.getIPAddress();
		System.out.println(ipAddress);
	}
}
