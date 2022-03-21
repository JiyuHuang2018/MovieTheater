package Main;
import java.io.IOException;
import java.util.Vector;
public class Main {
	public static void main(String args[]) throws IOException {
		ReservationService r = new ReservationService();
		Vector<Reservation> listOfReservation ;
		listOfReservation = r.readInputFile("reservation.txt");
		listOfReservation = r.assignSeats(listOfReservation);
		r.outPutFile("C:\\Users\\huang\\Desktop\\out.txt",listOfReservation);
	}
}
