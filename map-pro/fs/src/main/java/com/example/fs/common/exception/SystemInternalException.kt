package com.aisino.htlz.ai.platform.common.exception

import com.example.fs.common.exception.FsException

class SystemInternalException : FsException {
    constructor()
    constructor(message: String?) : super(message)
    constructor(code: Int?, message: String?) : super(code, message)
}
