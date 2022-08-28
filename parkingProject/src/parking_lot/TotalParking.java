package parking_lot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TotalParking {
/*	static ArrayList<String> daily = new ArrayList<>();		// 1회권
	static ArrayList<String> monthly = new ArrayList<>();	// 정기권
	static ArrayList<String> parking = new ArrayList<>();	// 총주차
	
			
	public static int total() {
		
		DAO dao = new DAO();
		int a = (10-dao.selectComm().size());
		return a;
	}
	public static int total2() {
		int a = (20 - daily.size());
		return a;
	}
	// 현재 주차현황을 보여주는 메서드
	public static void TotalParking(){
		parking.addAll(daily);
		parking.addAll(monthly);
		System.out.println("현재 남은자리");
		System.out.println("정기권 : " + total() + "\t" + "일회권 : " + total2());
		if (parking.size() == 30) {
			System.out.println("죄송합니다. 현재 만차입니다. 다음에 이용해주세요");
		} else {
			
		}
	}*/
	
	public static int comm() {
		DAO dao = new DAO();
		int a = (10-dao.selectComm().size());
		
		return a;
	}
	
	public static int oneTime() {
		DAO dao = new DAO();
		int a = (20-dao.selectOnetime().size());
		
		return a;
	}
	
	
	public static void TotalParking() {
		System.out.println("현재 남은 자리");
		System.out.println("정기권 : " + comm() + "	일회권 : " + oneTime());
		
	}
	
	
}
