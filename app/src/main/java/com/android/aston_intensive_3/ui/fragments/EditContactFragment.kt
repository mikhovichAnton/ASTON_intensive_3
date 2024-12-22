package com.android.aston_intensive_3.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.aston_intensive_3.databinding.FragmentEditContactBinding
import com.android.aston_intensive_3.models.Contact
import com.android.aston_intensive_3.ui.viewmodel.ContactsViewModel

class EditContactFragment : Fragment() {

    private lateinit var binding: FragmentEditContactBinding
    private val viewModel: ContactsViewModel by activityViewModels()
    private var editContact: Contact? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditContactBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        onClickedView()

        Toast.makeText(context,"firstName",Toast.LENGTH_SHORT).show()
        viewModel.choesedContact.observe(viewLifecycleOwner) { contact ->
            editContact = contact
            editContact?.let {
                with(binding){
                    etFirstName.setText(it.firstName)
                    etLastName.setText(it.lastName)
                    etPhoneNumber.setText(it.phoneNumber)
                }
            }
        }

        with(binding){
            buttonSave.setOnClickListener {
                val firstName = etFirstName.text.toString()
                val lastName = etLastName.text.toString()
                val phoneNumber = etPhoneNumber.text.toString()

                if (firstName.isNotEmpty() && lastName.isNotEmpty() && phoneNumber.isNotEmpty()){
                    val newContact = Contact(
                        id = editContact?.id ?: 0,
                        firstName = firstName,
                        lastName = lastName,
                        phoneNumber = phoneNumber
                    )
                    viewModel.addContact(newContact)
                    parentFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun onClickedView(){

    }
}