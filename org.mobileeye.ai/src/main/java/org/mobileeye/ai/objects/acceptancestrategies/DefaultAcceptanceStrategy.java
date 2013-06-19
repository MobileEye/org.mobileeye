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
 * Uses a mixture of {@link RecognitionAcceptanceStrategy} strategies to accept
 * objects that:
 * 	have enough confidence level
 * 	are not too big
 * 	are not too small
 * 	have valid rectangular shape

 * @author Aakash Polra
 *
 * @param <T> The type of {@link RecognizedObject}. T is not used by this implementation.
 * @see AcceptIfNotTooBig
 * @see AcceptIfNotTooSmall
 * @see AcceptOnlyValidBoundingBox
 */
public class DefaultAcceptanceStrategy<T> extends AbstractAcceptanceStrategy<T> {

	private final float minimumConfidenceRequired;
	
	public DefaultAcceptanceStrategy(final float minimumConfidenceRequired,
			final float minimumDistance, final float maximumDistance) {
		super(new AcceptOnlyValidBoundingBox<>(
				new AcceptIfNotTooBig<T>(
						new AcceptIfNotTooSmall<T>(minimumDistance),
						maximumDistance)));
		this.minimumConfidenceRequired = minimumConfidenceRequired;
	}
	
	@Override
	public boolean isAcceptable(RecognizedObject<T> object) {
		if (!hasEnoughConfidence(object)) {
			return false;
		}
		return super.isAcceptable(object);
	}

	private boolean hasEnoughConfidence(RecognizedObject<T> object) {
		return object.getConfidence() >= this.minimumConfidenceRequired;
	}
}
