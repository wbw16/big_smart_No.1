package com.wbw.note.util

import android.util.Log
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter

object FileUtil {
    val fileName: ArrayList<String>
        // 读取文件夹下面的所有文件，返回文件名列表
        get() {
            val path = "/data/data/com.wbw.supermemo/files"
            val fileList = ArrayList<String>()
            fileList.add("test")
            fileList.add("test2")
            fileList.add("test3")
            Log.d("getFileName", "getTestFileName: success")
            //        File folder = new File(path);
//        if (folder.exists() && folder.isDirectory()) { // 文件夹存在且为目录才进行操作
//            File[] files = folder.listFiles();
//            if (files != null) {
//                for (File file : files) {
//                    if (file.isFile()) {
//                        fileList.add(file.getName());
//                    }
//                }
//            }
//        }else {
//            folder.mkdirs();
//        }
            return fileList
        }

    fun saveFile(filename: String, input: String?) {
        var writer: BufferedWriter? = null
        try {
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