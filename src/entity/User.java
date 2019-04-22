package entity;

import java.util.ArrayList;
import java.util.List;

import dao.ExcelDAOImpl;

/**
 * User entity is designed based on Tweet object, and aim to encapsulate data in
 * <b>Model</b> layer and <b>Controller</b> layer.
 * 
 * <p>
 * Inside this entity, following attributes are used to implement sorting
 * operation: {@code username}, {@code followers}. Users would be ranked based
 * on the {@code followers} and unique identified by {@code username}.
 * 
 * <p>
 * In order to visualize this object, toString() method is overrided and
 * important attributes could be printed out.
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
 * @see UserDAOImpl
 * @since 2019-03-20
 */
public class User implements Comparable<User> {
	
	/** The id. */
	private String ID;
	
	/** The username. */
	private String username;
	
	/** The nickname. */
	private String nickname;
	
	/** The tweets. */
	private List<Tweet> tweets = new ArrayList<Tweet>();
	
	/** The followers. */
	private int followers;

	/**
	 * Instantiates a new user.
	 *
	 * @param ID
	 *            the id
	 * @param username
	 *            the username
	 * @param followers
	 *            the followers
	 */
	public User(String ID, String username, int followers) {
		this.username = username;
		this.followers = followers;
		this.ID = ID;
	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [username=" + username + ", nickname=" + nickname +", ID=" + ID + ", followers="
				+ followers + "]";
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
	 * Gets the tweets.
	 *
	 * @return the tweets
	 */
	public List<Tweet> getTweets() {
		return tweets;
	}

	/**
	 * Sets the tweets.
	 *
	 * @param tweets
	 *            the new tweets
	 */
	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
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

	/*
	 * Firstly it would compare the followers, if equal, then username would be
	 * compared.
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(User o) {
		int followers_1 = this.followers;
		int followers_2 = o.getFollowers();

		if (followers_2 > followers_1) {
			return 1;
		} else if (followers_2 < followers_1) {
			return -1;
		} else {
			return this.username.compareTo(o.getUsername());
		}
	}

}
