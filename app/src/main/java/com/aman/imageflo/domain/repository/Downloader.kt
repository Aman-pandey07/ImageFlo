package com.aman.imageflo.domain.repository

interface Downloader {
    fun downloadFile(url: String, fileName: String?)
}