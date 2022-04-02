package es.vytale.milanesa.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;
import org.junit.Test;

import java.util.concurrent.ConcurrentSkipListSet;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class DeserializationTest {

    @Test
    public void doTest() {
        TestData test = new TestData(new ConcurrentSkipListSet<>());
        test.getSet().add("ñ");

        GsonBuilder gsonBuilder = new GsonBuilder();

        Gson gson = gsonBuilder.create();

        String json = gson.toJson(test);

        System.out.println("Gson: " + json);

        TestData testDataDeserialized = gson.fromJson(json, TestData.class);
        System.out.println("Set used is: " + testDataDeserialized.getSet().getClass().getSimpleName());
    }

    @Data
    public static class TestData {
        private final ConcurrentSkipListSet<String> set;
    }
}
