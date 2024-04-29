package android.example.gomscheduler;
//used to make dummy content for travel and accomodation for each different day
public class Day {
    String name;
    String accom;
    String shortAccom;

    String travel;
    String shortTravel;

    public Day(String date){

        name = date;
        shortAccom = "Accom on:\n" + date;

        shortTravel = "Travel on:\n" + date;

        StringBuilder accomBuilder = new StringBuilder();
        StringBuilder travelBuilder = new StringBuilder();

        accomBuilder.append(shortAccom);
        travelBuilder.append(shortTravel);
        for (int i = 0; i<4 ; i++){
            accomBuilder.append("\nextended");
            travelBuilder.append("\nextended");
        }
        accom = accomBuilder.toString();
        travel = travelBuilder.toString();
    }

}
