package entity;

import dao.ExcelDAOImpl;

/**
 * Tweet entity is designed based on columns on dataset excel file, and aim to
 * encapsulate data in <b>Model</b> layer and <b>Controller</b> layer.
 * 
 * <p>
 * Inside this entity, following attributes are used to implement sorting
 * operation: {@code favs}, {@code rts}, {@code followers}. Tweets would be
 * ranked based on the sum of:
 * <ol>
 * <li>number of users who like the tweet (Favs)</li>
 * <li>number of users who re-tweet the tweet (RTs)</li>
 * </ol>
 * 
 * <p>
 * In order to visualize this object, toString() method is overrided and all of
 * its attributes could be printed out.
 * 
 * <p>
 * <b>Note: </b> For the {@code compareTo(Object o)} method, it doesn't follow
 * the conventions that for object which is literally bigger than itself, it
 * will return -1. On the contrast, it will return 1 so that when ordered data
 * structures such as TreeSet would have the biggest element on the top.
 *
 * @author Xunjie Liu
 * @version 1.0
 * @see ExcelDAOImpl
 * @see TweetDAOImpl
 * @since 2019-03-20
 */

public class Tweet implements Comparable<Tweet>{
	
	/** The id. */
	private String id;
	
	/** The date. */
	private String date;
	
	/** The hour. */
	private String hour;
	
	/** The username. */
	private String username;
	
	/** The nickname. */
	private String nickname;
	
	/** The content of this tweet. */
	private String content;
	
	/** Number of users who like the tweet. */
	private int favs;
	
	/** Number of users who re-tweet the tweet (republish). */
	private int rts; 
	
	/** Latitude of the location where a tweet is published. */
	private String latitude;
	
	/** Longitude of the location where a tweet is published. */
	private String longitude; 
	
	/**
	 * Number of followers of a user (the values are the same for the same user).
	 */
	private int followers;
	
	/**
	 * Instantiates a new tweet.
	 *
	 * @param id
	 *            the id
	 * @param date
	 *            the date
	 * @param hour
	 *            the hour
	 * @param username
	 *            the username
	 * @param nickname
	 *            the nickname
	 * @param content
	 *            The content of this tweet
	 * @param favs
	 *            Number of users who like the tweet
	 * @param rts
	 *            Number of users who re-tweet the tweet (republish) 
	 * @param latitude
	 *            Latitude of the location where a tweet is published
	 * @param longitude
	 *            Longitude of the location where a tweet is published
	 * @param followers
	 *            Number of followers of a user (the values are the same for the same user)
	 */	
	public Tweet(String id, String date, String hour, String username, String nickname, String content, int favs,
			int rts, String latitude, String longitude, int followers) {
		this.id = id;
		this.date = date;
		this.hour = hour;
		this.username = username;
		this.nickname = nickname;
		this.content = content;
		this.favs = favs;
		this.rts = rts;
		this.latitude = latitude;
		this.longitude = longitude;
		this.followers = followers;
	}
	
	/**
	 * Instantiates a new tweet.
	 */
	public Tweet() {
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Tweet [id=" + id + ", date=" + date + ", hour=" + hour + ", username=" + username + ", nickname="
				+ nickname + ", content=" + content + ", favs=" + favs + ", rts=" + rts + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", followers=" + followers + "]";
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date
	 *            the new date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Gets the hour.
	 *
	 * @return the hour
	 */
	public String getHour() {
		return hour;
	}

	/**
	 * Sets the hour.
	 *
	 * @param hour
	 *            the new hour
	 */
	public void setHour(String hour) {
		this.hour = hour;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username
	 *            the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the nickname.
	 *
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Sets the nickname.
	 *
	 * @param nickname
	 *            the new nickname
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content
	 *            the new content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Gets the favs.
	 *
	 * @return the favs
	 */
	public int getFavs() {
		return favs;
	}

	/**
	 * Sets the favs.
	 *
	 * @param favs
	 *            the new favs
	 */
	public void setFavs(int favs) {
		this.favs = favs;
	}

	/**
	 * Gets the rts.
	 *
	 * @return the rts
	 */
	public int getRts() {
		return rts;
	}

	/**
	 * Sets the rts.
	 *
	 * @param rts
	 *            the new rts
	 */
	public void setRts(int rts) {
		this.rts = rts;
	}

	/**
	 * Gets the latitude.
	 *
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * Sets the latitude.
	 *
	 * @param latitude
	 *            the new latitude
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets the longitude.
	 *
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * Sets the longitude.
	 *
	 * @param longitude
	 *            the new longitude
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * Gets the followers.
	 *
	 * @return the followers
	 */
	public int getFollowers() {
		return followers;
	}

	/**
	 * Sets the followers.
	 *
	 * @param followers
	 *            the new followers
	 */
	public void setFollowers(int followers) {
		this.followers = followers;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	/* 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Tweet o) {
		int favs_1 = o.getFavs();
		int favs_2 = this.getFavs();
		int rts_1 = o.getRts();
		int rts_2 = this.getRts();
		
		int sum1 = favs_1 + rts_1;
		int sum2 = favs_2 + rts_2;
		if (sum2 > sum1) {
			return -1;
		} else if (sum2 < sum1) {
			return 1;
		} else {
			return 0;
		}
	}



}
