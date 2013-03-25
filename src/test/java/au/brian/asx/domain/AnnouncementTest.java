package au.brian.asx.domain;


import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import au.brian.asx.domain.Announcement;
import au.brian.asx.domain.Share;

public class AnnouncementTest {

	private static Logger log = LoggerFactory.getLogger("AnnouncementTest");
	
	@DataProvider(name="testValues")
	public Object[][] createTestValues() {
		return new Object[][] 
        {
			{ "/Users/briancole/Downloads/41zk1jggm90mmp.pdf",Announcement.Type.BUY_SELL,50,0 },
			{ "/Users/briancole/Downloads/41zk1mmcbzcrgj.pdf",Announcement.Type.BUY_SELL,4317,0 },
			{ "/Users/briancole/Downloads/41zsqscksqwlz9.pdf",Announcement.Type.BUY_SELL,1000,0 },
			{ "/Users/briancole/Downloads/420jyp71tjwd15.pdf",Announcement.Type.BUY_SELL,20000,0 },
			{ "/Users/briancole/Downloads/420kvbksx0t768.pdf",Announcement.Type.BUY_SELL,10000,0 },
			{ "/Users/briancole/Downloads/420nqx23dvvsd3.pdf",Announcement.Type.BUY_SELL,0,7688 },
			{ "/Users/briancole/Downloads/420nzwthq7kkdb.pdf",Announcement.Type.OTHER,0,0 },
			{ "/Users/briancole/Downloads/4214h9d3gvsl1k.pdf",Announcement.Type.OTHER,0,0 },
			{ "/Users/briancole/Downloads/4218058x0zvvtp.pdf",Announcement.Type.OTHER,0,0 }

	    };
	}
	
	  
@Test(dataProvider="testValues")
public void test1(String file, Announcement.Type type, int boughtNumber, int soldNumber) throws IOException {
	
//	  log.info("hello");

	// fake share to make the test go
	  Share share = new Share("QBE");
	  share.setPrice(100);
	  
	  File f = new File(file);

//	  log.info("pattern=" + Announcement.changeOfDirectorInterestNoticePattern);
//	  List<Integer> li = Announcement.allIndexOf("Change of Director's Interest Notice",Announcement.changeOfDirectorInterestNoticePattern);
//	  for (int i : li) {
//		  System.out.println(i);
//	  }
//	  log.info("indexof=" + "Change of Director�s Interest Notice".indexOf("ange of Director"));
	  Announcement a = null;
	  try {
		  a = Announcement.parseAnnouncement(share, f);
		  //log.info("Announcement=" + a);
		  assert(a.getType() == type);
		  if (type == Announcement.Type.BUY_SELL) {
			  assert(a.getBoughtNumber() == boughtNumber);
			  assert(a.getSoldNumber() == soldNumber);
		  }
		  else {
			  assert(a.nothingBoughtOrSold());
		  }
		  //assert(a.getValue() == 50*100);
	  }
	  catch (AssertionError ae) {
		  log.error("Announcement = " + a);
		  throw ae;
		 // AssertJUnit.fail("Got exception : " + e);
	  }
//	  catch (Exception e) {
//		  log.error(e.toString(),e);
//		  fail("Got exception : " + e);
//	  }
//	  log.info("bye");

	}

}
