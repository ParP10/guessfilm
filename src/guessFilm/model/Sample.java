package guessFilm.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "samples")
public class Sample {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name = "filmId")
	int filmId;
	
	@Column(name = "questionId")
	int questionId;
	
	@Column(name = "answer")
	int answer;
	/**
	 * @return the filmId
	 */
	public int getFilmId() {
		return filmId;
	}
	/**
	 * @param filmId the filmId to set
	 */
	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}
	/**
	 * @return the questionId
	 */
	public int getQuestionId() {
		return questionId;
	}
	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	/**
	 * @return the answer
	 */
	public int getAnswer() {
		return answer;
	}
	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(int answer) {
		this.answer = answer;
	}

}
