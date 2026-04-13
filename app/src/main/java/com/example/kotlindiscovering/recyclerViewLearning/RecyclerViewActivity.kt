package com.example.kotlindiscovering.recyclerViewLearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.kotlindiscovering.databinding.ActivityRecyclerViewBinding
class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerViewBinding
    private lateinit var adapter: ReminderAdapter
    private lateinit var layoutManager: LayoutManager
    private val reminderService = ReminderService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecyclerViewBinding.inflate(layoutInflater).also { setContentView(it.root) }
        adapter = ReminderAdapter(object: ReminderActions {

            override fun deleteReminder(reminder: Reminder) {
                reminderService.deleteReminder(reminder)
            }

            override fun changeReminder(reminder: Reminder) {
                showChangeReminderDialog(reminder)

            }

            override fun showDetails(reminder: Reminder) {
                Toast.makeText(this@RecyclerViewActivity, "${reminder.title} id: ${reminder.id}", Toast.LENGTH_SHORT).show()
            }


            override fun onReminderCheckedChanged(isChecked: Boolean, reminder: Reminder) {
                reminderService.onReminderCheckedChanged(isChecked, reminder)
            }

            override fun onFinishEditing(reminder: Reminder, title: String, description: String) {
                reminderService.moveReminderToBottom(reminder)
                reminderService.finishEditing(reminder, title, description)
            }
        })



        layoutManager = LinearLayoutManager(this)
        binding.recyclerviewLearning.adapter = adapter
        adapter.reminders = reminderService.getReminders()
        binding.recyclerviewLearning.layoutManager = layoutManager

        reminderService.addListener(remindersListener)

        binding.addReminderButton.setOnClickListener{
            reminderService.addReminder()

        }
        setupChangeReminderDialogListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        reminderService.removeListener(remindersListener)
    }

    private val remindersListener: RemindersListener = { reminders ->
        adapter.reminders = reminders

        val isCreatingReminderNow = reminders.any { it.isEditing }
        binding.addReminderButton.isEnabled = !isCreatingReminderNow
        binding.addReminderButton.alpha = if (isCreatingReminderNow) 0.5f else 1f

    }

    private fun showChangeReminderDialog(reminder: Reminder) {
        ChangeReminderDialogFragment.show(supportFragmentManager, reminder)
    }


    private fun setupChangeReminderDialogListener () {
        ChangeReminderDialogFragment.setupListener(supportFragmentManager, this) {id, title, desc ->
            val updatedReminder = Reminder(id, title, desc)
            reminderService.changeReminder(updatedReminder, title, desc)
        }

    }
}