package com.example.nca_final.ui.fragments

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.example.nca_final.DuckListApplication
import com.example.nca_final.databinding.FragmentDuckCreationFormBinding
import com.example.nca_final.model.entities.Duck
import com.example.nca_final.model.viewmodels.DucksViewModel
import com.example.nca_final.model.viewmodels.DucksViewModelFactory
import com.example.nca_final.utils.DuckRarity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class DuckCreationFormFragment : Fragment() {
    private var _binding: FragmentDuckCreationFormBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DucksViewModel by activityViewModels {
        DucksViewModelFactory(
            (activity?.application as DuckListApplication).database.DuckDAO()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDuckCreationFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spinnerRarity.adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, DuckRarity.values())

        binding.buttonCreateDuck.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val value = binding.editTextSellValue.text.toString().toDoubleOrNull()
            val rarity = binding.spinnerRarity.selectedItemPosition

            if (value == null) {
                Toast.makeText(context, "Incorrect value format!", Toast.LENGTH_SHORT).show()
            } else if (name == "") {
                Toast.makeText(context, "Name is empty!", Toast.LENGTH_SHORT).show()
            } else {
                val duck = Duck(null, name, value, rarity)
                lifecycle.coroutineScope.launch {
                    viewModel.insert(duck)
                    MainScope().launch { findNavController().navigateUp() }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}