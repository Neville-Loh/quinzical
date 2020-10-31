package test;

import java.util.List;

import quinzical.model.Category;
import quinzical.model.Question;
import quinzical.model.QuizModel;
import quinzical.model.Session;

/**
 * Integrated test for Class session
 * This class is for development purposes, unit test has to be done seperated from this class
 * 
 * @author Neville
 *
 */
public class testSession {
	/**
	 * Method to repaidly test session system
	 * @param session
	 */
	public static void completeSession(Session session) {
		List<Category> cats = session.getCategoryList();
		int i = 0;
		for (Category cat: cats) {
			for (Question question: cat.getQuestions()) {
				question.setAttempted(true);
				if (i == 4 && question.getScore() == 500) {
					question.setAttempted(false);
				}
			}
			i++;
		}
		for (Question question: session.getHiddenCategory().getQuestions()) {
			question.setAttempted(true);
		}
		session.setRemainingQuestion(1);
		session.setIsFinished(true);
		session.addWinnings((int) (Math.random() * 5000));
	}
}
