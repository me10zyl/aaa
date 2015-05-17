import java.io.IOException;
import java.text.ParseException;

import net.xicp.zyl_me.util.ExternalTools;

import org.junit.Test;


public class TestExternalTools {
	
	@Test
	public void testShutdown() throws IOException
	{
		ExternalTools externalTool = new ExternalTools();
		externalTool.shutdown();
	}
	
	@Test
	public void testShutdownCancel() throws IOException
	{
		ExternalTools.shutdownCancel();
	}
	
	@Test
	public void testShutdownAtTime() throws IOException, ParseException
	{
		ExternalTools.shutdownAtTime("23:00");
	}
	
}
