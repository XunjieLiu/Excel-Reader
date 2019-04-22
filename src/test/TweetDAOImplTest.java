package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dao.TweetDAOImpl;

class TweetDAOImplTest {
	private TweetDAOImpl tweetDAO = new TweetDAOImpl();
	private String[] row;
	@Before
	void setUp() throws Exception {
		row = new String[2];
	}


	@Test
	void testGetTweet() {
		assertEquals(null, tweetDAO.getTweet(row));	
	}

	@Test
	void testIsNumber() {
		assertEquals(0, tweetDAO.isNumber(""));
		assertEquals(1, tweetDAO.isNumber(" 1"));
		assertEquals(1, tweetDAO.isNumber("1"));
		assertEquals(1, tweetDAO.isNumber("1 "));
		assertEquals(1, tweetDAO.isNumber(" 1 "));
		assertEquals(0, tweetDAO.isNumber("      "));
		assertEquals(0, tweetDAO.isNumber("1  3"));
	}

}
