package br.com.sunshine.stockmanager.domain.exceptions

import br.com.sunshine.stockmanager.domain.enums.SunshineExceptionEnum

class SunshineException(
    val error: SunshineExceptionEnum,
    val errorExtraInfo: String = ""
) : RuntimeException(error.description)