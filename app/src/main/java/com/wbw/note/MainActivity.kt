package com.wbw.note

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.wbw.note.databinding.ActivityMainBinding
import com.getbase.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)


        val addMemoButton =
            findViewById<com.getbase.floatingactionbutton.FloatingActionButton>(R.id.add_memo)
        addMemoButton.setOnClickListener {
            showAddMemoDialog(0)
//            Log.d("add_memo", "filename: $filename")
            //携带filename跳转到编辑页面
//            val intent = Intent(this, EditActivity::class.java)
//            intent.putExtra("filename", filename)
//            intent.putExtra("mode", 0)
//            startActivity(intent)

            Log.d("add_memo", "onCreate: addMemoButton")
        }
        val importMemoButton =
            findViewById<com.getbase.floatingactionbutton.FloatingActionButton>(R.id.import_memo)
        importMemoButton.setOnClickListener {
            showAddMemoDialog(1)
            Log.d("import_memo", "onCreate: importMemoButton")
        }
        val downloadMemoButton =
            findViewById<com.getbase.floatingactionbutton.FloatingActionButton>(R.id.download_memo)
        downloadMemoButton.setOnClickListener {
            Log.d("download_memo", "onCreate: downloadMemoButton")
        }
    }

    private fun showAddMemoDialog(select: Int = 0) {

        //0:新建备忘录
        if (select == 0) {
            val filename: String = ""
            val builder = MaterialAlertDialogBuilder(
                this,
                com.google.android.material.R.style.MaterialAlertDialog_Material3
            )
            val input = TextInputEditText(this)
            input.hint = "Enter filename"
            builder.setView(input)
            builder.setTitle("Add Memo")
            builder.setPositiveButton("OK") { dialog, _ ->
                val filename = input.text.toString()
                Log.d("add_memo", "Filename: $filename")
                Log.d("add_memo", "filename: $filename")
                //携带filename跳转到编辑页面
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra("fileName", filename)
                intent.putExtra("mode", 0)
                startActivity(intent)
                dialog.dismiss()
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()

        } else if (select == 1) {
            //1:导入备忘录
            val builder = MaterialAlertDialogBuilder(
                this,
                com.google.android.material.R.style.MaterialAlertDialog_Material3
            )
            val input = TextInputEditText(this)
            input.hint = "Enter key to import"
            builder.setView(input)
            builder.setTitle("Import Memo")
            builder.setPositiveButton("OK") { dialog, _ ->
                val filename = input.text.toString()
                Log.d("import_memo", "Filename: $filename")
                dialog.dismiss()
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        } else if (select == 2) {
            //2:下载备忘录
            val builder = MaterialAlertDialogBuilder(
                this,
                com.google.android.material.R.style.MaterialAlertDialog_Material3
            )
            val input = TextInputEditText(this)
            input.hint = "Enter url to download"
            builder.setView(input)
            builder.setTitle("Download Memo")
            builder.setPositiveButton("OK") { dialog, _ ->
                val filename = input.text.toString()


                Log.d("download_memo", "Filename: $filename")
                dialog.dismiss()
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
    }
}