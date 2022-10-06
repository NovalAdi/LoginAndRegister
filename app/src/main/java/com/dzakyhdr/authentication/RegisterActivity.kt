package com.dzakyhdr.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dzakyhdr.authentication.data.UserEntity
import com.dzakyhdr.authentication.databinding.ActivityRegisterBinding
import com.dzakyhdr.authentication.viewmodel.RegisterViewModel
import com.dzakyhdr.authentication.viewmodel.RegisterViewModelFactory
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            RegisterViewModelFactory.getInstance(this)
        )[RegisterViewModel::class.java]

        binding.btnRegister.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            viewModel.insert(UserEntity(username = username, password = password))
        }

        viewModel.saved.observe(this) {
            val check = it.getContentIfNotHandled() ?: return@observe

            if (check) {
                val intent = Intent(this, LoginActivity::class.java)
                Toast.makeText(this, "User Berhasil Dibuat", Toast.LENGTH_LONG).show()
                startActivity(intent)
            } else {
                Snackbar.make(binding.root, "User Gagal Dibuat", Snackbar.LENGTH_LONG).show()
            }
        }

    }
}