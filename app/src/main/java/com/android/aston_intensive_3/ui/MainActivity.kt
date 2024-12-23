package com.android.aston_intensive_3.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.aston_intensive_3.R
import com.android.aston_intensive_3.adapters.ContactsAdapter
import com.android.aston_intensive_3.databinding.ActivityMainBinding
import com.android.aston_intensive_3.models.Contact
import com.android.aston_intensive_3.ui.fragments.EditContactFragment
import com.android.aston_intensive_3.ui.viewmodel.ContactsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ContactsViewModel by viewModels()
    private lateinit var recyclerAdapter: ContactsAdapter
    private var itemSelection = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerInit()
        observerInit()
        clickObserver()

        with(binding) {
            buttonAdd.setOnClickListener {
                onAddAndEditContactFragment(null)
            }
        }

        onUpdateUi()
    }

    private fun onAddAndEditContactFragment(contact: Contact?) {
        val fragment = EditContactFragment()
        val fragmentManager: FragmentManager = this.supportFragmentManager
        fragmentManager.apply {
            beginTransaction()
            .replace(R.id.contact_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
        }
    }

    private fun recyclerInit() {
        recyclerAdapter = ContactsAdapter(
            clickOnItem = { position -> onClick(position) },
            longClickOnItem = { position -> toggleClick(position) }
        )
        with(binding) {
            contactsRecycler.adapter = recyclerAdapter
            contactsRecycler.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun onClick(position: Int) {
        if (itemSelection.isEmpty()){
            viewModel.selectContact(recyclerAdapter.contacts[position])
        }else{
            toggleClick(position)
        }
    }

    private fun toggleClick(position: Int) {
        if (itemSelection.contains(position)){
            itemSelection.remove(position)
        }else{
            itemSelection.add(position)
        }
        recyclerAdapter.notifyItemChanged(position)
        onUpdateUi()
    }

    private fun observerInit(){
        viewModel.contactsData.observe(this@MainActivity, Observer { contacts ->
            if (contacts != null) {
                recyclerAdapter.updateContactList(contacts)
            }
        })
    }

    private fun clickObserver(){
        viewModel.choesedContact.observe(this, Observer { contact ->
              contact?.let { onAddAndEditContactFragment(contact) }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_trash, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_trash -> {
                onUpdateUi()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onUpdateUi() {

        with(binding){
            buttonDelete.visibility = if (itemSelection.isNotEmpty()) View.VISIBLE else View.GONE
            buttonCancel.visibility = if (itemSelection.isNotEmpty()) View.VISIBLE else View.GONE
            buttonAdd.visibility = if (itemSelection.isEmpty()) View.VISIBLE else View.GONE

            if (itemSelection.isNotEmpty()){
                val itemsToDelete  = itemSelection.toList()
                buttonDelete.setOnClickListener {
                    viewModel.deleteContacts(itemsToDelete)
                    itemSelection.clear()
                    onUpdateUi()
                }
            }

            buttonCancel.setOnClickListener {
                itemSelection.clear()
                onUpdateUi()
            }
        }
    }
}