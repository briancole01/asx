package au.brian.asx.domain;

public class Share {

	private String code;
	private float price;
	private float yield;
	
	public Share(String code) { this.code = code; }
	
	public final String getCode() {
		return code;
	}
	public final void setCode(String code) {
		this.code = code;
	}
	public final float getYield() {
		return yield;
	}
	public final void setYield(float yield) {
		this.yield = yield;
	}

	public final float getPrice() {
		return price;
	}

	public final void setPrice(float price) {
		this.price = price;
	}
	
}
