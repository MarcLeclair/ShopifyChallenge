package com.shopifychallenge.cardgame

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.shopifychallenge.cardgame.databinding.FragmentStartGameBinding
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatButton
import com.afollestad.materialdialogs.customview.getCustomView


class StartGameFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentStartGameBinding>(inflater,
            R.layout.fragment_start_game,container,false)



        binding.playButton.setOnClickListener { view: View ->


            val adapter = ArrayAdapter(
                context,
                R.layout.drop_down_menu,
                resources.getStringArray(R.array.columns)
            )

            val adapterRows = ArrayAdapter(
                context,
                R.layout.drop_down_menu,
                resources.getStringArray(R.array.rows)
            )

            val adapterMatch = ArrayAdapter(
                context,
                R.layout.drop_down_menu,
                resources.getStringArray(R.array.pair)
            )

            val dialog = MaterialDialog(context!!).
                customView(R.layout.settings_dialogue, scrollable = true)


            val editTextFilledExposedDropdown : AutoCompleteTextView = dialog.getCustomView().findViewById(R.id.filled_exposed_dropdown)
            val editTextFilledExposedDropdownRow : AutoCompleteTextView = dialog.getCustomView().findViewById(R.id.filled_exposed_dropdown_rows)
            val numberofMatchDropDown : AutoCompleteTextView = dialog.getCustomView().findViewById(R.id.number_of_match)

            editTextFilledExposedDropdown.setAdapter(adapter)
            editTextFilledExposedDropdownRow.setAdapter(adapterRows)
            numberofMatchDropDown.setAdapter(adapterMatch)
            val playButton = dialog.getCustomView().findViewById<AppCompatButton>(R.id.start_game)

            val currView = this.view
            playButton.setOnClickListener { view : View ->
                val numOfColumns = dialog.getCustomView().findViewById<AutoCompleteTextView>(R.id.filled_exposed_dropdown).text.toString()
                val numOfRows = dialog.getCustomView().findViewById<AutoCompleteTextView>(R.id.filled_exposed_dropdown).text.toString()
                val numOfPairs = dialog.getCustomView().findViewById<AutoCompleteTextView>(R.id.filled_exposed_dropdown).text.toString()


                currView!!.findNavController()
                    .navigate(StartGameFragmentDirections.actionStartGameFragmentToOverviewFragment(numOfRows.toInt(), numOfColumns.toInt(), numOfPairs.toInt()))
                dialog.dismiss()
            }
            dialog.show()

        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.options_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!,view!!.findNavController())
                ||super.onOptionsItemSelected(item)
    }
}
