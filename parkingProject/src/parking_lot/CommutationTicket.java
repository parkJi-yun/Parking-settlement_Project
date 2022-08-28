package parking_lot;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Map;
import java.util.Date;
import java.util.Calendar;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*; 
import java.text.*;

/*
 * 차량번호 중복제거 v
 * 정기권 단독 -> 데이터 저장 v
 * 남은 계약일수 및 재계약 유무 v
 * 지정석 조회 및 발급 △ -> 등록 및 입차에서는 완료(but 데이터 저장x) / 출차는 예정
 * 정기권 마감시 사후처리 v
 */

public class CommutationTicket {

	public static Calendar inDay1; // public static 사용 이유

	public static void CommuTicket() throws AuthenException {

		DAO dao = new DAO();

		ParkingDTO dto = new ParkingDTO();

		Scanner sc = new Scanner(System.in);

		// 정기권 등록 시작
		System.out.println("정기권을 선택하셨습니다. \n" + "정기권은 30일 단위이며, 자유롭게 입차 가능합니다.\n"
				+ "다만 기간 내에 연장 또는 기간 초과 후 출차해주지 않으시면 견인되오니 주의 부탁드립니다.");
		for (int i = 0; i < 60; i++) {
			System.out.print("-");
		}

		try {
			Thread.sleep(300); // 0.3초 대기
		} catch (InterruptedException e) {
		}

		// 정기권 남은 수량 조회
		System.out.println("\n정기권 수량 조회 중입니다.");

		try {
			Thread.sleep(300); // 0.3초 대기
		} catch (InterruptedException e) {
		}

		System.out.println("...");

		try {
			Thread.sleep(300); // 0.3초 대기
		} catch (InterruptedException e) {
		}

		// TotalParking Class에서 정기권 남은 수량 불러오기
		ParkingMain pm = new ParkingMain();
		if (dao.selectComm().size() == 10) {
			System.out.println("정기권이 마감되었습니다.");

			try {
				Thread.sleep(1500); // 1.5초 대기
			} catch (InterruptedException e) {
			}

			return; // main 화면으로 돌아가기

		} else {
			System.out.println("정기권이 " + (10 - dao.selectComm().size()) + "개 남았습니다.");
		}

		try {
			Thread.sleep(300); // 0.3초 대기
		} catch (InterruptedException e) {
		}

		System.out.println("정기권을 등록할 수 있습니다.");

		try {
			Thread.sleep(300); // 0.3초 대기
		} catch (InterruptedException e) {
		}

		System.out.println("주차 지정석 발급 후 회원정보를 입력해주세요!\n");

//		Menu menu = new Menu();
//		menu.In();

		try {
			Thread.sleep(300); // 0.3초 대기
		} catch (InterruptedException e) {
		}

		// 차량 번호 입력
		boolean a = true;
		while (a) {
			System.out.println("차량 번호를 입력해주세요!");
			String carNum = sc.nextLine();
			dto.setCarNumber(carNum);
				// 오타 방지
				boolean isCarNumber = Pattern.matches("^([0-9가-힣]{3,4})\\s?[0-9]{4}", dto.getCarNumber()); // 차량번호 확인 정규표현식
				if (!isCarNumber) {
					System.out.println("차량 번호 형식이 맞지 않습니다.");
					
					try {
						Thread.sleep(300); // 0.3초 대기
					} catch (InterruptedException e) {
					}
					
					System.out.println("다시 입력해 주세요.");
					continue;
				} else {
					// 중복 방지
					//CommutationTicket ct = new CommutationTicket();
					if(already(carNum)) {
						System.out.println("중복된 차량번호입니다.");
						
						try {
							Thread.sleep(300); // 0.3초 대기
						} catch (InterruptedException e) {
						}
						
						continue;
					} else {
						break;
					}
				}
		}
		
		// 차량 소유자 이름 입력
		System.out.println("차량 소유자 이름을 입력해주세요!");
		String name = sc.nextLine();
		dto.setName(name);
		
		// 연락처 입력
		System.out.println("연락처를 입력해주세요!");
		boolean b = true;
		while (b) {
			String phoneNum = sc.nextLine();
			dto.setPhoneNum(phoneNum);

			boolean isPhoneNum = Pattern.matches("^01(?:0|1|)(?:\\d{4})\\d{4}$", dto.getPhoneNum()); // 핸드폰 번호 확인 정규표현식
			if (!isPhoneNum) {
				System.out.println("핸드폰 번호 형식이 맞지 않습니다.");

				try {
					Thread.sleep(300); // 0.3초 대기
				} catch (InterruptedException e) {
				}

				System.out.println("다시 입력해 주세요.");
				continue;
			}
			break;
		}

		// 차종 입력
		System.out.println("차종을 입력해주세요!");

		Payment pa = new Payment();

		boolean c = true;
		while (c) {
			System.out.println("1. 경차 " + " / " + " 2. 일반");
			String kind = sc.nextLine();
			dto.setCarKinds(kind); // DB등록
			if (dto.getCarKinds().contains("경차") || dto.getCarKinds().contains("1")) {
				System.out.println("10%가 할인됩니다.");
				for (int i = 0; i < 15; i++) {
					System.out.print("-");
				}

				try {
					Thread.sleep(300); // 0.3초 대기
				} catch (InterruptedException e) {
				}

				System.out.println("\n감사합니다.\n" + "결제창으로 넘어갑니다.");

				dto.setWhatDate(1);
				pa.payment();

			} else if (dto.getCarKinds().contains("일반") || dto.getCarKinds().contains("2")) {
				for (int i = 0; i < 15; i++) {
					System.out.print("-");
				}

				try {
					Thread.sleep(300); // 0.3초 대기
				} catch (InterruptedException e) {
				}

				System.out.println("\n감사합니다.\n" + "결제창으로 넘어갑니다.");

				dto.setWhatDate(1);
				pa.payment();
				
				inday(); // 입차날짜 저장
				

			} else {
				System.out.println("다시 입력해주세요!");
				continue;
			}
			break;
		}

		// DB에 저장되게 하는 메소드
		 dao.insertCommuTicket(dto.getCarNumber(), dto.getCarKinds(), dto.getName(), dto.getPhoneNum(), dto.getRegistration_date(), dto.getTerm());
	}
	
