package com.example.roomdbwithviewmodel.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdbwithviewmodel.R
import com.example.roomdbwithviewmodel.adapters.ContactRecyclerAdapter
import com.example.roomdbwithviewmodel.data.Contact
import com.example.roomdbwithviewmodel.data.ContactDb
import com.example.roomdbwithviewmodel.data.DaoContact
import com.example.roomdbwithviewmodel.databinding.ActivityMainBinding
import com.example.roomdbwithviewmodel.viewModel.ContactListViewModel


class MainActivity : AppCompatActivity(), ContactRecyclerAdapter.OnItemClickListener {


    private var contactRecyclerView: RecyclerView? = null
    private var recyclerViewAdapter: ContactRecyclerAdapter? = null
    private var binding: ActivityMainBinding? = null
    private var daoContact: DaoContact? = null
    private var contact: Contact? = null
    private var id: Int? = null
    private var viewModel: ContactListViewModel? = null
    private var db: ContactDb? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        db = ContactDb.getDataBase(this)
        contactRecyclerView = binding?.recyclerview
        recyclerViewAdapter = ContactRecyclerAdapter(arrayListOf(), this)
        contactRecyclerView!!.layoutManager = LinearLayoutManager(this)
        contactRecyclerView!!.adapter = recyclerViewAdapter
        viewModel = ViewModelProvider(this).get(ContactListViewModel::class.java)
        viewModel!!.getListContacts().observe(this, Observer { contacts ->
            recyclerViewAdapter!!.addContacts(contacts!!)
        })
        binding?.btnupdate?.setOnClickListener {
            updateContact()
            Toast.makeText(this, "$id", Toast.LENGTH_SHORT).show()
        }
        binding?.button?.setOnClickListener {
            saveContact()
            Toast.makeText(this, getString(R.string.save_contact), Toast.LENGTH_SHORT).show()
        }
        binding?.button2?.setOnClickListener {
            deleteAllContacts()
            Toast.makeText(this, getString(R.string.delete_contact), Toast.LENGTH_SHORT).show()

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all_items -> {
                deleteAllContacts()
            }
            R.id.twoWayDataBinding -> {
                TwoWayDataBinding()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //
    private fun saveContact() {
        val nameContact = binding?.editTextTextPersonName?.text.toString()
        val numberContact = binding?.editTextTextPersonName2?.text.toString()
        val contact = Contact(0, nameContact, numberContact)
        viewModel!!.addContact(contact)
        binding?.editTextTextPersonName?.text?.clear()
        binding?.editTextTextPersonName2?.text?.clear()
    }

    private fun updateContact() {
        val nameContact = binding?.editTextTextPersonName?.text.toString()
        val numberContact = binding?.editTextTextPersonName2?.text.toString()
        val contact = id?.let { Contact(it, nameContact, numberContact) }
        if (contact != null) {
            viewModel?.updateContact(contact)
        }
        binding?.editTextTextPersonName?.text?.clear()
        binding?.editTextTextPersonName2?.text?.clear()
    }

    private fun deleteAllContacts() {
        db!!.daoContact().deleteAllContacts()
        binding?.editTextTextPersonName?.text?.clear()
        binding?.editTextTextPersonName2?.text?.clear()
    }

    private fun TwoWayDataBinding() {
        startActivity(Intent(this, TwoWayDataBindingActivity::class.java))
    }

    override fun onItemClick(contact: Contact) {
        // update record
        binding?.editTextTextPersonName?.setText(contact.name)
        binding?.editTextTextPersonName2?.setText(contact.number)
        id = contact.Id

    }
}
