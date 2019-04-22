package service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.TreeSet;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import dao.ExcelDAOImpl;
import entity.Tweet;
import entity.User;
import lucene.Indexer;
import lucene.Searcher;

/**
 * <h1>Menu</h1> The Menu program provides highly abtracted methods for Main
 * program to invoke so that required functions can be called on console,
 * clearly and eficiently.
 * <p>
 * Similiar with MVC design model, this program is more like a Controller with
 * methods which are detached away from data access on the Model layer. In this
 * way, this whole project can be efficient and robust, modification of Model
 * layer on the botton would not influence the Controller.
 *
 * @author Xunjie Liu
 * @version jdk-10.0.2
 * @since 2019-03-20
 */

public class Menu {
	
	/** The top 10 tweets. */
	private List<Tweet> top10Tweets;
	
	/** The top 10 users. */
	private Map<String, Integer> top10Users;
	
	/** The excel DAO. */
	private ExcelDAOImpl excelDAO;
	
	/** The Constant INDEX_DIR. */
	private static final String INDEX_DIR = "D:\\Study\\Year 3\\CSE210\\CW\\luceneIndex";
	
	/** The indexer. */
	private Indexer indexer;

	/**
	 * This method is a constructor method which need no input parameter but only
	 * initialize some global variables, so that data could be import from bottom.
	 *
	 */
	public Menu() {
		
	}
	
	/**
	 * Initialize the Menu, including read data from excel files, get top Tweets and Users, and create index files.
	 *
	 * @throws InvalidFormatException
	 *             the invalid format exception
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void init() throws InvalidFormatException, FileNotFoundException, IOException {
		System.out.println("Start to import data from excel file.....");
		long startTime = System.currentTimeMillis() / 1000;
		this.excelDAO = new ExcelDAOImpl();
		long endTime = System.currentTimeMillis() / 1000;
		System.out.println("Running time: " + (endTime - startTime) + "s");
		
		System.out.println("All data imported!");
		this.top10Tweets = new ArrayList<Tweet>();
		this.top10Users = new HashMap<String, Integer>();
		indexer = new Indexer(INDEX_DIR, excelDAO.getAllTweets());
		getTop();
		
		System.out.println("Start to create index files...");
		initIndex();
	}

	/**
	 * Create the index files.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void initIndex() throws IOException {
		indexer.writeDocument();

		indexer.close();
		
		System.out.println("Index files created !");
	}

	/**
	 * Search using Luceue API.
	 *
	 * @param key
	 *            The search key words
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ParseException
	 *             the parse exception
	 */
	public void indexSearch(String key) throws IOException, ParseException {
		Searcher searcher = new Searcher();
		HashMap<String, String> result = searcher.search(INDEX_DIR, key);
		
		Iterator<Entry<String, String>> iterator = result.entrySet().iterator(); 
		
		while(iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			System.out.println("Username: " + entry.getKey());
			System.out.println("Contents: " + entry.getValue());
			System.out.println("<---------------------------------------------->");
		}
		
	}

	/**
	 * Gets the top Users and Tweets from excelDAO.
	 *
	 * @return the top
	 */
	public void getTop() {
		PriorityQueue<Tweet> allTweets = excelDAO.getAllTweets();
		TreeSet<User> allUsers = excelDAO.getAllUsers();
		User temp;

		for (int i = 0; i < 10; i++) {
			top10Tweets.add(allTweets.poll());
			
			// There are repeated users, so here using Map to delete duplicates
			do {
				temp = allUsers.pollFirst(); 
			} while (top10Users.containsKey(temp.getUsername()));

			top10Users.put(temp.getUsername(), temp.getFollowers());
		}

		for (Tweet t : top10Tweets) {
			allTweets.add(t);
		}
	}

	/**
	 * Prints the top users in descending order using inner comparator class. 
	 */
	public void printTopUsers() {
		List<Map.Entry<String, Integer>> sortedUsers = new ArrayList<>();
		sortedUsers.addAll(top10Users.entrySet());
		Menu.ValueComparator comparator = new ValueComparator();
		Collections.sort(sortedUsers, comparator);

		for (Map.Entry<String, Integer> entry : sortedUsers) {
			System.out.println("Username: " + entry.getKey() + "\nFollowers: " + entry.getValue()
					+ "\n<------------------------------>");
		}
	}

	/**
	 * Prints the top tweets.
	 */
	public void printTopTweets() {
		for (Tweet t : top10Tweets) {
			System.out.println(t.toString());
			System.out.println("Favs: " + t.getFavs() + " Rts: " + t.getRts());
		}
	}

	/**
	 * Text match using regular expression.
	 *
	 * @param target
	 *            the target
	 */
	public void textMatch(String target) {
		List<String> searchResult = new LinkedList<String>();
		PriorityQueue<Tweet> tweets = excelDAO.getAllTweets();
		
		for (Tweet t : tweets) {
			if (match(t, target)) {
				searchResult.add(t.toString());
			}
		}
		
		if(searchResult.isEmpty()) {
			System.out.println("Cannot find what you are looking for");
		}else {
			for(String s: searchResult) {
				System.out.println(s);
			}
		}
	}

	/**
	 * To check if query string is in this Tweet.
	 *
	 * @param tweet
	 *            the tweet
	 * @param target
	 *            the target
	 * @return true, if successful
	 */
	public static boolean match(Tweet tweet, String target) {
		boolean match = false;
		String content = tweet.getContent().toLowerCase().trim();
		String regex = "(.*)" + target.toLowerCase().trim() + "(.*)";

		match = content.matches(regex);
		return match;
	}
	
	/**
	 * The Class ValueComparator, aiming to order a Map by its values rather than keys.
	 */
	private static class ValueComparator implements Comparator<Map.Entry<String,Integer>>
	{
		
		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(Map.Entry<String,Integer> m,Map.Entry<String,Integer> n)
		{
			return n.getValue()-m.getValue();
		}
	}

}
