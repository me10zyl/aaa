import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import net.xicp.zyl_me.util.BASE64;
import net.xicp.zyl_me.util.EncyptUtil;

import org.junit.Test;


public class EncyptUtilTest {
	@Test
	public void testDecode() throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		String encyptPassword = EncyptUtil.encyptPassword("", "123102303");
		System.out.println(encyptPassword);
	}
}
