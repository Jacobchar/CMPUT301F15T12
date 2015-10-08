import android.test.ActivityInstrumentationTestCase2;

public class testPhotographsOfItems extends ActivityInstrumentationTestCase2{
	public void testAddPhoto(){
		Item item = new Item();
		String photoFile = “filename”;
		// assuming items can default to having no photo
		assertFalse(item.hasPhoto(photoFile));
		item.addPhoto(filename);
		assertTrue(item.hasPhoto(photoFile);
	}

	public void testViewPhoto(){
		//not sure how to test
	}
	
	public void testDeletePhoto(){
		Item item = new Item();
		String photoFile = “filename”;
		item.addPhoto(filename);
		assertTrue(item.hasPhoto(photoFile);
		item.deletePhoto();
		assertFale(item.hasPhoto(photoFile);
	}

	public void testCheckPhotoSize(){
		Item item = new Item();
		String photoFile = “filename”;
		item.addFile(photoFile);
		assertTrue(item.checkFileSize() < 65536);
	}

	public void testManualDownload(){
		// Not sure how to test
	}