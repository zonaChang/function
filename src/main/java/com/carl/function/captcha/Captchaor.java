package com.carl.function.captcha;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import nl.captcha.Captcha;
import nl.captcha.gimpy.BlockGimpyRenderer;
import nl.captcha.gimpy.FishEyeGimpyRenderer;
import nl.captcha.gimpy.GimpyRenderer;
import nl.captcha.gimpy.RippleGimpyRenderer;
import nl.captcha.noise.CurvedLineNoiseProducer;
import nl.captcha.text.renderer.ColoredEdgesWordRenderer;
import nl.captcha.text.renderer.DefaultWordRenderer;
import nl.captcha.text.renderer.WordRenderer;

public class Captchaor {

	    //验证码宽度
	    private static final int DEFAULT_WIDTH = 58;
	    //验证码高度
	    private static final int DEFAULT_HEIGHT = 35;
	    //干扰线大小
	    private static final float NOISE_SIZE = .1f;

	    private static final int COLOR_TOP_NUM = 200;
	    private static final Color FULL_TRANSPARENT = new Color(0.0F, 0.0F, 0.0F, 0.0F);
	    
	    public byte[] getCaptchaImage(String code) {
	        return getCaptchaImage(code, DEFAULT_WIDTH, DEFAULT_HEIGHT, (int)(DEFAULT_HEIGHT*.6));
	    }

	    /**
	     * 
	     * @desc 获取图片验证码
	     * @param code 验证码值
	     * @param w 验证图片宽度
	     * @param h 验证码图片高度
	     * @param baseFrontSize 验证码字体大小
	     * @return 图片验证码byte数组
	     */
	    public byte[] getCaptchaImage(String code, int w, int h, int baseFrontSize) {

	        WordRenderer wordRenderer = getRandomWordRenderer(baseFrontSize);
	        FixedTextProducer textValue = new FixedTextProducer(code);

	        Captcha.Builder builder = new Captcha.Builder(w, h);
	        
	        // 验证码添加干扰线
	        builder.addNoise(new CurvedLineNoiseProducer(getRandomColor(), NOISE_SIZE));
	        
	        // 添加验证码值
	        builder.addText(textValue, wordRenderer);
	        builder.gimp(getRandomGimpyRenderer(baseFrontSize));

	        Captcha captcha = builder.build();

	        ByteArrayOutputStream arrayStream = new ByteArrayOutputStream();
	        try {
	            ImageIO.write(captcha.getImage(), "png", arrayStream);
	        } catch (IOException e) {
	            return null;
	        }
	        byte[] data = arrayStream.toByteArray();
	        return data;
	    }

	    private GimpyRenderer getRandomGimpyRenderer(int frontSize){
	        int seed = (new Random()).nextInt(3);
	        switch (seed){
	            case 0: return new RippleGimpyRenderer();
	            case 1: return new BlockGimpyRenderer(frontSize/10);
	            case 2: return new FishEyeGimpyRenderer(FULL_TRANSPARENT, FULL_TRANSPARENT);
	        }
	        return null;
	    }

	    private WordRenderer getRandomWordRenderer(int frontSize){
	        Random random = new Random();
	        List<Font> fontList = Arrays.asList(new Font(null, Font.BOLD, frontSize));
	        List<Color> colorList = Arrays.asList(getRandomColor(),getRandomColor(),getRandomColor(),getRandomColor());

	        int seed = (new Random()).nextInt(2);
	        switch (seed){
	            case 0: return new ColoredEdgesWordRenderer(colorList, fontList, frontSize/20);
	            case 1: return new DefaultWordRenderer(colorList, fontList);
	        }
	        return null;
	    }

	    private Color getRandomColor(){
	        Random random = new Random();
	        return new Color(random.nextInt(COLOR_TOP_NUM), random.nextInt(COLOR_TOP_NUM), random.nextInt(COLOR_TOP_NUM));
	    }

}