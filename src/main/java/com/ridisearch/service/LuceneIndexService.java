package com.ridisearch.service;

import com.ridisearch.domain.DocumentInfo;
import com.ridisearch.domain.Items;
import com.ridisearch.utils.Constants;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/27/13
 * Time: 6:23 PM
 */
public class LuceneIndexService {
    @Autowired
    SearchService searchService;

    @Autowired
    AdminService adminService;

    /*
     * A method that indexes all the files read from DOCUMENT_BASE
     */
    public void indexItems(DocumentInfo documentInfo){
        try {

            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_41);

            // To store an index on disk
            Directory index = FSDirectory.open(new File(Constants.INDEX_PATH));

            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_41, analyzer);
            IndexWriter writer = new IndexWriter(index,config);
            addDoc(writer, documentInfo.getContent(), documentInfo.getItemId(), documentInfo.getUploadedBy(), documentInfo.getFileName());
            writer.close();


        }catch(Exception e){
            System.out.print(" ...INDEXING FAILED "+e.getMessage());
        }
    }

    public String index(Items items, MultipartFile multipartFile, String content) {
        String message = "File successfully uploaded";
        Long itemId = adminService.getItemId(items);
        if (checkIfFileExists()) {
            //if content is public index it else don't index it
            if (!items.getIsPrivate()) {
                //index content, userid, filename, id,stored_location,item_type

                DocumentInfo documentInfo = new DocumentInfo();
                documentInfo.setItemId(itemId.toString());
                documentInfo.setUploadedBy(items.getUser().getName());
                documentInfo.setFileName(items.getItemName());

                try {
                    String extension = items.getItemName().split("\\.")[1];
                    //if text file index the content else index the extension only
                    if (Constants.textList.contains(extension)) {
                        System.out.println("extension for text file = " + extension);

                        documentInfo.setContent(content);
                        indexItems(documentInfo);
                    } else if (Constants.multimediaList.contains(extension)) {
                        System.out.println("extension for multimedia file = " + extension);
                        documentInfo.setContent(extension);

                        indexItems(documentInfo);
                    } else {
                        throw new Exception("Not a valid file format :: " + extension);
                    }
                    message = "File successfully uploaded";

                } catch (Exception ex) {
                    ex.printStackTrace();
                    //delete the current entry in DB
                    searchService.deleteItem(itemId);
                    message = "Not a valid file format";
                }

            }
        } else {
            //delete the current entry in DB
            searchService.deleteItem(itemId);
            message = "Could not index file. Had problem in creating directory.";
        }
        return message;
    }

    private boolean checkIfFileExists() {
        boolean fileExists = true;
        File file = new File(Constants.INDEX_PATH);
        if (!file.exists()) {
            if (file.mkdirs()) {
                System.out.println(Constants.INDEX_PATH + " directory is created!");
                fileExists = true;
            } else {
                System.out.println("Failed to create directory!");
                fileExists = false;
            }
        }
        return fileExists;
    }


//    /*
//     * A method used to perform search operation on the indexed files that analyzes the
//     * Lucene query string and prints the result to the console
//     */
//    public void searchIndex(String queryString) throws ParseException, IOException {
//
//        Query query = new QueryParser(Version.LUCENE_41, "contents", analyzer).parse(queryString);
//
//
//        //serach
//        int hitsPerPage = 10;
//        IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(INDEX_PATH)));
//        IndexSearcher searcher = new IndexSearcher(reader);
//
//        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
//        searcher.search(query, collector);
//
//        ScoreDoc[] hits = collector.topDocs().scoreDocs;
//
//        //print result to console
//        System.out.println("Found : " + hits.length + " hits.");
//        for (int i = 0; i < hits.length; i++) {
//            int docId = hits[i].doc;
//            Document d = searcher.doc(docId);
//            System.out.println((i+1) + ". " + d.get("path") + "\t" + hits[i].score);
//        }
//
//        reader.close();
//    }


//    /*
//     * A method that parses a file and returns its content as a String object
//     */
//    private String parseReport(final File file) throws IOException, TikaException {
//        Tika tika = new Tika();
//        String content = tika.parseToString(file);
//        return content;
//    }


    /*
     * A method that adds the content, filename and path to the document object
     */
    private void addDoc(IndexWriter w, String content, String itemId, String uploadedBy, String fileName) throws IOException {
        Document doc = new Document();
        System.out.println("contents = " + content);
        doc.add(new TextField("contents", content, Field.Store.YES));
        doc.add(new StringField("itemId", itemId, Field.Store.YES));
        doc.add(new StringField("uploadedBy", uploadedBy, Field.Store.YES));
        doc.add(new StringField("fileName", fileName, Field.Store.YES));

        w.addDocument(doc);
    }

    public void deleteLuceneIndex(String itemId) throws IOException {
        File file                = new File(Constants.INDEX_PATH);
        Analyzer analyzer        = new StandardAnalyzer(Version.LUCENE_41);
        Directory index          = FSDirectory.open(file);
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_41, analyzer);
        IndexWriter writer       = new IndexWriter(index,config);

        writer.deleteDocuments(new Term("itemId",itemId));
        writer.close();
    }
}
