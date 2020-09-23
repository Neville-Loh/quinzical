package quinzical.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Festival implements TextToSpeech {
	
	private int _volume;
	private int _speed;
	private Process _currentProcess;
	
	public Festival() {
		
	}
	
	public void start(String text) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String command = "echo \"" + text + "\" | festival --tts";
				runBash(command);
			}

		}).start();
	}
	
	public void stop() {
		_currentProcess.destroy();
	}
	
	public void setVolume(int volume) {
		_volume = volume;
	}
	
	public void setSpeed(int speed) {
		_speed = speed;
	}
	
	/**
	 * Run a bash command using process builder
	 * @param command
	 */
	private void runBash(String command) {
		try {
			ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
			Process process = pb.start();
			_currentProcess = process;
			BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			int exitStatus = process.waitFor();
			if (exitStatus == 0) {
				String line;
				while ((line = stdout.readLine()) != null) {
					System.out.println(line);
				}
			} else {
				String line;
				while ((line = stderr.readLine()) != null) {
					System.err.println(line);
				}
			}
			process.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
