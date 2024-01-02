package ru.spacestar.logs_list.ui.logsList.business

import android.content.Context
import android.net.Uri
import android.os.storage.StorageManager
import android.provider.OpenableColumns
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import ru.spacestar.core.utils.StringExtensions.urlEncoded
import ru.spacestar.core_ui.viewmodel.BaseSideEffect
import ru.spacestar.core_ui.viewmodel.BaseViewModel
import ru.spacestar.logs_list.R
import ru.spacestar.logs_list.navigation.LogsListApiImpl
import ru.spacestar.logs_list.ui.views.logItem.LogItemState
import java.io.File
import javax.inject.Inject

@HiltViewModel
internal class LogsListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @ApplicationContext context: Context
) : BaseViewModel<LogsListState, LogsListSideEffect>(context, savedStateHandle) {

    private val appContext: Context
        get() = getApplication()

    private val logsListApi by lazy { LogsListApiImpl() }

    override val container = container(LogsListState()) { loadLogsList() }

    private fun loadLogsList() = intent {
        val logs = appContext.getDir(LOGS_SUBDIR, Context.MODE_PRIVATE).listFiles()?.map { file ->
            val name = file.name
            val date = file.lastModified()
            val uri = file.toUri()
            LogItemState(
                filename = name,
                uri = uri,
                date = date
            )
        }?.sortedByDescending { it.date }.orEmpty()
        reduce { LogsListState(logs) }
    }

    fun addLog(uri: Uri?) = intent {
        if (uri == null) return@intent
        val file = getFileInfo(uri) ?: return@intent

        // check free space
        val storageManager = appContext.getSystemService(StorageManager::class.java)
        val dirUuid = storageManager.getUuidForPath(appContext.filesDir)
        val availableSpace = storageManager.getAllocatableBytes(dirUuid)
        if (availableSpace >= file.size) {
            storageManager.allocateBytes(dirUuid, file.size)
        } else {
            postSideEffect(LogsListSideEffect.ShowMessage(R.string.not_enough_space))
        }

        // copy file to app-specific storage
        appContext.contentResolver.openInputStream(uri)?.use { input ->
            val dir = appContext.getDir(LOGS_SUBDIR, Context.MODE_PRIVATE)
            val localFile = resolveFilename(File(dir, file.name))
            localFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        loadLogsList()
    }

    fun deleteLog(uri: Uri) = intent {
        val file = uri.toFile()
        file.delete()
        loadLogsList()
    }

    fun selectLog(uri: Uri) = intent {
        val route = logsListApi.details(uri.toString().urlEncoded)
        postSideEffect(BaseSideEffect.Navigate(route))
    }

    private fun getFileInfo(uri: Uri): FileInfo? {
        return appContext.contentResolver
            .query(uri, null, null, null, null).use { cursor ->
                if (cursor?.moveToFirst() != true) return null
                val name = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val size = cursor.getColumnIndex(OpenableColumns.SIZE)

                FileInfo(
                    name = cursor.getString(name),
                    size = cursor.getLong(size)
                )
        }
    }

    private fun resolveFilename(file: File): File {
        if (!file.exists()) return file
        val fileVer = Regex(""".*\((\d+)\)""")
        val parent = file.parentFile
        val otherVersions = parent?.listFiles { _, name ->
            name.contains(file.nameWithoutExtension)
        }?.map { it.nameWithoutExtension }
            ?.map { fileVer.matchEntire(it)?.groupValues?.getOrNull(1)?.toIntOrNull() ?: 0 }
            .orEmpty()
        val latestVer = otherVersions.max()
        val newVer = latestVer + 1
        val filename = "${file.nameWithoutExtension} ($newVer).${file.extension}"
        return File(parent, filename)
    }

    private class FileInfo(
        val name: String,
        val size: Long
    )

    companion object {
        private const val LOGS_SUBDIR = "imported_logs"
    }
}