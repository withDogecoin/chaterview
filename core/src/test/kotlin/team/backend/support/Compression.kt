package team.backend.support

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

class Compression: StringSpec({

    val massiveText = "HelloHelloHelloHelloDlkanfioadfnakldn  ZLIB  kasdnfoijdfknzcvz!@#!#!sdafnaiadkfnaksdf" +
        "oijdfknzccxklvnz!%^&%^&^%&%^&SD  BAEK  Fvdfgahiohenraoifnzmvz,vzcvvz!@#!#!sd" +
        "oijdfknzccxklvnz!%^&%^&^  JungHo  %&%^&SDFvdfgahiohenraoifnzmvz,vzcvvz!@#!#!" +
        "sadfasfafnfnfnfnfndskncknverihqwnmzcnv"

    "Compression and DeCompression using ZLIB" {
        val compressedData = massiveText.zlibCompress()
        val deCompressedData = compressedData.zlibDecompress()

        val originalSize = massiveText.toByteArray(charset("UTF-8")).size
        val compressedDataSize = compressedData.size
        val deCompressedDataSize = deCompressedData.toByteArray(charset("UTF-8")).size

        println("original size: $originalSize")
        println("compressedData size: $compressedDataSize")
        println("deCompressedData size: $deCompressedDataSize")

        originalSize shouldBeGreaterThan compressedDataSize
        originalSize shouldBe deCompressedDataSize
        massiveText shouldBe deCompressedData
    }
})