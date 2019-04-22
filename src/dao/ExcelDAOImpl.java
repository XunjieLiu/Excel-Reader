package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.TreeSet;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import entity.Tweet;
import entity.User;

/**
 * An implementation of the {@code IExcelDAO} interface. In addition to
 * implementing the {@code IExcelDAO} interface, this class provides methods to
 * extract data from XSSFRow objects to String arrays.
 * 
 * <p>
 * Although the function of this class is to read data from Excel files rather
 * than real database like MySQL or Oracle, it still could be available for Data
 * Access Object design model, but situations would be a little different.
 * Normally TweetDAO and UserDAO should be totally seperated and they should
 * have their database opeartion methods, but since that this is a excel file
 * containing 210000 records, intensive computation resource and memory resource
 * are needed to do a single traverse. Therefore, traverse time should be only
 * one and for each record read into the memory, TweetDAO and UserDAO should
 * both be called so that one traverse would enough for everything, because data
 * access would be more efficient and fast within java data structure rather
 * than excel files.
 * 
 * <p>
 * The {@code getAllTweets}, {@code getAllUsers} return its global variables to
 * {@link service.Menu Menu}
 * 
 * <p>
 * In order to sort object easily, {@code PriorityQueue} is used to sort
 * compareble objects. Tweet and User object in {@link entity.Tweet Tweet} and
 * {@link entity.User User} have implemented Comparable interface according to
 * their own attributes.
 * 
 * <p>
 * Besides, this class use external jar packages from Apache POI to parse Excel
 * files, and its model is User Model rather than Event Model. See <a href=
 * "http://poi.apache.org/components/spreadsheet/quick-guide.html#CellContents">Busy
 * Developers' Guide to HSSF and XSSF Features</a>.
 * 
 * <p>
 * Efficiency and robustness are implemented through reduce dependency between
 * different classes and packages, each class should only implement one specific
 * function. This class only responsible for read raw data from excel files, and
 * then ExcelDAO object will focus on the next move. External jar package will
 * be only needed for this class.
 *
 * @author Xunjie Liu
 * @version 1.0
 * @see IExcelDAO
 * @see <a href=
 *      "http://poi.apache.org/components/spreadsheet/quick-guide.html#CellContents">Busy
 *      Developers' Guide to HSSF and XSSF Features</a>.
 * @since 2019-03-20
 */

public class ExcelDAOImpl implements IExcelDAO {
	
	/** PriorityQueue for sorting and storing all Tweet objects. */
	private PriorityQueue<Tweet> queue;

	/** TreeSet for sorting and deleting duplication of all user objects. */
	private TreeSet<User> set;

	/**
	 * Row Data Access Object to handle the cell instance extracted by {@code read}
	 * method and output Tweet objects.
	 */
	private IEntityDAO tweetDAO;

	/**
	 * Row Data Access Object to handle the cell instance extracted by {@code read}
	 * method and output User objects.
	 */
	private IEntityDAO userDAO;

	/**
	 * Constructs an instance of ExcelDAOImpl object and initial cell daos to read
	 * data, due to the requirements of this coursework, all main function needed to
	 * do is create a new instance of ExcelDAOImpl, and then data extracting work
	 * would be started automatically.
	 *
	 * @throws InvalidFormatException
	 *             {@inheritDoc}
	 * @throws FileNotFoundException
	 *             {@inheritDoc}
	 * @throws IOException
	 *             {@inheritDoc}
	 */
	public ExcelDAOImpl() throws InvalidFormatException, FileNotFoundException, IOException {
		this.tweetDAO = new TweetDAOImpl();
		this.userDAO = new UserDAOImpl();
		read(tweetDAO, userDAO);
	}

	/**
	 * Returns all Tweet objects, classes on <b>Controller</b> layer would only need to invoke this method and {@link getAllUsers},
	 * so that all data access operations are encapsulated in <b>Model</b> layer.
	 *
	 * @return PriorityQueue that contains all Tweet objects
	 */
	@Override
	public PriorityQueue<Tweet> getAllTweets() {
		return queue;
	}

	/**
	 * Returns all User objects,  classes on <b>Controller</b> layer would only need to invoke this method and {@link getAllTweets},
	 * so that all data access operations are encapsulated in <b>Model</b> layer.
	 *
	 * @return TreeSet that contains all User objects
	 */
	@Override
	public TreeSet<User> getAllUsers() {
		return set;
	}

	/**
	 * Exact data from excel files and give them to TweetDAO or UserDAO instance,
	 * core codes reading data referenc from official demo codes of Apache POI, see
	 * <a href=
	 * "http://poi.apache.org/components/spreadsheet/quick-guide.html#CellContents">Busy
	 * Developers' Guide to HSSF and XSSF Features</a>.
	 *
	 * @param tweetDAO
	 *            tweet cell dao instance
	 * @param userDAO
	 *            user cell dao instance
	 * @throws InvalidFormatException
	 *             {@inheritDoc}
	 * @throws IOException
	 *             {@inheritDoc}
	 * @throws FileNotFoundException
	 *             {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void read(IEntityDAO tweetDAO, IEntityDAO userDAO) throws InvalidFormatException, IOException, FileNotFoundException{
			// Use OPCPackage to unzip .xlsx file to get XML files
			OPCPackage pkg = OPCPackage.open(new File("D:\\Study\\Year 3\\CSE210\\CW\\dataset.xlsx"));

			// Workbook -> Sheet -> Row -> Cell -> Data
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(pkg);

			// Only one sheet
			XSSFSheet sheet = workbook.getSheetAt(0);

			// How many rows in total
			int lastrow = sheet.getLastRowNum();
			XSSFRow excel_row;

			// Loop rows
			for (int rowNum = 1; rowNum <= lastrow; rowNum++) {
				// Get one row
				excel_row = sheet.getRow(rowNum);

				if (excel_row == null) {
					System.out.println("This row is empty");
					continue; // if this is an empty row, jump to next row
				}

				String[] row = getRow(excel_row);

				// To handle row
				tweetDAO.handle_row(row);
				userDAO.handle_row(row);
			}

			queue = (PriorityQueue<Tweet>) tweetDAO.getData();
			set = (TreeSet<User>) userDAO.getData();

			pkg.close();

	}

	/**
	 * Exact data from XSSFRow instances and return a String array which contains all cells in this row.
	 *
	 * @param excel_row
	 *            XSSFRow instances that contains one row data in excel file
	 * @return a string array containing all data in one row
	 */
	public String[] getRow(XSSFRow excel_row) {
		String[] row = new String[11];
		for (int cellNum = 0; cellNum <= 10; cellNum++) {
			try {
				XSSFCell cell = excel_row.getCell(cellNum);

				boolean empty = (cell == null);

				if (empty) {
					row[cellNum] = "";
				} else {
					row[cellNum] = cell.getStringCellValue().trim();
				}
			} catch (Exception e) {
				System.out.println(cellNum);
				e.printStackTrace();
				continue;
			}
		}

		return row;
	}

}
