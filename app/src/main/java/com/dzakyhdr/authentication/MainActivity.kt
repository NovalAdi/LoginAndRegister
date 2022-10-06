package com.dzakyhdr.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dzakyhdr.authentication.databinding.ActivityMainBinding
import com.dzakyhdr.authentication.utils.Status
import com.dzakyhdr.authentication.viewmodel.MainViewModel
import com.dzakyhdr.authentication.viewmodel.MainViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userDataStoreManager = UserDataStoreManager(this)

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory.getInstance(this, userDataStoreManager)
        )[MainViewModel::class.java]

        viewModel.getIdUser().observe(this) {
            viewModel.userData(it)
        }

        viewModel.userData.observe(this) { user ->
            when (user.status) {
                Status.SUCCESS -> {
                    if (user.data != null) {
                        binding.txtUsername.text = user.data.username
                    } else {
                        Snackbar.make(binding.root, "User Tidak Ditemukan", Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(this, user.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }

        binding.btnLogout.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Logout")
            dialog.setMessage("Apakah anda yakin")
            dialog.setPositiveButton("ya") { _, _ ->
                Snackbar.make(binding.root, "Berhasil Logout", Snackbar.LENGTH_LONG).show()
                viewModel.clearDatauser()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            dialog.setNegativeButton("Tidak") { listener, _ ->
                listener.dismiss()
            }
            dialog.show()
        }
    }
}