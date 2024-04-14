package com.wbw.note.util

import android.util.Log
import android.widget.Toast
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import kotlin.math.log

object FileUtil {
    val fileName: ArrayList<String>
        // 读取文件夹下面的所有文件，返回文件名列表
        get() {
            val path = "/data/data/com.wbw.note/files"
            val fileList = ArrayList<String>()
            //判读文件夹是否存在，不存在则创建
            val folder = File(path)
            if (!folder.exists() || !folder.isDirectory) {
                folder.mkdirs()
            }
            //获取文件夹下的所有文件
            val files = folder.listFiles()
            if (files != null) {
                for (file in files) {
                    if (file.isFile) {
                        fileList.add(file.name)
                    }
                }
            }
            return fileList
}

    fun saveFile(filename: String, input: String?) {
        var writer: BufferedWriter? = null
        //检测文件是否存在，不存在则创建
        try {
            //检测文件是否存在，不存在则创建
            writer = BufferedWriter(FileWriter("/data/data/com.wbw.note/files/$filename"))
            writer.write(input)
            Log.d("wbw", "saveFile: 保存成功")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                writer?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun saveFileAsByteArray(filename: String, input: ByteArray) {
        var outputStream: FileOutputStream? = null
        try {
            outputStream = FileOutputStream("/data/data/com.wbw.note/files/$filename")
            outputStream.write(input)
            Log.d("wbw", "saveFileAsByteArraya: 保存成功"+ToolUtil.bytesToHexString(input).toString())
            Log.d("wbw", "saveFileAsByteArraya: 保存成功"+input.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                Log.d("wbw", "saveFileAsByteArray"+getFileSize(filename).toString())
                outputStream?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun getFileSize(fileName: String): Long {
        val filePath = "/data/data/com.wbw.note/files/$fileName"
        val file = File(filePath)
        if (!file.exists() || !file.isFile) {
            return -1 // 返回-1表示文件不存在或者不是一个文件
        }
        return file.length()
    }


    fun readFileAsByteArray(fileName: String): ByteArray? {

        var fileInputStream: FileInputStream? = null
        try {
            fileInputStream = FileInputStream("/data/data/com.wbw.note/files/$fileName")
            val outputStream = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var bytesRead = fileInputStream.read(buffer)
            while (bytesRead != -1) {
                outputStream.write(buffer, 0, bytesRead)
                bytesRead = fileInputStream.read(buffer)
            }
            val byteArray = outputStream.toByteArray()
            Log.d("wbw", "readFileAsByteArray: 读取成功 " + byteArray.size)
            return byteArray
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fileInputStream?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }



    fun deleteFile(filename: String) {
        val file = File("/data/data/com.wbw.note/files/$filename")
        if (file.exists()) {
            file.delete()
        }
    }

    fun creatFile(filename: String) {
        val file = File("/data/data/com.wbw.note/files/$filename")
        if (!file.exists()) {
            file.createNewFile()
            Log.d("creatFile", "creatFile: 文件创建成功")
        }else{
            Log.d("creatFile", "creatFile: 文件已存在")
            Toast.makeText(null, "文件已存在", Toast.LENGTH_SHORT).show()
        }
    }


    fun readFile(fileName: String): String {
        val content = StringBuilder()
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/data/data/com.wbw.note/files/$fileName"))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                content.append(line).append("\n")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return content.toString()
    }
}