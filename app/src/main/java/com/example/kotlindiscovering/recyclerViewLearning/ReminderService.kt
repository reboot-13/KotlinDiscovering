package com.example.kotlindiscovering.recyclerViewLearning

import java.util.Collections
import kotlin.random.Random

typealias RemindersListener = (reminders: List<Reminder>) -> Unit

class ReminderService {

    private var reminders = mutableListOf<Reminder>()
    private val listeners = mutableSetOf<RemindersListener>()

    fun getReminders(): List<Reminder> = reminders.toList()

    fun addReminder(){
        if (reminders.any { it.isEditing }) return
        val newReminder = Reminder(id = Random.nextLong(10000),
            title = "",
            description = "",
            isEditing = true )
        reminders = ArrayList(reminders)
        reminders.add(newReminder)
        notifyChanges()
    }

    fun finishEditing(reminder: Reminder, title: String, description: String){
        val index = reminders.indexOfFirst { it.id == reminder.id }
        if(index != -1) {
            reminders = ArrayList(reminders)
            reminders[index] = reminder.copy(
                title = title,
                description = description,
                isEditing = false
            )
            notifyChanges()
        }
    }

    private fun moveReminderToBottom (reminder: Reminder){
        val checkedCount = reminders.count { it.isDone }
        val selectedReminderIndex = reminders.indexOf(reminder)
        if (selectedReminderIndex != -1) {
            reminders = ArrayList(reminders)
            if (reminder.isDone) {
                reminders.removeAt(selectedReminderIndex)
                reminders.add(reminder)
            }
            else {
                val lastNotCheckedReminderIndex = reminders.size - 1 - checkedCount
                Collections.swap(reminders, selectedReminderIndex, lastNotCheckedReminderIndex)
            }
        }
    }

    fun deleteReminder(reminder: Reminder){
        val index = reminders.indexOfFirst { it.id == reminder.id }
        if (index != -1) {
            reminders = ArrayList(reminders)
            reminders.removeAt(index)
            notifyChanges()
        }
    }

    fun changeReminder(reminder: Reminder, changedTitle: String, changedDescription: String){
        val index = reminders.indexOfFirst { it.id == reminder.id }
        if (index != -1) {
            val updatedReminder = reminder.copy(title = changedTitle, description = changedDescription)
            reminders = ArrayList(reminders)
            reminders[index] = updatedReminder
            notifyChanges()
        }
    }



    fun onReminderCheckedChanged(isChecked: Boolean, reminder: Reminder){
        val index = reminders.indexOfFirst { it.id == reminder.id }
        if (index != -1) {
            val newReminder = reminder.copy(isDone = isChecked)
            reminders = ArrayList(reminders)
            reminders[index] = newReminder
            moveReminderToBottom(newReminder)
            notifyChanges()
        }

    }
    fun addListener(listener: RemindersListener){
        listeners.add(listener)
    }


    fun removeListener(listener: RemindersListener){
        listeners.remove (listener)
    }

    private fun notifyChanges(){
        listeners.forEach {it.invoke(reminders.toList())}
    }
}