package com.example.oauthservice.common

data class Sort(
    var name: String? = null,
    var order: String? = null,
)

val SUPPORTED_ORDERS = mapOf(
    "descending" to false,
    "ascending" to true,
)

fun isAsc(order: String?): Boolean {
    return SUPPORTED_ORDERS[order] ?: true
}

fun isSupportedOrder(order: String?): Boolean {
    order ?: return false
    return SUPPORTED_ORDERS.containsKey(order)
}
