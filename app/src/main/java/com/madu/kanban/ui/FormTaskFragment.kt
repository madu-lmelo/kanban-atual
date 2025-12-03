package com.madu.kanban.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.madu.kanban.R
import com.madu.kanban.databinding.FragmentFormTaskBinding
import com.madu.kanban.databinding.FragmentRecoverAccountBinding
import com.madu.kanban.util.initToolbar
import com.madu.kanban.util.showBottomSheet

class FormTaskFragment : Fragment() {
    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initListener()
    }

    private fun initListener(){
        binding.buttonSave.setOnClickListener {
            validateData()
        }
    }

    private fun validateData(){
        val description = binding.editTextDescricao.text.toString().trim()

        if(description.isNotBlank()){
            Toast.makeText(requireContext(), "Certinho <3", Toast.LENGTH_SHORT).show()
        }else{
            showBottomSheet(message = getString(R.string.description_empty))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}