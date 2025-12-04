package com.madu.kanban.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.madu.kanban.R
import com.madu.kanban.databinding.FragmentRegisterBinding
import com.madu.kanban.util.initToolbar
import com.madu.kanban.util.showBottomSheet
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initListener()
    }

    private fun initListener(){
        binding.registerButton.setOnClickListener {
            validateData()
        }
    }

    private fun registerUser(email: String, password: String) {
        try {
            auth.createUserWithEmailAndPassword(email, password) // Corrigido: 'With'
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        findNavController().navigate(R.id.action_global_homeFragment)
                    } else {
                        Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
        }catch (e: Exception){
            Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    private fun validateData() {
        val email = binding.editTextEmail.text.toString().trim()
        val senha = binding.editTextSenha.text.toString().trim()

        // CORREÇÃO 3: Remova a navegação antecipada daqui e chame registerUser()
        if (email.isNotBlank()) {
            if (senha.isNotBlank()) {
                // Se a validação local passar, CHAMA a função de registro
                registerUser(email, senha)
            } else {
                showBottomSheet(message = getString(R.string.password_empty_register))
            }
        } else {
            showBottomSheet(message = getString(R.string.email_empty_register))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}