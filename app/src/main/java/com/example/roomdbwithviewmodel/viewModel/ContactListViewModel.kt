package com.example.roomdbwithviewmodel.viewModel


import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.roomdbwithviewmodel.data.Contact
import com.example.roomdbwithviewmodel.data.ContactDb
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ContactListViewModel(application: Application) : AndroidViewModel(application) {

    var listContact: LiveData<List<Contact>>
    private val appDb: ContactDb

    init {
        appDb = ContactDb.getDataBase(this.getApplication())
        listContact = appDb.daoContact().getAllContacts()
    }

    fun getListContacts(): LiveData<List<Contact>> {
        return listContact
    }

    fun addContact(contact: Contact) {
        addAsynTask(appDb).execute(contact)
    }
    fun updateContact(contact:Contact){
        addAsynTask(appDb).execute(contact)
    }


    class addAsynTask(db: ContactDb) : AsyncTask<Contact, Void, Void>() {
        private var contactDb = db
        override fun doInBackground(vararg params: Contact): Void? {
            contactDb.daoContact().insertContact(params[0])
            contactDb.daoContact().updateContact(params[0])
            return null
        }

    }

}