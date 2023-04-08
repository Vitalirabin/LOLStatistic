package com.example.lolstatistic.splash

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.lolstatistic.BaseFragment
import com.example.lolstatistic.R

class SplashFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.splash_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.postDelayed({ goToEnterFragment(view) }, 500)
    }

    private fun goToEnterFragment(view: View) {
        view.let {
            Navigation.findNavController(it)
                .navigate(SplashFragmentDirections.actionSplashFragmentToEnterFragment2())
        }
    }
}