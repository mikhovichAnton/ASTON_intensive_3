package com.android.aston_intensive_3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.aston_intensive_3.R
import com.android.aston_intensive_3.models.Contact
import com.android.aston_intensive_3.utils.ContactDiffUtilCallback

class ContactsAdapter(
    private val clickOnItem: (Int) -> Unit,
    private val longClickOnItem: (Int) -> Unit
) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    var contacts: List<Contact> = emptyList()

    class ContactViewHolder(itemContact: View) : RecyclerView.ViewHolder(itemContact) {
        val nameTextView: TextView = itemContact.findViewById(R.id.tv_name)
        val phoneTextView: TextView = itemContact.findViewById(R.id.tv_phone_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val container = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(container)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        "${contact.firstName} ${contact.lastName}".also { holder.nameTextView.text = it }
        holder.phoneTextView.text = contact.phoneNumber

        holder.itemView.setOnClickListener { clickOnItem(position) }
        holder.itemView.setOnLongClickListener {
            longClickOnItem(position)
            true
        }
    }

    override fun getItemCount(): Int = contacts.size

    fun updateContactList(newContacts: List<Contact>) {
        val diffCallBack = ContactDiffUtilCallback(contacts, newContacts)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)
        contacts = newContacts
        diffResult.dispatchUpdatesTo(this)
    }
}