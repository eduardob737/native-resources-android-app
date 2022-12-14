package com.example.nativeresourcesandroid.contact


import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nativeresourcesandroid.R

class ContactActivity : AppCompatActivity() {

    val REQUEST_CONTACT = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CONTACTS), REQUEST_CONTACT)
        } else {
            setContacts()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CONTACT) setContacts()

    }


    @SuppressLint("Range")
    private fun setContacts() {
        val concactList: ArrayList<Contact> = ArrayList()

        val cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)

        if (cursor != null){
            while (cursor.moveToNext()) {
                concactList.add(
                    Contact(
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                )
                )
            }
            cursor.close()
        }

        val adapter = ContactAdapter(concactList)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_contacts)
        recyclerView.layoutManager = LinearLayoutManager (this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
    }
}