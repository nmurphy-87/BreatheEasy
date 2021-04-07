package com.example.breatheeasy.userinterface.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.breatheeasy.R
import com.example.breatheeasy.data.APIData.WeatherAndAirQualityAPIService
import com.example.breatheeasy.userinterface.base.ScopedFragment
import com.example.breatheeasy.userinterface.viewmodels.ConditionsViewModel
import com.example.breatheeasy.userinterface.viewmodels.ConditionsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_conditions.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

@AndroidEntryPoint
class ConditionsFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: ConditionsViewModelFactory by instance()


    private lateinit var viewModel: ConditionsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_conditions, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ConditionsViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = GlobalScope.launch(Dispatchers.Main){
        val getConditions = viewModel.conditions.await()
        getConditions.observe(viewLifecycleOwner, Observer {
            if(it == null){
                Log.d("CONDITIONS", "Conditions is null")
                return@Observer
            }

            tvConditions.text = it.toString()

            val conditionsText = it.toString()
            Log.d("CONDITIONS", conditionsText)
        })
    }

}