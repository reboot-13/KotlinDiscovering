package com.example.kotlindiscovering.recyclerViewLearning

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.kotlindiscovering.R
import com.example.kotlindiscovering.databinding.ChangeReminderItemBinding

class ChangeReminderDialogFragment(val reminder: Reminder): DialogFragment() {
    private var _binding: ChangeReminderItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = ChangeReminderItemBinding.inflate(layoutInflater)
        binding.reminderTitle.setText(reminder.title)
        binding.reminderDescription.setText(reminder.description)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()

        binding.reminderTitle.setSelectAllOnFocus(true)
        binding.reminderDescription.setSelectAllOnFocus(true)

        binding.dialogPositiveButton.setOnClickListener{
            val updatedTitle = binding.reminderTitle.text.toString()
            val updatedDescription = binding.reminderDescription.text.toString()
            val result = bundleOf(KEY_ID to reminder.id, KEY_TITLE to updatedTitle, KEY_DESCRIPTION to updatedDescription)
            parentFragmentManager.setFragmentResult(REQUEST_KEY, result)
            dialog.dismiss()
        }

        binding.dialogNegativeButton.setOnClickListener{
            dialog.dismiss()
        }

        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val TAG: String = ChangeReminderDialogFragment::class.java.name
        val KEY_TITLE = "$TAG:keyTitle"
        val KEY_DESCRIPTION = "$TAG:keyDescription"
        val KEY_ID = "$TAG:keyID"

        val REQUEST_KEY = "$TAG:defaultRequestKey"

        fun show(fragmentManager: FragmentManager, reminder: Reminder) {
            val dialogFragment = ChangeReminderDialogFragment(reminder)
            dialogFragment.arguments = bundleOf(KEY_TITLE to reminder.title, KEY_DESCRIPTION to reminder.description)
            dialogFragment.show(fragmentManager, TAG)
        }

        fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, listener: (Long, String, String) -> Unit){
            manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner, FragmentResultListener {_, result ->
                listener.invoke(result.getLong(KEY_ID), result.getString(KEY_TITLE).toString(), result.getString(KEY_DESCRIPTION).toString())
            })
        }


    }
}