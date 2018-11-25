package br.com.drivin.model

import org.apache.commons.lang3.builder.ToStringBuilder

/**
 * Created by pedromalta on 15/03/18.
 */

abstract class BaseModel {

    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this)
    }
}
