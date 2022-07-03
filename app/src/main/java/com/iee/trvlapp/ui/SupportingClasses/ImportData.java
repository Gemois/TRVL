package com.iee.trvlapp.ui.SupportingClasses;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.iee.trvlapp.FirestoreEntities.Costumers;
import com.iee.trvlapp.MainActivity;
import com.iee.trvlapp.roomEntities.CityHotels;
import com.iee.trvlapp.roomEntities.Offices;
import com.iee.trvlapp.roomEntities.Packages;
import com.iee.trvlapp.roomEntities.Tours;

public class ImportData {

    //This Method Prepopulates Room Entities

    public static void fillRoomDatabase() {

        // Office Entries

        Offices office1 = new Offices("Touristas", "Αντιγονιδων 21");
        Offices office2 = new Offices("DionTours", "Μητροπόλεως 98");
        Offices office3 = new Offices("Grecos.gr Travel", "Ναυάρχου Κουντουριώτου");
        Offices office4 = new Offices("Zorpidis", "Εγνατία 76");
        Offices office5 = new Offices("Prima Holidays", "Κλεάνθους 38");

        Offices[] officesModelsArray = {office1, office2, office3, office4, office5};

        // Tours Entries

        Tours tour1 = new Tours("London", "England", 5, "ByRoad");
        Tours tour2 = new Tours("Paris", "France", 8, "ByPlane");
        Tours tour3 = new Tours("Tokyo", "Japan", 6, "ByPlane");
        Tours tour4 = new Tours("Vienna", "Austria", 5, "ByRoad");
        Tours tour5 = new Tours("New York", "USA", 10, "Cruise");

        Tours[] toursModelsArray = {tour1, tour2, tour3, tour4, tour5};

        // Package Entries

        Packages package1 = new Packages(5, 4, "07/05/2022", 250.0);
        Packages package2 = new Packages(4, 3, "08/08/2022", 900.0);
        Packages package3 = new Packages(3, 2, "06/010/2022", 500.0);
        Packages package4 = new Packages(2, 1, "03/03/2022", 300.0);
        Packages package5 = new Packages(1, 5, "05/09/2022", 1400.0);

        Packages[] packagesModelsArray = {package1, package2, package3, package4, package5};


        // CityHotels Entries


        CityHotels cityHotels1 = new CityHotels("The Lucerne Hotel", "Gouverneur Street", 5, 5);
        CityHotels cityHotels2 = new CityHotels("Hotel Edison", "Wall Street.", 4, 5);
        CityHotels cityHotels3 = new CityHotels(" Ink 48 Hotel", "Pearl Street", 3, 5);

        CityHotels cityHotels4 = new CityHotels("Hotel Raphael", "Rue Oberkampf.", 5, 2);
        CityHotels cityHotels5 = new CityHotels("Ibis Styles ", "Rue Saint-Rustique.", 4, 2);
        CityHotels cityHotels6 = new CityHotels(" Citadines", "Avenue Montaigne.", 3, 2);

        CityHotels cityHotels7 = new CityHotels("Kaiserhof Wien", "Julius-Ficker-Straße", 5, 4);
        CityHotels cityHotels8 = new CityHotels("Hilton Vienna", "Fickeysstraße ", 4, 4);
        CityHotels cityHotels9 = new CityHotels("Andaz Vienna", "Blutgasse", 3, 4);

        CityHotels cityHotels10 = new CityHotels("London Marriott ", "Oxford Street", 5, 1);
        CityHotels cityHotels11 = new CityHotels("The Resident ", " Abbey Road ", 4, 1);
        CityHotels cityHotels12 = new CityHotels("The Gate", "Baker Street", 3, 1);

        CityHotels cityHotels13 = new CityHotels("Shibuya", "Kabukichō Ichiban-gai", 5, 3);
        CityHotels cityHotels14 = new CityHotels("Mitsui", "Godzilla Road", 4, 3);
        CityHotels cityHotels15 = new CityHotels("JR Kyushu", "Omoide Yokocho", 3, 3);

        CityHotels[] cityHotelsModelsArray = {cityHotels1, cityHotels2, cityHotels3, cityHotels4, cityHotels5, cityHotels6, cityHotels7, cityHotels8, cityHotels9, cityHotels10, cityHotels11, cityHotels12, cityHotels13, cityHotels14, cityHotels15};


        //Executes Insert Action For Each Entity

        for (int i = 0; i < 5; i++) {
            MainActivity.appDatabase.officesDao().addOffice(officesModelsArray[i]);
        }

        for (int j = 0; j < 5; j++) {
            MainActivity.appDatabase.toursDao().addTour(toursModelsArray[j]);
        }

        for (int k = 0; k < 5; k++) {
            MainActivity.appDatabase.packagesDao().addPackage(packagesModelsArray[k]);
        }

        for (int l = 0; l < 15; l++) {
            MainActivity.appDatabase.cityHotelsDao().addCityHotel(cityHotelsModelsArray[l]);
        }

    }

    //This Method Deletes Room Entities

    public static void deleteRoomData() {
        MainActivity.appDatabase.officesDao().deleteAllOffices();
        MainActivity.appDatabase.toursDao().deleteAllTours();
        MainActivity.appDatabase.packagesDao().deleteAllPackages();
        MainActivity.appDatabase.cityHotelsDao().deleteAllCityHotels();
    }

    //This Method Prepopulates Firestore Collection

    public static void fillFirestoreDatabase() {

        Costumers costumer1 = new Costumers(1, "Georgios", "Moisidis", 231439216, "giorgosmoi@gmail.com", 2, 13);
        Costumers costumer2 = new Costumers(2, "Periklis", "Gousios", 231045216, "periklisgou@gmail.com", 4, 11);
        Costumers costumer3 = new Costumers(3, "Alex", "Pneumonidis", 231089216, "alexpneum@gmail.com", 3, 5);
        Costumers costumer4 = new Costumers(4, "Stratos", "Kountouras", 231061846, "stratosk@gmail.com", 1, 8);

        Costumers[] CostumersModelsArray = {costumer1, costumer2, costumer3, costumer4};

        for (int i = 0; i < 4; i++) {

            MainActivity.appDb.collection("costumers")
                    .document("" + CostumersModelsArray[i].getCid())
                    .set(CostumersModelsArray[i])
                    .addOnCompleteListener((task) -> {
                    })
                    .addOnFailureListener((e) -> {
                    });
        }
    }

    //This Method Deletes Firestore Collection Documents

    public static void delteAllFirestoreData() {

        CollectionReference collectionReference = MainActivity.appDb
                .collection("costumers");
        collectionReference
                .get().addOnSuccessListener(queryDocumentSnapshots -> {

            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                Costumers costumers = documentSnapshot.toObject(Costumers.class);
                collectionReference.document(String.valueOf(costumers.getCid())).delete();
            }
        }).addOnFailureListener(e -> {
        });

    }

}
