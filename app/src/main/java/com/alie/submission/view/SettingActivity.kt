package com.alie.submission.view

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alie.submission.R
import com.alie.submission.utils.AlarmReceiver
import kotlinx.android.synthetic.main.activity_setting.*
import java.util.*

class SettingActivity : AppCompatActivity() {

    private lateinit var sharePreferences: SharedPreferences
    private lateinit var spEditor : SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        tvLanguage.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)

        }
        tvFavorite.setOnClickListener {
            val intentFav = Intent(this, FavoriteActivity::class.java)
            startActivity(intentFav)
        }
        imgBackSetting.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        sharePreferences = getSharedPreferences("alarm reminder",Context.MODE_PRIVATE)
        switchReminder.setOnCheckedChangeListener { _, b ->
            spEditor = sharePreferences.edit()
            spEditor.putBoolean("notification",b)
            spEditor.apply()

            if (b){
                onDailyReminder(this)
                Toast.makeText(this, "alarm set up", Toast.LENGTH_SHORT).show()
            }else{
                offDailyRemainder(this)
                Toast.makeText(this, "alarm cancel", Toast.LENGTH_SHORT).show()
            }

        }
        val check : Boolean = sharePreferences.getBoolean("notification",false)
        switchReminder.isChecked = check


    }
    private fun onDailyReminder(context : Context){

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context,AlarmReceiver::class.java)

        val calender = Calendar.getInstance()
        calender.set(Calendar.HOUR_OF_DAY,9)
        calender.set(Calendar.MINUTE,0)
        calender.set(Calendar.SECOND,0)


        val pendingIntent = PendingIntent.getBroadcast(context, AlarmReceiver.ID_REPEATING,intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calender.timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent)


    }
    private fun offDailyRemainder(context: Context){

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context,AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, AlarmReceiver.ID_REPEATING,intent,0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)
    }
}