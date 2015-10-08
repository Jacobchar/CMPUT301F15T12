public class testInventory extends ActivityInstrumentationTestCase2 {

	public void testAddBook() {
		BookList bookList = new BookList();
		Book book = new Book("Expensive Textbook", "Your mom", "Haven't read", "Erotica", 12);
		bookList.addBook(book);
		assertTrue(bookList.hasBook(book));
	}

	public void testDeleteBook() {
		BookList bookList = new BookList();
		Book book = new Book("Expensive Textbook", "Your mom", "Haven't read", "Erotica", 12);
		bookList.addBook(book);
		bookList.DeleteBook(book);
		assertFalse(bookList.hasBook(book));
	}

	public void testHasBook() {
		BookList bookList = new BookList();
		Book book = new Book("Expensive Textbook", "Your mom", "Haven't read", "Erotica", 12);
		assertFalse(bookList.hasBook(book));
		bookList.addBook(book);
		assertTrue(bookList.hasBook(book));
	}

	public void testSizeInventory() {
		BookList bookList = new BookList();
		Book book1 = new Book("Expensive Textbook", "The University", "Haven't read", "Erotica", 12);
		bookList.addBook(book1);
		Book book2 =  new Book("Cheap Textbook", "Gahndi", "Useful", "Academia", 15);
		bookList.addBook(book2);
		int books = bookList.getSizeInventory();
		assertEquals(2, books);
	}

	public void testNumCopies() {
		BookList bookList = new BookList();
		Book book1 = new Book("Expensive Textbook", "The University", "Haven't read", "Erotica", 12);
		bookList.addBook(book1);
		Book book2 = new Book("Expensive Textbook", "The University", "Haven't read", "Erotica", 12);
		bookList.addBook(book2);
		int numCopies = bookList.numCopies(isbn);
		assertEquals(2, numCopies);
	}
}