package org.mobileeye.ai.objects.acceptancestrategies;

import org.mobileeye.ai.objects.RecognizedObject;

public class DefaultAcceptanceStrategy<T> extends AbstractAcceptanceStrategy<T> {

	private final float minimumConfidenceRequired;
	
	public DefaultAcceptanceStrategy(final float minimumConfidenceRequired,
			final float minimumSize, final float maximumSize) {
		super(new AcceptOnlyValidBoundingBox<>(
				new AcceptIfNotTooBig<T>(
						new AcceptIfNotTooSmall<T>(minimumSize),
						maximumSize)));
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
