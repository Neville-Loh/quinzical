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
		
		
		// user select a model
		model.selectRandomPracticeQuestion(6);
		model.answerPracticeQuestion("the answer for the user");
		
		
		PracticeQuestion pq = model.getCurrentPracticeQuestion();
		
		
		
		
		
		// user answer a question 
		
		
		// user answer a 3 times
		
		
		// 
		
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
