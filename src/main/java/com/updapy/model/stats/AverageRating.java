package com.updapy.model.stats;

import java.text.DecimalFormat;

public class AverageRating {

	private Double score;

	private Long nbVotes;

	public AverageRating(Double score, Long nbVotes) {
		this.score = score;
		this.nbVotes = nbVotes;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Long getNbVotes() {
		return nbVotes;
	}

	public void setNbVotes(Long nbVotes) {
		this.nbVotes = nbVotes;
	}

	public Double getScoreRoundedToHalf() {
		if (score == null) {
			return null;
		}
		return 0.5 * Math.round(score * 2);
	}

	public Double getScoreRounded() {
		if (score == null) {
			return null;
		}
		return Double.valueOf(new DecimalFormat("#.#").format(score));
	}

}
