package com.cdbfkk.lootly.shared.logging

import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import java.io.ByteArrayInputStream

class CachedBodyHttpServletRequest(request: HttpServletRequest) : HttpServletRequestWrapper(request) {

    private val cachedBody: ByteArray = request.inputStream.readBytes()

    override fun getInputStream(): ServletInputStream {
        val byteArrayInputStream = ByteArrayInputStream(cachedBody)

        return object : ServletInputStream() {
            override fun read(): Int = byteArrayInputStream.read()
            override fun isFinished() = byteArrayInputStream.available() == 0
            override fun isReady() = true
            override fun setReadListener(readListener: ReadListener?) {}
        }
    }

    fun getCachedBody(): String = cachedBody.toString(Charsets.UTF_8)
}