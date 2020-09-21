package quinzical.util;

public interface TextToSpeech {
	
	/**
	 * Starts the text to speech process which run in background
	 */
	public void start();
	
	/**
	 * Stop the text to speech by force
	 */
	public void stop();
	
	/**
	 * Set the volume of the current text to speech
	 */
	public void setVolume();
	
	/**
	 * Set the speed of the curren text to speech
	 */
	public void setSpeed();
}
