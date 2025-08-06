package utils;

import com.github.javafaker.Faker;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Utils {

    public static Faker faker = new Faker();

    public static String getRandomFirstName() {
        return faker.name().firstName();
    }

    public static String getRandomLastName() {
        return faker.name().lastName();
    }

    public static String getRandomEmail() {
        return "abcd" + Utils.generateRandomNumber(100, 999) + "@gmail.com";
    }

    public static String getPassword() {
        return "1234";
    }

    public static String getRandomPhoneNumber() {
        return "0178" + Utils.generateRandomNumber(1000000, 9999999);
    }

    public static String getRandomAddress() {
        return faker.address().fullAddress();
    }

    public static String getRandomGender() {
        return faker.options().option("Male", "Female");
    }


    public static void setEnvVar(String key, String value) throws  org.apache.commons.configuration.ConfigurationException {
        PropertiesConfiguration config=new PropertiesConfiguration("./src/test/resources/config.properties");
        config.setProperty(key,value);
        config.save();
    }
    public static int generateRandomNumber(int min, int max){
        double random=Math.random()*(max-min)+min;
        return (int)random;
    }
}
