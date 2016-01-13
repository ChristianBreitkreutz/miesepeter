package miesepeter;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import com.epages.sonar.miesepeter.parser.TleLine;
import com.epages.sonar.miesepeter.parser.Parser;

public class test_tleparser {

	@Test
	public void loadFile() {
		File file = new File("src/test/resources/dummyTLE.html");
		Parser tleP = new Parser();
		ArrayList<TleLine> testValue = tleP.parseFile(file);
		assertEquals("IF", testValue.get(0).getType());
		assertEquals(1, testValue.get(0).getLineNumber());

		assertEquals("LOCAL", testValue.get(1).getType());
		assertEquals(5, testValue.get(1).getLineNumber()); // local in line 5

		assertEquals("IF", testValue.get(2).getType());
		assertEquals(6, testValue.get(2).getLineNumber()); // if in line 6
	}

}
