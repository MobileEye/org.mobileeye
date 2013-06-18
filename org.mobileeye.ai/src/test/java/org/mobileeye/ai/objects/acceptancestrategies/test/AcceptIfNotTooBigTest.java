package org.mobileeye.ai.objects.acceptancestrategies.test;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mobileeye.ai.objects.BoundingBox;
import org.mobileeye.ai.objects.Point2d;
import org.mobileeye.ai.objects.RecognitionAcceptanceStrategy;
import org.mobileeye.ai.objects.RecognizedObject;
import org.mobileeye.ai.objects.acceptancestrategies.AcceptIfNotTooBig;

public class AcceptIfNotTooBigTest {
	
	private RecognitionAcceptanceStrategy<Object> acceptanceStrategy;
	
	@Before
	public void setup() {
		acceptanceStrategy = new AcceptIfNotTooBig<Object>(10.0f);
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
}
