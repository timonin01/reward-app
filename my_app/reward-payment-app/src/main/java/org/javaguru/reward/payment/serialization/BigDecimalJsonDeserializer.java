package org.javaguru.reward.payment.serialization;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@JsonComponent
public class BigDecimalJsonDeserializer extends JsonDeserializer<BigDecimal> {

    @Override
    public BigDecimal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String value = jsonParser.getText();

        if (value == null || value.trim().isEmpty()) {
            return null; // Default value if input is empty
        }

        try {
            return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP); // Ensure 2 decimal places
        } catch (NumberFormatException e) {
            throw new IOException("Invalid BigDecimal format: " + value);
        }
    }

}