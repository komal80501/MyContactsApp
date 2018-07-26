package com.example.felix_its_07.mycontactsapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MyRecycler adapter;

    List<MyContact> cntList;

    private RecyclerView recyclerView;
    Uri uriContact=ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS =10;

    String [] projection=new String[]{ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.PHOTO_ID};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        showContacts();
    }

    private void showContacts() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);

        }else {
            cntList= new ArrayList<>();
            cntList = getContactNames();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            MyRecycler adapter=new MyRecycler(this,cntList);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==PERMISSIONS_REQUEST_READ_CONTACTS){

            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                showContacts();
            }
            else
            {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();

            }

        }
    }

    private ArrayList<MyContact> getContactNames() {

        ArrayList<MyContact> contacts=new ArrayList<>();

        ContentResolver contentResolver=getContentResolver();
        Cursor cursor=contentResolver.query(uriContact,null,null,null, null);
        if(cursor.moveToFirst()){

            do{
                String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String contactID=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //int imgsrc=cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_ID);


                contacts.add(new MyContact(name,contactID));

            }while (cursor.moveToNext());
        }
        cursor.close();
        return contacts;
    }
}
