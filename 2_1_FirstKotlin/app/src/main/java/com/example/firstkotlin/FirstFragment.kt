package com.example.firstkotlin

import android.os.Build.ID
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.firstkotlin.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.toast_button).setOnClickListener{
            val myToast = Toast.makeText(context,"Hello Toast!",Toast.LENGTH_LONG)
            myToast.show()
        }

        view.findViewById<Button>(R.id.count_button).setOnClickListener {
            countMe(view)
        }

//        val showCountTextView = view.findViewById<TextView>(R.id.textview_first)
//        val currentCount = showCountTextView.text.toString().toInt()
//        val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(currentCount)

        binding.randomButton.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            val showCountTextView = view.findViewById<TextView>(R.id.textview_first)
            val currentCount = showCountTextView.text.toString().toInt()
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(currentCount)
            findNavController().navigate(action)
        }
    }
    private fun countMe(view: View){
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first)
        val  countstring = showCountTextView.text.toString()
        var  count = countstring.toInt()
        count++
        showCountTextView.text = count.toString()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}