package feedback.service;

import org.springframework.stereotype.Service;

@Service
public class FeedbackPyService {	
	// 하단 런타임 내용 Flask로 대체 예정
//	public boolean runtime(String fileName) {
//		String path = System.getProperty("user.dir");
//		System.out.println("Working Directory = " + path);
//		String command = "cmd.exe /c start " + path + "\\py\\" + fileName + ".py";
//		String[] cmdArr = {"cmd.exe", "/c", "start", path + "\\py\\" + fileName + ".py"};
//		System.out.println(command);
//		Process pr = null;
//		try {
////			pr = Runtime.getRuntime().exec(command);
//			pr = Runtime.getRuntime().exec(cmdArr);
//			try {
//				pr.waitFor();
//				System.out.println("다했음돠");
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			pr.destroy();
//		}		
//		return true;
//	}
}
