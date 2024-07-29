package com.example.assignment2
import android.view.Menu
import android.view.MenuItem
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Spinner setup
        val options = arrayOf("Select an option", "Option A", "Option B", "Option C")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerOptions.adapter = adapter

        binding.spinnerOptions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position > 0) {
                    binding.tvMessage.text = "Selected: ${options[position]}"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Radio buttons setup
        binding.radioGroupOptions.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioOption1 -> binding.tvMessage.text = "Radio Option 1 selected"
                R.id.radioOption2 -> binding.tvMessage.text = "Radio Option 2 selected"
            }
        }

        // Button setup
        binding.btnShowMessage.setOnClickListener {
            val selectedOption = when (binding.radioGroupOptions.checkedRadioButtonId) {
                R.id.radioOption1 -> "Radio Option 1"
                R.id.radioOption2 -> "Radio Option 2"
                else -> "No Radio Option selected"
            }
            val spinnerOption = binding.spinnerOptions.selectedItem.toString()
            binding.tvMessage.text = "Button clicked with $selectedOption and Spinner: $spinnerOption"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                showSettingsFragment()
                true
            }
            R.id.action_about -> {
                showAboutDialogFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSettingsFragment() {
        val fragment = SettingsFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showAboutDialogFragment() {
        val dialogFragment = AboutDialogFragment()
        dialogFragment.show(supportFragmentManager, "AboutDialogFragment")
    }
}
