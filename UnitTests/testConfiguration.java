import android.test.ActivityInstrumentationTestCase2;

public class testConfiguration extends ActivityInstrumentationTestCase2{
	// Use Case 7.1
	public testDownloadImageSettings(){
		Configuration config  = new Configuration();
		// Assuming download images defaults to false
		assertFalse(config.getDownloadSetting);
		config.setDownloadSetting(true);
		assertTrue(config.getDownloadSetting);
	}

	public testEditProfileName(){
	// Use Case 7.2
		Configuration config  = new Configuration();
		User user = new User("Larry", 1234);
		assertTrue(user.getName().equals("Larry");
		config.editName("Bob");
		assertTrue(user.getName().equals("Bob");
	}

	public testEditProfilePassword(){
	// Use Case 7.2
		Configuration config  = new Configuration();
		User user = new User("Larry", 1234);
		assertTrue(user.getPassword() == 1234);
		config.editPassword(4567);
		assertTrue(user.getPassword() == 4567);
	}
