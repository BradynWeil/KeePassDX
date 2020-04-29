package com.kunzisoft.keepass.adapters

import android.app.Application
import androidx.lifecycle.*
import com.kunzisoft.keepass.app.App
import com.kunzisoft.keepass.app.database.FileDatabaseHistoryAction
import com.kunzisoft.keepass.model.DatabaseFile
import com.kunzisoft.keepass.settings.PreferencesUtil
import com.kunzisoft.keepass.utils.FileDatabaseInfo

class DatabaseFilesViewModel(application: Application) : AndroidViewModel(application) {

    private var mFileDatabaseHistoryAction: FileDatabaseHistoryAction? = null

    init {
        mFileDatabaseHistoryAction = FileDatabaseHistoryAction.getInstance(application.applicationContext)
    }

    val databaseFiles: MutableLiveData<List<DatabaseFile>> by lazy {
        MutableLiveData<List<DatabaseFile>>().also {
            loadDatabaseFiles()
        }
    }

    private fun loadDatabaseFiles() {
        val databaseFileList = ArrayList<DatabaseFile>()

        mFileDatabaseHistoryAction?.getAllFileDatabaseHistories { databaseFileHistoryList ->
            databaseFileHistoryList?.let { historyList ->

                val application = getApplication<App>()
                val hideBrokenLocations = PreferencesUtil.hideBrokenLocations(application.applicationContext)

                // Show only uri accessible
                historyList.forEach {
                    val fileDatabaseInfo = FileDatabaseInfo(application.applicationContext, it.databaseUri)
                    if (hideBrokenLocations && fileDatabaseInfo.exists
                            || !hideBrokenLocations) {
                        databaseFileList.add(DatabaseFile(it, fileDatabaseInfo))
                    }
                }

                databaseFiles.value = databaseFileList
            }
        }
    }
}