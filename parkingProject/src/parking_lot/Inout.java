package parking_lot;

import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Inout {
	static ParkingDTO dto = new ParkingDTO();
	static TotalParking tp = new TotalParking();

	public static void Enterance() throws AuthenException{
//      ArrayList<Member> list = new ArrayList<Member>();
		Scanner sc = new Scanner(System.in);
		LocalTime now = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		String formatedNow = now.format(formatter);
		DAO dao = new DAO();
		
		while (true) {
			System.out.println("차량 번호를 입력하세요");
			dto.setCarNumber(sc.nextLine());
			boolean isNum = Pattern.matches("^([0-9가-힣]{3,4})\\s?[0-9]{4}", dto.getCarNumber()); // 차량번호 확인 정규표현식

//         for (int i = 0; i < totalP.parking.size(); i++) {
//            if (carNum.equals(list.get(i).getName())) {
//               System.out.println("차 번호 : " + list.toString());
//            }
//            
//         }
			if (!isNum) {
				System.out.println("등록되지 않은 차량입니다.");
			} else {
				System.out.println("1. 정기권(20만원)" + "\t" + "2. 일회권(30분에 5000원, 이후 10분 단위로 1000원씩 부과됩니다)");
				String num = sc.next();
				
				switch (num) {
				case "정기권":
					dto.setWhatDate(1);
					if(isNum || dao.selectComm().contains(dto.getCarNumber())) {
						System.out.println("등록된 차량입니다.");
			            System.out.println("입차완료"); 
						dao.insertEntrance(dto.getCarNumber(), dto.getCarKinds(), dto.getEntranceTime());
			        } 
					break;
				case "일회권":
					dto.setWhatDate(2);
					System.out.println("차종을 입력해주세요(경차\t일반)");
					//String kind = sc.next();
					dto.setCarKinds(sc.next());
					if (dto.getCarKinds().equals("경차")) {
						//TotalParking.daily.add(dto.getCarNumber());
						//dao.toString();
						dto.setEntranceTime(now.format(formatter));
						// db 연동 소스
					} else if (dto.getCarKinds().equals("일반")) {
//						Payment.pay_daily();
						dto.setEntranceTime(now.format(formatter));
					} else {
						return;
					}
					dao.insertEntrance(dto.getCarNumber(), dto.getCarKinds(),dto.getEntranceTime());
					dao.insertOneTime(dto.getCarNumber());
					return;
				}
			}
			break;
		}

	}

	public static void Exit() throws AuthenException{
		Scanner sc = new Scanner(System.in);
		CommutationTicket ct = new CommutationTicket();
		Payment pay = new Payment();
		//ParkingDTO dto = new ParkingDTO();
		DAO dao = new DAO();
		while (true) {
			System.out.println("출차를 진행합니다.");
			System.out.println("차량 번호를 입력하세요");
			dto.setCarNumber(sc.next());
			
			boolean isNum = Pattern.matches("^([0-9가-힣]{3,4})\\s?[0-9]{4}", dto.getCarNumber());	// 차량번호 확인 정규표현식

			if (!isNum) {
				System.out.println("잘못된 차량번호입니다.");
				continue;
			} else {
				if( dao.selectTotalParking().contains(dto.getCarNumber()) || dao.selectComm().contains(dto.getCarNumber())) {
					// 해당 차량번호 정기권 남은 날짜 출력
					CommutationTicket.Term_Dday1();
					int result = dao.deleteExit(dto.getCarNumber());
					// 해당 차량 출차후 parking, monthly 리스트에서 해당번호 삭제
					dao.deleteComm(dto.getCarNumber());
					
				} else {
					// 해당 차량 시간계산 후 요금지불
					String time = (dao.time().get(dto.getCarNumber()));
					String[] time_sp = time.split("\\s");
					String get_time = time_sp[1];
					System.out.println("입차 시간 : " + get_time);
					
					LocalTime now = LocalTime.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
					String formatedNow = now.format(formatter);
					
					SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
					try {
						Date d1 = f.parse(get_time);
						Date d2 = f.parse(formatedNow);
						
						System.out.println("출차 시간 : " + formatedNow);
						
						long diff = d2.getTime()-d1.getTime();
						dto.setMin((diff/1000)/60);
						
						System.out.println(dto.getMin() + "분");
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					pay.payment();
					
					int result = dao.deleteExit(dto.getCarNumber());
					// 해당 차량 출차 후 parking daily 리스트에서 해당번호 삭제
					dao.deleteOneTime(dto.getCarNumber());
				}
					
			}
			break;
			
			
		}
	}
}