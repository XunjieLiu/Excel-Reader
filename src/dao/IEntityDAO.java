package dao;

/**
 * Entity Data Access Object Interface defines the methods for creating Objects
 * according to the input data of one excel row.	
 * 
 * @author Xunjie Liu
 * @version 1.0
 * @since 2019-03-20
 * @see TweetDAOImpl
 * @see UserDAOImpl
 * 
 */

public interface IEntityDAO {
	
	/**
	 * This method will extract data from one String array and create an
	 * Object(Tweet or User) to be stored.
	 * 
	 * @param row
	 *            String array contains all data in one excel row
	 */
	public void handle_row(String[] row);
	
	/**
	 * This method will return a data structure which stores objects(Tweet or User).	
	 * 
	 * @return Object, could be a queue or set
	 */
	public Object getData();
}
