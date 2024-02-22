package raf.edu.rs.nutritiontracker.presentation.activities


import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

import raf.edu.rs.nutritiontracker.R
import raf.edu.rs.nutritiontracker.db.converters.MealEntityMealConverter
import raf.edu.rs.nutritiontracker.models.domain.Meal
import raf.edu.rs.nutritiontracker.presentation.contracts.DBContract
import raf.edu.rs.nutritiontracker.presentation.contracts.MainContract
import raf.edu.rs.nutritiontracker.presentation.viewmodel.ViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class SaveMealFragment(var meal: Meal?) : Fragment(R.layout.meal_save_fragment) {

    private val postsViewModel: MainContract.ViewModel by viewModel<ViewModel>()
    private val mealDbViewModel: DBContract.ViewModel by viewModel<ViewModel>()
    private lateinit var takePictureLauncher: ActivityResultLauncher<Intent>

    private val cameraPermissionRequestCode = 123
    private var currentPhotoPath: String? = null

    private var datePickerDialog: DatePickerDialog? = null
    private var dateButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dateButton = view.findViewById(R.id.datePickerUpdateButton)
        dateButton!!.text = getTodayDate()
        dateButton!!.setOnClickListener{
            datePickerDialog?.show()

        }
        initDatePicker()
        val tvMealName=view.findViewById<TextView>(R.id.tvMealNameUpdate)
        val ivMealSave=view.findViewById<ImageView>(R.id.ivMealUpdate)
        tvMealName.text= meal?.strMeal
        println(meal?.strMeal)
        println(meal?.strImageSource)
        println(meal?.strMealThumb)
        Glide.with(view.context).load( if (meal?.strImageSource == null) meal?.strMealThumb else meal!!.strImageSource).into(ivMealSave);

        val spinner: Spinner = view.findViewById(R.id.spinnerUpdate)

        ArrayAdapter.createFromResource(
            view.context,
            R.array.options_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
           spinner.adapter = adapter
        }
        val button:Button=view.findViewById(R.id.updateMealButton1)
        button.setOnClickListener{
         var  mealEntity=  MealEntityMealConverter.fromMeal(meal)!!
            var date=Date();
            mealEntity.dateModified=date.time;
            mealEntity.datePlanned= dateButton!!.text as String?;
            mealEntity.forMeal= spinner.selectedItem as String?
             mealDbViewModel.insertMeal(mealEntity,object : DBContract.InsertMealCallback {
                 override fun onMealInserted() {
                     Toast.makeText(view.context, "Meal inserted successfully!", Toast.LENGTH_SHORT)
                         .show()
                     activity?.supportFragmentManager?.popBackStack()
                 }

                 override fun onInsertError(error: Throwable) {
                     Toast.makeText(view.context, "Failed to insert meal", Toast.LENGTH_SHORT)
                         .show()
                 }
             })
        }





        takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                // The image capture was successful, you can access the image data from the Intent or save it
                val imageBitmap = result.data?.extras?.get("data") as? Bitmap
                if (imageBitmap != null) {
                    // Do something with the captured image

                    println("DOLAZI OVDE WTF")
                    println(currentPhotoPath)
                    meal?.strImageSource =if (currentPhotoPath.isNullOrBlank()) meal?.strImageSource else currentPhotoPath;
                    meal?.strMealThumb =if (currentPhotoPath.isNullOrBlank()) meal?.strImageSource else currentPhotoPath;
                    ivMealSave.setImageBitmap(imageBitmap)
                   writeBitmapToFile(imageBitmap,File(currentPhotoPath));



                }
            } else {

                Toast.makeText(view.context, "Failed to insert meal", Toast.LENGTH_SHORT)
            }
        }
            ivMealSave.setOnClickListener{

                val cameraPermission = Manifest.permission.CAMERA
                if (ContextCompat.checkSelfPermission(view.context, cameraPermission) == PackageManager.PERMISSION_GRANTED) {
                    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                    val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                    val storageDir = view.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    val imageFile = File.createTempFile("IMG_$timestamp", ".jpg", storageDir)


             //      var uri= FileProvider.getUriForFile(view.context, "raf.edu.rs.nutritiontracker.fileProvider", imageFile)
                    currentPhotoPath=imageFile.absolutePath;
              //      takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri)
                    takePictureIntent.putExtra(MediaStore.ACTION_IMAGE_CAPTURE,"data")
                    takePictureLauncher.launch(takePictureIntent)

                } else {
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(cameraPermission), cameraPermissionRequestCode)
                }




        }



    }



    private fun getTodayDate(): String? {
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        var month: Int = cal.get(Calendar.MONTH)
        month += 1
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)
        return "$year\\$month\\$day";
    }

    private fun initDatePicker() {
        val dateSetListener =
            OnDateSetListener { _, year, month, day ->
                var month = month
                month += 1
                val date = makeDateString(day, month, year)
                dateButton!!.text = date
            }
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)
      //  val style: Int = AlertDialog.THEME_DEVICE_DEFAULT_DARK
        datePickerDialog = DatePickerDialog(requireView().context, 0, dateSetListener, year, month, day)
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private fun makeDateString(day: Int, month: Int, year: Int): String? {
        return getMonthFormat(month) + " " + day + " " + year
    }

    private fun getMonthFormat(month: Int): String {
        if (month == 1) return "JAN"
        if (month == 2) return "FEB"
        if (month == 3) return "MAR"
        if (month == 4) return "APR"
        if (month == 5) return "MAY"
        if (month == 6) return "JUN"
        if (month == 7) return "JUL"
        if (month == 8) return "AUG"
        if (month == 9) return "SEP"
        if (month == 10) return "OCT"
        if (month == 11) return "NOV"
        return if (month == 12) "DEC" else "JAN"

        //default should never happen
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == cameraPermissionRequestCode && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Camera permission has been granted, start the camera intent here
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val photoUri:String="";
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val storageDir = view?.context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val imageFile = File.createTempFile("IMG_$timestamp", ".jpg", storageDir)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                view?.let { FileProvider.getUriForFile(it.context, "raf.edu.rs.nutritiontracker.fileProvider", imageFile) }
            } else {
                Uri.fromFile(imageFile)
            }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            takePictureLauncher.launch(takePictureIntent)
            println(photoUri)
        } else {
            // Camera permission has been denied
            Toast.makeText(requireView().context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }


    private fun writeBitmapToFile(bitmap: Bitmap, file: File) {
        try {
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}
