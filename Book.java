public class Book {

	private String title, author, bio, category;
	private Integer isbn;
	public void book(String title, String author, String bio, String category, Integer isbn){
		this.title = title;
		this.author = author;
		this.bio = bio;
		this.category = category;
		this.isbn = isbn;
	}
}