package team.backend.support

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

internal class CompressionTest: StringSpec({

    val massiveText = "HelloHelloHelloHelloDlkanfioadfnakldn  ZLIB  kasdnfoijdfknzcvz!@#!#!sdafnaiadkfnaksdf" +
        "oijdfknzccxklvnz!%^&%^&^%&%^&SD  BAEK  Fvdfgahiohenraoifnzmvz,vzcvvz!@#!#!sd" +
        "oijdfknzccxklvnz!%^&%^&^  JungHo  %&%^&SDFvdfgahiohenraoifnzmvz,vzcvvz!@#!#!" +
        "sadfasfafnfnfnfnfndskncknverihqwnmzcnv"

    "Compression and DeCompression using ZLIB" {
        val compressedData = massiveText.compress()
        val deCompressedData = compressedData.decompress()

        val originalSize = massiveText.toByteArray(charset("UTF-8")).size
        val compressedDataSize = compressedData.size
        val deCompressedDataSize = deCompressedData.toByteArray(charset("UTF-8")).size

        println("Original size: $originalSize")
        println("Compressed size: $compressedDataSize")
        println("DeCompressed size: $deCompressedDataSize")
        println("DeCompressed Results: $deCompressedData")

        originalSize shouldBeGreaterThan compressedDataSize
        originalSize shouldBe deCompressedDataSize
        massiveText shouldBe deCompressedData
    }
})