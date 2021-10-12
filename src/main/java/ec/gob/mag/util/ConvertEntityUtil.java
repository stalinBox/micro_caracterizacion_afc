package ec.gob.mag.util;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service("convertEntityUtil")
public class ConvertEntityUtil {
	public <T> T ConvertSingleEntityGET(Class<T> clazz, Object obj) throws IOException, NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		String jsonString = null;
		ObjectMapper mprObjecto = new ObjectMapper();
		mprObjecto.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		mprObjecto.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		jsonString = mprObjecto.writeValueAsString(obj);
		return mprObjecto.readValue(jsonString, clazz);
	}

	public <T> T ConvertListEntity(Class<T> clazz, Object obj) throws IOException, NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		String jsonString = null;
		ObjectMapper mprObjecto = new ObjectMapper();
		mprObjecto.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mprObjecto.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		mprObjecto.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
		jsonString = mprObjecto.writeValueAsString(obj);
		return mprObjecto.readValue(jsonString, mprObjecto.getTypeFactory().constructCollectionType(List.class, clazz));
	}

}
