package eu.lestard.snakefx.highscore;

public class HighScoreEntry implements Comparable<HighScoreEntry> {

	private int ranking;

	private String playername;

	private int points;

	public HighScoreEntry(){
	}

	public HighScoreEntry(int ranking, String playername, int points){
		this.ranking = ranking;
		this.playername = playername;
		this.points = points;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public String getPlayername() {
		return playername;
	}

	public void setPlayername(String playername) {
		this.playername = playername;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public int compareTo(HighScoreEntry o) {
		return Integer.compare(o.points, this.points);
	}

	@Override
	public String toString(){
		return "#" + ranking + ":" + playername + "->" + points + " points";
	}

}
