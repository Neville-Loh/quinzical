package quinzical.model;

import java.util.ArrayList;

/**
 * Class to represent the organization of the category, which category contains
 * multiple questions, for example 5 questions per category.
 * 
 * @author Neville, Daniel
 */
public class Category {
	private ArrayList<Question> _questions;
	private String _title;
	private int catID = -1;
	
	public Category(String title) {
		_title = title;
		_questions = new ArrayList<Question>();
	}
	
	/**
	 * Add a question to a category 
	 * @param question
	 */
	public void add(Question q) {
	_questions.add(q);
	}
	
	/**
	 * Gets the Title of the category
	 * @return Title of the category
	 */
	public String getTitle() {
		return _title;
	}
	
	/**
	 * Get the question from a list of question
	 * @param index i
	 * @return ith question in the list.
	 */
	public Question get(int i) {
		return _questions.get(i);
	}
	
	/**
	 * Get Method
	 * @return array list containing all question in the category
	 */
	public ArrayList<Question> getQuestions(){
		return _questions;
	}
	/**
	 * Get Method
	 * @return return the total number of question in the category
	 */
	public int getQuestionCount() {
		return _questions.size();
	}
	
	/**
	 * Checks if all the questions in the category have been answered.
	 * returns true if all questions have been attempted, false otherwise
	 * @return
	 */
	public boolean isComplete() {
		for (Question question: _questions) {
			if (!question.isAttempted()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Get the id of the provided category
	 * @return the id of the current category
	 */
	public int getCategoryID() {
		return catID;
	}
	
	/**
	 * Set method for ID,
	 * CAUTION!, this method only meant to be called by database implementation and
	 * not Anywhere of the application, throw illegalArgumentException if id already exist.
	 * @param id
	 */
	public void setID(int id) {
		if (catID == -1) {
			catID = id;
		} else {
			throw new IllegalArgumentException("Id already exist for question: " + _title);
		}
	}
}
