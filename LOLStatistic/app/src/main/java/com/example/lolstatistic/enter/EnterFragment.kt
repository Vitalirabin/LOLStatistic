package com.example.lolstatistic.enter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import com.example.lolstatistic.BaseFragment
import com.example.lolstatistic.MainActivity
import com.example.lolstatistic.R
import com.example.lolstatistic.databinding.FragmentEnterNameBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class EnterFragment : BaseFragment() {
    override fun getLayoutId(): Int = binding.root.id

    private val enterViewModel: EnterViewModel by viewModel()

    private var _binding: FragmentEnterNameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterNameBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.clickInfo = enterViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createSpinner()
        binding.enterButton.setOnClickListener {
            if (binding.nameOfAccount.editText?.text.toString() == "") {
                Toast.makeText(requireContext(), "Enter name!!!", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val action = EnterFragmentDirections.actionEnterFragmentToMatchListFragment()
            action.name = binding.nameOfAccount.editText?.text.toString()
            Navigation.findNavController(view).navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createSpinner() {
        val adapter = ArrayAdapter<String>(
            (requireActivity() as MainActivity),
            R.layout.server_spinner,
            Servers.values().map { it.server }
        )
        adapter.setDropDownViewResource(R.layout.server_spiner_dropdown)
        binding.spinner.adapter = adapter
        binding.spinner.prompt = "Выберите сервер"
        binding.spinner.setSelection(0)
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View?, selectedItemPosition: Int, selectedId: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

}