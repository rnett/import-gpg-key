package com.rnett.action

import Buffer
import com.rnett.action.core.inputs
import com.rnett.action.core.maskSecret
import com.rnett.action.core.outputs
import com.rnett.action.exec.exec

suspend fun main() {
    val secretKey = inputs["secret-key"]
        .also(::maskSecret)
        .replace("\\n", "\n")
        .also(::maskSecret)

    val exportGpgSecring = inputs["export-secring"].toBoolean()

    exec.execCommand("gpg --batch --import", input = Buffer.from(secretKey))

    val keyId = exec.execCommandAndCapture("gpg --list-secret-keys --keyid-format SHORT").stdout
        .substringAfter("sec")
        .substringAfter("/")
        .substringBefore(" ")
        .also(::maskSecret)

    outputs["key-id"] = keyId

    if(exportGpgSecring){
        exec.execShell("gpg --export-secret-key $keyId > ~/.gnupg/secring.gpg")
    }
}