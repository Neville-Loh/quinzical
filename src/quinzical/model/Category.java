package quinzical.model;

import java.util.ArrayList;

/**
 * Class to represent the organization of the category, which category contains
 * multiple questions, for example 5 question per category.
 * 
 * @author Neville
 */
public class Category {
	private ArrayList<Question> _questions;
	private String _title;
	private int catID;
	
	public Category(String title, int id) {
		_title = title;
		_questions = new ArrayList<Question>();
		catID = id;
	}
	
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
	 * @return array list containing all quesiton in the category
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
}
