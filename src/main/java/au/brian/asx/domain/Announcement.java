package au.brian.asx.domain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class Announcement {
	
	public static enum Type {
		BUY_SELL, OTHER
	}

	private static Logger log = LoggerFactory.getLogger("Announcement");

	private Type type = Type.OTHER;
	private Share share;
	private int num = 0;
	private int value = 0;
	private String boughtText = "";
	private String soldText = "";
	private Integer boughtNumber = null;
	private Integer soldNumber = null;
	
	//public static String changeOfDirectorInterestNoticeRegex = "ppendix";
	//public static String changeOfDirectorInterestNoticeRegex = "Change of Director�s Interest Notice";
	public static String changeOfDirectorInterestNoticeRegex = "Change of Director";
	public static Pattern changeOfDirectorInterestNoticePattern = Pattern.compile(changeOfDirectorInterestNoticeRegex,Pattern.CASE_INSENSITIVE & Pattern.MULTILINE);
	//public static Pattern changeOfDirectorInterestNoticePattern = Pattern.compile(changeOfDirectorInterestNoticeRegex);
	public static String numberAcquiredRegex = "Number acquired";
	public static Pattern numberAcquiredPattern = Pattern.compile(numberAcquiredRegex,Pattern.CASE_INSENSITIVE & Pattern.MULTILINE);
	public static String numberDisposedRegex = "Number disposed";
	public static Pattern numberDisposedPattern = Pattern.compile(numberDisposedRegex,Pattern.CASE_INSENSITIVE & Pattern.MULTILINE);
	
	public Announcement(Share s) {
		this.share = s;
	}
	
	public static List<Announcement> getAnnoucements(Share s, Date start,
			Date end) {
		// TODO Auto-generated method stub
		return new ArrayList<Announcement>();
	}
	
	public static Announcement parseAnnouncement(Share share, String s) {
		//log.debug("got PDF text = " + s);
		Announcement a = new Announcement(share);
		s = s.replaceAll("[^\\x00-\\x7f]", " "); // get rid of weird stuff
		List<Integer> li = allIndexOf(s,changeOfDirectorInterestNoticePattern);
		if (li.size() <= 0) {
			log.info("no change of director interest notice text found");
			a.setType(Announcement.Type.OTHER);
			return a;		
		}
		a.setType(Announcement.Type.BUY_SELL);
		a.findNumberAcquired(s);
		a.findNumberDisposed(s);
		
		if (a.nothingBoughtOrSold()) {
			log.warn("found no bought or sold in suspected change of director's interest notice");
			a.setType(Announcement.Type.OTHER);
		}
		else {
			a.calcValue();
		}
		return a;
	}
	
	private void calcValue() {
		value = (int) (((boughtNumber == null ? 0: boughtNumber)-(soldNumber == null ? 0: soldNumber))*share.getPrice());
	}

	private void findNumberDisposed(String s) {
		List<Integer> li = allIndexOf(s,numberDisposedPattern);
		for (int i: li) {
			soldText = s.substring(i,Math.min(s.length()-1, i+200));
			soldNumber = getFirstNumberOrNA(soldText);
			if (soldNumber != null) return;
		}
	}

	private void findNumberAcquired(String s) {
		List<Integer> li = allIndexOf(s,numberAcquiredPattern);
		for (int i: li) {
			boughtText = s.substring(i,Math.min(s.length()-1, i+200));
			boughtNumber = getFirstNumberOrNA(boughtText);
			if (boughtNumber != null) return;
		}
	}

	public boolean nothingBoughtOrSold() { return boughtNumber == null && soldNumber == null; }
	
	public static Announcement parseAnnouncement(Share share, File f) throws IOException {
		log.debug("parsing announcement from file = " + f);
		PDFTextStripper s = new PDFTextStripper();
		PDDocument d = null;
		try {
			d = PDDocument.load(f);
			return parseAnnouncement(share, s.getText(d));
		}
		finally {
			if (d != null) d.close();
		}	
	}
	
	private static Integer getFirstNumberOrNA(String source) {
		String[] array = StringUtils.split(source); // split on whitespace
		for (String word: array) {
			String s = word.replace(",", "");
			s = s.replace(".00", "");
			if (StringUtils.isNumeric(s)) {
				return Integer.parseInt(s);
			}
			if (isNAorNil(s)) {
				return 0;
			}
		}
		return null;
	}
	
	private static boolean isNAorNil(String s) {
		if (s.length() < 3) {
			return false;
		}
		String s3 = s.substring(0, 3); // trim doesn't remove stupid whitespace-like characters
		if (s3.equalsIgnoreCase("N/A")) {
			return true;
		}
		if (s3.equalsIgnoreCase("nil")) {
			return true;
		}
		if (s.length() < 4) {
			return false;
		}
		String s4 = s.substring(0, 4); // trim doesn't remove stupid whitespace-like characters
		if (s4.equalsIgnoreCase("none")) {
			return true;
		}
		return false;
	}

	public static List<Integer> allIndexOf(String source, Pattern pattern) {
		List<Integer> found = new ArrayList<Integer>();
		Matcher m = pattern.matcher(source);
		while (m.find()) {
			//System.out.println("Found match =" + m.group());
			found.add(m.start());
		}
		return found;
	}
	
	@Override
	public String toString() {
		return "Announcement[" + type + " Bought=" + boughtNumber + ". Sold=" + soldNumber + "]";
	}

	public String showDetails() {
		return "Announcement[" + type + "Bought=" + boughtNumber + ",text=" + boughtText + ". Sold=" + soldNumber + ",text=" + soldText + "]";
	}
	public final Type getType() {
		return type;
	}
	public final void setType(Type type) {
		this.type = type;
	}
	public final Share getShare() {
		return share;
	}
	public final void setShare(Share share) {
		this.share = share;
	}
	public final int getNum() {
		return num;
	}
	public final void setNum(int num) {
		this.num = num;
	}
	public final int getValue() {
		return value;
	}
	public final void setValue(int value) {
		this.value = value;
	}

	public final String getBoughtText() {
		return boughtText;
	}

	public final void setBoughtText(String boughtText) {
		this.boughtText = boughtText;
	}

	public final String getSoldText() {
		return soldText;
	}

	public final void setSoldText(String soldText) {
		this.soldText = soldText;
	}

	public final Integer getBoughtNumber() {
		return boughtNumber;
	}

	public final void setBoughtNumber(Integer boughtNumber) {
		this.boughtNumber = boughtNumber;
	}

	public final Integer getSoldNumber() {
		return soldNumber;
	}

	public final void setSoldNumber(Integer soldNumber) {
		this.soldNumber = soldNumber;
	}

}
