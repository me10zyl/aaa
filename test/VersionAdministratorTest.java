import static org.junit.Assert.*;
import net.xicp.zyl_me.util.VersionAdministrator;
import net.xicp.zyl_me.util.VersionInfomation;

import org.junit.Test;


public class VersionAdministratorTest {
	@Test
	public void testDetect() {
		VersionAdministrator versionAdministrator = new VersionAdministrator();
		VersionInfomation versionInformation = versionAdministrator.detectVersion();
		System.out.println(versionInformation);
	}
}
