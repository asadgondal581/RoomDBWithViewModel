package com.example.roomdbwithviewmodel.view


import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.roomdbwithviewmodel.R
import com.example.roomdbwithviewmodel.data.Contact
import com.example.roomdbwithviewmodel.data.ContactDb
import com.example.roomdbwithviewmodel.data.DaoContact
import com.example.roomdbwithviewmodel.databinding.ActivityContactDetailsBinding
import com.example.roomdbwithviewmodel.viewModel.ContactListViewModel


class ContactDetailsActivity : AppCompatActivity() {


    private var daoContact: DaoContact? = null
    private var viewModel: ContactListViewModel? = null
    private var binding: ActivityContactDetailsBinding? = null

    private var currentContact: Int? = null
    private var contact: Contact? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactDetailsBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        val db: ContactDb = ContactDb.getDataBase(this)

        daoContact = db.daoContact()

        viewModel = ViewModelProvider(this).get(ContactListViewModel::class.java)
        currentContact = intent.getIntExtra("idContact", -1)
        if (currentContact != -1) {
            setTitle(R.string.edit_contact_title)
            contact = daoContact!!.getContactById(currentContact!!)
            binding?.nameEditText?.setText(contact!!.name)
            binding?.numberEditText?.setText(contact!!.number)
        } else {
            setTitle(R.string.add_contact_title)
            invalidateOptionsMenu()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.done_item -> {
                if (currentContact == -1) {
                    //saveContact()
                    Toast.makeText(this, getString(R.string.save_contact), Toast.LENGTH_SHORT)
                        .show()

                } else {
                    updateContact()
                    Toast.makeText(this, getString(R.string.update_contact), Toast.LENGTH_SHORT)
                        .show()
                }
                finish()
            }

            R.id.delete_item -> {
                deleteContact()
                Toast.makeText(this, getString(R.string.delete_contact), Toast.LENGTH_SHORT).show()
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        super.onPrepareOptionsMenu(menu)
        if (currentContact == -1) {
            menu.findItem(R.id.delete_item).isVisible = false
        }
        return true
    }



    private fun deleteContact() {
        daoContact!!.deleteContact(contact!!)
    }

    private fun updateContact() {
        val nameContact = binding?.nameEditText?.text.toString()
        val numberContact = binding?.numberEditText?.text.toString()
        val contact = Contact(contact!!.Id, nameContact, numberContact)
        daoContact!!.updateContact(contact)
    }
}