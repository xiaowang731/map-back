package com.example.oauthservice.common

import lombok.Data

@Data
class JsonResponse<T> {
    private var code = 0
    private var message = ""
    private var data: T? = null

    fun data(data: T?): JsonResponse<T> {
        this.data = data
        return this
    }

    fun code(code: Int): JsonResponse<T> {
        this.code = code
        return this
    }

    fun message(message: String): JsonResponse<T> {
        this.message = message
        return this
    }

    fun fail(): JsonResponse<T> {
        this.code = FAIL
        return this
    }

    fun success(): JsonResponse<T> {
        this.code = SUCCESSFUL
        return this
    }

    fun redistribute(): JsonResponse<T> {
        this.code = REDISTRIBUTE
        return this
    }

    fun unauthorized(): JsonResponse<T> {
        this.code = UNAUTHORIZED
        return this
    }

    fun forbidden(): JsonResponse<T> {
        this.code = FORBIDDEN
        return this
    }


    companion object {
        const val SUCCESSFUL: Int = 200 // OK
        const val REDISTRIBUTE: Int = 302 // 重定向
        const val FAIL: Int = 400 // fail
        const val UNAUTHORIZED: Int = 401 // Token错误
        const val FORBIDDEN: Int = 403 // 验证码错误
        const val NOT_FOUND: Int = 404 // 资源未找到
        const val METHOD_NOT_ALLOWED: Int = 405 // 请求方法不支持
        const val ACCOUNT_ERROR: Int = 406 // 密码错误
        const val PERMISSIONS_ERROR: Int = 407 // 权限不足
        const val ACCOUNT_STOP: Int = 408 // 账号未启用
        const val DUPLICATE_EMAIL: Int = 409 // 账号邮箱重复
        const val EMAIL_ERROR: Int = 410 // 邮箱格式错误
        const val NO_PLAN: Int = 411 // 当天无计划

        @JvmStatic
        fun <T> FAIL(): JsonResponse<T> {
            return JsonResponse<T>().fail()
        }

        @JvmStatic
        fun <T> SUCCESSFUL(): JsonResponse<T> {
            return JsonResponse<T>().success()
        }

        @JvmStatic
        fun <T> REDISTRIBUTE(): JsonResponse<T> {
            return JsonResponse<T>().redistribute()
        }

        @JvmStatic
        fun <T> UNAUTHORIZED(): JsonResponse<T> {
            return JsonResponse<T>().unauthorized()
        }

        @JvmStatic
        fun <T> FORBIDDEN(): JsonResponse<T> {
            return JsonResponse<T>().forbidden()
        }

        @JvmStatic
        fun <T> of(code: Int, message: String = ""): JsonResponse<T> {
            return JsonResponse<T>().code(code).message(message)
        }

        @JvmStatic
        fun <T> SUCCESSFUL(message: String = "", data: T): JsonResponse<T> {
            return JsonResponse<T>().success().data(data).message(message)
        }

        @JvmStatic
        fun SUCCESSFUL(message: String = ""): JsonResponse<Any> {
            return JsonResponse<Any>().success().data(null).message(message)
        }

        @JvmStatic
        fun <T> FAIL(message: String = "", data: T): JsonResponse<T> {
            return JsonResponse<T>().fail().data(data).message(message)
        }

        @JvmStatic
        fun FAIL(message: String = ""): JsonResponse<Any> {
            return JsonResponse<Any>().fail().data(null).message(message)
        }
    }
}

