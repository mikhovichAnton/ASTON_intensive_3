package com.android.aston_intensive_3.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.aston_intensive_3.models.Contact

class ContactsViewModel : ViewModel() {

    private val initContacts = mutableListOf<Contact>()

    private val contactsMutable = MutableLiveData<List<Contact>>(mutableListOf())
    val contactsData: MutableLiveData<List<Contact>> = contactsMutable

    private val selectedContactMutable = MutableLiveData<Contact?>()
    val choesedContact: LiveData<Contact?> = selectedContactMutable

    init {
        for (iterator in 1..100) {
            initContacts.add(
                Contact(
                    iterator, "Имя$iterator",
                    "Фамилия", "Номер"
                )
            )
        }
        contactsMutable.value = initContacts
    }

    fun addContact(contact: Contact) {
        contactsMutable.value = contactsMutable.value?.plus(contact)
    }

    fun deleteContacts(toDeleteChoesed: List<Int>) {
        val updatedList = contactsMutable
            .value?.filterIndexed { index, _ -> index !in toDeleteChoesed }
        contactsMutable.value = updatedList!!
    }

    fun selectContact(contact: Contact) {
        selectedContactMutable.value = contact
    }

    fun updateContact(updatedContact: Contact) {
        contactsMutable.value?.let { currentList ->
            currentList.map { contact ->
                if (contact.id == updatedContact.id) {
                    initContacts[updatedContact.id - 1] = updatedContact
                }
            }
            contactsMutable.value = initContacts
        }
    }
}