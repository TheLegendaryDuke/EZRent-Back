package com.aoranzhang.ezrentback.data.graphql.dataTypes

import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.GraphQLScalarType
import org.springframework.stereotype.Component

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

@Component
class GraphqlDateType : GraphQLScalarType("Date", "Date", object : Coercing<Date, String> {
    var sdf = SimpleDateFormat("yyyy-MM-dd kk:mm")

    override fun serialize(input: Any): String? {
        return if (input is Date) {
            sdf.format(input)
        } else {
            null
        }
    }

    override fun parseValue(input: Any): Date? {
        return if (input is String) {
            try {
                sdf.parse(input)
            } catch (e: ParseException) {
                null
            }
        } else {
            null
        }
    }

    override fun parseLiteral(input: Any): Date? {
        if (input is StringValue) {
            try {
                return sdf.parse(input.getValue())
            } catch (e: ParseException) {
                return null
            }

        }
        return null
    }
})
