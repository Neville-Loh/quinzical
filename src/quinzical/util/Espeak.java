package quinzical.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import javafx.concurrent.Task;

public class Espeak implements TextToSpeech {
	
	private int _volume = 100;
	private int _speed = 175;
	private Process _currentProcess;
	
	public Espeak() {
	}
	
	public void start(String text) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				runTerminal(text);
			}

		}).start();
	}
	
	/*
	public void start(String text) {
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws Exception {
				try {
					ProcessBuilder pb = new ProcessBuilder("espeak", "hello");
					Process process = pb.start();
					process.waitFor();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		};
		task.setOnSucceeded(event -> {
			System.out.println("success");
		});
		
		new Thread(task).run();
	}
	*/	
	 
	
	public void stop() {
		/*Stream<ProcessHandle> descendants = ProcessHandle.current().descendants();
		descendants.filter(ProcessHandle::isAlive).forEach(ph -> {
			ph.destroy();
		});*/
		_currentProcess.destroy();
	}
	
	/**
	 * This sets the volume of speech synthesis. Input must be an integer between 0 and 100.
	 * Default is 50.
	 * Throws IllegalArgumentException
	 */
	public void setVolume(int volume) throws IllegalArgumentException {
		if (volume < 0 || volume > 100) {
			throw new IllegalArgumentException();
		}
		
		_volume = volume * 2;
	}
	
	/**
	 * This method changes the speed of the speech synthesis of festival.
	 * @param speed is an integer between 80 and 450 measured in words per minute.
	 * Default is 175. 
	 * 
	 */
	public void setSpeed(int speed) throws IllegalArgumentException {
		if (speed < 80 || speed > 450) {
			throw new IllegalArgumentException();
		}
		_speed = speed;
	}
	
	
	/**
	 * Run a bash command using process builder
	 * @param command
	 */
	private void runTerminal(String text) {
		try {
			ProcessBuilder pb;
			pb = new ProcessBuilder("espeak", "-s", String.valueOf(_speed), "-a", String.valueOf(_volume), text);
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