package quinzical.util;

/**
 * Text to speech interface use as adapter to different text to speech 
 * implementation.
 * @author Neville
 *
 */
public interface TextToSpeech {
	
	/**
	 * Starts the text to speech process which run in background
	 */

	public void start(String text);

	
	/**
	 * Stop the text to speech by force
	 */
	public void stop();
	
	/**
	 * Set the volume of the current text to speech
	 */
	public void setVolume(int volume);

	
	/**
	 * Set the speed of the curren text to speech
	 */
	public void setSpeed(int speed);

}
