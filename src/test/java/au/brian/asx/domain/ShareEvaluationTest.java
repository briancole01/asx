package au.brian.asx.domain;

import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.*;

import au.brian.asx.domain.Announcement.Type;
import au.brian.asx.domain.ShareEvaluation.Recommendation;

public class ShareEvaluationTest {

	private static Share makeShare(String name, float price) {
		Share s1 = new Share(name);
		s1.setPrice(price);
		return s1;
	}
	private static Announcement makeAnnouncement(Share share, int value) {
		Announcement a = new Announcement(share);
		a.setType(Type.BUY_SELL);
		a.setValue(value);
		return a;
	}
	private static List<Announcement> makeAnnouncementList(Announcement ... args) {
		List<Announcement> list = new ArrayList<Announcement>(args.length);
		for (Announcement a: args) {
			list.add(a);
		}
		return list;
	}
	
	@Test
	public void test1() {
		Share qbe = makeShare("QBE",1000);
		ShareEvaluation e = new ShareEvaluation(qbe);
		e.setAnnouncments(makeAnnouncementList(makeAnnouncement(qbe,1000),makeAnnouncement(qbe,100)));
		Recommendation r = e.doEvaluation();
		System.out.println("e=" + e);
	}
}
