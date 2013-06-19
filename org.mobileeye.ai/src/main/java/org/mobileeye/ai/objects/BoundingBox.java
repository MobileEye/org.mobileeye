/**
 * Copyright (c) Aakash Polra. 2013.
 * 
 * Licensed under The Non-Profit Open Software License version 3.0 (NPOSL-3.0).
 * Full license terms are available on the Open Source Initiative website.
 * http://opensource.org/licenses/NPOSL-3.0
 */
package org.mobileeye.ai.objects;

public class BoundingBox {

	private final Point2d point1;
	private final Point2d point2;
	private final Point2d point3;
	private final Point2d point4;
	
	public BoundingBox(final Point2d point1, final Point2d point2, final Point2d point3, final Point2d point4) {
		this.point1 = point1;
		this.point2 = point2;
		this.point3 = point3;
		this.point4 = point4;
	}

	public Point2d getPoint1() {
		return point1;
	}

	public Point2d getPoint2() {
		return point2;
	}

	public Point2d getPoint3() {
		return point3;
	}

	public Point2d getPoint4() {
		return point4;
	}
}
