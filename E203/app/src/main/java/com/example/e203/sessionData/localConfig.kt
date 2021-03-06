package com.example.e203.sessionData

import com.example.e203.TransactionRecord
import java.io.File
import com.example.e203.Utility.FileReadUtil.Singleton.instance as FileReadUtils
import com.example.e203.Utility.FileWriteUtil.Singleton.instance as FileWriteUtils
import com.example.e203.appData.FileManagerUtil.Singleton.instance as FileManagerUtils

class LocalConfig {

    val USERNAME = "username"
    lateinit var viewTransaction: TransactionRecord

    private var localConfigMap: MutableMap<String, String> = mutableMapOf()

    object Singleton {
        var instance = LocalConfig()
    }

    fun setValue(key: String, value: String) {
        localConfigMap[key] = value

        FileWriteUtils.writeToInternalFile(
            FileManagerUtils.localConfigurationStorage,
            FileWriteUtils.deseriallizeFromMap(localConfigMap)
        )
    }

    fun getValue(key: String): String? {
        FileReadUtils.readPairCSVnPopulateMap(
            localConfigMap,
            FileManagerUtils.localConfigurationStorage
        )

        return localConfigMap[key]
    }

    fun doesUsernameExists(): Boolean {
        if (FileManagerUtils.doesFileExist(FileManagerUtils.localConfigurationStorage)) {
            val username = getValue(USERNAME)
            if (username != null && username.isNotEmpty()) {
                return true
            }
        }
        return false
    }

    fun deleteData() {
        var destination = FileManagerUtils.localConfigurationStorage.destination
        val file = File(destination)
        if (file.exists()) file.delete()
    }
}