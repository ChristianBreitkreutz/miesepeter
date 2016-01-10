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
		ArrayList<miese_tleDTO> testValue = tleP.parseFile(file);
		Assert.assertEquals("IF", testValue.get(0).getTleType());
		Assert.assertEquals(1, testValue.get(0).getLineNumber());
		
		Assert.assertEquals("LOCAL", testValue.get(1).getTleType());
		Assert.assertEquals(5, testValue.get(1).getLineNumber()); // local in line 5
		
		Assert.assertEquals("IF", testValue.get(2).getTleType());
		Assert.assertEquals(6, testValue.get(2).getLineNumber()); // if in line 6
	}

}
