package com.example.loweschallenge.ui.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.loweschallenge.R
import com.example.loweschallenge.databinding.FragmentCityBinding
import com.example.loweschallenge.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
class CityFragment : Fragment() {

    private var _binding: FragmentCityBinding? =null
    private val binding: FragmentCityBinding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    private val textWatcher = object: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.btnSubmit.isEnabled = true
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding.let { binding ->
            binding.etCityInput.addTextChangedListener(textWatcher)

            binding.btnSubmit.setOnClickListener {
                viewModel.city = binding.etCityInput.text.toString()
                submit()
                view.findNavController().navigate(R.id.action_cityRequest_to_forecastFragment)
            }
        }

    }


    private fun submit() {
        viewModel.getForecast()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}