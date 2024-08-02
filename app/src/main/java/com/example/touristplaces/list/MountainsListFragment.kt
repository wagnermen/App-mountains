package com.example.touristplaces.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.model.MountainsListModel
import com.example.touristplaces.BaseFragment
import com.example.touristplaces.R
import com.example.touristplaces.databinding.FragmentMountainsListBinding
import com.example.touristplaces.list.adapter.MountainsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MountainsListFragment : BaseFragment() {
    private var _binding: FragmentMountainsListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MountainsListViewModel by viewModels()
    private lateinit var mountainsAdapter: MountainsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getMountains()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMountainsListBinding.inflate(inflater, container, false)
        observer()
        return binding.root
    }

    private fun observer(){
        viewModel.isViewLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) dialogProgress?.show() else dialogProgress?.dismiss()
        }
        viewModel.mountains.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) {
                setAdapter(it)
            }else {
                Toast.makeText(
                    requireContext(),
                    "No hay datos para mostrar",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setAdapter(lstItem: List<MountainsListModel>){
        mountainsAdapter = MountainsAdapter(lstItem){ mountain ->
            val bundle = Bundle().apply { putSerializable("idMountain", mountain.id) }
            findNavController().navigate(R.id.action_mountainsListFragment_to_detailMountainsFragment, bundle)
        }
        binding.rvMountains.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMountains.adapter = mountainsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}