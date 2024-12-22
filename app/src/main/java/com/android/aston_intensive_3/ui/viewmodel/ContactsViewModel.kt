package com.android.aston_intensive_3.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.aston_intensive_3.models.Contact

class ContactsViewModel : ViewModel() {
    private val contactsMutable = MutableLiveData<List<Contact>>(mutableListOf())
    val contactsData: LiveData<List<Contact>> get() = contactsMutable

    private val selectedContactMutable = MutableLiveData<Contact?>()
    val choesedContact: LiveData<Contact?>
        get() = selectedContactMutable

    fun addContact(contact: Contact) {
        contactsMutable.value = contactsMutable.value?.plus(contact)
    }

    fun deleteContacts(toDeleteChoesed: List<Int>) {
        val updateContactList =
            contactsMutable.value?.filterIndexed { index, _ -> index !in toDeleteChoesed }
    }

    fun selectContact(contact: Contact?) {
        selectedContactMutable.value = contact
    }

    fun clearSelectedContact() {
        selectedContactMutable.value = null
    }

}