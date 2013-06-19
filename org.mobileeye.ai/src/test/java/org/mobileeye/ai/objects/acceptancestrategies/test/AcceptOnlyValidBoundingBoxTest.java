package org.mobileeye.ai.objects.acceptancestrategies.test;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mobileeye.ai.objects.BoundingBox;
import org.mobileeye.ai.objects.Point2d;
import org.mobileeye.ai.objects.RecognitionAcceptanceStrategy;
import org.mobileeye.ai.objects.RecognizedObject;
import org.mobileeye.ai.objects.acceptancestrategies.AcceptOnlyValidBoundingBox;

public class AcceptOnlyValidBoundingBoxTest {
	
	private RecognitionAcceptanceStrategy<Object> acceptanceStrategy;
	
	@Before
	public void setup() {
		acceptanceStrategy = new AcceptOnlyValidBoundingBox<Object>();
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

	@Test
	public void nonCrossingLine1() {
		boolean result = acceptanceStrategy.isAcceptable(new RecognizedObject<Object>(
				"test-acceptable",
				new BoundingBox(new Point2d(0.0,  0.0),
						new Point2d(5.0,  3.85),
						new Point2d(5.5,  -2.3),
						new Point2d(3.4,  -2.31)),
				1.0f, new Object()));
		Assert.assertTrue(result);
	}
}
