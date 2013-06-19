/**
 * Copyright (c) Aakash Polra. 2013.
 * 
 * Licensed under The Non-Profit Open Software License version 3.0 (NPOSL-3.0).
 * Full license terms are available on the Open Source Initiative website.
 * http://opensource.org/licenses/NPOSL-3.0
 */
package org.mobileeye.ai.objects;

/**
 * Represents a two-dimensional point that has coordinates stored with double precision.
 * 
 * @author Aakash Polra
 */
public class Point2d {

	private double x;
	private double y;
	
	public Point2d(final double x, final double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public void setX(final double x) {
		this.x = x;
	}
	
	public void setY(final double y) {
		this.y = y;
	}
}
