/*
 * if type = 1, then number card
 * if type = 2, then action card
 * if type = 3, then wild card
 * 
 */
public class UNO_Card {

	private String color = null;
	private int value = 0;
	private int type = 0;
	private String action = null;
	private int point = 0;
	
	public UNO_Card(String color, int value, int type, String action) {
		this.color = color;
		this.value = value;
		this.type = type;
		this.action = action;
	}
	
	public String getColor() {
		return color;
	}
	
	public int getValue(){
		return value;
	}
	
	public int getType() {
		return type;
	}
	
	public String getAction() {
		return action;
	}
	
	public int getPoint() {
		return point;
	}
	
	public void setPoint(int point) {
		this.point = point;
	}
}
