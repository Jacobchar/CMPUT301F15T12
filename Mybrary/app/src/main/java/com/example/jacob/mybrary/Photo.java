
public class Photo {

    private Integer size;
    private String format;
    private String encodedImage;

    /* This is to be done later, in project 5 */


    Boolean checkValidFormat(String format){

    }

    Boolean checkValidSize(Integer size){
       if (size < 65536){
           return true;
       } else {
           return false;
       }

    }

    String convertImageToJson(Bitmap image){


    }

}