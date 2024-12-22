package com.android.aston_intensive_3.ui

import android.os.Bundle
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



        for (iterator in 1..100) {
            viewModel.addContact(
                Contact(
                    iterator, "Имя$iterator",
                    "Фамилия", "Номер"
                )
            )
        }

        with(binding) {
            buttonAdd.setOnClickListener {
                onAddAndEditContactFragment(null)
            }
        }
    }

    private fun onAddAndEditContactFragment(contact: Contact?) {
        viewModel.clearSelectedContact()
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
    }

    private fun observerInit(){
        viewModel.contactsData.observe(this@MainActivity, Observer { contacts ->
            recyclerAdapter.updateContactList(contacts)
        })
    }

    private fun clickObserver(){
        viewModel.choesedContact.observe(this, Observer { contact ->
            contact?.let { onAddAndEditContactFragment(contact) }
        })
    }
}