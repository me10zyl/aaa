import java.io.UnsupportedEncodingException;

import net.xicp.zyl_me.util.BASE64;

import org.junit.Test;


public class BASE64Test {
	
	@Test
	public void testDecode()
	{
		String encode = BASE64.encode("".getBytes());
		System.out.println(encode);
		System.out.println(BASE64.decode(encode.getBytes()));
	}
}
