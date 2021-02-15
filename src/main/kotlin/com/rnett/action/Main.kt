package com.rnett.action

import Buffer
import com.rnett.action.core.inputs
import com.rnett.action.core.maskSecret
import com.rnett.action.core.outputs
import com.rnett.action.exec.exec

suspend fun main() {
    val secretKey = inputs.getRequired("secret-key")
        .also(::maskSecret)
        .replace("\\n", "\n")
        .also(::maskSecret)

    val exportGpgSecring = inputs["export-secring"]?.toLowerCase().toBoolean()

    exec.execCommand("gpg --batch --import", input = Buffer.from(secretKey))

    val keyId = exec.execCommandAndCapture("gpg --list-secret-keys --keyid-format SHORT").stdout
        .substringAfter("sec")
        .substringAfter("/")
        .substringBefore(" ")
        .also(::maskSecret)

    outputs["key-id"] = keyId

    if(exportGpgSecring){
        exec.execCommand("gpg --export-secret-key $keyId > ~/.gnupg/secring.gpg")
    }
}