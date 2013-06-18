package org.mobileeye.ai.objects;

public interface RecognitionAcceptanceStrategy<T> {

	boolean isAcceptable(RecognizedObject<T> object);
	
}
