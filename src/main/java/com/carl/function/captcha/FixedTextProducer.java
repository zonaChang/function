package com.carl.function.captcha;

import nl.captcha.text.producer.TextProducer;

public class FixedTextProducer implements TextProducer {
    private String textValue;

    public FixedTextProducer(String textValue) {
        this.textValue = textValue;
    }

    public FixedTextProducer() {
    }

	@Override
    public String getText() {
        return this.textValue;
    }
  
}