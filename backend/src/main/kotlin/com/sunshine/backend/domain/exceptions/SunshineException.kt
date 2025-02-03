package com.sunshine.backend.domain.exceptions

import com.sunshine.backend.domain.enums.SunshineExceptionEnum

class SunshineException(
    val error: SunshineExceptionEnum,
    val errorExtraInfo: String = ""
) : RuntimeException(error.description)