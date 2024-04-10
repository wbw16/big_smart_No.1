package com.wbw.note

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.wbw.note.encrypt.RC4Encryption
import com.wbw.note.util.FileUtil
import io.noties.markwon.Markwon
import io.noties.markwon.image.ImagesPlugin


class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val key = intent.getStringExtra("encryptionKey")
        val mode = intent.getIntExtra("mode", -1)
        val fileName = intent.getStringExtra("fileName").toString()
        var input = ""

        if (mode == 1) {
            // 修改模式
            input = FileUtil.readFile(fileName)
        } else if (mode == 0) {
            // 新建模式
            input = ""
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

        val markwon = Markwon.builder(this)
            .usePlugin(ImagesPlugin.create())
            .build()
        val editText = findViewById<EditText>(R.id.editText)
        val mdShowView = findViewById<TextView>(R.id.markdownView)

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
    }
}
