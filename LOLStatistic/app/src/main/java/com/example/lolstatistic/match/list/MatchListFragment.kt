package com.example.lolstatistic.match.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lolstatistic.BaseFragment
import com.example.lolstatistic.R
import com.example.lolstatistic.databinding.FragmentMatchListBinding
import com.example.lolstatistic.match.MatchViewModel
import com.example.lolstatistic.match.details.MatchModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MatchListFragment : BaseFragment() {

    private val matchViewModel: MatchViewModel by viewModel()
    private var adapter: MatchItemAdapter? = null
    override fun getLayoutId(): Int = R.layout.fragment_match_list
    private lateinit var name: String

    private lateinit var binding: FragmentMatchListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMatchListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        name = MatchListFragmentArgs.fromBundle(requireArguments()).name
        matchViewModel.getPuuidByAccount(name)
        matchViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBar.visibility = ProgressBar.VISIBLE
            } else {
                binding.progressBar.visibility = ProgressBar.INVISIBLE
            }
        })
        if (adapter == null) {
            adapter = MatchItemAdapter(
                object : ItemOnClickListener {
                    override fun onClick(match: MatchModel?) {
                        click(match)
                    }
                }
            )
        }
        matchViewModel.liveDataListOfMatch.observe(viewLifecycleOwner, Observer {
            adapter?.setData(matchViewModel.puuid,it.toList())
            addOnScrollListener(name)
        })
        binding.matchList.layoutManager = GridLayoutManager(context,1).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.matchList.adapter = adapter
        if (matchViewModel.liveDataListOfMatch.value == null)
            matchViewModel.updateList(name)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

    private fun addOnScrollListener(name: String) {
        binding.matchList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                if (totalItemCount < (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() + 2) {
                    matchViewModel.updateList(name)
                }
            }
        })
    }

    fun click(match: MatchModel?) {
        Log.d("MatchListFragment", match?.metadata?.matchId.toString())
        val action = MatchListFragmentDirections.actionMatchListFragmentToMatchFragment()
        action.id = match?.metadata?.matchId.toString()
        action.puuid = matchViewModel.getPuuidByAccount(name)
        view?.let { Navigation.findNavController(it).navigate(action) }
    }
}
