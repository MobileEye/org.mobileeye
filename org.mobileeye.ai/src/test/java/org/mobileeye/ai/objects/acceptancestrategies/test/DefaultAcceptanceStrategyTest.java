/**
 * Copyright (c) Aakash Polra. 2013.
 * 
 * Licensed under The Non-Profit Open Software License version 3.0 (NPOSL-3.0).
 * Full license terms are available on the Open Source Initiative website.
 * http://opensource.org/licenses/NPOSL-3.0
 */
package org.mobileeye.ai.objects.acceptancestrategies.test;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mobileeye.ai.objects.BoundingBox;
import org.mobileeye.ai.objects.Point2d;
import org.mobileeye.ai.objects.RecognitionAcceptanceStrategy;
import org.mobileeye.ai.objects.RecognizedObject;
import org.mobileeye.ai.objects.acceptancestrategies.DefaultAcceptanceStrategy;

public class DefaultAcceptanceStrategyTest {

	private RecognitionAcceptanceStrategy<Object> acceptanceStrategy;
	
	@Before
	public void setup() {
		acceptanceStrategy = new DefaultAcceptanceStrategy<Object>(0.8f, 1.0f, 10.0f);
	}
	
	@Test
	public void notConfidentObjectFails() {
		boolean result = acceptanceStrategy.isAcceptable(new RecognizedObject<Object>(
				"test-not-confident",
				new BoundingBox(new Point2d(0.0,  0.0),
						new Point2d(5.0,  0.0),
						new Point2d(5.0,  5.0),
						new Point2d(0.0,  5.0)),
				0.79f, new Object()));
		Assert.assertFalse(result);
	}
	
	@Test
	public void tooSmallObjectFails() {
		boolean result = acceptanceStrategy.isAcceptable(new RecognizedObject<Object>(
				"test-too-small",
				new BoundingBox(
						new Point2d(0.0,  0.0),
						new Point2d(0.9,  0.0),
						new Point2d(0.9,  0.9),
						new Point2d(0.0,  0.9)),
				1.0f, new Object()));
		Assert.assertFalse(result);
	}
	
	@Test
	public void tooBigObjectFails() {
		boolean result = acceptanceStrategy.isAcceptable(new RecognizedObject<Object>(
				"test-too-big",
				new BoundingBox(new Point2d(0.0,  0.0),
						new Point2d(11.0,  0.0),
						new Point2d(11.0,  11.0),
						new Point2d(0.0,  11.0)),
				1.0f, new Object()));
		Assert.assertFalse(result);
	}
	
	@Test
	public void objectWithInvalidPointOrderFails() {
		boolean result = acceptanceStrategy.isAcceptable(new RecognizedObject<Object>(
				"test-crossing-lines",
				new BoundingBox(new Point2d(0.0,  0.0),
						new Point2d(5.0,  0.0),
						new Point2d(0.0,  5.0),
						new Point2d(5.0,  5.0)),
				1.0f, new Object()));
		Assert.assertFalse(result);
	}
	
	@Test
	public void acceptableObjectMinRange() {
		boolean result = acceptanceStrategy.isAcceptable(new RecognizedObject<Object>(
				"test-acceptable-min-range",
				new BoundingBox(new Point2d(0.0,  0.0),
						new Point2d(1.0,  0.0),
						new Point2d(1.0,  1.0),
						new Point2d(0.0,  1.0)),
				1.0f, new Object()));
		Assert.assertTrue(result);
	}

	@Test
	public void acceptableObjectMaxRange() {
		boolean result = acceptanceStrategy.isAcceptable(new RecognizedObject<Object>(
				"test-acceptable-max-range",
				new BoundingBox(new Point2d(0.0,  0.0),
						new Point2d(10.0,  0.0),
						new Point2d(10.0,  10.0),
						new Point2d(0.0,  10.0)),
				1.0f, new Object()));
		Assert.assertTrue(result);
	}
	
	@Test
	public void acceptableObject() {
		boolean result = acceptanceStrategy.isAcceptable(new RecognizedObject<Object>(
				"test-acceptable",
				new BoundingBox(new Point2d(0.0,  0.0),
						new Point2d(5.0,  0.0),
						new Point2d(5.0,  5.0),
						new Point2d(0.0,  5.0)),
				1.0f, new Object()));
		Assert.assertTrue(result);
	}
	
}
