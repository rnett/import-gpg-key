package com.rnett.action

import Buffer
import com.rnett.action.core.inputs
import com.rnett.action.core.outputs
import com.rnett.action.exec.exec
import com.rnett.action.exec.execAndCapture

suspend fun main() {
    val secretKey = inputs.getRequired("secret-key").replace("\\n", "\n")

    val exportSecring = inputs["export-secring"]?.toLowerCase().toBoolean()

    exec("gpg --batch --import", input = Buffer(secretKey))

    val keyId = execAndCapture("gpg --list-secret-keys --keyid-format SHORT").stdout
        .substringAfter("sec").substringAfter("/").substringBefore(" ")

    outputs["key-id"] = keyId

    if(exportSecring){
        exec("gpg --export-secret-key $keyId > ~/.gnupg/secring.gpg")
    }
}