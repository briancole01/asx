package au.brian.asx.download;

import java.util.ArrayList;
import java.util.List;

public class PDFGetter {

	String url1 = "http://www.asx.com.au/asx/statistics/announcements.do?by=asxCode&asxCode=";
	String url2 = "&timeframe=Y&year=2011";
		
	// 1. manually use share filter to get list of candidate shares
	// 2. get announcements as files. save in /sharecode/year
	// 3. run evaluator on files to get recommendations
	
	public List<String> getPDFURLs(String shareCode) {
		List<String> pdfs = new ArrayList<String>();
		return pdfs;
	}
			
}
