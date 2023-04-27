package com.example.nca_final.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nca_final.DuckListApplication
import com.example.nca_final.R
import com.example.nca_final.adapters.DuckListAdapter
import com.example.nca_final.databinding.FragmentDuckListBinding
import com.example.nca_final.model.viewmodels.DucksViewModel
import com.example.nca_final.model.viewmodels.DucksViewModelFactory

class DuckListFragment : Fragment() {
    private var _binding: FragmentDuckListBinding? = null
    private val binding get() = _binding!!
    private var searchCriteria = ""
    private val duckListAdapter = DuckListAdapter()

    private val viewModel: DucksViewModel by activityViewModels {
        DucksViewModelFactory(
            (activity?.application as DuckListApplication).database.DuckDAO()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDuckListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewDucks.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewDucks.adapter = duckListAdapter

        /*lifecycle.coroutineScope.launch {
            viewModel.getAllDucks().collect {
                duckListAdapter.submitList(it)
            }
        }*/
        viewModel.duckListLiveData.observe(viewLifecycleOwner, Observer {
            duckListAdapter.submitList(it)
        })
        viewModel.setSearchQuery("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_duck_list, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = MenuItemCompat.getActionView(searchItem) as SearchView

        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        viewModel.setSearchQuery(it)
                    }
                    return true
                }
            }
        )

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_create -> {
                findNavController().navigate(R.id.action_DuckListFragment_to_DuckCreationFormFragment)
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}