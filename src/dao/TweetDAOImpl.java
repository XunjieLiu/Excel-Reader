package dao;

import java.util.PriorityQueue;

import entity.Tweet;

/**
 * An implementation of the {@code IEntityDAO} interface. This class is used to
 * handle data and get Tweet objects. In addition to implementing the
 * {@code IEntityDAO} interface, this class provides a method to valid input
 * data.
 * 
 * <h1> This class is only responsible for generating Tweet objects according to
 * the input data, and then store these Tweet objects into a queue. Since that
 * Tweet objects are needed to be sorted, so {@link PriorityQueue} is used to do
 * sort automatically. The {@code compareTo()}} method of Tweet entity is
 * overrided so that the first element in PriorityQueue would be the biggest
 * one, rather than the smallest by default. When the traverse of excel file is
 * done, TweetDAO will return its queue.
 *
 * @author Xunjie Liu
 * @version 1.0
 * @see IEntityDAO
 * @see ExcelDAOImpl
 * @since 2019-03-20
 */

public class TweetDAOImpl implements IEntityDAO {
	
	/** PriorityQueue for sorting and storing all Tweet objects. */
	private PriorityQueue<Tweet> queue;

	/**
	 * Constructs an instance of TweetDAOImpl object and initial queue to store
	 * data.
	 */
	public TweetDAOImpl() {
		queue = new PriorityQueue<Tweet>();
	}

	/**
	 * This method will get a Tweet object from input String array and store it.
	 * 
	 * @param row
	 *            String array contains all data in one excel row
	 */
	@Override
	public void handle_row(String[] row) {
		Tweet tweet = getTweet(row);
		queue.add(tweet);
	}

	/**
	 * This method will extract data from one String array and return a Tweet
	 * object.
	 *
	 * @param row
	 *            String array contains all data in one excel row
	 * @return A Tweet object
	 */
	public Tweet getTweet(String[] row) {
		try {
			int favs = isNumber(row[6]);
			int rts = isNumber(row[7]);
			int followers = isNumber(row[10]);
			
			Tweet t = new Tweet(row[0], row[1], row[2], row[3], row[4], row[5], favs, rts, row[8], row[9], followers);
			
			return t;
		}catch(ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * This method will validate a input string and turn it into Integer. If this
	 * input string is not valid, it would directly return 0.
	 * 
	 * @param str
	 *            String to be validated
	 * @return A Integer, if not valid, return 0, else return its literal value
	 */
	public int isNumber(String str) {
		String reg = "^[0-9]+(.[0-9]+)?$";
		str = str.trim();
		boolean isNum = str.matches(reg);

		if (!isNum) {
			return 0;
		} else {
			return Integer.parseInt(str);
		}
	}

	/**
	 * This method will return a PriorityQueue which stores all Tweet objects.
	 * 
	 * @return Queue containing all Tweet objects.
	 */
	public PriorityQueue<Tweet> getQueue() {
		return queue;
	}

	/**
	 * This method will return a data structure which stores objects(Tweet or User).
	 * 
	 * @return Object, could be a queue or set
	 */
	@Override
	public Object getData() {
		return queue;
	}

}

