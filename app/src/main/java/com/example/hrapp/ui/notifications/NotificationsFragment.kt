package com.example.hrapp.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hrapp.application.OneHR
import com.example.hrapp.databinding.FragmentNotificationsBinding
import data.Notification


class NotificationsFragment : Fragment() {
    //private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null
    private lateinit var recyclerview: RecyclerView;
    private lateinit var adapter: NotificationAdapter;
    //private lateinit var notif: ArrayList<String>;
    //private lateinit var time: ArrayList<String>;

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val notificationsViewModel : NotificationsViewModel by viewModels{
        NotificationsViewModelFactory((this.requireActivity().application as OneHR).repo)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)


        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textViewNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        recyclerview = binding.recyclerViewNotification
        recyclerview.layoutManager = LinearLayoutManager(this.context)

        binding.clearAllButton.setOnClickListener {
            clearAll()
        }

        notificationsViewModel.getList().observe(viewLifecycleOwner, Observer{
            adapter = NotificationAdapter(it as ArrayList<Notification>)
            recyclerview.adapter = adapter
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*** Clear all notifications upon FloatingActionButton click ***/
    private fun clearAll(){
        notificationsViewModel.deleteAll()
    }

}