package com.example.lolstatistic.match.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lolstatistic.BaseFragment
import com.example.lolstatistic.Constants.MATCH_ID_VALUE
import com.example.lolstatistic.R
import com.example.lolstatistic.databinding.FragmentMatchStatisticBinding
import android.util.Log
import com.example.lolstatistic.Constants.PUUID
import com.example.lolstatistic.databinding.FragmentMatchStatisticBindingImpl
import com.example.lolstatistic.match.MatchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MatchFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_match_statistic

    private val matchViewModel: MatchViewModel by viewModel()


    private lateinit var _binding: FragmentMatchStatisticBinding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val id =this.arguments?.getString(MATCH_ID_VALUE).toString()
        val puuid =this.arguments?.getString(PUUID).toString()
        Log.d("MatchFragment", id)
        matchViewModel.getParticipant(puuid,id)
        _binding = FragmentMatchStatisticBindingImpl.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.matchViewModel = matchViewModel
        return binding.root
    }
}