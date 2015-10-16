public class testFriendsList extends ActivityInstrumentationTestCase2 {
	// Use Case 2.1
	public void testAddFriend() {
		FriendList friendList = new FriendList();
		Friend friend = new Friend("David", 12);
		friendList.addBook(friend);
		assertTrue(friendList.hasBook(friend));

		Friend friend2 = new Friend("David", 12);
		friendList.addBook(friend2);
		int numFriends = friendList.numFriends();
		assertEquals(1, numFriends);
	}
	// Use Case 2.5
	public void testExterminateFriend() {
		FriendList friendList = new FriendList();
		Friend friend = new Friend("Bob", 14);
		friendList.addBook(friend);
		friendList.DeleteBook(friend);
		assertFalse(friendList.hasBook(friend));
	}

	public void testHasFriend() {
		FriendList friendList = new FriendList();
		Friend friend = new Friend("Freddy", 16);
		assertFalse(friendList.hasBook(friend));
		friendList.addBook(friend);
		assertTrue(friendList.hasBook(friend));
	}
	public void testNumFriends() {
		FriendList friendList = new FriendList();
		Friend friend1 = new Friend("David", 12);
		friendList.addBook(friend1);
		Friend friend2 = new Friend("Leroy", 14);
		friendList.addBook(friend2);
		int numFriends = friendList.numFriends();
		assertEquals(2, numFriends);
	}
}
