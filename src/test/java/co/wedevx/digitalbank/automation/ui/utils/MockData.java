package co.wedevx.digitalbank.automation.ui.utils;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.bouncycastle.crypto.agreement.srp.SRP6Client;

import java.util.*;

public class MockData {

    private FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-US"),
            new RandomService());

    public Map<String, String> generateRandomNameEmail(){
        String name = fakeValuesService.bothify(new Faker().name().firstName());
        String email = fakeValuesService.bothify(name + "####@gmail.com");

        Map<String, String> data = new HashMap<>();
        data.put("name", name);
        data.put("email", email);
        return data;
    }

    public String generateRandomSsn() {
        String ssn = String.format("%09d", new Random().nextInt(1000000000));
       return ssn;
    }
}
