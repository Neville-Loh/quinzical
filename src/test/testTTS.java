package test;

public class testTTS {
	
	public testTTS() {
		
		System.out.println("start");
		try {
			ProcessBuilder pb = new ProcessBuilder("espeak", "hello");
			pb.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("end");
	}

}
