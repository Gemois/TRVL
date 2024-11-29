package com.iee.trvlapp.util;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.iee.trvlapp.MainActivity;
import com.iee.trvlapp.entities.CityHotel;
import com.iee.trvlapp.entities.Customer;
import com.iee.trvlapp.entities.Office;
import com.iee.trvlapp.entities.Package;
import com.iee.trvlapp.entities.Tour;

public class ImportDataUtil {

    // Populates the Room database with sample entities (Offices, Tours, Packages, CityHotels)
    public static void populateRoomDatabase() {

        // Creating Office entries
        Office office1 = new Office("Touristas", "Αντιγονιδων 21");
        Office office2 = new Office("DionTours", "Μητροπόλεως 98");
        Office office3 = new Office("Grecos.gr Travel", "Ναυάρχου Κουντουριώτου");
        Office office4 = new Office("Zorpidis", "Εγνατία 76");
        Office office5 = new Office("Prima Holidays", "Κλεάνθους 38");

        Office[] officesModelsArray = {office1, office2, office3, office4, office5};


        // Creating Tour entries
        Tour tour1 = new Tour("London", "England", 5, "ByRoad");
        Tour tour2 = new Tour("Paris", "France", 8, "ByPlane");
        Tour tour3 = new Tour("Tokyo", "Japan", 6, "ByPlane");
        Tour tour4 = new Tour("Vienna", "Austria", 5, "ByRoad");
        Tour tour5 = new Tour("New York", "USA", 10, "Cruise");

        Tour[] toursModelsArray = {tour1, tour2, tour3, tour4, tour5};


        // Creating Package entries
        Package package1 = new Package(5, 4, "07/05/2022", 250.0);
        Package package2 = new Package(4, 3, "08/08/2022", 900.0);
        Package package3 = new Package(3, 2, "06/010/2022", 500.0);
        Package package4 = new Package(2, 1, "03/03/2022", 300.0);
        Package package5 = new Package(1, 5, "05/09/2022", 1400.0);

        Package[] packagesModelsArray = {package1, package2, package3, package4, package5};


        // Creating CityHotel entries
        CityHotel cityHotels1 = new CityHotel("The Lucerne Hotel", "Gouverneur Street", 5, 5);
        CityHotel cityHotels2 = new CityHotel("Hotel Edison", "Wall Street.", 4, 5);
        CityHotel cityHotels3 = new CityHotel(" Ink 48 Hotel", "Pearl Street", 3, 5);

        CityHotel cityHotels4 = new CityHotel("Hotel Raphael", "Rue Oberkampf.", 5, 2);
        CityHotel cityHotels5 = new CityHotel("Ibis Styles ", "Rue Saint-Rustique.", 4, 2);
        CityHotel cityHotels6 = new CityHotel(" Citadines", "Avenue Montaigne.", 3, 2);

        CityHotel cityHotels7 = new CityHotel("Kaiserhof Wien", "Julius-Ficker-Straße", 5, 4);
        CityHotel cityHotels8 = new CityHotel("Hilton Vienna", "Fickeysstraße ", 4, 4);
        CityHotel cityHotels9 = new CityHotel("Andaz Vienna", "Blutgasse", 3, 4);

        CityHotel cityHotels10 = new CityHotel("London Marriott ", "Oxford Street", 5, 1);
        CityHotel cityHotels11 = new CityHotel("The Resident ", " Abbey Road ", 4, 1);
        CityHotel cityHotels12 = new CityHotel("The Gate", "Baker Street", 3, 1);

        CityHotel cityHotels13 = new CityHotel("Shibuya", "Kabukichō Ichiban-gai", 5, 3);
        CityHotel cityHotels14 = new CityHotel("Mitsui", "Godzilla Road", 4, 3);
        CityHotel cityHotels15 = new CityHotel("JR Kyushu", "Omoide Yokocho", 3, 3);

        CityHotel[] cityHotelsModelsArray = {cityHotels1, cityHotels2, cityHotels3, cityHotels4, cityHotels5, cityHotels6, cityHotels7, cityHotels8, cityHotels9, cityHotels10, cityHotels11, cityHotels12, cityHotels13, cityHotels14, cityHotels15};

        // Inserting Office entries into the Room database
        for (int i = 0; i < 5; i++) {
            MainActivity.appDatabase.officesDao().addOffice(officesModelsArray[i]);
        }

        // Inserting Tour entries into the Room database
        for (int j = 0; j < 5; j++) {
            MainActivity.appDatabase.toursDao().addTour(toursModelsArray[j]);
        }

        // Inserting Package entries into the Room database
        for (int k = 0; k < 5; k++) {
            MainActivity.appDatabase.packagesDao().addPackage(packagesModelsArray[k]);
        }

        // Inserting CityHotel entries into the Room database
        for (int l = 0; l < 15; l++) {
            MainActivity.appDatabase.cityHotelsDao().addCityHotel(cityHotelsModelsArray[l]);
        }

    }

    // Deletes all entries from the Room database
    public static void deleteAllRoomApiData() {
        MainActivity.appDatabase.officesDao().deleteAllOffices();
        MainActivity.appDatabase.toursDao().deleteAllTours();
        MainActivity.appDatabase.packagesDao().deleteAllPackages();
        MainActivity.appDatabase.cityHotelsDao().deleteAllCityHotels();
    }

    // Populates Firestore with customer data
    public static void populateFirestoreDatabase() {

        Customer costumer1 = new Customer(1, "Georgios", "Moisidis", 231439216, "giorgosmoi@gmail.com", 2, 13);
        Customer costumer2 = new Customer(2, "Periklis", "Gousios", 231045216, "periklisgou@gmail.com", 4, 11);
        Customer costumer3 = new Customer(3, "Alex", "Pneumonidis", 231089216, "alexpneum@gmail.com", 3, 5);
        Customer costumer4 = new Customer(4, "Stratos", "Kountouras", 231061846, "stratosk@gmail.com", 1, 8);

        Customer[] CostumersModelsArray = {costumer1, costumer2, costumer3, costumer4};

        for (int i = 0; i < 4; i++) {
            MainActivity.appDb.collection("customers")
                    .document("" + CostumersModelsArray[i].getCid()).set(CostumersModelsArray[i])
                    .addOnCompleteListener((task) -> {})
                    .addOnFailureListener((e) -> {});
        }
    }

    // Deletes all customer documents from Firestore
    public static void deleteAllFirestoreData() {

        CollectionReference collectionReference = MainActivity.appDb.collection("costumers");
        collectionReference.get().addOnSuccessListener(queryDocumentSnapshots -> {

            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                Customer costumers = documentSnapshot.toObject(Customer.class);
                collectionReference.document(String.valueOf(costumers.getCid())).delete();
            }
        }).addOnFailureListener(e -> {
        });

    }
}
