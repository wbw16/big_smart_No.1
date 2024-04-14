package com.wbw.note.util

import java.io.IOException
import java.io.InputStream

class ToolUtil {
  //返回ByteArray的十六进制字符串


    companion object {
        fun bytesToHexString(src: ByteArray): String {
            val stringBuilder = StringBuilder("")
            if (src.isEmpty()) {
                return ""
            }
            for (element in src) {
                val v = element.toInt() and 0xFF
                val hv = Integer.toHexString(v)
                if (hv.length < 2) {
                    stringBuilder.append(0)
                }
                stringBuilder.append(hv)
            }
            return stringBuilder.toString()
        }
    }


}