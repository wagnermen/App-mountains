package com.example.touristplaces.maps

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.touristplaces.R
import com.example.touristplaces.databinding.FragmentMapsBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mapFragment: SupportMapFragment
    private lateinit var mMap: GoogleMap
    private lateinit var coordinates: Pair<Double, Double>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coordinates = arguments?.getSerializable("coordinates") as Pair<Double, Double>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val googlePlayServicesAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(requireContext())
        if (googlePlayServicesAvailable == ConnectionResult.SUCCESS) {
            mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync { googleMap ->
                try {
                    onMapReady(googleMap)
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }else {
            GoogleApiAvailability.getInstance().getErrorDialog(requireActivity(), googlePlayServicesAvailable, 0)?.show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mountainLocation()
    }

    private fun mountainLocation() {
        mMap.clear()
        val userLatLng = LatLng(coordinates.first, coordinates.second)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 12f))
        val markerOptions = MarkerOptions().position(userLatLng)
        mMap.addMarker(markerOptions.position(userLatLng).title("Ubicación Montaña"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng,12f))
    }

}