package org.mobileeye.ai.objects.impl.opencv;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DMatch;

public class RecognizableObject {

	private final String identifier;
	private final Mat image;
	private final MatOfKeyPoint keyPoints;
	private final Mat descriptors;

	// FIXME : delete this
	private final List<DMatch> goodMatches;
	
	public RecognizableObject(final String identifier, final Mat image,
			final MatOfKeyPoint keyPoints, final Mat descriptors) {
		this.identifier = identifier;
		this.image = image;
		this.keyPoints = keyPoints;
		this.descriptors = descriptors;
		this.goodMatches = new ArrayList<DMatch>();
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public Mat getImage() {
		return image;
	}
	
	public MatOfKeyPoint getKeyPoints() {
		return keyPoints;
	}
	
	public Mat getDescriptors() {
		return descriptors;
	}

	// FIXME : delete this method
	public void addGoodMatch(DMatch dMatch) {
		this.goodMatches.add(dMatch);
	}

	// FIXME : delete this method
	public void clearGoodMatches() {
		this.goodMatches.clear();
	}
	
	// FIXME : delete this method
	public List<DMatch> getGoodMatches() {
		return goodMatches;
	}
	
	// FIXME : delete this method
	public DMatch[] getGoodMatchesAsArray() {
		DMatch[] goodMatchesArray = new DMatch[goodMatches.size()];
		this.goodMatches.toArray(goodMatchesArray);
		return goodMatchesArray;
	}
}
