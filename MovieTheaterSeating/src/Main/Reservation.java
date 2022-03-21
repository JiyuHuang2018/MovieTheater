package Main;

public class Reservation {
	private String reservationID;
	private int numSeat;
	private String seatLocation;
	
	Reservation(String givenReservationID, int givenNumSeat){
		reservationID = givenReservationID;
		numSeat = givenNumSeat;
		seatLocation = "";
	}
	
	public String toString() { 					//change formatting
		String rID = reservationID;
		String seatL = seatLocation;
		
		if(reservationID.isEmpty()){
			rID = "NOID";
		}
		if(seatLocation.isEmpty()) {
			seatL = "NO LOCATION";
		}
		return rID+" "+seatL;
	}
	public String getResID() {
		return reservationID;
	}
	public int getNumSeat() {
		return numSeat;
	}
	public void setSeatLocation(String s) {
		seatLocation = s;
	}
	public String getSeatLocation() {
		return seatLocation;
	}
}