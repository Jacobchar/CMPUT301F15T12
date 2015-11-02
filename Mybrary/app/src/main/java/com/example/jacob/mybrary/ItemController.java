import java.util.ArrayList;

public class ItemController {

    private Book thisBook;
    private Button addPhoto;
    private Button addComment;
    private Button share;
    private Button changeCategory;

    void addPhoto(Photo photo){
        thisBook.photo = photo;
    }

    void addComment(Collection<String> comment)(){
        thisBook.comment.add(comment);
    }

    void share(){
        thisBook.shareWithOthers = true;
    }

    void dontShare(){
        thisBook.shareWithOthers = false;
    }

    void changeCategory(String newCategory){
        thisBook.category = newCategory;
    }
}