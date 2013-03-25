package au.brian.asx.domain;

import java.util.Date;
import java.util.List;

public class ShareEvaluation {

	public static enum Recommendation { NONE, BUY, SELL };
	
	Share s;
	private int buyAmount = 0;
	private int buyNum = 0;
	private Recommendation recommendation;
	List<Announcement> announcements = null;
	
	public ShareEvaluation(Share s) {
		this.s = s;
	}	
	public int setAnnouncments(Date start, Date end) {
		announcements = Announcement.getAnnoucements(s,start,end);
		return announcements.size();
	}
	public void setAnnouncments(List<Announcement> announcements) {
		this.announcements = announcements;
	}
	
	public Recommendation doEvaluation() {
		this.recommendation = Recommendation.NONE;
		buyAmount = 0;
		buyNum = 0;
		if (announcements == null) {
			return recommendation;
		}
		for (Announcement a: announcements) {
			switch (a.getType()) {
				case BUY_SELL: {
					buyAmount += a.getValue(); 
					if (a.getValue() > 0) buyNum++;
					else buyNum--;
				}
			}			
		}
		if (buyNum >=2 && buyAmount > 5000) {
			// FIXME - vary cutoffs and weight for num of directors, amount of buy, and yield
			this.recommendation = Recommendation.BUY;
		}
		return this.recommendation;
	}
	
	@Override
	public String toString() {
		return "ShareEvaluation[" + recommendation + "," + buyAmount + "]";
	}

}
