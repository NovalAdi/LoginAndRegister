package com.dzakyhdr.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dzakyhdr.authentication.databinding.ActivityLoginBinding
import com.dzakyhdr.authentication.utils.Status
import com.dzakyhdr.authentication.viewmodel.LoginViewModel
import com.dzakyhdr.authentication.viewmodel.LoginViewModelFactory
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userDataStoreManager = UserDataStoreManager(this)

        viewModel = ViewModelProvider(
            this,
            LoginViewModelFactory.getInstance(this, userDataStoreManager)
        )[LoginViewModel::class.java]


        binding.txtToLogin.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        viewModel.getStatus().observe(this) { status ->
            if (status) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

        }

        binding.btnLogin.setOnClickListener {
            Toast.makeText(this, "test", Toast.LENGTH_SHORT).show()
            viewModel.login(
                binding.edtUsername.text.toString(),
                binding.edtPassword.text.toString()
            )
        }

        viewModel.loginStatus.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data != null) {
                        viewModel.saveUserDataStore(true, it.data.id)
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {
                        Snackbar.make(binding.root, "User Tidak Ditemukan", Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

    }

}
