package miesepeter;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import junit.framework.Assert;

public class test_tleparser {

	@Test
	public void loadFile() {
		File file = new File("/root/git/miesepeter/miesepeter/src/test/resources/dummyTLE.html");
		miese_tleparser tleP = new miese_tleparser();
		ArrayList<Integer> testValue = tleP.parseFile(file);
		Assert.assertEquals(1, (int)testValue.get(0));
		Assert.assertEquals(3, (int)testValue.get(1));
	}

}
