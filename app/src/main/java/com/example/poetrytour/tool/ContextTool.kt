package com.example.poetrytour.tool

import android.app.Application
import android.content.Context
import com.example.poetrytour.tool.ContextTool.Companion._context

class ContextTool:Application() {

    companion object {
        var  _context: Application? = null
        fun getContext(): Context {
            return _context!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        _context = this
    }

}


