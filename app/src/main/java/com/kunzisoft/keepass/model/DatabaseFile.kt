package com.kunzisoft.keepass.model

import com.kunzisoft.keepass.app.database.FileDatabaseHistoryEntity
import com.kunzisoft.keepass.utils.FileDatabaseInfo

data class DatabaseFile(var entity: FileDatabaseHistoryEntity,
                        var fileDatabaseInfo: FileDatabaseInfo)