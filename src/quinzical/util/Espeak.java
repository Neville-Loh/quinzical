package quinzical.util;


/**
 * Espeak Implementation of the text to speech system
 * @author Daniel
 *
 */
public class Espeak implements TextToSpeech {
	
	private int _volume = 30;
	private int _speed = 30;
	private Process _currentProcess;
	
	/**
	 * default constructor
	 */
	public Espeak() {
	}
	
	/**
	 * Start playing the speech synthesis for the given string
	 */
	public void start(String text) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// kill the speech if text to speech is in progress
				if (_currentProcess != null) {
					_currentProcess.destroy();
				}
				runTerminal(text);
			}

		}).start();
	}
	 
	/**
	 * Stop the text to speech if it is still playing
	 */
	public void stop() {
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
		
		_volume = volume;
	}
	
	/**
	 * This method changes the speed of the speech synthesis of festival.
	 * @param speed is an integer between 0 and 100
	 * Default is 50. 
	 * 
	 */
	public void setSpeed(int speed) throws IllegalArgumentException {
		if (speed < 0 || speed > 100) {
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
			pb = new ProcessBuilder("espeak", "-s", String.valueOf(_speed * 2 + 80), "-a", String.valueOf(_volume * 2), text);
			Process process = pb.start();
			_currentProcess = process;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the current volume 
	 */
	@Override
	public int getVolume() {
		return _volume;
	}

	/**
	 * Get the current speed
	 */
	@Override
	public int getSpeed() {
		return _speed;
	}

}
