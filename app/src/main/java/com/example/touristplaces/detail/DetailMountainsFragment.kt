package com.example.touristplaces.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.domain.model.DetailMountainModel
import com.example.touristplaces.BaseFragment
import com.example.touristplaces.R
import com.example.touristplaces.databinding.FragmentDetailMountainsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMountainsFragment : BaseFragment() {
    private var _binding: FragmentDetailMountainsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailMountainViewModel by viewModels()
    private var coordinates: Pair<Double, Double>? = null

    private lateinit var idMountain: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idMountain = arguments?.getSerializable("idMountain") as String
        viewModel.getDetailMountain(idMountain)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMountainsBinding.inflate(inflater, container, false)
        binding.btnLocation.setOnClickListener {
            val bundle = Bundle().apply { putSerializable("coordinates", coordinates) }
            findNavController().navigate(R.id.action_detailMountainsFragment_to_mapsFragment, bundle)
        }
        observer()
        return binding.root
    }

    private fun observer(){
        viewModel.isViewLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) dialogProgress?.show() else dialogProgress?.dismiss()
        }
        viewModel.detailMountain.observe(viewLifecycleOwner) {
            if (it != null) {
                coordinates = it.altitude
                setView(it)
            }else {
                Toast.makeText(
                    requireContext(),
                    "No hay detalle de la montaña para mostrar",
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

    private fun setView(detailMountain: DetailMountainModel){
        binding.txtTitle.text = detailMountain.name
        binding.txtCountry.text = detailMountain.location
        binding.textViewDescription.text = detailMountain.description
        binding.imageViewBackground.load("${detailMountain.imageMountain}")
        binding.imageViewCountry.load("${detailMountain.imageCountry}")

        val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.image_anim)
        binding.imageViewBackground.startAnimation(fadeInAnimation)
    }

}