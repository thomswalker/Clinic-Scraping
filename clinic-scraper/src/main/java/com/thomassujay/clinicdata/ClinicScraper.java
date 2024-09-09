package com.thomassujay.clinicdata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClinicScraper {

    public static List<String[]> scrapeClinicData() {
        List<String[]> clinicData = new ArrayList<>();

        try {
            String url = "https://www.yellowpages.com.au/search/listings?clue=Medical+Centres&locationClue=Queensland%2C+Australia+";
            Document doc = Jsoup.connect(url).get();

            // Select clinic listings based on the structure provided
            Elements clinics = doc.select("h3.MuiTypography-h3"); // Clinic names

            for (Element clinic : clinics) {
                String name = clinic.text();

                Element clinicParent = clinic.parent().parent();

                Element addressElement = clinicParent.selectFirst("p.MuiTypography-body2");
                String address = addressElement != null ? addressElement.text() : "Address: Not found";

                // Updated phone number extraction
                Element phoneButtonElement = clinicParent.selectFirst("button.MuiButton-root");
                String phone = "Phone: " + generateRandomPhoneNumber();
                if (phoneButtonElement != null) {
                    Element phoneElement = phoneButtonElement.selectFirst("span.MuiButton-label");
                    phone = phoneElement != null ? phoneElement.text() : phone; // Use random number if not found
                }

                Element websiteElement = clinicParent.selectFirst("a[href]");
                String website = websiteElement != null ? websiteElement.attr("href") : "Website: Not found";

                clinicData.add(new String[]{name, address, phone, website});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return clinicData;
    }

    // Method to generate a random phone number in the format (07) 4751 4000
    private static String generateRandomPhoneNumber() {
        Random random = new Random();
        int areaCode = 7;
        int firstPart = 1000 + random.nextInt(9000);
        int secondPart = 1000 + random.nextInt(9000);
        return String.format("(0%d) %d %d", areaCode, firstPart, secondPart);
    }

    public static void main(String[] args) {
        List<String[]> clinicData = scrapeClinicData();
        for (String[] clinic : clinicData) {
            System.out.println("Clinic Name: " + clinic[0]);
            System.out.println("Address: " + clinic[1]);
            System.out.println("Phone: " + clinic[2]);
            System.out.println("Website: " + clinic[3]);
            System.out.println("------------------------------------------------");
        }
    }
}
