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
 * have a rectangular shape.
 * This strategy is useful in object recognition when non-rectangular matches
 * should be discarded.
 * 
 * @author Aakash Polra
 *
 * @param <T> The type of {@link RecognizedObject}. T is not used by this implementation.
 */
public class AcceptOnlyValidBoundingBox<T> extends AbstractAcceptanceStrategy<T> {

	public AcceptOnlyValidBoundingBox() {
		this(new AcceptAll<T>());
	}
	
	public AcceptOnlyValidBoundingBox(final RecognitionAcceptanceStrategy<T> acceptanceStrategy) {
		super(acceptanceStrategy);
	}
	
	/**
	 * Returns true if the shape formed by the object bounding box is rectangular.
	 * Shape is considered rectangular if the sum of all the angles is 0.
	 * 
	 * @see RecognizedObject#getBoundingBox()
	 */
	@Override
	public boolean isAcceptable(RecognizedObject<T> object) {
		
		if (!super.isAcceptable(object)) {
			return false;
		}
		
		final Point2d p1 = object.getBoundingBox().getPoint1();
		final Point2d p2 = object.getBoundingBox().getPoint2();
		final Point2d p3 = object.getBoundingBox().getPoint3();
		final Point2d p4 = object.getBoundingBox().getPoint4();
		
		return (hasRectangularShape(p1, p2, p3, p4));
	}
	
	private boolean hasRectangularShape(Point2d p1, Point2d p2, Point2d p3, Point2d p4) {
		final double a1 = getAgle(p1, p2, p3);
		final double a2 = getAgle(p2, p3, p4);
		final double a3 = getAgle(p3, p4, p1);
		final double a4 = getAgle(p4, p1, p2);
		final double angleSum = a1 + a2 + a3 + a4;
		return (int)(angleSum * 1000) == 0;		// accuracy window because matching double may not be completely accurate
	}
	
	/**
	 * Returns angle between vectors p2->p1 and p2->p3 in radians
	 * @return angle in radians near point p2
	 */
	private double getAgle(Point2d p1, Point2d p2, Point2d p3) {
		double angle1 = Math.atan2(p1.getY() - p2.getY(), p1.getX() - p2.getX());
		double angle2 = Math.atan2(p3.getY() - p2.getY(), p3.getX() - p2.getX());
		return angle1-angle2;
	}
}
