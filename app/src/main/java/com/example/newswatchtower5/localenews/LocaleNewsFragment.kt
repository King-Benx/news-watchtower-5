package com.example.newswatchtower5.localenews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newswatchtower5.R
import com.example.newswatchtower5.shared.loadData

class LocaleNewsFragment : Fragment(), AdapterView.OnItemSelectedListener {
    val recyclerView by lazy {
        view?.findViewById<RecyclerView>(R.id.recycler_view)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) = Unit

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        loadData(
            this.context!!,
            recyclerView!!,
            parent?.getItemAtPosition(position).toString(),
            null
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_locale_news, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        ArrayAdapter.createFromResource(
            view.context,
            R.array.continents_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this
        return view
    }
}
