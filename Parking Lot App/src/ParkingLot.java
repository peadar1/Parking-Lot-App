import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    String parkingLotId;
    List<List<Slot>> slots;

    public ParkingLot(String parkingLotId, int nfloors, int noOfSlotsPerFlr) {
        this.parkingLotId = parkingLotId;

        slots = new ArrayList<>();  // outer list of carpark floors
        for (int i = 0; i < nfloors; i++) {
            slots.add(new ArrayList<>()); // add empty list to hold Slot objects
            List<Slot> floorSlots = slots.get(i); // references the list just added

            floorSlots.add(new Slot("truck"));
            floorSlots.add(new Slot("bike"));       // Add 1 truck and 2 bike slots to each floor
            floorSlots.add(new Slot("bike"));

            for (int j = 3; j<noOfSlotsPerFlr; j++) {
                slots.get(i).add(new Slot("car"));      // Populate rest of slots with a car slot
            }
        }

    }

    public String parkVehicle(String type, String regNo, String color) {
        Vehicle vehicle = new Vehicle(type, regNo, color);  // create new vehicle with given info

        for (int i = 0; i < slots.size(); i++) {    
            for(int j = 0; j < slots.get(i).size(); j++) {
                Slot slot = slots.get(i).get(j);
                if (slot.type==type && slot.vehicle==null) {
                    slot.vehicle = vehicle;
                    slot.ticketId = generateTicketId(i+1, j+1);
                    return slot.ticketId;
                }
            }
        }
        System.out.println("No slot available for given type");
        return null;
    }

    private String generateTicketId(int floor, int slotNo) {
        return parkingLotId + "_" + floor + "_" + slotNo;
    }
    public void unparkVehicle(String ticketId) {
        try {
            String[] extract = ticketId.split("_");
            int floor_index = Integer.parseInt(extract[1])-1;
            int slot_index = Integer.parseInt(extract[2])-1;

        for (int i = 0; i < slots.size(); i++) {    
            for(int j = 0; j < slots.get(i).size(); j++) {
                if (i==floor_index && j==slot_index) {
                    Slot slot = slots.get(i).get(j);
                    slot.vehicle =null;
                    slot.ticketId =null;
                    System.out.println("Unparked Vehicle");
                }    

                }
            }
        } catch(Exception e) {
            System.out.println("Invalid Ticket Id");
        }
    
    
    }

    public int getNoOfEmptySlots(String type) {
        int counter = 0;
        for (int i = 0; i < slots.size(); i++) {    
            for(int j = 0; j < slots.get(i).size(); j++) {
                Slot slot = slots.get(i).get(j);
                if(slot.type == type && slot.vehicle == null) {
                    counter++;
                }

                }
            }
        return counter;        
    }

    public static void main(String[] args) {
        int nfloors=4;
        int noOfSlotsPerFlr=6;
        ParkingLot parkingLot = new ParkingLot("PR1234", nfloors, noOfSlotsPerFlr);

        System.out.println(parkingLot.getNoOfEmptySlots("car"));
    }

}


