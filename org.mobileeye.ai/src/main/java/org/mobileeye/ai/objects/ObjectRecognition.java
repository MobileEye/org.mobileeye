package org.mobileeye.ai.objects;

import java.util.List;

/**
 * 
 * @author Aakash
 *
 * @param <S> data type of the Scene where object recognition is performed (Image for example)
 * @param <O> data type of the objects that are recognized (Image for example)
 */
public interface ObjectRecognition<S,O> {
	
	/**
	 * Adds the object to the list of recognizable objects
	 * @param recognizableObject	a known object to be added for recognition
	 */
	void addRecognizableObject(O recognizableObject);
	
	/**
	 * 
	 * @param scene									the scene where object recognition is performed
	 * @param minimumMatchingPercentageThreshold	0.00 -> all recognizable objects are returned. 1.00 -> only a perfect match is returned (unlikely).
	 * @return										all objects that match with more than minimumMatchingPercentageThreshold matching similarity.
	 */
	List<RecognizedObject<O>> recognizeObjects(S scene, double minimumMatchingPercentageThreshold);
}
