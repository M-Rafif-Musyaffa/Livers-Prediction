package com.example.mymechinelearning.ui.simulasi

import android.content.res.AssetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.example.mymechinelearning.R
import com.google.android.material.snackbar.Snackbar
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class SimulasiActivity : AppCompatActivity() {

    private lateinit var interpreter: Interpreter
    private val mModelPath = "liver.tflite"
    private lateinit var resultText: TextView
    private lateinit var edtAge: EditText
    private lateinit var edtBMI: EditText
    private lateinit var edtAlcoholConsumption: EditText
    private lateinit var radioGroupSmoking: RadioGroup
    private lateinit var radioGroupGeneticRisk: RadioGroup
    private lateinit var edtPhysicalActivity: EditText
    private lateinit var radioGroupDiabetes: RadioGroup
    private lateinit var radioGroupHypertension: RadioGroup
    private lateinit var edtLiverFunctionTest: EditText
    private lateinit var checkButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_simulasi)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // tombol help
        val helpAgeButton: ImageButton = findViewById(R.id.helpAge)
        val helpBmiButton: ImageButton = findViewById(R.id.helpBmi)
        val helpAlcoholButton: ImageButton = findViewById(R.id.helpAlcoholConsumption)
        val helpPhysicalActivityButton: ImageButton = findViewById(R.id.helpPhysicalActivity)
        val helpLiverFunctionButton: ImageButton = findViewById(R.id.helpLiverFunctionTest)

        // Setup OnClickListener untuk masing-masing ImageButton
        helpAgeButton.setOnClickListener {
            val message = "Masukkan usia Anda dalam bentuk angka. Contoh: 20"
            showSnackbar(helpAgeButton, message)
        }
        helpBmiButton.setOnClickListener {
            val message = "Masukkan BMI Anda dalam bentuk angka desimal. Range: 15 to 40, Contoh: 30.732470"
            showSnackbar(helpBmiButton, message)
        }

        helpAlcoholButton.setOnClickListener {
            val message = "Masukkan konsumsi alkohol Anda dalam bentuk angka desimal. Range: 0 to 20 units per minggu, Contoh: 2.201266"
            showSnackbar(helpAlcoholButton, message)
        }

        helpPhysicalActivityButton.setOnClickListener {
            val message = "Masukkan tingkat aktivitas fisik Anda dalam bentuk angka desimal. Range: 0 to 10 hours per minggu, Contoh: 1.670557"
            showSnackbar(helpPhysicalActivityButton, message)
        }

        helpLiverFunctionButton.setOnClickListener {
            val message = "Masukkan hasil tes fungsi hati Anda dalam bentuk angka desimal. Range: 20 to 100, Contoh: 67.309822"
            showSnackbar(helpLiverFunctionButton, message)
        }

        //akhir tombol help



        resultText = findViewById(R.id.txtResult)
        edtAge = findViewById(R.id.editAge)
        edtBMI = findViewById(R.id.editBmi)
        edtAlcoholConsumption = findViewById(R.id.editAlcoholConsumption)
        radioGroupSmoking = findViewById(R.id.radioGroupSmoking)
        radioGroupGeneticRisk = findViewById(R.id.radioGroupGeneticRisk)
        edtPhysicalActivity = findViewById(R.id.editPhysicalActivity)
        radioGroupDiabetes = findViewById(R.id.radioGroupDiabetes)
        radioGroupHypertension = findViewById(R.id.radioGroupHypertension)
        edtLiverFunctionTest = findViewById(R.id.editLiverFunctionTest)
        checkButton = findViewById(R.id.btnCheck)

        checkButton.setOnClickListener {
            if (edtAge.text.isEmpty() || edtBMI.text.isEmpty() || edtAlcoholConsumption.text.isEmpty() ||
                radioGroupSmoking.checkedRadioButtonId == -1 || radioGroupGeneticRisk.checkedRadioButtonId == -1 ||
                edtPhysicalActivity.text.isEmpty() || radioGroupDiabetes.checkedRadioButtonId == -1 ||
                radioGroupHypertension.checkedRadioButtonId == -1 || edtLiverFunctionTest.text.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                val smoking = when (radioGroupSmoking.checkedRadioButtonId) {
                    R.id.radioSmokingYes -> 1.0f
                    R.id.radioSmokingNo -> 0.0f
                    else -> -1.0f // Error case
                }

                val geneticRisk = when (radioGroupGeneticRisk.checkedRadioButtonId) {
                    R.id.radioGeneticRiskLow -> 0.0f
                    R.id.radioGeneticRiskMedium -> 1.0f
                    R.id.radioGeneticRiskHigh -> 2.0f
                    else -> -1.0f // Error case
                }

                val diabetes = when (radioGroupDiabetes.checkedRadioButtonId) {
                    R.id.radioDiabetesYes -> 1.0f
                    R.id.radioDiabetesNo -> 0.0f
                    else -> -1.0f // Error case
                }

                val hypertension = when (radioGroupHypertension.checkedRadioButtonId) {
                    R.id.radioHypertensionYes -> 1.0f
                    R.id.radioHypertensionNo -> 0.0f
                    else -> -1.0f // Error case
                }

                val result = doInference(
                    edtAge.text.toString(),
                    edtBMI.text.toString(),
                    edtAlcoholConsumption.text.toString(),
                    smoking.toString(),
                    geneticRisk.toString(),
                    edtPhysicalActivity.text.toString(),
                    diabetes.toString(),
                    hypertension.toString(),
                    edtLiverFunctionTest.text.toString()
                )
                runOnUiThread {
                    if (result == 1) {
                        resultText.text = "Tidak Terkena liver"
                    } else {
                        resultText.text = "Terkena liver"
                    }
                }
            }
        }
        initInterpreter()
    }

    private fun initInterpreter() {
        val options = Interpreter.Options()
        options.setNumThreads(10)
        options.setUseNNAPI(true)
        interpreter = Interpreter(loadModelFile(assets, mModelPath), options)
    }

    private fun doInference(input1: String, input2: String, input3: String, input4: String, input5: String, input6: String, input7: String, input8: String, input9: String): Int {
        val inputVal = FloatArray(9)
        inputVal[0] = input1.toFloat()
        inputVal[1] = input2.toFloat()
        inputVal[2] = input3.toFloat()
        inputVal[3] = input4.toFloat()
        inputVal[4] = input5.toFloat()
        inputVal[5] = input6.toFloat()
        inputVal[6] = input7.toFloat()
        inputVal[7] = input8.toFloat()
        inputVal[8] = input9.toFloat()
        val output = Array(1) { FloatArray(3) }
        interpreter.run(inputVal, output)
        Log.e("result", (output[0].toList() + " ").toString())
        return output[0].indexOfFirst { it == output[0].maxOrNull() }
    }

    private fun loadModelFile(assetManager: AssetManager, modelPATH: String): MappedByteBuffer {
        val fileDescriptor = assetManager.openFd(modelPATH)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val starOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, starOffset, declaredLength)
    }

    fun resetInputs(view: View) {
        edtAge.text.clear()
        edtBMI.text.clear()
        edtAlcoholConsumption.text.clear()
        radioGroupSmoking.clearCheck()
        radioGroupGeneticRisk.clearCheck()
        edtPhysicalActivity.text.clear()
        radioGroupDiabetes.clearCheck()
        radioGroupHypertension.clearCheck()
        edtLiverFunctionTest.text.clear()
        resultText.text = ""
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }
}
