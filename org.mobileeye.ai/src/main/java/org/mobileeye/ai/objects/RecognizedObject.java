/**
 * Copyright (c) Aakash Polra. 2013.
 * 
 * Licensed under The Non-Profit Open Software License version 3.0 (NPOSL-3.0).
 * Full license terms are available on the Open Source Initiative website.
 * http://opensource.org/licenses/NPOSL-3.0
 */
package org.mobileeye.ai.objects;

/**
 * This class represents a Recognized Object containing arbitrary data of type D.
 * 
 * @author Aakash Polra
 *
 * @param <D> type of data to be stored with this object. For example, an Image
 */
public class RecognizedObject<D> {

	private String name;
	private BoundingBox boundingBox;
	private float confidence;
	private D objectData;
	
	public RecognizedObject(final String name, final BoundingBox boundingBox,
			final float confidence, final D objectData) {
		this.name = name;
		this.boundingBox = boundingBox;
		this.confidence = confidence;
		this.objectData = objectData;
	}

	public String getName() {
		return this.name;
	}

	public BoundingBox getBoundingBox() {
		return this.boundingBox;
	}

	public float getConfidence() {
		return this.confidence;
	}

	public D getObjectData() {
		return this.objectData;
	}
}
