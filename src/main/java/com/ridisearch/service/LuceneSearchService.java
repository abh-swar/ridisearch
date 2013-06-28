package com.ridisearch.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ridisearch.domain.SearchHits;
import com.ridisearch.utils.Constants;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/27/13
 * Time: 6:24 PM
 */
public class LuceneSearchService {

    /*
     * A method used to perform search operation on the indexed files that analyzes the
     * Lucene query string and makes modification on the list of SearchHits passed to it
     */
    public void searchIndex(String queryString, List<SearchHits> listOfHits) throws ParseException, IOException {
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_41);
//        Query query1 = new QueryParser(Version.LUCENE_41, "uploadedBy", analyzer).parse(queryString);
//        Query query2 = new QueryParser(Version.LUCENE_41, "fileName", analyzer).parse(queryString);

        BooleanQuery booleanQuery = new BooleanQuery();

//        Query query1 = new TermQuery(new Term("contents", queryString));
        QueryParser queryParser1 = new QueryParser(Version.LUCENE_41, "contents", analyzer);
        QueryParser queryParser2 = new QueryParser(Version.LUCENE_41, "uploadedBy", analyzer);
        QueryParser queryParser3 = new QueryParser(Version.LUCENE_41, "fileName", analyzer);

        queryParser1.setAllowLeadingWildcard(true);
        queryParser2.setAllowLeadingWildcard(true);
        queryParser3.setAllowLeadingWildcard(true);


        Query query1 = queryParser1.parse(queryString);
        Query query2 = queryParser2.parse(queryString);
        Query query3 = queryParser3.parse(queryString);


//        Query query2 = new TermQuery(new Term("uploadedBy", queryString));
//        Query query3 = new TermQuery(new Term("fileName", queryString));

        booleanQuery.add(query1, BooleanClause.Occur.SHOULD);
        booleanQuery.add(query2, BooleanClause.Occur.SHOULD);
        booleanQuery.add(query3, BooleanClause.Occur.SHOULD);

        //serach
        int hitsPerPage = 20;
        IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(Constants.INDEX_PATH)));
        IndexSearcher searcher = new IndexSearcher(reader);

        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
        searcher.search(booleanQuery,collector);
//        searcher.search(query, collector);

        ScoreDoc[] hits = collector.topDocs().scoreDocs;

//        print result to console
	    System.out.println("Found : " + hits.length + " hits.");
	    for (int i = 0; i < hits.length; i++) {
	    	int docId = hits[i].doc;
	    	Document d = searcher.doc(docId);
	    	System.out.println((i+1) + ". " +
	    	"fileName : " + d.get("fileName") + "\t" +
			"uploadedby : " +d.get("uploadedBy") + "\t" +
			"itemId : " + d.get("itemId") + "\t" +
	    	"Hits : " + hits[i].score);
	    }


        for (int i = 0; i < hits.length; i++) {

            int docId = hits[i].doc;
            Document d = searcher.doc(docId);

            listOfHits.add(new SearchHits().
                    setFileName(d.get("fileName")).
                    setUploadedBy(d.get("uploadedBy")).
                    setItemId(d.get("itemId"))
            );
        }

        reader.close();
    }
}
