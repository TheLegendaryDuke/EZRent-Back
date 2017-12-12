package com.aoranzhang.ezrentback.data.graphql.dataTypes;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class GraphqlDateType extends GraphQLScalarType {
    public GraphqlDateType() {

        super("Date", "Date", new Coercing<Date, String>() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm");

            @Override
            public String serialize(Object input) {
                if(input instanceof Date) {
                    return sdf.format(input);
                }else {
                    return null;
                }
            }

            @Override
            public Date parseValue(Object input) {
                if(input instanceof String) {
                    try {
                        return sdf.parse((String) input);
                    }catch (ParseException e) {
                        return null;
                    }
                }else {
                    return null;
                }
            }

            @Override
            public Date parseLiteral (Object input) {
                if (input instanceof StringValue) {
                    try {
                        return sdf.parse(((StringValue) input).getValue());
                    } catch (ParseException e) {
                        return null;
                    }
                }
                return null;
            }
        });
    }
}
