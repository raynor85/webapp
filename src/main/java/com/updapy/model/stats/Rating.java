package com.updapy.model.stats;

public class Rating {

	private Double averageRating;

	private Long nbVotes;

	public Rating(Double averageRating, Long nbVotes) {
		this.averageRating = averageRating;
		this.nbVotes = nbVotes;
	}

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}

	public Long getNbVotes() {
		return nbVotes;
	}

	public void setNbVotes(Long nbVotes) {
		this.nbVotes = nbVotes;
	}

}
