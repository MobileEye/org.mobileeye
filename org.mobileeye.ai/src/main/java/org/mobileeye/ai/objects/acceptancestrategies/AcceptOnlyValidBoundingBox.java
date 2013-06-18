package org.mobileeye.ai.objects.acceptancestrategies;

import org.mobileeye.ai.objects.Point2d;
import org.mobileeye.ai.objects.RecognitionAcceptanceStrategy;
import org.mobileeye.ai.objects.RecognizedObject;

public class AcceptOnlyValidBoundingBox<T> extends AbstractAcceptanceStrategy<T> {

	public AcceptOnlyValidBoundingBox() {
		this(new AcceptAll<T>());
	}
	
	public AcceptOnlyValidBoundingBox(final RecognitionAcceptanceStrategy<T> acceptanceStrategy) {
		super(acceptanceStrategy);
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
		
		return (!hasCrossingLines(p1, p2, p3, p4));
	}
	
	private boolean hasCrossingLines(Point2d p1, Point2d p2, Point2d p3, Point2d p4) {
		double a1 = getAgle(p1, p2, p3);
		double a2 = getAgle(p2, p3, p4);
		double a3 = getAgle(p3, p4, p1);
		double a4 = getAgle(p4, p1, p2);
		double angleSum = a1 + a2 + a3 + a4;
		double degrees = Math.toDegrees(angleSum);
		double degreesRounded = Math.abs(Math.round(degrees));
		return ((int)degreesRounded) != 90;
	}
	
	private double getAgle(Point2d p1, Point2d p2, Point2d p3) {
		final double dotProduct = getDotProduct(p1, p2, p3);
		final double crossProduct = getCrossProductLength(p1, p2, p3);
		
		return Math.atan2(crossProduct, dotProduct);
	}
	
	private double getDotProduct(Point2d p1, Point2d p2, Point2d p3) {
	    final double BAx = p1.getX() - p2.getX();
		final double BAy = p1.getY() - p2.getY();
		final double BCx = p3.getX() - p2.getX();
		final double BCy = p3.getX() - p2.getX();

		return BAx * BCx + BAy * BCy;
	}
	
	private double getCrossProductLength(Point2d p1, Point2d p2, Point2d p3) {
	    final double BAx = p1.getX() - p2.getX();
		final double BAy = p1.getY() - p2.getY();
		final double BCx = p3.getX() - p2.getX();
		final double BCy = p3.getX() - p2.getX();

		return BAx * BCy - BAy * BCx;
	}
}
