package com.eldar.eldarwallet.framework.datasource.qr

import com.eldar.eldarwallet.data.datasource.GenerateQrData
import com.eldar.eldarwallet.utils.configureServer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.random.Random

@HiltAndroidTest
class GenerateQrDataImplTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var mockWebServer: MockWebServer

    @Inject
    lateinit var generateQrData: GenerateQrData

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)

        hiltRule.inject()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun qrQueryNotReturnNull() {
        val filePath = "qr_code_test.png"

        mockWebServer.configureServer(
            200, 3, filePath, this.javaClass)

        runBlocking {
            val response = generateQrData.queryGenerateQr("daniel olivera")

            Assert.assertNotNull(response)
        }
    }

    @Test
    fun qrQueryReturnNullInRange100To199() {
        val filePath = "qr_code_test.png"

        val random = Random(System.currentTimeMillis())
        val code = random.nextInt(100, 199)

        mockWebServer.configureServer(
            code, 3, filePath, this.javaClass)

        runBlocking {
            val response = generateQrData.queryGenerateQr("daniel olivera")

            Assert.assertNull(response)
        }
    }

    @Test
    fun qrQueryReturnNullInRange300To599() {
        val filePath = "qr_code_test.png"

        val random = Random(System.currentTimeMillis())
        val code = random.nextInt(300, 599)

        mockWebServer.configureServer(
            code, 3, filePath, this.javaClass)

        runBlocking {
            val response = generateQrData.queryGenerateQr("daniel olivera")

            Assert.assertNull(response)
        }
    }
}