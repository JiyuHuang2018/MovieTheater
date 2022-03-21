package Main;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class ReservationServiceTest {

	ReservationService r = new ReservationService();
	@Test
	void testFirstAvailable() {
		boolean[] rowBeforeIsSat = new boolean[20];
		Arrays.fill(rowBeforeIsSat, false);
		int[] currentSeat = {0};
		char[] currentRow = {'A'};
		ReservationService r = new ReservationService();
		int test = r.firstAvailableSeats(rowBeforeIsSat, currentSeat, currentRow);
		assertTrue(test==20);
	}

	@Test
	void testFirstAvailable1() {
		boolean[] rowBeforeIsSat = new boolean[20];
		Arrays.fill(rowBeforeIsSat, false);
		for(int i =0; i <5; i++) {
			rowBeforeIsSat[i] = true;
		}
		int[] currentSeat = {0};
		char[] currentRow = {'A'};
		ReservationService r = new ReservationService();
		int test = r.firstAvailableSeats(rowBeforeIsSat, currentSeat, currentRow);
		assertTrue(test==15);
	}
}
