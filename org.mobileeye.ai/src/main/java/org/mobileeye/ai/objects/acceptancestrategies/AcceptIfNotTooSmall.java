package org.mobileeye.ai.objects.acceptancestrategies;

import org.mobileeye.ai.objects.Point2d;
import org.mobileeye.ai.objects.RecognitionAcceptanceStrategy;
import org.mobileeye.ai.objects.RecognizedObject;

public class AcceptIfNotTooSmall<T> extends AbstractAcceptanceStrategy<T> {

	private final float minimumSize;

	public AcceptIfNotTooSmall(final float minimumSize) {
		this(new AcceptAll<T>(), minimumSize);
	}
	
	public AcceptIfNotTooSmall(final RecognitionAcceptanceStrategy<T> acceptanceStrategy, final float minimumSize) {
		super(acceptanceStrategy);
		this.minimumSize = minimumSize;
	}
	
	@Override
	public boolean isAcceptable(RecognizedObject<T> object) {
		
		if (!super.isAcceptable(object)) {
			return false;
		}
		
		Point2d p1 = object.getBoundingBox().getPoint1();
		Point2d p2 = object.getBoundingBox().getPoint2();
		Point2d p3 = object.getBoundingBox().getPoint3();
		Point2d p4 = object.getBoundingBox().getPoint4();
		
		return (!isTooSmall(p1, p2, p3, p4));
	}
	
	private boolean isTooSmall(Point2d... points) {
		final double minLengthSquare = this.minimumSize * this.minimumSize;
		for (int i=0; i < points.length; i++) {
			double dx = points[(i+1)%points.length].getX() - points[i].getX();
			double dy = points[(i+1)%points.length].getY() - points[i].getY();
			if (dx * dx + dy * dy < minLengthSquare) {
				return true;	// too small
			}
		}
		return false;	// not too small - acceptable
	}
}
