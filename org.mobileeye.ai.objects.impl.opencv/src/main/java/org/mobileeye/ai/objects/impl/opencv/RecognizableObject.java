/**
 * Copyright (c) Aakash Polra. 2013.
 * 
 * Licensed under The Non-Profit Open Software License version 3.0 (NPOSL-3.0).
 * Full license terms are available on the Open Source Initiative website.
 * http://opensource.org/licenses/NPOSL-3.0
 */
package org.mobileeye.ai.objects.impl.opencv;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DMatch;

/**
 * Represents an object used for performing recognition.
 * 
 * @author Aakash Polra
 * 
 * @see ObjectRecognitionImpl
 */
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
		return this.identifier;
	}
	
	public Mat getImage() {
		return this.image;
	}
	
	public MatOfKeyPoint getKeyPoints() {
		return this.keyPoints;
	}
	
	public Mat getDescriptors() {
		return this.descriptors;
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
