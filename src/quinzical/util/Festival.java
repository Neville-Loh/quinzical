package quinzical.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Festival implements TextToSpeech {
	
	private int _volume;
	private Process _currentProcess;
	
	public Festival() {
		setVoice();
	}
	
	public void start(String text) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String command = "echo \"" + text + "\" | festival --tts";
				runTerminal(command, true);
			}

		}).start();
	}
	
	public void stop() {
		_currentProcess.destroy();
	}
	
	private void setVoice() {
		runTerminal("festival", false);
		runTerminal("(voice_akl_nz_jdt_diphone)", false);
		runTerminal("(quit)", false);
	}
	
	public void setVolume(int volume) {
		_volume = volume;
	}
	
	/**
	 * This method changes the speed of the speech synthesis of festival.
	 * @param speed is the requested speed expressed as a percentage of the default speed.
	 * For example to set the speed as the default speed the input would be 100 
	 * corresponding to 100%
	 * 
	 */
	public void setSpeed(int speed) {
		double duration = 1 / ( (double) speed / 100);
		runTerminal("festival", false);
		String command = "(Parameter.set 'Duration_Stretch " + Double.toString(duration) + ")";
		runTerminal(command, false);
		runTerminal("(quit)", false);
	}
	
	
	/**
	 * Run a bash command using process builder
	 * @param command
	 */
	private void runTerminal(String command, boolean bash) {
		try {
			ProcessBuilder pb;
			if (bash) {
				pb = new ProcessBuilder("bash", "-c", command);
			} else {
				pb = new ProcessBuilder(command);
			}
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
