/**
 * Copyright (c) Aakash Polra. 2013.
 * 
 * Licensed under The Non-Profit Open Software License version 3.0 (NPOSL-3.0).
 * Full license terms are available on the Open Source Initiative website.
 * http://opensource.org/licenses/NPOSL-3.0
 */
package org.mobileeye.ai.objects;

import java.util.List;

/**
 * 
 * @author Aakash Polra
 *
 * @param <S> data type of the Scene where object recognition is performed (Image, for example)
 * @param <O> data type of the objects that are recognized (Image, for example)
 */
public interface ObjectRecognition<S,O> {
	
	/**
	 * Adds the object to the list of recognizable objects
	 * @param recognizableObject	a known object to be added for recognition
	 */
	void addRecognizableObject(O recognizableObject);
	
	/**
	 * 
	 * @param scene					the scene where object recognition is performed
	 * @param acceptanceStrategy	strategy to accept/discard recognized objects
	 * @return						all objects that match with more than minimumMatchingPercentageThreshold matching similarity.
	 */
	List<RecognizedObject<O>> recognizeObjects(S scene, RecognitionAcceptanceStrategy<O> acceptanceStrategy);
}
