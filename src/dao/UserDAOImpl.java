package dao;

import java.util.Set;
import java.util.TreeSet;

import entity.User;

/**
 * An implementation of the {@code IEntityDAO} interface. This class is used to
 * handle data and get User objects. In addition to implementing the
 * {@code IEntityDAO} interface, this class provides a method to valid input
 * data.
 * 
 * <p>
 * This class is only responsible for generating User objects according to the
 * input data, and then store these User objects into a set. User objects are
 * required to remove duplication according to the username, so the {@link Set}
 * data structure should be utilized, besides, orderliness is alse required, so
 * {@link TreeSet} is selected as its data structure. When the traverse of excel
 * file is done, TweetDAO will return its set.
 * 
 * @author Xunjie Liu
 * @version 1.0
 * @since 2019-03-20
 * @see IEntityDAO
 * @see ExcelDAOImpl
 * 
 */

public class UserDAOImpl implements IEntityDAO{
	/**
	 * TreeSet for sorting and storing all User objects
	 */
	private Set<User> set;
	
	/**
	 * Constructs an instance of UserDAOImpl object and initial set to store data
	 *
	 */
	public UserDAOImpl() {
		set = new TreeSet<User>();
	}
	
	/**
	 * This method will get a User object from input String array and store it.
	 * 
	 * @param row
	 *            String array contains all data in one excel row
	 */
	@Override
	public void handle_row(String[] row) {
		User user = getUser(row);
		set.add(user);		
	}
	
	/**
	 * This method will extract data from one String array and return a User object
	 * 
	 * @param row
	 *            String array contains all data in one excel row
	 * @return A User object
	 */
	public User getUser(String[] row) {
		String username = row[3];
		int followers = isNumber(row[10]);
		User user = new User(row[0], username, followers);
		
		return user;
	}
	
	/**
	 * This method will validate a input string and turn it into Integer. If this
	 * input string is not valid, it would directly return 0.
	 * 
	 * @param str
	 *            String to be validated
	 * @return A Integer, if not valid, return 0, else return its literal value
	 */
	public int isNumber(String str){
		String reg = "^[0-9]+(.[0-9]+)?$";
		boolean isNum = str.matches(reg);
		
		if(!isNum) {
			return 0;
		}else {
			return Integer.parseInt(str);
		}
	}
	
	/**
	 * This method will return a TreeSet which stores all User objects.
	 * 
	 * @return Set containing all User objects.
	 */
	public Set<User> getSet() {
		return set;
	}
	
	/**
	 * This method will return a data structure which stores objects(Tweet or User).
	 * 
	 * @return Object, could be a queue or set
	 */
	@Override
	public Object getData() {
		return set;
	}

}

