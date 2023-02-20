package com.example.poetrytour.ui.mine

import java.io.*

class GetPrivacyFile {
    fun getString(inputStream: InputStream?): String {
        var inputStreamReader: InputStreamReader? = null
        try {
            inputStreamReader = InputStreamReader(inputStream, "utf-8")
        } catch (e1: UnsupportedEncodingException) {
            e1.printStackTrace()
        }
        val reader = BufferedReader(inputStreamReader)
        val sb = StringBuffer("")
        var line: String?
        try {
            while (reader.readLine().also { line = it } != null) {
                sb.append(line)
                sb.append("\n")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return sb.toString()
    }
}