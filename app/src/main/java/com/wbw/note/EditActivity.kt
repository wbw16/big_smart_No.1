package com.wbw.note

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.wbw.note.encrypt.RC4Encryption
import com.wbw.note.util.FileUtil
import com.wbw.note.util.ToolUtil
import io.noties.markwon.Markwon
import io.noties.markwon.image.ImagesPlugin
import java.nio.charset.StandardCharsets


class EditActivity : AppCompatActivity() {
    private lateinit var key: String
    private var mode: Int = -1
    private lateinit var fileName: String
    private lateinit var input: String
    private lateinit var markwon: Markwon
    private lateinit var editText: EditText
    private lateinit var mdShowView: TextView
    private val markwon1: Markwon
        get() {
            val markwon = Markwon.builder(this)
                .usePlugin(ImagesPlugin.create())
                .build()
            return markwon
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        key = intent.getStringExtra("encryptionKey") ?: ""
        mode = intent.getIntExtra("mode", -1)
        fileName = intent.getStringExtra("fileName").toString()
        input = ""

        if (mode == 1) {
            // 修改模式
            input = FileUtil.readFile(fileName)
        } else if (mode == 0) {
            // 新建模式
            FileUtil.creatFile(fileName)
            Log.d("onCreat", "create file success")
            input = "" // 初始化为一个空字符串
        } else if (mode == 2) {
            // 解密模式
            input = FileUtil.readFile(fileName)
            key?.let {
                input = try {
                    String(RC4Encryption.decryptData(it.toByteArray(), input.toByteArray()))
                } catch (e: Exception) {
                    "解密失败"
                }
            }
        }


        markwon = Markwon.builder(this)
            .usePlugin(ImagesPlugin.create())
            .build()
        editText = findViewById<EditText>(R.id.editText)
        mdShowView = findViewById<TextView>(R.id.markdownView)

        editText.setText(input)
        markwon.setMarkdown(mdShowView, input)

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Not needed here, as Markdown rendering is done afterTextChanged
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Not needed here, as Markdown rendering is done afterTextChanged
            }

            override fun afterTextChanged(s: Editable) {
                input = s.toString()
                markwon.setMarkdown(mdShowView, input)
                FileUtil.saveFile(fileName, input)
            }
        })
        //加密按钮
        val encryptButton =
            findViewById<com.getbase.floatingactionbutton.FloatingActionButton>(R.id.encrypt_button)
        encryptButton.setOnClickListener {
            showEncryptedDialog()
            Log.d("encrypt_button", "onCreate: encryptButton")
        }
        //解密查看按钮
        val decryptButton =
            findViewById<com.getbase.floatingactionbutton.FloatingActionButton>(R.id.decrypt_button)
        decryptButton.setOnClickListener {
            showDecryptDialog()
            Log.d("decrypt_button", "onCreate: decryptButton")
        }
        //解锁按钮
        val unlockButton =
            findViewById<com.getbase.floatingactionbutton.FloatingActionButton>(R.id.unlock_button)
        unlockButton.setOnClickListener {
            showUnlockDialog()
            Log.d("unlock_button", "onCreate: unlockButton")
        }
    }
    private fun showUnlockDialog() {
        val builder = MaterialAlertDialogBuilder(
            this,
            com.google.android.material.R.style.MaterialAlertDialog_Material3
        )
        val dialogInput = TextInputEditText(this)
        dialogInput.hint = "Enter key to unlock file"
        builder.setView(dialogInput)
        builder.setTitle("Decrypt")
        builder.setPositiveButton("OK") { dialog, _ ->
            val key = dialogInput.text.toString() // 使用text属性获取用户输入的文本
            if (key.isEmpty()) {
                Toast.makeText(this, "Key is empty", Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            }

            val tempData: ByteArray? = FileUtil.readFileAsByteArray(fileName)
            if (tempData != null) {
                // 解密数据
                val decryptedData = RC4Encryption.decryptData(key.toByteArray(), tempData)

                // 将解密后的数据保存到文件
                FileUtil.saveFileAsByteArray(fileName, decryptedData)
//                editText.setText(String(decryptedData, StandardCharsets.UTF_8))
                // 显示解密后的数据
                val decryptedString = String(decryptedData, Charsets.UTF_8) // 使用UTF-8编码将byte数组转换为字符串
                markwon.setMarkdown(mdShowView, decryptedString)
                editText.setText(decryptedString)
                Log.d("decrypt", "Key: $key")
            } else {
                Toast.makeText(this, "Failed to read file", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun showEncryptedDialog() {
        val builder = MaterialAlertDialogBuilder(
            this,
            com.google.android.material.R.style.MaterialAlertDialog_Material3
        )
        val dialogInput = TextInputEditText(this)
        dialogInput.hint = "Enter key to encrypt"
        builder.setView(dialogInput)
        builder.setTitle("Encrypt")
        builder.setPositiveButton("OK") { dialog, _ ->
            val key = dialogInput.text.toString()
            if (key.isEmpty()) {
                Toast.makeText(this, "Key is empty", Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            }

            val tempData:String=input
            Log.d("showEncryptedDialog", "showEncryptedDialog: $tempData")
            val encryptedData = RC4Encryption.encryptData(key.toByteArray(), tempData.toByteArray())
            FileUtil.saveFileAsByteArray(fileName, encryptedData)
//            input= encryptedData.toString()
//            FileUtil.saveFile(fileName, encryptedData.toString())
            Log.d("check", "showEncryptedDialog: "+encryptedData.size+"  "+FileUtil.getFileSize(fileName))
            //用十六进制字符串显示加密后的数据
            finish()
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun showDecryptDialog() {
        val builder = MaterialAlertDialogBuilder(
            this,
            com.google.android.material.R.style.MaterialAlertDialog_Material3
        )
        val dialogInput = TextInputEditText(this)
        dialogInput.hint = "Enter the Key to view "
        builder.setView(dialogInput)
        builder.setTitle("Decrypt")
        builder.setPositiveButton("OK") { dialog, _ ->
            val key = dialogInput.text.toString() // 使用text属性获取用户输入的文本
            if (key.isEmpty()) {
                Toast.makeText(this, "Key is empty", Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            }

            val tempData: ByteArray? = FileUtil.readFileAsByteArray(fileName)
            if (tempData != null) {
                // 解密数据
                val decryptedData = RC4Encryption.decryptData(key.toByteArray(), tempData)

                // 将解密后的数据保存到文件
//                FileUtil.saveFileAsByteArray(fileName, decryptedData)
                editText.isEnabled = false

                // 显示解密后的数据
                val decryptedString = String(decryptedData, Charsets.UTF_8) // 使用UTF-8编码将byte数组转换为字符串
                markwon.setMarkdown(mdShowView, decryptedString)
                Log.d("decrypt", "Key: $key")
            } else {
                Toast.makeText(this, "Failed to read file", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

}
