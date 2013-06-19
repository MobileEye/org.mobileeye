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

public abstract class AbstractAcceptanceStrategy<T> implements RecognitionAcceptanceStrategy<T> {

	private final RecognitionAcceptanceStrategy<T> acceptanceStrategy;
	
	public AbstractAcceptanceStrategy(final RecognitionAcceptanceStrategy<T> acceptanceStrategy) {
		this.acceptanceStrategy = acceptanceStrategy;
	}
	
	@Override
	public boolean isAcceptable(RecognizedObject<T> object) {
		return this.acceptanceStrategy.isAcceptable(object);
	}
}