	// 차량번호 중복제거 매소드
	public static boolean already(String Car_Num) {
		DAO dao = new DAO();
		return dao.selectComm().contains(Car_Num);
	}
	
//-------------------------------------------------------------------------------------------------------------------------------

	public static void Term_Dday() throws AuthenException {
		ParkingDTO dto = new ParkingDTO();
		
		Calendar inDay1 = Calendar.getInstance();
		CommutationTicket.printDateTime(inDay1);
		
		
		Calendar outDay = Calendar.getInstance();
		
		outDay.add(Calendar.DAY_OF_MONTH, 1);
		printDateTime(outDay);
		
		long outDayL = outDay.getTimeInMillis() / (24*60*60*1000);
		long inDayL = inDay1.getTimeInMillis() / (24*60*60*1000);
		
		long term = outDayL - inDayL;
		dto.setTerm((int)term);
		System.out.println(dto.getTerm() + "일 남았습니다.");
		
	}		
	
	public static void Term_Dday1() throws AuthenException {
		Scanner sc = new Scanner(System.in);
		Payment pa = new Payment();
		ParkingDTO dto = new ParkingDTO();
		DAO dao = new DAO();
		
		if(dto.getTerm() != 0) {
			System.out.println("당신의 남은 계약일수는 " + dto.getTerm() + " 일 남았습니다.");
			
			try {
				Thread.sleep(300); // 0.3초 대기
			} catch (InterruptedException e) {}
			
			System.out.println("연장하시겠습니까?");	
			
			try {
				Thread.sleep(300); // 0.3초 대기
			} catch (InterruptedException e) {}
			
			System.out.println("1. 연장" + " / " + "2. 미연장");
			String reContract = sc.next();
			
			boolean z = true;
			while(z) {
				if(reContract.contains("1") || reContract.contains("연장")) {
					System.out.println("연장하셨습니다. \n" + "결제 창으로 넘어갑니다. \n" + "...");
					
					dto.setWhatDate(1);
					pa.payment();
					
					inday();
	  				System.out.println(dto.getRegistration_date());
					
				} else if(reContract.contains("2") || reContract.contains("미연장")) {
					System.out.println("기간 내 출차바랍니다.");
				}
				break;
			}
		} else if (dto.getTerm() == 0) {
			System.out.println("기간이 지났습니다."+ " / " + dto.getTerm() + "일");
			
			try {
				Thread.sleep(300); // 0.3초 대기
			} catch (InterruptedException e) {
			}
			
//			TotalParking.monthly.remove(dto.getCarNumber()); // 차량 번호 삭제
//			TotalParking.monthly.remove(dto.getName()); // 차주 이름 삭제
//			TotalParking.monthly.remove(dto.getPhoneNum()); // 차주 번호 삭제
//			TotalParking.monthly.remove(dto.getCarKinds()); // 차량 종류 삭제
			
			dao.deleteComm(dto.getCarKinds());
			
			
			System.out.println("기간이 지났는데도 연장하지 않고 출차하지 않아 견인 조치 되었음을 알려드립니다.\n" + "당신의 차는 현재 중고차로 해외에 수출되었습니다.");
		}
	}

	
//	public static void inday() {
//		inDay = Calendar.getInstance();
//		CommutationTicket.printDateTime(inDay);
//	}
	
//	public static Calendar inday() {
//		Calendar inDay = Calendar.getInstance();
//		CommutationTicket.printDateTime(inDay);
//		return inDay;
//	}
	
	public static void inday() {
		ParkingDTO dto = new ParkingDTO();
		inDay1 = Calendar.getInstance();
		//CommutationTicket.printDateTime(inDay);
		toString(inDay1);
		dto.setRegistration_date(toString(inDay1));
		//TotalParking.monthly.add(dto.getRegistration_date());
		//System.out.println(toString(inDay1));
	}
	
	public static String toString(Calendar inDay) {
		return inDay.get(Calendar.YEAR)+ "년" + (inDay.get(Calendar.MONTH)+1) + "월 " + inDay.get(Calendar.DATE) + "일 " ; 
	}
	
	public static void printDateTime(Calendar cal) {
		
		int yy = cal.get(Calendar.YEAR);
		int mm = cal.get(Calendar.MONTH) + 1;
		int dd = cal.get(Calendar.DAY_OF_MONTH);
		
	}
	
	
}
