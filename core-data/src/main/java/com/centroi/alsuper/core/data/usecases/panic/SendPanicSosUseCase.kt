package com.centroi.alsuper.core.data.usecases.panic

import com.centroi.alsuper.core.data.repositories.panic.PanicRepositoryInt
import javax.inject.Inject

class SendPanicSosUseCase @Inject constructor(
    private val repository: PanicRepositoryInt
) {
    operator fun invoke() = repository.sendSos()
}