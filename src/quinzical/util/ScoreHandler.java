package quinzical.util;
/**
 * Utility class to handel user title and score
 * @author Neville
 *
 *
 * 0-999 novice
* 1000-1999 not bad
* 2000-2999 cool
* 3000-3999 awesome
* 4000-4999 excellent
* 5000-5999 epic
* 6000-6999 amazing
* 7000-7999 badass
* 8000-8999 legendary
* 9000 flawless
 */
public class ScoreHandler {

	public static String geTitle(int score) {
		if (score < 1000) {
			return "novice";
		} else if ( score < 2000 ){
			return "not bad";
		} else if ( score < 3000 ){
			return "cool";
		} else if ( score < 4000 ){
			return "awesome";
		} else if ( score < 5000){
			return "excellent";
		} else if ( score < 6000 ){
			return "epic";
		} else if ( score < 7000 ){
			return "amazing";
		} else if ( score < 8000 ){
			return "badass";
		} else if ( score < 9000 ){
			return "legendary";
		} else {
			return "flawless";
		}
	}
}
