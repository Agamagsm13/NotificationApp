package com.example.testappl.view.fragment

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testappl.App
import com.example.testappl.R
import com.example.testappl.databinding.FragmentMainBinding
import com.example.testappl.utils.Constants.CHANNEL_ID
import com.example.testappl.utils.Constants.DELAY
import com.example.testappl.utils.Constants.ID
import com.example.testappl.utils.Constants.URL
import com.example.testappl.utils.receiver.NotificationReceiver
import com.example.testappl.view.activity.WebActivity
import timber.log.Timber

class MainFragment: Fragment() {
    private var _binding: FragmentMainBinding? = null
    val binding get() = _binding!!
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        binding.button.setOnClickListener {
            notificationChannel = NotificationChannel(CHANNEL_ID, "description", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
            notificationManager.cancelAll()
            scheduleNotification(binding.editText.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun scheduleNotification(contentText: String) {
        Timber.d(contentText)
        val intent = Intent(requireContext(), WebActivity::class.java).putExtra(URL, contentText)
        val activity = PendingIntent.getActivity(
            requireContext(),
            ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        builder = Notification.Builder(requireActivity(), CHANNEL_ID)
            .setContentText(contentText)
            .setSmallIcon(R.drawable.sun)
            .setAutoCancel(true)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.sun))
            .setContentIntent(activity)
        val notification = builder.build()
        val notificationIntent = Intent(requireContext(), NotificationReceiver::class.java)
        notificationIntent.putExtra(NotificationReceiver.NOTIFICATION_ID, ID)
        notificationIntent.putExtra(NotificationReceiver.NOTIFICATION, notification)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            ID,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val futureInMillis = SystemClock.elapsedRealtime() + DELAY
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager[AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis] = pendingIntent
    }

}