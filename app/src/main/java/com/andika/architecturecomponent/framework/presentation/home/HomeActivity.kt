package com.andika.architecturecomponent.framework.presentation.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.andika.architecturecomponent.R
import com.andika.architecturecomponent.databinding.ActivityMainBinding
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()

    }

    private fun initView() {
        binding.navView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.favouriteFragment -> {
                    installChatModule()
                }
                R.id.tvFragment -> {
                    binding.navHostFragment.findNavController().navigate(R.id.tvFragment)
                }
                R.id.movieFragment -> {
                    binding.navHostFragment.findNavController().navigate(R.id.movieFragment)
                }

            }
            true
        }
    }

    private fun installChatModule() {
        try {
            val splitInstallManager = SplitInstallManagerFactory.create(this)
            val moduleFav = "favourite"
            if (splitInstallManager.installedModules.contains(moduleFav)) {
                binding.navHostFragment.findNavController().navigate(R.id.favouriteFragment)
            } else {
                val request = SplitInstallRequest.newBuilder()
                    .addModule(moduleFav)
                    .build()
                splitInstallManager.startInstall(request)
            }
        }catch (e:Exception){
            Toast.makeText(applicationContext,e.toString(),Toast.LENGTH_LONG).show()
        }
    }

}