package com.android.aston_intensive_3.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.aston_intensive_3.R
import com.android.aston_intensive_3.adapters.ContactsAdapter
import com.android.aston_intensive_3.databinding.ActivityMainBinding
import com.android.aston_intensive_3.models.Contact
import com.android.aston_intensive_3.ui.viewmodel.ContactsViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: ContactsViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerAdapter: ContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerInit()

        with(binding){
            buttonAdd.setOnClickListener {
                for (iterator in 1..100) {
                    viewModel.addContact(
                        Contact(
                            iterator, "Имя$iterator",
                            "Фамилия", "Номер"
                        )
                    )
                    viewModel.contactsData.observe(this@MainActivity, Observer { contacts ->
                        recyclerAdapter.updateContactList(contacts)
                    })
                }
            }
        }
    }

    private fun recyclerInit(){
        recyclerAdapter = ContactsAdapter()
        with(binding){
            contactsRecycler.adapter = recyclerAdapter
            contactsRecycler.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}