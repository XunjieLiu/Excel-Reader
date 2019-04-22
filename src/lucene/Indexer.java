package lucene;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.PriorityQueue;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import entity.Tweet;

/**
 * This class implements index file creating work and Lucene 7.7.1 API is
 * used to do this. This function is a precondition for high efficiency search
 * using Lucene API.
 * 
 * <p>
 * There are 4 fields when creating index files, which are {@code contents},
 * {@code ID}, {@code username} and {@code Tweet}, the search will beased on
 * contents, and search result will show the username.
 * 
 * <p>
 * This entity would be instantiated by {@link service.Menu Menu} after extrated
 * and encapsulating all data from excel file.
 * 
 *
 * @author Xunjie Liu
 * @version 1.0
 * @since 2019-03-20
 * @see <a href=
 *      "https://lucene.apache.org/core/7_7_1/demo/overview-summary.html">Overview
 *      (Lucene 7.7.1 API)</a>
 * @see <a href=
 *      "https://blog.csdn.net/ccdust/article/details/53447890">Tutorial for
 *      Lucene 6.2.1 (1): Create index files and basic search operations</a>
 */
public class Indexer {
	
	/** IndexWriter is used to write index files into disk */
	private IndexWriter writer;
	
	/** The tweets stores all Tweet objects extracted from excel file */
	private PriorityQueue<Tweet> tweets;

	/**
	 * Instantiates a new indexer using path url and all Tweet objects
	 *
	 * @param indexDir
	 *            the index directory where the index files would be stored.
	 * @param tweets
	 *            All Tweet objects to be indexed
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public Indexer(String indexDir, PriorityQueue<Tweet> tweets) throws IOException {
		// Get index path using input parameter
		Directory dir = FSDirectory.open(Paths.get(indexDir));
		
		// Initialize config
		IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
		config.setOpenMode(OpenMode.CREATE_OR_APPEND);
		
		// Create writer
		writer = new IndexWriter(dir, config);
		this.tweets = tweets;
	}

	/**
	 * Close writer
	 *
	 * @throws IOException
	 *             Fail to close.
	 */
	public void close() throws IOException {
		writer.close();
	}

	/**
	 * This method will generate Document object, and Document object is the
	 * encapsulation for all attributes of this document.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void writeDocument() throws IOException {
		Document doc;

		for (Tweet t : tweets) {
			doc = new Document();
			doc.add(new Field("contents", t.toString().toLowerCase(), TextField.TYPE_STORED));
			doc.add(new Field("username", t.getUsername(), TextField.TYPE_STORED));
			doc.add(new Field("tweet", t.getContent(), TextField.TYPE_STORED));
			writer.addDocument(doc);
		}

	}
}
