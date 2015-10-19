public class testFriendsList extends ActivityInstrumentationTestCase2 {
	// Use Case 2.1
	public void testAddFriend() {
		FriendList friendList = new FriendList();
		Friend friend = new Friend("David", 12);
		friendList.addFriend(friend);
		assertTrue(friendList.hasFriend(friend));
	}
	// Use Case 2.5
	public void testRemoveFriend() {
		FriendList friendList = new FriendList();
		Friend friend = new Friend("Bob", 14);
		friendList.addFriend(friend);
		friendList.removeFriend(friend);
		assertFalse(friendList.hasFriend(friend));
	}

	public void testHasFriend() {
		FriendList friendList = new FriendList();
		Friend friend = new Friend("Freddy", 16);
		assertFalse(friendList.hasFriend(friend));
		friendList.addFriend(friend);
		assertTrue(friendList.hasFriend(friend));
	}

	public void testNumFriends() {
		FriendList friendList = new FriendList();
		Friend friend1 = new Friend("David", 12);
		friendList.addFriend(friend1);
		Friend friend2 = new Friend("Leroy", 14);
		friendList.addFriend(friend2);
		int numFriends = friendList.numFriends();
		assertEquals(2, numFriends);
	}
}
