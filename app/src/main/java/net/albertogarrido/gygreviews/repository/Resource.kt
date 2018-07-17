package net.albertogarrido.gygreviews.repository

import net.albertogarrido.gygreviews.util.Status

fun <T> resourceSuccess(data: T): Resource<T> = Resource(Status.SUCCESS, data, null)

fun <T> resourceError(msg: String, data: T): Resource<T> = Resource(Status.ERROR, data, msg)

fun <T> resourceEmpty(msg: String, data: T): Resource<T> = Resource(Status.EMPTY_DATA, data, msg)

fun <T> resourceLoading(data: T): Resource<T> = Resource(Status.LOADING, data, null)

data class Resource<T>(val status: Status, val data: T, val message: String?)
