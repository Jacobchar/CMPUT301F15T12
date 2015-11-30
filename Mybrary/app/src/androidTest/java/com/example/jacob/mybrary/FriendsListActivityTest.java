package com.example.jacob.mybrary;

import android.app.Instrumentation;
import android.content.DialogInterface;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;

/**
 * Test the FriendsList View
 * Tests are currently incomplete and broken..
 */
public class FriendsListActivityTest extends ActivityInstrumentationTestCase2 {
    public FriendsListActivityTest(){
        super(FriendsListActivity.class);
    }

    /*
        This doesn't pass yet...
         */
//    public void testFriendList(){
//        LocalUser user = LocalUser.getInstance();
//        DataManager dataManager = DataManager.getInstance();
//        User user2 = new User("TestUser1", "asdf", "234", "s3", "sdf", "23");
//        //todo: add user to the friendslist, otherwise array is empty
//        user.getFriendsList().addFriend(user2);
//        try{
//            dataManager.storeUser(user2);
//            Thread.sleep(2000);
//            FriendsListActivity activity = (FriendsListActivity) getActivity();
//            ListView text = (ListView) activity.findViewById(R.id.listView);
//            String name = (String) text.getAdapter().getItem(0);
//            assertNotNull(name);
//            dataManager.removeUser(user2.getUUID().toString());
//        }
//        catch(IOException e){
//            fail("No test on server");
//        }
//        catch(InterruptedException e){
//            fail("thread is tired");
//        }
//    }

    /*
    This doesn't pass yet...
     */
//    public void testLongClick(){
//        LocalUser user = LocalUser.getInstance();
//        DataManager dataManager = DataManager.getInstance();
//        User user2 = new User("TestUser1", "asdf", "234", "s3", "sdf", "23");
//        user.getFriendsList().addFriend(user2);
//        try{
//            dataManager.storeUser(user2);
//            Thread.sleep(2000);
//            FriendsListActivity activity = (FriendsListActivity) getActivity();
//            assertNotNull(activity);
//
//            ListView text = (ListView) activity.findViewById(R.id.listView);
//
//            // http://stackoverflow.com/questions/23454654/how-to-simulate-an-user-click-to-a-listview-item-in-junit-testing
//            // Answered by Markn12, edited by Nathan Tuggy
//            TouchUtils.longClickView(this,text.getChildAt(0));
//
//            // http://stackoverflow.com/questions/13349530/android-testing-dialog-check-it-isshowing
//            // Answered by Robin Chander
//            assertTrue(activity.getAlertDialog().isShowing());
//            dataManager.removeUser(user2.getUUID().toString());
//        }
//        catch(IOException e){
//            fail("No test on server");
//        }
//        catch(InterruptedException e){
//            fail("thread is tired");
//        }
//    }



}
