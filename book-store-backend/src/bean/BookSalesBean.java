package bean;

public class BookSalesBean extends BookBean {
	private int sales;
	private int rank;
	
	public BookSalesBean(String bid, String title, double price, String author, String category, double review_score,
			int number_of_reviews, String image_url, int sales, int rank) {
		super(bid, title, price, author, category, review_score, number_of_reviews, image_url);
		this.sales = sales;
		this.rank = rank;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
}
