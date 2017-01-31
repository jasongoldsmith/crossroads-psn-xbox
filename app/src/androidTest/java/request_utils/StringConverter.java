package request_utils;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Scanner;

public class StringConverter implements Converter {
    // TODO: 8/25/15 test this
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        try {
            Scanner s = new Scanner(body.in()).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        } catch (IOException e) {
            return "Error";
        }
    }

    public TypedOutput toBody(Object object) {
        return null;
    }
}
