package com.example.nca_final.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.example.nca_final.DuckListApplication
import com.example.nca_final.R
import com.example.nca_final.databinding.FragmentDuckDetailsBinding
import com.example.nca_final.model.AppDatabase
import com.example.nca_final.model.entities.Duck
import com.example.nca_final.model.viewmodels.DucksViewModel
import com.example.nca_final.model.viewmodels.DucksViewModelFactory
import com.example.nca_final.utils.DuckRarity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class DuckDetailsFragment : Fragment() {

    private var _binding: FragmentDuckDetailsBinding? = null
    private val binding get() = _binding!!
    private val database: AppDatabase by lazy { AppDatabase.getDatabase(requireContext()) }

    private val viewModel: DucksViewModel by activityViewModels {
        DucksViewModelFactory(
            (activity?.application as DuckListApplication).database.DuckDAO()
        )
    }
    private lateinit var duck: Duck

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDuckDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            duck = bundle.getSerializable("duck") as Duck
            binding.textViewSeed.text = duck.seed.toString()

            binding.textViewUrl.setOnClickListener {
                val webpage: Uri = Uri.parse(duck.url)
                val intent = Intent(Intent.ACTION_VIEW, webpage)
                startActivity(intent)
            }

            binding.editTextName.setText(duck.name)
            binding.editTextSellValue.setText(duck.value.toString())
            binding.imageViewDuck.setImageResource(requireContext().resources.getIdentifier(duck.image, "drawable", requireContext().packageName))
            binding.spinnerRarity.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, DuckRarity.values())
            binding.spinnerRarity.setSelection(duck.rarity!!)

            binding.buttonCreateDuck.setOnClickListener {
                val name = binding.editTextName.text.toString()
                val value = binding.editTextSellValue.text.toString().toDoubleOrNull()
                val rarity = binding.spinnerRarity.selectedItemPosition

                if (value == null) {
                    Toast.makeText(context, "Incorrect value format!", Toast.LENGTH_SHORT).show()
                } else if (name == "") {
                    Toast.makeText(context, "Name is empty!", Toast.LENGTH_SHORT).show()
                } else {
                    duck.name = name
                    duck.value = value
                    duck.rarity = rarity

                    lifecycle.coroutineScope.launch {
                        lifecycle.coroutineScope.launch {
                            viewModel.updateDucks(duck)
                            MainScope().launch { findNavController().navigateUp() }
                        }
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_duck_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                if (this::duck.isInitialized) {
                    lifecycle.coroutineScope.launch {
                        viewModel.deleteDucks(duck)
                        MainScope().launch { findNavController().navigateUp() }
                    }
                }
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}