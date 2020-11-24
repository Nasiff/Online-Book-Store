package bean;

public class ReviewBean {
	private String rid;
	private String bid;
	private int uid;
	private String review;
	private int score;
	
	public ReviewBean(String rid, String bid, int uid, String review, int score) {
		super();
		this.rid = rid;
		this.bid = bid;
		this.uid = uid;
		this.review = review;
		this.score = score;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
}
