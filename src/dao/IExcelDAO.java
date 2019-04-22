package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.TreeSet;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import entity.Tweet;
import entity.User;

/**
 * Excel Data Access Object Interface defines the methods for Excel DAO objects
 * to extract data from Excel files and return Java objects. Excel DAO uses
 * different handler to encapsulate string data from excel into Tweet and User
 * object. Since that there is no requirements to modify data in excel, there is
 * only read function to get access to excel files.
 * <p>
 * In order to efficiently store data, following data structures are used in
 * this project:
 * <ul>
 * <li>PriorityQueue: for its feature that it would automatically sort data as
 * soon as data is added.
 * <li>TreeSet: In addition to its orderliness, it also do not allowed
 * duplicated objects.
 * </ul>
 * 
 * @author Xunjie Liu
 * @version 1.0
 * @since 2019-03-20
 * @see ExcelDAOImpl
 * 
 */

public interface IExcelDAO {

	/**
	 * This method will call methods from POI jar package and utilise them to
	 * extract data from excel. read() took two handler objects as input to help it
	 * to transform Strings to Tweets and Users. <br>
	 * 
	 * @param tweethandler
	 *            Read string object and return Tweet object
	 * @param userhandler
	 *            Read string object and return user object
	 * @throws InvalidFormatException If file format is not valid for OPCPackage
	 * @throws IOException  If an I/O error occurs, which is possible because the construction of the canonical pathname may require filesystem queries
	 * @throws FileNotFoundException If specified path name is not valid
	 */
	public void read(IEntityDAO tweethandler, IEntityDAO userhandler) throws InvalidFormatException, IOException, FileNotFoundException;

	/**
	 * This method is a getter method of IExcelDAO, return a queue of tweets<br>
	 * 
	 * @return return a queue with all tweets inside
	 */
	public PriorityQueue<Tweet> getAllTweets();

	/**
	 * This method is a getter method of IExcelDAO, return a queue of users<br>
	 * 
	 * @return return a queue with all users inside
	 */
	public TreeSet<User> getAllUsers();
}
