import android.test.ActivityInstrumentationTestCase2;

public class testPhotographsOfItems extends ActivityInstrumentationTestCase2{
	//Use Case 5.1
	public void testAddPhoto(){
		Book book = new Book();
		String photoFile = "filename";
		// assuming items can default to having no photo
		assertFalse(book.hasPhoto(photoFile));
		book.addPhoto(filename);
		assertTrue(book.hasPhoto(photoFile);
	}

	public void testViewPhoto(){
		Book book = new Book();
		String photoFile = "filename";
		book.add(photoFile)
		assertTrue(book.hasPhoto());
	}
	// Use Case 5.2
	public void testDeletePhoto(){
		Book book = new Book();
		String photoFile = "filename";
		book.addPhoto(filename);
		assertTrue(book.hasPhoto(photoFile);
		item.deletePhoto();
		assertFalse(book.hasPhoto(photoFile);
	}
	// Use Case 5.3
	public void testCheckPhotoSize(){
		Book book = new Book();
		String photoFile = "filename";
		book.addFile(photoFile);
		assertTrue(book.checkFileSize() < 65536);
	}
	// Use Case 5.4
	public void testManualDownload(){
		// Not sure how to test
	}
