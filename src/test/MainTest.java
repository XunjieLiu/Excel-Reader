package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import service.Main;

public class MainTest {
	

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIsValid() {
		assertTrue(Main.isValid(""));
		assertTrue(Main.isValid(" "));
		assertTrue(Main.isValid("s"));
	}

	@Test
	public void testIsNumber() {
		assertEquals(0, Main.isNumber(""));
		assertEquals(1, Main.isNumber(" 1"));
		assertEquals(1, Main.isNumber("1"));
		assertEquals(1, Main.isNumber("1 "));
		assertEquals(1, Main.isNumber(" 1 "));
		assertEquals(0, Main.isNumber("      "));
		assertEquals(0, Main.isNumber("1  3"));
	}

}
