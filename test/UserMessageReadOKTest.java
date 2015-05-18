import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import net.xicp.zyl_me.entity.MessageReadOKResponse;
import net.xicp.zyl_me.entity.UserMessageReadOKRequest;
import net.xicp.zyl_me.exception.CannotConnectToServerException;
import net.xicp.zyl_me.soap.UserMessageReadOK;

import org.dom4j.DocumentException;
import org.junit.Test;


public class UserMessageReadOKTest {
	@Test
	public void testUserMessageRead() throws UnsupportedEncodingException, NoSuchAlgorithmException, DocumentException, IOException, CannotConnectToServerException
	{
		UserMessageReadOK userMessageOK = new UserMessageReadOK(new UserMessageReadOKRequest("1233304", "123123", "213131"));
		MessageReadOKResponse messageReadOKResponse = userMessageOK.userMessageReadOK();
		System.out.println(messageReadOKResponse.getResponse());
		System.out.println(messageReadOKResponse.getMessageReadOKResult() + "," + messageReadOKResponse.getErrInfo());
	}
}
