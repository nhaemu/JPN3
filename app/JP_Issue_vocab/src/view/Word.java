package view;

import java.util.ArrayList;
import java.util.List;

public class Word {

	private String word;
	private List<String> value = new ArrayList<String>();
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public List<String> getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value.add(value);
	}
	
}
