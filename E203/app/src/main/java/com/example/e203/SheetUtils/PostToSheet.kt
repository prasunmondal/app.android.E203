package com.example.e203.SheetUtils

import com.prasunmondal.lib.posttogsheets.PostToGSheet

class PostToSheets private constructor() {
    var logs: PostToGSheet =
        PostToGSheet(
            "https://script.google.com/macros/s/AKfycbyoYcCSDEbXuDuGf0AhQjEi61ECAkl8JUv4ffNofz1yBIKfcT4/exec",
            "https://docs.google.com/spreadsheets/d/1qacLjDP01fA5xxo1RNI9oGDyP6iknMQyIOPx24brJlA/edit#gid=0",
            "default",
            "https://docs.google.com/spreadsheets/d/1qacLjDP01fA5xxo1RNI9oGDyP6iknMQyIOPx24brJlA/edit#gid=0",
            "template",
            true, null
        )

    var error: PostToGSheet =
        PostToGSheet(
            "https://script.google.com/macros/s/AKfycbyoYcCSDEbXuDuGf0AhQjEi61ECAkl8JUv4ffNofz1yBIKfcT4/exec",
            "https://docs.google.com/spreadsheets/d/1qacLjDP01fA5xxo1RNI9oGDyP6iknMQyIOPx24brJlA/edit#gid=0",
            "errors",
            "https://docs.google.com/spreadsheets/d/1qacLjDP01fA5xxo1RNI9oGDyP6iknMQyIOPx24brJlA/edit#gid=0",
            "template",
            true, null
        )

    fun skipPost(): Boolean {
        return false
    }

    private object InstanceHolder {
        val INSTANCE = PostToSheets()
    }

    companion object {
        val instance: PostToSheets by lazy { InstanceHolder.INSTANCE }
    }
}