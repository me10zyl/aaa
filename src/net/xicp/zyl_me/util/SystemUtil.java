package net.xicp.zyl_me.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;

public class SystemUtil {
	public static String getIPAddress() throws UnknownHostException, SocketException {
		String addressesStr = "";
		for(InetAddress address : getIPAddress_())
		{
			addressesStr += address.getHostAddress() + ",";
		}
		return addressesStr.substring(0, addressesStr.length() - 1);
	}
	
	private static ArrayList<InetAddress> getIPAddress_() throws SocketException
	{
		ArrayList<InetAddress> arr = new ArrayList<InetAddress>();
		Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
		Enumeration<InetAddress> addresses;
		while (networkInterfaces.hasMoreElements()) {
			NetworkInterface networkinterface = networkInterfaces.nextElement();
			addresses = networkinterface.getInetAddresses();
			while (addresses.hasMoreElements()) {
				InetAddress address = addresses.nextElement();
				if (address instanceof Inet4Address && !address.getHostAddress().equals("127.0.0.1"))
				{
					if(!networkinterface.getDisplayName().contains("WiFi")) //avoid send wifi ip
					{
						arr.add(address);
					}
				}
			}
		}
		return arr;
	}
	
	private static ArrayList<String> getMACAddress_() throws SocketException
	{
		ArrayList<String> arr = new ArrayList<String>();
		ArrayList<InetAddress> ipAddresses = getIPAddress_();
		for(InetAddress ipAddress : ipAddresses)
		{
			byte[] macBytes = NetworkInterface.getByInetAddress(ipAddress).getHardwareAddress();
			StringBuffer sb = new StringBuffer();
		    for (int i = 0; i < macBytes.length; i++)
		    {
		      String s = Integer.toHexString(macBytes[i] & 0xFF);
		      sb.append(s.length() == 1 ? 0 + s : s);
		    }
		    arr.add(sb.toString());
		}
		return arr;
	}
	public static String getMACAddress() throws SocketException
	{
		String addressesStr = "";
		for(String address : getMACAddress_())
		{
			addressesStr += address + ",";
		}
		return addressesStr.substring(0, addressesStr.length() - 1);
	}
	
	public static String getComputerName()
	{
		Map<String, String> map = System.getenv();
		return (String)map.get("COMPUTERNAME");
	}
}
