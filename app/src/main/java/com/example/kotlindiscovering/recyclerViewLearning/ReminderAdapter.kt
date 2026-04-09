package com.example.kotlindiscovering.recyclerViewLearning

import android.text.Editable
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.kotlindiscovering.R
import com.example.kotlindiscovering.databinding.ReminderItemBinding

interface ReminderActions{

    fun deleteReminder(reminder: Reminder)

    fun changeReminder(reminder: Reminder)

    fun showDetails(reminder: Reminder)

    fun onReminderCheckedChanged(isChecked: Boolean, reminder: Reminder)

    fun onFinishEditing(reminder: Reminder, title: String, description: String)
}

class DiffReminderCallback(val oldList: List<Reminder>, val newList: List<Reminder>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  oldList[oldItemPosition] == newList[newItemPosition]
    }
}

class ReminderAdapter(private val reminderActions: ReminderActions): RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>(), View.OnClickListener {

    var reminders: List<Reminder> = emptyList()
        set(value) {
            val diffReminderCallback = DiffReminderCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffReminderCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)

        }

    override fun onClick(v: View) {
        val reminder = v.tag as Reminder
        when(v.id){
            R.id.moreButton -> showPopupMenu(v)
            R.id.deleteButton -> reminderActions.deleteReminder(reminder)

            else -> {reminderActions.showDetails(reminder)}
        }

    }

    private fun showPopupMenu(v: View) {
        val context = v.context
        val reminder = v.tag as Reminder
        val popupMenu = PopupMenu(context, v)
        popupMenu.menu.add(0, ID_CHANGE_REMINDER, Menu.NONE, context.getString(R.string.change_reminder_popup_menu_item))
        popupMenu.menu.add(0, ID_DELETE_REMINDER, Menu.NONE, context.getString(R.string.delete_popup_menu_item))

        popupMenu.setOnMenuItemClickListener {
            when(it.itemId) {
                ID_DELETE_REMINDER -> reminderActions.deleteReminder(reminder)

                ID_CHANGE_REMINDER -> reminderActions.changeReminder(reminder)
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    private fun showDeleteButton(binding: ReminderItemBinding){
        with(binding){
            deleteButton.visibility = View.VISIBLE
            moreButton.visibility = View.INVISIBLE
        }
    }

    private fun showMoreButton(binding: ReminderItemBinding){
        with(binding){
            deleteButton.visibility = View.INVISIBLE
            moreButton.visibility = View.VISIBLE
        }
    }

    private fun showEditableComponents(binding: ReminderItemBinding){
        with(binding){
            reminderTitleEdit.visibility = View.VISIBLE
            reminderDescriptionEdit.visibility = View.VISIBLE

            reminderTitle.visibility = View.GONE
            reminderDescription.visibility = View.GONE
            reminderCheckBox.visibility = View.GONE
            moreButton.visibility = View.GONE
            deleteButton.visibility = View.VISIBLE
        }
    }

    private fun showStaticComponents(binding: ReminderItemBinding){
        with(binding){
            reminderTitleEdit.visibility = View.GONE
            reminderDescriptionEdit.visibility = View.GONE

            reminderTitle.visibility = View.VISIBLE
            reminderDescription.visibility = View.VISIBLE
            reminderCheckBox.visibility = View.VISIBLE

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ReminderItemBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        binding.moreButton.setOnClickListener(this)
        binding.deleteButton.setOnClickListener(this)
        return ReminderViewHolder(binding)
    }

    private fun requestKeyboardFocus(editableItem: EditText){
        editableItem.requestFocus()
        editableItem.post {
            val imm = editableItem.context.getSystemService(android.content.Context.INPUT_METHOD_SERVICE)
                    as android.view.inputmethod.InputMethodManager
            imm.showSoftInput(editableItem, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT)}
    }

    override fun getItemCount(): Int {
        return reminders.size
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = reminders[position]
        with(holder.binding){

            root.tag = reminder
            moreButton.tag = reminder
            deleteButton.tag = reminder

            reminderCheckBox.setOnCheckedChangeListener(null)
            reminderCheckBox.isChecked = reminder.isDone
            if (reminder.isDone) {
                showDeleteButton(this)
            } else {
                showMoreButton(this)
            }

            if (reminder.isEditing){
                reminderTitleEdit.setText(reminder.title)
                reminderDescriptionEdit.setText(reminder.description)
                showEditableComponents(this)
                requestKeyboardFocus(reminderTitleEdit)
                reminderTitleEdit.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) requestKeyboardFocus(reminderDescriptionEdit)
                }
                reminderDescriptionEdit.setOnEditorActionListener { v, actionId, event ->
                    if (actionId ==  android.view.inputmethod.EditorInfo.IME_ACTION_DONE ||
                        (event != null && event.keyCode == android.view.KeyEvent.KEYCODE_ENTER && event.action == android.view.KeyEvent.ACTION_DOWN)) {
                        reminderActions.onFinishEditing(
                        reminder,
                        reminderTitleEdit.text.toString(),
                        reminderDescriptionEdit.text.toString())
                        true
                    } else false
                }
                
            } else {
                showStaticComponents(this)

                reminderTitle.text = reminder.title
                reminderDescription.text = reminder.description
            }

            reminderCheckBox.setOnCheckedChangeListener { _, isChecked ->

                reminderActions.onReminderCheckedChanged(isChecked, reminder)
            }
        }
    }

    class ReminderViewHolder  (
        val binding: ReminderItemBinding
    ): ViewHolder(binding.root)

    companion object {
        const val ID_DELETE_REMINDER = 0
        const val ID_CHANGE_REMINDER = 1
    }
}