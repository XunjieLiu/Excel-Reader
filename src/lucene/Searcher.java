package lucene;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


/**
 * This class defines the method to do index search using Lucene API and print
 * out results.
 *
 * @author Xunjie Liu
 * @version 1.0
 * @see <a href=
 *      "https://lucene.apache.org/core/7_7_1/demo/overview-summary.html">Overview
 *      (Lucene 7.7.1 API)</a>
 * @see <a href=
 *      "https://blog.csdn.net/ccdust/article/details/53447890">Tutorial for
 *      Lucene 6.2.1 (1): Create index files and basic search operations</a>
 * @since 2019-03-20
 */
public class Searcher {

	/**
	 * This method will take the path of index files and query statement(What to
	 * search), print out the result.
	 *
	 * @param indexDir
	 *            the index directory where the index files are stored
	 * @param query
	 *            the query statement
	 * @return the hash map
	 * @throws IOException
	 *             Fail to read index files
	 * @throws ParseException
	 *             Fail to parse the query
	 */
	public HashMap<String, String> search(String indexDir, String query) throws IOException, ParseException {
		// Get index path using input parameter
		Directory dir = FSDirectory.open(Paths.get(indexDir));

		// Then get index files
		IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(dir));

		// Initialize the parser, and define to which field to search, in this example,
		// "contents" will be searched
		QueryParser parser = new QueryParser("contents", new StandardAnalyzer());

		// Initialize query
		Query q = parser.parse(query.toLowerCase());

		long startTime = System.currentTimeMillis() / 1000;
		// Search, return the result, it would only takes several seconds.
		TopDocs hits = searcher.search(q, 100);
		long endTime = System.currentTimeMillis() / 1000;

		// print out how mant matches
		System.out.println("\n" + hits.totalHits);
		System.out.println("Search time: " + (endTime - startTime) + "s\nDetails(Only part of result is shown): \n-------------------------------------------------");
		
		
		HashMap<String, String> result = getResult(hits, searcher);
		
		return result;
	}
	
	
	/**
	 * This method will get a HashMap object which contains the result of Lucene search, with the form of (key, value)
	 *
	 * @param hits
	 *            TopDOcs object which contains the result of search
	 * @param searcher
	 *            IndexSearcher object which retrives Document object from hits
	 * @return result	
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public HashMap<String, String> getResult(TopDocs hits, IndexSearcher searcher) throws IOException{
		Document doc;
		String username, contents;
		HashMap<String, String> result = new HashMap<String, String>();

		for (ScoreDoc scoreDoc : hits.scoreDocs) {
			doc = searcher.doc(scoreDoc.doc);
			username = doc.get("username");
			contents = doc.get("contents");
			
			if(username != null && contents != null) {
				result.put(username, contents);
			}
			
		}
		
		return result;
	}
}
