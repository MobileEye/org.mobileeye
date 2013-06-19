/**
 * Copyright (c) Aakash Polra. 2013.
 * 
 * Licensed under The Non-Profit Open Software License version 3.0 (NPOSL-3.0).
 * Full license terms are available on the Open Source Initiative website.
 * http://opensource.org/licenses/NPOSL-3.0
 */
package org.mobileeye.ai.objects.acceptancestrategies;

import org.mobileeye.ai.objects.RecognitionAcceptanceStrategy;
import org.mobileeye.ai.objects.RecognizedObject;

/**
 * Acceptance strategy that accepts all objects.
 * 
 * @author Aakash Polra

 * @param <T> Type of {@link RecognizedObject}. T is not used by this strategy.
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
