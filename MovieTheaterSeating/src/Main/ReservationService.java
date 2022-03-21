package Main;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class ReservationService {
	
	ReservationService(){
	}
	/**
	 * Takes in file name
	 * Parse the file 
	 * Output a vector of reservations
	 * @param filename
	 * @return Vector<Reservation
	 * @throws IOException
	 */
	public Vector<Reservation> readInputFile(String fName) throws IOException{
		Vector<Reservation> listOfReservation = new Vector<Reservation>();
		FileInputStream in = null;
		try {
			in = new FileInputStream(fName);
			Scanner scan = new Scanner(in);
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				if(!(line.isEmpty())) {
					String[] splitLine = line.trim().split("\\s+");
					Reservation r = new Reservation(splitLine[0],Integer.parseInt(splitLine[1]));
					listOfReservation.add(r);
				}
			}
			scan.close();
			}
		catch (IOException exception){
			exception.printStackTrace();
		}
		finally {
			in.close();
		}
		return listOfReservation;
	}
	/**
	 * Takes in a list of reservation
	 * Rearrange the list to be assigned to seats
	 * assigns the seat and return a new list with Locations fill out
	 * @param listOfReservation
	 * @returnVector<Reservation
	 */
	public Vector<Reservation> assignSeats(Vector<Reservation> listOfReservation) {
		final int numOfSeatPerRow = 20;
		Vector<Reservation> copyReservation= new Vector<Reservation>();
		Collections.sort(listOfReservation,new ReservationSortingComparator());
		boolean[] rowBeforeIsSat = new boolean[numOfSeatPerRow];
		Arrays.fill(rowBeforeIsSat, false);
		int[] currentSeat= {0};
		char[] currentRow = {'A'};
		do {
			int indexOfGuest;
			do {
			int availableSeats = firstAvailableSeats(rowBeforeIsSat,currentSeat,currentRow);
			indexOfGuest = getMaxSeats(availableSeats,listOfReservation);
				if(indexOfGuest == -1) {
					currentSeat[0] += availableSeats;
				}
			}while(indexOfGuest == -1);
			String seatsAssigned = assignSeat(indexOfGuest, currentSeat,rowBeforeIsSat,listOfReservation,currentRow);
			if(currentRow[0]>'J') {
				System.out.print("OUT OF RANGE");
			}
			Reservation assignedGuest = listOfReservation.remove(indexOfGuest);
			assignedGuest.setSeatLocation(seatsAssigned);
			copyReservation.add(assignedGuest);
		}while(!listOfReservation.isEmpty());
		return copyReservation;
	}
	/**
	 * Takes in These parameters and assigns seats and
	 * set new location of current seat
	 * Update the seat to be sat in
	 * @param indexOfGuest
	 * @param currentSeat
	 * @param rowBeforeIsSat
	 * @param listOfReservation
	 * @param currentRow
	 * @return String of assignSeat
	 */
	String assignSeat(int indexOfGuest,int[] currentSeat, boolean[] rowBeforeIsSat, Vector<Reservation> listOfReservation,char[] currentRow) {
		int numberOfGuest = listOfReservation.get(indexOfGuest).getNumSeat();
		String assignedSeat = currentRow[0] + String.valueOf(currentSeat[0]+1);
		rowBeforeIsSat[currentSeat[0]] = true;
		currentSeat[0] = currentSeat[0]+1;
		for(int i = 1; i<numberOfGuest; i++,currentSeat[0]++) {
			assignedSeat = assignedSeat+ ", "+ currentRow[0] + String.valueOf(currentSeat[0]+1);
			rowBeforeIsSat[currentSeat[0]] = true;
		}
		for(int i =0; i <3 && currentSeat[0]+i<20;i++) {
			rowBeforeIsSat[currentSeat[0]+i] = false;
		}
		currentSeat[0] = currentSeat[0] +3;
		return assignedSeat;
	}

	/**
	 * find the next amount of available seasts to be sat in
	 * @param rowBeforeIsSat
	 * @param currentSeat
	 * @param currentRow
	 * @return
	 */
	int firstAvailableSeats(boolean[] rowBeforeIsSat, int[] currentSeat, char[] currentRow) {
		int endOfAvailableSeats;
		if(currentSeat[0]>=20) {
			currentSeat[0] =0;
			currentRow[0]++;
		}
		while(rowBeforeIsSat[currentSeat[0]]) {
			rowBeforeIsSat[currentSeat[0]] = false;
			currentSeat[0]++;
			if(currentSeat[0]>=20) {
				currentSeat[0]=0;
				currentRow[0]++;
			}
		};
		endOfAvailableSeats = currentSeat[0];
		while(!rowBeforeIsSat[endOfAvailableSeats]) {
			endOfAvailableSeats++;
			if(endOfAvailableSeats>=20) {
				return 20 - currentSeat[0];
			}
		}
		return endOfAvailableSeats - currentSeat[0];
	}
	/**
	 * find the next biggest amount of groups that can be sat in the seats available
	 * @param seatToFill
	 * @param v
	 * @return
	 */
	int getMaxSeats(int seatToFill,Vector<Reservation> v) {
		int reIndex = -1;
		for(int i =0; i<v.size();i++) {
			if(v.get(i).getNumSeat()<=seatToFill) {
				return i;
			}
		}
		return reIndex;
	}
	static class ReservationSortingComparator implements Comparator<Reservation>{
		public int compare(Reservation r1, Reservation r2) {
			Integer a = Integer.valueOf(r1.getNumSeat());
			Integer b = Integer.valueOf(r2.getNumSeat());
			return b.compareTo(a);
		}
	}
	
	public void outPutFile(String fName, Vector<Reservation> room) throws IOException{
		PrintWriter fout = new PrintWriter(fName);
		for(int i =0; i< room.size();i++) {
			fout.println(room.get(i).toString()+"\n");
		}
		fout.close();
	}
}
