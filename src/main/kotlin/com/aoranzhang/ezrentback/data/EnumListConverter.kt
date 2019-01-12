package com.aoranzhang.ezrentback.data

import com.aoranzhang.ezrentback.data.entity.Role
import com.google.common.base.Joiner
import com.google.common.collect.ImmutableSet
import java.util.stream.Collectors
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class EnumListConverter : AttributeConverter<Set<Role>, String> {
    override fun convertToDatabaseColumn(attribute: Set<Role>?): String {
        return Joiner.on(',').join(attribute!!.stream().map { t -> t.name }.collect(Collectors.toList()) as Iterable<String>)
    }

    override fun convertToEntityAttribute(dbData: String?): Set<Role> {
        return ImmutableSet.copyOf(dbData!!.split(",").stream().map { s -> Role.valueOf(s) }.collect(Collectors.toList()) as Iterable<Role>)
    }
}