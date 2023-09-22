package com.singaludra.weatherapp.domain.base

/**
 * @param P
 * @return a Resource written in R
 * */
abstract class BaseUseCase<in P, R> {
    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    abstract suspend fun execute(parameters: P): R
}