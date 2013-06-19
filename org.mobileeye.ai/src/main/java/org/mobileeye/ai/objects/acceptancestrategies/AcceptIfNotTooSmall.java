/**
 * Copyright (c) Aakash Polra. 2013.
 * 
 * Licensed under The Non-Profit Open Software License version 3.0 (NPOSL-3.0).
 * Full license terms are available on the Open Source Initiative website.
 * http://opensource.org/licenses/NPOSL-3.0
 */
package org.mobileeye.ai.objects.acceptancestrategies;

import org.mobileeye.ai.objects.Point2d;
import org.mobileeye.ai.objects.RecognitionAcceptanceStrategy;
import org.mobileeye.ai.objects.RecognizedObject;

/**
 * Acceptance strategy for {@link RecognizedObject} that accepts objects that
 * are not too small.
 * This strategy is useful in object recognition when objects smaller than
 * an acceptable threshold should be discarded.
 * 
 * @author Aakash Polra
 *
 * @param <T> The type of {@link RecognizedObject}. T is not used by this implementation.
 * @see AcceptIfNotTooBig
 */
public class AcceptIfNotTooSmall<T> extends AbstractAcceptanceStrategy<T> {

	private final float minimumDistance;

	/**
	 * Constructs a new instance using the given minimumDistance threshold.
	 * @param minimumDistance	minimum allowed distance between any two points
	 */
	public AcceptIfNotTooSmall(final float minimumDistance) {
		this(new AcceptAll<T>(), minimumDistance);
	}
	
	/**
	 * Constructs a new instance using the given minimumDistance threshold and an
	 * acceptance strategy. The given {@link RecognitionAcceptanceStrategy} is checked
	 * first when using this{@link #isAcceptable(RecognizedObject)} method.
	 * 
	 * @param acceptanceStrategy	parent acceptance strategy
	 * @param minimumDistance		minimum allowed distance between any two points
	 */
	public AcceptIfNotTooSmall(final RecognitionAcceptanceStrategy<T> acceptanceStrategy, final float minimumDistance) {
		super(acceptanceStrategy);
		this.minimumDistance = minimumDistance;
	}
	
	/**
	 * Returns true if the object is acceptable by the parent acceptable strategy (if any)
	 * AND if the distance between any two points of the object bounding box is not less
	 * than the maximumDistance value.
	 */
	@Override
	public boolean isAcceptable(final RecognizedObject<T> object) {
		
		if (!super.isAcceptable(object)) {
			return false;
		}
		
		final Point2d p1 = object.getBoundingBox().getPoint1();
		final Point2d p2 = object.getBoundingBox().getPoint2();
		final Point2d p3 = object.getBoundingBox().getPoint3();
		final Point2d p4 = object.getBoundingBox().getPoint4();
		
		return (!isTooSmall(p1, p2, p3, p4));
	}
	
	private boolean isTooSmall(final Point2d... points) {
		final double minLengthSquare = this.minimumDistance * this.minimumDistance;
		for (int i=0; i < points.length; i++) {
			final double dx = points[(i+1)%points.length].getX() - points[i].getX();
			final double dy = points[(i+1)%points.length].getY() - points[i].getY();
			if (dx * dx + dy * dy < minLengthSquare) {
				return true;	// too small
			}
		}
		return false;	// not too small - acceptable
	}
}
