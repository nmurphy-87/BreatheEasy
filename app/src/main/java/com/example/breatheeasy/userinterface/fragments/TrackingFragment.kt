package com.example.breatheeasy.userinterface.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.breatheeasy.R
import com.example.breatheeasy.additional.ContantValues.ACTION_PAUSE
import com.example.breatheeasy.additional.ContantValues.ACTION_START_RESUME
import com.example.breatheeasy.additional.ContantValues.ACTION_STOP
import com.example.breatheeasy.additional.ContantValues.MAP_ZOOM
import com.example.breatheeasy.additional.ContantValues.POLYLINE_COLOR
import com.example.breatheeasy.additional.ContantValues.POLYLINE_WIDTH
import com.example.breatheeasy.additional.TrackingUtility
import com.example.breatheeasy.data.databases.run.Run
import com.example.breatheeasy.services.Polyline
import com.example.breatheeasy.services.TrackingService
import com.example.breatheeasy.userinterface.viewmodels.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tracking.*
import java.util.*
import kotlin.math.round

@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.fragment_tracking) {

    private val viewModel: MainViewModel by viewModels()

    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()

    private var map: GoogleMap? = null

    private var currentTimeInMilliseconds = 0L

    private var menu: Menu? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.tracking_menu, menu)
        this.menu = menu
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        if(currentTimeInMilliseconds > 0L) {
            this.menu?.getItem(0)?.isVisible = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.miCancel -> {
                showCancelRunDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showCancelRunDialog() {
        val dialog = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
            .setTitle("Cancel Run")
            .setMessage("Are you sure you want to cancel")
            .setIcon(R.drawable.ic_close)
            .setPositiveButton("Yes") { _, _ ->
                stopRun()
            }
            .setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .create()
        dialog.show()
    }

    private fun stopRun(){
        sendCommandToService(ACTION_STOP)
        findNavController().navigate(R.id.action_trackingFragment_to_runFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)
        btnToggleRun.setOnClickListener {
            toggleRun()
        }

        btnFinishRun.setOnClickListener {
            panOutForBitmap()
            endRunAndSave()
        }

        mapView.getMapAsync {
            map = it
            addAllPolylines()
        }

        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        TrackingService.isTracking.observe(viewLifecycleOwner, Observer {
            updateTracking(it)
        })

        TrackingService.pathPoints.observe(viewLifecycleOwner, Observer {
            pathPoints = it
            addNewestPolyline()
            focusScreenToUserLocation()
        })

        TrackingService.timeRunInMilliSecs.observe(viewLifecycleOwner, Observer {
            currentTimeInMilliseconds = it
            val formattedTime = TrackingUtility.getFormattedStopwatchTime(currentTimeInMilliseconds, true)
            tvTimer.text = formattedTime
        })
    }

    private fun toggleRun() {
        if(isTracking) {
            menu?.getItem(0)?.isVisible = true
            sendCommandToService(ACTION_PAUSE)
        } else {
            sendCommandToService(ACTION_START_RESUME)
        }
    }

    private fun updateTracking(isTracking : Boolean) {
        this.isTracking = isTracking
        if(!isTracking) {
            btnToggleRun.text = "Start"
            btnFinishRun.visibility = View.VISIBLE
        } else {
            btnToggleRun.text = "Stop"
            menu?.getItem(0)?.isVisible = true
            btnFinishRun.visibility = View.GONE
        }
    }

    private fun focusScreenToUserLocation() {
        if(pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()) {
            map?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    pathPoints.last().last(),
                    MAP_ZOOM
                )
            )
        }
    }


    private fun addAllPolylines() {
        for (polyline in pathPoints) {
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .addAll(polyline)
            map?.addPolyline(polylineOptions)
        }
    }

    private fun addNewestPolyline() {
        if(pathPoints.isNotEmpty() && pathPoints.last().size > 1) {
            val penultimatePoint = pathPoints.last()[pathPoints.last().size -2]
            val lastPoint = pathPoints.last().last()
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .add(penultimatePoint)
                .add(lastPoint)
            map?.addPolyline(polylineOptions)
        }
    }

    private fun panOutForBitmap() {
        val bounds = LatLngBounds.Builder()
        for (polyline in pathPoints) {
            for(pos in polyline) {
                bounds.include(pos)
            }
        }
        map?.moveCamera(
                CameraUpdateFactory.newLatLngBounds(
                        bounds.build(),
                        mapView.width,
                        mapView.height,
                        (mapView.height * 0.05f).toInt()
                )
        )
    }

    private fun endRunAndSave() {
        map?.snapshot { bmp ->

            var distanceInMeters = 0
            for (polyline in pathPoints) {
                distanceInMeters += TrackingUtility.calculatePolylineLength(polyline).toInt()
            }

            val averageSpeed = round((distanceInMeters / 1000f) / (currentTimeInMilliseconds / 1000f / 60 / 60)) / 10f
            val dateTimeStamp = Calendar.getInstance().timeInMillis
            val calories = ((distanceInMeters / 1000f) * 80).toInt()
            val run = Run(bmp, dateTimeStamp, averageSpeed, distanceInMeters, currentTimeInMilliseconds, calories)
            viewModel.insertRun(run)
            Snackbar.make(
                    requireActivity().findViewById(R.id.mainActivityRootView),
                    "Run Saved",
                    Snackbar.LENGTH_LONG
            ).show()
            stopRun()
        }
    }

    private fun sendCommandToService(action : String) =
        Intent(requireContext(), TrackingService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

}