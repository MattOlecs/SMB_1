package mateusz.oleksik.smb_projekt_1

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import mateusz.oleksik.smb_projekt_1.databinding.ActivitySettingsBinding

class OptionsActivity : AppCompatActivity() {

    private lateinit var _binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        _binding = binding

        binding.applyButton.setOnClickListener {
            try {
                val sharedPref = getSharedPreferences(Constants.PreferencesFileName, Context.MODE_PRIVATE)
                val editor = sharedPref.edit()

                //Save selected color
                val selectedButtonID = binding.radioGroup.checkedRadioButtonId
                val selectedButton = binding.root.findViewById<RadioButton>(selectedButtonID)
                val selectedColor = translateStringToColorEnum(selectedButton.text.toString())
                editor.putInt(Constants.ColorPreferenceID, selectedColor)

                //Save selected font size
                val fontSize = binding.fontTextNumber.text.toString().toInt()
                editor.putInt(Constants.FontSizePreferenceID, fontSize)

                editor.apply()
                Toast.makeText(
                    this,
                    "Settings applied",
                    Toast.LENGTH_LONG)
                    .show()
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "Saving settings failed! Try again.",
                    Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        initDefaultSettingsOnUI()
    }

    private fun initDefaultSettingsOnUI(){
        val sharedPref = getSharedPreferences(Constants.PreferencesFileName, Context.MODE_PRIVATE)
        val color = sharedPref.getInt(Constants.ColorPreferenceID, Color.BLUE)
        val font = sharedPref.getInt(Constants.FontSizePreferenceID, 14)

        val radioButtonID = translateColorEnumToRadioButtonID(color)
        val selectedButton = _binding.radioGroup[radioButtonID] as RadioButton
        selectedButton.isChecked = true
        _binding.fontTextNumber.setText(font.toString())
    }

    private fun translateStringToColorEnum(name: String): Int{
        return when(name){
            "Blue" -> Color.BLUE
            "Green" -> Color.GREEN
            "Gray" -> Color.GRAY
            "Black" -> Color.BLACK
            else -> Color.BLUE
        }
    }

    private fun translateColorEnumToRadioButtonID(enum: Int): Int{
        return when(enum){
            Color.BLUE -> 0
            Color.GREEN -> 1
            Color.GRAY -> 2
            Color.BLACK -> 3
            else -> 0
        }
    }
}