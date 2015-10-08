public class testSearch extends ActivityInstrumentationTestCase2 {

	public void testBookSearch() {
		BookList bookList = new BookList();
		Book book = new Book("Expensive Textbook", "Your mom", "Haven't read", "Erotica", 12);
		bookList.addBook(book);
		Search searchedBook = new Search();
		Book book1 = searchedBook.searchBookByISBN(12);
		assertEqual(book, book1);
	}

	public void testFriendSearch() {
		FriendList friendList = new FriendList();
		Friend friend = new Friend("David", 12);
		friendList.addBook(friend);
		Search searchedFriend = new Search();
		Friend friend2 = searchedFriend.searchFriendByID(12);
		assertEqual(friend, friend2);
	}
}