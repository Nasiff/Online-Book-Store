package bean;

public class BookBean {
	private String bid;
	private String title;
	public double price;
	private String author;
	private String category;
	private double review_score;
	private int number_of_reviews;
	private String image_url;
	


	public BookBean(String bid, String title, double price, String author, String category, double review_score,
			int number_of_reviews, String image_url) {
		super();
		this.bid = bid;
		this.title = title;
		this.author = author;
		this.category = category;
		this.review_score = review_score;
		this.number_of_reviews = number_of_reviews;
		this.image_url = image_url;
	}
	
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getReview_score() {
		return review_score;
	}
	public void setReview_score(double review_score) {
		this.review_score = review_score;
	}
	public int getNumber_of_reviews() {
		return number_of_reviews;
	}
	public void setNumber_of_reviews(int number_of_reviews) {
		this.number_of_reviews = number_of_reviews;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	
	
}
