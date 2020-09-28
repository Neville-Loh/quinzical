package test;

import quinzical.model.PracticeQuestion;
import quinzical.model.QuizModel;

public class testQuizModel {
	QuizModel model;
	
	public testQuizModel(){
		model = new QuizModel();{
			
		}
	}
	
	
	private void testPracticeMode() {
		
		
		// User select a model, button will contain cat_id
		int categoryId = 6;
		model.selectRandomPracticeQuestion(categoryId);
		
		boolean isCorrect;
		isCorrect = model.answerPracticeQuestion("the answer for the user");
		isCorrect = model.answerPracticeQuestion("the answer for the user");
		isCorrect = model.answerPracticeQuestion("the answer for the user");
		
		
		if (isCorrect) {
			// Bring user to congratulation screen
		}
		
		
		// User get it wrong 3 times
		PracticeQuestion pq = model.getCurrentPracticeQuestion();
		if (pq.getAttemptLeft() == 0) {
			// bring to answer and result screen
			System.out.printf("You used all attempts, The answer is actually %s%n", pq.getAnswer());
		}
		
	}
	
	
	
	
	private void testGameMode() {
		
		// set
		int userid  = model.createNewUser("Default User");
		model.setUser(userid);
		model.loadUserSession();
		
		
		// game mode start
		model.createNewSession();
		int qid = 14;
		model.setActiveQuestion(qid);
		boolean result = model.answerQuestion(qid, "someinput");
		System.out.println("The input is :" + result +
				"Winning after answered a question:" +model.getWinning());
		
		
		
		
		
		
		
		
		// save session
		
		model.saveUserSession();
		
		
		
		// load session
		
		
		
		
		
		//  
		

	}
}
