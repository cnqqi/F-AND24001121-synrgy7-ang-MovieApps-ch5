package com.synrgy.movieapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.synrgy.movieapp.R
import com.synrgy.movieapp.database.AppDatabase
import com.synrgy.movieapp.util.UserRepository
import com.synrgy.movieapp.viewmodel.AuthViewModel
import com.synrgy.movieapp.viewmodel.AuthViewModelFactory
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Hide the action bar
        (activity as? AppCompatActivity)?.supportActionBar?.hide()

        // Initialize database and repository
        val database = AppDatabase.getInstance(requireContext())
        val userDao = database.userDao()
        val userRepository = UserRepository(userDao)
        val factory = AuthViewModelFactory(userRepository)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        view.findViewById<Button>(R.id.btnlogin).setOnClickListener {
            val username = view.findViewById<EditText>(R.id.et_name).text.toString()
            val password = view.findViewById<EditText>(R.id.et_password).text.toString()

            lifecycleScope.launch {
                val user = viewModel.login(username, password)
                if (user != null) {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    showToast("Login failed. Please check your credentials.")
                }
            }
        }

        view.findViewById<Button>(R.id.btn_register_in_login).setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return view
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
