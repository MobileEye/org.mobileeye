package org.mobileeye.ai.objects.acceptancestrategies;

import org.mobileeye.ai.objects.RecognitionAcceptanceStrategy;
import org.mobileeye.ai.objects.RecognizedObject;

/**
 * Always accepts any object by returning true.
 * @author Aakash
 *
 * @param <T> Any type
 */
public class AcceptAll<T> extends AbstractAcceptanceStrategy<T> {

	public AcceptAll() {
		super(new RecognitionAcceptanceStrategy<T>() {
			@Override
			public boolean isAcceptable(RecognizedObject<T> object) {
				return true;	// always accept
			}
		});
	}
}
