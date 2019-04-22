package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import entity.Tweet;
import service.Menu;

class MenuTest {
	private Tweet testTweet = new Tweet();

	@Before
	void setUp() throws Exception {
		testTweet.setContent("This is test");
	}

	@Test
	void testMatch() {
		assertEquals(true, Menu.match(testTweet, "is"));
		assertEquals(true, Menu.match(testTweet, ""));
		assertEquals(true, Menu.match(testTweet, " "));
		assertEquals(true, Menu.match(testTweet, "   "));
		assertEquals(false, Menu.match(testTweet, "a"));
		assertEquals(true, Menu.match(testTweet, " s"));
	}

}
