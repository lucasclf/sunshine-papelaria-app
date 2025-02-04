package com.sunshine.backend.presentation.utils

import com.sunshine.backend.domain.enums.SunshineExceptionEnum
import com.sunshine.backend.domain.exceptions.SunshineException

object ValidationUtils {
    fun validateId(id: Int?){
        if(id == null){
            throw SunshineException(SunshineExceptionEnum.INVALID_ID)
        }
    }
}