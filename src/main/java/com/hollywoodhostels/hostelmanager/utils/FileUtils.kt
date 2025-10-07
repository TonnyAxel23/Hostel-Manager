package com.hollywoodhostels.hostelmanager.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

object FileUtils {
    fun getAppDirectory(context: Context): File {
        return File(context.getExternalFilesDir(null), "HostelManager").apply {
            if (!exists()) mkdirs()
        }
    }

    fun createExportFile(context: Context, fileName: String): File {
        val directory = getAppDirectory(context)
        return File(directory, fileName)
    }

    fun shareFile(context: Context, file: File, title: String) {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/csv"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(shareIntent, "Share $title"))
    }
}